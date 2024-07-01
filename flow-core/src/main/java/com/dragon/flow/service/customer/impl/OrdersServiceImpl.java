package com.dragon.flow.service.customer.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dragon.flow.model.customer.Orders;
import com.dragon.flow.service.customer.OrdersService;
import com.dragon.flow.mapper.customer.OrdersMapper;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author IRDC-Front
* @description 针对表【tbl_customer_orders】的数据库操作Service实现
* @createDate 2024-06-24 16:47:08
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService {


    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public PagerModel<Orders> getPagerModal(Orders entity, Query query) {
        IPage<Orders> ordersPage = new Page<>(query.getPageNum(), query.getPageSize());
        IPage<Orders> page = ordersMapper.getPagerModal(ordersPage,entity);
        PagerModel<Orders> ordersPagerModel = new PagerModel<>(page.getTotal(), page.getRecords());
        return ordersPagerModel;
    }

    @Override
    public long sumTimes(String uid, String gid) {
        return ordersMapper.sumTimes(uid,gid);
    }

}




