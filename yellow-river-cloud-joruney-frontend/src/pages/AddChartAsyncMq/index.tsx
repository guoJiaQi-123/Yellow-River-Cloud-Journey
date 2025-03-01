import { genChartByAiAsyncMqUsingPost } from '@/services/tyut-bi/chartController';
import { UploadOutlined } from '@ant-design/icons';
import { Button, Card, Form, Input, message, Result, Select, Space, Upload } from 'antd';
import { useForm } from 'antd/es/form/Form';
import TextArea from 'antd/es/input/TextArea';
import React, { useState } from 'react';

/**
 * 添加图表页面
 * @constructor
 */
const AddChart: React.FC = () => {
  const [form] = useForm();
  const [submitting, setSubmitting] = useState<boolean>(false);
  const [success, setSuccess] = useState<boolean>(false);

  /**
   * 提交
   * @param values
   */
  const onFinish = async (values: any) => {
    // 避免重复提交
    if (submitting) {
      return;
    }
    setSubmitting(true);
    // 对接后端，上传数据
    const params = {
      ...values,
      file: undefined,
    };
    try {
      const res = await genChartByAiAsyncMqUsingPost(params, {}, values.file.file.originFileObj);
      if (!res?.data) {
        // 如果res。data不存在，则抛出错误
        message.error('分析失败');
      } else {
        message.success('分析任务提交成功，稍后请在我的图表页面查看');
        setSuccess(true);
        // const chartOption = JSON.parse(res.data.genChart ?? '');
        // if (!chartOption) {
        //   throw new Error('图表代码解析错误');
        // }
        form.resetFields();
      }
    } catch (e: any) {
      message.error('分析失败，' + e.message);
    }
    setSubmitting(false);
  };

  return (
    <div className="add-chart-async">
      {!success && (
        <Card title="智能分析-基于消息队列">
          <Form
            form={form}
            name="addChart"
            labelAlign="left"
            labelCol={{ span: 4 }}
            wrapperCol={{ span: 16 }}
            onFinish={onFinish}
            initialValues={{}}
          >
            <Form.Item
              name="goal"
              label="分析目标"
              rules={[{ required: true, message: '请输入分析目标' }]}
            >
              <TextArea placeholder="请输入你的分析需求，比如：分析网站用户的增长情况" />
            </Form.Item>
            <Form.Item name="name" label="图表名称">
              <Input placeholder="请输入图表名称" />
            </Form.Item>
            <Form.Item name="chartType" label="图表类型">
              <Select
                options={[
                  { value: '折线图', label: '折线图' },
                  { value: '柱状图', label: '柱状图' },
                  { value: '堆叠图', label: '堆叠图' },
                  { value: '饼图', label: '饼图' },
                  { value: '雷达图', label: '雷达图' },
                ]}
              />
            </Form.Item>
            <Form.Item name="file" label="原始数据">
              <Upload name="file" maxCount={1}>
                <Button icon={<UploadOutlined />}>上传 CSV 文件</Button>
              </Upload>
            </Form.Item>

            <Form.Item wrapperCol={{ span: 16, offset: 4 }}>
              <Space>
                <Button type="primary" htmlType="submit" loading={submitting} disabled={submitting}>
                  提交
                </Button>
                <Button htmlType="reset">重置</Button>
              </Space>
            </Form.Item>
          </Form>
        </Card>
      )}
      {success && (
        <Result
          status="success"
          title="分析任务提交成功，稍后请在我的图表页面查看"
          subTitle="分析工作可能需要10-20秒，请稍后"
          extra={[
            <Button type="primary" key="console" href={'http://localhost:8000/my_chart'}>
              我的图表
            </Button>,
            <Button
              key="buy"
              onClick={() => {
                setSuccess(false);
              }}
            >
              再次分析
            </Button>,
          ]}
        />
      )}
    </div>
  );
};
export default AddChart;
