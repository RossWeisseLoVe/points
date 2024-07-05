package com.dragon.flow.mapper.customer;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dragon.flow.model.customer.Client;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;

/**
* @author IRDC-Front
* @description 针对表【tbl_customer_client】的数据库操作Mapper
* @createDate 2024-05-20 12:25:48
* @Entity com.dragon.flow.model.customers.TblCustomerClient
*/
@Repository
public interface ClientMapper extends BaseMapper<Client> {

    IPage<Client> getPageModal(IPage<Client> clientPage, Client entity);

    List<Client> getUserPointTopTen();

    List<Client> getUserPointUsedTopTen(Integer status);
}




