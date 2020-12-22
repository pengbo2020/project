<template>
	<view class="page_edu">
		
		<view id="top-box" :class="flag==true?'cu-bar bg-white solid-bottom':'cu-bar bg-white'" >
			
			<view class="action text-black" >
				<text style="font-size:large">考研政治</text>
				
			</view>
			
			<view class="action" >
		
				<view class="action" @tap="visible = true" data-target="modalCard" padding-right="10px">
					<view class="cuIcon-cu-image">
						<cl-icon :name="visible ==false?'cl-icon-plus':'cl-icon-minus'" :size="45" ></cl-icon>
					</view>
		
				</view>
			
				</view>
		
		</view>
		
		<view class="page_edu_header" style="height: 30%;">
			
			<!-- <view class="header" >
				<image src="/static/icon_main.png" class="btn" @tap="visible = true"></image> 
				 <view class="input">
					<image src="/static/search.png" class="search"></image>
					<input type="text" value="" placeholder="搜索" />
				</view>
				<image src="/static/msg.png" class="btn"></image>
			</view> -->
			
			<view class="header_content" style="margin-top: 5%;">
				
				<view class="left">
					<text class="title">考研政治题库</text>
					<text >恰同学少年，风华正茂；书生意气，挥斥方遒。指点江山，激扬文字，粪土当年万户侯。曾记否，到中流击水，浪遏飞舟。</text>
					
					
				</view>
				<view>
					<image src="/static/right.png" style="width: 131px;height: 122px;"></image>
				</view>
			</view>
			<!-- <view id="top-box" :class="flag==true?'cu-bar bg-white solid-bottom':'cu-bar bg-white'" >
				
				<view class="action text-black" >
					<text style="font-size: medium;">考研政治</text>
					
				</view>
				
				<view class="action" >

					<view class="action" @tap="visible = true" data-target="modalCard" padding-right="10px">
						<view class="cuIcon-cu-image">
							<cl-icon name="cl-icon-question" :size="39" ></cl-icon>
						</view>

					</view>
	
					</view>

			</view> -->
		</view>
		<view class="page_content" style="margin-top: auto;">
			<view class="menu">
				<template v-for="(it,i) in menus">
					<view class="item" :key="'menu_'+i">
						<view class="img_view" :style="{background: it.bg}" @click="exam(i)">
							<image :src="it.icon" class="image"></image>
						</view>
						<text class="txt" >{{it.txt}}</text>
					</view>
				</template>
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
		</view>
		<scroll-view scroll-x="true" class="slider">
			<template v-for="(it, i) in records">
				<view class="item" :key="'slider_item_'+i" :style="{background: it.bg, marginRight: i === records.length - 1 ? '15px' : '0px'}">
					<view class="item_content" @click="chapter(i)">
						<view class="title">
							<text class="first" style="font-size:smaller;">{{it.title}}</text>
							<text class="main" style="font-size:smaller;">{{it.mainTeacher}}</text>
							
						</view>
						<image class="image" :src="it.icon"></image>
						<!-- <text class="free">免\n费</text> -->
					</view>
				</view>
			</template>
		</scroll-view>
		
		<view >
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
							<cl-col span="8" >
								<view class="block" @click="operate(0)">
									<cl-icon name="cl-icon-question" :size="44"></cl-icon>
									<text class="label">开始考试</text>
								</view>
							</cl-col>
							<cl-col span="8" >
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
		
		
		
		<view class="cu-modal" :class="modalCard==true?'show':''" @tap="hideModalcard">
			<view class="cu-dialog" @tap.stop style="height: 70%;">
				
				
				
				    <page-head :title="subtitle"></page-head>
				   <view class="uni-padding-wrap">
				   	<view style="background:#FFF; padding:40rpx;">
				   		<block v-if="hasUserInfo === false">
				   			<view class="uni-hello-text uni-center">
				   				<text>请点击下方按钮获取用户头像及昵称</text>
				   			</view>
							<view class="uni-btn-v" v-if="hasUserInfo===false">
								
								<!-- <button type="primary" @click="getUserInfo">获取用户信息</button> -->
								
								
								<button type="primary" open-type="getUserInfo" @getuserinfo="getUserInfo">授权</button>
								
								
							</view>
							
				   		</block>
				   		<block v-if="hasUserInfo === true">
				   			<view class="uni-h4 uni-center uni-common-mt">{{userInfo.nickName || userInfo.email}}</view>
				   			<view style="padding:30rpx 0; text-align:center;">
				   				<image class="userinfo-avatar" :src="userInfo.avatarUrl" ></image>
				   			</view>
				   		</block>
				   	</view>
				   	
				   </view>
				
				
			</view>
		</view>
		
	</view>
	
</template>

<script>
	var _this;
	import Util from 'common/util.js'
	export default {
		
		data() {
			return {
				downloaded:false,
				hasUserInfo:false,
				title:false,
				userInfo:null,
				modalCard:false,
				subtitle:"授权",
				examshow:false,
				flag:true,
				visible: false,
				paper:null,
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
					},
					
				],
				menus: [{
						bg: 'linear-gradient(0deg,rgba(9,216,162,1),rgba(90,242,217,1))',
						icon: '/static/graduation.png',
						txt: '智能组卷',
						isFree: true,
					
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
				
				records: [{
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
			}
		},
		onLoad() {
			uni.navigateTo({
				url:"/pages/demo/nav/tabs"
			})
          this.test();
		  
		   // #ifdef MP-WEIXIN || MP-BAIDU || MP-QQ
		  this.checkAuth();
		  //#endif
		 
		  
		  
		},
		methods: {
			hideModalcard()
			{
				if(this.hasUserInfo==true && this.modalCard==true)
				{
					this.modalCard=false;
				}
			},
			getUserInfo(){
				var that =this;
				wx.authorize({
					scope:"scope.userInfo",
					
					complete(e){
						uni.getSetting({
						  success (res) {
							  if(res.authSetting["scope.userInfo"]){
									
									that.onGetUserInfo();
							  }else{
								  
								  wx.showToast({
								  	"title":"授权失败,请重新授权",
								  	"icon":"none"
								  })
							  }
						  }
						})
					}
				})
			},
			
			onGetUserInfo() {
			    //const userInfo = event.detail.userInfo
				   /*  wx.showLoading(); */
			         var that=this; 
					
			        wx.login({
			            success: function (login_res) {
			                wx.getUserInfo({
			                    success: function (res) {
								/* 	console.log(2222222);
									console.log(login_res);
									console.log(res); */
									var userInfo = res.userInfo;
			                        wx.request({
			                            url: that.$server+'/api/me/login',
			                            method: 'POST',
										withCredentials:true,
			                            header: {
			                                'content-type': 'application/x-www-form-urlencoded'
			                            },
			                            data: {
			                                code:login_res.code,
			                                userHead: userInfo.avatarUrl,
			                                userName: userInfo.nickName,
			                                userGender: userInfo.gender,
			                                userCity: userInfo.city,
			                                userProvince: userInfo.province
			                            },
			                            success:function(res) {
			                               /* var userInfo = res.data.data;
											
											console.log(userInfo); */
											
											that.hasUserInfo=true;
											that.userInfo=userInfo;
											
											uni.hideLoading();
											that.modalCard=true;
			                                // 将返回的数据保存到全局的缓冲中，方便其他页面使用
											
			                                wx.setStorageSync( "sessionid", res.data.data)
											
			                            }
			                        })
			                    },
								fail:function(res){
									
									
									uni.hideLoading();
									that.modalCard=true;
									
									/* wx.showToast({
										"title":"请检查网络连接",
										"icon":"none"
									}) */
									/* console.log(444444444);
									console.log(res); */
								}
			                })
			            },
						fail:function(res){
							
							uni.hideLoading();
							that.modalCard=true;
							
						}
						
			        })
			        /* this.setData({
			            hasUserInfo: true,
			            userInfo: userInfo
			        }) */
			    //}
			},
			checkAuth()
			{
			 uni.showLoading({title:"加载中"});
			 var that=this;	 
			 
			  uni.getSetting({
			    success (res) {
			  	  if(res.authSetting["scope.userInfo"]){
					    that.subtitle="欢迎";
			  			that.onGetUserInfo();
						
			  	  }else{
					 
			  		   that.subtitle="授权";
					  
					   uni.hideLoading();
					   that.modalCard=true;
			  	  }
			    },
				fail(res){
					 uni.hideLoading();
					console.log(res);
				}
			  })	
			  
				
			},
			chapter(i){
			 uni.navigateTo({
				 url:'/pages/chapter/index?pid='+(i+1)
			 })
			},
			download(exam)
			{
				
				var that=this;
				if(that.downloaded==false){
				var eid=exam.id;
				uni.showLoading({
					title:"正在下载"
				})
				uni.request({
					url: that.$server+"/exam/download?eid="+eid,
					method: "GET",
					dataType: 'json',
					success(res) {
						if(res.data.code==200)
						{
							var url=that.$server+"/paper/"+res.data.data.path+".pdf";
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
												  
												 that.downloaded=true;
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
							
							
							
							 /* const downloadTask = uni.downloadFile({
							    url: url, 
							    success: (res) => {
							        if (res.statusCode === 200) {
							            uni.saveFile({
							                tempFilePath: res.tempFilePath,
							                    success: function(result) {
							                        //that.savedFilePath = res.savedFilePath;
													that.flag=true;
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
							    }); */
								
								
			
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
			operate(i)
			{
			  if(this.examshow && this.paper!=null)
			  {
				  if(i==0)
				  {
					  uni.navigateTo({
					  	url:'/pages/exam/start?eid='+this.paper.id
					  })
				  }else{
					  this.download(this.paper);
					  
				  }
				  
			  }else{
				  uni.showToast({
				  	icon:"none",
					title:"出现错误了"
				  })
			  }
			},
			test(){
				uni.request({
					url:this.$server+"/user/set",
					success(res){
						console.log(res);
					}
				});
			},
			menu(i){
				switch(i){
					case 0: Util.jump('/pages/user/history');break;
					case 1: this.to(0);break;
					case 2: this.to(1);break;
					case 3: this.to(2);break;
					case 4:Util.jump('/pages/exam/download'); break;
				}
				
			},
			to(type){
				var that=this;
				var url=this.$server+"/question/get?type="+type;
				 uni.request({
					url:url ,
					header:{'Cookie':"JSESSIONID="+that.JSESSIONID()},
					withCredentials:true,
					success(res){
						console.log(res.data);
						if(res.data.code==200){
							       
							       if(res.data.data!=null){
							       Util.jump('/pages/user/questions?type='+type+'&pn=0');
								   }
								   }else{
									   uni.showToast({
									   	icon:"none",
										title:"无数据"
									   })
								   }
					},
					fail(res){
						uni.showToast({
							icon:"none",
							title:"无数据"
						})
					}
					
				});
			},
			open(item) {
				item.visible = true;
			},
			submit() {
				this.$refs["confirm"].open({
					title: "提示",
					message: "确定添加该条数据吗？",
					callback: ({ action }) => {
						if (action === "confirm") {
							this.visible = false;
						}
					},
				});
			},
			
			exam(i)
			{
				var that=this;
				if(i==0 || i==1){
				uni.request({
						url: this.$server+"/exam/get?type="+(i+1),
						method: "GET",
						dataType: 'json',
						header:{'Cookie':"JSESSIONID="+that.JSESSIONID()},
						success(res) {
							if(res.data.code==200)
							{
								var eid=null;
								if(res.data.data!=null){
								eid=res.data.data.id;
								}
								
								if(eid!=null){
								if(i==0){
								 that.paper=res.data.data;	
								 that.examshow=true;
								 console.log(that.examshow);   
								 
								}else{
									
									Util.jump('/pages/exam/start?eid='+eid);
								}
								}else{
									uni.showToast({
										icon:"none",
										title:"无数据"
									})
								}
							}else{
								uni.showToast({
									icon:"none",
									title:"出现问题了"
								})
								
							}
						},
						fail(res) {
							uni.showToast({
								icon:"none",
								title:"出现问题了"
							})
							
						},
						complete() {
							
						}
					});
					}else if(i==2){
						
						uni.request({
							url: this.$server+"/exam/zt",
							withCredentials:true,
							success(res){
								if(res.data.code==200){
								 uni.hideLoading();	
								 if(res.data.data!=null && res.data.data.length>0)
								 {
									 
									Util.jump('/pages/exam/zt');
								 }else{
									 
									 uni.showToast({
									 	icon:"none",
									 	title:"无数据"
									 })
								 }
								}else{
									
									 uni.showToast({
										icon:"none",
										title:"出现问题了"
									})
								}
							},
							fail(res){
								
								uni.showToast({
									icon:"none",
									title:"出现问题了"
								})
							}
							
						});
					
					}else{
						Util.jump('/pages/knowledge/index');
					}
				}
					
				
			}
			}

	
</script>

<style>
	page {
		width: 100%;
		background-color: #ebebeb;
	}
</style>
<style lang="scss" scoped>
	
	
	@function realSize($args) {
		@return $args / 1.5;
	}
	
	@import "../../colorui/animation.css";
	
	page {
		background-color: #FFFFFF;
		font-size: medium;
	}
	
	.cu-form-group {
		justify-content: flex-start
	}
	
	.cu-form-group .title {
		padding-left: 30upx;
		padding-right: 0upx;
	}
	
	.cu-form-group+.cu-form-group {
		border-top: none;
	}
	
	.cu-bar-title {
		min-height: 50upx;
	}
	
	.cu-list.menu>.cu-item-error{justify-content: flex-start;}
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
		background-color: rgba(171,218,255,1);
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
					color: rgba(0, 0, 0, 1.0);
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
			box-shadow: 0px 10px 10px 0px rgba(0, 161, 124, 0.1);
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
						color:rgba(46,65,69,1);
					}
					.main {
						font-size: 13px;
						color:rgba(46,65,69,1);
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
</style>
