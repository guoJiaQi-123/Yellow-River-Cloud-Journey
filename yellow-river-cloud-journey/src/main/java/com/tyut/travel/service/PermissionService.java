package com.tyut.travel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.travel.mapper.PermissionMapper;
import com.tyut.travel.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    //查询权限
    public Page<Permission> findPage(int page,int size){
        return permissionMapper.selectPage(new Page<>(page, size), null);
    }

    //新增权限
    public void insertPermission(Permission permission){
        permissionMapper.insert(permission);
    }

    //修改权限(根据id先查询权限)
    public Permission findPermissionByPid(Integer pid){
        return permissionMapper.selectById(pid);
    }

    //修改权限(更新权限)
    public void updatePermission(Permission permission){
        permissionMapper.updateById(permission);
    }

    //删除权限
    public void delete(int pid){
        permissionMapper.deleteById(pid);
    }

}
