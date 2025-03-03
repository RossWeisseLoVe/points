package com.dragon.flow.model.generate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dragon.tools.common.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@TableName(value = "tbl_gen_propertydefinition")
public class PropertyDefinition extends BaseModel implements Serializable {

    private static final long serialVersionUID = UUID.randomUUID().getMostSignificantBits();

    @TableId(type = IdType.AUTO)
    private Long id;

    private String classId;

    private String propertyName;

    private String propertyType;

    private String inputOrOutput;

    private String formItem;

    private String formItemName;

    private String displayBy;

    private Integer min;

    private Integer max;

    private Integer decimalPoint;

    private String placeholder;

    private String options;

}
