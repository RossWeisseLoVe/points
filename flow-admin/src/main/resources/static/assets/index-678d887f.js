import FramePage from"./index-53e38637.js";import{u as useFrameKeepAlive}from"./useFrameKeepAlive-bfa904f0.js";import{d as defineComponent,Y as useRouter,u as unref,f as ref,o as onMounted,x as nextTick,c as computed,a6 as resolveComponent,Z as openBlock,_ as createElementBlock,l as createVNode,a8 as withCtx}from"./vue-f7f38239.js";import{bj as useMultipleTabStore,c as _export_sfc}from"./index.js";import{a as getByModelId}from"./modelInfo-bc19c532.js";import{a0 as Tabs,bq as PicLeftOutlined,D as Dropdown,x as Menu}from"./antd-bebda08e.js";import"./useWindowSizeFn-01ffbee2.js";const _sfc_main=defineComponent({name:"FormDesigner",components:{FramePage,Tabs,TabPane:Tabs.TabPane,PicLeftOutlined,Dropdown,Menu,MenuItem:Menu.Item},setup(){const{currentRoute}=useRouter(),{query:{modelId,modelKey,categoryCode}}=unref(currentRoute),processInfoDisabled=ref(!0),formUrl=ref(""),url=ref(""),formDesignerFrameRef=ref(),processModelId=ref(""),activeKey=ref("formDesigner"),frame=ref({}),tabStore=useMultipleTabStore(),router=useRouter(),currentTab=tabStore.getTabList.find(e=>e.fullPath===router.currentRoute.value.fullPath);function changeUrlArg(url,arg,val){let pattern=arg+"=([^&]*)",replaceText=arg+"="+val;return url.match(pattern)?url.replace(eval("/("+arg+"=)([^&]*)/gi"),replaceText):url.match("[?]")?url+"&"+replaceText:url+"?"+replaceText}function updateTabInfo(e){if(e&&e.modelId){processInfoDisabled.value=!1;let r=window.location.href;r=changeUrlArg(r,"modelId",e.modelId),r=changeUrlArg(r,"modelKey",e.modelKey),currentTab.meta.title="自定义流程-"+e.modelName,processModelId.value=e.modelId,history.pushState("","",r),url.value="/flow-bpmn/index.html#/bpmn/designer?modelId="+e.modelId+"&formType=0"}}function onSave(e){updateTabInfo(e)}onMounted(()=>{window.removeEventListener("message",e=>{}),window.addEventListener("message",e=>{const r=e.data;switch(r.cmd){case"returnFormJson":break;case"updateTabInfo":updateTabInfo(r.params.data);break}}),currentTab.meta.title.indexOf("设计流程-")!==0&&(processModelId.value=modelId,modelId&&getByModelId(modelId).then(e=>{currentTab.meta.title&&currentTab.meta.title.indexOf("-"+e.name)===-1&&(currentTab.meta.title=currentTab.meta.title+"-"+e.name)}).catch(()=>{}))}),modelId&&(processInfoDisabled.value=!1),nextTick(()=>{url.value="/flow-bpmn/index.html#/bpmn/designer?modelId="+modelId+"&formType=0",formUrl.value="/form-designer/index.html#/?modelKey="+modelKey+"&formType=custom&categoryCode="+categoryCode});const{getFramePages,hasRenderFrame,showIframe}=useFrameKeepAlive(),showFrame=computed(()=>unref(getFramePages).length>0);return{processInfoDisabled,formDesignerFrameRef,processModelId,getFramePages,hasRenderFrame,showIframe,showFrame,url,formUrl,frame,activeKey,modelKey,categoryCode,onSave}}}),index_vue_vue_type_style_index_0_lang="",_hoisted_1={class:"w-full h-full"};function _sfc_render(e,r,s,l,m,d){const t=resolveComponent("FramePage"),a=resolveComponent("TabPane"),o=resolveComponent("Tabs");return openBlock(),createElementBlock("div",_hoisted_1,[createVNode(o,{class:"form-designer",type:"card",tabBarStyle:{marginBottom:0},activeKey:e.activeKey,"onUpdate:activeKey":r[0]||(r[0]=n=>e.activeKey=n),style:{height:"100%"}},{default:withCtx(()=>[createVNode(a,{key:"formDesigner",tab:"表单设计"},{default:withCtx(()=>[createVNode(t,{ref:"formDesignerFrameRef",frameSrc:e.formUrl},null,8,["frameSrc"])]),_:1}),createVNode(a,{key:"processDesigner",tab:"流程设计",disabled:e.processInfoDisabled},{default:withCtx(()=>[createVNode(t,{frameSrc:e.url},null,8,["frameSrc"])]),_:1},8,["disabled"])]),_:1},8,["activeKey"])])}const index=_export_sfc(_sfc_main,[["render",_sfc_render]]);export{index as default};