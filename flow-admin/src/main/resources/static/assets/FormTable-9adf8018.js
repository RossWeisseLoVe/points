import{_}from"./BasicTable.vue_vue_type_script_setup_true_lang-68f7da80.js";import"./TableImg.vue_vue_type_style_index_0_lang-c025c7d6.js";import"./BasicForm.vue_vue_type_style_index_0_lang-1d0165cd.js";import{u as w}from"./useTable-dd83d9cb.js";import{getBasicColumns as g,getFormConfig as R}from"./tableData-6a02937d.js";import{d as h}from"./table-b1f4ce3d.js";import{d as K,r as b,a6 as k,Z as i,a7 as C,a8 as o,E as n,l as a,u as l,_ as c,F as x,$ as S,a0 as F}from"./vue-f7f38239.js";import{a2 as T}from"./antd-bebda08e.js";import"./BasicForm.vue_vue_type_script_setup_true_lang-b5fd2c2d.js";import"./index.js";import"./index-6bb63bf0.js";import"./useWindowSizeFn-01ffbee2.js";import"./useForm-d2875f1c.js";import"./uuid-31b8b5a4.js";import"./sortable.esm-4ae27e0b.js";import"./onMountedOrActivated-a837e8fb.js";import"./index-1b9aa1da.js";import"./useSortable-9ec2a7be.js";import"./download-2647fd8d.js";import"./base64Conver-39fc0d26.js";import"./index-b34be3ab.js";import"./IconPicker.vue_vue_type_script_setup_true_lang-af62677b.js";import"./copyTextToClipboard-ad844e61.js";import"./index-091b1c05.js";import"./select-10be400c.js";const B={key:1},se=K({__name:"FormTable",setup(V){const e=b({selectedRowKeys:[]}),[p,{getForm:v}]=w({title:"开启搜索区域",api:h,columns:g(),useSearchForm:!0,formConfig:R(),showTableSetting:!0,tableSetting:{fullScreen:!0},showIndexColumn:!1,rowKey:"id",rowSelection:{type:"checkbox",selectedRowKeys:e.selectedRowKeys,onSelect:u,onSelectAll:f}});function d(){}function u(s,r){if(r){e.selectedRowKeys.push(s.id);return}const t=e.selectedRowKeys.indexOf(s.id);t!==-1&&e.selectedRowKeys.splice(t,1)}function f(s,r,t){const m=t.map(y=>y.id);s?e.selectedRowKeys.push(...m):e.selectedRowKeys.splice(0)}return(s,r)=>{const t=k("a-button");return i(),C(l(_),{onRegister:l(p)},{"form-custom":o(()=>[n(" custom-slot ")]),headerTop:o(()=>[a(l(T),{type:"info","show-icon":""},{message:o(()=>[e.selectedRowKeys.length>0?(i(),c(x,{key:0},[S("span",null,"已选中"+F(e.selectedRowKeys.length)+"条记录(可跨页)",1),a(t,{type:"link",onClick:r[0]||(r[0]=m=>e.selectedRowKeys.splice(0)),size:"small"},{default:o(()=>[n("清空")]),_:1})],64)):(i(),c("span",B,"未选中任何项目"))]),_:1})]),toolbar:o(()=>[a(t,{type:"primary",onClick:d},{default:o(()=>[n("获取表单数据")]),_:1})]),_:1},8,["onRegister"])}}});export{se as default};