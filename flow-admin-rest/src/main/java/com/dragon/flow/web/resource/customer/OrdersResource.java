package com.dragon.flow.web.resource.customer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dragon.flow.model.customer.Activity;
import com.dragon.flow.model.customer.Goods;
import com.dragon.flow.model.customer.Orders;
import com.dragon.flow.model.customer.Point;
import com.dragon.flow.service.customer.ActivityService;
import com.dragon.flow.service.customer.GoodsService;
import com.dragon.flow.service.customer.OrdersService;
import com.dragon.flow.service.customer.PointService;
import com.dragon.flow.vo.pager.ParamVo;
import com.dragon.flow.web.resource.BaseResource;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.vo.ReturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flow/customer/orders")
public class OrdersResource extends BaseResource<Orders> {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private PointService pointService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 参与活动增加积分
     * @return
     */
    @PostMapping("save")
    @Transactional
    public ReturnVo save(@RequestBody Orders orders){
        orders.setOrderType(1);
        orders.setCancelFlag(0);
        orders.setNum(1);
        LambdaQueryWrapper<Activity> activityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        activityLambdaQueryWrapper.eq(Activity::getId,orders.getAddType());
        Activity one = activityService.getOne(activityLambdaQueryWrapper);
        Integer point = one.getPoint();
        orders.setPoint(point);
        one.setInventory(one.getInventory()-1);
        activityService.updateById(one);
        ordersService.save(orders);
        pointService.updatePoint(orders.getCid(),point);
        return new ReturnVo(ReturnCode.SUCCESS,"保存成功");
    }

    /**
     * 兑换物品减少积分
     * @return
     */
    @PostMapping("exchange")
    @Transactional
    public ReturnVo exchange(@RequestBody Orders orders){
        orders.setOrderType(2);
        orders.setCancelFlag(0);
        LambdaQueryWrapper<Goods> goodsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        goodsLambdaQueryWrapper.eq(Goods::getId,orders.getDecreaseType());
        Goods one = goodsService.getOne(goodsLambdaQueryWrapper);
        Integer point =  one.getPoint()*orders.getNum();
        LambdaQueryWrapper<Point> pointLambdaQueryWrapper = new LambdaQueryWrapper<Point>();
        pointLambdaQueryWrapper.eq(Point::getCid,orders.getCid());
        Point one1 = pointService.getOne(pointLambdaQueryWrapper);
        if(point > one1.getPoints()){
            return new ReturnVo(ReturnCode.FAIL,"积分余额不足");
        }
        orders.setPoint(point);
        ordersService.save(orders);
        one.setInventory(one.getInventory()- orders.getNum());
        goodsService.updateById(one);
        pointService.decreasePoint(orders.getCid(),point);
        return new ReturnVo(ReturnCode.SUCCESS,"保存成功");

    }

    /**
     * 查询该用户已经参与几次该活动
     * @return
     */
    @GetMapping("getTimesRemain/{uid}/{aid}")
    public ReturnVo<Long> getTimesRemain(@PathVariable String uid,@PathVariable String aid){
        LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ordersLambdaQueryWrapper.eq(Orders::getCid,uid).eq(Orders::getAddType,aid);
        long count = ordersService.count(ordersLambdaQueryWrapper);
        return new ReturnVo<Long>(ReturnCode.SUCCESS,"查询成功",count);
    }

    /**
     * 查询该用户已经兑换几次该奖品
     * @return
     */
    @GetMapping("getRewardsTimesRemain/{uid}/{gid}")
    public ReturnVo<Long> getRewardsTimesRemain(@PathVariable String uid,@PathVariable String gid){
        long count = ordersService.sumTimes(uid,gid);
        return new ReturnVo<Long>(ReturnCode.SUCCESS,"查询成功",count);
    }

    /**
     * 查询历史记录
     * @return
     */
    @PostMapping("page")
    public ReturnVo<PagerModel<Orders>> getHistoryPage(@RequestBody ParamVo<Orders> param){
        PagerModel<Orders> page = ordersService.getPagerModal(param.getEntity(),param.getQuery());
        ReturnVo<PagerModel<Orders>> pagerModelReturnVo = new ReturnVo<>(ReturnCode.SUCCESS,"查询成功",page);
        return pagerModelReturnVo;
    }

}
