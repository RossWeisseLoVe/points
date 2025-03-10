package com.dragon.flow.service.calculate;

import com.dragon.flow.model.calculate.CalculateModel;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ModelsRepository extends MongoRepository<CalculateModel,String> {

    @Override
    CalculateModel insert(CalculateModel entity);

}
