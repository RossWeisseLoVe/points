package com.dragon.flow.model.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dragon.tools.common.BaseModel;
import lombok.Data;
import java.util.UUID;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tbl_customer_client")
public class Client extends BaseModel implements Serializable {
    private static final long serialVersionUID = UUID.randomUUID().getMostSignificantBits();
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 客户名称
     */
    private String name;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 地址
     */
    private String address;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 公司
     */
    private String company;
    /**
     * 行业
     */
    private String industry;
    /**
     * 客户联系人
     */
    private String representative;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 类型
     */
    private String type;
}
