import{B as u}from"./TableImg.0ca8f48a.js";import"./useForm.449ab0d8.js";import{u as d}from"./useTable.e99618b3.js";import{P as f}from"./index.a877f91e.js";import{L as _,a as T,aF as i,o as m,f as b,m as h,z as p,i as g,B,t as k,j as y}from"./index.2b16d9a1.js";import{T as t}from"./index.d0d8cdf7.js";import{todoTableSchema as v,searchFormSchema as $}from"./data.6a52d5e9.js";import x from"./ProcessHeader.7d2272ed.js";import C from"./LaunchButton.d774d6f5.js";import{a as I,c as P}from"./process.32df2958.js";import"./index.11e06de4.js";import"./index.e956cba1.js";import"./index.29c63fac.js";import"./index.beefe435.js";import"./get.9f6eebb7.js";import"./useWindowSizeFn.ed3cc4b0.js";import"./index.e7eaeed7.js";import"./FullscreenOutlined.acb862b9.js";import"./index.dc7ada20.js";import"./sortable.esm.2632adaa.js";import"./RedoOutlined.d8bbda42.js";import"./eagerComputed.bb8c5d26.js";import"./scrollTo.1200d453.js";import"./index.c3a748d0.js";import"./index.0e6c0c26.js";import"./index.6d6145ef.js";import"./useFlexGapSupport.5b5e1140.js";import"./_baseIteratee.f482732d.js";import"./useSize.a2b98417.js";import"./index.a2713d3d.js";import"./transButton.25002429.js";import"./index.3f55bd74.js";import"./useRefs.c375bc6f.js";import"./download.33788f11.js";import"./index.d96f1b73.js";import"./ClockCircleOutlined.2fc96921.js";import"./index.6149f0c4.js";import"./uniqBy.d1c21867.js";import"./index.e4a19c05.js";import"./index.0da486db.js";import"./useContentViewHeight.0617d761.js";import"./ArrowLeftOutlined.cb29a710.js";import"./index.8fed1b97.js";import"./PlusOutlined.097c7aec.js";const S=T({components:{BasicTable:u,ProcessHeader:x,LaunchButton:C,PageWrapper:f,[t.name]:t,[t.TabPane.name]:t.TabPane},setup(){const[e,{getForm:r}]=d({api:I,title:"",columns:v,formConfig:{labelWidth:120,schemas:$,showAdvancedButton:!1,showResetButton:!1,autoSubmitOnEnter:!0},useSearchForm:!0,pagination:!0,showIndexColumn:!0,canResize:!1});return P().then(s=>{const{updateSchema:a}=r();a({field:"appSn",componentProps:{options:s}})}),{registerTodoTable:e}}}),w={class:"desc-wrap process"};function N(e,r,s,a,F,R){const n=i("router-link"),c=i("BasicTable");return m(),b("div",w,[h(c,{onRegister:e.registerTodoTable},{bodyCell:p(({column:l,record:o})=>[l.key==="formName"?(m(),g(n,{key:0,to:`/process/approve/${o.processDefinitionKey}?taskId=${o.taskId}&procInstId=${o.processInstanceId}&businessKey=${o.businessKey}`},{default:p(()=>[B(k(o.formName),1)]),_:2},1032,["to"])):y("",!0)]),_:1},8,["onRegister"])])}var xo=_(S,[["render",N]]);export{xo as default};