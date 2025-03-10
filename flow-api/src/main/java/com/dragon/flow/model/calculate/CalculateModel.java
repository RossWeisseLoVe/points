package com.dragon.flow.model.calculate;

import com.dragon.tools.common.BaseModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "models")
public class CalculateModel extends BaseModel {

    @Id
    private String id;

    // 模型名称
    private String name;

    // 模型
    @Transient
    private List<RegionModel> template;

    // 描述
    private String description;

    public CalculateModel(){

    }
}
