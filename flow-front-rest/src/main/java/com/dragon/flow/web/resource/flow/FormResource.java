package com.dragon.flow.web.resource.flow;

import com.alibaba.fastjson.JSONObject;
import com.dragon.flow.constant.FlowFrontConstant;
import com.dragon.flow.model.flowable.ModelInfo;
import com.dragon.flow.model.user.Account;
import com.dragon.flow.properties.StorageProperties;
import com.dragon.flow.service.storage.impl.LocalStorage;
import com.dragon.flow.service.storage.impl.StorageFactory;
import com.dragon.flow.vo.flowable.model.ModelInfoVo;
import com.dragon.flow.vo.flowable.runtime.StartProcessInstanceVo;
import com.dragon.flow.vo.pager.ParamVo;
import com.dragon.flow.vo.storage.FileStorage;
import com.dragon.flow.web.resource.BaseResource;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.common.UUIDGenerator;
import com.dragon.tools.vo.ReturnVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * @program: flow
 * @description:
 * @author: Bruce.Liu
 * @create: 2021-05-12 23:00
 **/
@Slf4j
@RestController
@RequestMapping("/front/flow")
public class FormResource extends BaseResource {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private FlowResource flowResource;
    @Autowired
    private StorageFactory storageFactory;

    @GetMapping(value = "/getFormInfoByModelKey/{modelKey}", produces = "application/json")
    public ReturnVo getFormInfoByModelKey(HttpServletRequest request, @PathVariable String modelKey) {
        HttpHeaders headers = this.createHttpHeaders(request);
        HttpEntity httpEntity = new HttpEntity<>(headers);
        String url = this.getApiUrl(FlowFrontConstant.GET_FORMINFO_BYMODELKEY_URL) + "?modelKey=" + modelKey;
        ReturnVo returnVo = restTemplate.postForObject(url, httpEntity, ReturnVo.class, modelKey);
        return returnVo;
    }

    @GetMapping(value = "/getFormDataInfoByProcessInstanceId/{procInstId}", produces = "application/json")
    public ReturnVo getFormDataInfoByProcessInstanceId(HttpServletRequest request, @PathVariable String procInstId) {
        HttpHeaders headers = this.createHttpHeaders(request);
        HttpEntity httpEntity = new HttpEntity<>(headers);
        String url = this.getApiUrl(FlowFrontConstant.GET_GETFORMDATAINFOBYPROCESSINSTANCEID_URL) + "?procInstId=" + procInstId;
        ReturnVo returnVo = restTemplate.postForObject(url, httpEntity, ReturnVo.class, procInstId);
        return returnVo;
    }

    @PostMapping(value = "/startFormFlow", produces = "application/json")
    public ReturnVo startFormFlow(HttpServletRequest request, @RequestBody StartProcessInstanceVo startProcessInstanceVo) {
        Account loginAccount = this.getLoginAccount(request);
        startProcessInstanceVo.setCurrentUserCode(loginAccount.getCode());
        startProcessInstanceVo.setCreator(loginAccount.getCode());

        ReturnVo modelInfoReturnVo = flowResource.getModelInfoByModelKey(request, startProcessInstanceVo.getProcessDefinitionKey());
        if (modelInfoReturnVo.isSuccess()) {
            ModelInfo modelInfo = JSONObject.parseObject(JSONObject.toJSON(modelInfoReturnVo.getData()).toString(), ModelInfo.class);

            startProcessInstanceVo.setFormName(loginAccount.getName() + "发起的" + modelInfo.getName());
            startProcessInstanceVo.setAppSn(modelInfo.getAppSn());
        }
        startProcessInstanceVo.setBusinessKey(UUIDGenerator.generate());
        String url = this.getApiUrl(FlowFrontConstant.STARTFORMFLOW_URL);
        HttpHeaders headers = this.createHttpHeaders(request);
        HttpEntity<StartProcessInstanceVo> httpEntity = new HttpEntity<>(startProcessInstanceVo, headers);
        ReturnVo returnVo = restTemplate.postForObject(url, httpEntity, ReturnVo.class);
        return returnVo;
    }

    /**
     * 上传文件
     * @param file 文件流
     * @return
     */
    @PostMapping("/uploadFile")
    public ReturnVo<String> uploadFile(MultipartFile file) {
        ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.FAIL, "附件上传失败");
        try {
            if (file != null) {
                InputStream inputStream = file.getInputStream();
                FileStorage fileStorage = storageFactory.store(inputStream, file.getSize(), file.getContentType(), null, file.getOriginalFilename());
                if (fileStorage != null) {
                    returnVo.setCode(ReturnCode.SUCCESS);
                    returnVo.setData(fileStorage.getUrl());
                    returnVo.setMsg("上传文件成功！");
                } else {
                    returnVo.setMsg("上传文件失败！");
                }
            }
        } catch (Exception e) {
            log.error("uploadFile:上传自定义表单附件异常！", e);
            returnVo.setMsg("上传附件异常！");
            e.printStackTrace();
        }
        return returnVo;
    }
}
