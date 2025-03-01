package com.tyut.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyut.travel.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    //查询用户拥有的所有角色
    List<Integer> findRoleByAdmin(Integer aid);
    //根据id删除所有角色
    void deleteByAdminId(Integer aid);
    //添加用户角色
    void insertByAdminId(@Param("aid") Integer aid, @Param("rid") Integer rid);
}
