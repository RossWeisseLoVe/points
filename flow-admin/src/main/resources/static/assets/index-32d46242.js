var u=(e,s,t)=>new Promise((p,n)=>{var l=o=>{try{a(t.next(o))}catch(c){n(c)}},i=o=>{try{a(t.throw(o))}catch(c){n(c)}},a=o=>o.done?p(o.value):Promise.resolve(o.value).then(l,i);a((t=t.apply(e,s)).next())});import{_ as d}from"./BasicForm.vue_vue_type_script_setup_true_lang-b5fd2c2d.js";import"./BasicForm.vue_vue_type_style_index_0_lang-1d0165cd.js";import{u as b}from"./useForm-d2875f1c.js";import{a as f,c as _}from"./index.js";import{P}from"./index-5a8cda67.js";import{d as g,Z as h,a7 as x,a8 as v,l as w,u as m}from"./vue-f7f38239.js";import"./antd-bebda08e.js";import"./index-6bb63bf0.js";import"./useWindowSizeFn-01ffbee2.js";import"./index-1b9aa1da.js";import"./uuid-31b8b5a4.js";import"./useSortable-9ec2a7be.js";import"./download-2647fd8d.js";import"./base64Conver-39fc0d26.js";import"./index-b34be3ab.js";import"./IconPicker.vue_vue_type_script_setup_true_lang-af62677b.js";import"./copyTextToClipboard-ad844e61.js";import"./index-091b1c05.js";import"./useContentViewHeight-4f74c208.js";import"./onMountedOrActivated-a837e8fb.js";const r={span:8},B=[{field:"title",component:"Input",label:"标题",colProps:r,componentProps:{placeholder:"给目标起个名字"},required:!0},{field:"time",component:"RangePicker",label:"起止日期",colProps:r,required:!0},{field:"client",component:"Input",colProps:r,label:"客户",helpMessage:"目标的服务对象",subLabel:"( 选填 )",componentProps:{placeholder:"请描述你服务的客户，内部客户直接 @姓名／工号"}},{field:"weights",component:"InputNumber",label:"权重",colProps:r,subLabel:"( 选填 )",componentProps:{formatter:e=>e?`${e}%`:"",parser:e=>Number(e.replace("%","")),placeholder:"请输入"}},{field:"target",component:"InputTextArea",label:"目标描述",colProps:r,componentProps:{placeholder:"请输入你的阶段性工作目标",rows:4},required:!0},{field:"metrics",component:"InputTextArea",label:"衡量标准",colProps:r,componentProps:{placeholder:"请输入衡量标准",rows:4},required:!0},{field:"inviteer",component:"Input",label:"邀评人",colProps:{span:8},subLabel:"( 选填 )",componentProps:{placeholder:"请直接 @姓名／工号，最多可邀请 5 人"}},{field:"disclosure",component:"RadioGroup",label:"目标公开",colProps:{span:16},itemProps:{extra:"客户、邀评人默认被分享"},componentProps:{options:[{label:"公开",value:"1"},{label:"部分公开",value:"2"},{label:"不公开",value:"3"}]}},{field:"disclosure",component:"Select",label:" ",colProps:{span:8},show:({model:e})=>e.disclosure==="2",componentProps:{placeholder:"公开给",mode:"multiple",options:[{label:"同事1",value:"1"},{label:"同事2",value:"2"},{label:"同事3",value:"3"}]}}],I=g({name:"FormBasicPage",__name:"index",setup(e){const{createMessage:s}=f(),[t,{validate:p,setProps:n}]=b({labelCol:{span:8},wrapperCol:{span:15},schemas:B,actionColOptions:{offset:8,span:23},submitButtonOptions:{text:"提交"},submitFunc:l});function l(){return u(this,null,function*(){try{yield p(),n({submitButtonOptions:{loading:!0}}),setTimeout(()=>{n({submitButtonOptions:{loading:!1}}),s.success("提交成功！")},2e3)}catch(i){}})}return(i,a)=>(h(),x(m(P),{title:"基础表单",contentBackground:"",content:" 表单页用于向用户收集或验证信息，基础表单常见于数据项较少的表单场景。",contentClass:"p-4"},{default:v(()=>[w(m(d),{onRegister:m(t)},null,8,["onRegister"])]),_:1}))}});const E=_(I,[["__scopeId","data-v-c1a69db9"]]);export{E as default};