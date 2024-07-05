package com.dragon.flow.web.resource.customer;

import com.dragon.flow.model.customer.Client;
import com.dragon.flow.service.customer.ClientService;
import com.dragon.flow.vo.pager.ParamVo;
import com.dragon.flow.web.resource.BaseResource;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.vo.ReturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flow/customer/client")
public class ClientResource extends BaseResource<Client> {

    @Autowired
    private ClientService clientService;

    @PostMapping(value = "/page")
    public ReturnVo<PagerModel<Client>> page(@RequestBody ParamVo<Client> param){
        PagerModel<Client> pagerModel = clientService.getPageModal(param.getEntity(),param.getQuery());
        ReturnVo<PagerModel<Client>> pagerModelReturnVo = new ReturnVo<>(ReturnCode.SUCCESS,"OK");
        pagerModelReturnVo.setData(pagerModel);
        return pagerModelReturnVo;
    }

    @PostMapping(value = "/add")
    public ReturnVo addClient(@RequestBody Client client){
        boolean save = clientService.addClient(client);
        if(save){
            return new ReturnVo<>(ReturnCode.SUCCESS,"新增成功");
        }else{
            return new ReturnVo<>(ReturnCode.FAIL,"新增失败");
        }
    }

    @PostMapping(value = "/deleteByIds")
    public ReturnVo deleteByIds(@RequestBody String[] ids){
        clientService.deleteByIds(ids);
        return new ReturnVo<>(ReturnCode.SUCCESS,"删除成功");
    }

    @PostMapping(value = "/update")
    public ReturnVo updateClient(@RequestBody Client client){
        boolean b = clientService.updateById(client);
        if(b){
            return new ReturnVo<>(ReturnCode.SUCCESS,"更新成功");
        }else{
            return new ReturnVo<>(ReturnCode.FAIL,"更新失败");
        }
    }

    @GetMapping("getUserPointTopTen")
    public ReturnVo<List<Client>> getUserPointTopTen(){
        List<Client> list = clientService.getUserPointTopTen();
        return new ReturnVo<>(ReturnCode.SUCCESS,"查询成功",list);
    }

    @GetMapping("getUserPointUsedTopTen/{status}")
    public ReturnVo<List<Client>> getUserPointUsedTopTen(@PathVariable Integer status){
        List<Client> list = clientService.getUserPointUsedTopTen(status);
        return new ReturnVo<>(ReturnCode.SUCCESS,"查询成功",list);
    }

}
