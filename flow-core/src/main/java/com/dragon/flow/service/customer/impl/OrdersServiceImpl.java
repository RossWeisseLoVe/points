package com.dragon.flow.service.customer.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dragon.flow.model.customer.Orders;
import com.dragon.flow.service.customer.OrdersService;
import com.dragon.flow.mapper.customer.OrdersMapper;
import org.springframework.stereotype.Service;

/**
* @author IRDC-Front
* @description 针对表【tbl_customer_orders】的数据库操作Service实现
* @createDate 2024-06-24 16:47:08
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService {



}




