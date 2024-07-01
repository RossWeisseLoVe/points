package com.dragon.flow.mapper.customer;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dragon.flow.model.customer.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author IRDC-Front
* @description 针对表【tbl_customer_orders】的数据库操作Mapper
* @createDate 2024-06-24 16:47:08
* @Entity com.dragon.flow.model.customers.TblCustomerOrders
*/
@Repository
public interface OrdersMapper extends BaseMapper<Orders> {

    IPage<Orders> getPagerModal(IPage<Orders> ordersPage, Orders entity);

    long sumTimes(String uid, String gid);
}




