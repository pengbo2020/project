<template>
	<view >
		
		<view class="banner" v-if="flag==true">
			<image class="banner-img" :src="banner.cover"></image>
			<view class="banner-title">{{ banner.title }}</view>
		</view>
		<view class="uni-list"  v-if="flag==true">
			<view class="uni-list-cell"  v-for="(value, key) in listData" :key="key" @click="goDetail(value)" >
				<view class="uni-media-list">
					<!-- <image class="uni-media-list-logo" :src="value.cover"></image> -->
					<view class="uni-media-list-body">
						<view class="uni-media-list-text-top">{{ value.title }}</view>
						<view class="uni-media-list-text-bottom">
							<text style="font-size: smaller;">来源:{{value.author_name}} </text>
							<text style="font-size: smaller;">{{ value.published_at }}</text>
							<!-- :class="[userFavor?'cl-icon-notification-fill':'cl-icon-notification']" -->
							<!-- <view @click.stop="play(key)"><text class="cl-icon-notification" :class="[value.audioPlayback?'cl-icon-notification-fill':'cl-icon-notification']"></text>{{value.audioPlayback?"暂停":"播报"}}</view> -->
						</view>
					</view>
				</view>
			</view>
		</view>
		<uni-load-more :status="status"  :icon-size="16" :content-text="contentText" v-if="flag==true"/>
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
import uniLoadMore from '@/components/uni-load-more/uni-load-more.vue';
var dateUtils = require('@/common/uni/util.js').dateUtils;
import uniFab from '@/components/uni-fab/uni-fab.vue'
let interstitialAd=null;
export default {
	components: {
		uniLoadMore,
		uniFab
	},
	data() {
		return {
			tabbarcurrent:0,
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
				/* {
					iconPath: "photo",
					selectedIconPath: "photo-fill",
					text: '放映厅',
					customIcon: false,
				}, */
				{
					iconPath: "/static/image/home/2.png",
					selectedIconPath: "/static/image/home/3.png",
					text: '听知识点',
					"pagePath": "/pages/index/index",
					midButton: true,
					customIcon: false,
				},
				/* {
					iconPath: "play-right",
					selectedIconPath: "play-right-fill",
					text: '直播',
					customIcon: false,
				}, */
				{
					iconPath: "/static/image/home/4.png",
					selectedIconPath: "/static/image/home/5.png",
					text: '练习',
					"pagePath": "/pages/index/practice",
					isDot: false,
					customIcon: false,
				}
			],
			flag:false,
			preIndex:null,
			audioPlayback:false,
			banner: {},
			listData: [],
			last_id: 0,
			reload: false,
			status: 'more',
			contentText: {
				contentdown: '上拉加载更多',
				contentrefresh: '加载中',
				contentnomore: '没有更多'
			},
			audioManager:wx.getBackgroundAudioManager(),
			playId:null,
		};
	},
	
	onLoad() {
		
		this.getBanner();
		this.getList();
		//this.initAudioManager();
		
		
		
		
		},
		/* onShow(){
			if (interstitialAd) {
			  interstitialAd.show().catch((err) => {
			    console.error(err)
			  })
			}
		}, */
	onPullDownRefresh() {
		this.reload = true;
		this.last_id = 0;
		//this.getBanner();
		this.getList();
	},
	onReachBottom() {
		if(this.status!="nomore"){
		this.status = 'more';
		this.getList();
		}
	},
	methods: {
		jump(e){
			console.log(e);
			switch(e)
			{
				case 2:uni.navigateTo({
					url:'/pages/index/practice'
				});this.tabbarcurrent=2;break;
				case 1:
				uni.navigateTo({
					url:'/pages/index/index?current='+1
				}); this.tabbarcurrent=1;console.log("hello"); break;
			}
		},
		
		initAudioManager(){
			var that=this;
			that.audioManager.coverImgUrl = '../../static/img/1.jpeg';
			that.audioManager.onWaiting(function(){
				
				wx.showLoadding({
					title:"缓冲中"
				});
			})
			that.audioManager.onCanplay(function(){
			    wx.hideLoading();
			});
			that.audioManager.onPrev(function(){
				
				if(0==that.playId)
				{
					that.playId=that.listData.length-1;
					
				}else{
					that.playId=that.playId-1;
				}
				that.play(that.playId);
				
			});
			that.audioManager.onNext(function(){
				if(that.listData.length-1==that.playId)
				{
					that.playId=0;
					
				}else{
					that.playId=that.playId+1;
				}

				that.play(that.playId);
			});
			that.audioManager.onStop(function(){
				/* if(that.listData[that.playId].audioPlayback==true){
				that.play(that.playId);
				} */
				that.listData[that.playId].audioPlayback=false;
			});
			that.audioManager.onPause(function(){
				that.play(that.playId);
			});
		},
		play(index){
			/* console.log(e);
			this.audioPlayback=true; */
			this.playId=index;
			if(!this.audioPlayback)
			{
				this.audioPlayback=true;
			}
			if(this.listData[index].audioPlayback)
			{
				this.listData[index].audioPlayback=false;
				this.audioManager.pause();
				//this.audioManager.startTime=this.audioManager.currentTime;
			}else{
			if(this.preIndex!=null)
			{
				this.listData[this.preIndex].audioPlayback=false;
			}
			this.listData[index].audioPlayback=true;
			this.preIndex=index;
			this.playId=index;
			this.playAudio();
			}
			
		},
		playAudio()
		{
			var that=this;
			that.audioManager.title = that.listData[that.playId].title;
			that.audioManager.singer="研知音";
			//that.audioManager.src="https://www.lztk.xyz/knowledgeaudio/3.mp3";
			that.audioManager.src=that.$server+"/newsaudio/"+that.listData[that.playId].id+".mp3";
		},
		getBanner() {
			var that=this;
			let data = {
				column: 'id,post_id,title,author_name,cover,published_at' //需要的字段名
			};
			
			uni.request({
				url: this.$server+'/image/getBannerImage',
				data: data,
				success(res) {
					
					uni.stopPullDownRefresh();
					if (res.statusCode == 200 && res.data!=undefined && res.data.code==200 && res.data.data!=null) {
						var data=res.data.data
						that.banner.cover=that.$server+"/images/"+data.path;
						that.banner.title=data.description;
					}else{
						that.banner.cover='../../static/img/0.jpeg';
						that.banner.title="听新闻,习政治";
					}
				},
				fail: (data, code) => {
					//console.log('fail' + JSON.stringify(data))
					that.banner.cover='../../static/img/0.jpeg';
					that.banner.title="听新闻,习政治";
				}
			});
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
		getList() {
			var that=this;
			var data = {
				/* column: 'id,post_id,title,author_name,cover,published_at' */ //需要的字段名
				pageNumber:0,
				pageSize:20
			};
			if (that.last_id) {
				//说明已有数据，目前处于上拉加载
				that.status =that.status!="nomore"?'loading':"nomore";
				data.pageNumber = that.last_id+1;
				data.time = new Date().getTime() + '';
				data.pageSize = 10;
			}
			if(that.status!="nomore"){
			 if(that.flag==false)
				 {
					 uni.showLoading({
					 	title:"加载中"
					 });
				 }
			uni.request({
				/* url: 'https://unidemo.dcloud.net.cn/api/news', */
				url:that.$server+'/news/getList',
				data: data,
				success: data => {
					if (data.statusCode == 200) {
						
						data=data.data.data;
						if(data!=undefined && data!=null && data.length>0){
						let list = that.setTime(data);
						that.listData = that.reload ? list : that.listData.concat(list);
						/* this.last_id = list[list.length - 1].id; */
						that.last_id = that.last_id+1;
						
						that.reload = false;
						if(that.flag==false)
						{
							uni.hideLoading();
							that.flag=true;
						}
						
						}else{
							that.status="nomore";
							if(that.flag==false)
							{
								uni.hideLoading();
								uni.showToast({
									icon:"none",
									title:"暂无数据"
								})
							}
						}
					}else{
						if(that.flag==false)
						{
							uni.hideLoading();
							uni.showToast({
								icon:"none",
								title:"出现问题了"
							})
						}
						
					}
				},
				fail: (data, code) => {
					if(that.flag==false)
					{
						uni.hideLoading();
						uni.showToast({
							icon:"none",
							title:"出现问题了"
						})
					}
				}
			});
			}
		},
		goDetail: function(e) {
			// 				if (!/前|刚刚/.test(e.published_at)) {
			// 					e.published_at = dateUtils.format(e.published_at);
			// 				}
			
			let detail = {
				author_name: e.authorName,
				cover: null,
				id: e.id,
				post_id: null,
				published_at: e.publishedAt,
				title: e.title
			};
			uni.navigateTo({
				url: '/pages/news/newsDetail_new?detailDate=' + encodeURIComponent(JSON.stringify(detail))
			});
		},
		setTime: function(items) {
			var newItems = [];
			var that=this;
			items.forEach(e => {
				newItems.push({
					audioPlayback:false,
					author_name: e.authorName,
					cover: null,
					id: e.id,
					post_id: null,
					published_at: that.timestamp(e.publishedAt),
					title: e.title
				});
			});
			return newItems;
		}
	}
};
</script>

<style>
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
		background-color: #efeff4;
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



	/* #ifndef APP-NVUE */
	page {
		height: 100vh;
	}

	/* #endif */

	.uni-fab-box {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		justify-content: center;
		position: absolute;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
	}

	.uni-padding-wrap {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}

	.word-btn {
		width: 250px;
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

.uni-media-list-logo {
	width: 180rpx;
	height: 140rpx;
}

.uni-media-list-body {
	height: auto;
	justify-content: space-around;
}

.uni-media-list-text-top {
	height: 74rpx;
	font-size: 28rpx;
	overflow: hidden;
}

.uni-media-list-text-bottom {
	display: flex;
	flex-direction: row;
	justify-content: space-between;
}
</style>
