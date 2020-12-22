<template>
	<view>
		<view id="top-box" :class="flag==true?'cu-bar bg-white solid-bottom':'cu-bar bg-white'" >
			
			<view class="action text-black" v-if="subjectList!=null">
				<text v-if="currentType===1">判断题</text>
				<text v-else-if="currentType===2">单选题 {{subjectIndex+1}}/{{subjectList.length}}</text>
				<text v-else-if="currentType===3">多选题 {{subjectIndex+1}}/{{subjectList.length}}</text>
				<text v-else-if="currentType===4">填空题</text>
				<text v-else-if="currentType===5">分析题 {{subjectIndex+1}}/{{subjectList.length}}</text>
			</view>
			<!-- <view class="action text-black" v-if="subjectList==null">
				<text>出现问题了</text>
				
			</view> -->
			<view class="action" v-if="subjectList!=null">
				<!-- <view class="action" v-if="subjectList[subjectIndex].showAnswer==true">
					<view class="cuIcon-cu-image" @click="noteModify">
						
						<cl-icon name="cl-icon-round-check" :size="39" ></cl-icon>
					</view>
					
				</view> -->
				<view class="action" @click="FavorSubject">
					<view class="cuIcon-cu-image">
						<text class="lg cuIcon-favor" :class="[userFavor?'cl-icon-favor-fill':'lg cuIcon-favor']"></text>
					</view>
					
				</view>
				
				
				<!-- <view class="action" style="margin-left: 40rpx;">
					<view class="cuIcon-cu-image" @click="note">
						
						<view class="cuIcon-cu-image">
							
							<text class="cuIcon-list lg text-black"></text>
						</view>
					</view>
					
				</view> -->
				
				<view class="action" @tap="showCardModal" data-target="modalCard" style="margin-left: 40rpx;">
					<view class="cuIcon-cu-image">
						<!-- <cl-icon name="cl-icon-question" :size="39" ></cl-icon> -->
						<text class="cuIcon-more lg text-black"></text>
					</view>
					
				</view>
				
				
			
				</view>
			
			
		</view>
		
		
		<view class="cu-modal" :class="modalCard=='modalCard'?'show':''" @tap="hideCardModal">
			<view class="cu-dialog" @tap.stop>
				
				<scroll-view class="page padding" :scroll-y=true :style="{'height':swiperHeight}" >				
				<view class="cu-bar solid-bottom">
					
					<cl-card  label="题目" padding="0">
						
					</cl-card>	
					<view class="action" @bindtap="hideCardModal" @tap="hideCardModal"><text class="cuIcon-close text-gray"></text></view>			
				</view>
				<view class="grid col-5 ">
					<view class="margin-tb-sm text-center" v-for="(subject,index) in subjectList" :key="index">
						<!-- <button class="cu-btn round" :class="[(subject.userAnswer==null || (subject.userAnswer!=null && subject.userAnswer.length===0))?'line-grey':'bg-blue']" @click="AppointedSubject(index)" >{{index+1}}</button> -->
						<button class="cu-btn round" :class="[subject.userAnswer.length===0?'line-grey':(subject.answer==subject.userAnswer?'bg-green':'bg-red')]" @click="AppointedSubject(index)" >{{index+1}}</button>
					</view>
				</view>
				
			
				
				
				</scroll-view>
			</view>
		</view>
		
		
		<form >
			<swiper :current="subjectIndex" class="swiper-box" @change="SwiperChange" :style="{'height':swiperHeight}">
				<swiper-item v-for="(subject,index) in subjectList">
					
					<view v-if="index-subjectIndex>=-1&&index-subjectIndex<=1">
					
					<scroll-view class="page padding" :scroll-y=true :style="{'height':swiperHeight}" style="font-size:large">
					<cl-card  label="题目" padding="0">
						<cl-noticebar
						:text="subject.title"	
						></cl-noticebar>
					</cl-card>
					
										
					
					<view>

						<radio-group class="block"  @change="RadioboxChange" v-if="(subject.type===1||subject.type===2) && subject.showAnswer==false">
							<view class="cu-form-group" v-for="option in subject.optionList" style="margin-bottom: 60rpx;">
								<radio :value="option.id" :checked="(subject.userAnswer!=null && subject.userAnswer.indexOf(option.id) > -1)?true:false" :disabled="subject.showAnswer==true?true:false" style="transform: scale(0.9);" ></radio>
								<view class="title text-black" style="line-height: 45rpx;margin-top: 18rpx;font-size: medium;" @click="optionChange(option.id)">{{option.id}}.{{option.content}}</view>
							</view>		
						</radio-group>
						
						<checkbox-group class="block"  @change="CheckboxChange" v-if="subject.type===2 && subject.showAnswer==true">
							<view class="cu-form-group" v-for="option in subject.optionList" style="margin-bottom: 60rpx;">
								<checkbox :value="option.id"  :class="( (subject.userAnswer!=null && subject.userAnswer.indexOf(option.id) > -1) || subject.answer.indexOf(option.id) > -1)?((subject.answer.indexOf(option.id)>-1)?'round green checked':'round red checked'):'round'" :checked="(subject.answer.indexOf(option.id) > -1 || subject.userAnswer.indexOf(option.id) > -1)?true:false"  :disabled="subject.showAnswer==true?true:false" style="transform: scale(0.9);" color="#FFCC33"></checkbox>
								<view class="title  text-black" style="line-height: 45rpx;margin-top: 18rpx;font-size: medium;">{{option.id}}.{{option.content}}</view>
							</view>
						</checkbox-group>
						
						<!-- <checkbox-group class="block"  @change="CheckboxChange" v-else-if="subject.type===3">
							<view class="cu-form-group" v-for="option in subject.optionList">
								<checkbox :value="option.id" :class="subject.userAnswer.indexOf(option.id) > -1?'checked':''" :checked="subject.userAnswer.indexOf(option.id) > -1?true:false"  :disabled="subject.showAnswer==true?true:false" style="transform: scale(0.9);"></checkbox>
								<view class="title  text-black" >{{option.id}}.{{option.content}}</view>
							</view>
						</checkbox-group> -->
						
						<checkbox-group class="block"  @change="CheckboxChange" v-if="subject.type===3 && subject.showAnswer==false">
							<view class="cu-form-group" v-for="option in subject.optionList" style="margin-bottom: 60rpx;">
								<checkbox :value="option.id"  :class="(subject.userAnswer!=null && subject.userAnswer.indexOf(option.id) > -1)?'checked':''" :checked="(subject.userAnswer!=null && subject.userAnswer.indexOf(option.id) > -1)?true:false"  :disabled="subject.showAnswer==true?true:false" style="transform: scale(0.9);" color="#FFCC33"></checkbox>
								<view class="title  text-black" style="line-height: 45rpx;margin-top: 18rpx;font-size: medium;" @click="optionChange(option.id)">{{option.id}}.{{option.content}}</view>
							</view>
						</checkbox-group>
						
						<checkbox-group class="block"  @change="CheckboxChange" v-if="subject.type===3 && subject.showAnswer==true">
							<view class="cu-form-group" v-for="option in subject.optionList" style="margin-bottom: 60rpx;">
								<checkbox :value="option.id"  :class="( (subject.userAnswer!=null && subject.userAnswer.indexOf(option.id) > -1) || subject.answer.indexOf(option.id) > -1)?((subject.answer.indexOf(option.id)>-1)?'green checked':'red checked'):''" :checked="(subject.answer.indexOf(option.id) > -1 || subject.userAnswer.indexOf(option.id) > -1)?true:false"  :disabled="subject.showAnswer==true?true:false" style="transform: scale(0.9);" color="#FFCC33"></checkbox>
								<view class="title  text-black" style="line-height: 45rpx;margin-top: 18rpx;font-size: medium;">{{option.id}}.{{option.content}}</view>
							</view>
						</checkbox-group>

						<!-- <view v-else-if="subject.type===4">
							<view class="cu-form-group solid-bottom">
								<view class="title  text-black">
									答：
								</view>
								<input placeholder="文本输入框" name="input" v-model="subject.userAnswer" @blur="textInput" ></input>
							</view>
						</view> -->

					<!-- 	<view v-else-if="subject.type===5">
							<view class="cu-bar cu-bar-title bg-white margin-top">
								<view class="action  text-black">
									答：
								</view>
							</view>
							<view class="cu-form-group solid margin">
								<textarea maxlength="-1"  @blur="textInput" v-model="subject.userAnswer" placeholder="多行文本输入框"></textarea>
							</view>
						</view> -->

					</view>

					<view v-show="subject.showAnswer" class="margin-top solid-top">
					
						<cl-card  label="答案" padding="0" v-if="subject.type<5">
							<cl-noticebar
							:text="'正确答案是 '+subject.answer+((subject.userAnswer!=null && subject.userAnswer.length>0)?(',你的答案是 '+subject.userAnswer):'')"	
							></cl-noticebar>
						</cl-card>
						
						<cl-card  label="解析" padding="0" v-if="subject.explain!=null">
							<cl-noticebar
							:text="subject.explain"	
							></cl-noticebar>
						</cl-card>
						
						
						<cl-card  label="笔记" padding="0" v-if="subject.flag==true">
						
							
							
							<view class="content" >
					           
								<view class="uni-padding-wrap" v-if="subject.flag==true">
									
									<view class="uni-common-mt" style="background:#FFF; padding:20rpx;font-size: medium;">
										<rich-text :nodes="subject.nodes"></rich-text>
									</view>
									
									<view class="uni-common-mt" style="background:#FFF; padding:20rpx;">
										<rich-text :nodes="subject.strings"></rich-text>
									</view>
									
									
									
								</view>
								<view class="uni-padding-wrap" v-if="subject.flag==false">
									<view class="title  text-black" style="font-size: medium;">点击<cl-icon name="cl-icon-round-check" :size="30" ></cl-icon>添加或修改笔记</view>
									
								</view>
								
							</view>
							
							
							
						</cl-card>
		
						
					</view>
                     </scroll-view>   
					</view>
				</swiper-item>
			</swiper>
		</form>
		
		<view id="foot-box">
			<u-tabbar
			    v-model="current"
			    :show="show"
			    :bg-color="bgColor"
			    :border-top="borderTop"
			    :list="list2"
				
				:inactive-color="inactiveColor"
				:activeColor="inactiveColor"
				@change="change2"
			     ></u-tabbar>
		</view>	
		
		</view>
		
		
		

	
</template>

<script>
	
   
   var subjectList;
	export default {
		data() {
			return {
				eid:null,
				current: -1,
				show: true,
				bgColor: '#ffffff',
				borderTop: true,
				/* midButton: true, */
				inactiveColor: '#909399',
				activeColor: '#5098FF',
				list2: [{
						iconPath: "arrow-left",
						selectedIconPath: "arrow-left",
						text: '上一题',
						
						isDot: false,
						customIcon: false,
					},
					/* {
						iconPath: "photo",
						selectedIconPath: "photo-fill",
						text: '放映厅',
						customIcon: false,
					}, */
					/* {
						iconPath: "/static/uview/example/min_button.png",
						selectedIconPath: "/static/uview/example/min_button_select.png",
						text: '听知识点',
						midButton: true,
						customIcon: false,
					}, */
					{
						iconPath: "order",
						selectedIconPath: "order",
						text: '笔记',
						customIcon: false,
					},
					{
						iconPath: "arrow-right",
						selectedIconPath: "arrow-right",
						text: '下一题',
						
						isDot: false,
						customIcon: false,
					},
				],
				type:0,//0 继续联系 1 重新练习
				cid:null,
				total:false,
				flag:false,
				type:0,
				pn:0,
				html: '',
				userFavor:false,//是否已收藏
				currentType: 0, //当前题型
				subjectIndex: 0,//跳转索引
				autoShowAnswer: false,//答错是否显答案
				autoRadioNext:true,//判断题、单项题，自动移下一题
				swiperHeight: '800px',//
				title: null,
				subjectList:null,
				modalCard: null ,//显示答题卡
				modalError:null , //纠错卡
				errorList:['题目不完整','答案不正确','含有错别字','图片不存在','解析不完整','其他错误'],
			}
		},
		
		
		
		 onShow(){
			 if(this.subjectList!=undefined && this.subjectList!=null){
			 this.loadCurrent();
			 
			 }
			
		},
		onReady() {
			var tempHeight = 800;
			var _me = this;
			uni.getSystemInfo({
				//获取手机屏幕高度信息，让swiper的高度和手机屏幕一样高                
				success: function(res) {
				                   
					tempHeight = res.windowHeight;
	
					uni.createSelectorQuery().select("#top-box").fields({
						size: true,
						scrollOffset: true
					}, (data) => {
						tempHeight -= data.height;
					
						_me.swiperHeight = tempHeight + 'px';
						uni.createSelectorQuery().select("#foot-box").fields({
							size: true,
							scrollOffset: true
						}, (data) => {
							tempHeight -= data.height;
							//console.log("减掉底部后的高度 " + tempHeight);
							_me.swiperHeight = tempHeight +10+ 'px';
							//console.log("滑屏最后高度 " + _me.swiperHeight);
						}).exec();

					

					}).exec();
				}
			});
			
			

		},
		onLoad(option) {
			
			
		    this.eid=parseInt(option.eid);
			
			
			this.loadData();
			
			/* if(!isNaN(Number(this.type))){
			 var title="";
			 if(this.type==0)
			 {
				 title="我的收藏"
				 }else if(this.type==1)
				 {
					 title="我的错题";
				 }else if(this.type==2)
				 {
					 title="我的笔记"
				 }else{
					 title="考研政治"
				 }
				 uni.setNavigationBarTitle({
				 	title: title
				 });	
			} */
			/* uni.setNavigationBarTitle({
				title: this.title
			});		
		 */
				
			
			//添加用户显示答案字段
			
			
		},
		methods: {
			optionChange(optionId){
				var that=this;
				if(this.currentType==2){
				this.subjectList[this.subjectIndex].userAnswer = optionId;
				var userAnswer = this.subjectList[this.subjectIndex].userAnswer;
				var answer = this.subjectList[this.subjectIndex].answer;
				var wrong= (userAnswer==answer)?0:1;
				var that=this;
				uni.request({
					url:that.$server+"/user-question/submitAnswer2?qid="+that.subjectList[that.subjectIndex].qid+"&userAnswer="+userAnswer+"&wrong="+wrong+"&eid="+that.eid,
					header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
					withCredentials:true,
					success(res){
						
					},
					fail(res){
						that.failSubmit.push(that.subjectIndex);
					}
				})
				
				/* if(this.autoRadioNext && this.subjectIndex < this.subjectList.length - 1){
					this.subjectIndex += 1;						
					}; */
					}else{
						var temp=[];
						if(this.subjectList[this.subjectIndex].userAnswer.indexOf(optionId)>-1)
						{
							
							var answer="";
							var userAnswer=this.subjectList[this.subjectIndex].userAnswer;
							for(var i=0;i<userAnswer.length;++i)
							{
								console.log(optionId);
								
								if(userAnswer[i]!=optionId)
								{
									
									temp.push(userAnswer[i]);
								}
							}
							var obj={
								detail:{
									value:temp
								}
							};
							
							this.CheckboxChange(obj);
							
							
							
							
						}else{
							this.subjectList[this.subjectIndex].userAnswer+=optionId;
						    var userAnswer = this.subjectList[this.subjectIndex].userAnswer;
							/* var answer={
								A:0,
								B:0,
								C:0,
								D:0
							}; */
							
							
							
							//this.subjectList[this.subjectIndex].userAnswer=userAnswer;
							for(var i=0;i<userAnswer.length;++i)
							{
								/* if(userAnswer[i]=='A')
								{
									answer.A=1;
								}else if(userAnswer[i]=='B'){
									answer.B=1;
								}else if(userAnswer[i]=='C'){
									answer.C=1;
								}else{
									answer.D=1;
								} */
								
									
									temp.push(userAnswer[i]);
								
							}
							
							
						}
						var obj={
							detail:{
								value:temp
							}
						};
						
						this.CheckboxChange(obj);
						
					}
				
				
				
			},
			/* change2(e)
			{
				//console.log(e);
				var that=this;
				var index=null;
				if(e==0 || e==2){
				if(e==0)
				{
				   index = that.subjectIndex-1;						
				}else{
				   index = that.subjectIndex+1;
				}
				if (index>=0 && index<that.subjectList.length) {
					
					that.subjectIndex = index;
					that.currentType = that.subjectList[index].type;
					that.userFavor = that.subjectList[index].userFavor;	
								
				}else{
					uni.showToast({
						icon:"none",
						title:"没有了"
					});
				}
					}else{
						
					
						
						that.ShowAnswerChange(1);
						
					}
			}, */
			change2(e)
			{
				
				//console.log(e);
				var that=this;
				if(that.subjectList!=null){
				var index=null;
				if(e==0 || e==2){
				if(e==0)
				{
				   index = that.subjectIndex-1;						
				}else{
					
				   index = that.subjectIndex+1;
				   
				}
				if (index>=0 && index<that.subjectList.length) {
					
					that.subjectIndex = index;
					that.currentType = that.subjectList[index].type;
					that.userFavor = that.subjectList[index].userFavor;	
					if(!that.total && index>that.subjectList.length/2){
						
					that.more();
											
						}
								
				}else{
					uni.showToast({
						icon:"none",
						title:"没有了"
					});
				}
					}else{
						
						/*var qid=that.subjectList[that.subjectIndex].qid;
						uni.navigateTo({
							url: '/pages/note/modify?qid='+qid
						}) */
						
						//that.ShowAnswerChange(1);
						that.note();
						
					}
					}
			},
			loadCurrent()
			{

			        var that=this; 
					var url=this.$server+"/question/getone?pid="+this.subjectList[this.subjectIndex].qid;
					
					 uni.request({
						url:url,
						withCredentials:true,
						header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
						success(res)
						{
							//console.log(res);
							if(res.data.code==200){
								  if(res.data.data!=null){
									var data=res.data.data;  
								    var i =that.subjectIndex;
									
									that.subjectList[i].nodes=[{
									name: 'div',
									attrs: {
										class: 'div-class',
										style: 'line-height: 20px;'
									},
									children: [{
										type: 'text',
										text: data.note.content
									}]
								}];
								if(data.note.content!=null && data.note.content.length>0)
								{
									that.subjectList[i].flag=true;
								}
								
								
								   that.subjectList[i].strings="";
								  //strings: '<div style="text-align:center;"><img src="https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/uni@2x.png"/></div><div style="text-align:center;"><img src="https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/uni@2x.png"/></div>'
								    var imageList=data.note.imageList;
									var images="";
								
								    if(imageList!=null && imageList.length>0){
										that.subjectList[i].flag=true;
								    	for(var j=0;j<imageList.length;++j)
								    	{
								    		
								    		images+="<div class='image' mode='widthFix' style='text-align:center'><img style='max-width:100%;height:auto' src='"+that.$server+"/noteImgs/"+imageList[j].path+"'></div>";
								    	}
										that.subjectList[i].strings=images;
								    	
								    }
									that.$forceUpdate();
										
								//console.log(that.subjectList[i]);
								}
							}
						},
						fail(res){
							console.log(res);
							uni.showToast({
								icon:"none",
								title:"请检查网络连接"
							})
						}
						
					});
					//console.log(this.subjectList[this.subjectIndex]);
					
					
				
				
			},
			
			more:async function()
			{
					this.pn+=1;
					var l=this.subjectList.length;
					var url=this.$server+"/question/get?type="+this.type+"&pn="+this.pn+"&ps=20";
					
					var[error,res] = await uni.request({
							url:url ,
							withCredentials:true,
							header:{'Cookie':"JSESSIONID="+this.JSESSIONID()}
						});
						if(res!=undefined && res.data.code!=undefined && res.data.code==200){
									if(res.data.data!=null && res.data.data.length>0){
									var list=res.data.data;
									console.log(res.data.data);
									for(var i=0;i<list.length;++i)
									{
										this.subjectList.push(list[i]);
										this.subjectList[l+i].flag=false;
										if(this.type==1 || this.subjectList[l+i].userAnswer==null){
										this.subjectList[l+i].userAnswer="";
										}
										if(this.subjectList[l+i].answer==null){
										this.subjectList[l+i].answer="";
										}
										
										this.subjectList[l+i].nodes=[{
											name: 'div',
											attrs: {
												class: 'div-class',
												style: 'line-height: 20px;'
											},
											children: [{
												type: 'text',
												text: this.subjectList[l+i].note.content
											}]
										}];
										if(this.subjectList[l+i].note.content!=null)
										{
											this.subjectList[l+i].flag=true;
										}
										this.subjectList[l+i].strings="";
										    var imageList=this.subjectList[l+i].note.imageList;
											var images="";
										    if(imageList!=null){
												this.subjectList[l+i].flag=true;
										    	for(var j=0;j<imageList.length;++j)
										    	{
										    		
										    		images+="<div class='image' mode='widthFix' style='text-align:center;'><img src='"+this.$server+"/noteImgs/"+imageList[j].path+"'></div>";
										    	}
												this.subjectList[l+i].strings=images;
										    	
										    }
										
										
										
										
										
										this.$set(this.subjectList[l+i],"showAnswer",false);
									}
									
								
								}else{
									this.total=true;
								}
						}else{
							//console.log(res);
						}
						
			},
			
			loadData:async function(){
				uni.showLoading({title:"加载中"});
				var url=this.$server+"/exam/answer?eid="+this.eid;
				var [error,res] = await uni.request({
					url:url ,
					withCredentials:true,
					header:{'Cookie':"JSESSIONID="+this.JSESSIONID()}
					
				});
				
				if(res!=undefined &&  res.data.code==200){
				  if(res.data.data!=null && res.data.data.length>0){
				this.subjectList = res.data.data;
				this.userFavor=this.subjectList[0].userFavor;
				this.currentType = this.subjectList[0].type;
				//console.log(res.data.data);
				for (var i = 0; i < this.subjectList.length; i++) {
					this.subjectList[i].flag=false;
					if(this.type==1 || this.subjectList[i].userAnswer==null){
					this.subjectList[i].userAnswer="";
					}
					if(this.subjectList[i].answer==null){
					this.subjectList[i].answer="";
					}
					this.subjectList[i].nodes=[{
					name: 'div',
					attrs: {
						class: 'div-class',
						style: 'line-height: 20px;'
					},
					children: [{
						type: 'text',
						text: this.subjectList[i].note.content
					}]
				}];
				if(this.subjectList[i].note.content!=null)
				{
					this.subjectList[i].flag=true;
				}
				
				this.subjectList[i].strings="";
				  //strings: '<div style="text-align:center;"><img src="https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/uni@2x.png"/></div><div style="text-align:center;"><img src="https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/uni@2x.png"/></div>'
				    var imageList=this.subjectList[i].note.imageList;
					var images="";
				
				    if(imageList!=null){
						this.subjectList[i].flag=true;
				    	for(var j=0;j<imageList.length;++j)
				    	{
				    		
				    		images+="<div class='image' mode='widthFix' style='text-align:center'><img style='max-width:100%;height:auto' src='"+this.$server+"/noteImgs/"+imageList[j].path+"'></div>";
				    	}
						this.subjectList[i].strings=images;
				    	
				    }
					this.$set(this.subjectList[i],"showAnswer",true);				
				}
				
				uni.hideLoading();
				this.flag=true;
				}else{
					uni.hideLoading();
					uni.showToast({
						icon:"none",
						title:"暂无数据"
					})
					
				}
			}else{
				uni.hideLoading();
				uni.showToast({
					icon:"none",
					title:"出问题了"
				})
				
			}
			if(this.flag==false)
			{
				this.list2=[];
			}
			
			
			},
			noteModify:function(){
				var qid = this.subjectList[this.subjectIndex].qid;
				uni.navigateTo({
					url: '/pages/note/modify?qid='+qid
				})
			},
			showCardModal: function(e) {
				this.modalCard = e.currentTarget.dataset.target
			},
			hideCardModal: function(e) {
				this.modalCard = null
			},
			showErrorModal: function(e) {
				this.modalError = e.currentTarget.dataset.target
			},
			hideErrorModal: function(e) {
				this.modalError = null
			},
			SwiperChange: function(e) { //滑动事件
			    if(e.target.source=='autoplay' || e.target.source=='touch'){
				let index = e.target.current;				
				if (index != undefined) {
					this.subjectIndex = index;
					this.currentType = this.subjectList[index].type;
					this.userFavor = this.subjectList[index].userFavor;	
			         var i=index;
					   
											
					/* if(!this.total && index>this.subjectList.length/2){
						
					this.more();
											
						} */
												
			    }
				}
			},			
			RadioboxChange : function(e) { //单选选中
			
				var items = this.subjectList[this.subjectIndex].optionList;
				var values = e.detail.value;
				this.subjectList[this.subjectIndex].userAnswer = values;
				/* if(this.autoRadioNext && this.subjectIndex <=this.subjectList.length - 1){
					
					this.ShowAnswerChange();
											
					}; */
				
			},
			CheckboxChange: function(e) { //复选选中

				var items = this.subjectList[this.subjectIndex].optionList;
				var values = e.detail.value;
				this.subjectList[this.subjectIndex].userAnswer = "";
				for (var i = 0, lenI = items.length; i < lenI; ++i) {
					for (var j = 0, lenJ = values.length; j < lenJ; ++j) {
						if (items[i].id == values[j]) {

							this.subjectList[this.subjectIndex].userAnswer += items[i].id;
							break
						}
					}
				}
			},
			textInput : function(e) { //填空题
			
				this.subjectList[this.subjectIndex].userAnswer = e.detail.value;
				
			},
			note(){
				this.noteModify();
			},			
			ShowAnswerChange: function(e) { //显示答案
			
				/* if(this.subjectList[this.subjectIndex].showAnswer)
				{
					
					this.noteModify();
					
					
										
				}
				else{ */
					
					this.subjectList[this.subjectIndex].showAnswer=true;
					/* uni.request({
						url:
					}) */
					var answer = this.subjectList[this.subjectIndex].answer;
					var userAnswer = this.subjectList[this.subjectIndex].userAnswer;
					var wrong=0;
					var qid=this.subjectList[this.subjectIndex].qid;
					if(answer!=userAnswer)
					{
						wrong=1;
					}
					var that = this;
					uni.request({
						url:this.$server+"/user-question/submitAnswer?qid="+qid+"&userAnswer="+userAnswer+"&wrong="+wrong,
						withCredentials:true,
						header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
						success(res){
							
						}
					});
					
					
					
					
				//}				
			},
			
			FavorSubject: function(e) { //收藏题
			
				var qid=this.subjectList[this.subjectIndex].qid;
				var collect;
				var that=this;
				if(this.userFavor)
				{
					collect=0;
					uni.request({
						url:that.$server+"/user-question/favor?qid="+qid+"&collect="+collect,
						header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
						withCredentials:true,
						success(res){
							if(res.data.code==200){
							that.userFavor=false;
							that.subjectList[that.subjectIndex].userFavor=false;
							uni.showToast({
								icon:"none",
								title:"取消收藏成功"
							})
							}else{
								uni.showToast({
									icon:"none",
									title:"取消收藏失败"
								})
							}
						},
						fail(res){
							uni.showToast({
								icon:"none",
								title:"取消收藏失败"
							})
						}
					})
										
				}
				else{
					collect=1;
					uni.request({
						url:this.$server+"/user-question/favor?qid="+qid+"&collect="+collect,
						withCredentials:true,
						header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
						success(res){
							if(res.data.code==200){
							that.userFavor=true;
							that.subjectList[that.subjectIndex].userFavor=true;	
							uni.showToast({
								icon:"none",
								title:"收藏成功"
							})
							}else{
								uni.showToast({
									icon:"none",
									title:"收藏失败"
								})
							}
						},
						fail(res){
							uni.showToast({
								icon:"none",
								title:"收藏失败"
							})
						}
					})
					
				}			
			},
			
			MoveSubject: function(e) { //上一题、下一题

				if (e === -1 && this.subjectIndex != 0) {
					this.subjectIndex -= 1;
				}
				if (e === 1 && this.subjectIndex < this.subjectList.length - 1) {
					this.subjectIndex += 1;
				}
			},
			
			AppointedSubject: function(e) { //题卡指定
				
				   
					this.modalCard = null;
					this.subjectIndex = e;
					this.currentType=this.subjectList[e].type;
					/* if(!this.total && e>this.subjectList.length/2){
						
					this.more();
											
						} */
					
					
														
			},			
			
			SubmitError: function(e) { //提交纠错
			       
				   
					
					this.modalError = null;														
			}
			
		}
	}
</script>

<style>
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
</style>










