package com.dragon.flow.vo.customer;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsTypeVo implements Serializable {

    private Integer point;

    private String id;

    private String cname;

    private String color;
}
