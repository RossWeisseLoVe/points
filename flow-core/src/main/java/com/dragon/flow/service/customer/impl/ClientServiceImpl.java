package com.dragon.flow.service.customer.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dragon.flow.model.customer.Client;
import com.dragon.flow.model.customer.Point;
import com.dragon.flow.service.customer.ClientService;
import com.dragon.flow.mapper.customer.ClientMapper;
import com.dragon.flow.service.customer.PointService;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import com.dragon.tools.vo.ReturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author IRDC-Front
* @description 针对表【tbl_customer_client】的数据库操作Service实现
* @createDate 2024-05-20 12:25:48
*/
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client>
    implements ClientService {

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private PointService pointService;

    @Override
    public List<Client> getUserPointTopTen() {
        return clientMapper.getUserPointTopTen();
    }

    @Override
    public List<Client> getUserPointUsedTopTen(Integer status) {
        return clientMapper.getUserPointUsedTopTen(status);
    }

    @Override
    public PagerModel<Client> getPageModal(Client entity, Query query) {
        IPage<Client> clientPage = new Page<>(query.getPageNum(),query.getPageSize());
        IPage<Client> page = clientMapper.getPageModal(clientPage,entity);
        List<Client> records = page.getRecords();
        PagerModel<Client> pagerModel = new PagerModel<>(page.getTotal(),records);
        return pagerModel;
    }

    @Override
    public void deleteByIds(String[] ids) {
        UpdateWrapper<Client> clientUpdateWrapper = new UpdateWrapper<>();
        clientUpdateWrapper.in("id",ids);
        Client client = new Client();
        client.setDelFlag(0);
        this.update(client,clientUpdateWrapper);
    }

    @Override
    @Transactional
    public boolean addClient(Client client) {
        boolean save = this.save(client);
        if(!save){
            return false;
        }
        Point point = new Point();
        point.setCid(client.getId());
        point.setVersion(1);
        point.setPoints(0);
        boolean save1 = pointService.save(point);
        if(save1){
            return true;
        }else{
            return false;
        }
    }
}




