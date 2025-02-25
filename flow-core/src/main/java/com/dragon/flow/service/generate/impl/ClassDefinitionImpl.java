package com.dragon.flow.service.generate.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.dragon.flow.mapper.generate.ClassDefinitionMapper;
import com.dragon.flow.mapper.generate.PropertyDefinitionMapper;
import com.dragon.flow.model.generate.ClassDefinition;
import com.dragon.flow.model.generate.PropertyDefinition;
import com.dragon.flow.service.generate.ClassDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassDefinitionImpl extends ServiceImpl<ClassDefinitionMapper, ClassDefinition>
        implements ClassDefinitionService {

    @Autowired
    private ClassDefinitionMapper classDefinitionMapper;

    @Autowired
    private PropertyDefinitionMapper propertyDefinitionMapper;

    public ClassDefinition getClassWithPropertiesByName(String className) {
        // 1. 查询 ClassDefinition
        QueryWrapper<ClassDefinition> classQuery = new QueryWrapper<>();
        classQuery.eq("class_name", className); // 确保数据库列名为 class_name
        ClassDefinition classDef = classDefinitionMapper.selectOne(classQuery);

        if (classDef == null) {
            return null; // 或抛出异常
        }

        // 2. 查询关联的 PropertyDefinition
        QueryWrapper<PropertyDefinition> propQuery = new QueryWrapper<>();
        propQuery.eq("class_id", classDef.getId());
        List<PropertyDefinition> properties = propertyDefinitionMapper.selectList(propQuery);

        // 3. 组合数据
        classDef.setProperties(properties);
        return classDef;
    }
}
