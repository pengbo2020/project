<template>
	<view>
		<view id="top-box" class="cu-bar bg-white solid-bottom" >
			
			<view class="action text-black" v-if="subjectList!=null">
				<text v-if="currentType===1">判断题</text>
				<text v-else-if="currentType===2">单选题</text>
				<text v-else-if="currentType===3">多选题</text>
				<text v-else-if="currentType===4">填空题</text>
				<text v-else-if="currentType===5">问答题</text>
			</view>
			<view class="action text-black" v-if="subjectList==null">
				<text>出现问题了</text>
				
			</view>
			<view class="action" v-if="subjectList!=null">
				<view class="action" @click="FavorSubject">
					<view class="cuIcon-cu-image">
						<text class="lg cuIcon-favor" :class="[userFavor?'cl-icon-favor-fill':'lg cuIcon-favor']"></text>
					</view>
					
				</view>
				
				<view class="action" >
					<view class="cuIcon-cu-image" @click="ShowAnswerChange">
						
						<cl-icon name="cl-icon-round-check" :size="39" ></cl-icon>
					</view>
					
				</view>
				
				<view class="action" @tap="showCardModal" data-target="modalCard" padding-right="10px">
					<view class="cuIcon-cu-image">
						<cl-icon name="cl-icon-question" :size="39" ></cl-icon>
					</view>
					
				</view>
				
				
			
				<!-- <button class="cu-btn bg-green shadow" @tap="showCardModal" data-target="modalCard">答题卡</button> -->
			</view>
			
			
		</view>
		<view v-if ="subjectList==null">
				<view class="padding flex flex-direction " >
					
				 <cl-button  @click="reload">
				 	<text>点击刷新</text>
				 </cl-button>
			</view>
			</view>
		
		<view class="cu-modal" :class="modalCard=='modalCard'?'show':''" @tap="hideCardModal">
			<view class="cu-dialog" @tap.stop>
				
				<scroll-view class="page padding" :scroll-y=true :style="{'height':swiperHeight}" >				
				<view class="cu-bar solid-bottom">
					
					<cl-card  label="题目" padding="0">
						
					</cl-card>				
				</view>
				<view class="grid col-5 ">
					<view class="margin-tb-sm text-center" v-for="(subject,index) in subjectList" :key="index">
						<button class="cu-btn round" :class="[subject.userAnswer.length===0?'line-grey':'bg-red']" @click="AppointedSubject(index)" >{{index+1}}</button>
					</view>
				</view>
				
				<!-- <view class="padding flex flex-direction ">
					<cl-button >
						提 交 试 卷
					</cl-button>
					</view> -->
				
				
				</scroll-view>
			</view>
		</view>
		
		
		<form >
			<swiper :current="subjectIndex" class="swiper-box" @change="SwiperChange" :style="{'height':swiperHeight}">
				<swiper-item v-for="(subject,index) in subjectList">
					
					<view v-if="index-subjectIndex>=-1&&index-subjectIndex<=1">
					
					<scroll-view class="page padding" :scroll-y=true :style="{'height':swiperHeight}" >
					<cl-card  label="题目" padding="0">
						<cl-noticebar
						:text="subject.title"	
						></cl-noticebar>
					</cl-card>
					
										
					<!-- <view class="cu-bar bg-white solid-bottom">
						<view class="action text-black">
							<text class="cuIcon-title text-red"></text>{{subject.title}}
						</view>
						
						
					</view> -->
					<view>

						<radio-group class="block"  @change="RadioboxChange" v-if="subject.type===1||subject.type===2">
							<view class="cu-form-group" v-for="option in subject.optionList">
								<radio :value="option.id" :checked="subject.userAnswer.indexOf(option.id) > -1?true:false" style="size: 10rpx;"></radio>
								<view class="title text-black">{{option.id}}.{{option.content}}</view>
							</view>
						</radio-group>

						<checkbox-group class="block"  @change="CheckboxChange" v-else-if="subject.type===3">
							<view class="cu-form-group" v-for="option in subject.optionList">
								<checkbox :value="option.id" :class="subject.userAnswer.indexOf(option.id) > -1?'checked':''" :checked="subject.userAnswer.indexOf(option.id) > -1?true:false"></checkbox>
								<view class="title  text-black">{{option.id}}.{{option.content}}</view>
							</view>
						</checkbox-group>

						<view v-else-if="subject.type===4">
							<view class="cu-form-group solid-bottom">
								<view class="title  text-black">
									答：
								</view>
								<input placeholder="文本输入框" name="input" v-model="subject.userAnswer" @blur="textInput" ></input>
							</view>
						</view>

						<view v-else-if="subject.type===5">
							<view class="cu-bar cu-bar-title bg-white margin-top">
								<view class="action  text-black">
									答：
								</view>
							</view>
							<view class="cu-form-group solid margin">
								<textarea maxlength="-1"  @blur="textInput" v-model="subject.userAnswer" placeholder="多行文本输入框"></textarea>
							</view>
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
							<view class="content">
					
								<view class="uni-padding-wrap">
									
									<view class="uni-common-mt" style="background:#FFF; padding:20rpx;">
										<rich-text :nodes="subject.nodes"></rich-text>
									</view>
									
									<view class="uni-common-mt" style="background:#FFF; padding:20rpx;">
										<rich-text :nodes="subject.strings"></rich-text>
									</view>
								</view>
							</view>
							
							
							
							
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
		
		</view>
		
		
		

	
</template>

<script>
	
   import jyfParser from "@/components/jyf-parser/jyf-parser";
   var subjectList;
	export default {
		data() {
			return {
				type:0,
				pn:0,
				html: '',
				userFavor:false,//是否已收藏
				currentType: 0, //当前题型
				subjectIndex: 0,//跳转索引
				autoShowAnswer: false,//答错是否显答案
				autoRadioNext:true,//判断题、单项题，自动移下一题
				swiperHeight: '800px',//
				/* title: '初中二年级2019期中考券', */
				subjectList:null,
				modalCard: null ,//显示答题卡
				modalError:null , //纠错卡
				errorList:['题目不完整','答案不正确','含有错别字','图片不存在','解析不完整','其他错误'],
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
				/* 	console.log("屏幕可用高度 " + tempHeight);
 */
					uni.createSelectorQuery().select("#top-box").fields({
						size: true,
						scrollOffset: true
					}, (data) => {
						tempHeight -= data.height;
						/* console.log("减掉顶部后的高度 " + tempHeight); */
						_me.swiperHeight = tempHeight + 'px';

						/* uni.createSelectorQuery().select("#foot-box").fields({
							size: true,
							scrollOffset: true
						}, (data) => {
							tempHeight -= data.height;
							console.log("减掉底部后的高度 " + tempHeight);
							_me.swiperHeight = tempHeight + 'px';
							console.log("滑屏最后高度 " + _me.swiperHeight);
						}).exec(); */

					}).exec();
				}
			});
			
			

		},
		onLoad(option) {
			
			
		    this.type=parseInt(option.type);
			this.loadData();
				
			
			//添加用户显示答案字段
			
			
		},
		methods: {
			more:async function()
			{
				
					this.pn+=1;
					var l=this.subjectList.length;
					var url=this.$server+"/question/get?type="+this.type+"&pn="+this.pn;
					var[error,res] = await uni.request({
							url:url 
						});
						if(res.data.code==200){
									if(res.data.data!=null){
									var list=res.data.data;
									for(var i=0;i<list.length;++i)
									{
										this.subjectList.push(list[i]);
										this.$set(this.subjectList[l+i],"showAnswer",false);
									}
								
								}
						}
						
			},
			
			loadData:async function(){
				var url=this.$server+"/question/get?type="+this.type+"&pn="+this.pn;
				var [error,res] = await uni.request({
					url:url ,
					
				});
				
				if(res.data.code==200){
				this.subjectList = res.data.data;
				this.currentType = this.subjectList[0].type;
				console.log(res.data.data);
				for (var i = 0; i < this.subjectList.length; i++) {
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
				this.subjectList[i].strings="";
				  //strings: '<div style="text-align:center;"><img src="https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/uni@2x.png"/></div><div style="text-align:center;"><img src="https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/uni@2x.png"/></div>'
				    var imageList=this.subjectList[i].note.imageList;
					var images="";
					console.log(imageList);
				    if(imageList!=null){
				    	for(var j=0;j<imageList.length;++j)
				    	{
				    		
				    		images+="<div class='image' mode='widthFix' style='text-align:center;with:100px,height:100px'><img src='"+"http://192.168.43.202:8081/noteImgs/"+imageList[j].path+"'></div>";
				    	}
						this.subjectList[i].strings=images;
				    	
				    }
					this.$set(this.subjectList[i],"showAnswer",true);				
				}
				
				//this.html = '<div>Hello World!</div>';
				//this.html+="<div style='left:50%'><image src='../../static/logo.png' ></image><div>";
				/* this.html+="<image src='../../static/psbzc2.jpg' style='text-align: center;'></image>";
				
				 */
				
				/* this.html="";
				var imageList=this.subjectList[0].note.imageList;
				var content = this.subjectList[0].note.content;
				if(content!=null){
					content="<div>"+content+"</div>";
					this.html+=content;
				}
				
				if(imageList!=null){
				for(var i=0;i<imageList.length;++i)
				{
					
					this.html+="<image src='"+"http://192.168.43.202:8081/noteImgs/"+imageList[i].path+"'></image>"
				}
				
			} */
			/* nodes: [{
					name: 'div',
					attrs: {
						class: 'div-class',
						style: 'line-height: 20px;'
					},
					children: [{
						type: 'text',
						text: '&nbsp; 在山的那边睡的那边有一个蓝精灵在山的那边睡的那边有一个蓝精灵在山的那边睡的那边有一个蓝精灵在山的那边睡的那边有一个蓝精灵在山的那边睡的那边有一个蓝精灵在山的那边睡的那边有一个蓝精灵在山的那边睡的那边有一个蓝精灵'
					}]
				}],
				strings: '<div style="text-align:center;"><img src="https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/uni@2x.png"/></div><div style="text-align:center;"><img src="https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/uni@2x.png"/></div>'
			
			 */
			
			
			
			}else{
				uni.showToast({
					icon:"none",
					title:"请检查网络连接"
				})
				//console.log(res.data.data);
			}
			},
			noteModify:function(qid){
				console.log(qid);
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
					    this.subjectList[i].strings="";
					      //strings: '<div style="text-align:center;"><img src="https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/uni@2x.png"/></div><div style="text-align:center;"><img src="https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/uni@2x.png"/></div>'
					        var imageList=this.subjectList[i].note.imageList;
					    	var images="";
					        if(imageList!=null){
					        	for(var j=0;j<imageList.length;++j)
					        	{
					        		
					        		images+="<div class='image' mode='widthFix' style='text-align:center;'><img src='"+"http://192.168.43.202:8081/noteImgs/"+imageList[j].path+"'></div>";
					        	}
					    		this.subjectList[i].strings=images;
					        	
					        }
												
					if(index>this.subjectList.length/2){
					this.more();						
						}
												
			    }
				}
			},			
			RadioboxChange : function(e) { //单选选中
			
				var items = this.subjectList[this.subjectIndex].optionList;
				var values = e.detail.value;
				this.subjectList[this.subjectIndex].userAnswer = values;
				if(this.autoRadioNext && this.subjectIndex < this.subjectList.length - 1){
					//this.subjectIndex += 1;
					this.$set(this.subjectList[this.subjectIndex],"showAnswer",true);						
					};
				
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
			
				if(this.userFavor)
				{
					this.userFavor=false;
					this.subjectList[this.subjectIndex].userFavor=false;					
				}
				else{
					
					this.userFavor=true;
					this.subjectList[this.subjectIndex].userFavor=true;	
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
					
					
														
			},			
			
			SubmitError: function(e) { //提交纠错
			       
				    for(var i=0;i<subjectList.length;++i)
					{
						console.log(subjectList[i]);
					}
					
					this.modalError = null;														
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
