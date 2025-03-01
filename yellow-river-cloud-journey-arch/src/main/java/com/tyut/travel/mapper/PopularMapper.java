package com.tyut.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.travel.pojo.Popular;
import com.tyut.travel.pojo.Product;

public interface PopularMapper extends BaseMapper<Popular> {

    Page<Popular> findPopularPage(Page<Popular> PopularPage);
}
