package com.dragon.flow.mapper.customer;

import com.dragon.flow.model.customer.Point;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author IRDC-Front
* @description 针对表【tbl_customer_point】的数据库操作Mapper
* @createDate 2024-06-17 14:52:20
* @Entity com.dragon.flow.model.customers.TblCustomerPoint
*/
@Repository
public interface PointMapper extends BaseMapper<Point> {

    boolean updatePoint(String cid, Integer point);

    boolean decreasePoint(String cid, Integer point);
}




