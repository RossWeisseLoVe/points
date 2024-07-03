package com.dragon.flow.web.resource.customer;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dragon.flow.model.customer.Activity;
import com.dragon.flow.model.customer.Goods;
import com.dragon.flow.service.customer.ActivityService;
import com.dragon.flow.vo.pager.ParamVo;
import com.dragon.flow.web.resource.BaseResource;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.vo.ReturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flow/customer/activity")
public class ActivityResource extends BaseResource<Activity> {

    @Autowired
    private ActivityService activityService;

    /**
     * 查询活动列表
     *
     * @return
     */
    @PostMapping("/page")
    public ReturnVo<PagerModel<Activity>> page(@RequestBody ParamVo<Activity> param){
        PagerModel<Activity> page = activityService.getPagerModal(param.getEntity(),param.getQuery());
        ReturnVo<PagerModel<Activity>> returnVo = new ReturnVo<>(ReturnCode.SUCCESS,"OK");
        returnVo.setData(page);
        return returnVo;
    }

    /**
     * 增加活动
     *
     * @return
     */
    @PostMapping("/addActivity")
    public ReturnVo addActivity(@RequestBody Activity activity){
        activity.setStatus(0);
        activity.setTimedStatus(1);
        activityService.save(activity);
        ReturnVo<Page<Activity>> returnVo = new ReturnVo<>(ReturnCode.SUCCESS,"新增成功");
        return returnVo;
    }

    /**
     * 编辑活动
     *
     * @return
     */
    @PostMapping("/updateActivity")
    public ReturnVo updateActivity(@RequestBody Activity activity){
        activityService.updateById(activity);
        ReturnVo<Page<Activity>> returnVo = new ReturnVo<>(ReturnCode.SUCCESS,"编辑成功");
        return returnVo;
    }

    /**
     * 删除活动
     *
     * @return
     */
    @PostMapping("/deleteActivityByIds")
    public ReturnVo deleteActivity(@RequestBody String[] ids){
        activityService.deleteActivityByIds(ids);
        ReturnVo<Page<Activity>> returnVo = new ReturnVo<>(ReturnCode.SUCCESS,"删除成功");
        return returnVo;
    }

    @GetMapping("setTimedStatus/{id}/{status}")
    public ReturnVo activityService(@PathVariable String id,@PathVariable Integer status){
        Activity activity = activityService.getById(id);
        activity.setTimedStatus(status);
        activityService.updateById(activity);
        return new ReturnVo(ReturnCode.SUCCESS,"修改成功");
        //TODO:定时发布的功能
    }

}
