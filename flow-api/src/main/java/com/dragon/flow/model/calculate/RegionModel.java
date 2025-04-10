package com.dragon.flow.model.calculate;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
}
