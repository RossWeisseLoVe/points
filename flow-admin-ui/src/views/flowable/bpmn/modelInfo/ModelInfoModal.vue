<template>
  <BasicModal wrapClassName="form-flow-designer" v-bind="$attrs" @register="registerModal" >
    <template #title>
      <Row>
        <Col span="8">
          <span v-if="!formBaseInfo.modelName">新建流程</span>
          <Tooltip v-else placement="leftBottom">
            <template #title>
              {{ formBaseInfo.modelName }}
            </template>
            编辑流程
            -
            <TypographyText @click="doCopyContent(formBaseInfo.modelName)">{{ formBaseInfo.modelName }}</TypographyText>
            -
            <TypographyText type="secondary" @click="doCopyContent(formBaseInfo.modelKey)">{{formBaseInfo.modelKey}}</TypographyText>
          </Tooltip>
        </Col>
        <Col span="8">
          <Steps
            class="designer-steps"
            @change="handleStepChange"
            v-model:current="currentStepValue"
            size="small"
            type="navigation"
            :style="{ marginBottom: '0px', paddingTop: '0' }"
          >
            <Step :disabled="stepsDisabled.formDesigner" >
              <template #title >
                表单设计
              </template>
            </Step>
            <Step :disabled="stepsDisabled.bpmnDesigner">
              <template #title>
                流程设计
              </template>
            </Step>
            <Step :disabled="stepsDisabled.settingInfo">
              <template #title>
                扩展设置
              </template>
            </Step>
          </Steps>
        </Col>
        <Col span="8" style="text-align: right;">
          <Space>
            <Button :disabled="currentStepValue===0" @click="handlePrev" :loading="saveLoading">上一步</Button>
            <Button :disabled="currentStepValue > 1" type="primary" @click="handleNext" :loading="saveLoading">下一步</Button>
            <Button type="primary" @click="handleSave" :loading="saveLoading">保存</Button>

<!--            <Button v-if="publishBtnVisibility" type="success" @click="handlePublish" :loading="saveLoading">发布</Button>-->

            <Button type="default" @click="handleClose">关闭</Button>
          </Space>
        </Col>
      </Row>
    </template>

    <div class="w-full h-full" >
      <!-- 表单设计 -->
      <div v-show="currentStepValue === 0" style="height: 100%;">
        <FramePage ref="formDesignerRef" :frameSrc="formDesignerUrl" />
      </div>

      <!-- 流程设计 -->
      <div v-show="currentStepValue === 1" >
        <FramePage ref="flowDesignerRef" :frameSrc="flowDesignerUrl" />
      </div>

      <!-- 基本配置 -->
      <div v-show="currentStepValue === 2">
        <div style="width: 800px; margin: 30px auto;">
          <BasicForm @register="registerForm" />
        </div>
      </div>
    </div>
  </BasicModal>
</template>
<script lang="ts" setup>
  import {ref, computed, unref, nextTick, watch} from 'vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { BasicForm, useForm } from '@/components/Form/index';
  import { modelInfoFormSchema } from './modelInfo.data';
  import {saveFlowInfo, getByModelId, validateBpmnModel, saveBpmnModel} from '@/api/flowable/bpmn/modelInfo';
  import { getAll } from '@/api/base/app';
  import { Button, Row, Col, Space, Steps, TypographyText, Result} from "ant-design-vue"
  import FramePage from '@/views/components/iframe/index.vue';
  import {getFormInfoByModelKey, saveFormInfo,} from "@/api/form/formInfo";
  import { useMessage } from '@/hooks/web/useMessage';
  import { copyText } from '@/utils/copyTextToClipboard';

  const Step = Steps.Step;

  const { createMessage, createConfirm } = useMessage();
  const emit = defineEmits(['success']);

  const isUpdate = ref(false);
  const formBaseInfo = ref({});
  const flowBaseInfo = ref({});
  // const flowDesignerUrl = ref<string>('');
  const formDesignerUrl = ref<string>('/form-making/index.html');
  const formDesignerRef = ref<HTMLElement>();
  const flowDesignerRef = ref<HTMLElement>();
  const isDev = import.meta.env.DEV;
  const haveShowFlowDesigner = ref(false);
  const currentStepValue = ref<number>(0);
  const saveLoading = ref(false);
  let formDesignerLoadInterval = -1;

  // 是否已加载状态
  const stepLoadStatus = ref({
      formDesigner: false,
      bpmnDesigner: false,
      settingInfo: false,
  });
  // 是否要禁用状态
  const stepsDisabled = ref({
    formDesigner: false,
    bpmnDesigner: true,
    settingInfo: true,
  });

  const [registerForm, { setFieldsValue, updateSchema, resetFields, validate }] = useForm({
    labelWidth: 100,
    schemas: modelInfoFormSchema,
    showActionButtonGroup: false,
    showResetButton: false,
    submitButtonOptions: {
      text: '保存'
    },
    actionColOptions: {
      span: 14,
    },
  });

  const submitFormInfo = (data) => {
    const {id: modelInfoId, categoryCode} = unref(flowBaseInfo);
    const saveData = {
      id: unref(formBaseInfo)?.id,
      code: data.modelKey,
      name: data.modelName,
      title: data.modelName,
      formJson: data.formJson,
      categoryCode: categoryCode
    };

    const flowInfo = {
      id: modelInfoId,
      modelKey: data.modelKey,
      name: data.modelName,
      categoryCode: categoryCode,
    };
    return saveFlowInfo(flowInfo).then(resFlow=>{
      const {data: {success: flowResSuccess, msg: flowResMsg, data: flowDataData}} = resFlow;
      if(flowResSuccess){
        return saveFormInfo(saveData).then(resForm => {
          const {data: {success: formResSuccess, msg: formResMsg, data: formDataData} } = resForm;
          if(formResSuccess){
            createMessage.success({content: formResMsg, style: {marginTop: '10vh'}});
            flowBaseInfo.value = flowDataData;
            formBaseInfo.value = {id: formDataData.id, modelKey: formDataData.code, modelName: formDataData.name, formJson: formDataData.formJson}
            setFieldsValue({id: flowDataData.id});
            const iframe = unref(unref(formDesignerRef).frameRef);
            // 保存成功后设置modelKey禁用
            iframe.contentWindow.vueObj.modelKeyDisabled = true;
            return Promise.resolve(formResMsg);
          }else{
            createMessage.error({content: formResMsg, style: {marginTop: '10vh'}});
            return Promise.reject(formResMsg);
          }
        })
      } else {
        createMessage.error({content: flowResMsg, style: {marginTop: '10vh'}});
        return Promise.reject(flowResMsg);
      }
    });
  };

  const [registerModal, { setModalProps, changeLoading, closeModal }] = useModalInner(async (data) => {
    formDesignerLoadInterval = -1;
    resetFields();
    haveShowFlowDesigner.value = false;
    isUpdate.value = !!data?.isUpdate;
    if(!isUpdate.value){
      currentStepValue.value = 0;
    }

    stepsDisabled.value = {
      formDesigner: false,
      bpmnDesigner: !isUpdate.value,
      settingInfo: !isUpdate.value,
    }

    changeLoading(true);
    let appList = null;
    const {modelKey, name, categoryCode, modelId} = data.record;
    flowBaseInfo.value = data.record;
    saveLoading.value = false;
    formBaseInfo.value = {
      modelKey, modelName: name,
      formJson: '',
      categoryCode: categoryCode
    };

    if(modelKey){
      const formInfo = await getFormInfoByModelKey(modelKey);
      if(formInfo){
        formBaseInfo.value = {id: formInfo.id, modelKey: formInfo.code, modelName: formInfo.name, formJson: formInfo.formJson};
      }
    }

    nextTick(()=>{
      formDesignerLoadInterval = setInterval(()=>{
        loadIframeFormDesignerData(formBaseInfo)
      }, 200);
      setTimeout(() => {
        try{
          clearInterval(formDesignerLoadInterval);
          formDesignerLoadInterval = -1;
        } catch (e){
        }
      }, 5000);
    })
    try{
      appList = await getAll();
    }finally {
      changeLoading(false);
    }
    await updateSchema([
      {
        field: 'appSn',
        componentProps: { options: appList , labelField:'id'},
      }
    ]);

    setFieldsValue({
      ...data.record,
    });

    // 判断 【已发布的流程不允许修改所属系统】
    data.record.modelId && getByModelId(data.record.modelId).then(res=>{
      if(res.version > 0){
        updateSchema({
            field: 'appSn',
            componentProps: {
                disabled: true,
            },
            helpMessage: '已发布的流程不允许修改所属系统！'
        });
      }
    });
    loadAppSnDisabled(data.record.modelId);
  });

  function loadAppSnDisabled (modelId) {
    // 判断 【已发布的流程不允许修改所属系统】
    modelId&&getByModelId(modelId).then(res=>{
      if(res.version > 0){
        updateSchema({
          field: 'appSn',
          componentProps: {
            disabled: true,
          },
          helpMessage: '已发布的流程不允许修改所属系统！'
        });
      }
    });
  }

  function loadIframeFormDesignerData(formBaseInfo){
    const iframe = unref(unref(formDesignerRef).frameRef);
    // 轮询iframe是否加载完成
    if(iframe){
      if (iframe.contentWindow?.vueObj) {
        iframe.contentWindow.CustomForm.loadFormInfo(unref(formBaseInfo));
        clearInterval(formDesignerLoadInterval);
        formDesignerLoadInterval = -1;
      }
    }
  }

  const flowDesignerUrl = computed(() => {
    const { modelId } = unref(flowBaseInfo);
    return isDev ? ('/flow-bpmn-front/index.html/#/bpmn/designer?modelId=' + modelId) : ('/flow-bpmn/index.html/#/bpmn/designer?modelId=' + modelId);
  })

  watch(currentStepValue, (newVal, oldVal)=>{
    const { modelId } = unref(flowBaseInfo);
    if(newVal === 1){
      if(!haveShowFlowDesigner.value){
        changeLoading(true);
        // flowDesignerUrl.value = isDev ? ('/flow-bpmn-front/index.html/#/bpmn/designer?modelId=' + modelId) : ('/flow-bpmn/index.html/#/bpmn/designer?modelId=' + modelId);
        const iframe = unref(unref(flowDesignerRef).frameRef)
        setTimeout(()=>{
          iframe.contentWindow.location.reload(true);
          changeLoading(false);
        }, 100);
      }
      haveShowFlowDesigner.value = true;
    }
  })

  const saveFormDesignerInfo = () => {
    const iframe = unref(unref(formDesignerRef).frameRef);
    // 轮询iframe是否加载完成
    if(iframe){
      if(iframe.contentWindow?.vueObj){
        const formData = iframe.contentWindow.CustomForm.getFormDesignerSaveData()
        if(!formData){
          return;
        }
        const { formJson = null }= formData;
        if(!formJson){
          return Promise.reject("未获取到数据！");
        }
        if (!formJson.list || formJson.list.length <= 0) {
          createMessage.warn({
            content: '请添加字段！', style: {marginTop: '40px'}
          });
          return Promise.reject("请添加字段");
        }
        formData.formJson = JSON.stringify(formData.formJson);
        return submitFormInfo(formData);
      }
    }else{
      createMessage.error({content: '未获取到表单设计器数据，保存失败！'});
      return Promise.reject("未获取到框架信息，操作失败！");
    }
  }

  const getBpmnModelXml = ()=>{
    const iframe = unref(unref(flowDesignerRef).frameRef);
    if(iframe){
      return iframe.contentWindow?.bpmnInstances.modeler.saveXML(true).then(res => {
        return Promise.resolve(res);
      })
    }else{
      createMessage.error({content: '未获取到框架信息！'})
    }
  }

  const saveBpmnModelInfoBase = (data) => {
    saveLoading.value = true;
    saveBpmnModel(data).then((res) => {
      if (res.success) {
        createMessage.success({content: "保存成功！", style: {marginTop: '40px'}});
      } else {
        createMessage.error(res.msg);
      }
      saveLoading.value = false;
      changeLoading(false);
    }).catch((e) => {
      saveLoading.value = false;
      changeLoading(false);
    });
  }

  const saveBpmnModelInfo = (res) =>{
    const { xml } = res;
    const { modelId } = unref(flowBaseInfo);
    const data = {
      modelId,
      // categoryCode: unref(categoryCode),
      modelXml: xml,
    };
    changeLoading(true);
    validateBpmnModel(data).then((res) => {
      if (res.success) {
        if (res.data) {
          // 如果验证成功直接保存
          saveBpmnModelInfoBase(data);
        } else {
          createConfirm({
            iconType: 'warning',
            title: '温馨提示',
            content: `${res.msg}`,
            onOk: ()=>{
              saveBpmnModelInfoBase(data);
            },
            onCancel: ()=>{
              changeLoading(false);
            }
          });
        }
      } else {
        createMessage.error(res.msg);
        changeLoading(false);
      }
    }).catch((e) => {
      createMessage.error("调用验证Bpmn的XML时出现异常！" + e);
    });
  }

  const handleNext = () => {
    if(currentStepValue.value === 0){
      saveFormDesignerInfo().then(res => {
        unref(currentStepValue)<=2 && currentStepValue.value++;
        handleStepChange(currentStepValue.value);
      });
      return;
    }
    if(currentStepValue.value === 1){
      getBpmnModelXml().then(res => {
        saveBpmnModelInfo(res);
        setTimeout(() => {
          unref(currentStepValue)<=2 && currentStepValue.value++;
          handleStepChange(currentStepValue.value);
        }, 500)
      });
      return;
    }
    unref(currentStepValue)<=2 && currentStepValue.value++;
    handleStepChange(currentStepValue.value);
  };
  const handlePrev = () => {
    unref(currentStepValue) > 0 && currentStepValue.value--;
    handleStepChange(currentStepValue.value);
  };

  function handleStepChange(current){
    if(current === 0){
      stepLoadStatus.value.formDesigner = true;
      stepsDisabled.value.formDesigner = false;
    }
    if(current === 1){
      stepLoadStatus.value.bpmnDesigner = true;
      stepsDisabled.value.bpmnDesigner = false;
    }
    if(current === 2){
      stepLoadStatus.value.settingInfo = true;
      stepsDisabled.value.settingInfo = false;
    }
  }
  function handleSave(){
    if(currentStepValue.value === 0){
      saveLoading.value = true;
      saveFormDesignerInfo().then(res => {
        handleStepChange(currentStepValue.value);
        saveLoading.value = false;
      }).finally(() => {
        saveLoading.value = false;
      });
      return;
    }
    if(currentStepValue.value === 1){
      getBpmnModelXml().then(res => {
        saveBpmnModelInfo(res);
      });
    }
    if(currentStepValue.value === 2){
      saveBaseInfo();
    }
  }

  async function saveBaseInfo(){
    try {
      saveLoading.value = true;
      const values = await validate();
      const result = await saveFlowInfo(values);

      const {data: {msg, success, data}} = result;
      if(success){
        createMessage.success({content: msg, style: {marginTop: '10vh'}});
        loadAppSnDisabled(data.modelId);
      } else {
        createMessage.error({content: msg, style: {marginTop: '10vh'}});
      }
    } finally {
      saveLoading.value = false;
      changeLoading(false);
    }
  }

  function handleClose() {
    closeModal();
    clearInterval(formDesignerLoadInterval);
    formDesignerLoadInterval = -1;
    emit('success');
  }

  function doCopyContent(content){
    copyText(content);
  }
</script>

<style lang="less">
  .form-flow-designer{
    .scroll-container {
      padding-top: 0!important;
      padding-bottom: 0!important;
      .scrollbar__wrap{
        margin-bottom: 0!important;
      }
    }
    .ant-modal{
      max-width: unset;
      .ant-modal-header{
        padding-top: 10px;
        padding-bottom: 8px;
        cursor: default!important;
      }
      .ant-modal-body{
        .scrollbar__view{
          >div{
            height: auto!important;
          }
        }
      }
    }
  }

  /* 设计器样式 */
  .designer-container{
    width: 100%;
    height: 100%;
    overflow: auto;
  }

</style>
