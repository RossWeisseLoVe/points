var C=(e,c,l)=>new Promise((f,d)=>{var g=n=>{try{r(l.next(n))}catch(o){d(o)}},m=n=>{try{r(l.throw(n))}catch(o){d(o)}},r=n=>n.done?f(n.value):Promise.resolve(n.value).then(g,m);r((l=l.apply(e,c)).next())});import{_ as y}from"./BasicForm.vue_vue_type_script_setup_true_lang-b5fd2c2d.js";import"./BasicForm.vue_vue_type_style_index_0_lang-1d0165cd.js";import{u as F}from"./useForm-d2875f1c.js";import{C as U,l as A,a as E,c as w}from"./index.js";import{C as L}from"./index-091b1c05.js";import{h as k}from"./header-55b09394.js";import{a as P}from"./account-57e5abfe.js";import{b as S}from"./data-23d455fd.js";import{u as M}from"./upload-94aa82ef.js";import{au as $,at as I,aH as O,B as V,U as R,V as D}from"./antd-bebda08e.js";import{d as G,f as J,o as N,c as x,a6 as i,Z as _,a7 as j,a8 as t,l as a,$ as s,_ as v,E as z}from"./vue-f7f38239.js";import{_ as B}from"./tooltip-app-name-0d16fdaa.js";import"./index-6bb63bf0.js";import"./useWindowSizeFn-01ffbee2.js";import"./index-1b9aa1da.js";import"./uuid-31b8b5a4.js";import"./useSortable-9ec2a7be.js";import"./download-2647fd8d.js";import"./base64Conver-39fc0d26.js";import"./index-b34be3ab.js";import"./IconPicker.vue_vue_type_script_setup_true_lang-af62677b.js";import"./copyTextToClipboard-ad844e61.js";const H=G({components:{BasicForm:y,PlusOutlined:$,Upload:I,Popover:O,CollapseContainer:U,Button:V,ARow:R,ACol:D,CropperAvatar:L},setup(){const{createMessage:e}=E(),c=A(),l=J(),[f,{setFieldsValue:d}]=F({labelWidth:120,schemas:S,showActionButtonGroup:!1});N(()=>C(this,null,function*(){const o=yield P();d(o)}));const g=x(()=>{const{avatar:o}=c.getUserInfo;return o||k});function m(o){const p=c.getUserInfo;p.avatar=o,c.setUserInfo(p)}const r=(o,p)=>{const u=new FileReader;u.addEventListener("load",()=>p(u.result)),u.readAsDataURL(o)};return{avatar:g,register:f,beforeUpload:o=>o.type==="image/jpeg"||o.type==="image/png"?o.size/1024/1024<2?(r(o,h=>{imageUrl.value=h}),!1):(e.error("图片不能大于2MB！"),!1):(e.error("只允许上传JPG图片！"),!1),appLogo:l,uploadApi:M,updateAvatar:m,handleSubmit:()=>{e.success("更新成功！")}}}});const T=s("div",{class:"tooltip-popover-content"},[s("img",{src:B})],-1),W=s("div",{class:"mb-2 mt-4"},"项目名称",-1),Z=s("div",{class:"tooltip-popover-content"},[s("img",{src:B})],-1),q={class:"change-logo"},K=s("div",{class:"mb-2 mt-4"},"站点图标",-1),Q=["src"],X={key:1},Y=s("div",{class:"ant-upload-text"},"上传",-1),oo=s("div",{class:"tooltip-popover-content"},[s("img",{src:B})],-1),to={class:"change-logo"},eo=s("div",{class:"mb-2 mt-4"},"LOGO",-1),ao=["src"],so={key:1},no=s("div",{class:"ant-upload-text"},"上传",-1);function ro(e,c,l,f,d,g){const m=i("a-input"),r=i("a-col"),n=i("Popover"),o=i("plus-outlined"),p=i("Upload"),u=i("a-row"),h=i("Button"),b=i("CollapseContainer");return _(),j(b,{title:"基本设置",canExpan:!1},{default:t(()=>[a(u,{gutter:24},{default:t(()=>[a(n,{placement:"right"},{content:t(()=>[T]),default:t(()=>[a(r,{span:13},{default:t(()=>[W,a(m)]),_:1})]),_:1}),a(n,{placement:"right"},{content:t(()=>[Z]),default:t(()=>[a(r,{span:13},{default:t(()=>[s("div",q,[K,a(p,{style:{margin:"auto"},name:"avatar","list-type":"picture-card",class:"favicon-uploader","show-upload-list":!1,"before-upload":e.beforeUpload,multiple:!1},{default:t(()=>[e.appLogo?(_(),v("img",{key:0,src:e.appLogo,alt:"avatar"},null,8,Q)):(_(),v("div",X,[a(o),Y]))]),_:1},8,["before-upload"])])]),_:1})]),_:1}),a(n,{placement:"right"},{content:t(()=>[oo]),default:t(()=>[a(r,{span:13},{default:t(()=>[s("div",to,[eo,a(p,{style:{margin:"auto"},name:"avatar","list-type":"picture-card",class:"logo-uploader","show-upload-list":!1,"before-upload":e.beforeUpload,multiple:!1},{default:t(()=>[e.appLogo?(_(),v("img",{key:0,src:e.appLogo,alt:"avatar"},null,8,ao)):(_(),v("div",so,[a(o),no]))]),_:1},8,["before-upload"])])]),_:1})]),_:1})]),_:1}),a(h,{type:"primary",onClick:e.handleSubmit},{default:t(()=>[z(" 更新基本信息 ")]),_:1},8,["onClick"])]),_:1})}const So=w(H,[["render",ro]]);export{So as default};