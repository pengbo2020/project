
var isReady=false;var onReadyCallbacks=[];
var __uniConfig = {"pages":["pages/index/index","pages/index/index2","pages/exam/index","pages/exam/answers","pages/exam/modify","pages/exam/downloadfile","pages/note/note","pages/note/test","pages/exam/start","pages/user/user","pages/user/history","pages/user/wrongQuestions","pages/demo/basic/button","pages/demo/basic/input","pages/demo/basic/image","pages/demo/basic/icon","pages/demo/basic/tag","pages/demo/basic/divider","pages/demo/feedback/toast","pages/demo/feedback/message","pages/demo/feedback/loading","pages/demo/form/radio","pages/demo/form/switch","pages/demo/form/checkbox","pages/demo/form/select","pages/demo/form/upload","pages/demo/form/rate","pages/demo/form/rules","pages/demo/operate/loadmore","pages/demo/operate/modal","pages/demo/operate/action-sheet","pages/demo/operate/search","pages/demo/operate/dropdown","pages/demo/nav/tabs","pages/demo/nav/topbar","pages/demo/layout/flex","pages/demo/layout/grid","pages/demo/layout/waterfall","pages/demo/layout/list","pages/demo/view/popup","pages/demo/view/swiper","pages/demo/view/badge","pages/demo/view/avatar","pages/demo/view/slider","pages/demo/view/progress","pages/demo/view/noticebar","pages/demo/view/countdown","pages/demo/advanced/list-index"],"window":{"navigationBarTextStyle":"black","navigationBarTitleText":"cool-uni","navigationBarBackgroundColor":"#fff"},"renderer":"auto","splashscreen":{"alwaysShowBeforeRender":true,"autoclose":false},"appname":"COOL-UNI","compilerVersion":"2.6.16","entryPagePath":"pages/index/index","networkTimeout":{"request":60000,"connectSocket":60000,"uploadFile":60000,"downloadFile":60000}};
var __uniRoutes = [{"path":"/pages/index/index","meta":{"isQuit":true},"window":{"titleNView":false}},{"path":"/pages/index/index2","meta":{},"window":{"titleNView":false}},{"path":"/pages/exam/index","meta":{},"window":{"navigationBarTitleText":"组卷"}},{"path":"/pages/exam/answers","meta":{},"window":{}},{"path":"/pages/exam/modify","meta":{},"window":{}},{"path":"/pages/exam/downloadfile","meta":{},"window":{"navigationBarTitleText":"缓存文件"}},{"path":"/pages/note/note","meta":{},"window":{}},{"path":"/pages/note/test","meta":{},"window":{}},{"path":"/pages/exam/start","meta":{},"window":{}},{"path":"/pages/user/user","meta":{},"window":{}},{"path":"/pages/user/history","meta":{},"window":{}},{"path":"/pages/user/wrongQuestions","meta":{},"window":{}},{"path":"/pages/demo/basic/button","meta":{},"window":{"navigationBarTitleText":"Button 按钮"}},{"path":"/pages/demo/basic/input","meta":{},"window":{"navigationBarTitleText":"Input 输入框"}},{"path":"/pages/demo/basic/image","meta":{},"window":{"navigationBarTitleText":"Image 图片"}},{"path":"/pages/demo/basic/icon","meta":{},"window":{"navigationBarTitleText":"Icon 图标"}},{"path":"/pages/demo/basic/tag","meta":{},"window":{"navigationBarTitleText":"Tag 标签"}},{"path":"/pages/demo/basic/divider","meta":{},"window":{"navigationBarTitleText":"Divider 分割线"}},{"path":"/pages/demo/feedback/toast","meta":{},"window":{"navigationBarTitleText":"Toast 提示"}},{"path":"/pages/demo/feedback/message","meta":{},"window":{"navigationBarTitleText":"Message 消息提醒"}},{"path":"/pages/demo/feedback/loading","meta":{},"window":{"navigationBarTitleText":"Loading 加载"}},{"path":"/pages/demo/form/radio","meta":{},"window":{"navigationBarTitleText":"Radio 单选框"}},{"path":"/pages/demo/form/switch","meta":{},"window":{"navigationBarTitleText":"Switch 开关"}},{"path":"/pages/demo/form/checkbox","meta":{},"window":{"navigationBarTitleText":"Checkbox 多选框"}},{"path":"/pages/demo/form/select","meta":{},"window":{"navigationBarTitleText":"Select 下拉框"}},{"path":"/pages/demo/form/upload","meta":{},"window":{"navigationBarTitleText":"Upload 上传"}},{"path":"/pages/demo/form/rate","meta":{},"window":{"navigationBarTitleText":"Rate 评分"}},{"path":"/pages/demo/form/rules","meta":{},"window":{"navigationBarTitleText":"Rules 校验"}},{"path":"/pages/demo/operate/loadmore","meta":{},"window":{"navigationBarTitleText":"LoadMore 页底提示"}},{"path":"/pages/demo/operate/modal","meta":{},"window":{"navigationBarTitleText":"Modal 模态框"}},{"path":"/pages/demo/operate/action-sheet","meta":{},"window":{"navigationBarTitleText":"ActionSheet 底部菜单"}},{"path":"/pages/demo/operate/search","meta":{},"window":{"navigationBarTitleText":"Search 搜索"}},{"path":"/pages/demo/operate/dropdown","meta":{},"window":{"navigationBarTitleText":"Dropdown 下拉框"}},{"path":"/pages/demo/nav/tabs","meta":{},"window":{"navigationBarTitleText":"Tabs 选项卡"}},{"path":"/pages/demo/nav/topbar","meta":{},"window":{"navigationBarTitleText":"Topbar 顶部导航"}},{"path":"/pages/demo/layout/flex","meta":{},"window":{"navigationBarTitleText":"Flex 布局"}},{"path":"/pages/demo/layout/grid","meta":{},"window":{"navigationBarTitleText":"Grid 宫格"}},{"path":"/pages/demo/layout/waterfall","meta":{},"window":{"navigationBarTitleText":"Waterfall 瀑布流"}},{"path":"/pages/demo/layout/list","meta":{},"window":{"navigationBarTitleText":"List 列表"}},{"path":"/pages/demo/view/popup","meta":{},"window":{"navigationBarTitleText":"Popup 弹出层"}},{"path":"/pages/demo/view/swiper","meta":{},"window":{"navigationBarTitleText":"Swiper 滑块视图"}},{"path":"/pages/demo/view/badge","meta":{},"window":{"navigationBarTitleText":"Badge 标记"}},{"path":"/pages/demo/view/avatar","meta":{},"window":{"navigationBarTitleText":"Avatar 头像"}},{"path":"/pages/demo/view/slider","meta":{},"window":{"navigationBarTitleText":"Slider 滑块"}},{"path":"/pages/demo/view/progress","meta":{},"window":{"navigationBarTitleText":"Progress 进度条"}},{"path":"/pages/demo/view/noticebar","meta":{},"window":{"navigationBarTitleText":"Noticebar 通知栏"}},{"path":"/pages/demo/view/countdown","meta":{},"window":{"navigationBarTitleText":"Countdown 倒计时"}},{"path":"/pages/demo/advanced/list-index","meta":{},"window":{"navigationBarTitleText":"List-Index 索引列表"}}];
__uniConfig.onReady=function(callback){if(__uniConfig.ready){callback()}else{onReadyCallbacks.push(callback)}};Object.defineProperty(__uniConfig,"ready",{get:function(){return isReady},set:function(val){isReady=val;if(!isReady){return}const callbacks=onReadyCallbacks.slice(0);onReadyCallbacks.length=0;callbacks.forEach(function(callback){callback()})}});
service.register("uni-app-config",{create(a,b,c){if(!__uniConfig.viewport){var d=b.weex.config.env.scale,e=b.weex.config.env.deviceWidth,f=Math.ceil(e/d);Object.assign(__uniConfig,{viewport:f,defaultFontSize:Math.round(f/20)})}return{instance:{__uniConfig:__uniConfig,__uniRoutes:__uniRoutes,global:void 0,window:void 0,document:void 0,frames:void 0,self:void 0,location:void 0,navigator:void 0,localStorage:void 0,history:void 0,Caches:void 0,screen:void 0,alert:void 0,confirm:void 0,prompt:void 0,fetch:void 0,XMLHttpRequest:void 0,WebSocket:void 0,webkit:void 0,print:void 0}}}});
