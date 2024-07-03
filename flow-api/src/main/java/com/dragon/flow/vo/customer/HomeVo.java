package com.dragon.flow.vo.customer;

import lombok.Data;

import java.io.Serializable;

@Data
public class HomeVo implements Serializable {

    private Integer monthPointOut;

    private Integer monthPointIn;

    private Integer totalPointOut;

    private Integer totalPointIn;
}
