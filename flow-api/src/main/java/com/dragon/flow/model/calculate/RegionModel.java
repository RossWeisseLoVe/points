package com.dragon.flow.model.calculate;

import com.dragon.flow.model.generate.PropertyDefinition;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "region")
public class RegionModel {

    @Id
    private String id;

    private String modelId;

    private RegionInfoModel info;

    private org.bson.Document relationOut;

    private org.bson.Document relationIn;

    private String type;

    //是否为即时聚合  1为是，0为否
    private Integer isAnytime;

}
