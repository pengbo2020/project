<template>
	<view class="demo-list" v-if="flag==true">
		
		<cl-card label="试卷" padding="0">
			<cl-list :label="item.name"  v-for="(item,index) in list"
					:key="index" @click="to(index)">{{item.createAt}}
				<!-- <text slot="append" class="cl-icon-arrow-right"></text>
		
				<view class="append" slot="menu">
					<button class="primary-btn" @click="preview(index)">查看</button>
					<button class="error-btn" @click="del(index)">删除</button>
				</view> -->
			</cl-list>
		</cl-card>
		
		<view>
			<cl-popup :visible.sync="examshow" direction="bottom">
				<view class="demo-icon">
					<cl-card label="操作">
						<cl-row>
							<cl-col span="8" >
								<view class="block" @click="operate(0)">
									<cl-icon name="cl-icon-question" :size="44"></cl-icon>
									<text class="label">查看</text>
								</view>
							</cl-col>
							<cl-col span="8" >
								<view class="block" @click="operate(1)">
									<cl-icon name="cl-icon-close" :size="44"></cl-icon>
									<text class="label">删除</text>
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

export default {
	data() {
		return {
			exam:null,
			examshow:false,
			flag:false,
			tabIndex: 0,
			labels: [
				{
					label: "练习"
				},
				{
					label: "试卷"
				}
			
			],
			list: []
		};
	},
	onLoad(){
		
		this.loadData();
		
	},
	methods:{
		operate(i)
		{
		  if(this.examshow && this.exam!=null)
		  {
			  if(i==0)
			  {
				  /* var exam = this.exams[this.exam];
				  uni.navigateTo({
				  	url:'/pages/exam/historyExamQuestions?eid='+exam.id    //'eid='+this.exam.id
				  }) */
				  this.preview(this.exam);
			  }else{
				 // this.download(this.exam);
				 this.del(this.exam);
				  
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
		loadData:function(){
			
			 uni.showLoading({
			 				 title:"加载中"
			 });
			 var that=this;
			 var fileList=[];
			  const manage = wx.getFileSystemManager();
			
			 manage.readdir({
			 	dirPath:wx.env.USER_DATA_PATH,
			 	success(res){
			 		
			 		var dirs=res.files;
			 		
			 		for(var i=0;i<dirs.length;++i)
			 		{
			 			
			 			(function(i){
			 			if(dirs[i]!="miniprogramLog")
			 			{
			 				var fileInfo={
			 					name:null,
			 					filePath:null,
			 					
			 					createAt:null
			 				};
			 				var file = manage.readdirSync(wx.env.USER_DATA_PATH+"/"+dirs[i]);
			 				fileInfo.filePath=wx.env.USER_DATA_PATH+"/"+dirs[i];
			 				
			 			
			 				
			 				
			 				file=file[0];
			 				if(file!=undefined)
			 				{
			 					var name=file.replace(".pdf","");
			 					if(name!=null && name.length>0){
			 					fileInfo.createAt = that.timestamp(dirs[i]);
			 					fileInfo.filePath=wx.env.USER_DATA_PATH+"/"+dirs[i];
			 					
			 					fileInfo.name=name;
			 					fileList.push(fileInfo);
			 					}
			                    
			 				}
			 			}
			 			}
			 			)(i);
			 		}
					console.log(fileList);
					if(fileList!=undefined && fileList!=null && fileList.length>0){
					that.list=fileList;
					
					uni.hideLoading();
					that.flag=true;
					}else{
									 uni.hideLoading();
									 uni.showToast({
									 	icon:"none",
									 	title:"无数据"
									 										  
									 });
					}
			 	},
			 	fail(res){
			 		uni.hideLoading();
			 		uni.showToast({
			 			icon:"none",
			 			title:"无数据"
			 												  
			 		});
			 	}
			 });
			
			
			 
			 
			 
			/* uni.getSavedFileList({
			  success: function (res) {
				    
					 if(res.fileList!=undefined && res.fileList!=null && res.fileList.length>0){
						          
			 					  that.list=res.fileList;
			 					  that.flag=true;
								  uni.hideLoading();
								  }else{
									  uni.hideLoading();
									  uni.showToast({
										  icon:"none",
										  title:"无数据"
										  
									  });
								  }
			 	
			 					
			    	
			   },
			   fail()
			   {
				   uni.hideLoading();
				   uni.showToast({
				   										  icon:"none",
				   										  title:"获取数据失败"
				   										  
				   });
				   
			   }
			 		
			 }); */
			
		},
		
		
		question(id){
			uni.navigateTo({
				url:'/pages/exam/index?eid='+id
			});
		},
		del(index)
		{
			this.examshow=false;
			uni.showLoading({
				title:"正在删除"
			})
			 const manage = wx.getFileSystemManager();
			var filePath=this.list[index].filePath;
		    var that=this;
			manage.rmdir({
				dirPath:filePath,
				recursive:true,
				success(res)
				{
					
					that.list.splice(index,1);
					uni.hideLoading();
					uni.showToast({
						icon:"none",
						 title:"删除成功"
															  
					});
				},
				fail(res){
					uni.hideLoading();
					uni.showToast({
						icon:"none",
						 title:"删除失败，请重试"
															  
					});
				}
			});
			
		},
		preview(index)
		{
			uni.showLoading({title:"正在打开..."})
			uni.openDocument({
				filePath:this.list[index].filePath+"/"+this.list[index].name+".pdf",
				success(res){
					uni.hideLoading();
				},
				fail(res){
				console.log(res);
				}
			});
		}
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

</style>
