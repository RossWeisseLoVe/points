package com.dragon.flow.model.customer;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.dragon.tools.common.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tbl_customer_point")
public class Point extends BaseModel implements Serializable {
    private static final long serialVersionUID = UUID.randomUUID().getMostSignificantBits();
    /**
     * 主键
     */
    private String id;
    /**
     * 客户id
     */
    private String cid;
    /**
     * 积分
     */
    private Integer points;
    /**
     * 备注
     */
    private String remark;
    /**
     * 版本
     */
    @Version
    private Integer version;
}
