var y=Object.defineProperty,b=Object.defineProperties;var v=Object.getOwnPropertyDescriptors;var d=Object.getOwnPropertySymbols;var k=Object.prototype.hasOwnProperty,w=Object.prototype.propertyIsEnumerable;var f=(i,e,t)=>e in i?y(i,e,{enumerable:!0,configurable:!0,writable:!0,value:t}):i[e]=t,u=(i,e)=>{for(var t in e||(e={}))k.call(e,t)&&f(i,t,e[t]);if(d)for(var t of d(e))w.call(e,t)&&f(i,t,e[t]);return i},_=(i,e)=>b(i,v(e));var h=(i,e,t)=>new Promise((s,n)=>{var l=r=>{try{a(t.next(r))}catch(c){n(c)}},o=r=>{try{a(t.throw(r))}catch(c){n(c)}},a=r=>r.done?s(r.value):Promise.resolve(r.value).then(l,o);a((t=t.apply(i,e)).next())});import{a as C}from"./BasicForm.vue_vue_type_style_index_0_lang-1d0165cd.js";import"./index.js";import"./antd-bebda08e.js";import"./index-6bb63bf0.js";import"./TableImg.vue_vue_type_style_index_0_lang-c025c7d6.js";import"./uuid-31b8b5a4.js";import"./sortable.esm-4ae27e0b.js";import{g as S}from"./system-f9b163cb.js";import{P as A}from"./index-5a8cda67.js";import{_ as B}from"./DeptTree.vue_vue_type_script_setup_true_lang-fccf5a15.js";import{d as P}from"./account.data-18eb9a0b.js";import{V as R}from"./index-db323e86.js";import{d as T,f as g,r as V,Z as I,a7 as N,a8 as x,l as m,$,u as p,ab as q}from"./vue-f7f38239.js";import"./index-1b9aa1da.js";import"./useSortable-9ec2a7be.js";import"./download-2647fd8d.js";import"./base64Conver-39fc0d26.js";import"./index-b34be3ab.js";import"./IconPicker.vue_vue_type_script_setup_true_lang-af62677b.js";import"./copyTextToClipboard-ad844e61.js";import"./index-091b1c05.js";import"./useWindowSizeFn-01ffbee2.js";import"./onMountedOrActivated-a837e8fb.js";import"./useContentViewHeight-4f74c208.js";import"./index-f9546e03.js";import"./useContextMenu-b66322cd.js";const z=[{title:"用户名",field:"account",width:120},{title:"昵称",field:"nickname",width:120},{title:"邮箱",field:"email",width:120},{title:"创建时间",field:"createTime",width:180},{title:"角色",field:"role",width:200},{title:"所属部门",field:"dept",slots:{default:({row:i})=>P[i.dept]}},{title:"备注",field:"remark"},{width:160,title:"操作",align:"center",slots:{default:"action"},fixed:"right"}],F=[{field:"account",title:"用户名",itemRender:{name:"AInput"},span:6},{field:"nickname",title:"昵称",itemRender:{name:"AInput"},span:6},{span:12,align:"right",className:"!pr-0",itemRender:{name:"AButtonGroup",children:[{props:{type:"primary",content:"查询",htmlType:"submit"},attrs:{class:"mr-2"}},{props:{type:"default",htmlType:"reset",content:"重置"}}]}}],H={class:"m-4 vxebasic-form-container"},de=T({__name:"index",setup(i){const e=g(),t=g(),s=V({id:"VxeTable",keepSource:!0,columns:z,formConfig:{enabled:!0,items:F},height:"auto",proxyConfig:{ajax:{query:r=>h(this,[r],function*({page:o,form:a}){return S(_(u({page:o.currentPage,pageSize:o.pageSize},a),{searchInfo:t.value}))})}}}),n=(o="")=>{t.value=o,e.value&&e.value.commitProxy("query")},l=o=>[{label:"详情",onClick:()=>{}},{label:"编辑",onClick:()=>{}},{label:"删除",color:"error",popConfirm:{title:"是否确认删除",confirm:()=>{var r;(r=e.value)==null||r.remove(o)}}}];return(o,a)=>(I(),N(p(A),{dense:"",contentFullHeight:"",fixedHeight:"",contentClass:"flex"},{default:x(()=>[m(B,{class:"w-1/4 xl:w-1/5",onSelect:n}),$("div",H,[m(p(R),q({ref_key:"tableRef",ref:e},s),{action:x(({row:r})=>[m(p(C),{outside:"",actions:l(r)},null,8,["actions"])]),_:1},16)])]),_:1}))}});export{de as default};