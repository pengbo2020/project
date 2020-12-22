<template>
	<!-- <view class="demo-list" v-if="flag==true">
	
		<cl-card label="章节" padding="0" v-if="chapters.length>0">
			<cl-list :label="chapter.name" v-for="chapter in chapters" :key="chapter" @click="to(chapter)">
				<cl-icon slot="icon" name="cl-icon-info" :size="44"></cl-icon> 
			</cl-list>
			
		</cl-card>
		
	</view> -->
	
	<view class="wrap" v-if="flag==true">
		
		<view class="nav-wrap">
			<view class="nav-title">
				<!-- <image class="logo" src="https://cdn.uviewui.com/uview/common/logo.png" mode="widthFix"></image> -->
				<view class="nav-info">
					<view class="nav-title__text">
						专项练习
					</view>
					<view class="nav-slogan">
						每个知识点都要好好练习
					</view>
				</view>
			</view>
			<view class="nav-desc">
				{{extraDesc}}
			</view>
			
		</view>
	
		<view class="list-wrap">
			<u-cell-group title-bg-color="rgb(243, 244, 246)" :title="chapterName">
				<u-cell-item :titleStyle="{fontWeight: 500}" :is-link="false"  :title="item1.name" :arrow="false" :value="'已练'+(item1.uqnum==null?'0':item1.uqnum)+'/共'+item1.cqnum+'题'"
				 v-for="(item1, index1) in chapters" :key="index1" @click="to(item1)" v-if="item1.name.length>0">
					<image slot="icon" class="u-cell-icon" :src="getIcon(item1.icon)" mode="widthFix"></image>
				</u-cell-item>
			</u-cell-group>
		</view>
		
		
	</view>
	
	
</template>

<script>
	import iconList from "common/components.config.js";
	let interstitialAd=null;
	export default {
		data() {
			return {
				pid:null,
				pn:0,
				flag:false,
				chapters:[],
				iconlist:[],
				hasData:0,
				title:"",
				subTitle:"",
				extraDesc:"",
				chapterName:null
			}
		},
		onLoad(option){
			this.pid=parseInt(option.pid);
			// #ifdef MP-WEIXIN || MP-BAIDU || MP-QQ
			
			// 在页面onLoad回调事件中创建插屏广告实例
			if (wx.createInterstitialAd) {
			  interstitialAd = wx.createInterstitialAd({
			    adUnitId: 'adunit-fd162fca2f3524bb'
			  })
			  interstitialAd.onLoad(() => {})
			  interstitialAd.onError((err) => {})
			  interstitialAd.onClose(() => {})
			}
			
			// 在适合的场景显示插屏广告
			if (interstitialAd) {
			  interstitialAd.show().catch((err) => {
			    console.error(err)
			  })
			}
			
				//#endif
			
			this.initIcon();
			this.loadData();
			var subName1="马克思主义基本原理概论";
			var subName2="毛泽东思想和中国特色社会主义理论体系概论";
			var subName3="中国近代史纲要";
			var subName4="思想道德修养与法律基础";
			var subName5="形势与政策以及当代世界经济与政治";
			switch(this.pid)
			{
				case 1:this.chapterName=subName1;break;
				case 2:this.chapterName=subName2;break;
				case 3:this.chapterName=subName3;break;
				case 4:this.chapterName=subName4;break;
				case 5:this.chapterName=subName5;break;
			}
			
			
			
			
		},
		
		computed: {
			getIcon() {
				return path => {
					return 'https://cdn.uviewui.com/uview/example/' + path + '.png';
				}
			},
			/* desc() {
				return this.$t('components.desc');
			} */
		},
		
		onShow(){
			if(this.JSESSIONID()!=null && this.chapters.length!=0)
			{
				this.initIcon();
				this.loadData();
			}
			
		},
		methods: {
			initIcon()
			{
				for(var i=0;i<iconList.length;++i)
				{
					for(var j=0;j<iconList[i].list.length;++j)
					{
						this.iconlist.push(iconList[i].list[j].icon);
					}
				}
				
			
				
			},
			loadData()
			{
				var that=this;
				uni.showLoading({
					title:"加载中"
				})
				uni.request({
					url:this.$server+"/chapter/get?pid="+this.pid,
					header: { Cookie: 'JSESSIONID=' + that.JSESSIONID() },
					withCredentials: true,
					success(res)
					{
						
						if(res.data.code==200)
						{
							if(res.data.data!=null && res.data.data.length>0)
							{
								that.chapters=res.data.data;
								for(var i=0;i<that.chapters.length;++i)
								{
									var l=that.iconlist.length-1;
									var index = Math.floor(Math.random() * (l - 0 + 1)) + 0 ;
									//console.log(that.iconlist[index]);
									that.chapters[i].icon=that.iconlist[index];
									if(that.chapters[i].name.length>13)
									{
										that.chapters[i].name=that.chapters[i].name.substring(0,14)+"...";
									}
									that.iconlist.splice(index,1);
									
									
								}
								
								
								that.flag=true;
								uni.hideLoading();
								
							}else{
							uni.hideLoading();
							uni.showToast({
								icon:"none",
								title:"无数据"
							});	
							}
						}else{
							uni.hideLoading();
							uni.showToast({
								icon:"none",
								title:"出现问题了"
							});
						}
					},
					fail(res)
					{
						uni.hideLoading();
						uni.showToast({
							icon:"none",
							title:"出现问题了"
						});
					}
				})
			},
			to(c)
			{
				
			    if(c.uqnum==null)
				{
					uni.navigateTo({
						url:'/pages/chapter/questions?cid='+c.id+"&type=1"
					})
				}else{
			    uni.showActionSheet({
			    	title:'',
			    	itemList: ['继续练习', '重新开始'],
			    	success: (e) => {
			    		if(e.tapIndex==0)
						{
							uni.navigateTo({
								url:'/pages/chapter/questions?cid='+c.id
							})
						}else{
							uni.navigateTo({
								url:'/pages/chapter/questions?cid='+c.id+"&type=1"
							})
						}
				
			    	}
			    })	
				}
				
				
				
			}
		}
	}
</script>

<style>
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
</style>
