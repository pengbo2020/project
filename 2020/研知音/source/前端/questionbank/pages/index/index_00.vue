<template>
	<view class="page_edu">
		<view id="top-box" :class="flag == true ? 'cu-bar bg-white ' : 'cu-bar bg-white'">
			<!-- <block v-if="hasUserInfo === true">
				
			</block> -->
			<view class="action text-black" v-if="hasUserInfo === true">
				
				<cl-avatar-group :urls="avatarUrl" style="margin: 0 20rpx;" @tap="modalCard = true" data-target="modalCard"></cl-avatar-group>
				<text style="font-size:small">{{ userInfo.nickName || userInfo.email }}</text>
			</view>
			<view class="action text-black" v-if="hasUserInfo === false">
				
				<cl-avatar :size="70" style="margin-right:20rpx;" @tap="modalCard = true" data-target="modalCard"></cl-avatar>
				
				<text style="font-size:small">未授权</text>
			</view>

			<view class="action" v-if="hasUserInfo === true">
				<view class="action" @tap="visible = true" data-target="modalCard" style="margin-right: 10rpx;">
					<view class="cuIcon-cu-image">
						<cl-icon :name="visible == false ? 'cl-icon-plus' : 'cl-icon-minus'" :size="45"></cl-icon>
					</view>
				</view>
			</view>
			<view class="action" v-if="hasUserInfo === false">
				<view class="action" data-target="modalCard" style="margin-right:0rpx;">
					<view class="cuIcon-cu-image">
						
						<button class="mini-btn" type="default" size="mini" open-type="getUserInfo" @getuserinfo="getUserInfo">登录</button>
	
					</view>
				</view>
			</view>
		</view>

		<view class="content" style="margin-left: 10px;margin-right: 10px;">
			<uni-swiper-dot :info="info" :current="current" :mode="mode" :dots-styles="dotsStyles" field="content" style="border-radius: 10rpx;">
				<swiper class="swiper-box" @change="change" autoplay="true">
					<swiper-item v-for="(item, index) in info" :key="index" style="border-radius: 10rpx;">
						<!-- <view :class="item.colorClass" class="swiper-item">
							<image class="image" :src="item.url" mode="aspectFill" />
						</view> -->
						<image class="image" :src="item.url" mode="aspectFill" />
					</swiper-item>
				</swiper>
			</uni-swiper-dot>
		</view>

		<!-- <view class="page_edu_header" style="height: 30%;">
			<view class="header" >
				<image src="/static/icon_main.png" class="btn" @tap="visible = true"></image> 
				 <view class="input">
					<image src="/static/search.png" class="search"></image>
					<input type="text" value="" placeholder="搜索" />
				</view>
				<image src="/static/msg.png" class="btn"></image>
			</view>

			<view class="header_content" >
				<view class="left"><text>恰同学少年，风华正茂；书生意气，挥斥方遒。指点江山，激扬文字，粪土当年万户侯。曾记否，到中流击水，浪遏飞舟。</text></view>
				<view><image src="/static/right.png" style="width: 131px;height: 122px;"></image></view>
				
			</view>
			
			
			
			
		
		</view> -->
		<view class="page_content" style="margin-top: auto;">
			<!-- <view class="menu">
				<template v-for="(it, i) in menus">
					<view class="item" :key="'menu_' + i">
						<view class="img_view" :style="{ background: it.bg }" @click="exam(i)"><image :src="it.icon" class="image"></image></view>
						<text class="txt">{{ it.txt }}</text>
					</view>
				</template>
			</view> -->
			<view class="demo-icon" style="height: 120rpx;">
				<cl-card>
					<cl-row>
						<cl-col span="6" v-for="(item, index) in menulist" :key="index">
							<view class="block" @click="exam(index)">
								<!-- <cl-icon :name="item.value" :size="44"></cl-icon> -->
								<cl-avatar :size="60">{{ item.value }}</cl-avatar>
								<text class="label">{{ item.label }}</text>
							</view>
						</cl-col>
					</cl-row>
				</cl-card>
			</view>
			<!-- <view class="s_menu">
				<template v-for="(it,i) in second_menus">
					<view class="item" :key="'s_menu_'+i">
						<image :src="it.icon" class="image"></image>
						<text class="txt">{{it.txt}}</text>
					</view>
				</template>
			</view> -->
			<!-- <view class="ad">
				<view class="ad_btn">
					<text class="title">教资面试课上线</text>
					<text class="sub_title">老用户现实立减100元</text>
				</view>
				<image src="/static/tag.png" class="bg"></image>
			</view> -->
			<!-- <view style="margin-top: 40rpx;">
				<text>	hello</text>
			</view> -->
			<view class="action text-black" style="margin-top: 60rpx;margin-left: 30rpx;"></view>
		</view>
         <view class="get_job" style="margin-left: 10rpx;">
         	<text>听知识点</text>
         </view>
		<scroll-view scroll-x="true" class="slider">
			<template v-for="(it, i) in records" style="top: 0rpx;">
				<view class="item" :key="'slider_item_' + i" :style="{ background: it.bg, marginRight: i === records.length - 1 ? '15px' : '0px' }">
					<view class="item_content" @click="chapterknowledge(i)">
						<view class="title">
							<text class="first" style="font-size:smaller;">{{ it.title }}</text>
							<text class="main" style="font-size:smaller;">{{ it.mainTeacher }}</text>
						</view>
						<image class="image" :src="it.icon"></image>
						<!-- <text class="free">免\n费</text> -->
					</view>
				</view>
			</template>
		</scroll-view>
		<view class="container">
		<view class="get_job" style="margin-left: 10rpx;">
			<text>章节练习</text>
		</view>
		<view class="technology">
			<view class="java" style="margin-left: 10rpx;" @click="chapter(0)">马哲</view>
			<view class="html" style="margin-left: 10rpx;" @click="chapter(1)">毛中特</view>
			<view class="css" style="margin-left: 10rpx;"  @click="chapter(2)">近代史</view>
			<view class="javascipt" style="margin-left: 10rpx;" @click="chapter(3)">思修</view>
			<view class="javascipt" style="margin-left: 10rpx;" @click="chapter(4)">形势政策</view>
		</view>
		</view>

		<view>
			<cl-popup :visible.sync="visible" direction="bottom">
				<view class="demo-icon">
					<cl-card label="选 择">
						<cl-row>
							<cl-col span="8" v-for="(item, index) in list" :key="index">
								<view class="block" @click="menu(index)">
									<cl-icon :name="item.value" :size="44"></cl-icon>
									<text class="label">{{ item.label }}</text>
								</view>
							</cl-col>
						</cl-row>
					</cl-card>
				</view>
			</cl-popup>
		</view>

		<view>
			<cl-popup :visible.sync="examshow" direction="bottom">
				<view class="demo-icon">
					<cl-card label="操作">
						<cl-row>
							<cl-col span="8">
								<view class="block" @click="operate(0)">
									<cl-icon name="cl-icon-question" :size="44"></cl-icon>
									<text class="label">开始考试</text>
								</view>
							</cl-col>
							<cl-col span="8">
								<view class="block" @click="operate(1)">
									<cl-icon name="cl-icon-arrow-bottom" :size="44"></cl-icon>
									<text class="label">下载</text>
								</view>
							</cl-col>
						</cl-row>
					</cl-card>
				</view>
			</cl-popup>
		</view>

	
		<view class="cu-modal" :class="modalCard == true ? 'show' : ''" @tap="hideModalcard">
			<view class="cu-dialog" @tap.stop style="height: 70%;">
				<view class="cu-bar bg-white justify-end">
					<view class="content">{{ subtitle }}</view>
					<view class="action" @bindtap="hideModalcard" @tap="hideModalcard"><text class="cuIcon-close text-gray"></text></view>
				</view>

				
				<view class="padding-xl">
					
					<view style="background:#FFF; padding:40rpx;">
						<block v-if="hasUserInfo === false">
							<view class="uni-hello-text uni-center"><text>请点击下方按钮获取用户头像及昵称</text></view>
							<view class="uni-btn-v" v-if="hasUserInfo === false"><button type="primary" open-type="getUserInfo" @getuserinfo="getUserInfo">授权</button></view>
						</block>
						<block v-if="hasUserInfo === true">
							<!-- <view class="uni-h4 uni-center uni-common-mt">{{ userInfo.nickName || userInfo.email }}</view>
							<view style="padding:30rpx 0; text-align:center;"><image class="userinfo-avatar" :src="userInfo.avatarUrl"></image></view> -->
							
							<view class="example-body">
								<text style="font-size: medium;text-align: center;">离考试时间还有</text>
								<uni-countdown :day="remainingDays" :hour="remainingHours" :minute="remainingMinutes" :second="remainingSeconds" :show-colon="false" style="font-size: medium;"/>
							</view>
							<view class="example-body">
								<text style="font-size: medium;text-align: center;">{{sentence}}</text>
								<!-- <uni-countdown :day="remainingDays" :hour="remainingHours" :minute="remainingMinutes" :second="remainingSeconds" :show-colon="false" style="font-size: medium;"/> -->
							</view>
						</block>
					</view>

					<!-- </view> -->
				</view>
			</view>
		</view>
	</view>
</template>

<script>
var _this;
import Util from 'common/util.js';
import uniSection from '@/components/uni-section/uni-section.vue'
import uniSwiperDot from '@/components/uni-swiper-dot/uni-swiper-dot.vue'
import uniCountdown from '@/components/uni-countdown/uni-countdown.vue'
	var remainingDays=0;
	var remainingHours=0;
	var remainingMinutes=0;
	var remainingSeconds=0;
export default {
	components: {
		uniSection,
		uniSwiperDot,
		uniCountdown,
	},
	created() {
		
		var timeup= setTimeout(() => {
			this.remainingDays = remainingDays;
			this.remainingHours = remainingHours;
			this.remainingMinutes = remainingMinutes;
			this.remainingSeconds = remainingSeconds;
			
		}, 2000);
		
		
	},
	data() {
		return {
			menulist: [
				{
					label: '组卷',
					value: '智'
				},
				{
					label: '练习',
					value: '练'
				},
				{
					label: '真题',
					value: '真'
				},
				{
					label: '实事',
					value: '热'
				},

			],
			remainingDays:0,
			remainingMinutes:0,
			remainingHours:0,
			remainingSeconds:0,
			avatarUrl:[],
			modeIndex: -1,
			styleIndex: -1,
			current: 0,
			mode: 'default',
			dotsStyles: {},
			dotStyle: [{
					backgroundColor: 'rgba(0, 0, 0, .3)',
					border: '1px rgba(0, 0, 0, .3) solid',
					color: '#fff',
					selectedBackgroundColor: 'rgba(0, 0, 0, .9)',
					selectedBorder: '1px rgba(0, 0, 0, .9) solid'
				},
				{
					backgroundColor: 'rgba(255, 90, 95,0.3)',
					border: '1px rgba(255, 90, 95,0.3) solid',
					color: '#fff',
					selectedBackgroundColor: 'rgba(255, 90, 95,0.9)',
					selectedBorder: '1px rgba(255, 90, 95,0.9) solid'
				},
				{
					backgroundColor: 'rgba(83, 200, 249,0.3)',
					border: '1px rgba(83, 200, 249,0.3) solid',
					color: '#fff',
					selectedBackgroundColor: 'rgba(83, 200, 249,0.9)',
					selectedBorder: '1px rgba(83, 200, 249,0.9) solid'
				}
			],
			info: [],
			downloaded: false,
			hasUserInfo: false,
			title: false,
			userInfo: null,
			modalCard: false,
			subtitle: '授权',
			examshow: false,
			flag: true,
			visible: false,
			paper: null,
			sentence:"恰同学少年，风华正茂；书生意气，挥斥方遒。指点江山，激扬文字，粪土当年万户侯。曾记否，到中流击水，浪遏飞舟。",
			list: [
				{
					label: '练习历史',
					value: 'cl-icon-info'
				},
				{
					label: '我的收藏',
					value: 'cl-icon-favor-fill'
				},
				{
					label: '我的错题',
					value: 'cl-icon-close'
				},
				{
					label: '我的笔记',
					value: 'cl-icon-image'
				},
				{
					label: '资料缓存',
					value: 'cl-icon-arrow-bottom'
				}
			],
			menus: [
				{
					bg: 'linear-gradient(0deg,rgba(9,216,162,1),rgba(90,242,217,1))',
					icon: '/static/graduation.png',
					txt: '智能组卷',
					isFree: true
				},
				{
					bg: 'linear-gradient(0deg,rgba(251,184,35,1),rgba(255,228,40,1))',
					icon: '/static/live.png',
					txt: '快速练习',
					isFree: false
				},
				{
					bg: 'linear-gradient(0deg,rgba(255,126,34,1),rgba(240,184,27,1))',
					icon: '/static/emblem.png',
					txt: '历年真题',
					isFree: true
				},
				{
					bg: 'linear-gradient(0deg,rgba(9,177,252,1),rgba(24,200,255,1))',
					icon: '/static/question_bank.png',
					txt: '知识点',
					isFree: true
				}
			],
			/* var subName1="马克思主义基本原理概论";
					var subName2="毛泽东思想和中国特色社会主义理论体系概论";
					var subName3="中国近代史纲要";
					var subName4="思想道德修养与法律基础";
					var subName5="形势与政策以及当代世界经济与政治"; */

			records: [
				{
					bg: 'linear-gradient(-30deg,rgba(171,218,255,1),rgba(215,239,255,1))',
					title: '马克思主义基本',
					mainTeacher: '原理概论',
					subTitle: '标题名称',
					subColor: '#15639F',
					icon: '/static/test2.png',
					isFree: true
				},
				{
					bg: 'linear-gradient(-30deg,rgba(192,253,227,1),rgba(224,252,240,1))',
					title: '毛泽东思想和中国特色社会',
					mainTeacher: '主义理论体系概论',
					subTitle: '标题名称',
					subColor: '#07B77B',
					icon: '/static/test.png',
					isFree: false
				},
				{
					bg: 'linear-gradient(-30deg,rgba(171,218,255,1),rgba(215,239,255,1))',
					title: '中国近代史纲要',
					mainTeacher: '',
					subTitle: '标题名称',
					subColor: '#15639F',
					icon: '/static/test2.png',
					isFree: true
				},
				{
					bg: 'linear-gradient(-30deg,rgba(192,253,227,1),rgba(224,252,240,1))',
					title: '思想道德修养与',
					mainTeacher: '法律基础',
					subTitle: '标题名称',
					subColor: '#07B77B',
					icon: '/static/test.png',
					isFree: false
				},
				{
					bg: 'linear-gradient(-30deg,rgba(171,218,255,1),rgba(215,239,255,1))',
					title: '形势与政策以及当代',
					mainTeacher: '世界经济与政治',
					subTitle: '标题名称',
					subColor: '#15639F',
					icon: '/static/test2.png',
					isFree: true
				}
			]
		};
	},
	onLoad() {
		/* 	uni.navigateTo({
				url:"/pages/demo/nav/tabs"
			}) */
		this.test();
		this.getHomePageImage();
		// this.hasUserInfo=true;
		this.getSentence();

		// #ifdef MP-WEIXIN || MP-BAIDU || MP-QQ
		this.checkAuth();
		//#endif
		
	},
	onReady() {
		this.countdown2();
	},
	methods: {
		
		chapterknowledge(i){
			if (this.hasUserInfo == true) {
				uni.navigateTo({
					url: '/pages/knowledge/chapterKnowledge?pid=' + (i + 1)
				});
			} else {
				uni.showToast({
					icon: 'none',
					title: '请授权登录'
				});
			}
		},
		
		countdown2()
		{
			 //http://localhost:8081/h5/ 
			
			var month=new Date().getMonth()+1;
			var day=new Date().getDate();
			var hours=new Date().getHours();
			var minutes=new Date().getMinutes();
			var seconds=new Date().getSeconds();
			switch(month)
			{
				case 10:remainingDays=56+(31-day);break;
				case 11:remainingDays=26+(30-day);break;
				case 12:remainingDays=26-day;break;
				default:0;break;
			}
			remainingHours=24-hours;
			remainingMinutes=60-minutes;
			remainingSeconds=60-seconds;
			if(minutes>0)
			{
				remainingHours--;
			}
			if(seconds>0)
			{
				remainingMinutes--;
			}
			
			
			
		},
		getSentence()
		{
			
			 var that=this;
			 uni.request({
			 	url: this.$server+'/image/getSentence',
			 	success(res) {
			 		if (res.statusCode == 200 && res.data!=undefined && res.data.code==200 && res.data.data!=null) {
			 			var data=res.data.data
						
			 			//that.banner.cover=that.$server+"/images/"+data.path;
			 			that.sentence=data.description;
		
			 		}
			 	},
			 	fail: (data, code) => {
			 		
			 	}
			 });
		
		},
		getHomePageImage()
		{
			
			 var that=this;
			 uni.request({
			 	url: this.$server+'/image/getHomePageImage2',
			 	success(res) {
			 		if (res.statusCode == 200 && res.data!=undefined && res.data.code==200 && res.data.data!=null) {
			 			var data=res.data.data
			 			//that.banner.cover=that.$server+"/images/"+data.path;
			 			for(var i=0;i<data.length;++i)
						{
							var url=that.$server+"/images/"+data[i].path;
							var content=data[i].description;
							var info={
					                   colorClass: 'uni-bg-blue',
					                   url: url,
					                   content: content
				                     };
                         that.info.push(info);
						}

			 		}else{
			 			that.info=[{
					colorClass: 'uni-bg-red',
					url: 'https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/shuijiao.jpg',
					content: '内容 A'
				},
				{
					colorClass: 'uni-bg-green',
					url: 'https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/muwu.jpg',
					content: '内容 B'
				},
				{
					colorClass: 'uni-bg-blue',
					url: 'https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/cbd.jpg',
					content: '内容 C'
				}];
			 		}
			 	},
			 	fail: (data, code) => {
			 		//console.log('fail' + JSON.stringify(data))
			 		that.info=[{
			 			colorClass: 'uni-bg-red',
			 			url: 'https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/shuijiao.jpg',
			 			content: '内容 A'
			 		},
			 		{
			 			colorClass: 'uni-bg-green',
			 			url: 'https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/muwu.jpg',
			 			content: '内容 B'
			 		},
			 		{
			 			colorClass: 'uni-bg-blue',
			 			url: 'https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/cbd.jpg',
			 			content: '内容 C'
			 		}];
			 	}
			 });

		},
		change(e) {
			this.current = e.detail.current
		},
		selectStyle(index) {
			this.dotsStyles = this.dotStyle[index]
			this.styleIndex = index
		},
		selectMode(mode, index) {
			this.mode = mode
			this.modeIndex = index
			this.styleIndex = -1
			this.dotsStyles = this.dotStyle[0]

		},
		hide() {
			if (this.examshow == true) {
				console.log('hello world');
				this.examshow = false;
				this.downloaded = false;
			}
		},
		hideModalcard() {
			if (this.modalCard == true) {
				this.modalCard = false;
			}
		},
		getUserInfo() {
			wx.showLoading({
				title:"登陆中",
				mask:true
			})
			var that = this;
			wx.authorize({
				scope: 'scope.userInfo',

				complete(e) {
					uni.getSetting({
						success(res) {
							if (res.authSetting['scope.userInfo']) {
								wx.setStorageSync("eid",-1);
								that.onGetUserInfo();
							} else {
							    wx.hideLoading();
								wx.showToast({
									title: '授权失败',
									icon: 'none'
								});
							}
						}
					});
				}
			});
		},

		onGetUserInfo() {
			//const userInfo = event.detail.userInfo
			/*  wx.showLoading(); */
			var that = this;

			wx.login({
				success: function(login_res) {
					wx.getUserInfo({
						success: function(res) {
							/* 	console.log(2222222);
									console.log(login_res);
									console.log(res); */
							var userInfo = res.userInfo;
							wx.request({
								url: that.$server + '/api/me/login',
								method: 'POST',
								withCredentials: true,
								header: {
									'content-type': 'application/x-www-form-urlencoded'
								},
								data: {
									code: login_res.code,
									userHead: userInfo.avatarUrl,
									userName: userInfo.nickName,
									userGender: userInfo.gender,
									userCity: userInfo.city,
									userProvince: userInfo.province
								},
								success: function(res) {
									/* var userInfo = res.data.data;

											console.log(userInfo); */

									that.hasUserInfo = true;
									that.userInfo = userInfo;
									that.avatarUrl.push(userInfo.avatarUrl);
									that.subtitle="欢迎";

									wx.hideLoading();
									var eid= wx.getStorageSync("eid");
									
									if(isNaN(eid) || eid==-1){
									that.modalCard = true;
									}else{
										uni.showModal({
											title: '提示',
											content: "是否继续考试",
											success: function(res) {
												if (res.confirm) {
													uni.navigateTo({
														url:"/pages/exam/start?eid="+eid+"&statu=1",
													})
												} else if (res.cancel) {
													wx.setStorageSync("eid",-1);
													//console.log('用户点击取消')
												}
											}
										})
									}
									// 将返回的数据保存到全局的缓冲中，方便其他页面使用

									wx.setStorageSync('sessionid', res.data.data);
								}
							});
						},
						fail: function(res) {
							uni.hideLoading();
							that.modalCard = true;

							/* wx.showToast({
										"title":"请检查网络连接",
										"icon":"none"
									}) */
							/* console.log(444444444);
									console.log(res); */
						}
					});
				},
				fail: function(res) {
					uni.hideLoading();
					that.modalCard = true;
				}
			});
			/* this.setData({
			            hasUserInfo: true,
			            userInfo: userInfo
			        }) */
			//}
		},
		checkAuth() {
			uni.showLoading({ title: '加载中' });
			var that = this;

			uni.getSetting({
				success(res) {
					if (res.authSetting['scope.userInfo']) {
						that.subtitle = '欢迎';
						that.onGetUserInfo();
					} else {
						that.subtitle = '授权';

						uni.hideLoading();
						//that.modalCard=true;
					}
				},
				fail(res) {
					uni.hideLoading();
					//console.log(res);
				}
			});
		},
		chapter(i) {
			if (this.hasUserInfo == true) {
				uni.navigateTo({
					url: '/pages/chapter/index?pid=' + (i + 1)
				});
			} else {
				uni.showToast({
					icon: 'none',
					title: '请授权登录'
				});
			}
		},
		download(exam) {
			var that = this;
			if (that.downloaded == false) {
				var eid = exam.id;
				uni.showLoading({
					title: '正在下载'
				});
				uni.request({
					url: that.$server + '/exam/download?eid=' + eid,
					method: 'GET',
					dataType: 'json',
					success(res) {
						if (res.data.code == 200) {
							var url = that.$server + '/paper/' + res.data.data.path + '.pdf';
							console.log(url);
							//url="http://cn.createpdfonline.org/pdffiles/hello%20word(20200623144016).pdf";
							/* var filename = url.split('/');
							filename=filename[filename.length-1]; */

							var temp = url.split('/');
							var fileName = temp[temp.length - 1];
							wx.downloadFile({
								url: url,
								success: function(res) {
									const manage = wx.getFileSystemManager();
									if (res.statusCode === 200) {
										var tempFilePath = res.tempFilePath;
										var dir = new Date().getTime();
										manage.mkdir({
											dirPath: wx.env.USER_DATA_PATH + '/' + dir,
											recursive: false,
											success(res) {
												manage.saveFile({
													tempFilePath: tempFilePath,
													filePath: wx.env.USER_DATA_PATH + '/' + dir + '/' + fileName,
													success: function(res) {
														that.downloaded = true;
														uni.hideLoading();
														uni.showToast({
															icon: 'none',
															title: '下载成功'
														});
													},
													fail(res) {
														uni.hideLoading();
														uni.showToast({
															icon: 'none',
															title: '下载失败,请重试'
														});
													}
												});
											},
											fail(res) {
												uni.hideLoading();
												uni.showToast({
													icon: 'none',
													title: '下载失败,请重试'
												});
											}
										});
									} else {
										uni.hideLoading();
										uni.showToast({
											icon: 'none',
											title: '下载失败,请重试'
										});
									}
								}
							});

						} else {
							uni.hideLoading();
							uni.showToast({
								icon: 'none',
								title: '下载失败'
							});
						}
					},
					fail(res) {
						uni.hideLoading();
						console.log(res);
						uni.showToast({
							icon: 'none',
							title: '网络连接失败'
						});
					},
					complete() {}
				});
			} else {
				uni.showToast({
					icon: 'none',
					title: '下载成功'
				});
			}
		},
		operate(i) {
			if (this.examshow && this.paper != null) {
				if (i == 0) {
					wx.setStorageSync("eid",this.paper.id);
					uni.navigateTo({
						url: '/pages/exam/start?eid=' + this.paper.id
					});
				} else {
					this.download(this.paper);
				}
			} else {
				uni.showToast({
					icon: 'none',
					title: '出现错误了'
				});
			}
		},
		test() {
			uni.request({
				url: this.$server + '/user/set',
				success(res) {
					console.log(res);
				}
			});
		},
		menu(i) {
			var that = this;
			if (that.hasUserInfo == true) {
				switch (i) {
					case 0:
						Util.jump('/pages/user/history');
						break;
					case 1:
						this.to(0);
						break;
					case 2:
						this.to(1);
						break;
					case 3:
						this.to(2);
						break;
					case 4:
						Util.jump('/pages/exam/download');
						break;
				}
			} else {
				uni.showToast({
					icon: 'none',
					title: '请授权登陆'
				});
			}
		},
		to(type) {
			var that = this;
			var url = this.$server + '/question/get?type=' + type;
			uni.request({
				url: url,
				header: { Cookie: 'JSESSIONID=' + that.JSESSIONID() },
				withCredentials: true,
				success(res) {
					console.log(res.data);
					if (res.data.code == 200) {
						if (res.data.data != null) {
							Util.jump('/pages/user/questions?type=' + type + '&pn=0');
						}
					} else {
						uni.showToast({
							icon: 'none',
							title: '无数据'
						});
					}
				},
				fail(res) {
					uni.showToast({
						icon: 'none',
						title: '无数据'
					});
				}
			});
		},
		open(item) {
			item.visible = true;
		},
		submit() {
			this.$refs['confirm'].open({
				title: '提示',
				message: '确定添加该条数据吗？',
				callback: ({ action }) => {
					if (action === 'confirm') {
						this.visible = false;
					}
				}
			});
		},

		exam(i) {
			var that = this;
			if (i == 0 || i == 1) {
				if (that.hasUserInfo == true) {
					uni.request({
						url: this.$server + '/exam/get?type=' + (i + 1),
						method: 'GET',
						dataType: 'json',
						header: { Cookie: 'JSESSIONID=' + that.JSESSIONID() },
						success(res) {
							if (res.data.code == 200) {
								var eid = null;
								if (res.data.data != null) {
									eid = res.data.data.id;
								}

								if (eid != null) {
									if (i == 0) {
										that.paper = res.data.data;
										that.examshow = true;
										that.downloaded = false;
									} else {
										wx.setStorageSync("eid",eid);
										Util.jump('/pages/exam/start?eid=' + eid);
									}
								} else {
									uni.showToast({
										icon: 'none',
										title: '无数据'
									});
								}
							} else {
								uni.showToast({
									icon: 'none',
									title: '出现问题了'
								});
							}
						},
						fail(res) {
							uni.showToast({
								icon: 'none',
								title: '出现问题了'
							});
						},
						complete() {}
					});
				} else {
					uni.showToast({
						icon: 'none',
						title: '请授权登陆'
					});
				}
			} else if (i == 2) {
				if (that.hasUserInfo == true) {
					uni.request({
						url: this.$server + '/exam/zt',
						withCredentials: true,
						success(res) {
							if (res.data.code == 200) {
								uni.hideLoading();
								if (res.data.data != null && res.data.data.length > 0) {
									Util.jump('/pages/exam/zt');
								} else {
									uni.showToast({
										icon: 'none',
										title: '无数据'
									});
								}
							} else {
								uni.showToast({
									icon: 'none',
									title: '出现问题了'
								});
							}
						},
						fail(res) {
							uni.showToast({
								icon: 'none',
								title: '出现问题了'
							});
						}
					});
				} else {
					uni.showToast({
						icon: 'none',
						title: '请授权登陆'
					});
				}
			} else {
				/* Util.jump('/pages/knowledge/index'); */
				Util.jump('/pages/knowledge/index');
				//Util.jump('/pages/news/index');
			}
		}
	}
};
</script>

<style>
page {
	width: 100%;
	background-color: #ffffff;
}
</style>
<style lang="scss" scoped>
@function realSize($args) {
	@return $args / 1.5;
}

@import '../../colorui/animation.css';

page {
	background-color: #ffffff;
	font-size: medium;
}

/******************************************/

.get_job{
		padding: 0rpx 20rpx;
		display: flex;
		align-items: center;
		margin: 20rpx 0rpx;
	}
	.get_job text:nth-child(1){
		font-size: 33rpx;
		font-weight: 400;
		text-align: left;
	}
	.get_job text:nth-child(2){
		margin-left: 40rpx;
		font-size: 25rpx;
		color: #808080;
	}
	.technology{
		display: flex;
		justify-content: space-between;
		margin-top: 20rpx;
		box-shadow: 0rpx 2rpx 10rpx 0rpx #F0F0F0;
		padding: 20rpx 20rpx;
		margin: 0rpx 20rpx;
		border-radius: 10rpx;
	}
	.java,.html,.css,.javascipt{
		text-align: center;
		width: 155rpx;
		height: 100rpx;
		line-height: 100rpx;
		/* background-image: linear-gradient(45deg,rgb(6, 121, 252),rgb(120, 163, 228)); */
		background-image:linear-gradient(-30deg,rgba(171,218,255,1),rgba(215,239,255,1));
		border-radius: 10%;
		color: text-black;
		/* box-shadow: 3rpx 5rpx 10rpx 0rpx #CCCCCC; */
		font-size: small;
	}
	/* .html{
		background-image:linear-gradient(-30deg,rgba(192,253,227,1),rgba(224,252,240,1))
	}
	.css{
		background-image:linear-gradient(-30deg,rgba(171,218,255,1),rgba(215,239,255,1));
	}
	.javascipt{
		background-image:linear-gradient(-30deg,rgba(192,253,227,1),rgba(224,252,240,1))
	} */


/* #ifdef MP-TOUTIAO */
@font-face {
	font-family: uniicons;
	font-weight: normal;
	font-style: normal;
	src: url('~@/static/uni.ttf') format('truetype');
}

/* #endif */

/* #ifndef APP-NVUE */
page {
	display: flex;
	flex-direction: column;
	box-sizing: border-box;
	background-color: #ffffff;
	min-height: 100%;
	height: auto;
}

view {
	font-size: 14px;
	line-height: inherit;
}

.example {
	padding: 0 15px 15px;
}

.example-info {
	padding: 15px;
	color: #3b4144;
	background: #ffffff;
}

.example-body {
	flex-direction: row;
	flex-wrap: wrap;
	justify-content: center;
	padding: 0;
	font-size: 14px;
	background-color: #ffffff;
}

/* #endif */
.example {
	padding: 0 15px;
}

.example-info {
	/* #ifndef APP-NVUE */
	display: block;
	/* #endif */
	padding: 15px;
	color: #3b4144;
	background-color: #ffffff;
	font-size: 14px;
	line-height: 20px;
}

.example-info-text {
	font-size: 14px;
	line-height: 20px;
	color: #3b4144;
}

.example-body {
	flex-direction: column;
	padding: 15px;
	background-color: #ffffff;
}

.word-btn-white {
	font-size: 18px;
	color: #ffffff;
}

.word-btn {
	/* #ifndef APP-NVUE */
	display: flex;
	/* #endif */
	flex-direction: row;
	align-items: center;
	justify-content: center;
	border-radius: 6px;
	height: 48px;
	margin: 15px;
	background-color: #007aff;
}

.word-btn--hover {
	background-color: #4ca2ff;
}

.swiper-box {
	height: 200px;
}

.swiper-item {
	/* #ifndef APP-NVUE */
	display: flex;
	/* #endif */
	flex-direction: column;
	justify-content: center;
	align-items: center;
	background-color: #999;
	color: #fff;
}

.image {
	width: 750rpx;
}

.uni-bg-red {
	background-color: #ff5a5f;
}

.uni-bg-green {
	background-color: #09bb07;
}

.uni-bg-blue {
	background-color: #007aff;
}

.example-body {
	/* #ifndef APP-NVUE */
	display: flex;
	/* #endif */
	flex-direction: row;
	padding: 20rpx;
}

.example-body-item {
	flex-direction: row;
	justify-content: center;
	align-items: center;
	margin: 15rpx;
	padding: 15rpx;
	height: 60rpx;
	/* #ifndef APP-NVUE */
	display: flex;
	padding: 0 15rpx;
	/* #endif */
	flex: 1;
	border-color: #e5e5e5;
	border-style: solid;
	border-width: 1px;
	border-radius: 5px;
}

.example-body-item-text {
	font-size: 28rpx;
	color: #333;
}

.example-body-dots {
	width: 16rpx;
	height: 16rpx;
	border-radius: 50px;
	background-color: #333333;
	margin-left: 10rpx;
}

.active {
	border-style: solid;
	border-color: #007aff;
	border-width: 1px;
}

.cu-form-group {
	justify-content: flex-start;
}

.cu-form-group .title {
	padding-left: 30upx;
	padding-right: 0upx;
}

.cu-form-group + .cu-form-group {
	border-top: none;
}

.cu-bar-title {
	min-height: 50upx;
}

.cu-list.menu > .cu-item-error {
	justify-content: flex-start;
}
.userinfo-avatar {
	/* border-radius: 128rpx;
		width: 128rpx;
		height: 128rpx; */
	border-radius: 300rpx;
	width: 300rpx;
	height: 300rpx;
}
/* 原本的 */
.demo-popup {
	.f-26 {
		font-size: 26rpx;
	}

	.footer {
		margin-top: 20rpx;
		margin-bottom: 20rpx;
		display: flex;

		.cl-button {
			flex: 1;
		}
	}
}

.demo-icon {
	.block {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		height: 160rpx;

		&:active {
			background-color: #eee;
		}

		.label {
			font-size: 24rpx;
			margin-top: 10rpx;
		}
	}
}

.page_edu {
	width: 100%;
}

.page_edu_header {
	padding-top: var(--status-bar-height);
	background-color: rgba(171, 218, 255, 1);
	/* background-color: #0bc99d; */
	width: 100%;
	height: realSize(415px);

	.header {
		display: flex;
		flex-direction: row;
		align-items: center;
		padding: realSize(20px);

		.btn {
			width: realSize(36px);
			height: realSize(30px);
		}

		.input {
			height: realSize(59px);
			width: 100%;
			margin-left: realSize(20px);
			margin-right: realSize(20px);
			background: rgba(255, 255, 255, 1);
			border-radius: realSize(30px);
			display: flex;
			flex-direction: row;
			align-items: center;

			.search {
				width: realSize(30px);
				height: realSize(30px);
				margin-left: realSize(20px);
				margin-right: realSize(20px);
			}
		}
	}

	.header_content {
		display: flex;
		flex-direction: row;

		.left {
			display: flex;
			flex-direction: column;
			width: 57%;
			margin-top: 10px;
			margin-left: 15px;
			margin-right: 15px;

			.title {
				width: realSize(419px);
				height: realSize(59px);
				font-size: realSize(30px);
				/* font-weight: bold; */
				color: rgba(0, 0, 0, 1);
				/* color: rgba(255, 255, 255, 1); */
			}

			.sub_title {
				margin-top: 3px;
				font-size: realSize(18px);
				font-weight: 400;
				color: rgba(255, 255, 255, 1);

				background: linear-gradient(0deg, rgba(120, 255, 224, 1) 0%, rgba(255, 255, 255, 1) 100%);
				-webkit-background-clip: text;
				-webkit-text-fill-color: transparent;
			}

			.btn {
				margin-top: 3px;
				width: realSize(198px);
				height: realSize(60px);
				background: linear-gradient(-30deg, rgba(252, 135, 29, 1), rgba(246, 185, 9, 1));
				box-shadow: 0px 4px 10px 0px rgba(255, 121, 0, 0.5);
				border-radius: realSize(30px);
				color: rgba(255, 255, 255, 1);
				display: flex;
				align-items: center;
				justify-content: center;
			}
		}
	}
}
.content {
	/* margin-left: 5px;
	margin-right: 5px; */
	/* padding-left: 10px;
	padding-right: 10px; */
	background: rgba(255, 255, 255, 1);

	/* display: flex;
	flex-direction: row;
	align-items: stretch;
	justify-content: space-between; */
}

.page_content {
	width: 100%;
	margin-top: -74px;

	.menu {
		margin-left: 10px;
		margin-right: 10px;
		padding-left: 10px;
		padding-right: 10px;
		height: realSize(176px);
		background: rgba(255, 255, 255, 1);
		/* box-shadow: 0px 10px 10px 0px rgba(0, 161, 124, 0.1); */
		border-radius: 10px;
		display: flex;
		flex-direction: row;
		align-items: stretch;
		justify-content: space-between;

		.item {
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;

			.img_view {
				width: 60px;
				height: 60px;
				border-radius: 30px;
				display: flex;
				align-items: center;
				justify-content: center;

				.image {
					width: 50px;
					height: 50px;
				}
			}

			.txt {
				margin-top: 5px;
				font-size: 14px;
				color: rgba(51, 51, 51, 1);
			}
		}
	}

	.s_menu {
		display: flex;
		flex-direction: row;
		align-items: stretch;
		justify-content: space-between;
		margin-top: 15px;
		margin-left: 10px;
		margin-right: 10px;
		padding-left: 10px;
		padding-right: 10px;

		.item {
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;

			.image {
				width: 35px;
				height: 35px;
			}

			.txt {
				margin-top: 5px;
				font-size: 14px;
				color: rgba(51, 51, 51, 1);
			}
		}
	}

	.ad {
		width: 100%;
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: center;

		.bg {
			position: absolute;
			width: 120px;
			height: 105px;
			left: 0;
		}

		.ad_btn {
			width: 100%;
			height: 63px;
			margin: 30px;
			background: linear-gradient(0deg, rgba(253, 155, 28, 1), rgba(251, 197, 33, 1));
			border-radius: 67px;
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;

			.title {
				font-size: realSize(38px);
				font-family: PingFang-SC-Heavy;
				font-weight: 800;
				color: rgba(255, 255, 255, 1);
			}

			.sub_title {
				background: linear-gradient(0deg, rgba(255, 128, 37, 1), rgba(255, 153, 32, 1));
				box-shadow: 0px 4px 5px 0px rgba(92, 53, 48, 0.3), 0px 1px 0px 0px rgba(228, 228, 228, 1);
				border-radius: realSize(24px);
				font-size: realSize(24px);
				font-family: PingFang-SC-Heavy;
				font-weight: 800;
				font-style: italic;
				color: rgba(255, 236, 177, 1);
				line-height: realSize(26px);
			}
		}
	}
}

.slider {
	white-space: nowrap;
	width: 100%;
	background-color: white;

	.item {
		display: inline-block;
		margin-left: 15px;
		margin-top: 13px;
		margin-bottom: 13px;
		width: 60%;
		height: 125px;
		border-radius: 10px;

		.item_content {
			display: flex;
			flex-direction: row;

			.title {
				width: 36%;
				margin: 20px;
				display: flex;
				flex-direction: column;

				.first {
					font-size: 13px;
					color: rgba(46, 65, 69, 1);
				}
				.main {
					font-size: 13px;
					color: rgba(46, 65, 69, 1);
					margin-top: 5px;
				}
				.sub {
					width: 60px;
					font-size: 10px;
					margin-top: 20px;
					background: rgba(255, 255, 255, 0.4);
					border-radius: 5px;
					display: flex;
					align-items: center;
					justify-content: center;
				}
			}

			.image {
				margin-top: 35px;
				width: 80px;
				height: 80px;
			}

			.free {
				background: rgba(11, 147, 252, 1);
				border-radius: 0px 0px 22px 22px;
				width: 25px;
				height: 50px;
				display: flex;
				align-items: center;
				justify-content: center;
				color: #ffffff;
				font-size: 14px;
			}
		}
	}
}
</style>
