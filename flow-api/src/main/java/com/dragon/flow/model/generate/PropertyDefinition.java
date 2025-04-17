package com.dragon.flow.model.generate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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

    //用于在后期设置当前属性是否对外提供，仅在MongoDB中存储 1为向外提供 0为不向外提供
    @TableField(exist = false)
    private Integer isForeign;

    //用于记录用的是哪个region的
    private String regionId;

    //用于记录所属region的类型（box、agg\other）
    private String regionType;

}
