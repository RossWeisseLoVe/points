package com.dragon.flow.service.customer.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dragon.flow.model.customer.Activity;
import com.dragon.flow.model.customer.Goods;
import com.dragon.flow.service.customer.GoodsService;
import com.dragon.flow.mapper.customer.GoodsMapper;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author IRDC-Front
* @description 针对表【tbl_customer_goods】的数据库操作Service实现
* @createDate 2024-06-17 16:54:53
*/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
    implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public PagerModel<Goods> getPage(Goods entity, Query query) {
        IPage<Goods> queryPage = new Page<>(query.getPageNum(), query.getPageSize());
        IPage<Goods> page = goodsMapper.getPagerModal(queryPage,entity);
        List<Goods> list = page.getRecords();
        PagerModel<Goods> goodsPagerModel = new PagerModel<>(page.getTotal(), list);
        return goodsPagerModel;
    }
}




