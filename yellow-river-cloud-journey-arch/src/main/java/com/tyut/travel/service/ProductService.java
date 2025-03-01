package com.tyut.travel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.travel.mapper.PopularMapper;
import com.tyut.travel.mapper.ProductMapper;
import com.tyut.travel.pojo.Popular;
import com.tyut.travel.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class ProductService {


    @Autowired
    private ProductMapper productMapper;



    //分页查询
    public Page<Product> findPage(int page, int size) {

        return productMapper.findProductPage(new Page<>(page, size));
    }

    //新增产品
    public void addProduct(Product product){
        productMapper.insert(product);
    }

    //删除产品
    public void deleteProduct(Integer pid){
        productMapper.deleteById(pid);
    }

    //修改产品
    public void updateProduct(Product product){
        productMapper.updateById(product);
    }

    //根据id查询产品，包括相关联的产品分类
    public Product findOne(Integer pid){
        return productMapper.findOne(pid);
    }

    //根据id查询产品
    /*
    public Product findById(Integer pid){
        return productMapper.selectById(pid);
    }*/

    //更新产品状态
    public void updateStatus(Integer pid){
        Product product = productMapper.selectById(pid);
        product.setStatus(!product.isStatus());
        productMapper.updateById(product);
    }


    //查询旅游列表
    public Page<Product> findTravelList(Integer cid,String productName,int page, int size) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        if(cid!=null){
            queryWrapper.eq("cid",cid);
        }
        if(StringUtils.hasText(productName)){
            queryWrapper.like("productName",productName);
        }
        //查询产品状态是1
        queryWrapper.eq("status",1);
        queryWrapper.orderByDesc("pid");
        return productMapper.selectPage(new Page<>(page, size), queryWrapper);

    }


}
