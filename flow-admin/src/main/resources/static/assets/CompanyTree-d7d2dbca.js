var f=(o,c,t)=>new Promise((n,s)=>{var l=e=>{try{a(t.next(e))}catch(i){s(i)}},r=e=>{try{a(t.throw(e))}catch(i){s(i)}},a=e=>e.done?n(e.value):Promise.resolve(e.value).then(l,r);a((t=t.apply(o,c)).next())});import{_ as u}from"./index-f9546e03.js";import{g as _}from"./company-7689c9fc.js";import{bF as h,c as v}from"./index.js";import{G as T}from"./antd-bebda08e.js";import{d as g,f as d,o as y,x as B,u as m,a6 as C,q as D,n as b,Z as k,_ as S,l as $}from"./vue-f7f38239.js";import"./useContextMenu-b66322cd.js";const w=g({name:"CompanyTree",components:{BasicTree:u,Spin:T},emits:["select"],setup(o,{emit:c}){const t=d([]),n=d(!1),s=d(null);function l(){return f(this,null,function*(){n.value=!0,_().then(a=>{t.value=a,B(()=>{var e;m(t).length<10&&((e=m(s))==null||e.filterByLevel(1))})}).finally(()=>{n.value=!1})})}function r(a,e){const i=h(t.value,p=>p.id===a[0],{id:"id",pid:"pid",children:"children"});c("select",i)}return y(()=>{l()}),{treeData:t,treeLoading:n,basicTreeRef:s,handleSelect:r}}}),x={class:"company-tree bg-white m-4 mr-0 overflow-hidden"};function F(o,c,t,n,s,l){const r=C("BasicTree"),a=D("loading");return b((k(),S("div",x,[$(r,{title:"公司",toolbar:"",search:"",clickRowToExpand:!1,treeData:o.treeData,replaceFields:{key:"id",title:"shortName"},onSelect:o.handleSelect,ref:"basicTreeRef"},null,8,["treeData","onSelect"])])),[[a,o.treeLoading]])}const V=v(w,[["render",F]]);export{V as default};