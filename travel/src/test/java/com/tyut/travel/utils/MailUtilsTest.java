package com.tyut.travel.utils;

import com.tyut.travel.util.MailUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailUtilsTest {

    private MailUtils mailUtils;

    @Autowired
    public void setMailUtils(MailUtils mailUtils) {
        this.mailUtils = mailUtils;
    }

    @Test
    void testSendMail(){
        mailUtils.sendMail("2667629684@qq.com","这是一封测试邮件","测试");
    }
}
