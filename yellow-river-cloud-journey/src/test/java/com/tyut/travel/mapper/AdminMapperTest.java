package com.tyut.travel.mapper;

import com.tyut.travel.pojo.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminMapperTest {
    @Autowired
    private AdminMapper adminMapper;

    @Test
    void testFindDesc(){
        Admin desc = adminMapper.findDesc(1);
        System.out.println(desc);
    }
}
