package com.dragon.flow.service.calculate;

import com.dragon.flow.model.calculate.CalculateModel;
import com.dragon.flow.model.calculate.InstanceModel;
import com.dragon.flow.vo.calculate.CalculateParamVo;
import com.dragon.tools.vo.ReturnVo;
import org.bson.Document;

public interface CalculateService {

    public ReturnVo<Object> getCalculateInstance(CalculateParamVo param,boolean isFromWeb) throws Exception;

    public CalculateModel getModelById(String id);

    public InstanceModel newInstance(InstanceModel instanceModel, Boolean isOtherModel, Document relation,Document relationInForInstance,String fatherInstanceId) throws Exception;

    public void callSetterMethod(Object obj, String propertyName,Object fieldValue);

}
