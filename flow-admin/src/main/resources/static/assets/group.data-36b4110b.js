import{y as a}from"./index.js";import{m as t}from"./vue-f7f38239.js";import{Q as o}from"./antd-bebda08e.js";const c=[{title:"名称",dataIndex:"name",align:"left"},{title:"标识",dataIndex:"sn",width:180,align:"left"},{title:"用户",dataIndex:"users",align:"left",customRender:({record:e})=>{const s=e.users,n="green";return s&&s.map(l=>t(o,{color:n,style:{marginRight:"5px"}},()=>l.realName))||[]}},{title:"备注",dataIndex:"note",align:"left"}],u=[{title:"菜单名称",dataIndex:"name",align:"left",width:150,customRender:({record:e})=>t("span",{title:e.name+"["+e.sn+"]"},[t(a,{icon:e.image}),t("span",e.name)])},{dataIndex:"pvs",align:"left",slots:{customRender:"pvs",title:"customTitle"}}],d=[{field:"keyword",label:"关键字",component:"Input",componentProps:{placeholder:"请输入名称/标识"},labelWidth:60,colProps:{span:6,lg:{span:6,offset:0},sm:{span:10,offset:0},xs:{span:16,offset:0}}}],f=[{field:"id",label:"ID",component:"Input",show:!1},{field:"name",label:"名称",required:!0,component:"Input",rules:[{required:!0,whitespace:!0,message:"名称不能为空！"},{max:64,message:"字符长度不能大于64！"}],colProps:{span:24}},{field:"sn",label:"标识",required:!0,component:"Input",colProps:{span:24}},{label:"备注",field:"note",component:"InputTextArea",rules:[{max:1024,message:"字符长度不能大于1024！"}],colProps:{span:24}}],I=[{field:"id",label:"ID",component:"Input",show:!1},{label:"选择用户",field:"users",slot:"users",component:"Input",labelWidth:65}],h=[{field:"id",label:"ID",component:"Input",show:!1},{field:"sn",label:"ID",required:!0,component:"Input",show:!1},{label:"",labelWidth:"0",field:"acls",slot:"acls",component:"Input",colProps:{span:24}}];export{h as a,u as b,c,d,f,I as s};