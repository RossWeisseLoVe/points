package com.dragon.flow.service.customer.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dragon.flow.mapper.customer.GoodsMapper;
import com.dragon.flow.model.customer.Orders;
import com.dragon.flow.service.customer.OrdersService;
import com.dragon.flow.mapper.customer.OrdersMapper;
import com.dragon.flow.vo.customer.GoodsTypeVo;
import com.dragon.flow.vo.customer.GoodsVo;
import com.dragon.flow.vo.customer.HomeVo;
import com.dragon.flow.vo.customer.PointVo;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
    public HomeVo getHomeData() {
        HomeVo homeVo = new HomeVo();
        List<PointVo> pointVo =  ordersMapper.getTotalData();
        pointVo.forEach((item)->{
            if(item.getOrderType().equals(1)){
                homeVo.setTotalPointOut(item.getPoint());
            }else{
                homeVo.setTotalPointIn(item.getPoint());
            }
        });
        LocalDate currentDate = LocalDate.now();
        YearMonth currentYearMonth = YearMonth.from(currentDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String formattedYearMonth = currentYearMonth.format(formatter);
        List<PointVo> pointVoMonth = ordersMapper.getMonthData(formattedYearMonth);
        pointVoMonth.forEach((item)->{
            if(item.getOrderType().equals(1)){
                homeVo.setMonthPointOut(item.getPoint());
            }else{
                homeVo.setMonthPointIn(item.getPoint());
            }
        });
        return homeVo;
    }

    @Override
    public long sumTimes(String uid, String gid) {
        long l = ordersMapper.sumTimes(uid, gid);
        return l;
    }

    @Override
    public List<GoodsTypeVo> getExchangeAnalysis() {
        return ordersMapper.getExchangeAnalysis();
    }
}




