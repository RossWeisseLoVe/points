import{_ as M}from"./BasicTable.vue_vue_type_script_setup_true_lang-68f7da80.js";import"./TableImg.vue_vue_type_style_index_0_lang-c025c7d6.js";import{a as E}from"./BasicForm.vue_vue_type_style_index_0_lang-1d0165cd.js";import{u as R}from"./useTable-dd83d9cb.js";import{j as I,k as P,l as $,m as A}from"./dictionary-0bba14e5.js";import{P as F}from"./index-5a8cda67.js";import{a as N,c as V}from"./index.js";import{b as W}from"./index-6bb63bf0.js";import K from"./DictionaryModal-18bd0175.js";import{d as U,f as j,a6 as r,Z as f,_ as L,l as b,a8 as h,a7 as g,E as O,aa as _}from"./vue-f7f38239.js";import"./BasicForm.vue_vue_type_script_setup_true_lang-b5fd2c2d.js";import"./antd-bebda08e.js";import"./useForm-d2875f1c.js";import"./uuid-31b8b5a4.js";import"./sortable.esm-4ae27e0b.js";import"./onMountedOrActivated-a837e8fb.js";import"./useWindowSizeFn-01ffbee2.js";import"./index-1b9aa1da.js";import"./useSortable-9ec2a7be.js";import"./download-2647fd8d.js";import"./base64Conver-39fc0d26.js";import"./index-b34be3ab.js";import"./IconPicker.vue_vue_type_script_setup_true_lang-af62677b.js";import"./copyTextToClipboard-ad844e61.js";import"./index-091b1c05.js";import"./useContentViewHeight-4f74c208.js";import"./constantEnum-c819fa5f.js";const{createMessage:Z}=N(),q=U({name:"DictionaryTable",components:{BasicTable:M,DictionaryModal:K,PageWrapper:F,TableAction:E},setup(e,{emit:l}){const[y,{openModal:c,setModalProps:s}]=W(),n=j(""),[d,{reload:a,setProps:p,setTableData:u,setSelectedRowKeys:m}]=R({title:"数据字典列表",api:I,columns:P,formConfig:{labelWidth:120,schemas:$,showAdvancedButton:!1,showResetButton:!1,autoSubmitOnEnter:!0},immediate:!1,clickToRowSelect:!0,rowSelection:{type:"radio",columnWidth:40},useSearchForm:!0,showIndexColumn:!1,showTableSetting:!1,bordered:!0,pagination:!0,rowKey:"id",actionColumn:{width:80,title:"操作",dataIndex:"action"}});function i(){if(n.value===""){Z.warning("请选择数据类型！",2);return}s({title:"新增字典"}),c(!0,{record:{dicTypeId:n.value},isUpdate:!1})}function T(t){n.value=t,p({searchInfo:{dicTypeId:t}}),a({page:1})}function C(){u([]),n.value=""}function k(t,o){o.stopPropagation(),s({title:"修改字典"}),c(!0,{record:t,isUpdate:!0})}function D(t,o){o.stopPropagation()}function S(t){A(t.id).then(()=>{a()})}function w(){setTimeout(()=>{a()},200)}function B(t){m([t.id]),l("handleSelect",t.id)}function v({keys:t,rows:o}){o&&o.length>0&&l("handleSelect",o[0].id)}return{registerTable:d,registerModal:y,dictTypeId:n,handleDeleteStop:D,clickDictionary:B,changeDictionary:v,filterByDictType:T,cleanTableData:C,handleCreate:i,handleEdit:k,handleDelete:S,handleSuccess:w}}});const z={class:"bg-white overflow-hidden dictionary"};function G(e,l,y,c,s,n){const d=r("a-button"),a=r("TableAction"),p=r("BasicTable"),u=r("DictionaryModal");return f(),L("div",z,[b(p,{onRegister:e.registerTable,onRowClick:e.clickDictionary,onSelectionChange:e.changeDictionary},{toolbar:h(()=>[e.dictTypeId!==""?(f(),g(d,{key:0,type:"primary",onClick:e.handleCreate},{default:h(()=>[O("新增")]),_:1},8,["onClick"])):_("",!0)]),bodyCell:h(({column:m,record:i})=>[m.key==="action"?(f(),g(a,{key:0,actions:[{tooltip:"修改",icon:"clarity:note-edit-line",onClick:e.handleEdit.bind(null,i)},{tooltip:"删除",icon:"ant-design:delete-outlined",color:"error",onClick:e.handleDeleteStop.bind(null,i),popConfirm:{title:"是否确认删除",confirm:e.handleDelete.bind(null,i),placement:"left"}}]},null,8,["actions"])):_("",!0)]),_:1},8,["onRegister","onRowClick","onSelectionChange"]),b(u,{onRegister:e.registerModal,onSuccess:e.handleSuccess},null,8,["onRegister","onSuccess"])])}const Ce=V(q,[["render",G]]);export{Ce as default};