package com.tyut.travel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.travel.mapper.ProductMapper;
import com.tyut.travel.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FavoriteService {

    @Autowired
    private ProductMapper productMapper;

    //查询用户是否收藏线路
    public boolean isFavorite(Integer pid,Integer mid){
        int isFavorite = productMapper.findIsFavorite(pid, mid);
        return isFavorite == 1;
    }

    //收藏
    public void addFavorite(Integer pid,Integer mid){
        productMapper.addFavorite(pid,mid);
    }

    //取消收藏
    public void delFavorite(Integer pid,Integer mid){
        productMapper.delFavorite(pid,mid);
    }


    //查询我的收藏
    public Page<Product> findMyFavorite(Integer mid,Integer page,Integer size){
        return productMapper.findMyFavorite(mid, new Page<>(page, size));
    }
}
