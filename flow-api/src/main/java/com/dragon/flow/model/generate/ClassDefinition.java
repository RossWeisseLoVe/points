package com.dragon.flow.model.generate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dragon.tools.common.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@TableName(value = "tbl_gen_classdefinition")
public class ClassDefinition extends BaseModel implements Serializable {
    private static final long serialVersionUID = UUID.randomUUID().getMostSignificantBits();

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String className;

    private String description;

    private String sourceCode;

    @TableField(exist = false)
    private List<PropertyDefinition> properties;

}
