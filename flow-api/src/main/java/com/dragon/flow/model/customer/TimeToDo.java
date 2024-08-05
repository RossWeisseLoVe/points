package com.dragon.flow.model.customer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.dragon.tools.common.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @TableName tbl_customer_time_to_do
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value ="tbl_customer_time_to_do")
public class TimeToDo extends BaseModel implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = UUID.randomUUID().getMostSignificantBits();
    /**
     * 
     */
    @TableId
    private String id;

    /**
     * 0：活动   
1：奖品
     */
    private Integer type;

    /**
     * 外键 foreignKey
     */
    private String fid;

    /**
     * 
     */
    private String creator;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private String updator;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private Integer delFlag;



}