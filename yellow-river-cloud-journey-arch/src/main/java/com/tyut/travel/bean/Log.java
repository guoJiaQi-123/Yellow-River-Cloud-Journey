package com.tyut.travel.bean;

import lombok.Data;

import java.time.format.SignStyle;
import java.util.Date;

@Data
public class Log {
    private String url; // 访问的路径
    private Date visitTime; // 访问时间
    private int executionTime; // 访问时长
    private String username; // 访问者
    private String ip; // 访问ip
    private String exceptionMessage; // 异常信息
}
