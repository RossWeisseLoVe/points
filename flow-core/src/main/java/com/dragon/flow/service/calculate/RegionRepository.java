package com.dragon.flow.service.calculate;

import com.dragon.flow.model.calculate.RegionModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RegionRepository extends MongoRepository<RegionModel,String> {

    @Override
    <S extends RegionModel> List<S> saveAll(Iterable<S> entities);

    List<RegionModel> findByModelId(String modelId);

    void deleteAllByModelId(String modelId);

    @Override
    List<RegionModel> findAllById(Iterable<String> strings);
}
