package com.tyut.travel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.travel.mapper.PopularMapper;
import com.tyut.travel.pojo.Popular;
import com.tyut.travel.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PopularService {

    @Autowired
    private PopularMapper popularMapper;

    //分页查询所有popular
    public Page<Popular> FindPage(Integer page, Integer size){
        return popularMapper.findPopularPage(new Page<>(page, size));
    }

    //添加数据
    public void add(Popular popular){
        popularMapper.insert(popular);
    }

    //修改状态
    public void updateStatus(Integer pid){
        Popular popular = popularMapper.selectById(pid);
        popular.setStatus(!popular.isStatus());
        popularMapper.updateById(popular);
    }

    //删除热门产品
    public void delete(Integer pid){
        popularMapper.deleteById(pid);
    }

    //根据id查询
    public Popular findById(Integer pid){
        return popularMapper.selectById(pid);
    }

    //更新
    public void update(Popular popular){
        popularMapper.updateById(popular);
    }

    //查询热门产品
    public List<Popular> findPopular(){
        return popularMapper.selectList(null);
    }

    //查询详情
    public Popular findPopularDesc(Integer pid){
        return popularMapper.selectById(pid);
    }
}
