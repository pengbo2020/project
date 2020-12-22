<template>
	
	<!-- <view class="demo-tabs" v-if="flag==true" >
				<view v-for="item in list[0].data"  v-if="flag==true">
					<uni-collapse ref="add" class="block" >
						<uni-collapse-item v-for="(sub, key) in item.data" :key="key" :open="sub.open" :show-animation="sub.showAnimation" :disabled="sub.disabled" :title="sub.subName" v-if="sub.subName!='分析题'">
							<template >
								<uni-list>
									<uni-list-item v-for="(list2, listIndex) in sub.subList" :key="listIndex" :title="list2.name" :note="list2.note" :thumb="list2.thumb" :show-extra-icon="list2.showExtraIcon" :extra-icon="list2.extraIcon" :show-switch="list2.showSwitch" @switchChange="change"  @click="question(list2.id)" />          
								</uni-list>
							</template>
						</uni-collapse-item>
					</uni-collapse>
				</view>
			
		
		
	</view> -->
	<view class="wrap" v-if="flag==true">
		
		<view class="nav-wrap">
			<view class="nav-title">
		
				<view class="nav-info">
					<view class="nav-title__text">
						收藏的知识点
					</view>
					<view class="nav-slogan">
						
					</view>
				</view>
			</view>
			<view class="nav-desc">
				
			</view>
			
		</view>
		<view v-for="chapter in list">
	     <view class="nav-title__text" style="margin-left: 4%;font-size: 32rpx;margin-bottom: -20rpx;">
	     	{{chapter.subName}}
	     </view>
		<view class="list-wrap" style="margin-bottom: 20rpx;">
			<u-cell-group title-bg-color="rgb(170, 85, 0)" :title="item.chapter.name" v-for="(item, index) in chapter.list" :key="index" v-if="item.chapter.name!='分析题'" >
				<u-cell-item :titleStyle="{fontWeight: 500}" :is-link="false"  :title="item1.name.length>14?(item1.name.substring(0,14)+'...'):item1.name" :arrow="false" :value="item.uknumList[index1]+'个'"
				 v-for="(item1, index1) in item.childChapterList" :key="index1" @click="toKnowledge(item1.id)">
					<image slot="icon" class="u-cell-icon" :src="getIcon(item1.icon)" mode="widthFix"></image>
				</u-cell-item>
			</u-cell-group>
		</view>
		</view>
		<!-- <u-gap height="70"></u-gap> -->
		<!-- <u-tabbar :list="vuex_tabbar" :mid-button="true"></u-tabbar> -->
		
	</view>
</template>

<script>
import iconList from "common/components.config.js";
export default {
	data() {
		return {
			pid:null,
			examshow:false,
			flag:false,
			exam:null,
			exams:[],
			tabIndex: 0,
			hasData1:0,
			hasData2:0,
			labels: [
			],
			list: [],
			swiperHeight: '800px',
			iconlist:[]
		};
	},
	onReady() {
		var tempHeight = 800;
		var _me = this;
		/* uni.getSystemInfo({
			//获取手机屏幕高度信息，让swiper的高度和手机屏幕一样高                
			success: function(res) {
			                   
				tempHeight = res.windowHeight;
		
				uni.createSelectorQuery().select("#top-box").fields({
					size: true,
					scrollOffset: true
				}, (data) => {
					tempHeight -= data.height;
				
					_me.swiperHeight = tempHeight + 'px';
	
				
	
				}).exec();
			}
		}); */
		
		
	
	},
	onLoad(option){
		
		/* uni.setNavigationBarTitle({
			title: "历 史 "
		});	 */
		this.pid = parseInt(option.pid);
		
		
		this.initIcon();
		/* this.load(); */
		this.loadData();
		
	},
	methods:{
		toKnowledge(e){
			uni.navigateTo({
				url:'/pages/knowledge/collectionKnow?cid='+e
			})
			/* console.log(e); */
		},
		getIcon(path) {
			/* console.log(path);
			return path => {
				return 'https://cdn.uviewui.com/uview/example/' + path + '.png';
			} */
			return 'https://cdn.uviewui.com/uview/example/' + path + '.png';
		},
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
		load:async function()
		{
			uni.showLoading({
				title:"加载中"
			});
			var that = this;
			var url=this.$server+"/chapter-knowledge/getCollectionList?pid="+this.pid;
			 uni.request({
					url:url ,
					withCredentials:true,
					header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
					success(res){
						if(res!=undefined && res.data.code==200){
										if(res.data.data!=undefined && res.data.data.length>0)
										{
											var data=res.data.data;	
											for(var i=0;i<data.length;++i)
											{
												
												for(var j=0;j<data[i].childChapterList.length;++j)
												{
													var l=that.iconlist.length-1;
													if(l<=1)
													{
														that.initIcon();
														l=that.iconlist.length-1;
													}
													var index = Math.floor(Math.random() * (l - 0 + 1)) + 0 ;
													data[i].childChapterList[j].icon=that.iconlist[index];
													that.iconlist.splice(index,1);
												}
											}
											that.list=data;
										
											uni.hideLoading();
											that.flag=true;
											
										}else{
											var mes=null;
											if(res.data.data=undefined)
											{
												mes="出现问题了";
											}else{
												mes="暂无数据";
											}
											uni.hideLoading();
											
											uni.showToast({
												icon:"none",
												title:mes
											})
										}
										
										
						
								
							
						}else{
							var mes=null;
							if(res.data.data=undefined)
							{
								mes="出现问题了";
							}else{
								mes="暂无数据";
							}
							uni.hideLoading();
							
							uni.showToast({
								icon:"none",
								title:mes
							})
						}
					},
					fail(res){
						uni.hideLoading();
						
						uni.showToast({
							icon:"none",
							title:"出现问题了"
						})
					}
				});
				
			
				
				
				
			
				
		},
		request(pid){
			var that=this;
		   return new Promise(resolve=>{
			   var url=this.$server+"/chapter-knowledge/getCollectionList?pid="+pid;
			   uni.request({
			   						url:url ,
			   						withCredentials:true,
			   						header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
			   						success(res){
										//resolve(res.data);
			   							if(res!=undefined && res.data.code==200){
			   										if(res.data.data!=null && res.data.data.length>0){
			   											
			   										var data=res.data.data;
			   										
			   											//title="list.title" :note="list.note"
			   										var subName1="马克思主义基本原理概论";
			   										var subName2="毛泽东思想和中国特色社会主义理论体系概论";
			   										var subName3="中国近代史纲要";
			   										var subName4="思想道德修养与法律基础";
			   										var subName5="形势与政策以及当代世界经济与政治";
			   										var subName=null;
			   										
			   										switch(pid-1)
			   										{
			   											case 0:subName=subName1;break;
			   											case 1:subName=subName2;break;
			   											case 2:subName=subName3;break;
			   											case 3:subName=subName4;break;
			   											case 4:subName=subName5;break; 
			   										}
													
													
													for(var i=0;i<data.length;++i)
													{
														
														for(var j=0;j<data[i].childChapterList.length;++j)
														{
															var l=that.iconlist.length-1;
															if(l<=1)
															{
																that.initIcon();
																l=that.iconlist.length-1;
															}
															var index = Math.floor(Math.random() * (l - 0 + 1)) + 0 ;
															data[i].childChapterList[j].icon=that.iconlist[index];
															that.iconlist.splice(index,1);
														}
													}
													var obj={
														subName:subName,
														list:data
													};
													that.list.push(obj);
													
													

			   										
			   										
			   									}
												
			   								
			   							}
										if(pid==5)
										{
											
											if(that.list!=null&& that.list.length>0)
											{
												uni.hideLoading();
												that.flag=true;
											}else{
												uni.hideLoading();
												uni.showToast({
													icon:"none",
													title:"暂无数据"
												});
											}
										}
										resolve(res);
			   						},
			   						fail(res){
										resolve(res);
									}
			   					});
		   }).catch((e)=>{});	
			
				
		},
		loadData:async function()
		{
			uni.showLoading({
				title:"加载中"
			});
			var that=this;
			for(var pid=1;pid<6;++pid)
			{
			 var obj = await that.request(pid);	
		
			  
			}  
			
						
		},
		
		
		
		
		to(e)
		{
		
			this.exam=e;
			this.examshow=true;
		},
		
		question(i){
			
			uni.navigateTo({
				url:'/pages/knowledge/index?cid='+i
			});
			
		},
		
	}
};
</script>

<style lang="scss">
/* page {
	height: 100%;
	background-color: #ffffff;
} */
/* .demo-tabs {
	height: 100%;
	overflow: hidden;
} */

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
