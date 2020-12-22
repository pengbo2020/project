<template>
    <view class="page" v-if="flag==true">
        <view class="feedback-body" >
            <textarea :placeholder="sendDate.content==''?'请输入...':''" style="border-bottom:1px solid   #F1F1F3;" v-model="sendDate.content" class="feedback-textare"  />
        </view>
        <!-- <choose :count="count"  :imgList="imgList"  @changes="fileChange"></choose>
        <compress  ref="compress" :maxwh="maxwh" :quality="quality"> </compress> -->


       <!-- <view class="swiper-list">
            <view class="uni-list-cell uni-list-cell-pd feedback-title">
                <view class="uni-list-cell-db ">图片是否压缩</view>
                <switch :checked="isYasuo" @change="changeIndicatorDots" />
            </view>
        </view>
        <view class='feedback-title'>
            <text>QQ/邮箱</text>
        </view>
        <view class="feedback-body">
            <input class="feedback-input" v-model="sendDate.contact" placeholder="(选填,方便我们联系你 )" />
        </view>
        <view class='feedback-title feedback-star-view'>
            <text>插件评分</text>
            <view class="feedback-star-view">
                <text class="feedback-star" v-for="(value,key) in stars" :key="key" :class="key < sendDate.score ? 'active' : ''" @tap="chooseStar(value)"></text>
            </view>
        </view> -->
        <button type="default" class="feedback-submit" @tap="send" >保存</button>

    </view>
</template>

<script>
   
	
    export default {
        name:'newsPublish',
       
        data() {
            return {
				book:false,
				flag:false,
				qid:null,
                isYasuo:true,
                count:3,
                maxwh:280,
                quality:1, 
                msgContents: ["界面显示错乱", "启动缓慢，卡出翔了", "UI无法直视，丑哭了", "偶发性崩溃"],
                stars: [1, 2, 3, 4, 5],
                imgList: [],
				imgBook:[],
                sendDate: {
                    score: 0,
                    content: null,
                    contact: ""
                }
            }
        },
        onLoad(option) {
			//this.imgList=['http://192.168.43.202:8081/noteImgs/60497f56-6.png'];
		    this.qid = parseInt(option.qid);
			this.loadData();
			

        },
        methods: {
		
			loadData:async function(){
				uni.showLoading({title:"加载中"});
				var that=this;
				uni.request({
						url: that.$server+"/user-note/modify?qid="+that.qid,
						method: "GET",
						dataType: 'json',
						header:{'Cookie':"JSESSIONID="+that.JSESSIONID()},
						success(res) {
							if(res!=undefined && res.data.code==200){
										
							that.sendDate.content = res.data.data.content==null?'':res.data.data.content;
							//this.imgList=res.data.data.imageList==null?[]:res.data.data.imageList;
							if(res.data.data.imageList!=null)
							{
								var imageList=res.data.data.imageList;
								
								for(var i=0;i<imageList.length;++i)
								{
									that.imgBook.push({id:imageList[i].id,operate:0,path:that.$server+"/noteImgs/"+imageList[i].path});   // 0是表示没有变化
									that.imgList.push(that.$server+"/noteImgs/"+imageList[i].path);
								}
							}
							if(that.sendDate.content!="" || that.imgList.length>0)
							{
								//console.log(this.sendDate.content);
								that.book=true;
							}
							
							 uni.hideLoading();	
							 that.flag=true;	
							}else{
								/* console.log(error); */
								uni.hideLoading();
								uni.showToast({
									icon:"none",
									title:"出了点问题"
								})
							}
						},
						fail(res) {
							uni.hideLoading();
							uni.showToast({
								icon:"none",
								title:"出了点问题"
							})
							
						},
						complete() {
							
						}
					});
				
				
				
					
				/* var [error,res] = await uni.request({
					url: this.$server+"/user-note/modify?qid="+this.qid,
				}); */
				
				
				
			},
			
            compressImg(e){
             // console.log(e)  
            },
            changeIndicatorDots(e){
            this.isYasuo = !this.isYasuo
            },
            fileChange(e){	
              this.imgList=e;
			
                  //             YCImg.canvasToBase64(e)
                  // .then(e => {
                  //     // console.log(JSON.stringify(e))
                  //     this.value = e;
                  //     this.confirm();
                  //     // console.log(e)
                  // })
                  // .catch(e => {
                  //     uni.showToast({
                  //         title: '失败！' + e.message,
                  //         icon: 'none',
                  //         duration: 1000
                  //     });
                  // })
            },
            chooseStar(e) { //点击评星
                this.sendDate.score = e;
            },
            previewImage() { //预览图片
                uni.previewImage({
                    urls: this.imgList
                });
            },
            send() { //发送提交
                //console.log(JSON.stringify(this.sendDate));
				 var that=this;
                
                function requst(imgs,data){
					
					//console.log(imgs);
					if(data.content!="" || imgs.length>0)
					{
						that.book=true;
					}
					if(that.book){
					uni.showLoading({title:"保存中"});	
                   /// console.log(JSON.stringify(imgs));
					data.qid=that.qid;
					
					uni.request({
						url: that.$server+"/user-note/addContent",
						data:data,
						method: 'POST',
						withCredentials:true,
						header: {
							'Cookie':"JSESSIONID="+that.JSESSIONID(),
						    'content-type': 'application/x-www-form-urlencoded'
						},
						dataType: 'json',
						success(res) {
	
							if(res.data.code==200)
							{
								uni.hideLoading();
								uni.showToast({
									icon:"none",
								    title: "保存成功"
								});
								var data=res.data.data;
								//data.id  //就是添加的用户的笔记的Id 
								var deleted=0;
								if(imgs.length==0)
								deleted=1;
								
								//that.uploadImgs(data,deleted,imgs);
								
								
								
					
							}else{
								uni.hideLoading();
								uni.showToast({
									icon:"none",
								    title: "出了点问题"
								});
							}
						},
						fail(res) {
							uni.hideLoading();
							uni.showToast({
								icon:"none",
							    title: "操作失败"
							});
						},
						complete() {
					
						}
					})
					}else{
						//uni.hideLoading();
						uni.showToast({
							icon:"none",
						    title: "请输入"
						});
					}
                                    
                }
				//console.log(this.imgList);
                
                /* if(this.isYasuo){
					var temp=[];
					for(var i=0;i<this.imgList.length;++i)
					{
						var file={uri:null};
						file.uri=this.imgList[i];
						temp.push(file);
					}
                   this.checkandupdate(temp);  
                    this.$refs.compress.yasuoImg(this.imgList).then(e=>{
                       
                        let imgs = e.map((value, index) => {
                           
                            return {
                                name: "image" + index,
                                uri:value.path,
                                base64:value.tempFilePath
                                
                            }
                        })
                        // console.log(imgs)
						
						for(var i=0;i<this.imgBook.length;++i)
						{
							this.imgBook[i].path=imgs[i].uri;
						}
                        requst(imgs,this.sendDate)
                    })
                }else{
				
                    
                    let imgs = this.imgList.map((value, index) => {
                        return {
                            name: "image" + index,
                            uri:value,
                            base64:value
                        }
                    })
                    requst(imgs,this.sendDate)
                } */
				 requst(this.imgList,this.sendDate)
            },
			uploadImgs(data,deleted,imgs)
			{
				
				//console.log(imgs);
				
				
				var that=this;
				//console.log(imgs);
				
				var mes=[];
				
				for(var i=that.imgBook.length;i<imgs.length;++i){
				(function(i){
					/* console.log(imgs[i]); */
					var temp=imgs[i].base64;
					var data1={
						nid:data.id,
						img:JSON.stringify(temp),
						type:"png"
					};
				 uni.request({
					 url: that.$server+"/user-note/addImg3000",
					  //files: imgs,
					  data:data1,
					  method: "POST",
					  dataType: 'json',
					  withCredentials:true,
					  header:{'Cookie':"JSESSIONID="+that.JSESSIONID(),'content-type': 'application/x-www-form-urlencoded'},
					 
					  success(res){
						  console.log(res);
					  },
					  fail(res){
						  console.log(res);
					  }
				 });	
				 uni.uploadFile({
				    url: that.$server+"/user-note/addImg",
				    //files: imgs,
				   /* data:data1,
				    method: "POST", */
				    dataType: 'json',
					
					filePath:imgs[i].uri,
					fileType: 'image',
					name:"img",
				    dataType:"json", 
					
					formData:{
					 nid:data.id	
					},
				    success: (result) => {
						
						result.data =JSON.parse(result.data);
						//uni.hideLoading();
				        if (result.data.code==200) {
				            if(result.data.data!=null){
							// console.log(result.data);	
				            /* uni.showToast({
								icon:"none",
				                title: result.data.data
				            }); */
							
							if(result.data.data!=undefined &&  result.data.data.id!=undefined)
							{
								var data1=result.data.data;
								/* console.log("上传成功了");
								console.log(data);
							    console.log(i);
							    console.log(imgs[i]);
								console.log("结束了"); */
								that.imgBook.push({id:data1.id,operate:0,path:imgs[i].uri});   // 0是表示没有变化
								
								
							}else{
								//that.imgBook.push({id:-1,operate:0,path:""});   // 0是表示没有变化
								that.imgBook.push({id:-1,operate:0,path:imgs[i].uri});
								
								mes.push({index:i,message:result.data.data});
		
							}
							
							//that.imgList.splice(i);
							}/* else{
							 mes.push({index:i,message:"上传失败，请重试"});
							 this.imgBook.push({id:-1,operate:0,""});   // 0是表示没有变化
							 
							} */
						    if(data.content=="" && imgs.length==0)
							{
								that.book=false;
							}
				           /* this.imgList = [];
				            this.sendDate = {
				                score: 0,
				                content: "",
				                contact: ""
				            } */
				       }else{
						   
						   /* console.log(res);
						    console.log(22222);
							uni.showToast({
								icon:"none",
							    title: "上传图片失败"
							}); */
							mes.push({index:i,message:"上传失败，请重试"});
							//this.imgBook.push({id:-1,operate:0,path:""});   // 0是表示没有变化
							that.imgBook.push({id:-1,operate:0,path:imgs[i].uri});
							
						}
				    },
				    fail: (res) => {
						
						//uni.hideLoading();	
					   /* uni.hideLoading();	
				       uni.showToast({
				       	icon:"none",
				           title: "上传图片失败"
				       }); */
					 /* console.log(res); */ 
					  mes.push({index:i,message:"上传失败，请重试"});
					  that.imgBook.push({id:-1,operate:0,path:imgs[i].uri});   // 0是表示没有变化
					  
				    }
				});
				})(i);
				}
				if(mes.length==0){
				uni.hideLoading();
				uni.showToast({
					icon:"none",
				    title: "保存成功"
				});
				}else{
					uni.hideLoading();
					mes=[];
				}
				/* for(var i=0;i<imgs.length;++i)
				{
					if(mes[i].message!=null)
					{
						imgs.split();
					}
				} */
				
			},
			checkandupdate(imgs)
			{
				/* console.log(this.imgBook);
				console.log(imgs); */
				var i=0,j=0;
			    var l1=this.imgBook.length;
				var l2=imgs.length;
				if(l2==0 && l1!=0)
				{
					while(i<l1)
					{
						this.imgBook[i].operate=1;
						i++;
					}
					
				}else{
				while(i<l1 && j<l2)
				{
					if(this.imgBook[i].path!=imgs[j].uri)
					{
						this.imgBook[i].operate=1;
						i++;
					}else{
						i++;
						j++;
					}
				}
				
				while(i<l1){
				  this.imgBook[i].operate=1;
				  i++;
				}
				
				
				
				
			}
			
			
			var ids=[];
			var temp=[];
			for(var i=0;i<this.imgBook.length;++i)
			{
				if(this.imgBook[i].operate==1)
				{
					ids.push(this.imgBook[i].id);
				}else{
					temp.push(this.imgBook[i]);
				}
			}
			this.imgBook=temp;
			
			
			
			if(ids.length>0)
			{
			 
			 uni.request(
			 {
				url:this.$server+"/user-note/delImg",
				data:JSON.stringify(ids),
				method:"POST",
				
			    success(res)
				{
					//console.log(res);
				},
				fail(res)
				{
					uni.showToast({
						icon:"none",
						title:"出现问题了,保存失败"
					});
				}
				
			 });	
				
				
			}
			
			
        }
		}
    }
</script>

<style>
    page {
        background-color: #EFEFF4;
    }

    .input-view {
        font-size: 28upx;
    }
    .close-view{
        text-align: center;line-height:14px;height: 16px;width: 16px;border-radius: 50%;background: #FF5053;color: #FFFFFF;position: absolute;top: -6px;right: -4px;font-size: 12px;
    }
</style>
