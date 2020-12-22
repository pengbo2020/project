<template>
	
	<view class="demo-tabs" v-if="flag==true">
		
		
		<cl-tabs v-model="tabIndex" lazy>
			<cl-tab-pane
				v-for="(item0, index) in labels"
				:key="index"
				:label="item0.label"
				:name="index"
			>
				<view v-if="index==0 && hasData1>0" >
				
				<view class="list-wrap">
					<u-cell-group title-bg-color="rgb(243, 244, 246)" :title="item.data[0].subName" v-for="(item, index2) in list[0].data" :key="index2"  v-if="item.data[0].subList.length>0">
						<u-cell-item :titleStyle="{fontWeight: 500}" :is-link="false"  :title="item1.title" :arrow="false" :value="item1.num+'题'"
						 v-for="(item1, index1) in item.data[0].subList" :key="index1" @click="question(item1.id)">
							<image slot="icon" class="u-cell-icon" :src="getIcon(item1.icon)" mode="widthFix"></image>
						</u-cell-item>
					</u-cell-group>
				</view>
					
				<!-- <cl-card label="章节练习"  >		
				<view v-for="item in list[0].data" >
					
					<uni-collapse ref="add" class="block"   v-if="item.data[0].subList.length>0">
						<uni-collapse-item v-for="(sub, key) in item.data" :key="key" :open="sub.open" :show-animation="sub.showAnimation" :disabled="sub.disabled" :title="sub.subName" v-if="sub.subList.length>0">
							<template v-if="!sub.type">
								<text class="content">{{ sub.content }}</text>
							</template>
							<template v-else>
								<uni-list>
									<uni-list-item v-for="(list2, listIndex) in sub.subList" :key="listIndex" :title="list2.title" :note="list2.note" :thumb="list2.thumb" :show-extra-icon="list2.showExtraIcon" :extra-icon="list2.extraIcon" :show-switch="list2.showSwitch" @switchChange="change"  @click="question(list2.id)" />          
								</uni-list>
							</template>
						</uni-collapse-item>
					</uni-collapse>
				</view>
				</cl-card> -->
				
				</view>
				<view v-if="(index==1 && hasData2==1) || (index==0 && hasData1==0)">
					<!-- <view class="demo-list">
					
						<cl-card label="试卷" padding="0" v-if="exams.length>0">
							<cl-list :label="exam.name" v-for="(exam,i) in exams" :key="i" @click="to(i)">
								<cl-icon slot="icon" name="cl-icon-info" :size="44"></cl-icon> {{exam.type==0?'':exam.createAt}}
							</cl-list>
							
						</cl-card>
						
					</view> -->
					<view class="list-wrap">
						<u-cell-group title-bg-color="rgb(243, 244, 246)">
							<u-cell-item :titleStyle="{fontWeight: 500}" :is-link="false"  :title="getFieldTitle(item1)" :arrow="false"  :value="item1.type==0?'':item1.createAt"
							 v-for="(item1, index1) in exams" :key="index1" @click="to(index1)">
								<image slot="icon" class="u-cell-icon" :src="getIcon(item1.icon)" mode="widthFix" ></image>
							</u-cell-item>
						</u-cell-group>
					</view>
				</view>
			</cl-tab-pane>
		</cl-tabs>
		
		<!-- <cl-tabs v-model="tabIndex" type="swiper" lazy :labels="labels">
			<template v-slot=" {index} ">
				
				<view v-if="index==0 " >
					
				<cl-card label="章节练习"  >		
				<view v-for="item in list[0].data" >
				
					<uni-collapse ref="add" class="block"   v-if="item.data[0].subList.length>0">
						<uni-collapse-item v-for="(sub, key) in item.data" :key="key" :open="sub.open" :show-animation="sub.showAnimation" :disabled="sub.disabled" :title="sub.subName" v-if="sub.subList.length>0">
							<template v-if="!sub.type">
								<text class="content">{{ sub.content }}</text>
							</template>
							<template v-else>
								<uni-list>
									<uni-list-item v-for="(list2, listIndex) in sub.subList" :key="listIndex" :title="list2.title" :note="list2.note" :thumb="list2.thumb" :show-extra-icon="list2.showExtraIcon" :extra-icon="list2.extraIcon" :show-switch="list2.showSwitch" @switchChange="change"  @click="question(list2.id)" />          
								</uni-list>
							</template>
						</uni-collapse-item>
					</uni-collapse>
				</view>
				</cl-card>
				
				</view>
				<view v-if="(index==1 && hasData2==1) || (index==0 && hasData1==0)">
					<view class="demo-list">
					
						<cl-card label="试卷" padding="0" v-if="exams.length>0">
							<cl-list :label="exam.name" v-for="(exam,i) in exams" :key="i" @click="to(i)">
								<cl-icon slot="icon" name="cl-icon-info" :size="44"></cl-icon> {{exam.createAt}}
							</cl-list>
							
						</cl-card>
						
					</view>
				</view>
			</template>
		</cl-tabs> -->
		

		<!-- #ifdef MP
		<cl-tabs v-model="tabIndex" lazy>
			<cl-tab-pane
				v-for="(item, index) in labels"
				:key="index"
				:label="item.label"
				:name="index"
			>
				<cl-list
					v-for="(item2, index2) in list[index]"
					:key="index2"
					:label="`${item2}`"
				>
					<cl-icon name="cl-icon-arrow-right"></cl-icon>
				</cl-list>
			</cl-tab-pane>
		</cl-tabs>
		#endif -->
		<view>
			<cl-popup :visible.sync="examshow" direction="bottom">
				<!-- <view class="demo-icon">
					<cl-card label="操作">
						<cl-row>
							<cl-col span="8" >
								<view class="block" @click="operate(0)">
									<cl-icon name="cl-icon-question" :size="44"></cl-icon>
									<text class="label">查看</text>
								</view>
							</cl-col>
							<cl-col span="8" v-if="exam!=null && exams!=null && (exams[exam].type==1 || exams[exam].type==0)">
								<view class="block" @click="operate(1)">
									<cl-icon name="cl-icon-arrow-bottom" :size="44"></cl-icon>
									<text class="label">下载</text>
								</view>
							</cl-col>
						</cl-row>
					</cl-card>
				</view> -->
				
				<view class="demo-icon">
					<cl-card label="操作">
						<cl-row>
							<cl-col span="8" >
								<view class="block" @click="operate(2)">
									<text class="cuIcon-write lg text-gray" style="font-size: 44rpx;"></text>
									<text class="label">开始考试</text>
								</view>
							</cl-col>
							<cl-col span="8" >
								<view class="block" @click="operate(0)">
									<text class="cuIcon-text lg text-gray" style="font-size: 44rpx;"></text>
									<text class="label">查看历史</text>
								</view>
							</cl-col>
							<cl-col span="8" >
								<view class="block" @click="operate(1)" v-if="exam!=null && exams!=null && (exams[exam].type==1 || exams[exam].type==0)">
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
    var subName1="马克思主义基本原理概论"; 
	var subName2="毛泽东思想和中国特色社会主义理论体系概论";
	var subName3="中国近代史纲要"; 
	var subName4="思想道德修养与法律基础";
	var subName5="形势与政策以及当代世界经济与政治"; 
	let listData = [
		{
			    id:-1, 
				name: '添加动画效果',
				data: [{
					type: true,
					subName: subName1,
					showAnimation: true,
					subList: []
				}]
			},
			{
				 id:-1, 
					name: '添加动画效果',
					data: [{
						type: true,
						subName: subName2,
						showAnimation: true,
						subList: []
					}]
				},
				{
					 id:-1, 
						name: '添加动画效果',
						data: [{
							type: true,
							subName: subName3,
							showAnimation: true,
							subList: []
						}]
					},
					{
						 id:-1, 
							name: '添加动画效果',
							data: [{
								type: true,
								subName: subName4,
								showAnimation: true,
								subList: []
							}]
						},
						{
							 id:-1, 
								name: '添加动画效果',
								data: [{
									type: true,
									subName: subName5,
									showAnimation: true,
									subList: []
								}]
							}
		
	];
		let interstitialAd=null;
export default {
	data() {
		return {
			iconlist:[],
			iconlist2:[],
			examshow:false,
			flag:false,
			exam:null,
			exams:[],
			tabIndex: 0,
			hasData1:0,
			hasData2:0,
			labels: [
			],
			list: [{data:[]},{data:[]}]
		};
	},
	onLoad(){
		
		/* uni.setNavigationBarTitle({
			title: "历 史 "
		});	 */
		uni.showLoading({
			title:"加载中"
		});
		this.initIcon1();
		this.initIcon2();
		
		this.load();
		
		
		
		
		
		
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
	methods:{
		initIcon1()
		{
			for(var i=0;i<iconList.length;++i)
			{
				for(var j=0;j<iconList[i].list.length;++j)
				{
					this.iconlist.push(iconList[i].list[j].icon);
					
				}
			}
			
		
			
		},
		initIcon2()
		{
			for(var i=0;i<iconList.length;++i)
			{
				for(var j=0;j<iconList[i].list.length;++j)
				{
					
					this.iconlist2.push(iconList[i].list[j].icon);
				}
			}
			
		
			
		},
		getGroupTitle(item) {
			return  item.subName 
		},
		getFieldTitle(item) {
			return item.name 
		},
		load:async function()
		{
			uni.showLoading({
				title:"加载中"
			});
			var that=this;
			var hasData=0;
			for(var pid=1;pid<6;++pid)
			{
			var url=this.$server+"/chapter-question/getChildChapter?pid="+pid;
			
			var[error,res] = await uni.request({
					url:url ,
					withCredentials:true,
					header:{'Cookie':"JSESSIONID="+this.JSESSIONID()}
				});
				//console.log(res);
			
				if(res!=undefined && res.data.code==200){
					       /* var data={
					        		name: '添加动画效果',
					        		data: [{
					        			type: true,
					        			subName: subName,
					        			showAnimation: true,
					        			subList: []
					        		}]
					        	}; */
								listData[pid-1].data[0].subList=[];
							if(res.data.data!=null && res.data.data.length>0){
								hasData++;
							var chapters=res.data.data;
							
								//title="list.title" :note="list.note"
							for(var i=0;i<chapters.length;++i)
							{
								var l2=that.iconlist.length-1;
								
								if(l2<=1)
								{
									that.initIcon1();
									l2=that.iconlist.length-1;
								}
								var index = Math.floor(Math.random() * (l2 - 0 + 1)) + 0 ;
								var chapter={
									title:chapters[i].name,
									note:"",
									icon:that.iconlist[index],
									id:chapters[i].id,
									num:chapters[i].questionNum
									
								};
								that.iconlist.splice(index,1);
							 listData[pid-1].data[0].subList.push(chapter);
							}
							//console.log(listData[pid-1].data[0].subList);
						     
						}
					
				}
				}
				if(hasData>0)
				{
					this.list[0].data=listData;
					this.hasData1=1;
					this.labels.push({
					label: "习题"
				});
				}
				this.loadExam();
				
				
				
				
		},
		
		loadExam:async function()
		{
			var url=this.$server+"/exam/history";
			var that = this;
			var hasError=0;
			var[error,res] = await uni.request({
					url:url,
					withCredentials:true,
					header:{'Cookie':"JSESSIONID="+this.JSESSIONID()}
				});
				if(res!=undefined &&  res.data.code==200){
						
							if(res.data.data!=null && res.data.data.length>0){
							this.labels.push({
								label: "试卷"
							});	
							that.hasData2=1;	
							//console.log(res.data.data);
							that.exams=res.data.data;
							for(var i=0;i<that.exams.length;++i)
							{
								
								var l2=that.iconlist2.length-1;
								if(l2<=1)
								{
									that.initIcon2();
									l2=that.iconlist2.length-1;
								}
								var index = Math.floor(Math.random() * (l2 - 0 + 1)) + 0 ;
															
								//that.$set(that.exams[i],"icon",that.iconlist[index]);
								that.exams[i].icon=that.iconlist2[index];
								that.iconlist2.splice(index,1);
								that.exams[i].downloaded=false;
								
							}
						
				}
				//console.log(res.data.data);
				
		}else{
			console.log(error);
		}
		if(this.hasData1==0 && this.hasData2==0)
		{
			uni.hideLoading();
			uni.showToast({
				icon:"none",
				title:"无数据"
			})
		}else 
		{
			uni.hideLoading();
			this.flag=true;
			
		}
		
		},
		to(e)
		{
			
			/* uni.navigateTo({
				url:"/pages/exam/index?eid="+e.id
			}) */
			//console.log(e);
			this.exam=e;
			this.examshow=true;
		},
		/* change(e) {
			if(e>0)
			{
				uni.navigateTo({
					url:'/pages/exam/answers?eid='+e
				});
			}
		}, */
		/* exam(i){
			if(i>0)
			{
				uni.navigateTo({
					url:'/pages/exam/answers?eid='+i
				});
			}
			
		}, */
		question(i){
			//var url="http://192.168.43.202:8081/chapter-question/getChildChapter?pid="+pid;
			uni.navigateTo({
				url:'/pages/user/history_new?pid='+i
			});
			/* uni.request({
				url: "http://192.168.43.202:8081/chapter-question/getQuestions?pid="+i,
				success(res){
					if(res.data.code==200)
					{
						if(res.data.data!=null && res.data.data.length>0)
						{
							console.log(res.data.data);
							uni.navigateTo({
								url:'/pages/user/questionHistory?pid='+i
							});
						}
					}
				}
				
				
			}); */
			
			
			
		},
		download(index)
		{
			
			var that=this;
			var exam= that.exams[index];
			//console.log(exam);
			if(exam.downloaded==false){
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
					//console.log(res);
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
		download1(exam)
		{
			var that=this;
			var eid=exam.id;
			uni.showLoading({
				title:"正在下载"
			})
			uni.request({
				url: this.$servre+"/exam/download?eid="+eid,
				method: "GET",
				dataType: 'json',
				success(res) {
					if(res.data.code==200)
					{
						var url=res.data.data.path;
						//url="http://cn.createpdfonline.org/pdffiles/hello%20word(20200623144016).pdf";
						/* var filename = url.split('/');
						filename=filename[filename.length-1]; */
						
						 const downloadTask = uni.downloadFile({
						    url: url, //仅为示例，并非真实的资源
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
						    });
							
							
		
					}else{
						uni.showToast({
							icon:"none",
							title:"下载失败"
						})
					}
				},
				fail(res) {
					uni.showToast({
						icon:"none",
						title:"网络连接失败"
					})
				},
				complete() {
		
				}
			})
			
		
		
		},
		operate(i)
		{
		  if(this.examshow && this.exam!=null)
		  {
			  var exam = this.exams[this.exam];
			  if(i==0)
			  {
				  
				  uni.navigateTo({
				  	url:'/pages/exam/historyExamQuestions?eid='+exam.id    //'eid='+this.exam.id
				  })
			  }else if(i==1){
				 
				  this.download(this.exam);
				  
			  }else{
				 uni.navigateTo({
				 	url:'/pages/exam/startExam?eid='+exam.id+"&type=1"    //'eid='+this.exam.id
				 })
			  }
			  
		  }else{
			  uni.showToast({
			  	icon:"none",
				title:"出现错误了"
			  })
		  }
		},
	}
};
</script>

<style lang="scss">
page {
	height: 100%;
}
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
