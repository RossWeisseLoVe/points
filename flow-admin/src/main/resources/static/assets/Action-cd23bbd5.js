import{Z as d,c as f}from"./index.js";import{P as v}from"./index-5a8cda67.js";import{d as k,f as x,a6 as C,Z as m,a7 as y,a8 as e,$ as a,l,E as n,u as i,_ as B,F as g,a9 as $,a0 as b}from"./vue-f7f38239.js";import"./antd-bebda08e.js";import"./useContentViewHeight-4f74c208.js";import"./useWindowSizeFn-01ffbee2.js";import"./onMountedOrActivated-a837e8fb.js";const w={class:"my-4"},N={class:"scroll-wrap"},A={class:"p-3"},E=k({__name:"Action",setup(S){const _=x(null),p=()=>{const s=i(_);if(!s)throw new Error("scroll is Null");return s};function c(s){p().scrollTo(s)}function u(){p().scrollBottom()}return(s,t)=>{const r=C("a-button");return m(),y(i(v),{title:"滚动组件函数示例",content:"基于el-scrollbar"},{default:e(()=>[a("div",w,[l(r,{onClick:t[0]||(t[0]=o=>c(100)),class:"mr-2"},{default:e(()=>[n(" 滚动到100px位置 ")]),_:1}),l(r,{onClick:t[1]||(t[1]=o=>c(800)),class:"mr-2"},{default:e(()=>[n(" 滚动到800px位置 ")]),_:1}),l(r,{onClick:t[2]||(t[2]=o=>c(0)),class:"mr-2"},{default:e(()=>[n(" 滚动到顶部 ")]),_:1}),l(r,{onClick:t[3]||(t[3]=o=>u()),class:"mr-2"},{default:e(()=>[n(" 滚动到底部 ")]),_:1})]),a("div",N,[l(i(d),{class:"mt-4",ref_key:"scrollRef",ref:_},{default:e(()=>[a("ul",A,[(m(),B(g,null,$(100,o=>a("li",{key:o,class:"p-2",style:{border:"1px solid #eee"}},b(o),1)),64))])]),_:1},512)])]),_:1})}}});const I=f(E,[["__scopeId","data-v-05a41703"]]);export{I as default};