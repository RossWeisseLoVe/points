import{M as _,a as h}from"./index-2e239d81.js";import{P as v}from"./index-5a8cda67.js";import{R as k}from"./antd-bebda08e.js";import{d as w,f as s,a6 as g,Z as R,a7 as b,a8 as r,$ as u,l as a,E as i,u as o}from"./vue-f7f38239.js";import"./index.js";import"./index-6bb63bf0.js";import"./useWindowSizeFn-01ffbee2.js";import"./onMountedOrActivated-a837e8fb.js";import"./useContentViewHeight-4f74c208.js";const C={class:"mt-2"},T=w({__name:"index",setup(D){const l=s(null),e=s(`
# 标题h1

##### 标题h5

**加粗**
*斜体*
~~删除线~~
[链接](https://github.com/vbenjs/vue-vben-admin)
↓分割线↓

---


* 无序列表1
  * 无序列表1.1

1. 有序列表1
2. 有序列表2

* [ ] 任务列表1
* [x] 任务列表2

> 引用示例

\`\`\`js
// 代码块:
(() => {
  var htmlRoot = document.getElementById('htmlRoot');
  var theme = window.localStorage.getItem('__APP__DARK__MODE__');
  if (htmlRoot && theme) {
    htmlRoot.setAttribute('data-theme', theme);
    theme = htmlRoot = null;
  }
})();
\`\`\`

| 表格 | 示例 | 🎉️ |
| --- | --- | --- |
| 1 | 2 | 3 |
| 4 | 5 | 6 |
`);function d(){const t=o(l);if(!t)return;t.getVditor().setTheme("dark","dark","dracula")}function c(t){e.value=t}function f(){e.value=""}return(t,n)=>{const m=g("a-button");return R(),b(o(v),{title:"MarkDown组件示例"},{default:r(()=>[u("div",null,[a(m,{onClick:d,class:"mb-2",type:"primary"},{default:r(()=>[i(" 黑暗主题 ")]),_:1}),a(m,{onClick:f,class:"mb-2",type:"default"},{default:r(()=>[i(" 清空内容 ")]),_:1}),a(o(_),{value:e.value,"onUpdate:value":n[0]||(n[0]=p=>e.value=p),onChange:c,ref_key:"markDownRef",ref:l,placeholder:"这是占位文本"},null,8,["value"])]),u("div",C,[a(o(k),{title:"Markdown Viewer 组件演示"},{default:r(()=>[a(o(h),{value:e.value},null,8,["value"])]),_:1})])]),_:1})}}});export{T as default};
