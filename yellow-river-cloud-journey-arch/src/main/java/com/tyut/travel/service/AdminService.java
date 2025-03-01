package com.tyut.travel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.travel.bean.RoleWithStatus;
import com.tyut.travel.mapper.AdminMapper;
import com.tyut.travel.mapper.RoleMapper;
import com.tyut.travel.pojo.Admin;
import com.tyut.travel.pojo.Permission;
import com.tyut.travel.pojo.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdminService {

    private final AdminMapper adminMapper;

    private final RoleMapper roleMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminService(AdminMapper adminMapper, RoleMapper roleMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.adminMapper = adminMapper;
        this.roleMapper = roleMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //Admin分页查询 业务层
    @SuppressWarnings("all")
    public Page<Admin> findPage(int page, int size) {
        Page<Admin> selectPage = adminMapper.selectPage(new Page<>(page, size), null);
        return selectPage;
    }

    //新增管理员
    public void add(Admin admin) {
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        adminMapper.insert(admin);
    }

    //根据ID查询管理员
    public Admin findById(Integer aid) {
        return adminMapper.selectById(aid);
    }

    //根据管理员ID修改管理员
    public void updateById(Admin admin) {
        //获取旧密码
        String oldPassword = adminMapper.selectById(admin.getAid()).getPassword();
        //获取新密码
        String newPassword = admin.getPassword();
        //如果新密码和旧密码不同，才进行加密
        if (!newPassword.equals(oldPassword)) {
            admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        }
        adminMapper.updateById(admin);
    }

    //根据管理员ID查询管理员详情
    public Admin findDesc(Integer aid) {
        return adminMapper.findDesc(aid);
    }

    //根据管理员ID查询管理员拥有的所有角色
    public List<RoleWithStatus> findRole(Integer aid) {
        //查询所有的角色
        List<Role> roles = roleMapper.selectList(null);
        //查询用户拥有的角色
        List<Integer> roleByAdmin = roleMapper.findRoleByAdmin(aid);
        List<RoleWithStatus> roleWithStatusList = new ArrayList<>();
        //遍历所有角色
        for (Role role : roles) {
            RoleWithStatus roleWithStatus = new RoleWithStatus();
            BeanUtils.copyProperties(role, roleWithStatus);
            roleWithStatus.setAdminHas(roleByAdmin.contains(role.getRid()));
            roleWithStatusList.add(roleWithStatus);
        }
        return roleWithStatusList;
    }

    //修改用户角色
    public void updateRole(Integer aid, Integer[] ids) {
        //删除当前用户原有的所有角色
        roleMapper.deleteByAdminId(aid);
        if (ids == null) {
            return;
        }
        for (Integer rid : ids) {
            roleMapper.insertByAdminId(aid, rid);
        }
    }

    //修改用户状态
    public void updateStatus(Integer aid) {
        Admin admin = adminMapper.selectById(aid);
        admin.setStatus(!admin.isStatus());
        adminMapper.updateById(admin);
    }

    //删除管理员
    public void delete(Integer aid) {
        adminMapper.deleteById(aid);
    }

    //根据管理员名字查询管理员
    public Admin findUserByUsername(String username) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return adminMapper.selectOne(queryWrapper);
    }

    //根据管理员姓名查询管理员拥有的权限
    public List<Permission> findPermission(String username) {
        return adminMapper.findAllPermission(username);
    }
}
