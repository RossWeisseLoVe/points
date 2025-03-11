package com.dragon.flow.service.calculate;

import com.dragon.flow.model.calculate.CalculateModel;
import com.dragon.flow.model.calculate.InstanceModel;
import com.dragon.tools.vo.ReturnVo;

public interface CalculateService {

    public Object getCalculateInstance(Object param,String typeName) throws Exception;

    public CalculateModel getModelById(String id);

    public InstanceModel newInstance(InstanceModel instanceModel) throws Exception;
}
