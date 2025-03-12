package com.dragon.flow.vo.calculate;

import com.dragon.flow.model.customer.Activity;
import lombok.Data;


@Data
public class CalculateParamVo {

    //接收region数据
    private Object param;

    private String regionId;

    private String typeName;

    private String instanceId;

    private String modelId;

}
