var b=(d,u,i)=>new Promise((f,p)=>{var o=t=>{try{e(i.next(t))}catch(l){p(l)}},r=t=>{try{e(i.throw(t))}catch(l){p(l)}},e=t=>t.done?f(t.value):Promise.resolve(t.value).then(o,r);e((i=i.apply(d,u)).next())});import{_ as C}from"./BasicForm.vue_vue_type_script_setup_true_lang-b5fd2c2d.js";import"./BasicForm.vue_vue_type_style_index_0_lang-1d0165cd.js";import{C as k,a as c}from"./index.js";import{P as $}from"./index-5a8cda67.js";import{d as P,f as B,a6 as w,Z as x,a7 as S,a8 as s,$ as v,l as n,E as a,u as m,x as g}from"./vue-f7f38239.js";import"./antd-bebda08e.js";import"./index-6bb63bf0.js";import"./useWindowSizeFn-01ffbee2.js";import"./index-1b9aa1da.js";import"./uuid-31b8b5a4.js";import"./useSortable-9ec2a7be.js";import"./download-2647fd8d.js";import"./base64Conver-39fc0d26.js";import"./index-b34be3ab.js";import"./IconPicker.vue_vue_type_script_setup_true_lang-af62677b.js";import"./copyTextToClipboard-ad844e61.js";import"./index-091b1c05.js";import"./useContentViewHeight-4f74c208.js";import"./onMountedOrActivated-a837e8fb.js";const y={class:"mb-4"},R={class:"mb-4"},L=P({__name:"RefForm",setup(d){const u=[{field:"field1",component:"Input",label:"字段1",colProps:{span:8},componentProps:{placeholder:"自定义placeholder",onChange:r=>{}}},{field:"field2",component:"Input",label:"字段2",colProps:{span:8}},{field:"field3",component:"DatePicker",label:"字段3",colProps:{span:8}},{field:"field4",component:"Select",label:"字段4",colProps:{span:8},componentProps:{options:[{label:"选项1",value:"1",key:"1"},{label:"选项2",value:"2",key:"2"}]}},{field:"field5",component:"CheckboxGroup",label:"字段5",colProps:{span:8},componentProps:{options:[{label:"选项1",value:"1"},{label:"选项2",value:"2"}]}},{field:"field7",component:"RadioGroup",label:"字段7",colProps:{span:8},componentProps:{options:[{label:"选项1",value:"1"},{label:"选项2",value:"2"}]}}],i=B(null),{createMessage:f}=c();function p(r){f.success("click search,values:"+JSON.stringify(r))}function o(r){return b(this,null,function*(){const e=m(i);e&&(yield g(),yield e.setProps(r))})}return(r,e)=>{const t=w("a-button");return x(),S(m($),{title:"Ref操作示例"},{default:s(()=>[v("div",y,[n(t,{onClick:e[0]||(e[0]=l=>o({labelWidth:150})),class:"mr-2"},{default:s(()=>[a(" 更改labelWidth ")]),_:1}),n(t,{onClick:e[1]||(e[1]=l=>o({labelWidth:120})),class:"mr-2"},{default:s(()=>[a(" 还原labelWidth ")]),_:1}),n(t,{onClick:e[2]||(e[2]=l=>o({size:"large"})),class:"mr-2"},{default:s(()=>[a(" 更改Size ")]),_:1}),n(t,{onClick:e[3]||(e[3]=l=>o({size:"default"})),class:"mr-2"},{default:s(()=>[a(" 还原Size ")]),_:1}),n(t,{onClick:e[4]||(e[4]=l=>o({disabled:!0})),class:"mr-2"},{default:s(()=>[a(" 禁用表单 ")]),_:1}),n(t,{onClick:e[5]||(e[5]=l=>o({disabled:!1})),class:"mr-2"},{default:s(()=>[a(" 解除禁用 ")]),_:1}),n(t,{onClick:e[6]||(e[6]=l=>o({readonly:!0})),class:"mr-2"},{default:s(()=>[a(" 只读表单 ")]),_:1}),n(t,{onClick:e[7]||(e[7]=l=>o({readonly:!1})),class:"mr-2"},{default:s(()=>[a(" 解除只读 ")]),_:1}),n(t,{onClick:e[8]||(e[8]=l=>o({compact:!0})),class:"mr-2"},{default:s(()=>[a(" 紧凑表单 ")]),_:1}),n(t,{onClick:e[9]||(e[9]=l=>o({compact:!1})),class:"mr-2"},{default:s(()=>[a(" 还原正常间距 ")]),_:1}),n(t,{onClick:e[10]||(e[10]=l=>o({actionColOptions:{span:8}})),class:"mr-2"},{default:s(()=>[a(" 操作按钮位置 ")]),_:1})]),v("div",R,[n(t,{onClick:e[11]||(e[11]=l=>o({showActionButtonGroup:!1})),class:"mr-2"},{default:s(()=>[a(" 隐藏操作按钮 ")]),_:1}),n(t,{onClick:e[12]||(e[12]=l=>o({showActionButtonGroup:!0})),class:"mr-2"},{default:s(()=>[a(" 显示操作按钮 ")]),_:1}),n(t,{onClick:e[13]||(e[13]=l=>o({showResetButton:!1})),class:"mr-2"},{default:s(()=>[a(" 隐藏重置按钮 ")]),_:1}),n(t,{onClick:e[14]||(e[14]=l=>o({showResetButton:!0})),class:"mr-2"},{default:s(()=>[a(" 显示重置按钮 ")]),_:1}),n(t,{onClick:e[15]||(e[15]=l=>o({showSubmitButton:!1})),class:"mr-2"},{default:s(()=>[a(" 隐藏查询按钮 ")]),_:1}),n(t,{onClick:e[16]||(e[16]=l=>o({showSubmitButton:!0})),class:"mr-2"},{default:s(()=>[a(" 显示查询按钮 ")]),_:1}),n(t,{onClick:e[17]||(e[17]=l=>o({resetButtonOptions:{disabled:!0,text:"重置New"}})),class:"mr-2"},{default:s(()=>[a(" 修改重置按钮 ")]),_:1}),n(t,{onClick:e[18]||(e[18]=l=>o({submitButtonOptions:{disabled:!0,loading:!0}})),class:"mr-2"},{default:s(()=>[a(" 修改查询按钮 ")]),_:1})]),n(m(k),{title:"使用ref调用表单内部函数示例"},{default:s(()=>[n(m(C),{schemas:u,ref_key:"formElRef",ref:i,labelWidth:100,onSubmit:p,actionColOptions:{span:24}},null,512)]),_:1})]),_:1})}}});export{L as default};