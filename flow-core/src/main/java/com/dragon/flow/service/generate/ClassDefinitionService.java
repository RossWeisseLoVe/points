package com.dragon.flow.service.generate;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dragon.flow.model.generate.ClassDefinition;


public interface ClassDefinitionService extends IService<ClassDefinition> {

    public ClassDefinition getClassWithPropertiesByName(String name);

}
