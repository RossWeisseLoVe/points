package com.dragon.flow.web.resource.customer;

import com.dragon.flow.model.customer.Goods;
import com.dragon.flow.service.customer.GoodsService;
import com.dragon.flow.vo.customer.GoodsVo;
import com.dragon.flow.vo.pager.ParamVo;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.vo.ReturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flow/customer/goods")
public class GoodsResource {

    @Autowired
    private GoodsService goodsService;

    @PostMapping("page")
    public ReturnVo<PagerModel<Goods>> page(@RequestBody ParamVo<Goods> param){
        PagerModel<Goods> page = goodsService.getPage(param.getEntity(),param.getQuery());
        ReturnVo<PagerModel<Goods>> pagerModelReturnVo = new ReturnVo<>();
        pagerModelReturnVo.setData(page);
        pagerModelReturnVo.setMsg("查询成功");
        pagerModelReturnVo.setCode(ReturnCode.SUCCESS);
        return pagerModelReturnVo;
    }

    @PostMapping("add")
    public ReturnVo addGoods(@RequestBody Goods goods){
        goods.setStatus(0);
        goods.setTimedStatus(1);
        goodsService.save(goods);
        return new ReturnVo(ReturnCode.SUCCESS,"新增成功");
    }

    @PostMapping("update")
    public ReturnVo updateGoods(@RequestBody Goods goods){
        goodsService.updateById(goods);
        return new ReturnVo(ReturnCode.SUCCESS,"修改成功");
    }

    @GetMapping("setTimedStatus/{id}/{status}")
    public ReturnVo setTimedStatus(@PathVariable String id,@PathVariable Integer status){
        Goods goods = goodsService.getById(id);
        goods.setTimedStatus(status);
        goodsService.updateById(goods);
        return new ReturnVo(ReturnCode.SUCCESS,"修改成功");
        //TODO:定时发布的功能
    }


}
