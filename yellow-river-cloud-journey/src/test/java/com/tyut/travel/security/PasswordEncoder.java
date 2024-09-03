package com.tyut.travel.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class PasswordEncoder {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void PasswordHandler() {
        String encode = passwordEncoder.encode("baizhan");
        System.out.println(encode);
        boolean ikunkun = passwordEncoder.matches("ikunkun", "$2a$10$yQzn.FZcwT2GLFB5INk6bO3Log5BgyL5eQuLC2Qy1pn3ToX5R6Exa");
        System.out.println(ikunkun);
    }
}
