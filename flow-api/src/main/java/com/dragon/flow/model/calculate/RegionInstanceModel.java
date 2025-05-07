package com.dragon.flow.model.calculate;

import com.dragon.flow.model.generate.PropertyDefinition;
import com.dragon.tools.common.BaseModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "regionInstance")
public class RegionInstanceModel extends BaseModel {

    //生成的实例中的每一个表单对象

    @Id
    private String id;

    private String instanceId;

    private String description;

    private List<PropertyDefinition> properties;

    //类型 普通box或者聚合器
    private String type;

    //这个regionId是region拖入后生成的独一无二的Id，而不是每一个regionModel的Id
    private String regionId;

    private String className;

    private org.bson.Document relationIn;

    private org.bson.Document ghostRelationIn;

    private org.bson.Document relationOut;

    private org.bson.Document data;

}
