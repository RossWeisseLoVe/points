package com.dragon.flow.model.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dragon.tools.common.BaseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tbl_customer_activity")
public class Activity extends BaseModel implements Serializable {
    private static final long serialVersionUID = UUID.randomUUID().getMostSignificantBits();
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 积分
     */
    private Integer point;
    /**
     * 备注
     */
    private String remark;
    /**
     * 类型
     */
    private String type;
    /**
     * 参与次数上线
     */
    private Integer max;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 定时上架状态
     */
    private Integer timedStatus;
    /**
     * 活动名额
     */
    private Integer inventory;
    /**
     * 活动开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 活动结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 是否只查询上架状态的
     */
    @TableField(exist = false)
    private boolean onlyOnsale;
}
