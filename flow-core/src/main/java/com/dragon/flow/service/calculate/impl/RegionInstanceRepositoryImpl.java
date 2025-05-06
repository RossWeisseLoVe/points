package com.dragon.flow.service.calculate.impl;

import com.dragon.flow.model.calculate.RegionInstanceModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionInstanceRepositoryImpl{

    private final MongoTemplate mongoTemplate;

    public List<RegionInstanceModel> findRegionInstanceModelsByCriteria(List<Example<RegionInstanceModel>> examples) {
        Criteria criteria = new Criteria();
        for (Example<RegionInstanceModel> example : examples) {
            criteria.orOperator(Criteria.byExample(example)); // OR 连接
        }
        Query query = new Query(criteria);
        return mongoTemplate.find(query, RegionInstanceModel.class);
    }


}
