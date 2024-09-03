package com.tyut.travel.service;

import com.tyut.travel.pojo.Permission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PermissionServiceTest {
    @Autowired
    PermissionService permissionService;

    @Test
    void testFindPermissionByPid() {
        Permission permission = permissionService.findPermissionByPid(1);
        System.out.println(permission);
    }

}
