package com.dragon.flow.vo.calculate;

import com.dragon.flow.model.customer.Activity;
import lombok.Data;


@Data
public class CalculateParamVo {

    //接收region数据
    private Object param;

    //模板计算域
    private String regionId;

    //模板类名
    private String typeName;

    //所属于哪个计算空间
    private String instanceId;

    //所属于哪个计算模型
    private String modelId;

    //实例id
    private String regionInstanceId;

}
