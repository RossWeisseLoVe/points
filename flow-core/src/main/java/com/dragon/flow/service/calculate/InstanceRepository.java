package com.dragon.flow.service.calculate;

import com.dragon.flow.model.calculate.InstanceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InstanceRepository extends MongoRepository<InstanceModel,String> {

    Page<InstanceModel> findByFidIsNull(Pageable pageable);

}
