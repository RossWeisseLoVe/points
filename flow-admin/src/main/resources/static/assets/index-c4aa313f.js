import{_ as g}from"./BasicTable.vue_vue_type_script_setup_true_lang-68f7da80.js";import"./TableImg.vue_vue_type_style_index_0_lang-c025c7d6.js";import{a as b}from"./BasicForm.vue_vue_type_style_index_0_lang-1d0165cd.js";import{u as _}from"./useTable-dd83d9cb.js";import{g as B,d as T}from"./dicType-916f1d40.js";import{G as k,c as M,s as y}from"./DicTypeModal-2ae3d6a0.js";import{a as D,c as E}from"./index.js";import{b as S}from"./index-6bb63bf0.js";import{d as w,a6 as s,Z as C,_ as A,l as m,a8 as d,E as R,a7 as $,aa as F}from"./vue-f7f38239.js";import"./BasicForm.vue_vue_type_script_setup_true_lang-b5fd2c2d.js";import"./antd-bebda08e.js";import"./useForm-d2875f1c.js";import"./uuid-31b8b5a4.js";import"./sortable.esm-4ae27e0b.js";import"./onMountedOrActivated-a837e8fb.js";import"./useWindowSizeFn-01ffbee2.js";import"./index-1b9aa1da.js";import"./useSortable-9ec2a7be.js";import"./download-2647fd8d.js";import"./base64Conver-39fc0d26.js";import"./index-b34be3ab.js";import"./IconPicker.vue_vue_type_script_setup_true_lang-af62677b.js";import"./copyTextToClipboard-ad844e61.js";import"./index-091b1c05.js";import"./constantEnum-c819fa5f.js";const{createMessage:G}=D(),P=w({name:"GetDic",components:{BasicTable:g,TableAction:b,GetDicModal:k},setup(){const[t,{openModal:n,setModalProps:a}]=S(),[f,{reload:i}]=_({title:"列表",api:B,columns:M,formConfig:{labelWidth:120,schemas:y,showAdvancedButton:!1,showResetButton:!1,autoSubmitOnEnter:!0},canColDrag:!0,pagination:!1,expandRowByClick:!0,useSearchForm:!0,bordered:!0,showIndexColumn:!1,actionColumn:{width:120,title:"操作",dataIndex:"action",fixed:!1}});function h(){a({title:"新增字典分类"}),n(!0,{isUpdate:!1})}function l(e,o){o.stopPropagation(),a({title:"修改字典分类"}),n(!0,{record:e,isUpdate:!0})}function r(e,o){o.stopPropagation(),a({title:"新增【"+e.name+"】的子分类"}),e={pid:e.id},n(!0,{record:e,isUpdate:!0})}function c(e){if(e.children&&e.children.length>0){G.warning("有子节点，不能删除！");return}T([e.id]).then(o=>{i()})}function u(){i()}function p(){setTimeout(()=>{i()},200)}return{registerTable:f,registerModal:t,handleCreate:h,handleEdit:l,handleCreateChild:r,handleDelete:c,handleSuccess:p,doSearch:u}}});function v(t,n,a,f,i,h){const l=s("a-button"),r=s("TableAction"),c=s("BasicTable"),u=s("GetDicModal");return C(),A("div",null,[m(c,{onRegister:t.registerTable},{toolbar:d(()=>[m(l,{type:"primary",onClick:t.handleCreate},{default:d(()=>[R(" 新增 ")]),_:1},8,["onClick"])]),bodyCell:d(({column:p,record:e})=>[p.key==="action"?(C(),$(r,{key:0,actions:[{tooltip:"添加子分类",icon:"ant-design:plus-outlined",onClick:t.handleCreateChild.bind(null,e)},{tooltip:"修改",icon:"clarity:note-edit-line",onClick:t.handleEdit.bind(null,e)},{tooltip:"删除",icon:"ant-design:delete-outlined",color:"error",onClick:o=>{o.stopPropagation()},popConfirm:{title:"是否确认删除",confirm:t.handleDelete.bind(null,e),placement:"left"}}]},null,8,["actions"])):F("",!0)]),_:1},8,["onRegister"]),m(u,{onRegister:t.registerModal,onSuccess:t.handleSuccess},null,8,["onRegister","onSuccess"])])}const le=E(P,[["render",v]]);export{le as default};