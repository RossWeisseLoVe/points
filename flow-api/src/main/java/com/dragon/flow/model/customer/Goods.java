package com.dragon.flow.model.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dragon.tools.common.BaseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tbl_customer_goods")
public class Goods extends BaseModel implements Serializable {
    private static final long serialVersionUID = UUID.randomUUID().getMostSignificantBits();
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 货物名称
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
     * 库存数量
     */
    private Integer inventory;
    /**
     * 单次最少兑换数量
     */
    private Integer min;
    /**
     * 单次最大兑换数量
     */
    private Integer max;
    /**
     * 每人最大兑换数量
     */
    private Integer maxPerHead;
    /**
     * 类型
     */
    private String type;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 定时上架状态
     */
    private Integer timedStatus;
    /**
     * 上架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    /**
     * 下架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
    private Date endTime;
    /**
     * 是否只查询上架状态的
     */
    @TableField(exist = false)
    private boolean onlyOnsale;
}
