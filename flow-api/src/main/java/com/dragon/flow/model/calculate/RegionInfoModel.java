package com.dragon.flow.model.calculate;

import com.dragon.flow.model.generate.PropertyDefinition;
import lombok.Data;

import java.util.List;

@Data
public class RegionInfoModel {

    //是聚合器还是普通表单等
    private String type;

    private String ClassName;

    private String description;

    private List<PropertyDefinition> properties;

    //如果type为otherModel
    private String sourceModelId;

}
