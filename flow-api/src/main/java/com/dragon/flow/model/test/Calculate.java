package com.dragon.flow.model.test;

import lombok.Data;

import java.io.Serializable;

@Data
public class Calculate implements Serializable {

    private Double first;

    private Double second;

    private Double third;

    private Double average;

    private String errorMsg;

    private String className;



}
