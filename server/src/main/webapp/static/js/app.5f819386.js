(function(t){function e(e){for(var a,r,o=e[0],u=e[1],l=e[2],m=0,d=[];m<o.length;m++)r=o[m],Object.prototype.hasOwnProperty.call(i,r)&&i[r]&&d.push(i[r][0]),i[r]=0;for(a in u)Object.prototype.hasOwnProperty.call(u,a)&&(t[a]=u[a]);c&&c(e);while(d.length)d.shift()();return s.push.apply(s,l||[]),n()}function n(){for(var t,e=0;e<s.length;e++){for(var n=s[e],a=!0,o=1;o<n.length;o++){var u=n[o];0!==i[u]&&(a=!1)}a&&(s.splice(e--,1),t=r(r.s=n[0]))}return t}var a={},i={app:0},s=[];function r(e){if(a[e])return a[e].exports;var n=a[e]={i:e,l:!1,exports:{}};return t[e].call(n.exports,n,n.exports,r),n.l=!0,n.exports}r.m=t,r.c=a,r.d=function(t,e,n){r.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:n})},r.r=function(t){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},r.t=function(t,e){if(1&e&&(t=r(t)),8&e)return t;if(4&e&&"object"===typeof t&&t&&t.__esModule)return t;var n=Object.create(null);if(r.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var a in t)r.d(n,a,function(e){return t[e]}.bind(null,a));return n},r.n=function(t){var e=t&&t.__esModule?function(){return t["default"]}:function(){return t};return r.d(e,"a",e),e},r.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},r.p="/";var o=window["webpackJsonp"]=window["webpackJsonp"]||[],u=o.push.bind(o);o.push=e,o=o.slice();for(var l=0;l<o.length;l++)e(o[l]);var c=u;s.push([0,"chunk-vendors"]),n()})({0:function(t,e,n){t.exports=n("56d7")},"034f":function(t,e,n){"use strict";var a=n("85ec"),i=n.n(a);i.a},"1bd8":function(t,e,n){},3647:function(t,e,n){},"4e7e":function(t,e,n){"use strict";var a=n("a180"),i=n.n(a);i.a},"56d7":function(t,e,n){"use strict";n.r(e);n("4de4"),n("e260"),n("e6cf"),n("cca6"),n("a79d"),n("0cdd");var a=n("2b0e"),i=n("8c4f"),s=n("5f5b");n("ab8b"),n("2dd8");a["default"].use(s["a"]);var r,o,u=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{attrs:{id:"app"}},[n("b-navbar",{attrs:{id:"bar",toggleable:"lg",type:"dark",variant:"info"}},[n("b-navbar-brand",{attrs:{href:"#"}},[t._v("Cam Guard")])],1),n("div",{attrs:{id:"main"}},[n("router-view")],1)],1)},l=[],c=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{attrs:{id:"board"}},[n("b-row",[n("TimeRange")],1),t._l(t.getWebcams,(function(e){return n("div",{key:e},[n("h1",[t._v(t._s(e))]),n("div",[n("b-card",{attrs:{"no-body":""}},[n("b-tabs",{attrs:{"content-class":"mt-3"}},[n("b-tab",{attrs:{title:"Direct",active:""}},[n("img",{staticStyle:{margin:"auto"},attrs:{src:"/api/images/direct/"+e+"?timeIndex="+t.timerIndex,width:"640"}})]),n("b-tab",{attrs:{title:"Historique"}},[n("Webcam",{attrs:{webcamName:e}})],1),n("b-tab",{attrs:{title:"Alerts"}},[n("AlertsWebcam",{attrs:{webcamName:e}})],1)],1)],1)],1)])}))],2)},m=[],d=(n("4160"),n("d81d"),n("159b"),n("ade3")),f=n("2f62"),g=n("1157"),h=n.n(g),b={post:function(t,e){return h.a.ajax({url:t,type:"POST",data:JSON.stringify(e),dataType:"json",contentType:"application/json; charset=utf-8"})},get:function(t,e){return null==e?h.a.get(t):h.a.get(t,e)},delete:function(t){return h.a.ajax({url:t,method:"DELETE"})}},p={listWebcams:function(){return b.get("/api/webcams/list")},listImages:function(t,e,n){return b.get("/api/images/list/"+t,{start:e,end:n})},listAlerts:function(t){return b.get("/api/alert/list/"+t)}};a["default"].use(f["a"]);var v="UPDATE_WEBCAMS",_="UPDATE_IMAGES_LIST",y="UPDATE_TIME_RANGE",x="UPDATE_ALERTS",w="ACTION_LOAD_WEBCAMS",O="ACTION_LOAD_IMAGES",I="ACTION_LOAD_ALERTS",D="ACTION_UPDATE_TIME_RANGE",N=new f["a"].Store({state:{webcams:[],imagesByWebcamName:{},alertsByWebcamName:{},timeRange:{start:new Date(Date.now()-6e5),end:new Date}},mutations:(r={},Object(d["a"])(r,v,(function(t,e){console.log(e),t.webcams=e})),Object(d["a"])(r,_,(function(t,e){console.log(_,e),t.imagesByWebcamName=Object.assign({},t.imagesByWebcamName,e)})),Object(d["a"])(r,y,(function(t,e){t.timeRange=e})),Object(d["a"])(r,x,(function(t,e){t.alertsByWebcamName=Object.assign({},t.alertsByWebcamName,e),console.log(t.alertsByWebcamName)})),r),actions:(o={},Object(d["a"])(o,w,(function(t){console.log(w),p.listWebcams().done((function(e){t.commit(v,e),e.forEach((function(e){t.dispatch(O,e)}))}))})),Object(d["a"])(o,O,(function(t,e){console.log(O),p.listImages(e,t.state.timeRange.start.valueOf(),t.state.timeRange.end.valueOf()).done((function(n){console.log("loading images list",n);var a={};a[e]=n.map((function(t){return"/api/images/retrieve/"+t})),t.commit(_,a)}))})),Object(d["a"])(o,D,(function(t,e){t.commit(y,e),t.state.webcams.forEach((function(e){return t.dispatch(O,e)}))})),Object(d["a"])(o,I,(function(t,e){console.log(I),p.listAlerts(e).done((function(n){n.forEach((function(t){return t.imageUrl="/api/images/retrieve/"+t.filename})),console.log("loading images list",n);var a={};a[e]=n,t.commit(x,a)}))})),o)}),M=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{attrs:{id:"webcam"}},[n("div",{staticClass:"rowSnapshot"},t._l(t.getImages,(function(e,a){return n("img",{key:e,staticClass:"images",attrs:{src:e,width:"200",title:e},on:{click:function(e){t.index=a}}})})),0),n("GalerySlideShowVue",{attrs:{images:t.getImages,index:t.index},on:{close:function(e){t.index=null}}})],1)},S=[],T=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("transition",{attrs:{name:"modal"}},[null!==t.imgIndex?n("div",{staticClass:"vgs",on:{click:t.close}},[n("button",{staticClass:"vgs__close",attrs:{type:"button"},on:{click:t.close}},[t._v(" × ")]),t.isMultiple?n("button",{staticClass:"vgs__prev",attrs:{type:"button"},on:{click:function(e){return e.stopPropagation(),t.onPrev(e)}}},[t._v(" ‹ ")]):t._e(),t.images?n("div",{staticClass:"vgs__container",on:{click:function(e){return e.stopPropagation(),t.onNext(e)}}},[n("img",{staticClass:"vgs__container__img",attrs:{src:t.imageUrl,alt:t.alt},on:{click:function(e){return e.stopPropagation(),t.onNext(e)}}}),t._t("default")],2):t._e(),t.isMultiple?n("button",{staticClass:"vgs__next",attrs:{type:"button"},on:{click:function(e){return e.stopPropagation(),t.onNext(e)}}},[t._v(" › ")]):t._e(),t.isMultiple?n("div",{ref:"gallery",staticClass:"vgs__gallery"},[t.images?n("div",{staticClass:"vgs__gallery__title"},[t._v(" "+t._s(t.imgIndex+1)+" / "+t._s(t.images.length)+" ")]):t._e(),t.images?n("div",{staticClass:"vgs__gallery__container",style:{transform:"translate("+t.galleryXPos+"px, 0)"}},t._l(t.images,(function(e,a){return n("img",{key:a,staticClass:"vgs__gallery__container__img",class:{"vgs__gallery__container__img--active":a===t.imgIndex},attrs:{src:"string"===typeof e?e:e.url,alt:"string"===typeof e?"":e.alt},on:{click:function(n){return n.stopPropagation(),t.onClickThumb(e,a)}}})})),0):t._e()]):t._e()]):t._e()])},W=[],j=(n("a9e3"),n("53ca")),E={props:{images:{type:Array,required:!0},index:{type:Number,required:!1,default:null}},data:function(){return{imgIndex:this.index,image:null,galleryXPos:0,thumbnailWidth:120}},computed:{imageUrl:function(){var t=this.images[this.imgIndex];return"string"===typeof t?t:t.url},alt:function(){var t=this.images[this.imgIndex];return"object"===Object(j["a"])(t)?t.alt:""},isMultiple:function(){return this.images.length>1}},watch:{index:function(t,e){var n=this;this.imgIndex=t,null==e&&null!=t&&this.$nextTick((function(){n.updateThumbails()}))}},mounted:function(){var t=this;window.addEventListener("keydown",(function(e){37===e.keyCode?t.onPrev():39===e.keyCode?t.onNext():27===e.keyCode&&t.close()}))},methods:{close:function(){this.imgIndex=null,this.$emit("close")},onPrev:function(){null!==this.imgIndex&&(this.imgIndex>0?this.imgIndex--:this.imgIndex=this.images.length-1,this.updateThumbails())},onNext:function(){null!==this.imgIndex&&(this.imgIndex<this.images.length-1?this.imgIndex++:this.imgIndex=0,this.updateThumbails())},onClickThumb:function(t,e){this.imgIndex=e,this.updateThumbails()},updateThumbails:function(){if(this.$refs.gallery){var t=this.$refs.gallery.clientWidth,e=this.imgIndex*this.thumbnailWidth,n=this.images.length*this.thumbnailWidth,a=Math.floor(t/(2*this.thumbnailWidth))*this.thumbnailWidth;n<t||(e<a?this.galleryXPos=0:e>this.images.length*this.thumbnailWidth-t+a?this.galleryXPos=-(this.images.length*this.thumbnailWidth-t-20):this.galleryXPos=-this.imgIndex*this.thumbnailWidth+a)}}}},$=E,k=(n("7837"),n("2877")),C=Object(k["a"])($,T,W,!1,null,null,null),A=C.exports,P={name:"Webcam",props:["webcamName"],data:function(){return{index:null}},computed:{getImagesByWebcamName:function(){return console.log(this.webcamName,this.$store.state.imagesByWebcamName),this.$store.state.imagesByWebcamName},getImages:function(){return console.log(this.webcamName,this.$store.state.imagesByWebcamName),this.$store.state.imagesByWebcamName[this.webcamName]}},components:{GalerySlideShowVue:A}},R=P,B=(n("7f9d"),Object(k["a"])(R,M,S,!1,null,"2aa821ba",null)),H=B.exports,V=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{attrs:{id:"alerts"}},[null==t.getAlerts?n("div",[t._v(" Loading... No alert done for "+t._s(t.webcamName)+" ")]):t._e(),n("div",{staticClass:"rowSnapshot"},t._l(t.getAlerts,(function(e,a){return n("img",{key:e.fileName,staticClass:"images",attrs:{src:e.imageUrl,width:"200",title:e.fileName},on:{click:function(e){t.index=a}}})})),0),n("GalerySlideShowVue",{attrs:{images:t.getUrls,index:t.index},on:{close:function(e){t.index=null}}})],1)},U=[],G={name:"AlertsWebcam",data:function(){return{index:null}},props:["webcamName"],computed:{getAlerts:function(){return this.$store.state.alertsByWebcamName[this.webcamName]},getUrls:function(){return this.$store.state.alertsByWebcamName[this.webcamName].map((function(t){return t.imageUrl}))}},mounted:function(){console.log(I),this.$store.dispatch(I,this.webcamName)},components:{GalerySlideShowVue:A}},L=G,X=Object(k["a"])(L,V,U,!1,null,"7eb181c1",null),Y=X.exports,q=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("b-form",{attrs:{inline:""}},[n("b-input",{ref:"startDate",attrs:{type:"text",value:t.getStartDate,placeholder:"Date de début(aaaa-mm-dd)"}}),t._v(" "),n("b-input",{ref:"startHours",staticClass:"hourMinuteInput",attrs:{type:"number",value:t.getStartHour,"no-wheel":"true",min:"0",max:"23"}}),t._v(" : "),n("b-input",{ref:"startMinutes",staticClass:"hourMinuteInput",attrs:{type:"number",value:t.getStartMinute,"no-wheel":"true",min:"0",max:"60"}}),t._v(" -> "),n("b-input",{ref:"endDate",attrs:{type:"text",value:t.getEndDate,placeholder:"Date de fin(aaaa-mm-dd)"}}),t._v(" "),n("b-input",{ref:"endHours",staticClass:"hourMinuteInput",attrs:{type:"number",value:t.getEndHour,"no-wheel":"true",min:"0",max:"23"}}),t._v(" : "),n("b-input",{ref:"endMinutes",staticClass:"hourMinuteInput",attrs:{type:"number",value:t.getEndMinute,"no-wheel":"true",min:"0",max:"60"}}),n("b-button",{attrs:{variant:"success"},on:{click:t.updateRange}},[t._v("Ok")])],1)},J=[],F=(n("ac1f"),n("1276"),n("d4ec")),z=n("bee2"),K=function(){function t(){Object(F["a"])(this,t)}return Object(z["a"])(t,[{key:"convertInputValue",value:function(t){var e=(""+t).split("-");return new Date(e[0],e[1]-1,e[2])}},{key:"addDay",value:function(t,e){var n=new Date(t.valueOf());return n.setDate(n.getDate()+e),n}},{key:"sameDay",value:function(t,e){return null!=t&&null!=e&&(t.getDate()==e.getDate()&&t.getMonth()==e.getMonth()&&t.getYear()==e.getYear())}},{key:"removeTime",value:function(t){var e=new Date(t.valueOf());return e.setHours(0),e.setMinutes(0),e.setSeconds(0),e.setMilliseconds(0),e}},{key:"build7DayBefore",value:function(t){var e=this.removeTime(t),n=[],a=this.addDay(e,-6);a=this.removeTime(a);while(a.valueOf()<=e.valueOf())n.push(a),a=this.addDay(a,1);return n}},{key:"filterByDay",value:function(t,e,n){var a=this;return null==e?[]:e.filter((function(e){return a.sameDay(t,n(e))}))}},{key:"formatDateISO",value:function(t){var e=t.getMonth()+1,n=t.getDate();return t.getFullYear()+"-"+(e<10?"0":"")+e+"-"+(n<10?"0":"")+n}}]),t}(),Q=new K,Z=Q,tt={name:"TimeRange",computed:{getStartDate:function(){return Z.formatDateISO(this.$store.state.timeRange.start)},getStartHour:function(){return this.$store.state.timeRange.start.getHours()},getStartMinute:function(){return this.$store.state.timeRange.start.getMinutes()},getEndDate:function(){return Z.formatDateISO(this.$store.state.timeRange.end)},getEndHour:function(){return this.$store.state.timeRange.end.getHours()},getEndMinute:function(){return this.$store.state.timeRange.end.getMinutes()}},methods:{updateRange:function(){var t=Z.convertInputValue(this.$refs.startDate.localValue);t.setHours(this.$refs.startHours.localValue),t.setMinutes(this.$refs.startMinutes.localValue);var e=Z.convertInputValue(this.$refs.endDate.localValue);e.setHours(this.$refs.endHours.localValue),e.setMinutes(this.$refs.endMinutes.localValue),this.$store.dispatch(D,{start:t,end:e})}}},et=tt,nt=(n("4e7e"),Object(k["a"])(et,q,J,!1,null,"d75e3fa8",null)),at=nt.exports,it={name:"Main",data:function(){return{timerIndex:Date.now()}},mounted:function(){var t=this;this.interval=setInterval((function(){t.timerIndex=Date.now()}),1e3)},created:function(){this.$store.dispatch(w)},computed:{getWebcams:function(){return this.$store.state.webcams}},components:{Webcam:H,TimeRange:at,AlertsWebcam:Y}},st=it,rt=(n("c515"),Object(k["a"])(st,c,m,!1,null,"6e4b7cb8",null)),ot=rt.exports,ut=[{path:"/",component:ot}],lt=new i["a"]({routes:ut}),ct={name:"app",created:function(){},computed:Object(f["b"])(["stateConnected"]),router:lt},mt=ct,dt=(n("034f"),Object(k["a"])(mt,u,l,!1,null,null,null)),ft=dt.exports;a["default"].use(i["a"]),a["default"].config.productionTip=!1,a["default"].prototype.$store=N,a["default"].filter("formatDate",(function(t){if(t)return t.getDate()+"/"+t.getMonth()+"/"+t.getYear()})),new a["default"]({render:function(t){return t(ft)}}).$mount("#app")},7837:function(t,e,n){"use strict";var a=n("e1dd"),i=n.n(a);i.a},"7f9d":function(t,e,n){"use strict";var a=n("1bd8"),i=n.n(a);i.a},"85ec":function(t,e,n){},a180:function(t,e,n){},c515:function(t,e,n){"use strict";var a=n("3647"),i=n.n(a);i.a},e1dd:function(t,e,n){}});
//# sourceMappingURL=app.5f819386.js.map