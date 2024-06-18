package com.dragon.flow.vo.customer;

import com.dragon.flow.model.customer.Goods;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsVo extends Goods implements Serializable {

    private String creatorName;

    private String updatorName;
}
