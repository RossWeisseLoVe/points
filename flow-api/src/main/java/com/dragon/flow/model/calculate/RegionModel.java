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

    private String relationOut;

    private String relationIn;

    private String type;
}
