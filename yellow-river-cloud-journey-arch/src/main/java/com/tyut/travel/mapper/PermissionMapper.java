package com.tyut.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyut.travel.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

    //根据角色ID查询所有权限id
    List<Integer> findPermissionByRoleId(Integer rid);

    //根据角色ID删除所有权限
    void deletePermissionByRoleId(Integer rid);

    //添加权限
    void insertPermissionById(@Param("rid") Integer rid, @Param("pid") Integer pid);
}
