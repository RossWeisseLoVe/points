var Se=Object.defineProperty;var H=Object.getOwnPropertySymbols;var xe=Object.prototype.hasOwnProperty,Be=Object.prototype.propertyIsEnumerable;var Q=(d,i,n)=>i in d?Se(d,i,{enumerable:!0,configurable:!0,writable:!0,value:n}):d[i]=n,Z=(d,i)=>{for(var n in i||(i={}))xe.call(i,n)&&Q(d,n,i[n]);if(H)for(var n of H(i))Be.call(i,n)&&Q(d,n,i[n]);return d};var J=(d,i,n)=>new Promise((l,M)=>{var S=w=>{try{h(n.next(w))}catch(b){M(b)}},u=w=>{try{h(n.throw(w))}catch(b){M(b)}},h=w=>w.done?l(w.value):Promise.resolve(w.value).then(S,u);h((n=n.apply(d,i)).next())});import{m as Me,d as Ce,f as g,x as Re,u as o,c as Ne,w as Te,a6 as Fe,Z as W,a7 as ee,a8 as r,l as f,_ as Pe,E as v,a0 as j,$ as B,n as O,z as L,ab as ke}from"./vue-f7f38239.js";import{a as Ke,B as Je}from"./index-6bb63bf0.js";import{_ as We}from"./BasicForm.vue_vue_type_script_setup_true_lang-b5fd2c2d.js";import"./BasicForm.vue_vue_type_style_index_0_lang-1d0165cd.js";import{u as je}from"./useForm-d2875f1c.js";import{Q as Oe,bf as oe,U as Le,V as U,bp as te,Y as Ue,B as N}from"./antd-bebda08e.js";import{a as ae,s as se,v as Ve,b as $e}from"./modelInfo-bc19c532.js";import{a as Xe}from"./app-72ac4173.js";import ne from"./index-53e38637.js";import{b as le,a as Ae}from"./index.js";import{c as Ee}from"./copyTextToClipboard-ad844e61.js";const po=[{title:"名称",dataIndex:"name",align:"left"},{title:"KEY",dataIndex:"modelKey",width:100,align:"left"},{title:"分类名称",dataIndex:"categoryName",width:100,align:"left"},{title:"系统",dataIndex:"appName",width:100,align:"left"},{title:"应用范围",dataIndex:"appliedRangeName",width:100,align:"left"},{title:"状态",dataIndex:"statusName",width:70,align:"center",customRender:({record:d})=>{const{status:i,statusName:n}=d;var l="";return~~i===2?l="warning":~~i===3?l="success":~~i===4?l="error":l="gray",Me(Oe,{color:l},()=>n)}},{title:"更新时间",dataIndex:"updateTime",width:120,align:"left"}],vo=[{field:"keyword",label:"关键字",component:"Input",componentProps:{placeholder:"请输入名称/编码"},labelWidth:60,colProps:{span:6,lg:{span:6,offset:0},sm:{span:10,offset:0},xs:{span:16,offset:0}}},{field:"appSn",label:"系统",component:"Select",labelWidth:60,colProps:{span:6,lg:{span:6,offset:0},sm:{span:10,offset:0},xs:{span:16,offset:0}}}],qe=[{field:"id",label:"ID",required:!1,component:"Input",show:!1},{field:"appSn",label:"所属系统",component:"Select",componentProps:{getPopupContainer:()=>document.body},required:!0,colProps:{span:24}}],ze=d=>le.get({url:"/flow/form/formInfo/getFormInfoByModelKey?modelKey="+d}),Ye=d=>le.post({url:"/flow/form/formInfo/saveOrUpdate",params:d},{isReturnNativeResponse:!0}),Ge={key:0},He={class:"w-full h-full"},Qe={style:{height:"100%"}},Ze={style:{width:"800px",margin:"30px auto"}},go=Ce({__name:"ModelInfoModal",emits:["success"],setup(d,{emit:i}){const n=oe.Step,{createMessage:l,createConfirm:M}=Ae(),S=g(!1),u=g({}),h=g({}),w=g("/form-making/index.html"),b=g(),T=g(),F=g(!1),a=g(0),c=g(!1);let _=-1;const P=g({formDesigner:!1,bpmnDesigner:!1,settingInfo:!1}),D=g({formDesigner:!1,bpmnDesigner:!0,settingInfo:!0}),[re,{setFieldsValue:V,updateSchema:k,resetFields:ie,validate:fe}]=je({labelWidth:100,schemas:qe,showActionButtonGroup:!1,showResetButton:!1,submitButtonOptions:{text:"保存"},actionColOptions:{span:14}}),me=e=>{var K;const{id:t,categoryCode:s}=o(h),m={id:(K=o(u))==null?void 0:K.id,code:e.modelKey,name:e.modelName,title:e.modelName,formJson:e.formJson,categoryCode:s},p={id:t,modelKey:e.modelKey,name:e.modelName,categoryCode:s};return se(p).then(I=>{const{data:{success:we,msg:Y,data:G}}=I;return we?Ye(m).then(be=>{const{data:{success:_e,msg:C,data:R}}=be;if(_e){l.success({content:C,style:{marginTop:"10vh"}}),h.value=G,u.value={id:R.id,modelKey:R.code,modelName:R.name,formJson:R.formJson},V({id:G.id});const De=o(o(b).frameRef);return De.contentWindow.vueObj.modelKeyDisabled=!0,Promise.resolve(C)}else return l.error({content:C,style:{marginTop:"10vh"}}),Promise.reject(C)}):(l.error({content:Y,style:{marginTop:"10vh"}}),Promise.reject(Y))})},[de,{setModalProps:eo,changeLoading:y,closeModal:ue}]=Ke(e=>J(this,null,function*(){_=-1,ie(),F.value=!1,S.value=!!(e!=null&&e.isUpdate),S.value||(a.value=0),D.value={formDesigner:!1,bpmnDesigner:!S.value,settingInfo:!S.value},y(!0);let t=null;const{modelKey:s,name:m,categoryCode:p,modelId:K}=e.record;if(h.value=e.record,c.value=!1,u.value={modelKey:s,modelName:m,formJson:"",categoryCode:p},s){const I=yield ze(s);I&&(u.value={id:I.id,modelKey:I.code,modelName:I.name,formJson:I.formJson})}Re(()=>{_=setInterval(()=>{ce(u)},200),setTimeout(()=>{try{clearInterval(_),_=-1}catch(I){}},5e3)});try{t=yield Xe()}finally{y(!1)}yield k([{field:"appSn",componentProps:{options:t,labelField:"id"}}]),V(Z({},e.record)),e.record.modelId&&ae(e.record.modelId).then(I=>{I.version>0&&k({field:"appSn",componentProps:{disabled:!0},helpMessage:"已发布的流程不允许修改所属系统！"})}),$(e.record.modelId)}));function $(e){e&&ae(e).then(t=>{t.version>0&&k({field:"appSn",componentProps:{disabled:!0},helpMessage:"已发布的流程不允许修改所属系统！"})})}function ce(e){var s;const t=o(o(b).frameRef);t&&(s=t.contentWindow)!=null&&s.vueObj&&(t.contentWindow.CustomForm.loadFormInfo(o(e)),clearInterval(_),_=-1)}const pe=Ne(()=>{const{modelId:e}=o(h);return"/flow-bpmn/index.html/#/bpmn/designer?modelId="+e});Te(a,(e,t)=>{if(o(h),e===1){if(!F.value){y(!0);const s=o(o(T).frameRef);setTimeout(()=>{s.contentWindow.location.reload(!0),y(!1)},100)}F.value=!0}});const X=()=>{var t;const e=o(o(b).frameRef);if(e){if((t=e.contentWindow)!=null&&t.vueObj){const s=e.contentWindow.CustomForm.getFormDesignerSaveData();if(!s)return;const{formJson:m=null}=s;return m?!m.list||m.list.length<=0?(l.warn({content:"请添加字段！",style:{marginTop:"40px"}}),Promise.reject("请添加字段")):(s.formJson=JSON.stringify(s.formJson),me(s)):Promise.reject("未获取到数据！")}}else return l.error({content:"未获取到表单设计器数据，保存失败！"}),Promise.reject("未获取到框架信息，操作失败！")},A=()=>{var t;const e=o(o(T).frameRef);if(e)return(t=e.contentWindow)==null?void 0:t.bpmnInstances.modeler.saveXML(!0).then(s=>Promise.resolve(s));l.error({content:"未获取到框架信息！"})},E=e=>{c.value=!0,$e(e).then(t=>{t.success?l.success({content:"保存成功！",style:{marginTop:"40px"}}):l.error(t.msg),c.value=!1,y(!1)}).catch(t=>{c.value=!1,y(!1)})},q=e=>{const{xml:t}=e,{modelId:s}=o(h),m={modelId:s,modelXml:t};y(!0),Ve(m).then(p=>{p.success?p.data?E(m):M({iconType:"warning",title:"温馨提示",content:`${p.msg}`,onOk:()=>{E(m)},onCancel:()=>{y(!1)}}):(l.error(p.msg),y(!1))}).catch(p=>{l.error("调用验证Bpmn的XML时出现异常！"+p)})},ve=()=>{if(a.value===0){X().then(e=>{o(a)<=2&&a.value++,x(a.value)});return}if(a.value===1){A().then(e=>{q(e),setTimeout(()=>{o(a)<=2&&a.value++,x(a.value)},500)});return}o(a)<=2&&a.value++,x(a.value)},ge=()=>{o(a)>0&&a.value--,x(a.value)};function x(e){e===0&&(P.value.formDesigner=!0,D.value.formDesigner=!1),e===1&&(P.value.bpmnDesigner=!0,D.value.bpmnDesigner=!1),e===2&&(P.value.settingInfo=!0,D.value.settingInfo=!1)}function he(){if(a.value===0){c.value=!0,X().then(e=>{x(a.value),c.value=!1}).finally(()=>{c.value=!1});return}a.value===1&&A().then(e=>{q(e)}),a.value===2&&ye()}function ye(){return J(this,null,function*(){try{c.value=!0;const e=yield fe(),t=yield se(e),{data:{msg:s,success:m,data:p}}=t;m?(l.success({content:s,style:{marginTop:"10vh"}}),$(p.modelId)):l.error({content:s,style:{marginTop:"10vh"}})}finally{c.value=!1,y(!1)}})}function Ie(){ue(),clearInterval(_),_=-1,i("success")}function z(e){Ee(e)}return(e,t)=>{const s=Fe("Tooltip");return W(),ee(o(Je),ke({wrapClassName:"form-flow-designer"},e.$attrs,{onRegister:o(de)}),{title:r(()=>[f(o(Le),null,{default:r(()=>[f(o(U),{span:"8"},{default:r(()=>[u.value.modelName?(W(),ee(s,{key:1,placement:"leftBottom"},{title:r(()=>[v(j(u.value.modelName),1)]),default:r(()=>[v(" 编辑流程 - "),f(o(te),{onClick:t[0]||(t[0]=m=>z(u.value.modelName))},{default:r(()=>[v(j(u.value.modelName),1)]),_:1}),v(" - "),f(o(te),{type:"secondary",onClick:t[1]||(t[1]=m=>z(u.value.modelKey))},{default:r(()=>[v(j(u.value.modelKey),1)]),_:1})]),_:1})):(W(),Pe("span",Ge,"新建流程"))]),_:1}),f(o(U),{span:"8"},{default:r(()=>[f(o(oe),{class:"designer-steps",onChange:x,current:a.value,"onUpdate:current":t[2]||(t[2]=m=>a.value=m),size:"small",type:"navigation",style:{marginBottom:"0px",paddingTop:"0"}},{default:r(()=>[f(o(n),{disabled:D.value.formDesigner},{title:r(()=>[v(" 表单设计 ")]),_:1},8,["disabled"]),f(o(n),{disabled:D.value.bpmnDesigner},{title:r(()=>[v(" 流程设计 ")]),_:1},8,["disabled"]),f(o(n),{disabled:D.value.settingInfo},{title:r(()=>[v(" 扩展设置 ")]),_:1},8,["disabled"])]),_:1},8,["current"])]),_:1}),f(o(U),{span:"8",style:{"text-align":"right"}},{default:r(()=>[f(o(Ue),null,{default:r(()=>[f(o(N),{disabled:a.value===0,onClick:ge,loading:c.value},{default:r(()=>[v("上一步")]),_:1},8,["disabled","loading"]),f(o(N),{disabled:a.value>1,type:"primary",onClick:ve,loading:c.value},{default:r(()=>[v("下一步")]),_:1},8,["disabled","loading"]),f(o(N),{type:"primary",onClick:he,loading:c.value},{default:r(()=>[v("保存")]),_:1},8,["loading"]),f(o(N),{type:"default",onClick:Ie},{default:r(()=>[v("关闭")]),_:1})]),_:1})]),_:1})]),_:1})]),default:r(()=>[B("div",He,[O(B("div",Qe,[f(ne,{ref_key:"formDesignerRef",ref:b,frameSrc:w.value},null,8,["frameSrc"])],512),[[L,a.value===0]]),O(B("div",null,[f(ne,{ref_key:"flowDesignerRef",ref:T,frameSrc:pe.value},null,8,["frameSrc"])],512),[[L,a.value===1]]),O(B("div",null,[B("div",Ze,[f(o(We),{onRegister:o(re)},null,8,["onRegister"])])],512),[[L,a.value===2]])])]),_:1},16,["onRegister"])}}});export{go as _,po as c,vo as s};