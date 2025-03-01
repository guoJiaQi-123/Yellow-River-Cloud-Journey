import { listMyChartByPageUsingPost } from '@/services/tyut-bi/chartController';
import { useModel } from '@@/exports';
import { SizeType } from '@ant-design/pro-form/es/BaseForm';
import { Avatar, Card, Divider, Flex, List, message, Result, Switch } from 'antd';
import Search from 'antd/es/input/Search';
import ReactECharts from 'echarts-for-react';
import React, { useEffect, useState } from 'react';

/**
 * 我的图表页面
 * @constructor
 */
const MyChartPage: React.FC = () => {
  const initSearchParams = {
    current: 1,
    pageSize: 4,
    sortField: 'createTime',
    sortOrder: 'desc',
  };

  const [searchParams, setSearchParams] = useState<API.ChartQueryRequest>({ ...initSearchParams });
  const { initialState } = useModel('@@initialState');
  const { currentUser } = initialState ?? {};
  const [chartList, setChartList] = useState<API.Chart[]>();
  const [total, setTotal] = useState<number>(0);
  const [loading, setLoading] = useState<boolean>(true);
  const [showGenRes, setShowGenRes] = useState<boolean>(true);
  const [showChartData, setShowChartData] = useState<boolean>(true);

  const loadData = async () => {
    setLoading(true);
    try {
      const res = await listMyChartByPageUsingPost(searchParams);
      if (res.data) {
        setChartList(res.data.records ?? []);
        setTotal(res.data.total ?? 0);
        // 隐藏图表的 title
        if (res.data.records) {
          res.data.records.forEach((data) => {
            // if (data.status === 'succeed') {
            const chartOption = JSON.parse(data.genChart ?? '{}');
            chartOption.title = undefined;
            data.genChart = JSON.stringify(chartOption);
            // }
          });
        }
      } else {
        message.error('获取我的图表失败');
      }
    } catch (e: any) {
      message.error('获取我的图表失败，' + e.message);
    }
    setLoading(false);
  };

  const onChange = (checked: boolean) => {
    setShowGenRes(checked);
  };

  useEffect(() => {
    loadData();
  }, [searchParams]);

  const onChangeShowChartData = (checked: boolean) => {
    setShowChartData(checked);
  };
  const [gapSize] = React.useState<SizeType | 'customize'>('small');
  const [customGapSize] = React.useState<number>(0);
  return (
    <div className="my-chart-page">
      {/* 搜索组件 */}
      <div>
        <Search
          placeholder="请输入图表名称"
          enterButton
          loading={loading}
          onSearch={(value) => {
            // 设置搜索条件
            setSearchParams({
              ...initSearchParams,
              name: value,
            });
          }}
        />
      </div>
      <Divider />

      <Flex gap="middle" vertical>
        {/*<Radio.Group value={gapSize} onChange={(e) => setGapSize(e.target.value)}>*/}
        {/*  {['small', 'middle', 'large', 'customize'].map((size) => (*/}
        {/*    <Radio key={size} value={size}>*/}
        {/*      {size}*/}
        {/*    </Radio>*/}
        {/*  ))}*/}
        {/*</Radio.Group>*/}
        {/*{gapSize === 'customize' && <Slider value={customGapSize} onChange={setCustomGapSize} />}*/}

        <Flex gap={gapSize !== 'customize' ? gapSize : customGapSize}>
          是否显示分析结论：
          <Switch
            checkedChildren="开启"
            unCheckedChildren="关闭"
            defaultChecked
            onChange={onChange}
          />
          是否显示原始数据：
          <Switch
            checkedChildren="开启"
            unCheckedChildren="关闭"
            defaultChecked
            onChange={onChangeShowChartData}
          />
        </Flex>
      </Flex>
      <div style={{ marginBottom: '20px' }} />
      <List
        grid={{
          // 动态修改
          gutter: 16,
          xs: 1,
          sm: 1,
          md: 1,
          lg: 2,
          xl: 2,
          xxl: 2,
        }}
        pagination={{
          // 分页
          onChange: (page, pageSize) => {
            setSearchParams({
              ...searchParams,
              current: page,
              pageSize,
            });
          },
          current: searchParams.current,
          pageSize: searchParams.pageSize,
          total: total,
        }}
        loading={loading}
        dataSource={chartList}
        renderItem={(item) => (
          <List.Item key={item.id}>
            <Card style={{ width: '100%' }}>
              <List.Item.Meta
                avatar={<Avatar src={currentUser && currentUser.userAvatar} />}
                title={item.name}
                description={item.chartType ? '图表类型：' + item.chartType : undefined}
              />
              <>
                {item.state === 0 && ( // 待生成
                  <>
                    <Result
                      status="warning"
                      title="待生成"
                      subTitle={item.execMessage ?? '当前图表生成队列繁忙，请耐心等候'}
                    />
                  </>
                )}
                {item.state === 1 && ( // 生成中
                  <>
                    <Result status="info" title="图表生成中" subTitle={item.execMessage} />
                  </>
                )}
                {item.state === 3 && ( // 生成成功
                  <>
                    <div style={{ marginBottom: 16 }} />
                    <p>{'分析目标：' + item.goal}</p>
                    <div style={{ marginBottom: 16 }} />
                    <ReactECharts option={item.genChart && JSON.parse(item.genChart)} />
                    {showGenRes && (
                      <p>
                        <b>{'分析结论：'}</b>
                        {item.genResult}
                      </p>
                    )}

                    {showChartData && (
                      <p>
                        <b>{'原始数据：'}</b>
                        {item.chartData}
                      </p>
                    )}
                  </>
                )}
                {item.state === 2 && ( // 生成失败
                  <>
                    <Result status="error" title="图表生成失败" subTitle={item.execMessage} />
                  </>
                )}
              </>
            </Card>
          </List.Item>
        )}
      />
    </div>
  );
};
export default MyChartPage;
