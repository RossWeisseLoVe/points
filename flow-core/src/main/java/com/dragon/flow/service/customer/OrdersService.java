package com.dragon.flow.service.customer;

import com.dragon.flow.model.customer.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dragon.flow.vo.customer.HomeVo;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;

/**
* @author IRDC-Front
* @description 针对表【tbl_customer_orders】的数据库操作Service
* @createDate 2024-06-24 16:47:08
*/
public interface OrdersService extends IService<Orders> {

    PagerModel<Orders> getPagerModal(Orders entity, Query query);

    long sumTimes(String uid, String gid);

    HomeVo getHomeData();
}
