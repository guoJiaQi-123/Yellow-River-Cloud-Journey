package com.tyut.travel.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Popular {
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
    //产品简述
    private String introduction;
    //产品评分
    private float scoring;
    //排名
    private int ranking;
    //产品类型
    @TableField(exist = false)
    private Category category;
}
