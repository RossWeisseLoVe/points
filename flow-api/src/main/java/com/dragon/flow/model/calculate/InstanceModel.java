package com.dragon.flow.model.calculate;

import com.dragon.tools.common.BaseModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "instance")
public class InstanceModel extends BaseModel {

    @Id
    private String id;

    private String fid;

    private String modelId;

    private String name;

    private String description;

    private String type;

    //物化路径
    private String path;
    //树的层级
    private Integer level;


    @Transient
    private List<RegionInstanceModel> data;

}
