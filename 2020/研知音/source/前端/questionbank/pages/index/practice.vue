<template>
	<view v-if="flag==true">
		
		<view class="wrap">
			<!-- <page-nav :desc="desc" title="nav.components"></page-nav> -->
			<view class="nav-wrap">
				<view class="nav-title">
					<!-- <image class="logo" src="https://cdn.uviewui.com/uview/common/logo.png" mode="widthFix"></image> -->
					<view class="nav-info">
						<view class="nav-title__text">
							练习
						</view>
						<view class="nav-slogan">
							学而时习之
						</view>
					</view>
				</view>
				<view class="nav-desc">
					
				</view>
				<view class="lang" @tap="visible = true">
					
					<u-icon size="46" color="default" name="grid"></u-icon>
				</view>
				<!-- <view class="page_edu">
				<view  class="slider">
				<template v-for="(it, i) in records" style="top: 0rpx;" >
					<view class="item" :key="'slider_item_' + i" :style="{ background: it.bg, marginRight: i === records.length - 1 ? '15px' : '0px' }">
						<view class="item_content" @click="chapter(i+1)">
							<view class="title">
								<text class="first" style="font-size:smaller;">{{ it.title }}</text>
								<text class="main" style="font-size:smaller;">{{ it.mainTeacher }}</text>
								<text class="main" style="font-size:smaller;">{{ it.other }}</text>
								<text class="main" style="font-size:smaller;">进度:{{chapterSchedule[i].uqnum!=undefined?chapterSchedule[i].uqnum:0}}/{{chapterSchedule[i].cqnum}}</text>
							</view>
						
						</view>
					</view>
				</template>
				</view>
				</view>
				<view class="page_edu">
				<scroll-view scroll-x="true" class="slider">
				<template v-for="(it, i) in records2" style="top: 0rpx;">
					<view class="item" :key="'slider_item_' + i" :style="{ background: it.bg, marginRight: i === records.length - 1 ? '15px' : '0px' }">
						<view class="item_content" @click="chapter(i+3)">
							<view class="title">
								<text class="first" style="font-size:smaller;">{{ it.title }}</text>
								<text class="main" style="font-size:smaller;">{{ it.mainTeacher }}</text>
								<text class="main" style="font-size:smaller;">进度:{{chapterSchedule[i+2].uqnum!=undefined?chapterSchedule[i+2].uqnum:0}}/{{chapterSchedule[i+2].cqnum}}</text>
							</view>
							
						</view>
					</view>
				</template>
				</scroll-view>
				</view>
				<view class="page_edu">
				<scroll-view scroll-x="true" class="slider">
				<template v-for="(it, i) in records3" style="top: 0rpx;">
					<view class="item" :key="'slider_item_' + i" :style="{ background: it.bg, marginRight: i === records.length - 1 ? '15px' : '0px' }">
						<view class="item_content" @click="chapter(i+5)">
							<view class="title">
								<text class="first" style="font-size:smaller;">{{ it.title }}</text>
								<text class="main" style="font-size:smaller;">{{ it.mainTeacher }}</text>
								<text class="main" style="font-size:smaller;" v-if="i+5<6">进度:{{chapterSchedule[i+4].uqnum!=undefined?chapterSchedule[i+4].uqnum:0}}/{{chapterSchedule[i+4].cqnum}}</text>
							</view>
							
						</view>
					</view>
				</template>
				</scroll-view>
				</view>
				 -->
				<view style="margin-top: 10%;">
					<scroll-view scroll-y class="page">
						
						<view class="nav-list">
							<view hover-class="none"  class="nav-li"  :class="'bg-'+item.color" @click="chapter(index)"
							 :style="[{animation: 'show ' + ((index+1)*0.2+1) + 's 1'}]" v-for="(item,index) in elements" :key="index">
								<view class="nav-title">{{item.title}}</view>
								<view class="nav-name" v-if="index<5 && chapterSchedule.length>0">进度:{{chapterSchedule[index].uqnum!=undefined?chapterSchedule[index].uqnum:0}}/{{chapterSchedule[index].cqnum}}</view>
								<view class="nav-name" v-if="index<5 && chapterSchedule.length==0">未开始练习</view>
								<view class="nav-name" v-if="index==5">习题和试卷</view>
								<text :class="'cuIcon-' + item.cuIcon"></text>
							</view>
						</view>
						
					</scroll-view>
				</view>
				
				
				
				<view class="page_content" style="margin-top: 5%;margin-bottom: 5%;">
					<view class="menu">
						<template v-for="(it, i) in menus">
							<view class="item" :key="'menu_' + i">
								<view class="img_view" :style="{ background: it.bg }" @click="exam(i)"><image :src="it.icon" class="image"></image></view>
								<text class="txt">{{ it.txt }}</text>
							</view>
						</template>
					</view>
					</view>
					<!-- #ifdef MP-WEIXIN || MP-QQ -->
					<ad unit-id="adunit-b70c21061a8b65b1" ad-type="grid" grid-opacity="0.8" grid-count="5" ad-theme="white"></ad>
					<!-- #endif -->
				
			</view>
	
		</view>
		<view>
			<cl-popup :visible.sync="visible" direction="bottom">
				<view class="demo-icon">
					<cl-card label="选 择">
						<cl-row>
							<cl-col span="8" v-for="(item, index) in list" :key="index">
								<view class="block" @click="menu(index)">
									<!-- <cl-icon :name="item.value" :size="44"></cl-icon> -->
									<text :class="item.value" class="lg text-black" style="font-size: 44rpx;"></text>
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
		
		
		<u-tabbar
		    v-model="tabbarcurrent"
		    :show="show"
		    :bg-color="bgColor"
		    :border-top="borderTop"
		    :list="list2"
			:midButton="true"
			:inactive-color="inactiveColor"
			:activeColor="activeColor"
			
		></u-tabbar>
	</view>
</template>

<script>
	import Util from 'common/util.js';
	let interstitialAd = null;
	export default {
		data() {
			return {
				downloaded: false,
				hasUserInfo: false,
				examshow: false,
				paper: null,
				visible: false,
				list: [
					/* {
						label: '练习历史',
						value: 'cl-icon-info'
					}, */
					{
						label: '我的收藏',
						value: 'cuIcon-favor'
					},
					{
						label: '我的错题',
						value: 'cuIcon-close'
					},
					{
						label: '我的笔记',
						value: 'cuIcon-write'
					},
					{
						label: '资料缓存',
						value: 'cuIcon-down'
					}
				],
				elements: [{
						title: '马哲',
						name: 'layout',
						color: 'cyan',
						cuIcon: 'newsfill'
					},
					{
						title: '毛中特',
						name: 'background',
						color: 'blue',
						cuIcon: 'colorlens'
					},
					{
						title: '近代史',
						name: 'text',
						color: 'purple',
						cuIcon: 'font'
					},
					{
						title: '思修 ',
						name: 'icon',
						color: 'mauve',
						cuIcon: 'cuIcon'
					},
					/* {
						title: '按钮',
						name: 'button',
						color: 'pink',
						cuIcon: 'btn'
					},
					{
						title: '标签',
						name: 'tag',
						color: 'brown',
						cuIcon: 'tagfill'
					},
					{
						title: '头像',
						name: 'avatar',
						color: 'red',
						cuIcon: 'myfill'
					},
					{
						title: '进度条',
						name: 'progress',
						color: 'orange',
						cuIcon: 'icloading'
					}, */
					{
						title: '形势政策',
						name: 'shadow',
						color: 'olive',
						cuIcon: 'copy'
					},
					{
						title: '练习历史',
						name: 'loading',
						color: 'green',
						cuIcon: 'loading2'
					}
				],
				tabbarcurrent:2,
				show: true,
				bgColor: '#ffffff',
				borderTop: true,
				midButton: true,
				inactiveColor: '#909399',
				activeColor: '#5098FF',
				list2: [{
					    
						iconPath: "/static/image/home/0.png",
						selectedIconPath: "/static/image/home/1.png",
						text: '听新闻',
						"pagePath": "/pages/news/index",
						isDot: false,
						customIcon: false,
					},
				
					{
						iconPath: "/static/image/home/2.png",
						selectedIconPath: "/static/image/home/3.png",
						text: '听知识点',
						"pagePath": "/pages/index/index",
						midButton: true,
						customIcon: false,
					},
				
					{
						iconPath: "/static/image/home/4.png",
						selectedIconPath: "/static/image/home/5.png",
						text: '练习',
						"pagePath": "/pages/index/practice",
						isDot: false,
						customIcon: false,
					}
				],
				records: [
					{
						bg: 'linear-gradient(-30deg,rgba(171,218,255,1),rgba(215,239,255,1))',
						/* bg:'bg-cyan', */
						title: '马克思主义基本原理概论',
						mainTeacher: '',
						subTitle: '标题名称',
						subColor: '#15639F',
						icon: '/static/test2.png',
						isFree: true
					},
					{
						bg: 'linear-gradient(-30deg,rgba(192,253,227,1),rgba(224,252,240,1))',
						/* bg:'bg-blue', */
						title: '毛泽东思想和中国特色',
						mainTeacher: '社会主义理论体系概论',
						other:"",
						subTitle: '理论体系概论',
						subColor: '#07B77B',
						icon: '/static/test.png',
						isFree: false
					}/* ,
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
					} */
				],
				records2: [
					{
						bg: 'linear-gradient(-30deg,rgba(192,253,227,1),rgba(224,252,240,1))',
						
						title: '中国近代史纲要',
						mainTeacher: '',
						subTitle: '标题名称',
						subColor: '#15639F',
						icon: '/static/test2.png',
						isFree: true
					},
					{
						bg: 'linear-gradient(-30deg,rgba(171,218,255,1),rgba(215,239,255,1))',
						title: '思想道德修养与',
						mainTeacher: '法律基础',
						subTitle: '标题名称',
						subColor: '#07B77B',
						icon: '/static/test.png',
						isFree: false
					}/* ,
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
					} */
				],
				records3: [
					{
						bg: 'linear-gradient(-30deg,rgba(171,218,255,1),rgba(215,239,255,1))',
						title: '形势与政策以及当代',
						mainTeacher: '世界经济与政治',
						subTitle: '标题名称',
						subColor: '#15639F',
						icon: '/static/test2.png',
						isFree: true
					},
					{
						bg: 'linear-gradient(-30deg,rgba(192,253,227,1),rgba(224,252,240,1))',
						title: '练习历史',
						mainTeacher: '',
						subTitle: '标题名称',
						subColor: '#07B77B',
						icon: '/static/test.png',
						isFree: false
					}/* ,
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
					} */
				],
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
						label: '时政',
						value: '热'
					},
				
				],
				
				menus: [
					{
						/* bg: 'linear-gradient(0deg,rgba(9,216,162,1),rgba(90,242,217,1))', */
						bg:'#ffffff',
						icon: '/static/0.png',
						txt: '智能组卷',
						isFree: true
					},
					{
						/* bg: 'linear-gradient(0deg,rgba(251,184,35,1),rgba(255,228,40,1))', */
						bg:'#ffffff',
						icon: '/static/1.png',
						txt: '快速练习',
						isFree: false
					},
					{
						/* bg: 'linear-gradient(0deg,rgba(255,126,34,1),rgba(240,184,27,1))', */
						bg:'#ffffff',
						icon: '/static/2.png',
						txt: '历年真题',
						isFree: true
					}/* ,
					{
						bg: 'linear-gradient(0deg,rgba(9,177,252,1),rgba(24,200,255,1))',
						icon: '/static/question_bank.png',
						txt: '知识点',
						isFree: true
					} */
				],
				chapterSchedule:[],
				flag:true
				
			}
		},
		onUnload(){
			this.visible=false;
		},
		onHide(){
				this.visible=false;
		},
		
		onLoad(){
			//console.log(this.JSESSIONID()==null);
			if(this.JSESSIONID()!="")
			{
				this.hasUserInfo=true;
				this.initScheDule();
			}
		
			
			
			
			
		},
		onShow(){
			
			/* if(this.hasUserInfo==true)
			{
				console.log("hello world");
			}else{
				this.hasUserInfo=true;
			} */
			if(this.JSESSIONID()!=null && this.chapterSchedule.length>0)
			{
				this.initScheDule();
			}
		},
		methods: {
			chapter(i) {
	
				if (this.hasUserInfo == true) {
					if(i<5){
					uni.navigateTo({
						url: '/pages/chapter/index?pid=' + (i + 1)
					});
					}else{
					uni.navigateTo({
						url: '/pages/user/history'
					});	
						
					}
				} else {
					uni.showToast({
						icon: 'none',
						title: '请授权登录'
					});
				}
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
										/* if (i == 0) {
											that.paper = res.data.data;
											that.examshow = true;
											that.downloaded = false;
										} else {
											wx.setStorageSync("eid",eid);
											Util.jump('/pages/exam/start?eid=' + eid);
										} */
										Util.jump('/pages/exam/startExam?eid=' + eid);
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
							header: { Cookie: 'JSESSIONID=' + that.JSESSIONID() },
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
			
			menu(i) {
				var that = this;
				
				if (that.hasUserInfo == true) {
					switch (i) {
						
						case 0:
							this.to(0);
							break;
						case 1:
							this.to(1);
							break;
						case 2:
							this.to(2);
							break;
						case 3:
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
						
						if (res.data.code == 200) {
							if (res.data.data != null) {
								Util.jump('/pages/user/chapterQuestions?type=' + type + '&pn=0');
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
			
			initScheDule()
			{
			    var that=this;
			
					var url=that.$server+"/chapter/getScheDule?pid=1";
					
					                   uni.request({
											url:url ,
											withCredentials:true,
											header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
											success(res){
																//resolve(res.data);
															
												if(res!=undefined && res.data.code==200){
															if(res.data.data!=null){
																
															/* var chapters=res.data.data;
															console.log(chapters);   */
															that.chapterSchedule=[];
															var data=res.data.data;
															for(var key in data)
															{
																that.chapterSchedule.push(data[key]);
															}
															
															/* that.chapterSchedule.push(data.1);
															that.chapterSchedule.push(data.2);
															that.chapterSchedule.push(data.3);
															that.chapterSchedule.push(data.4);
															that.chapterSchedule.push(data.5); */
															//that.chapterSchedule=res.data.data;
															/* that.flag=true; */
														}
													
												}
											},
											fail(res){}
										});
				
			},
			
			jump(e){
				switch(e)
				{
					case 0:uni.navigateTo({
						url:'/pages/news/index'
					});this.tabbarcurrent=0;break;
					case 1:uni.navigateTo({
						url:'/pages/index/index'
					});this.tabbarcurrent=1;break;
				}
			}
			
		}
	}
</script>

<style lang="scss" scoped>
	page{
		background-color:rgba(255, 255, 255, 1);
	}
	
	.u-cell-icon {
		width: 36rpx;
		height: 36rpx;
		margin-right: 8rpx;
	}
	.nav-wrap {
		padding: 15px;
		position: relative;
	}
	
	.lang {
		position: absolute;
		top: 15px;
		right: 15px;
	}
	
	.nav-title {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		align-items: center;
	}
	
	.nav-info {
		margin-left: 15px;
	}
	
	.nav-title__text {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		color: $u-main-color;
		font-size: 25px;
		font-weight: bold;
	}
	
	.logo {
		width: 70px;
		/* #ifndef APP-NVUE */
		height: auto;
		/* #endif */
	}
	
	.nav-slogan {
		color: $u-tips-color;
		font-size: 14px;
	}
	
	.nav-desc {
		margin-top: 10px;
		font-size: 14px;
		color: $u-content-color;
	}
	
	
	.page_edu {
		width: 100%;
	}
	
	.page_edu_header {
		padding-top: var(--status-bar-height);
		background-color: #0bc99d;
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
					font-size: realSize(47px);
					font-weight: bold;
					color: rgba(255, 255, 255, 1);
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
	
	.page_content {
		width: 100%;
		margin-top: -74px;
	
		.menu {
			margin-left: 10px;
			margin-right: 10px;
			padding-left: 10px;
			padding-right: 10px;
			height: realSize(190px);
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
					width: 50px;
					height: 50px;
					border-radius: 25px;
					display: flex;
					align-items: center;
					justify-content: center;
	
					.image {
						width: 35px;
						height: 35px;
					}
				}
	
				.txt {
					margin-top: 1px;
					font-size: 12px;
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
			width: 45%;
			height: 105px;
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
						font-size: 16px;
						color:rgba(46,65,69,1);
					}
					.main {
						font-size: 13px;
						color:rgba(79,103,101,1);
						margin-top: 5px;
					}
					.sub {
						width: 60px;
						font-size: 10px;
						margin-top: 20px;
						background:rgba(255,255,255,0.4);
						border-radius:5px;
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
					color: #FFFFFF;
					font-size: 14px;
				}
			}
		}
	}
	/* .page {
		height: 80vh;
	} */
	
	.nav-list {
		display: flex;
		flex-wrap: wrap;
		padding: 0px 40upx 0px;
		justify-content: space-between;
	}
	
	.nav-li {
		padding: 30upx;
		border-radius: 12upx;
		width: 45%;
		margin: 0 2.5% 40upx;
		background-image: url(https://cdn.nlark.com/yuque/0/2019/png/280374/1552996358352-assets/web-upload/cc3b1807-c684-4b83-8f80-80e5b8a6b975.png);
		background-size: cover;
		background-position: center;
		position: relative;
		z-index: 1;
	}
	
	.nav-li::after {
		content: "";
		position: absolute;
		z-index: -1;
		background-color: inherit;
		width: 100%;
		height: 100%;
		left: 0;
		bottom: -10%;
		border-radius: 10upx;
		opacity: 0.2;
		transform: scale(0.9, 0.9);
	}
	
	.nav-li.cur {
		color: #fff;
		background: rgb(94, 185, 94);
		box-shadow: 4upx 4upx 6upx rgba(94, 185, 94, 0.4);
	}
	
	.nav-title {
		font-size: 32upx;
		font-weight: 300;
	}
	
	.nav-title::first-letter {
		font-size: 40upx;
		margin-right: 4upx;
	}
	
	.nav-name {
		font-size: 28upx;
		text-transform: Capitalize;
		margin-top: 20upx;
		position: relative;
	}
	
	.nav-name::before {
		content: "";
		position: absolute;
		display: block;
		width: 40upx;
		height: 6upx;
		background: #fff;
		bottom: 0;
		right: 0;
		opacity: 0.5;
	}
	
	.nav-name::after {
		content: "";
		position: absolute;
		display: block;
		width: 100upx;
		height: 1px;
		background: #fff;
		bottom: 0;
		right: 40upx;
		opacity: 0.3;
	}
	
	.nav-name::first-letter {
		font-weight: bold;
		font-size: 36upx;
		margin-right: 1px;
	}
	
	.nav-li text {
		position: absolute;
		right: 30upx;
		top: 30upx;
		font-size: 52upx;
		width: 60upx;
		height: 60upx;
		text-align: center;
		line-height: 60upx;
	}
	
	.text-light {
		font-weight: 300;
	}
	
	@keyframes show {
		0% {
			transform: translateY(-50px);
		}
	
		60% {
			transform: translateY(40upx);
		}
	
		100% {
			transform: translateY(0px);
		}
	}
	
	@-webkit-keyframes show {
		0% {
			transform: translateY(-50px);
		}
	
		60% {
			transform: translateY(40upx);
		}
	
		100% {
			transform: translateY(0px);
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
	.lang {
		position: absolute;
		top: 15px;
		right: 15px;
	}
</style>