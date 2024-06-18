package com.dragon.flow.mapper.customer;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dragon.flow.model.customer.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author IRDC-Front
* @description 针对表【tbl_customer_goods】的数据库操作Mapper
* @createDate 2024-06-17 16:54:53
* @Entity com.dragon.flow.model.customers.TblCustomerGoods
*/
@Repository
public interface GoodsMapper extends BaseMapper<Goods> {

    IPage<Goods> getPagerModal(IPage<Goods> queryPage, Goods entity);
}




