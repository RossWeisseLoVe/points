package com.dragon.flow.vo.customer;

import com.dragon.flow.model.customer.Activity;
import lombok.Data;

import java.io.Serializable;

@Data
public class ActivityVo extends Activity implements Serializable {

    /**
     * 创建人名称
     */
    private String creatorName;

    /**
     * 编辑人名称
     */
    private String updatorName;

}
