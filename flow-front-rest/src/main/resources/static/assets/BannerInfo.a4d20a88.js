import{a as m,b1 as _,cV as h,cW as f,L as g,aF as s,o as a,i as y,z as o,m as n,l as r,f as i,F as C,aI as v,b0 as b,t as w,aG as $}from"./index.2b16d9a1.js";import{C as l}from"./index.9a548ac5.js";import"./index.d0d8cdf7.js";import"./index.6d6145ef.js";import{C as k}from"./index.fcef8555.js";import"./index.027abab7.js";import"./index.6149f0c4.js";import"./useRefs.c375bc6f.js";import"./PlusOutlined.097c7aec.js";import"./useFlexGapSupport.5b5e1140.js";const x=m({props:{dataSource:Array,height:{type:Number,default:200}},components:{Card:l,CardGrid:l.Grid,Icon:_,Carousel:k,LeftOutlined:h,RightOutlined:f},setup(e){return{items:e.dataSource,height:e.height}}}),B={class:"custom-slick-arrow",style:{left:"0","z-index":"8"}},O={class:"custom-slick-arrow",style:{right:"0"}},L=["title"],S={class:"banner-text"};function I(e,z,A,F,G,N){const c=s("LeftOutlined"),d=s("RightOutlined"),u=s("Carousel"),p=s("Card");return a(),y(p,$({class:"banner-box",title:"",style:`height:${e.height}px;`,"body-style":"padding:0;"},e.$attrs),{default:o(()=>[n(u,{arrows:"",dots:!1,class:"!h-full !w-full"},{prevArrow:o(()=>[r("div",B,[n(c)])]),nextArrow:o(()=>[r("div",O,[n(d)])]),default:o(()=>[(a(!0),i(C,null,v(e.items,t=>(a(),i("div",{class:"!w-full carousel-item",key:t.id,title:t.title},[r("div",{class:"banner",style:b(`height: ${e.height}px; background-image: url('${t.imgSrc}')`)},[r("div",S,w(t.title),1)],4)],8,L))),128))]),_:1})]),_:1},16,["style"])}var K=g(x,[["render",I]]);export{K as default};