var pe=Object.defineProperty,fe=Object.defineProperties;var de=Object.getOwnPropertyDescriptors;var A=Object.getOwnPropertySymbols;var ge=Object.prototype.hasOwnProperty,we=Object.prototype.propertyIsEnumerable;var Z=(r,n,t)=>n in r?pe(r,n,{enumerable:!0,configurable:!0,writable:!0,value:t}):r[n]=t,m=(r,n)=>{for(var t in n||(n={}))ge.call(n,t)&&Z(r,t,n[t]);if(A)for(var t of A(n))we.call(n,t)&&Z(r,t,n[t]);return r},L=(r,n)=>fe(r,de(n));var G=(r,n,t)=>new Promise((i,l)=>{var p=c=>{try{e(t.next(c))}catch(u){l(u)}},s=c=>{try{e(t.throw(c))}catch(u){l(u)}},e=c=>c.done?i(c.value):Promise.resolve(c.value).then(p,s);e((t=t.apply(r,n)).next())});import{h as W,f as q,p as R,G as he,q as ye,d as U,Z as me,t as Y,s as De,M as ee,w as ke}from"./index.js";import{d as V,c as w,a6 as ve,Z as d,_,F as Be,N as g,a7 as P,a8 as h,E,a0 as T,ab as S,aa as b,a1 as k,u as a,ae as te,$ as X,l as I,f as D,g as F,w as J,q as $e,ac as K,n as Ce,a9 as _e,ah as be,ai as Pe,x as se,r as oe,H as O,h as Oe}from"./vue-f7f38239.js";import{b2 as Te,ab as Se,X as Fe,e as ae,a as He}from"./antd-bebda08e.js";const{t:Q}=W(),ne={confirmLoading:{type:Boolean},showCancelBtn:{type:Boolean,default:!0},cancelButtonProps:Object,cancelText:{type:String,default:Q("common.cancelText")},showOkBtn:{type:Boolean,default:!0},okButtonProps:Object,okText:{type:String,default:Q("common.okText")},okType:{type:String,default:"primary"},showFooter:{type:Boolean},footerHeight:{type:[String,Number],default:60}},Ne=m({isDetail:{type:Boolean},title:{type:String,default:""},loadingText:{type:String},showDetailBack:{type:Boolean,default:!0},open:{type:Boolean},loading:{type:Boolean},maskClosable:{type:Boolean,default:!0},getContainer:{type:[Object,String]},closeFunc:{type:[Function,Object],default:null},destroyOnClose:{type:Boolean}},ne),Le=V({name:"BasicDrawerFooter",__name:"DrawerFooter",props:L(m({},ne),{height:{type:String,default:"60px"}}),emits:["ok","close"],setup(r,{emit:n}){const t=r,{prefixCls:i}=q("basic-drawer-footer"),l=w(()=>{const e=`${t.height}`;return{height:e,lineHeight:`calc(${e} - 1px)`}});function p(){n("ok")}function s(){n("close")}return(e,c)=>{const u=ve("a-button");return e.showFooter||e.$slots.footer?(d(),_("div",{key:0,class:k(a(i)),style:te(l.value)},[e.$slots.footer?g(e.$slots,"footer",{key:1}):(d(),_(Be,{key:0},[g(e.$slots,"insertFooter"),e.showCancelBtn?(d(),P(u,S({key:0},e.cancelButtonProps,{onClick:s,class:"mr-2"}),{default:h(()=>[E(T(e.cancelText),1)]),_:1},16)):b("",!0),g(e.$slots,"centerFooter"),e.showOkBtn?(d(),P(u,S({key:1,type:e.okType,onClick:p},e.okButtonProps,{class:"mr-2",loading:e.confirmLoading}),{default:h(()=>[E(T(e.okText),1)]),_:1},16,["type","loading"])):b("",!0),g(e.$slots,"appendFooter")],64))],6)):b("",!0)}}});const Re={key:1},Ee=V({name:"BasicDrawerHeader",__name:"DrawerHeader",props:{isDetail:R.bool,showDetailBack:R.bool,title:R.string},emits:["close"],setup(r,{emit:n}){const{prefixCls:t}=q("basic-drawer-header");function i(){n("close")}return(l,p)=>r.isDetail?(d(),_("div",{key:1,class:k([a(t),`${a(t)}--detail`])},[X("span",{class:k(`${a(t)}__twrap`)},[r.showDetailBack?(d(),_("span",{key:0,onClick:i},[I(a(Te),{class:k(`${a(t)}__back`)},null,8,["class"])])):b("",!0),r.title?(d(),_("span",Re,T(r.title),1)):b("",!0)],2),X("span",{class:k(`${a(t)}__toolbar`)},[g(l.$slots,"titleToolbar")],2)],2)):(d(),P(a(he),{key:0,class:k(a(t))},{default:h(()=>[g(l.$slots,"title"),E(" "+T(l.$slots.title?"":r.title),1)]),_:3},8,["class"]))}});const Ie=V({inheritAttrs:!1,__name:"BasicDrawer",props:Ne,emits:["open-change","ok","close","register"],setup(r,{emit:n}){const t=r,i=D(!1),l=ye(),p=D({}),{t:s}=W(),{prefixVar:e,prefixCls:c}=q("basic-drawer"),u={setDrawerProps:ce,emitOpen:void 0},y=F();y&&n("register",u,y.uid);const M=w(()=>U(t,a(p))),B=w(()=>{const o=L(m(m({placement:"right"},a(l)),a(M)),{open:a(i)});o.title=void 0;const{isDetail:f,width:$,wrapClassName:C,getContainer:N}=o;if(f){$||(o.width="100%");const z=`${c}__detail`;o.rootClassName=C?`${C} ${z}`:z,N||(o.getContainer=`.${e}-layout-content`)}return o}),re=w(()=>m(m({},l),a(B))),x=w(()=>{const{footerHeight:o,showFooter:f}=a(B);return f&&o?Se(o)?`${o}px`:`${o.replace("px","")}px`:"0px"}),le=w(()=>({position:"relative",height:`calc(100% - ${a(x)})`})),ie=w(()=>{var o;return!!((o=a(B))!=null&&o.loading)});J(()=>t.open,(o,f)=>{o!==f&&(i.value=o)},{deep:!0}),J(()=>i.value,o=>{se(()=>{n("open-change",o),y&&u.emitOpen&&u.emitOpen(o,y.uid)})});function H(o){return G(this,null,function*(){const{closeFunc:f}=a(B);if(n("close",o),f&&ae(f)){const $=yield f();i.value=!$;return}i.value=!1})}function ce(o){p.value=U(a(p),o),Reflect.has(o,"open")&&(i.value=!!o.open)}function ue(){n("ok")}return(o,f)=>{const $=$e("loading");return d(),P(a(Fe),S(re.value,{class:a(c),onClose:H}),K({default:h(()=>[Ce((d(),P(a(me),{style:te(le.value),"loading-tip":o.loadingText||a(s)("common.loadingText")},{default:h(()=>[g(o.$slots,"default")]),_:3},8,["style","loading-tip"])),[[$,ie.value]]),I(Le,S(B.value,{onClose:H,onOk:ue,height:x.value}),K({_:2},[_e(Object.keys(o.$slots),C=>({name:C,fn:h(N=>[g(o.$slots,C,be(Pe(N||{})))])}))]),1040,["height"])]),_:2},[o.$slots.title?{name:"title",fn:h(()=>[g(o.$slots,"title")]),key:"1"}:{name:"title",fn:h(()=>[I(Ee,{title:M.value.title,isDetail:o.isDetail,showDetailBack:o.showDetailBack,onClose:H},{titleToolbar:h(()=>[g(o.$slots,"titleToolbar")]),_:3},8,["title","isDetail","showDetailBack"])]),key:"0"}]),1040,["class"])}}});const v=oe({}),j=oe({});function xe(){if(!F())throw new Error("useDrawer() can only be used inside setup() or functional components!");const r=D(null),n=D(!1),t=D(0);function i(s,e){Y(()=>{r.value=null,n.value=null,v[a(t)]=null}),!(a(n)&&De()&&s===a(r))&&(t.value=e,r.value=s,n.value=!0,s.emitOpen=(c,u)=>{j[u]=c})}const l=()=>{const s=a(r);return s||ee("useDrawer instance is undefined!"),s},p={setDrawerProps:s=>{var e;(e=l())==null||e.setDrawerProps(s)},getOpen:w(()=>j[~~a(t)]),openDrawer:(s=!0,e,c=!0)=>{var y;if((y=l())==null||y.setDrawerProps({open:s}),!e)return;if(c){v[a(t)]=null,v[a(t)]=O(e);return}He(O(v[a(t)]),O(e))||(v[a(t)]=O(e))},closeDrawer:()=>{var s;(s=l())==null||s.setDrawerProps({open:!1})}};return[i,p]}const ze=r=>{const n=D(null),t=F(),i=D(0);if(!F())throw new Error("useDrawerInner() can only be used inside setup() or functional components!");const l=()=>{const s=a(n);if(!s){ee("useDrawerInner instance is undefined!");return}return s},p=(s,e)=>{Y(()=>{n.value=null}),i.value=e,n.value=s,t==null||t.emit("register",s,e)};return Oe(()=>{const s=v[a(i)];s&&(!r||!ae(r)||se(()=>{r(s)}))}),[p,{changeLoading:(s=!0)=>{var e;(e=l())==null||e.setDrawerProps({loading:s})},changeOkLoading:(s=!0)=>{var e;(e=l())==null||e.setDrawerProps({confirmLoading:s})},getOpen:w(()=>j[~~a(i)]),closeDrawer:()=>{var s;(s=l())==null||s.setDrawerProps({open:!1})},setDrawerProps:s=>{var e;(e=l())==null||e.setDrawerProps(s)}}]},Ae=ke(Ie);export{Ae as B,ze as a,xe as u};