<template>
	<view class="qiun-columns"  v-if="subjectList!=null">
		<view class="qiun-padding" style="font-size: 32rpx;">
			<text>{{tips}}</text>
		</view>
   <!-- <view class="qiun-padding">
    	<view class="qiun-tip" @tap="changeData()">更新柱状图数据</view>
    </view> -->
        <view class="nav-wrap">
        	<view class="nav-title" v-if="flag==true">
        		<!-- <image class="logo" src="https://cdn.uviewui.com/uview/common/logo.png" mode="widthFix"></image> -->
        		<view class="nav-info">
        			<view class="nav-title__text">
        				考试结果
        			</view>
        			<view class="nav-slogan">
        				看一下自己掌握知识的程度吧
        			</view>
        		</view>
        	</view>
        	<view class="nav-desc">
        		
        	</view>
        	
        </view>
		<view class="qiun-bg-white qiun-title-bar qiun-common-mt" v-if="subjectList!=null && flag==true">
			<view class="qiun-title-dot-light">结果</view>
		</view>
		<view  v-if="subjectList!=null && flag==true">
		<view class="grid col-5 " style="background-color: #ffffff;">
			<view class="margin-tb-sm text-center" v-for="(subject,index) in subjectList" :key="index">
				<button class="cu-btn round" :class="[subject.userAnswer.length===0?'line-grey':((subject.userAnswer==subject.answer)?'bg-green':'bg-red')]" v-if="subject.type<5">{{index+1}}</button>
			</view>
			
		</view>
		<view class="padding" style="background-color: #ffffff;">
			
			
			<view class="cu-capsule radius" style="margin-left:30%">
				<view class='cu-tag bg-green sm'>
					正确
				</view>
				<view class="cu-tag line-green sm">
					{{rightNum}}
				</view>
			</view>
			<view class="cu-capsule radius" style="margin-left:10%">
			<view class='cu-tag bg-red sm'>
				错误
			</view>
			<view class="cu-tag line-red sm">
				{{wrongNum}}
			</view>
		</view>
		<!-- <view class="cu-capsule radius" style="margin-left:10%">
			<view class='cu-tag line-black sm'>
				未做
			</view>
			<view class="cu-tag line-black sm">
				23
			</view>
		</view> -->
		</view>
		
		
		</view>
		
		
		<view class="qiun-bg-white qiun-title-bar qiun-common-mt" v-if="flag==true && list.length>0">
			<view class="qiun-title-dot-light">知识点掌握情况</view>
		</view>
		
		<view class="list-wrap" v-if="flag==true && list.length>0">
			<u-cell-group title-bg-color="rgb(243, 244, 246)" :title="getGroupTitle(item)" v-for="(item, index) in list" :key="index">
				<u-cell-item :titleStyle="{fontWeight: 500}" :is-link="false"  :title="getFieldTitle(item1)" :arrow="false" :value="'正确:'+item1.rightNum+'/'+'共:'+item1.num"
				 v-for="(item1, index1) in item.chapter" :key="index1" >
					<image slot="icon" class="u-cell-icon" :src="getIcon(item1.icon)" mode="widthFix"></image>
				</u-cell-item>
			</u-cell-group>
			 
		</view>
		<view class="list-wrap" v-if="flag==true">
		<button type="default" @click="jump(0)" plain="true" style="margin-top: 20rpx;margin-bottom: 20rpx; width: 80%;background-color: #39b54a;color: white;border-color:#39b54a ;">查 看 答 案</button>
		<!-- <button type="default" @click="jump(1)" plain="true" style="margin-top: 20rpx;margin-bottom: 20rpx; width: 80%;background-color: #39b54a;color: white;border-color:#39b54a ;">观看广告查看详细答案</button> -->
		</view>
		<!-- <view class="qiun-charts" v-if="flag==true">
			
		</view>	 -->
		
		
		
		
	</view>
</template>

<script>
	import iconList from "common/components.config.js";
	var _self;
	var canvasObj = {};
   /* let videoAd = null; */
	export default {
		data() {
			return {
				ended:false,
				list: [],
				type:null,
				iconlist:[],
				subjectList:null,
				eid:null,
				rightNum:0,
				wrongNum:0,
				flag:false,
				exam:null,
				num:0,
				map:new Map()
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
		onLoad(option) {
			this.eid=parseInt(option.eid);
			this.initData();
			this.loadData1();
			this.initIcon();
			/* var that=this; */
			/* 
			
			if (wx.createRewardedVideoAd) {
			  videoAd = wx.createRewardedVideoAd({
			    adUnitId: 'adunit-59b2e622a879d0be'
			  })
			  videoAd.onLoad(() => {})
			  videoAd.onError((err) => {})
			  videoAd.onClose((res) => {
				  if (res && res.isEnded) {
				         正常播放结束，可以下发游戏奖励
						
						that.ended=true;
						uni.navigateTo({
							url:'/pages/exam/answers?eid='+that.eid
						})
				      } else {
				         播放中途退出，不下发游戏奖励
				      }
			  })
			}
			 */

			//this.fillData(Data);
		},
		onReady() {
			
			
		},
		methods: {
			jump(e){
				// 用户触发广告后，显示激励视频广告
				/*  MP-WEIXIN || MP-BAIDU || MP-QQ
				if (videoAd) {
				  videoAd.show().catch(() => {
				     失败重试
				    videoAd.load()
				      .then(() => videoAd.show())
				      .catch(err => {
				        console.log('激励视频 广告显示失败')
				      })
				  })
				}
				*/
				
				if(e==0){
				uni.navigateTo({
					url:'/pages/exam/answers?eid='+this.eid
				})
				}else{
					if(this.ended==true)
					{
						uni.navigateTo({
							url:'/pages/exam/answers?eid='+this.eid
						})
					}
				}
			},
			initUserResult(subjectList)
			{
				var that=this;
					var url=that.$server+"/chapter/getCidAndQid?eid="+that.eid;
					                     uni.request({
											url:url ,
											withCredentials:true,
											header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
											success(res){
												if(res.data.code!=undefined && res.data.code==200 && res.data.data!=undefined && res.data.data!=null && res.data.data.length>0)
												{
													
													var data=res.data.data;//qid cid   cid num 
													
													var map =new Map();
													for(var i=0;i<subjectList.length;++i)
													{
														
													   	
													   map.set(subjectList[i].qid,subjectList[i].userAnswer==subjectList[i].answer?1:-1);	
														
													}
													var map2 =new Map();
													for(var j=0;j<data.length;++j)
													{
													    var temp=0;
														if(map2.get(data[j].num)!=undefined)
															{
																temp=map2.get(data[j].num);
															}
															
															if(map.get(data[j].pid)==1)
															{
																map2.set(data[j].num,temp+1);  //只统计对的 错的和没写的就不统计了
															}
														
														
													}
													that.map=map2;
													
													
			
												}
												that.loadData();
											},
											fail(res){} 
				   	
				});
				
				
			},
			initData()
			{
				var that=this;
				var url=that.$server+"/exam/getExam?eid="+that.eid;
				                     uni.request({
										url:url ,
										withCredentials:true,
										header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
										success(res){
											if(res.data.code==200)
											{
												that.exam=res.data.data;
												
												
											}
										},
										fail(res){} 
			   	
			});
			},
			
			loadData1()
			{
				uni.showLoading({
					title:"加载中"
				});
				var that=this;
				var url=this.$server+"/exam/answer?eid="+this.eid
				 uni.request({
					url:url ,
					withCredentials:true,
					header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
					success(res){
						
						if(res!=undefined && res.data.code==200){
						  if(res.data.data!=undefined && res.data.data!=null && res.data.data.length>0){
							  that.subjectList=res.data.data;
							  var subjectList=that.subjectList;
							  for(var i=0;i<subjectList.length;++i)
							  {
								  if(subjectList[i].userAnswer==null)
								  {
									  subjectList[i].userAnswer="";
								  }
								  if(subjectList[i].userAnswer.length>0)
								  {
									  if(subjectList[i].answer==subjectList[i].userAnswer){
									  that.rightNum++;
									  }else{
										  that.wrongNum++;
									  }
								  }
							  }
							  that.subjectList=subjectList;
							  that.initUserResult(subjectList);
							  
							 /* that.flag=true;
							  uni.hideLoading(); */
							  
						  }else{
							/*  uni.hideLoading();
							  uni.showToast({
								  icon:"none",
								  title:"出现问题了"
							  }) */
							  
						  }
					}
					
				},
				fail(res){
					
				}});
				
				
				
				
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
			
			
			loadData:async function()
			{
				/* uni.showLoading({
					title:"加载中"
				}); */
				var that=this;
				for(var pid=1;pid<6;++pid)
				{
				 var obj = await that.request(pid);	
				
				/* if(obj.subName!=undefined)
				 {
					 that.list.push(obj);
					 uni.hideLoading();
					 that.flag=true;
				 } */
				  
				}  
				/* if(that.flag==false)
				{
					if(that.list.length>0)
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
				} */
				/* console.log(22222);
				console.log(this.list.length); */
							
			},
			request(pid){
				var that=this;
				
			 return new Promise(resolve=>{
				   var url=that.$server+"/exam/getChapters?pid="+pid+"&eid="+that.eid;
				   
				   uni.request({
				   						url:url ,
				   						withCredentials:true,
				   						header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
				   						success(res){
											//resolve(res.data);
				   							if(res!=undefined && res.data.code==200){
				   										if(res.data.data!=null && res.data.data.length>0){
				   											
				   										var chapters=res.data.data;
				   										
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
				   										
				   										var obj={
				   											subName:subName,
				   											chapter:[]
				   										};
				   										
				   											
				   										for(var i=0;i<chapters.length;++i)
				   										{
															
															var num1 = 0;
															
															
															if(that.map!=null)
															{
																console.log(chapters[i].id);
																if(that.map.get(chapters[i].id)!=undefined)
																{
																	num1=that.map.get(chapters[i].id);
																}
															}
				   											var chapter={
				   											    icon:null,
				   												title:chapters[i].name.length>14?chapters[i].name.substring(0,14)+"...":chapters[i].name,
				   												num:chapters[i].uqnum,
				   												id:chapters[i].id,
																rightNum:num1
				   											
				   											};
				   											var l=that.iconlist.length-1;
				   											var index = Math.floor(Math.random() * (l - 0 + 1)) + 0 ;
				   											//console.log(that.iconlist[index]);
				   											chapter.icon=that.iconlist[index];
				   											that.iconlist.splice(index,1);
				   											obj.chapter.push(chapter);	
															that.num+=chapters[i].uqnum;
				   										
				   										}
														that.list.push(obj);
														
				   										//resolve(obj);
				   										//console.log(listData[pid-1].data[0].subList);
				   										/* uni.hideLoading();
				   										that.flag=true; */
				   										/* if(this.list.length>0)
				   										{
				   											uni.hideLoading();
				   											this.flag=true;
				   										}else{
				   											uni.hideLoading();
				   											uni.showToast({
				   												icon:"none",
				   												title:"暂无数据"
				   											});
				   										} */
				   										
				   									     
				   									}
				   								
				   							}
											//console.log(pid);
											if(pid==5)
											{
												var otherRight=0;
												that.map.forEach((e,k,v)=>{
													
													
													
													otherRight+=e;
												});
												
											   var num1= that.exam.type<2?38:15;
											   if(that.num<num1){
											   var obj={
											   	subName:"其他",
											   	chapter:[]
											   };
											   var chapter={
											       icon:null,
											   	title:"其他",
											   	num:num1-that.num,
											   	id:0,
												rightNum:that.rightNum-otherRight
												
											   
											   };
											   var l=that.iconlist.length-1;
											   var index = Math.floor(Math.random() * (l - 0 + 1)) + 0 ;
											   //console.log(that.iconlist[index]);
											   chapter.icon=that.iconlist[index];
											   that.iconlist.splice(index,1);
											   obj.chapter.push(chapter);
											   that.list.push(obj);
											   //resolve(obj);
											   
											   
											   
											}
											
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
											resolve(res.data);
											
				   						},
				   						fail(res){
											
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
											resolve(res.data);
										}
				   					});
			   }).catch((e)=>{console.log(e);});	
				
					
			},
			getGroupTitle(item) {
				return  item.subName 
			},
			getFieldTitle(item) {
				return item.title 
			},
			
	}
	}
</script>

<style>
	page {
		background: #F2F2F2;
		width: 750rpx;
		overflow-x: hidden;
	}

	.qiun-padding {
		padding: 2%;
		width: 96%;
	}

	.qiun-wrap {
		display: flex;
		flex-wrap: wrap;
	}

	.qiun-rows {
		display: flex;
		flex-direction: row !important;
	}

	.qiun-columns {
		display: flex;
		flex-direction: column !important;
	}

	.qiun-common-mt {
		margin-top: 10rpx;
	}

	.qiun-bg-white {
		background: #FFFFFF;
	}

	.qiun-title-bar {
		width: 96%;
		padding: 10rpx 2%;
		flex-wrap: nowrap;
	}

	.qiun-title-dot-light {
		border-left: 10rpx solid #0ea391;
		padding-left: 10rpx;
		font-size: 32rpx;
		color: #000000
	}

	/* 通用样式 */
	.qiun-charts {
		width: 750rpx;
		height: 500rpx;
		background-color: #FFFFFF;
	}

	.charts {
		width: 750rpx;
		height: 500rpx;
		background-color: #FFFFFF;
	}

	/* 横屏样式 */
	.qiun-charts-rotate {
		width: 700rpx;
		height: 1100rpx;
		background-color: #FFFFFF;
		padding: 25rpx;
	}

	.charts-rotate {
		width: 700rpx;
		height: 1100rpx;
		background-color: #FFFFFF;
	}

	/* 圆弧进度样式 */
	.qiun-charts3 {
		width: 750rpx;
		height: 250rpx;
		background-color: #FFFFFF;
		position: relative;
	}

	.charts3 {
		position: absolute;
		width: 250rpx;
		height: 250rpx;
		background-color: #FFFFFF;
	}

	.qiun-tip {
		display: block;
		width: auto;
		overflow: hidden;
		padding: 15rpx;
		height: 30rpx;
		line-height: 30rpx;
		margin: 10rpx;
		background: #ff9933;
		font-size: 30rpx;
		border-radius: 8rpx;
		justify-content: center;
		text-align: center;
		border: 1px solid #dc7004;
		color: #FFFFFF;
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
