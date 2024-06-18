package com.dragon.flow.mapper.customer;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dragon.flow.model.customer.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author IRDC-Front
* @description 针对表【tbl_customer_activity】的数据库操作Mapper
* @createDate 2024-05-16 11:19:05
* @Entity com/dragon/flow.model/customers.Activity
*/
@Repository
public interface ActivityMapper extends BaseMapper<Activity> {

    IPage<Activity> getPagerModal(IPage<Activity> queryPage, Activity entity);
}




