var D=(C,m,r)=>new Promise((_,p)=>{var b=a=>{try{u(r.next(a))}catch(l){p(l)}},e=a=>{try{u(r.throw(a))}catch(l){p(l)}},u=a=>a.done?_(a.value):Promise.resolve(a.value).then(b,e);u((r=r.apply(C,m)).next())});import{_ as h}from"./BasicForm.vue_vue_type_script_setup_true_lang-b5fd2c2d.js";import"./BasicForm.vue_vue_type_style_index_0_lang-1d0165cd.js";import{u as V}from"./useForm-d2875f1c.js";import{C as B,a as x,c as P}from"./index.js";import{P as g}from"./index-5a8cda67.js";import{Z as c,E as n,W as s,$ as F}from"./antd-bebda08e.js";import{d as S,a6 as I,Z as U,a7 as q,a8 as d,l as t,u as i,m as O,E as f}from"./vue-f7f38239.js";import"./index-6bb63bf0.js";import"./useWindowSizeFn-01ffbee2.js";import"./index-1b9aa1da.js";import"./uuid-31b8b5a4.js";import"./useSortable-9ec2a7be.js";import"./download-2647fd8d.js";import"./base64Conver-39fc0d26.js";import"./index-b34be3ab.js";import"./IconPicker.vue_vue_type_script_setup_true_lang-af62677b.js";import"./copyTextToClipboard-ad844e61.js";import"./index-091b1c05.js";import"./useContentViewHeight-4f74c208.js";import"./onMountedOrActivated-a837e8fb.js";const w=S({__name:"CustomerForm",setup(C){const m=e=>[{required:!0,validator:()=>D(this,null,function*(){if(!e.typeKey)return Promise.reject("请选择类型");if(!e.typeValue)return Promise.reject("请输入数据");Promise.resolve()})}],r=[{field:"field1",component:"Input",label:"render方式",colProps:{span:8},dynamicDisabled:({values:e})=>!!e.field_disabled,rules:[{required:!0}],render:({model:e,field:u},{disabled:a})=>O(n,{placeholder:"请输入",value:e[u],onChange:l=>{e[u]=l.target.value},disabled:a})},{field:"field2",component:"Input",label:"render组件slot",colProps:{span:8},dynamicDisabled:({values:e})=>!!e.field_disabled,rules:[{required:!0}],renderComponentContent:(e,{disabled:u})=>({suffix:()=>u?"suffix_disabled":"suffix_default"})},{field:"field3",label:"自定义Slot",slot:"f3",colProps:{span:8},dynamicDisabled:({values:e})=>!!e.field_disabled,rules:[{required:!0}]},{field:"field4",component:"Input",renderColContent({model:e,field:u},{disabled:a}){return t(c,{name:"field4",label:"renderColContent渲染",rules:[{required:!0}]},{default:()=>[t(n,{placeholder:"请输入",value:e[u],"onUpdate:value":l=>e[u]=l,disabled:a},null)]})},colProps:{span:8},dynamicDisabled:({values:e})=>!!e.field_disabled},{field:"field5",component:"Input",label:"自定义colSlot",colSlot:"colSlot_field5",colProps:{span:8},dynamicDisabled:({values:e})=>!!e.field_disabled},{required:!0,field:"typeKey2",defaultValue:"测试类型",fields:["typeValue2"],defaultValueObj:{typeValue2:"默认测试_文字"},component:"Input",label:"复合field render",render({model:e,field:u},{disabled:a}){return t(n.Group,{compact:!0},{default:()=>[t(s,{disabled:a,style:"width: 120px",allowClear:!0,value:e[u],"onUpdate:value":l=>e[u]=l},{default:()=>[t(s.Option,{value:"测试类型"},{default:()=>[f("测试类型")]}),t(s.Option,{value:"测试名称"},{default:()=>[f("测试名称")]})]}),t(c,{name:"typeValue2",class:"local_typeValue",rules:[{required:!0}]},{default:()=>[t(F,null,{default:()=>[t(n,{placeholder:"请输入",value:e.typeValue2,"onUpdate:value":l=>e.typeValue2=l,disabled:a},null)]})]})]})},colProps:{span:8},dynamicDisabled:({values:e})=>!!e.field_disabled},{field:"typeKey",defaultValue:"公司名称",fields:["typeValue"],defaultValueObj:{typeValue:"默认文字"},component:"Input",renderColContent({model:e,field:u},{disabled:a}){return t(c,{name:"typeKey",label:"复合field renderColContent",rules:m(e)},{default:()=>[t(n.Group,{compact:!0},{default:()=>[t(s,{allowClear:!0,disabled:a,style:"width: 120px",value:e[u],"onUpdate:value":l=>e[u]=l},{default:()=>[t(s.Option,{value:"公司名称"},{default:()=>[f("公司名称")]}),t(s.Option,{value:"产品名称"},{default:()=>[f("产品名称")]})]}),t(F,null,{default:()=>[t(n,{style:"width: calc(100% - 120px); margin-left: -1px;",placeholder:"请输入",value:e.typeValue,"onUpdate:value":l=>e.typeValue=l,disabled:a},null)]})]})]})},colProps:{span:16},dynamicDisabled:({values:e})=>!!e.field_disabled},{field:"field_disabled",component:"Switch",label:"是否禁用 编辑字段",colProps:{span:8},labelWidth:200}],{createMessage:_}=x(),[p]=V({labelWidth:120,schemas:r,actionColOptions:{span:24}});function b(e){_.success("click search,values:"+JSON.stringify(e))}return(e,u)=>{const a=I("a-input");return U(),q(i(g),{title:"自定义组件示例"},{default:d(()=>[t(i(B),{title:"自定义表单"},{default:d(()=>[t(i(h),{class:"local_form",onRegister:i(p),onSubmit:b},{f3:d(({model:l,field:o,disabled:y})=>[t(a,{value:l[o],"onUpdate:value":v=>l[o]=v,disabled:y,placeholder:"自定义slot"},null,8,["value","onUpdate:value","disabled"])]),colSlot_field5:d(({model:l,field:o,disabled:y})=>[t(i(c),{name:o,label:"自定义colSlot",rules:[{required:!0}]},{default:d(()=>[t(a,{value:l[o],"onUpdate:value":v=>l[o]=v,disabled:y,placeholder:"自定义colSlot"},null,8,["value","onUpdate:value","disabled"])]),_:2},1032,["name"])]),_:1},8,["onRegister"])]),_:1})]),_:1})}}});const le=P(w,[["__scopeId","data-v-f92491f2"]]);export{le as default};