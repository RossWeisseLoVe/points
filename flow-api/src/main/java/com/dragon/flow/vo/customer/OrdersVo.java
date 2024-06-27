package com.dragon.flow.vo.customer;

import com.dragon.flow.model.customer.Orders;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrdersVo extends Orders implements Serializable {

    private String activityName;

    private String goodsName;

    private String activityType;

    private String goodsType;

    private String name;

    private String creatorName;
}
