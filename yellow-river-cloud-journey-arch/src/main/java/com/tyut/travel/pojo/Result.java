package com.tyut.travel.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private boolean flag;   //返回注册是否成功
    private String message;//返回失败信息
    private Object data;    //返回数据

    public Result(boolean flag,String message){
        this(flag,message,null);
    }
}
