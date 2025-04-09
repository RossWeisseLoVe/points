package com.dragon.flow.vo.calculate;

import com.dragon.flow.model.customer.Activity;
import lombok.Data;


@Data
public class CalculateParamVo {

    //接收region数据
    private Object param;

    //模板中的计算域Id
    private String regionId;

    //来源计算域Id
    private String sourceId;

    //模板类名
    private String typeName;

    //实例Id
    private String instanceId;

    //模型Id
    private String modelId;

    //计算域实例id
    private String regionInstanceId;


}
