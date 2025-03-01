package com.tyut.travel.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

// 旅游产品
@Data
public class Product {
    //id
    @TableId
    private Integer pid;
    //产品名称
    private String productName;
    //产品价格
    private Integer price;
    //热线电话
    private String hotline;
    //产品状态  false:关闭  true:开启
    private boolean status;
    //产品详情
    private String productDesc;
    // 产品图片
    private String pImage;
    // 产品类型id
    private Integer cid;
    //产品类型
    @TableField(exist = false)
    private Category category;
}
