package com.dragon.flow.model.generate;

import lombok.Data;

@Data
public class PropertyDefinition {

    private String propertyName;

    private String propertyType;

    private String inputOrOutput;

    private String formItem;

    private String displayBy;

    private String min;

    private String max;

    private String decimalPoint;

}
