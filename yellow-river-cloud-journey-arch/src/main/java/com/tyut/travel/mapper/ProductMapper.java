package com.tyut.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.travel.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper extends BaseMapper<Product> {


    Page<Product> findProductPage(Page<Product> productPage);

    Product findOne(Integer pid);

    //查询收藏表
    int findIsFavorite(@Param("pid")int pid,@Param("mid")int mid);

    //收藏产品
    void addFavorite(@Param("pid")int pid,@Param("mid")int mid);

    //取消收藏
    void delFavorite(@Param("pid")int pid,@Param("mid")int mid);

    //查询我的收藏
    Page<Product> findMyFavorite(Integer mid,Page<Product> page);
}
