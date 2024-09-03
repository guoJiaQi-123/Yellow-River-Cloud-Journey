package com.tyut.travel.bean;

import lombok.Data;

@Data
public class PermissionWithStatus {
    private Integer pid;
    private String permissionName;// 权限名
    private String permissionDesc;//权限详情
    private boolean roleHas; //权限状态，用户是否有该权限
}
