import{L as f,a as _,v as u,aF as r,o as g,i as I,z as y,l as t,m as e}from"./index.2b16d9a1.js";import{C}from"./index.9a548ac5.js";import"./index.d0d8cdf7.js";import"./index.6d6145ef.js";import{P as v}from"./index.a877f91e.js";import P from"./CommonProcess.5368f3ac.js";import w from"./ProjectCard.fb154f19.js";import h from"./PerformanceRecord.b99dccb2.js";import R from"./AttendanceRecord.8b24246d.js";import B from"./QuickNav.f2bc903e.js";import N from"./DynamicInfo.24facc7c.js";import S from"./ProcessCard.d353c447.js";import T from"./RecruitTrainCard.2a8379af.js";import $ from"./SaleRadar.ea108acd.js";import{d as b}from"./data.a70e8699.js";import k from"./BannerInfo.a4d20a88.js";import A from"./UserInfo.c96634f7.js";import U from"./NoticeInfo.591b3a2e.js";import{bannerList as W,noticeInfoItems as L}from"./data.6469c590.js";import"./index.027abab7.js";import"./index.6149f0c4.js";import"./useRefs.c375bc6f.js";import"./PlusOutlined.097c7aec.js";import"./useFlexGapSupport.5b5e1140.js";import"./index.e4a19c05.js";import"./index.0da486db.js";import"./useSize.a2b98417.js";import"./eagerComputed.bb8c5d26.js";import"./useWindowSizeFn.ed3cc4b0.js";import"./useContentViewHeight.0617d761.js";import"./ArrowLeftOutlined.cb29a710.js";import"./index.8fed1b97.js";import"./transButton.25002429.js";import"./index.3f55bd74.js";import"./header.d801b988.js";import"./useECharts.e8fd6fe7.js";import"./index.2958f281.js";import"./TableImg.0ca8f48a.js";import"./useForm.449ab0d8.js";import"./index.0e6c0c26.js";import"./_baseIteratee.f482732d.js";import"./get.9f6eebb7.js";import"./index.dc7ada20.js";import"./index.11e06de4.js";import"./index.beefe435.js";import"./index.e956cba1.js";import"./index.29c63fac.js";import"./index.a2713d3d.js";import"./index.e7eaeed7.js";import"./FullscreenOutlined.acb862b9.js";import"./download.33788f11.js";import"./index.d96f1b73.js";import"./ClockCircleOutlined.2fc96921.js";import"./uniqBy.d1c21867.js";import"./sortable.esm.2632adaa.js";import"./RedoOutlined.d8bbda42.js";import"./scrollTo.1200d453.js";import"./index.c3a748d0.js";import"./useTable.e99618b3.js";import"./process.32df2958.js";import"./index.fcef8555.js";const V=_({components:{PageWrapper:v,CommonProcess:P,ProjectCard:w,QuickNav:B,DynamicInfo:N,ProcessCard:S,RecruitTrainCard:T,BannerInfo:k,UserInfo:A,NoticeInfo:U,SaleRadar:$,Card:C,PerformanceRecord:h,AttendanceRecord:R},setup(){const o=u(!0);return setTimeout(()=>{o.value=!1},1500),{loading:o,items:b,bannerInfoItems:W,noticeInfoItems:L}}}),j={class:"flex w-full enter-y"},z={class:"w-1/4 mr-4"},D={class:"w-1/2"},F={class:"w-1/4 pl-4"},Q={class:"md:flex enter-y mt-4"},q={class:"lg:flex !mt-4"},x={class:"lg:w-5/10 w-full !mr-4 enter-y"},E={class:"lg:w-5/10 w-full enter-y"};function G(o,H,J,K,M,O){const n=r("UserInfo"),i=r("BannerInfo"),m=r("NoticeInfo"),a=r("CommonProcess"),s=r("AttendanceRecord"),p=r("PerformanceRecord"),c=r("ProcessCard"),l=r("RecruitTrainCard"),d=r("PageWrapper");return g(),I(d,null,{default:y(()=>[t("div",j,[t("div",z,[e(n,{class:"!my-4 enter-y"})]),t("div",D,[e(i,{dataSource:o.bannerInfoItems,height:"264",class:"!my-4 enter-y",loading:o.loading},null,8,["dataSource","loading"])]),t("div",F,[e(m,{dataSource:o.noticeInfoItems,loading:o.loading,class:"!my-4 enter-y"},null,8,["dataSource","loading"])])]),t("div",null,[e(a)]),t("div",Q,[e(s,{class:"lg:w-5/10 w-full !mr-4 enter-y",loading:o.loading},null,8,["loading"]),e(p,{class:"lg:w-5/10 w-full enter-y",loading:o.loading},null,8,["loading"])]),t("div",q,[t("div",x,[e(c,{loading:o.loading,class:"enter-y"},null,8,["loading"])]),t("div",E,[e(l,{loading:o.loading,class:"enter-y"},null,8,["loading"])])])]),_:1})}var ir=f(V,[["render",G]]);export{ir as default};