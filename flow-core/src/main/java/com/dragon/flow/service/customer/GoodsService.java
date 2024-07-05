package com.dragon.flow.service.customer;

import com.dragon.flow.model.customer.Activity;
import com.dragon.flow.model.customer.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dragon.flow.vo.customer.GoodsVo;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;

import java.util.List;

/**
* @author IRDC-Front
* @description 针对表【tbl_customer_goods】的数据库操作Service
* @createDate 2024-06-17 16:54:53
*/
public interface GoodsService extends IService<Goods> {

    PagerModel<Goods> getPage(Goods entity, Query query);


}
