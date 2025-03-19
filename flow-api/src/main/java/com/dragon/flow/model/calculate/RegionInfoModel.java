package com.dragon.flow.model.calculate;

import com.dragon.flow.model.generate.PropertyDefinition;
import lombok.Data;

import java.util.List;

@Data
public class RegionInfoModel {

    private String ClassName;

    private String description;

    private List<PropertyDefinition> properties;

}
