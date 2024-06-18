package com.dragon.flow.service.customer;

import com.dragon.flow.model.customer.Client;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;

/**
* @author IRDC-Front
* @description 针对表【tbl_customer_client】的数据库操作Service
* @createDate 2024-05-20 12:25:48
*/
public interface ClientService extends IService<Client> {

    boolean addClient(Client client);

    PagerModel<Client> getPageModal(Client entity, Query query);

    void deleteByIds(String[] ids);
}
