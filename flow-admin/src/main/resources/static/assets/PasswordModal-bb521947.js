var M=Object.defineProperty;var d=Object.getOwnPropertySymbols;var F=Object.prototype.hasOwnProperty,h=Object.prototype.propertyIsEnumerable;var f=(e,s,o)=>s in e?M(e,s,{enumerable:!0,configurable:!0,writable:!0,value:o}):e[s]=o,u=(e,s)=>{for(var o in s||(s={}))F.call(s,o)&&f(e,o,s[o]);if(d)for(var o of d(s))h.call(s,o)&&f(e,o,s[o]);return e};var l=(e,s,o)=>new Promise((c,i)=>{var p=t=>{try{a(o.next(t))}catch(m){i(m)}},n=t=>{try{a(o.throw(t))}catch(m){i(m)}},a=t=>t.done?c(t.value):Promise.resolve(t.value).then(p,n);a((o=o.apply(e,s)).next())});import{B as $,a as P}from"./index-6bb63bf0.js";import{_ as b}from"./BasicForm.vue_vue_type_script_setup_true_lang-b5fd2c2d.js";import"./BasicForm.vue_vue_type_style_index_0_lang-1d0165cd.js";import{u as k}from"./useForm-d2875f1c.js";import{p as v}from"./account.data-83659400.js";import{a as y}from"./account-1d577ee4.js";import{d as C,f as N,u as g,c as R,a6 as w,Z as L,a7 as O,a8 as S,l as A,ab as T}from"./vue-f7f38239.js";import{c as U}from"./index.js";import"./antd-bebda08e.js";import"./useWindowSizeFn-01ffbee2.js";import"./index-1b9aa1da.js";import"./uuid-31b8b5a4.js";import"./useSortable-9ec2a7be.js";import"./download-2647fd8d.js";import"./base64Conver-39fc0d26.js";import"./index-b34be3ab.js";import"./IconPicker.vue_vue_type_script_setup_true_lang-af62677b.js";import"./copyTextToClipboard-ad844e61.js";import"./index-091b1c05.js";const V=C({name:"AccountModal",components:{BasicModal:$,BasicForm:b},emits:["success","register"],setup(e,{emit:s}){const o=N(!0),[c,{setFieldsValue:i,resetFields:p,validate:n}]=k({labelWidth:100,schemas:v,showActionButtonGroup:!1,actionColOptions:{span:23}}),[a,{setModalProps:t,closeModal:m}]=P(r=>l(this,null,function*(){p(),t({confirmLoading:!1,title:`给账号【${r.record.realName}(${r.record.username})】设置密码`}),o.value=!!(r!=null&&r.isUpdate),g(o)&&i(u({},r.record))})),_=R(()=>g(o)?"设置密码":"新增账号");function B(){return l(this,null,function*(){try{t({confirmLoading:!0});const r=yield n();r.password=r.passwordNew,delete r.passwordNew,delete r.confirmPassword,y(r),m()}finally{t({confirmLoading:!1})}})}return{registerModal:a,registerForm:c,getTitle:_,handleSubmit:B}}});function G(e,s,o,c,i,p){const n=w("BasicForm"),a=w("BasicModal");return L(),O(a,T(e.$attrs,{onRegister:e.registerModal,title:e.getTitle,onOk:e.handleSubmit}),{default:S(()=>[A(n,{onRegister:e.registerForm},null,8,["onRegister"])]),_:1},16,["onRegister","title","onOk"])}const ao=U(V,[["render",G]]);export{ao as default};