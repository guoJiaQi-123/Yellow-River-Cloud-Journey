package com.tyut.travel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.travel.bean.PermissionWithStatus;
import com.tyut.travel.mapper.PermissionMapper;
import com.tyut.travel.mapper.RoleMapper;
import com.tyut.travel.pojo.Permission;
import com.tyut.travel.pojo.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    //查询角色
    public Page<Role> findRole(int page, int size) {
        return roleMapper.selectPage(new Page<>(page, size), null);
    }

    //新增角色
    public void insertRole(Role role) {
        roleMapper.insert(role);
    }

    //根据id查询角色
    public Role selectById(int rid) {
        return roleMapper.selectById(rid);
    }

    //修改角色
    public void updateRole(Role role) {
        roleMapper.updateById(role);
    }

    //删除角色
    public void deleteRole(Integer rid) {
        roleMapper.deleteById(rid);
    }

    //角色拥有的权限
    public List<PermissionWithStatus> permissionWithStatuses(Integer rid) {
        //先查询出所有权限
        List<Permission> permissions = permissionMapper.selectList(null);
        //再查询该角色拥有的权限
        List<Integer> permissionByRoleId = permissionMapper.findPermissionByRoleId(rid);

        List<PermissionWithStatus> permissionWithStatusList = new ArrayList<>();
        for (Permission permission : permissions) {
            PermissionWithStatus permissionWithStatus = new PermissionWithStatus();
            BeanUtils.copyProperties(permission,permissionWithStatus);
            //查看角色拥有的权限
            permissionWithStatus.setRoleHas(permissionByRoleId.contains(permission.getPid()));
            permissionWithStatusList.add(permissionWithStatus);
        }
        return permissionWithStatusList;
    }

    //删除所有的权限
    public void updatePermission(int rid,int[] ids){
        permissionMapper.deletePermissionByRoleId(rid);
        int i = 1/0;
        for (int id : ids) {
            permissionMapper.insertPermissionById(rid,id);
        }
    }



}
