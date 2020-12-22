<template>
	<view v-if="flag==true">
		<!-- <view class="banner">
			<image class="banner-img" :src="banner.cover"></image>
			<view class="banner-title">{{banner.title}}</view>
		</view> -->
		<view style="text-align: center;font-size: medium;margin-top: 10rpx;"><h3>{{banner.title}}</h3></view>
		<view class="article-meta">
			
			<!-- <text class="article-author">{{banner.author_name}}</text> -->
			<text class="article-text">来源:{{banner.author_name}} {{banner.published_at}}</text>
			<!-- <text class="article-time">{{banner.published_at}}</text> -->
		</view>
		<view class="article-content">
			<rich-text :nodes="htmlNodes"></rich-text>
		</view>
		
		<!-- <view class="holdon" @click="clshow()" v-if="(flag==true && show==false)">
			<image  class="ball" style="left:70%;top:80%"	 
					 :src="'../../static/icon/3.png'"  mode="aspectFit">
			</image>
		</view> -->
		<view class="holdon" @click="play()" v-if="(flag==true && show==false)">
			<image  class="ball" :style="'left:'+(moveX == 0 & x>0? x+'%':moveX+'px')+';top:'+(moveY == 0 & y>0? y+'%':moveY+'px')"	 
					 :src="'../../static/icon/'+userFavor+'.png'"  mode="aspectFit">
			</image>
		</view>
		
		<cl-popup :visible.sync="show" direction="bottom">
			<uni-section title="设置音频播放速率" type="line"></uni-section>
			<view class="example-body">
				<button type="default" size="mini"  :disabled="rate>0.5?false:true"  style="margin-right: 4rpx;" @tap="rateChange(0)">-</button><button type="default" size="mini" style="margin-right: 4rpx;">{{rate}}</button><button type="default" size="mini" :disabled="rate<2.0?false:true" @tap="rateChange(1)">+</button>
			</view>
			
			
		</cl-popup>
	</view>
	
</template>

<script>
	const DETAIL_PAGE_PATH = '/pages/news/newsDetail';

	import htmlParser from '@/common/html-parser'
	import dragball from "@/components/drag-ball/drag-ball.vue";
	import uniNumberBox from '@/components/uni-number-box/uni-number-box.vue'
	import uniSection from '@/components/uni-section/uni-section.vue'

	function _handleShareChannels(provider) {
		let channels = [];
		for (let i = 0, len = provider.length; i < len; i++) {
			switch (provider[i]) {
				case 'weixin':
					channels.push({
						text: '分享到微信好友',
						id: 'weixin',
						sort: 0
					});
					channels.push({
						text: '分享到微信朋友圈',
						id: 'weixin',
						sort: 1
					});
					break;
				default:
					break;
			}
		}
		channels.sort(function(x, y) {
			return x.sort - y.sort;
		});
		return channels;
	}

	export default {
		props: {
			x: {
				type: Number,
				default:80
			},
			y: {
				type: Number,
				default:80
			},
			image:{
				type:String,
				default: '../../static/icon/0.png'
			}
		},
		mounted() {
			const { windowWidth, windowHeight } = uni.getSystemInfoSync();	
			
			this.windowWidth = windowWidth
			this.windowHeight = windowHeight
		},
		data() {
			return {
				rate:1.0,
				show:false,
				start:[0,0],
				moveY:0,
				moveX:0,
				windowWidth:'',
				windowHeight:'',
				flag:false,
				title: '',
				banner: {},
				htmlNodes: [],
				userFavor:0,
				audioManager:wx.getBackgroundAudioManager()
			}
		},
		onLoad(event) {
			// TODO 后面把参数名替换成 payload
			const payload = event.detailDate || event.payload;
			// 目前在某些平台参数会被主动 decode，暂时这样处理。
			try {
				this.banner = JSON.parse(decodeURIComponent(payload));
			} catch (error) {
				this.banner = JSON.parse(payload);
			}
		
			/* uni.setNavigationBarTitle({
				title: this.banner.title
			}); */
			this.getDetail();
			this.initAudioManager();
		},
		onShareAppMessage() {
			return {
				title: this.banner.title,
				path: DETAIL_PAGE_PATH + '?detailDate=' + JSON.stringify(this.banner)
			}
		},
		onNavigationBarButtonTap(event) {
			const buttonIndex = event.index;
			if (buttonIndex === 0) {
				// 分享 H5 的页面
				const shareProviders = [];
				uni.getProvider({
					service: 'share',
					success: (result) => {
						// 目前仅考虑分享到微信
						if (result.provider && result.provider.length && ~result.provider.indexOf('weixin')) {
							const channels = _handleShareChannels(result.provider);
							uni.showActionSheet({
								itemList: channels.map(channel => {
									return channel.text;
								}),
								success: (result) => {
									const tapIndex = result.tapIndex;
									uni.share({
										provider: 'weixin',
										type: 0,
										title: this.banner.title,
										scene: tapIndex === 0 ? 'WXSceneSession' : 'WXSenceTimeline',
										href: 'https://uniapp.dcloud.io/h5' + DETAIL_PAGE_PATH + '?detailDate=' + JSON.stringify(this.banner),
										imageUrl: 'https://img-cdn-qiniu.dcloud.net.cn/uniapp/app/share-logo@3.png'
									});
								}
							});
						} else {
							uni.showToast({
								title: '未检测到可用的微信分享服务'
							});
						}
					},
					fail: (error) => {
						uni.showToast({
							title: '获取分享服务失败'
						});
					}
				});
			}
		},
		onUnload() {
			this.audioManager.stop();
			//this.audioManager=null;
		},
		methods: {
			rateChange(e){
				//if()
				
				var that=this;
				if(e==0)
				{
					if(that.rate>0.5)
					{
						that.rate-=0.5;
					}
				}else{
					if(that.rate<2.0)
					{
						that.rate+=0.5;
					}
				}
				if(that.userFavor!=0)
				{
					that.audioManager.pause();
					var currentTime=that.audioManager.currentTime;
					that.audioManager.title = that.banner.title;
					that.audioManager.singer="研知音";
					that.audioManager.playbackRate=that.rate;
					that.audioManager.startTime=currentTime;
					
					if(that.userFavor==1){
						that.audioManager.src=that.$server+"/newsaudio/"+that.banner.path;
					}
				}
				
			},
			clshow(){
				if(this.show==false && this.flag==true)
				{
					this.show=true;
					this.fab=false;
				}
			},
			initAudioManager(){
				var that=this;
				
				that.audioManager.coverImgUrl = that.$server+'/images/1.jpeg';
				that.audioManager.startTime=0;
				
				that.audioManager.onWaiting(function(){
					
					wx.showLoading({
						title:"缓冲中"
					});
				})
				that.audioManager.onCanplay(function(){
				    wx.hideLoading();
				});
				
				
				that.audioManager.onStop(function(){
					/* if(that.listData[that.playId].audioPlayback==true){
					that.play(that.playId);
					} */
					that.userFavor=0;
				});
				that.audioManager.onEnded(function(){
					that.userFavor=0;
				});
				that.audioManager.onPause(function(){
					//that.play(that.playId);
					that.userFavor=2;
				});
				that.audioManager.onPlay(function(){
					that.userFavor=1;
				})
			},
			play(){
				if(this.userFavor==1)
				{
					this.userFavor=2;
					
				
					this.audioManager.pause();
					//this.audioManager.startTime=this.audioManager.currentTime;
				}else if(this.userFavor==0){
				
				
				this.userFavor=1;
				//this.pre=index;
				
				this.playAudio();
				}else{
					
					
					this.userFavor=1;
					this.audioManager.play();
					
				}
				
				this.changeImage();
				
			},
			changeImage()
			{
				//console.log(this.image);
				if(this.userFavor==0)
				{
					this.image='../../static/icon/0.png';
				}else if(this.userFavor==1)
				{
					this.image='../../static/icon/1.png';
				}else{
					this.image='../../static/icon/2.png';
				}
				//console.log(this.image);
			},
			playAudio()
			{
				var that=this;
				that.audioManager.title = that.banner.title;
				that.audioManager.singer="研知音";
				that.audioManager.startTime=0;
				that.audioManager.playbackRate=that.rate;
				that.audioManager.src=that.$server+"/newsaudio/"+that.banner.path;
				
			},
			
			drag_start(event){
				this.start[0]= event.touches[0].clientX-event.target.offsetLeft;
				this.start[1]= event.touches[0].clientY-event.target.offsetTop;
				
			},
			drag_hmove(event){
					let	 tag 	 = event.touches;
					if(tag[0].clientX < 0 ){
						tag[0].clientX = 0
					}
					if(tag[0].clientY < 0 ){
						tag[0].clientY = 0
					}
					if(tag[0].clientX > this.windowWidth ){
						tag[0].clientX = this.windowWidth
					}
					if(tag[0].clientY > this.windowHeight ){
						tag[0].clientY = this.windowHeight
					}
					this.moveX	 = tag[0].clientX-this.start[0];
					this.moveY	 = tag[0].clientY-this.start[1];
			},
			timestamp(timestamp) {
			    // formats格式包括
			    // 1. Y-m-d
			    // 2. Y-m-d H:i:s
			    // 3. Y年m月d日
			    // 4. Y年m月d日 H时i分
								  var formats="Y-m-d H:i";
			    formats = formats || 'Y-m-d';
			
			    var zero = function (value) {
			        if (value < 10) {
			            return '0' + value;
			        }
			        return value;
			    };
			
			    var myDate = timestamp? new Date(parseInt(timestamp)): new Date();
			
			    var year = myDate.getFullYear();
			    var month = zero(myDate.getMonth() + 1);
			    var day = zero(myDate.getDate());
			
			    var hour = zero(myDate.getHours());
			    var minite = zero(myDate.getMinutes());
			    var second = zero(myDate.getSeconds());
								  //return year+"-"+month+"-"+day+" "+hour+":"+minite+":"+second;
			
			   return formats.replace(/Y|m|d|H|i|s/ig, function (matches) {
			        return ({
			            Y: year,
			            m: month,
			            d: day,
			            H: hour,
			            i: minite,
			            s:second
			        })[matches];
			    });
			},
			getDetail() {
				uni.showLoading({
					title:"加载中"
				})
				uni.request({
					/* url: 'https://unidemo.dcloud.net.cn/api/news/36kr/' + this.banner.post_id, */
					url:this.$server+"/news/detail?id="+this.banner.id,
					success: (data) => {
						if (data.statusCode == 200 && data.data.code!=undefined && data.data.code==200 && data.data.data!=null) {
							data = data.data.data;
							
							var htmlString = data.content.replace(/\\/g, "").replace(/<img/g, "<img style=\"display:none;\"");
							this.htmlNodes = htmlParser(htmlString);
							this.banner.author_name=data.authorName;
							this.banner.published_at =this.timestamp(data.publishedAt);
							this.banner.path=data.path;
							
							uni.hideLoading();
							this.flag=true;
						}else{
							uni.hideLoading();
							uni.showToast({
								icon:"none",
								title:"暂无数据"
							})
						}
					},
					fail: () => {
						uni.hideLoading();
						uni.showToast({
							icon:"none",
							title:"暂无数据"
						})
					}
				});
			}
		}
	}
</script>

<style>
	page{
		background-color: #ffffff;
	}
	
	/* 头条小程序组件内不能引入字体 */
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
		color: #FFFFFF;
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
		background-color: #007AFF;
	}
	
	.word-btn--hover {
		background-color: #4ca2ff;
	}
	.banner {
		height: 360rpx;
		overflow: hidden;
		position: relative;
		background-color: #ccc;
	}

	.banner-img {
		width: 100%;
	}

	.banner-title {
		max-height: 84rpx;
		overflow: hidden;
		position: absolute;
		left: 30rpx;
		bottom: 30rpx;
		width: 90%;
		font-size: 32rpx;
		font-weight: 400;
		line-height: 42rpx;
		color: white;
		z-index: 11;
	}

	.article-meta {
		padding: 20rpx 40rpx;
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		color: gray;
	}

	.article-text {
		font-size: 26rpx;
		line-height: 50rpx;
		margin: 0 20rpx;
	}

	.article-author,
	.article-time {
		font-size: 30rpx;
	}

	.article-content {
		padding: 0 30rpx;
		overflow: hidden;
		font-size: 30rpx;
		margin-bottom: 30rpx;
	}
	
	.holdon{
		width: 100%;
		height: 100%;
	}
	.ball{
		width: 70upx;height: 70upx;
		/* background:linear-gradient(to bottom, #F8F8FF,#ffffff); */
		background:transparent;
		border-radius: 50%;
		
		color: #fff;
		font-size: 30upx;
		display: flex;justify-content: center;align-items: center;
		position: fixed !important;
		z-index: 1000000;
	}
</style>
