package com.dragon.flow.model.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dragon.tools.common.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tbl_customer_orders")
public class Orders extends BaseModel implements Serializable {
    private static final long serialVersionUID = UUID.randomUUID().getMostSignificantBits();

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 客户id
     */
    private String cid;
    /**
     * 订单类型
     */
    private Integer orderType;
    /**
     * 增加积分的活动类型
     */
    private String addType;
    /**
     * 扣除、消耗积分的操作类型
     */
    private String decreaseType;
    /**
     * 说明、若自由添加、直接扣减、清除积分则必填说明
     */
    private String instructions;
    /**
     * 订单是否取消
     */
    private Integer cancelFlag;
    /**
     * 单次订单内的物品数量
     */
    private Integer num;
    /**
     * 积分
     */
    private Integer point;
    /**
     * 排序方式
     */
    @TableField(exist = false)
    private Integer sortType;
}
