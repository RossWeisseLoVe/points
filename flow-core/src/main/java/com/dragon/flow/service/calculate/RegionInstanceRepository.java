package com.dragon.flow.service.calculate;

import com.dragon.flow.model.calculate.RegionInstanceModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegionInstanceRepository extends MongoRepository<RegionInstanceModel,String> {

}
