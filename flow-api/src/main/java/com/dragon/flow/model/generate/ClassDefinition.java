package com.dragon.flow.model.generate;

import lombok.Data;

import java.util.List;

@Data
public class ClassDefinition {

    private String className;

    private List<PropertyDefinition> properties;

}
