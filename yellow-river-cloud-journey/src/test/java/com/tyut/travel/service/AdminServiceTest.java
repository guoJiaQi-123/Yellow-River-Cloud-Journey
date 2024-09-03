package com.tyut.travel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.travel.bean.RoleWithStatus;
import com.tyut.travel.pojo.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AdminServiceTest {
    @Autowired
    private AdminService adminService;

    @Test
    void testFindPage(){
        Page<Admin> page = adminService.findPage(1, 2);
        System.out.println(page);
    }

    @Test
    void testFindRole(){
        List<RoleWithStatus> role = adminService.findRole(2);
        System.out.println(role);
    }

    @Test
    void delete(){
        adminService.delete(26);
    }

}
