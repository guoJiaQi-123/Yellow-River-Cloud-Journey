package com.tyut.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyut.travel.pojo.Admin;
import com.tyut.travel.pojo.Permission;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin>{

    Admin findDesc(Integer aid);

    //根据管理员姓名查询管理员拥有的所有权限
    List<Permission> findAllPermission(String username);

}
