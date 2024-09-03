package com.tyut.travel.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tyut.travel.pojo.Permission;
import lombok.Data;

import java.util.List;

@Data
public class RoleWithStatus {
    private Integer rid;
    private String roleName;// 角色名
    private String roleDesc;// 角色介绍
    private boolean adminHas;//用户是否拥有该角色
}
