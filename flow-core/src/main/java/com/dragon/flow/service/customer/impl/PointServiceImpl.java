package com.dragon.flow.service.customer.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dragon.flow.mapper.customer.OrdersMapper;
import com.dragon.flow.model.customer.Point;
import com.dragon.flow.service.customer.PointService;
import com.dragon.flow.mapper.customer.PointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author IRDC-Front
* @description 针对表【tbl_customer_point】的数据库操作Service实现
* @createDate 2024-06-17 14:52:20
*/
@Service
public class PointServiceImpl extends ServiceImpl<PointMapper, Point>
    implements PointService {

    @Autowired
    private PointMapper pointMapper;

    @Override
    public boolean updatePoint(String cid, Integer point) {
        return pointMapper.updatePoint(cid,point);
    }
}




