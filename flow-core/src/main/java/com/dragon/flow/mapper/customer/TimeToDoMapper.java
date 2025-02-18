package com.dragon.flow.mapper.customer;

import com.dragon.flow.model.customer.TimeToDo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author IRDC-Front
* @description 针对表【tbl_customer_time_to_do】的数据库操作Mapper
* @createDate 2024-08-02 18:22:44
* @Entity com.dragon.flow.model.customer.CustomerTimeToDo
*/
@Repository
public interface TimeToDoMapper extends BaseMapper<TimeToDo> {

}




