<template>
	<view>
		<view id="top-box"  :class="subjectList==null?'cu-bar bg-white':'cu-bar bg-white solid-bottom'">
			
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
				<view class="action" @click="FavorSubject">
					<view class="cuIcon-cu-image">
						<text class="lg cuIcon-favor" :class="[userFavor?'cl-icon-favor-fill':'lg cuIcon-favor']"></text>
					
					</view>
					
				</view>
				
				<!-- <view class="action" >
					<view class="cuIcon-cu-image" @click="ShowAnswerChange">
						
						<cl-icon name="cl-icon-round-check" :size="39" ></cl-icon>
					</view>
					
				</view> -->
				<!-- <view class="action" @tap="showErrorModal" data-target="errorCard" style="margin-left: 20rpx;">
					<view class="cuIcon-cu-image">
						<cl-icon name="cl-icon-round-check" :size="39" ></cl-icon>
					</view>
					
				</view> -->
				<view class="action" @tap="showCardModal" data-target="modalCard" style="margin-left: 40rpx;">
					<view class="cuIcon-cu-image">
						<!-- <cl-icon name="cl-icon-question" :size="39" ></cl-icon> -->
						<text class="cuIcon-more lg text-black"></text>
					</view>
					
				</view>
				
				
			
				<!-- <button class="cu-btn bg-green shadow" @tap="showCardModal" data-target="modalCard">答题卡</button> -->
			</view>
			
			
		</view>
		
			
	<view class="cu-modal padding " :class="modalError?'show':''" @tap="hideErrorModal">
		<view class="cu-dialog bg-white" @tap.stop>								
			
			<view class="cu-bar bg-white justify-end">
									<view class="content">反馈</view>
									<view class="action" @bindtap="hideErrorModal" @tap="hideErrorModal"><text class="cuIcon-close text-gray"></text></view>
								</view>
								<view class="padding-xl">	
			
							
			<checkbox-group class="block"  @change="CheckboxChange2" :key="modalError">
				<view class="cu-form-group" v-for="(item,index) in errorList" :key="index" >
					<checkbox :value="index" :class="item.checked==true?'checked':''"></checkbox>
					<view class="title  text-black">{{item.value}}</view>
				</view>
			</checkbox-group>
			
			<view class="padding flex flex-direction ">				
				<!-- <button class="cu-btn bg-red margin-tb-sm lg" @click="SubmitError">提 交</button> -->
				<button class="cu-btn  margin-tb-sm lg" @click="errorSubmit()">提 交 </button>
			</view>
		</view>
	</view>		
	</view>
		
		<view class="cu-modal" :class="modalCard=='modalCard'?'show':''" @tap="hideCardModal">
			<view class="cu-dialog" @tap.stop>
				
				<scroll-view class="page padding" :scroll-y=true :style="{'height':swiperHeight}" >				
				<view class="cu-bar solid-bottom">
					
					<cl-card  label="答题卡" padding="0">
						
					</cl-card>
					<view class="action" @bindtap="hideCardModal" @tap="hideCardModal"><text class="cuIcon-close text-gray"></text></view>							
				</view>
				<view class="grid col-5 ">
					<view class="margin-tb-sm text-center" v-for="(subject,index) in subjectList" :key="index">
						<button class="cu-btn round" :class="[subject.userAnswer.length===0?'line-grey':'bg-blue']" @click="AppointedSubject(index)" >{{index+1}}</button>
					</view>
				</view>
				<view class="padding flex flex-direction ">
					<button class="cu-btn  margin-tb-sm lg" @click="SubmitError2()">提 交 试 卷</button>
				</view>
				
				
				</scroll-view>
			</view>
		</view>
		
		
		
		
		<form >
			<swiper :current="subjectIndex" class="swiper-box" @change="SwiperChange" :style="{'height':swiperHeight}" v-if="flag==true && subjectList!=null">
				<swiper-item v-for="(subject,index) in subjectList">
					
					<view v-if="index-subjectIndex>=-1&&index-subjectIndex<=1">
					
					<scroll-view class="page padding" :scroll-y=true :style="{'height':swiperHeight}" >
					<cl-card  label="题目" padding="0">
						
							
						<text style="font-size:medium ;margin-left:20rpx ;">{{subject.title}}</text>
					</cl-card>
										
					<view>

						<radio-group class="block"  @change="RadioboxChange" v-if="subject.type===1||subject.type===2">
							<view class="cu-form-group" v-for="option in subject.optionList" style="margin-bottom: 60rpx;" >
								<radio :value="option.id" :checked="subject.userAnswer.indexOf(option.id) > -1?true:false" style="size: 10rpx;" :ref="option.id"></radio>
								<view class="title text-black" style="line-height: 45rpx;margin-top: 18rpx;font-size: medium;" @click="optionChange(option.id)">{{option.id}}.{{option.content}}</view>
							</view>
						</radio-group>
						
						<!-- :key="index" :name="item.name" -->
					<!-- 	<view class="u-demo-wrap" v-if="subject.type===1||subject.type===2">
							
							<view class="u-demo-area">
								<view class="">
						<u-radio-group :shape="circle" :size="45"  :wrap="true"  @change="RadioboxChange" :activeColor="activeColor" >
							<u-radio   v-for="option in subject.optionList" 
								
							style="margin-bottom: 30rpx;margin-left: 15rpx;" :checked="subject.userAnswer.indexOf(option.id) > -1?true:false">{{option.id}}.{{option.content}}</u-radio>
						</u-radio-group>
						</view>
						</view>
						</view> -->

						<checkbox-group class="block"  @change="CheckboxChange" v-else-if="subject.type===3">
							<view class="cu-form-group" v-for="option in subject.optionList" style="margin-bottom: 60rpx;">
								<checkbox :value="option.id" :class="subject.userAnswer.indexOf(option.id) > -1?'checked':''" :checked="subject.userAnswer.indexOf(option.id) > -1?true:false"></checkbox>
								<view class="title  text-black" style="line-height: 45rpx;margin-top: 18rpx;font-size: medium;" @click="optionChange(option.id)">{{option.id}}.{{option.content}}</view>
							</view>
						</checkbox-group>

						<view v-else-if="subject.type===4 ">
							<view class="cu-form-group solid-bottom">
								<view class="title  text-black">
									暂不支持作答，请在交卷后核对答案并查看解析
								</view>
								<!-- <input placeholder="文本输入框" name="input" v-model="subject.userAnswer" @blur="textInput" ></input>
							 --></view>
						</view>

						<view v-else-if="subject.type===5">
							<view class="cu-bar cu-bar-title bg-white margin-top">
								<view class="action  text-black">
									暂不支持作答，请在交卷后核对答案并查看解析
								</view>
							</view>
							<!-- <view class="cu-form-group solid margin">
								<textarea maxlength="-1"  @blur="textInput" v-model="subject.userAnswer" placeholder="多行文本输入框"></textarea>
							</view> -->
						</view>

					</view>

					<view v-show="subject.showAnswer" class="margin-top solid-top">
						<!-- <view class="cu-bar">
							<view class="action  text-grey">
								<text>正确答案：</text>
								<text class="solid-bottom  padding-left text-green">{{subject.answer}}</text>
							</view>
							
						</view> -->
						<cl-card  label="正确答案" padding="0">
							<cl-noticebar
							:text="subject.answer"	
							></cl-noticebar>
						</cl-card>
						<!-- <view class="cu-bar cu-bar-title">
							<view class="action  text-grey">
								<text>解析：</text>
							</view>
						</view>
						<view class="text-content padding  text-grey">
							{{subject.explain}}
						</view> -->
						<cl-card  label="解析" padding="0">
							<cl-noticebar
							:text="subject.explain"	
							></cl-noticebar>
						</cl-card>
						
						
						<cl-card  label="笔记" padding="0">
							<!-- <cl-noticebar
							:text="subject.explain"	
							></cl-noticebar> -->
							<button @click='noteModify(subject.qid)'>编辑</button>
						</cl-card>
		
						<!-- <view>
							<div>
								<button @click='noteModify(subject.qid)'>编辑</button>
								<jyf-parser :html="html" ref="article"></jyf-parser>
							</div>
						</view> -->
						
					</view>
                     </scroll-view>   
					</view>
				</swiper-item>
			</swiper>
		</form>
		
		
		<view id="foot-box"   >
			<u-tabbar
			    v-model="current"
			    :show="show"
			    :bg-color="bgColor"
			    :border-top="borderTop"
			    :list="list2"
				
				:inactive-color="inactiveColor"
				:activeColor="inactiveColor"
				@change="change"
			></u-tabbar>
		</view>
		</view>
		
		
		

	
</template>

<script>
	
   var subjectList;
	export default {
		data() {
			return {
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
						text: '反馈',
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
				
				errorInfo:"",
				flag:false,
				eid:null,
				userFavor:false,//是否已收藏
				currentType: 0, //当前题型
				subjectIndex: 0,//跳转索引
				autoShowAnswer: false,//答错是否显答案
				autoRadioNext:true,//判断题、单项题，自动移下一题
				swiperHeight: '800px',//
				title: '初中二年级2019期中考券',
				subjectList:null,
				modalCard: null ,//显示答题卡
				modalError:null , //纠错卡
				errorList:[
					{
					 checked:false,
					 value:'题目不完整'	 
					},
					{
					 checked:false,
					 value:'题目有错字'	 
					},
					{
					 checked:false,
					 value:'选项不完整'	 
					},
					{
					 checked:false,
					 value:'选项有错字'	 
					},
					{
					 checked:false,
					 value:'排版有错'	 
					}],
				failSubmit:[],
				tempEid:-1,
				resetting:false,
				submitcount:0
			}
		},
		onReady() {
			var tempHeight = 800;
			var _me = this;
			uni.getSystemInfo({
				//获取手机屏幕高度信息，让swiper的高度和手机屏幕一样高                
				success: function(res) {
					// console.log(res.model);                    
					// console.log(res.pixelRatio);                    
					// console.log(res.windowWidth);                    
					// console.log(res.windowHeight);
					// //这里是手机屏幕高度                    
					// console.log(res.language);                    
					// console.log(res.version);                    
					// console.log(res.platform);                    
					tempHeight = res.windowHeight;
					//console.log("屏幕可用高度 " + tempHeight);

					uni.createSelectorQuery().select("#top-box").fields({
						size: true,
						scrollOffset: true
					}, (data) => {
						tempHeight -= data.height;
						_me.swiperHeight = tempHeight + 'px';
						//console.log("减掉顶部后的高度 " + tempHeight);

						uni.createSelectorQuery().select("#foot-box").fields({
							size: true,
							scrollOffset: true
						}, (data) => {
							tempHeight -= data.height;
							console.log("减掉底部后的高度 " + tempHeight);
							_me.swiperHeight = tempHeight + 'px';
							console.log("滑屏最后高度 " + _me.swiperHeight);
						}).exec();

					}).exec();
				}
			});

		},
		onLoad(option) {
			this.eid=parseInt(option.eid);
			 
			 this.reset();
		     /* this.activeColor = this.$u.color["success"];专治强迫症  */
			
			/* uni.setNavigationBarTitle({
				title: this.title
			});		 */	
			
			//添加用户显示答案字段
			
			
		},
		onUnload(){
			wx.setStorageSync("eid",-1);
		},
		methods: {
			change(e)
			{
				
				var that=this;
				if(that.subjectList!=null){
				var index=null;
				if(e==0 || e==2)
				{
				 if(e==0){	
				   index = that.subjectIndex-1;
					}else{
				
				   index = that.subjectIndex+1;
				   }
				
				if (index>=0 && index<that.subjectList.length) {
					//console.log(this.subjectList[index].type);
					
					
					that.errorListReset();
					if(that.tempEid!=-1 && that.tempEid!=index)
					{
						
						var userAnswer = that.subjectList[that.tempEid].userAnswer;
						var answer = that.subjectList[that.tempEid].answer;
						var wrong= (userAnswer==answer)?0:1;
						uni.request({
							url:that.$server+"/user-question/submitAnswer2?qid="+that.subjectList[that.tempEid].qid+"&userAnswer="+userAnswer+"&wrong="+wrong+"&eid="+that.eid,
							header:{'Cookie':"JSESSIONID="+that.JSESSIONID()},
							withCredentials:true,
							success(res){
							
							},
							fail(res){
								that.failSubmit.push(that.tempEid);
							}
						});
						that.tempEid=-1;
					}
					
					that.subjectIndex = index;
					that.currentType = that.subjectList[index].type;
					that.userFavor = that.subjectList[index].userFavor;	
					that.current=-1;
					//that.$forceUpdate(); 
								
				}else{
					uni.showToast({
						icon:"none",
						title:"没有了"
					});
					
				}
					}else{
						this.modalError=true;
						
					}
					}
			},
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
			
			errorListReset()
			{
				var that=this;
				that.errorList.forEach((v,i)=>{
					 that.errorList[i].checked=false;
				});
				//that.$forceUpdate();
			},
			errorSubmit()
			{
				var that=this;
				if(that.errorInfo.length!=0)
				{
					that.modalError = null;
					uni.showLoading({
						title:"提交中"
					});
					var data={
						bid:that.subjectList[that.subjectIndex].qid,
						type:0,
						content:that.errorInfo
					};
					uni.request({
						url:that.$server+"/feedback/add",
						header:{'Cookie':"JSESSIONID="+that.JSESSIONID()},
						withCredentials:true,
						data:data,
						success(res){
							if(res.statusCode==200 && res.data.code!=undefined && res.data.code==200){
								uni.hideLoading();
								uni.showToast({
									icon:"none",
									title:"感谢你的反馈"
								});
								that.errorListReset();
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
					})
				}
			},
			CheckboxChange2(e)
			{
			   var that=this;
			   that.errorInfo="";
			   var errorList = e.detail.value;
			   that.errorList.forEach((v,i)=>{
				   that.errorList[i].checked=false;
			   });
			   errorList.forEach((v,i)=>{
				   that.errorList[errorList[i]].checked=true;
				   that.errorInfo+=that.errorList[errorList[i]].value+",";
			   });
			   
			},
			reset:function(){
				uni.showLoading({
					title:"加载中"
				});
				var that=this;
			    console.log(wx.getStorageSync("eid"));
				if(wx.getStorageSync("eid")==-1){
				uni.request({
					url:that.$server+"/user-question/resetting?eid="+that.eid,
					header:{'Cookie':"JSESSIONID="+that.JSESSIONID()},
					withCredentials:true,
					success(res){
						if(res.statusCode==200 && res.data.code!=undefined && res.data.code==200){
						that.loadData();	
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
				})
				}else{
					that.loadData();
				}
				
				
				
			},
			reload:function(){
				uni.redirectTo({
                  url:"/pages/exam/start?eid="+this.eid					
				})
			},
			
			loadData:async function(){
				
				var url = this.$server+"/exam/start?eid="+this.eid;
				var [error,res] = await uni.request({
					url: url,
					withCredentials:true,
					header:{'Cookie':"JSESSIONID="+this.JSESSIONID()}
					
				});
				
				if(res!=undefined && res.data.code==200 && res.data.data!=undefined && res.data.data!=null && res.data.data.length>0){
				this.subjectList = res.data.data;
				this.currentType = this.subjectList[0].type;
				var eid=wx.getStorageSync("eid");
				for (var i = 0; i < this.subjectList.length; i++) {
					this.$set(this.subjectList[i],"showAnswer",false);	
					if(eid==-1)
					{
						this.subjectList[i].userAnswer="";
					}
				}
				this.flag=true;
				uni.hideLoading();
				}else{
				    uni.hideLoading();
					uni.showToast({
						icon:"none",
						title:"请检查网络连接"
					})
				}
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
				this.modalError = null;
				this.errorListReset();
			},
			SwiperChange: function(e) { //滑动事件
			
			      if(e.target.source=='autoplay' || e.target.source=='touch'){
				let index = e.target.current;
				
				if (index != undefined) {
					//console.log(this.subjectList[index].type);
					
					var that=this;
					that.errorListReset();
					if(that.tempEid!=-1 && that.tempEid!=index)
					{
						
						var userAnswer = that.subjectList[that.tempEid].userAnswer;
						var answer = that.subjectList[that.tempEid].answer;
						var wrong= (userAnswer==answer)?0:1;
						uni.request({
							url:that.$server+"/user-question/submitAnswer2?qid="+that.subjectList[that.tempEid].qid+"&userAnswer="+userAnswer+"&wrong="+wrong+"&eid="+that.eid,
							header:{'Cookie':"JSESSIONID="+that.JSESSIONID()},
							withCredentials:true,
							success(res){
							
							},
							fail(res){
								that.failSubmit.push(that.tempEid);
							}
						});
						that.tempEid=-1;
					}
					
					this.subjectIndex = index;
					this.currentType = this.subjectList[index].type;
					this.userFavor = this.subjectList[index].userFavor;	
								
				}		
										}
			},			
			RadioboxChange : function(e) { //单选选中
			
				var items = this.subjectList[this.subjectIndex].optionList;
				var values = e.detail.value;
				this.subjectList[this.subjectIndex].userAnswer = values;
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
				
			},
			CheckboxChange: function(e) { //复选选中
			    var that=this;
				console.log(e.detail.value);
                if(that.tempEid!=-1 && that.tempEid!=that.subjectIndex)
				{
					
					var userAnswer = that.subjectList[that.tempEid].userAnswer;
					var answer = that.subjectList[that.tempEid].answer;
					var wrong= (userAnswer==answer)?0:1;
					uni.request({
						url:that.$server+"/user-question/submitAnswer2?qid="+that.subjectList[that.tempEid].qid+"&userAnswer="+userAnswer+"&wrong="+wrong+"&eid="+that.eid,
						header:{'Cookie':"JSESSIONID="+that.JSESSIONID()},
						withCredentials:true,
						success(res){
							
						},
						fail(res){
							that.failSubmit.push(that.tempEid);
						}
					});
					
				}
			    that.tempEid=that.subjectIndex;
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
			ShowAnswerChange: function(e) { //显示答案
			
				if(this.subjectList[this.subjectIndex].showAnswer)
				{
					this.subjectList[this.subjectIndex].showAnswer=false;					
				}
				else{
					
					this.subjectList[this.subjectIndex].showAnswer=true;
				}				
			},
			
			FavorSubject: function(e) { //收藏题
			
				/* if(this.userFavor)
				{
					this.userFavor=false;
					this.subjectList[this.subjectIndex].userFavor=false;					
				}
				else{
					
					this.userFavor=true;
					this.subjectList[this.subjectIndex].userFavor=true;	
				}	 */
							
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
			
			        var that=this;
					that.errorListReset();
			        if(that.tempEid!=-1 && that.tempEid!=e)
			        {
						
			        	var userAnswer = that.subjectList[that.tempEid].userAnswer;
			        	var answer = that.subjectList[that.tempEid].answer;
			        	var wrong= (userAnswer==answer)?0:1;
			        	uni.request({
			        		url:that.$server+"/user-question/submitAnswer2?qid="+that.subjectList[that.tempEid].qid+"&userAnswer="+userAnswer+"&wrong="+wrong+"&eid="+that.eid,
			        		header:{'Cookie':"JSESSIONID="+that.JSESSIONID()},
			        		withCredentials:true,
			        		success(res){
			        			
			        		},
			        		fail(res){
			        			that.failSubmit.push(that.tempEid);
			        		}
			        	});
			        	that.tempEid=-1;
			        }
				
					this.modalCard = null;
					//this.currentType=this.subjectList[this.subjectIndex].type;
					this.subjectIndex = e;									
					this.currentType=this.subjectList[this.subjectIndex].type;
			},		
			SubmitError2:function(e)
				{
					var that=this;
					that.modalCard = null;
					uni.showLoading({
						title:"提交答案"
					});
					if(that.tempEid!=-1)
					{
						that.failSubmit.push(that.tempEid);
						that.tempEid=-1;
					}
					if(that.failSubmit.length!=0)
					{
						var failSubmitList=[];
						
						for(var i=0;i<that.failSubmit.length;++i)
						{
							var tempEid=that.failSubmit[i];
							var userAnswer = that.subjectList[tempEid].userAnswer;
							var answer = that.subjectList[tempEid].answer;
							var wrong= (userAnswer==answer)?0:1;
							uni.request({
								url:that.$server+"/user-question/submitAnswer2?qid="+that.subjectList[tempEid].qid+"&userAnswer="+userAnswer+"&wrong="+wrong+"&eid="+that.eid,
								header:{'Cookie':"JSESSIONID="+that.JSESSIONID()},
								withCredentials:true,
								success(res){
									
								},
								fail(res){
									failSubmitList.push(tempEid);
								}
							});
						}
						that.submitcount=that.submitcount+1;
						that.failSubmit=failSubmitList;
						
						if(that.failSubmit.length==0 || that.submitcount>=4)
						{
							wx.setStorageSync("eid",-1);
							uni.navigateTo({
								url:"/pages/exam/examResult?eid="+that.eid
							});
						}else{
							uni.hideLoading();
							that.SubmitError2();
						}
						
					}else{
						uni.hideLoading();
						uni.navigateTo({
							url:"/pages/exam/examResult?eid="+that.eid
						});
					}
					
					
					
				},
			
			SubmitError: function(e) { //提交纠错
			        this.modalCard = null;
			        uni.showLoading({
			        	title:"正在提交答案..."
			        });
					
					var questionVos = [];//this.subjectList;
					var questionVos2=[];
					var questionVos3=[];
					var questionVos4=[];
					var questionVos5=[];
					var that=this;
					var len = this.subjectList.length;
					if(len==15)
					{
						for(var k=0;k<len;++k)
						{
							
							var obj=new Object();
							obj={
							    qid:that.subjectList[k].qid,
							    title:"",
							    type:that.subjectList[k].type,
							    optionList:[],
							    answer:that.subjectList[k].answer,
							    userAnswer:that.subjectList[k].userAnswer,
							    userFavor:that.subjectList[k].userFavor,
							    explain:""
						};
						if(k<len/3){
						questionVos.push(obj);
						}else if(k>=len/3 && k<(len/3)*2)
						{
							questionVos2.push(obj);
						}else{
							questionVos3.push(obj);
						}
						}
					}else{
						
						for(var k=0;k<len-5;++k)
						{
							
							var obj=new Object();
							obj={
							    qid:that.subjectList[k].qid,
							    title:"",
							    type:that.subjectList[k].type,
							    optionList:[],
							    answer:that.subjectList[k].answer,
							    userAnswer:that.subjectList[k].userAnswer,
							    userFavor:that.subjectList[k].userFavor,
							    explain:""
						};
						if(k<len/5){
						questionVos.push(obj);
						}else if(k>=len/5 && k<(len/5)*2)
						{
							questionVos2.push(obj);
						}else if(k>=(len/5)*2 && k<(len/5)*3){
							questionVos3.push(obj);
						}else if(k>=(len/5)*3 && k<(len/5)*4){
							questionVos4.push(obj);
						}else{
							questionVos5.push(obj);
						}
						}
					}
					
					
					var temp = new Object();
					temp={
						    qid:null,
						    title:"",
						    type:-1,
						    optionList:[],
						    answer:"",
						    userAnswer:"",
						    userFavor:false,
						    explain:""
					};
					temp.qid=this.eid;
					questionVos.push(temp);
					questionVos2.push(temp);
					questionVos3.push(temp);
					
					var data=[];
					data.push(questionVos);
					data.push(questionVos2);
					data.push(questionVos3);
					
					if(len>15){
					questionVos4.push(temp);
					questionVos5.push(temp);
					data.push(questionVos4);
					data.push(questionVos5);
					
					}
					that.submitHelper(data,0);
					//questionVos[len].qid=this.eid;
					
					//console.log(this.subjectList[len]);
					var url=this.$server+"/exam/stopExam5";
					 
					// console.log(questionVos);
					//var data={};
					//data["questionVos"] = this.subjectList;
					//console.log(JSON.stringify(data));
					/* uni.request({
						url: url,
						withCredentials:true,
						data:{'datas':JSON.stringify(questionVos)},
						method: "POST",
						dataType: 'json', //'content-type': 'application/x-www-form-zurlencoded' 'content-type': 'application/json'
						header:{'Cookie':"JSESSIONID="+this.JSESSIONID(),'content-type': 'application/x-www-form-urlencoded'},
						success(res) {
							
							if(res.data.code==200)
							{
								uni.navigateTo({
									url:"/pages/exam/answers?eid="+that.eid
								})
								
							}else{
								uni.hideLoading();
								uni.showToast({
									icon:"none",
									title:"出现问题了"
								})
							}
						},
						fail(res) {
							uni.hideLoading();
							uni.showToast({
								icon:"none",
								title:"请检查网络连接"
							})
						},
						complete() {
					
						}
					})
					 */
				
					
					//this.modalError = null;														
			},
			submitHelper(data,index)
			{
				var url=this.$server+"/exam/stopExam3";
				var that=this;
				console.log(index);
				var count=index;
				if(index<data.length){
				
				uni.request({
					url: url,
					withCredentials:true,
					data:{'datas':JSON.stringify(data[index])},
					method: "POST",
					dataType: 'json', //'content-type': 'application/x-www-form-zurlencoded' 'content-type': 'application/json'
					header:{'Cookie':"JSESSIONID="+that.JSESSIONID(),'content-type': 'application/x-www-form-urlencoded'},
					success(res) {
						
						if(res.data.code==200)
						{
							/* if(count==0){
							
							that.submitHelper(data,1);
							}else if(count==1){
								that.submitHelper(data,2);
							}else if(count==2){
								that.submitHelper(data,3);
							}else{
								that.submitHelper(data,4);
							} */
							that.submitHelper(data,count+1);
							
						}else{
							that.submitHelper(data,count);
							/* uni.hideLoading();
							uni.showToast({
								icon:"none",
								title:"出现问题了"
							}) */
						}
					},
					fail(res) {
						that.submitHelper(data,count);
						/* uni.hideLoading();
						uni.showToast({
							icon:"none",
							title:"请检查网络连接"
						}) */
					},
					complete() {
				
					}
				})
				}else{
					uni.navigateTo({
						url:"/pages/exam/answers?eid="+that.eid
					});
				}
				
			}
			
		}
	}
</script>

<style>
	@import "../../colorui/animation.css";
	page {
		background-color: #FFFFFF;
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
