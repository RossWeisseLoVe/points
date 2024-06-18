package com.dragon.flow.service.customer;

import com.dragon.flow.model.customer.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;

/**
* @author IRDC-Front
* @description 针对表【tbl_customer_activity】的数据库操作Service
* @createDate 2024-05-16 11:19:05
*/
public interface ActivityService extends IService<Activity> {

    void deleteActivityByIds(String[] ids);

    PagerModel<Activity> getPagerModal(Activity entity, Query query);
}
