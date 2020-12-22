<template>
	<view class="wrap" v-if="flag==true">
	
		<!-- <cl-card label="真题" padding="0" v-if="exams.length>0">
			
			<cl-list :label="exam.name" v-for="(exam,index) in exams"  :key="index" swipe="right"  @click="to(index)">
				
			</cl-list>
		</cl-card> -->
		<view class="nav-wrap">
			<view class="nav-title">
				<!-- <image class="logo" src="https://cdn.uviewui.com/uview/common/logo.png" mode="widthFix"></image> -->
				<view class="nav-info">
					<view class="nav-title__text">
						历年真题
					</view>
					<view class="nav-slogan">
						<!-- 看一下自己掌握知识的程度吧 -->
					</view>
				</view>
			</view>
			<view class="nav-desc">
				
			</view>
			
		</view>
			
		<view class="list-wrap">
			<u-cell-group title-bg-color="rgb(243, 244, 246)">
				<u-cell-item :titleStyle="{fontWeight: 500}" :is-link="false"  :title="getFieldTitle(item1)" :arrow="false" 
				 v-for="(item1, index1) in exams" :key="index1" @click="to(index1)">
					<image slot="icon" class="u-cell-icon" :src="getIcon(item1.icon)" mode="widthFix"></image>
				</u-cell-item>
			</u-cell-group>
		</view>
		
		
		
		<view>
			<cl-popup :visible.sync="examshow" direction="bottom">
				<view class="demo-icon">
					<cl-card label="操作">
						<cl-row>
							<cl-col span="8" >
								<view class="block" @click="operate(0)">
									<text class="cuIcon-write lg text-gray" style="font-size: 44rpx;"></text>
									<text class="label">开始练习</text>
								</view>
							</cl-col>
							
							<cl-col span="8" v-if="exam!=null&& exams[exam].createAt=='t'">
								<view class="block" @click="operate(2)">
									<text class="cuIcon-text lg text-gray" style="font-size: 44rpx;"></text>
									<text class="label">继续练习</text>
								</view>
							</cl-col>
							
							
							
							
							<cl-col span="8" >
								<view class="block" @click="operate(1)">
									<text class="cuIcon-down lg text-gray" style="font-size: 44rpx;"></text>
									<text class="label">下载</text>
								</view>
							</cl-col>
						</cl-row>
					</cl-card>
				</view>
				
			</cl-popup>
		</view>
		
		
	</view>
</template>

<script>
import iconList from "common/components.config.js";	
export default {
	data() {
		return {
			exam:null,
			examshow:false,
			flag:false,
			exams:[],
			iconlist:[],
		};
	},
	onLoad(){
		/* uni.setNavigationBarTitle({
			title: "真 题"
		});	 */
		this.initIcon();
		this.loadData();
		
		
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
		getGroupTitle(item) {
			return  item.subName 
		},
		getFieldTitle(item) {
			return item.name 
		},
		operate(i)
		{
		  if(this.examshow && this.exam!=null)
		  {
			  var e = this.exams[this.exam];
			  if(i==0)
			  {
				  
				  /* uni.navigateTo({
				  	url:'/pages/exam/historyExamQuestions?eid='+exam.id    //'eid='+this.exam.id
				  }) */
				  wx.setStorageSync("eid",-1);
				  uni.navigateTo({
				  	url:"/pages/exam/start?eid="+e.id
				  })
				 
			  }else if(i==1){
				  this.download(this.exam);
				 //this.del(this.exam);
				  
			  }else{
				  //重新开始
				  wx.setStorageSync("eid",e.id);
				  uni.navigateTo({
				  	url:"/pages/exam/start?eid="+e.id
				  })
				  
			  }
			  
		  }else{
			  uni.showToast({
			  	icon:"none",
				title:"出现错误了"
			  })
		  }
		},
		to(e)
		{
			
			/* uni.navigateTo({
				url:"/pages/exam/index?eid="+e.id
			}) */
			
			this.exam=e;
			this.examshow=true;
		},
		timestamp(timestamp) {
		    // formats格式包括
		    // 1. Y-m-d
		    // 2. Y-m-d H:i:s
		    // 3. Y年m月d日
		    // 4. Y年m月d日 H时i分
		    var formats="Y-m-d H:i:s";
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
		            s: second
		        })[matches];
		    });
		},
		download1(exam)
		{
			var that=this;
			var eid=exam.id;
			console.log(that.$server);
			uni.showLoading({
				title:"正在下载"
			})
			uni.request({
				url: that.$server+"/exam/download?eid="+eid,
				method: "GET",
				dataType: 'json',
				withCredentials:true,
				header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
				success(res) {
					if(res.data.code==200)
					{
						var url=that.$server+"/paper/"+res.data.data.path+".pdf";
						
						//url="http://cn.createpdfonline.org/pdffiles/hello%20word(20200623144016).pdf";
					/* 	var filename = url.split('/');
						filename=filename[filename.length-1]; */
						 
						 const downloadTask = uni.downloadFile({
						    url: url, //仅为示例，并非真实的资源
						    success: (res) => {
						        if (res.statusCode === 200) {
						            uni.saveFile({
						                tempFilePath: res.tempFilePath,
						                    success: function(result) {
						                        //that.savedFilePath = res.savedFilePath;
												
												uni.hideLoading();
												uni.showToast({
													icon:"none",
													title:"下载成功"
												})
												
												
											   
						                   },
						            		fail:function(res)
						            		{
												uni.showToast({
													icon:"none",
													title:"下载失败"
												})
						            	      
						            		}
						                });
									
						        }else{
									uni.showToast({
										icon:"none",
										title:"下载失败"
									})
								}
		
						        let that = this;
						       
						        },
								fail(res){
									uni.showToast({
										icon:"none",
										title:"下载失败"
									})
								}
						    });
							
							
		
					}else{
						uni.showToast({
							icon:"none",
							title:"下载失败"
						})
					}
				},
				fail(res) {
					console.log(res);
					uni.showToast({
						icon:"none",
						title:"网络连接失败"
					})
				},
				complete() {
		
				}
			})
			
		
		
		},
		download(index)
		{
			
			var that=this;
			var exam=that.exams[index];
			if(exam.downloaded==false){
			var eid=exam.id;
			uni.showLoading({
				title:"正在下载"
			})
			uni.request({
				url: that.$server+"/exam/download?eid="+eid,
				method: "GET",
				dataType: 'json',
				withCredentials:true,
				header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
				success(res) {
					if(res.data.code==200)
					{
						var url=that.$server+"/paper/"+res.data.data.path+".pdf";
						//console.log(url);
						//url="http://cn.createpdfonline.org/pdffiles/hello%20word(20200623144016).pdf";
						/* var filename = url.split('/');
						filename=filename[filename.length-1]; */
						
						var temp = url.split("/");
						var fileName =temp[temp.length-1];
						wx.downloadFile({
						  url: url,
						  success: function(res) {
						    const manage = wx.getFileSystemManager();
						    if (res.statusCode === 200) {
							  var tempFilePath = res.tempFilePath;	
							  var dir=new Date().getTime();
							  	manage.mkdir({
							  		dirPath:wx.env.USER_DATA_PATH + "/"+dir,
									recursive: false,
							  		success(res){
							  			
										manage.saveFile({
										  tempFilePath: tempFilePath,
										  filePath: wx.env.USER_DATA_PATH + "/"+dir +"/"+ fileName,
										  success: function(res) {
											  
											 that.exams[index].downloaded=true;
											 uni.hideLoading();
											 uni.showToast({
											 	icon:"none",
											 	title:"下载成功"
											 })
										  },
										fail(res){
											uni.hideLoading();
											uni.showToast({
												icon:"none",
												title:"下载失败,请重试"
											});
										}
										});
										
										
										
							  		},
							  		fail(res){
										uni.hideLoading();
							  			uni.showToast({
							  				icon:"none",
							  				title:"下载失败,请重试"
							  			});
							  		}
							  	});
						 
						    }else{
								uni.hideLoading();
								uni.showToast({
									icon:"none",
									title:"下载失败,请重试"
								});
							}
							
							
							
							
						   
						  }
						});
						
						
		
					}else{
						uni.hideLoading();
						uni.showToast({
							icon:"none",
							title:"下载失败"
						})
					}
				},
				fail(res) {
					uni.hideLoading();
					console.log(res);
					uni.showToast({
						icon:"none",
						title:"网络连接失败"
					})
				},
				complete() {
		
				}
			})
			}else{
				uni.showToast({
					icon:"none",
					title:"下载成功"
				})
			}
			
		
		
		},
		
		
		
		/* to(e)
		{
			
			uni.navigateTo({
				url:"/pages/exam/start?eid="+e.id
			})
		}, */
		loadData()
		{
			uni.showLoading({
				title:"加载中"
			});
			var that=this;
			 uni.request({
				url: this.$server+"/exam/zt",
				withCredentials:true,
				header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
				success(res){
					if(res.data.code==200){
					 if(res.data.data!=null && res.data.data.length>0)
					 {
						 that.exams=res.data.data;
						 var l =that.exams.length;
						 for(var i=0;i<l;++i)
						 {
							 var l2=that.iconlist.length-1;
							 var index = Math.floor(Math.random() * (l2 - 0 + 1)) + 0 ;
							
							 //that.$set(that.exams[i],"icon",that.iconlist[index]);
							 that.exams[i].icon=that.iconlist[index];
							 that.iconlist.splice(index,1);
							 that.exams[i].downloaded=false;
						 }
						 
						 uni.hideLoading();
						 that.flag=true;
					 }else{
						 uni.hideLoading();
						 uni.showToast({
						 	icon:"none",
						 	title:"无数据"
						 })
					 }
					}else{
						 uni.hideLoading();
						uni.showToast({
							icon:"none",
							title:"出现问题了"
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
			
		}
	}
};
</script>

<style lang="scss" scoped>
	.demo-tabs {
		height: 100%;
		overflow: hidden;
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
.demo-list {
	/deep/.cl-card__container {
		padding: 0;
	}

	.cs-block {
		display: flex;
		align-items: center;
		width: 100%;
		padding: 20rpx 10rpx;

		/deep/.cl-avatar {
			height: 100rpx;
			width: 100rpx;
			margin-right: 30rpx;
			border-radius: 10rpx;
		}
	}

	.append {
		display: flex;
		align-items: center;
		height: 100%;

		.primary-btn,
		.error-btn {
			border-radius: 0;
			height: 100rpx;
			width: 130rpx;
			display: flex;
			justify-content: center;
			align-items: center;
			font-size: 26rpx;
			color: #fff;
			box-sizing: border-box;
			margin: 0;

			&::after {
				border: 0;
			}
		}

		.primary-btn {
			background-color: $uni-color-primary;
		}

		.error-btn {
			background-color: $uni-color-error;
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
