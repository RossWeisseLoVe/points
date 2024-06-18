package com.dragon.flow.service.customer.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dragon.flow.model.customer.Activity;
import com.dragon.flow.service.customer.ActivityService;
import com.dragon.flow.mapper.customer.ActivityMapper;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
* @author IRDC-Front
* @description 针对表【tbl_customer_activity】的数据库操作Service实现
* @createDate 2024-05-16 11:19:05
*/
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity>
    implements ActivityService{

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public void deleteActivityByIds(String[] ids) {
        UpdateWrapper<Activity> activityUpdateWrapper = new UpdateWrapper<>();
        activityUpdateWrapper.in("id",ids);
        Activity activity = new Activity();
        activity.setDelFlag(0);
        activityMapper.update(activity,activityUpdateWrapper);
    }

    @Override
    public PagerModel<Activity> getPagerModal(Activity entity, Query query) {
        IPage<Activity> queryPage = new Page<>(query.getPageNum(), query.getPageSize());
        IPage<Activity> page = activityMapper.getPagerModal(queryPage,entity);
        List<Activity> list = page.getRecords();
        PagerModel<Activity> activityPagerModel = new PagerModel<>(page.getTotal(), list);
        return activityPagerModel;
    }
}




