var _=(d,a,r)=>new Promise((c,i)=>{var m=e=>{try{l(r.next(e))}catch(n){i(n)}},f=e=>{try{l(r.throw(e))}catch(n){i(n)}},l=e=>e.done?c(e.value):Promise.resolve(e.value).then(m,f);l((r=r.apply(d,a)).next())});import{_ as b}from"./BasicForm.vue_vue_type_script_setup_true_lang-b5fd2c2d.js";import"./BasicForm.vue_vue_type_style_index_0_lang-1d0165cd.js";import{u as P}from"./useForm-d2875f1c.js";import{_ as h}from"./PersonTable.vue_vue_type_script_setup_true_lang-1024c295.js";import{P as g}from"./index-5a8cda67.js";import{R as u}from"./antd-bebda08e.js";import{d as q,f as y,a6 as k,Z as x,a7 as R,a8 as s,l as t,E as C,u as o}from"./vue-f7f38239.js";import{c as S}from"./index.js";import"./index-6bb63bf0.js";import"./useWindowSizeFn-01ffbee2.js";import"./index-1b9aa1da.js";import"./uuid-31b8b5a4.js";import"./useSortable-9ec2a7be.js";import"./download-2647fd8d.js";import"./base64Conver-39fc0d26.js";import"./index-b34be3ab.js";import"./IconPicker.vue_vue_type_script_setup_true_lang-af62677b.js";import"./copyTextToClipboard-ad844e61.js";import"./index-091b1c05.js";import"./BasicTable.vue_vue_type_script_setup_true_lang-68f7da80.js";import"./TableImg.vue_vue_type_style_index_0_lang-c025c7d6.js";import"./onMountedOrActivated-a837e8fb.js";import"./sortable.esm-4ae27e0b.js";import"./useTable-dd83d9cb.js";import"./useContentViewHeight-4f74c208.js";const p=[{label:"付晓晓",value:"1"},{label:"周毛毛",value:"2"}],v=[{label:"私密",value:"1"},{label:"公开",value:"2"}],w=[{field:"f1",component:"Input",label:"仓库名",required:!0},{field:"f2",component:"Input",label:"仓库域名",required:!0,componentProps:{addonBefore:"http://",addonAfter:"com"},colProps:{offset:2}},{field:"f3",component:"Select",label:"仓库管理员",componentProps:{options:p},required:!0,colProps:{offset:2}},{field:"f4",component:"Select",label:"审批人",componentProps:{options:p},required:!0},{field:"f5",component:"RangePicker",label:"生效日期",required:!0,colProps:{offset:2}},{field:"f6",component:"Select",label:"仓库类型",componentProps:{options:v},required:!0,colProps:{offset:2}}],B=[{field:"t1",component:"Input",label:"任务名",required:!0},{field:"t2",component:"Input",label:"任务描述",required:!0,colProps:{offset:2}},{field:"t3",component:"Select",label:"执行人",componentProps:{options:p},required:!0,colProps:{offset:2}},{field:"t4",component:"Select",label:"责任人",componentProps:{options:p},required:!0},{field:"t5",component:"TimePicker",label:"生效日期",required:!0,componentProps:{style:{width:"100%"}},colProps:{offset:2}},{field:"t6",component:"Select",label:"任务类型",componentProps:{options:v},required:!0,colProps:{offset:2}}],I=q({name:"FormHightPage",__name:"index",setup(d){const a=y(null),[r,{validate:c}]=P({layout:"vertical",baseColProps:{span:6},schemas:w,showActionButtonGroup:!1}),[i,{validate:m}]=P({layout:"vertical",baseColProps:{span:6},schemas:B,showActionButtonGroup:!1});function f(){return _(this,null,function*(){try{a.value;const[l,e]=yield Promise.all([c(),m()])}catch(l){}})}return(l,e)=>{const n=k("a-button");return x(),R(o(g),{class:"high-form",title:"高级表单",content:" 高级表单常见于一次性输入和提交大批量数据的场景。"},{rightFooter:s(()=>[t(n,{type:"primary",onClick:f},{default:s(()=>[C(" 提交 ")]),_:1})]),default:s(()=>[t(o(u),{title:"仓库管理",bordered:!1},{default:s(()=>[t(o(b),{onRegister:o(r)},null,8,["onRegister"])]),_:1}),t(o(u),{title:"任务管理",bordered:!1,class:"!mt-5"},{default:s(()=>[t(o(b),{onRegister:o(i)},null,8,["onRegister"])]),_:1}),t(o(u),{title:"成员管理",bordered:!1,class:"!mt-5"},{default:s(()=>[t(h,{ref_key:"tableRef",ref:a},null,512)]),_:1})]),_:1})}}});const re=S(I,[["__scopeId","data-v-ddf1f53d"]]);export{re as default};