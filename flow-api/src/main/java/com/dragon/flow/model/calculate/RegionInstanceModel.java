package com.dragon.flow.model.calculate;

import com.dragon.tools.common.BaseModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "regionInstance")
public class RegionInstanceModel extends BaseModel {

    @Id
    private String id;

    private String instanceId;

    private String regionId;

    private String ClassName;

    private String relationIn;

    private String relationOut;

    private org.bson.Document data;

}
