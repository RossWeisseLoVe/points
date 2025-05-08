package com.dragon.flow.service.calculate.impl;

import com.dragon.flow.model.calculate.RegionInstanceModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionInstanceRepositoryImpl{

    private final MongoTemplate mongoTemplate;

    public List<RegionInstanceModel> findRegionInstanceModelsByCriteria(
            List<Example<RegionInstanceModel>> examples) {

        if (examples.isEmpty()) {
            return Collections.emptyList(); // 更简洁的空列表返回方式
        }

        // 收集所有 Example 转换后的 Criteria
        List<Criteria> criteriaList = examples.stream()
                .map(Criteria::byExample)
                .collect(Collectors.toList());

        // 一次性构建 OR 条件
        Criteria criteria = new Criteria().orOperator(
                criteriaList.toArray(new Criteria[0])
        );

        return mongoTemplate.find(new Query(criteria), RegionInstanceModel.class);
    }


}
