import{B as u}from"./TableImg.0ca8f48a.js";import"./useForm.449ab0d8.js";import{u as f}from"./useTable.e99618b3.js";import{P as d}from"./index.a877f91e.js";import{L as _,a as b,aF as i,o as m,f as h,m as g,z as p,i as T,B as v,t as B,j as k}from"./index.2b16d9a1.js";import{T as o}from"./index.d0d8cdf7.js";import{haveDownTableSchema as w,searchFormSchema as y}from"./data.1805f188.js";import $ from"./ProcessHeader.7d2272ed.js";import x from"./LaunchButton.d774d6f5.js";import{b as C,c as I}from"./process.32df2958.js";import"./index.11e06de4.js";import"./index.e956cba1.js";import"./index.29c63fac.js";import"./index.beefe435.js";import"./get.9f6eebb7.js";import"./useWindowSizeFn.ed3cc4b0.js";import"./index.e7eaeed7.js";import"./FullscreenOutlined.acb862b9.js";import"./index.dc7ada20.js";import"./sortable.esm.2632adaa.js";import"./RedoOutlined.d8bbda42.js";import"./eagerComputed.bb8c5d26.js";import"./scrollTo.1200d453.js";import"./index.c3a748d0.js";import"./index.0e6c0c26.js";import"./index.6d6145ef.js";import"./useFlexGapSupport.5b5e1140.js";import"./_baseIteratee.f482732d.js";import"./useSize.a2b98417.js";import"./index.a2713d3d.js";import"./transButton.25002429.js";import"./index.3f55bd74.js";import"./useRefs.c375bc6f.js";import"./download.33788f11.js";import"./index.d96f1b73.js";import"./ClockCircleOutlined.2fc96921.js";import"./index.6149f0c4.js";import"./uniqBy.d1c21867.js";import"./index.e4a19c05.js";import"./index.0da486db.js";import"./useContentViewHeight.0617d761.js";import"./ArrowLeftOutlined.cb29a710.js";import"./index.8fed1b97.js";import"./PlusOutlined.097c7aec.js";const P=b({components:{BasicTable:u,ProcessHeader:$,LaunchButton:x,PageWrapper:d,[o.name]:o,[o.TabPane.name]:o.TabPane},setup(){const[t,{getForm:r}]=f({api:C,title:"",columns:w,formConfig:{labelWidth:120,schemas:y,showAdvancedButton:!1,showResetButton:!1,autoSubmitOnEnter:!0},useSearchForm:!0,pagination:!0,showIndexColumn:!0,canResize:!1});return I().then(a=>{const{updateSchema:s}=r();s({field:"appSn",componentProps:{options:a}})}),{registerHaveDownTable:t}}}),S={class:"desc-wrap process"};function D(t,r,a,s,N,F){const n=i("router-link"),c=i("BasicTable");return m(),h("div",S,[g(c,{onRegister:t.registerHaveDownTable},{bodyCell:p(({column:l,record:e})=>[l.key==="formName"?(m(),T(n,{key:0,to:`/process/view/${e.processDefinitionKey}?taskId=${e.taskId}&procInstId=${e.processInstanceId}&businessKey=${e.businessKey}`},{default:p(()=>[v(B(e.formName),1)]),_:2},1032,["to"])):k("",!0)]),_:1},8,["onRegister"])])}var ye=_(P,[["render",D]]);export{ye as default};