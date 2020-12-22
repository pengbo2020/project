<template>
	<view >
		
		<view id="top-box" :class="subjectList!=null?'cu-bar bg-white solid-bottom':'cu-bar bg-white'">
			
			<view class="action text-black" v-if="subjectList!=null">
				<!-- <text v-text="知识点" ></text> -->
				<text>知识点</text>
			</view>
			<view class="action" v-if="subjectList!=null">
				<!-- <view class="action" @click="play(subjectIndex)">
					<view class="cuIcon-cu-image">
			
						<text class="cl-icon-notification" :class="[userFavor?'cl-icon-notification-fill':'cl-icon-notification']"></text>
						
					 </view>
					
				</view> -->
				<view class="action" @click="FavorSubject1">
					<view class="cuIcon-cu-image">
						<text class="lg cuIcon-favor" :class="[subjectList[subjectIndex].knowledge.md5=='t'?'cl-icon-favor-fill':'lg cuIcon-favor']"></text>
					</view>
					
				</view>
				
				<!-- <view class="action" @tap="clshow" data-target="errorCard" style="margin-left: 10rpx;">
					<view class="cuIcon-cu-image">
						<cl-icon name="cl-icon-round-close" :size="39" ></cl-icon>
					</view>
					
				</view> -->
				
				<view class="action" @tap="showErrorModal" data-target="errorCard" style="margin-left: 20rpx;">
					<view class="cuIcon-cu-image">
						<cl-icon name="cl-icon-round-check" :size="39" ></cl-icon>
					</view>
					
				</view>
				<view class="action" @tap="showCardModal" data-target="modalCard" style="margin-left: 20rpx;">
					<view class="cuIcon-cu-image">
						<cl-icon name="cl-icon-question" :size="39" ></cl-icon>
					</view>
					
				</view>
			
				<!-- <view class="action" @tap="showCardModal" data-target="modalCard" style="margin-left: 10rpx;">
					<view class="cuIcon-cu-image">
						<cl-icon name="cl-icon-question" :size="39" ></cl-icon>
					</view>
					
				</view>
				
				<view class="action" @tap="showErrorModal" data-target="errorCard" style="margin-left: 10rpx;">
					<view class="cuIcon-cu-image">
						<cl-icon name="cl-icon-question" :size="39" ></cl-icon>
					</view>
					
				</view> -->
				
				
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
					<!-- <view class="cu-bar bg-white justify-end">
						<view class="content">知识点</view>
						<view class="action" @bindtap="hideCardModal" @tap="hideCardModal"><text class="cuIcon-close text-gray"></text></view>
					</view>
					<view class="padding-xl"> -->
				<scroll-view class="page padding" :scroll-y=true :style="{'height':swiperHeight}" >				
				<view class="cu-bar solid-bottom">
					
					<cl-card  label="知识点" padding="0">
						
					</cl-card>	
					<view class="action" @bindtap="hideCardModal" @tap="hideCardModal"><text class="cuIcon-close text-gray"></text></view>			
				</view>
				
				<view class="grid col-5 ">
					<view class="margin-tb-sm text-center" v-for="(subject,index) in subjectList" :key="index">
						<button class="cu-btn round" :class="[subject.knowledge.md5==='n'?'line-grey':'bg-blue']" @click="AppointedSubject(index)" >{{index+1}}</button>
					</view>
				</view>
				
				
				</scroll-view>
			</view>
		<!-- </view> -->
		</view>
		
		
		
		
			<cl-popup :visible.sync="show" direction="bottom">
				<view  :class="subjectList!=null?'cu-bar bg-white':'cu-bar bg-white'" v-if="flag==true">
					
					<view class="action text-black"  style="margin-left: 0rpx;">
						<!-- <text v-text="知识点" ></text> -->
						<text >{{subjectList[subjectIndex].knowledge.name.length>15?subjectList[subjectIndex].knowledge.name.substring(0,15)+"...":subjectList[subjectIndex].knowledge.name }} {{subjectIndex+1}}/{{subjectList.length}}</text>
					</view>
					
				</view>
				<view  class="cu-bar2" style="margin-top: 10rpx;">
				<view class="action text-black"  style="margin-top: -15rpx;margin-right: 20rpx;" >
					
					<text >3.0</text>
				</view>	
				<view class="action text-black"  style="width:85%">
					<!-- <text v-text="知识点" ></text> -->
					<u-slider :step="0.1" :height="height" :block-width="blockWidth"
						:active-color="activeColor" :value="3.999999" 
						:use-slot="useSlot" v-model="value"
						:min="min" :max="max" 
						@end="end"
						@moving="moving"
					></u-slider>
				</view>
				<view class="" @tap="showCardModal" data-target="modalCard" style="margin-top: -15rpx; margin-left: 20rpx;margin-right: 0rpx;">
					5.0
					
				</view>
				
				</view>
				<view  class="cu-bar" style="margin-top: -60rpx;" v-if="flag==true">
					<view class="cuIcon-cu-image" style="margin-left: 5%;">
						<text class="cuIcon-down lg text-gray" style="font-size: 50rpx;"></text>
					</view>
					<view class="cuIcon-cu-image" style="margin-left: 10%;">
						<cl-icon name="cl-icon-round-close" :size="50" ></cl-icon>
					</view>
					<view class="cuIcon-cu-image" style="margin-left: 10%;">
						<cl-icon name="cl-icon-round-close" :size="70" ></cl-icon>
					</view>
					<view class="cuIcon-cu-image" style="margin-left: 10%;">
						<cl-icon name="cl-icon-round-close" :size="50" ></cl-icon>
					</view>
					<view class="cuIcon-cu-image" style="margin-left: 10%;">
						<cl-icon name="cl-icon-round-close" :size="50" ></cl-icon>
					</view>
				<view class="action" >
					<!-- <view class="action" @click="play(subjectIndex)">
						<view class="cuIcon-cu-image">
				
							<text class="cl-icon-notification" :class="[userFavor?'cl-icon-notification-fill':'cl-icon-notification']"></text>
							
						 </view>
						
					</view> -->
					
					
					
					<!-- <view class="action" @tap="clshow" data-target="errorCard" style="margin-left: 10rpx;">
						<view class="cuIcon-cu-image">
							<cl-icon name="cl-icon-round-close" :size="45" ></cl-icon>
						</view>
						
					</view>
					<view class="action" @tap="clshow" data-target="errorCard" style="margin-left: 20%;">
						<view class="cuIcon-cu-image">
							<cl-icon name="cl-icon-round-close" :size="45" ></cl-icon>
						</view>
						
					</view>
					
					<view class="action" @tap="clshow" data-target="errorCard" style="margin-left: 20%;">
						<view class="cuIcon-cu-image">
							<cl-icon name="cl-icon-round-close" :size="45" ></cl-icon>
						</view>
						
					</view>
					
					<view class="action" @tap="showErrorModal" data-target="errorCard" style="margin-left: 20%;">
						<view class="cuIcon-cu-image">
							<cl-icon name="cl-icon-round-check" :size="45" ></cl-icon>
						</view>
						
					</view>
					<view class="action" @tap="showCardModal" data-target="modalCard" style="margin-left: 20%;">
						<view class="cuIcon-cu-image">
							<cl-icon name="cl-icon-question" :size="45" ></cl-icon>
						</view>
						
					</view> -->
				
					<!-- <view class="action" @tap="showCardModal" data-target="modalCard" style="margin-left: 10rpx;">
						<view class="cuIcon-cu-image">
							<cl-icon name="cl-icon-question" :size="39" ></cl-icon>
						</view>
						
					</view>
					
					<view class="action" @tap="showErrorModal" data-target="errorCard" style="margin-left: 10rpx;">
						<view class="cuIcon-cu-image">
							<cl-icon name="cl-icon-question" :size="39" ></cl-icon>
						</view>
						
					</view> -->
					
					
				</view>
				</view>
				
				
				
				
				<!-- <uni-section title="设置音频播放速率" type="line"></uni-section> -->
				<!-- <view class="example-body">
					<uni-number-box :min="0.5" :max="2.0" :value="1.0" :step="0.5" @change="rateChange" />
				</view> -->
				<!-- <uni-section title="反馈" type="line"></uni-section> -->
				<!-- <view class="uni-padding-wrap uni-common-mt" style="left: 10rpx;">
				<button type="default" size="mini">进行反馈</button>
				</view> -->
				
				
				
			<!-- 	</view> -->
				
				<!-- <view class="example-body">
					<button type="default" size="mini"  :disabled="rate>0.5?false:true"  style="margin-right: 4rpx;" @tap="rateChange(0)">-</button><button type="default" size="mini" style="margin-right: 4rpx;">{{rate}}</button><button type="default" size="mini" :disabled="rate<2.0?false:true" @tap="rateChange(1)">+</button>
				</view> -->
				
				
			</cl-popup>
		
	
		<form>
			<swiper :current="subjectIndex" class="swiper-box"  @change="SwiperChange" :style="{'height':swiperHeight}">
				<swiper-item v-for="(subject,index) in subjectList">
					
					<view v-if="index-subjectIndex>=-1&&index-subjectIndex<=1">
					<scroll-view class="page padding" :scroll-y=true :style="{'height':swiperHeight}" >
					<cl-card label="知识点名称" padding="0" >
						<cl-noticebar
						:text="subject.knowledge.name"	
						></cl-noticebar>	
						</cl-card>
					<cl-card :label="item.descName" padding="0" v-for="item in subject.knowledgeDescList">
						<cl-noticebar
						:text="item.descDetail"	
						></cl-noticebar>
					</cl-card>
					</scroll-view>
					

					

					</view>
				</swiper-item>
			</swiper>
		</form>
		
		
		<view id="foot-box" style="height: 0rpx;" :key="image" >
			
         <view class="holdon" @click="play(subjectIndex)" v-if="(flag==true && modalCard==null && show==false && modalError==null)" >
         	<image  class="ball" :style="'left:'+(moveX == 0 & x>0? x+'%':moveX+'px')+';top:'+(moveY == 0 & y>0? y+'%':moveY+'px')"	 
         			 :src="'../../static/icon/'+subjectList[subjectIndex].userFavor+'.png'"  mode="aspectFit">
         	</image>
         </view>
		 
		 <view class="info" style="background-color: #555500; opacity: 0.6;">
		 	<view class="song-title">
		 		<view class="song-name" style="font-size: small;">{{songList[nowIndex].name}}</view>
		 		<!-- <view class="song-author">{{songList[nowIndex].author}}</view> -->
		 	</view>
		 	
		 	<view class="audio-wrapper action text-black">
		 		<view class="audio-number">0</view>
		 		<!-- <slider class="audio-slider"  :value="currentTime" min="0" :max="duration" @change="sliderChange" block-size="15"  /> -->
				 <slider class="audio-slider" value="10"   activeColor="#ffffff" backgroundColor="#909399" block-color="#ffffff" block-size="20" />
		 		<view class="audio-number">50</view>
				
		 	</view>
		 	<view  class="audio-control-wrapper">
		 		<view  @tap="updateWay()" v-if="playWay==2">
		 			<image  class="iconbtn" src="../../static/image/icon/suijibofang.png"/>
		 		</view>
		 		<view  @tap="updateWay()" v-if="playWay==1">
		 			<image  class="iconbtn" src="../../static/image/icon/xunhuanbofang.png"/>
		 		</view>
		 		<view  @tap="updateWay()" v-if="playWay==0">
		 				<image  class="iconbtn" src="../../static/image/icon/danquxunhuan.png"/>
		 		</view>
		 		<view  @tap="last()" >
		 			<image class="iconbtn" src="../../static/image/icon/shangyishou.png"></image>
		 		</view>
		 		<view @tap="play()" v-if="playState===1">
		 				<image class="iconbtn play" src="../../static/image/icon/kaishi-2.png"></image>
		 		</view>
		 		<view @tap="pause()" v-else >
		 			<image class="iconbtn play" src="../../static/image/icon/zanting-2.png"></image>
		 		</view>
		 		<view  @tap="next()" >
		 			<image class="iconbtn" src="../../static/image/icon/xiayishou.png"></image>
		 		</view>
		 		<view><image class="iconbtn" src="../../static/image/icon/yinleliebiao.png"></image></view>
		 	</view>
		 
		 </view>
		 
		 
		</view>
		
		
		

	</view>
</template>

<script>
	
    import dragball from "../../components/drag-ball/drag-ball.vue";
	import uniNumberBox from '@/components/uni-number-box/uni-number-box.vue'
	import uniSection from '@/components/uni-section/uni-section.vue'
	
   var subjectList;
	export default {
		props: {
			x: {
				type: Number,
				default:80
			},
			y: {
				type: Number,
				default:80
			},
			image:{
				type:String,
				default: '../../static/icon/0.png'
			}
		},
		data() {
			return {
				playWay: 0,
				playState: 1,
				nowIndex: 0,
				duration: 0,
				currentTime: 0,
				songList: [{
						"name": "走歌人",
						"author": "暗杠",
						"src": "http://music.163.com/song/media/outer/url?id=418990139.mp3",
						"img": "http://p2.music.126.net/_UOTSqLC8qHRivyuUBC9OQ==/18200215974944920.jpg"				
					},
					{
						"name": "走歌人18",
						"author": "暗杠",
						"src": "http://music.163.com/song/media/outer/url?id=866018207.mp3",
						"img": "http://p2.music.126.net/D9_qDt18yiHxVPr6CRGgLA==/109951163406952902.jpg"
				
					},
					{
						"name": "走歌人原版无损伴奏",
						"author": "暗杠",
						"src": "http://music.163.com/song/media/outer/url?id=430685718.mp3",
						"img": "http://p2.music.126.net/_UOTSqLC8qHRivyuUBC9OQ==/18200215974944920.jpg"
					}
				],
				value: 30,
				useSlot: false,
				setp: 1,
				activeColor: '#2979ff',
				height: 6,
				blockWidth: 30,
				step: 1,
				min: 0,
				max: 100,
				id: '', //数据请求id
				paused: false,
				recycled: false,
				playing: 0,
				playList: [
					{
						id: 1,
						src: 'https://img-cdn-qiniu.dcloud.net.cn/uniapp/audio/music.mp3',
						name: '致爱丽丝',
						album: '睡前轻音乐大全',
						status: 0
					},
					{
						id: 2,
						src: 'http://mp3.jinmiao.cn/mp3file/huiben/19/quting9xu.mp3',
						name: '鼹鼠做裤子',
						album: '童话故事大全',
						status: 0
					}
				],
				now: '00:00',
				duration: '00:00',
				progress_max: 0,
				show:false,
				rate:1.0,
				fab:true,
				start:[0,0],
				moveY:0,
				moveX:0,
				windowWidth:'',
				windowHeight:'',
				cid:-1,
				total:false,
				pre:null,
				flag:false,
				hasFile:false,
				audioFilePath:null,
				innerAudioContext:null,
				name:"hello world",
				type:0,
				pn:0,
				html: '',
				userFavor:0,//是否已收藏
				currentType: 0, //当前题型
				subjectIndex: 0,//跳转索引
				autoShowAnswer: false,//答错是否显答案
				autoRadioNext:true,//判断题、单项题，自动移下一题
				swiperHeight: '800px',//
				subjectList:null,
				modalCard: null ,//显示答题卡
				modalError:null , //纠错卡
				errorList:[
					
					{
					 checked:false,
					 value:'知识点有错字'	 
					},
					{
					 checked:false,
					 value:'知识点不完整'	 
					},
				
					{
					 checked:false,
					 value:'排版有错'	 
					}],
				audioManager:wx.getBackgroundAudioManager(),
				playId:null,
			}
		},
		mounted() {
			
			const { windowWidth, windowHeight } = uni.getSystemInfoSync();	
			
			this.windowWidth = windowWidth
			this.windowHeight = windowHeight
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
						//console.log("减掉顶部后的高度 " + tempHeight);
						_me.swiperHeight = tempHeight + 'px';

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
		
		onHide(){
			//this.innerAudioContext.pause();
			
		},
		onUnload(){
			
			//this.innerAudioContext.pause();
		},
		onLoad(option) {
			
			this.cid = isNaN(parseInt(option.cid))?-1:parseInt(option.cid);
			
			this.loadData();
			
			this.initAudioManager();
			
			
	
			
			
		},
		methods: {
			FavorSubject1: function(e) { //收藏题
			   var kid=this.subjectList[this.subjectIndex].knowledge.id;
			   var favor=this.subjectList[this.subjectIndex].knowledge.md5;
			   var that=this;
			   if(favor=='t')
			   {
			   	uni.request({
			   		url:that.$server+"/user-knowledge/userFavor?kid="+kid+"&favor=0",
					withCredentials:true,
					header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
			   		success(res){
			   			if(res.data.code==200){
			   			that.userFavor=false;
			   			that.subjectList[that.subjectIndex].knowledge.md5='f';
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
			   
			   	uni.request({
			   		url:that.$server+"/user-knowledge/userFavor?kid="+kid+"&favor=1",
					withCredentials:true,
					header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
			   		success(res){
			   			if(res.data.code==200){
			   			that.userFavor=true;
			   			that.subjectList[that.subjectIndex].knowledge.md5='t'
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
			add(i){
				var that=this;
				 var kid=that.subjectList[i].knowledge.id;
				uni.request({
					url:this.$server+"/user-knowledge/add?kid="+kid,
					withCredentials:true,
					header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
					success(res){
						that.subjectList[i].knowledge.md5='f'
						
					},
					fail(res){
						
					}
				})
				
			},
			rateChange(e){
				console.log(this.audioManager.currentTime);
				console.log(this.playId);
				var that=this;
				if(e==0)
				{
					if(that.rate>0.5)
					{
						that.rate-=0.5;
					}
				}else{
					if(that.rate<2.0)
					{
						that.rate+=0.5;
					}
				}
				if(that.playId!=null)
				{
				    that.audioManager.pause(); 	
					var currentTime=that.audioManager.currentTime;
					that.audioManager.title = that.subjectList[that.playId].knowledge.name;
					that.audioManager.singer="研知音";
					that.audioManager.playbackRate=that.rate;
					that.audioManager.startTime=currentTime;
					
					if(that.subjectList[that.playId].userFavor==1){
						that.audioManager.src=this.$server+"/knowledgeaudio/"+this.subjectList[this.playId].audoFilePath.path;;
				}
	
					
				}
				
			},
			clshow(){
				if(this.show==false && this.flag==true)
				{
					this.show=true;
					this.fab=false;
				}
			},
			drag_start(event){
				this.start[0]= event.touches[0].clientX-event.target.offsetLeft;
				this.start[1]= event.touches[0].clientY-event.target.offsetTop;
				
			},
			drag_hmove(event){
					let	 tag 	 = event.touches;
					if(tag[0].clientX < 0 ){
						tag[0].clientX = 0
					}
					if(tag[0].clientY < 0 ){
						tag[0].clientY = 0
					}
					if(tag[0].clientX > this.windowWidth ){
						tag[0].clientX = this.windowWidth
					}
					if(tag[0].clientY > this.windowHeight ){
						tag[0].clientY = this.windowHeight
					}
					this.moveX	 = tag[0].clientX-this.start[0];
					this.moveY	 = tag[0].clientY-this.start[1];
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
				if(that.errorInfo!=null && that.errorInfo.length!=0)
				{
					that.modalError = null;
					uni.showLoading({
						title:"提交中"
					});
					var data={
						bid:that.subjectList[that.subjectIndex].knowledge.id,
						type:1,
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
								title:"提交反馈失败"
							})	
							}
						},
						fail(res){
							uni.hideLoading();
							uni.showToast({
								icon:"none",
								title:"提交反馈失败"
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
			
			initAudioManager(){
				var that=this;
				that.audioManager.coverImgUrl = that.$server+'/images/1.jpeg';
				that.audioManager.startTime=0;
				
				that.audioManager.onWaiting(function(){
					
					wx.showLoading({
						title:"缓冲中"
					});
				})
				that.audioManager.onCanplay(function(){
				    wx.hideLoading();
				});
				that.audioManager.onPrev(function(){
					
					
					//that.subjectList[that.playId].userFavor=0;
					var playid=null;
					if(0==that.playId)
					{
						playid=that.subjectList.length-1;
						
					}else{
						playid=that.playId-1;
					}
					
					
					
					that.play(playid);
					
				});
				that.audioManager.onNext(function(){
					//that.subjectList[that.playId].userFavor=0;
					var playid=null;
					if(that.subjectList.length-1==that.playId)
					{
						playid=0;
						
					}else{
						playid=that.playId+1;
					}
					/* if(that.subjectIndex!=that.playId)
					{
						
						that.subjectList[that.subjectIndex].userFavor=0;
						
					}else{
						that.subjectList[that.subjectIndex].userFavor=1;
					} */
			
					that.play(playid);
				});
				that.audioManager.onStop(function(){
					/* if(that.listData[that.playId].audioPlayback==true){
					that.play(that.playId);
					} */
					/* if(that.subjectIndex==that.playId)
					{
						
						that.userFavor=0;
						
					} */
					that.subjectList[that.playId].userFavor=0;
					that.subjectList[that.subjectIndex].userFavor=0;
					that.playId=null;
				});
				that.audioManager.onEnded(function(){
					/* if(that.subjectIndex==that.playId)
					{
						
						that.userFavor=0;
						
					} */
					that.subjectList[that.playId].userFavor=0;
					that.subjectList[that.subjectIndex].userFavor=0;
					that.playId=null;
				});
				that.audioManager.onPause(function(){
					if(that.subjectIndex==that.playId)
					{
						
						that.userFavor=2;
						
					}
					that.subjectList[that.playId].userFavor=2;
					
				});
				that.audioManager.onPlay(function(){
					if(that.subjectIndex==that.playId)
					{
						
						that.userFavor=1;
						
					}
					that.subjectList[that.playId].userFavor=1;
				})
				
			},
			more:async function()
			{
				
					this.pn+=1;
					var l=this.subjectList.length;
					var url=this.$server+"/knowledge-basic/get?pn="+this.pn+"&ps=20&cid="+this.cid;
					var[error,res] = await uni.request({
							url:url,
							withCredentials:true,
							header:{'Cookie':"JSESSIONID="+this.JSESSIONID()}, 
						});
						if(res!=undefined &&res!=null &&res.data!=undefined && res.data.code==200){
									if(res.data.data!=null && res.data.data.length>0){
										
									var list=res.data.data;
									for(var i=0;i<list.length;++i)
									{
										//this.total=true;
										list[i].userFavor=0;
										list[i].userAnswer="";
										this.subjectList.push(list[i]);
										
									}
								
								}else{
									this.total=true;
								}
						}
						
			},
			
			loadData:async function(){
				uni.showLoading({
					title:"加载中"
				});
				var url=this.$server+"/knowledge-basic/get?pn="+this.pn+"&ps=20&cid="+this.cid;
				var [error,res] = await uni.request({
					url:url ,
					withCredentials:true,
					header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
					
				});
				
				if(res!=undefined && res.data.code==200 && res.data.data!=null && res.data.data.length>0){
				
				var data=res.data.data;
				for(var i=0;i<data.length;++i)
				{
					data[i].userAnswer="";
					data[i].userFavor=0;
				}
				this.subjectList = res.data.data;
				if(this.subjectList!=null){
				this.name=this.subjectList[0].knowledge.name;	
				}
				
				
				uni.hideLoading();
				this.flag=true;
				
			}else{
				uni.hideLoading();
				uni.showToast({
					icon:"none",
					title:"出现问题了"
				})
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
				//this.show=false;
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
				    //this.userFavor=this.subjectList[index].userFavor;	
					//this.subjectList[this.pre].userFavor=false;
					//this.pre=index;
					//this.innerAudioContext.pause();	
					this.changeImage()
					
					this.subjectIndex = index;
					this.name = this.subjectList[index].knowledge.name; 
												
					if(!this.total && index>this.subjectList.length/2){
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
					this.subjectIndex += 1;						
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
							break;
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
			
			play(index){
				/* console.log(e);
				this.audioPlayback=true; */
				if(this.playId!=null && this.playId!=index)
				{
					this.subjectList[this.playId].userFavor=0;
					this.subjectList[this.playId].userAnswer="";
					//this.listData[this.preIndex].audioPlayback=false;
				}
				this.playId=index;
				this.add(index);
				
				
				if(this.subjectList[index].userFavor==1)
				{
					this.subjectList[index].userFavor=2;
					this.subjectList[index].userAnswer="";
					this.userFavor=2;
					this.audioManager.pause();
					//this.audioManager.startTime=this.audioManager.currentTime;
				}else if(this.subjectList[index].userFavor==0){
				
				this.subjectList[index].userFavor=1;
				this.subjectList[index].userAnswer="A";
				this.userFavor=1;
				//this.pre=index;
				this.playId=index;
				this.playAudio();
				}else{
	
					this.subjectList[index].userFavor=1;
					this.subjectList[index].userAnswer="A";
					this.userFavor=1;
					this.audioManager.play();
					
				}
				
				this.changeImage();
				
			},
			changeImage()
			{
				//console.log(this.image);
				if(this.subjectList[this.subjectIndex].userFavor==0)
				{
					this.image='../../static/icon/0.png';
				}else if(this.subjectList[this.subjectIndex].userFavor==1)
				{
					this.image='../../static/icon/1.png';
				}else{
					this.image='../../static/icon/2.png';
				}
				//console.log(this.image);
			},
			
			FavorSubject: function(e) { //收藏题 
			 
			   
	            this.playId=this.subjectIndex;
				if(this.userFavor)
				{
					/* if(this.innerAudioContext.paused==false){
						this.innerAudioContext.pause();
				  	
					} */
					this.subjectList[this.subjectIndex]
					this.userFavor=false;
					this.subjectList[this.subjectIndex].userFavor=false;
					this.audioManager.pause();
									
				}
				else{
					//https://img-cdn-qiniu.dcloud.net.cn/uniapp/audio/music.mp3
					if(this.pre!=null)
					{
						this.subjectList[this.pre].userFavor=false;
					}
					
					if(this.subjectList[this.subjectIndex].audoFilePath!=null){
						
						
					this.userFavor=true;
					this.subjectList[this.subjectIndex].userFavor=true;	
					//this.innerAudioContext.src =this.$server+"/knowledgeaudio/"+this.subjectList[this.subjectIndex].audoFilePath.path;
					
					this.playId=index;
					this.playAudio();
					
					/* this.innerAudioContext.autoplay = true;
					this.innerAudioContext.play();
					
					this.innerAudioContext.onPlay(() => {
						uni.showToast({
							icon:"none",
							title:"开始播放知识点语音"
						})
					
					 });
					 this.innerAudioContext.onEnded(()=>{
						
						 this.userFavor=false;
						 uni.showToast({
						 	icon:"none",
						 	title:"播放完成"
						 })
					 });
					 this.innerAudioContext.onError((res) => {
						 this.userFavor=false;
					  uni.showToast({
					  	icon:"none",
					  	title:"出现了一些问题"
					  })
					 }); */
					
				}else{
					uni.showToast({
						icon:"none",
						title:"出现了一些问题"
					})
				}
								}
			},
			
			playAudio()
			{
				var that=this;
				that.audioManager.title = that.subjectList[that.playId].knowledge.name;
				that.audioManager.singer="研知音";
				that.audioManager.playbackRate=that.rate;
				that.audioManager.startTime=0;
				that.audioManager.src=this.$server+"/knowledgeaudio/"+this.subjectList[this.playId].audoFilePath.path;;
			
				//that.audioManager.src="https://www.lztk.xyz/knowledgeaudio/3.mp3";
				//that.audioManager.src=that.$server+"/newsAudio/"+that.listData[that.playId].id+".mp3";
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
				
				   /* this.userFavor=this.subjectList[e].userFavor;
				   if(this.playId!=null && this.playId!=e){
				     
				   this.subjectList[this.playId].userFavor=false;
				   
				  
				   } */
					this.modalCard = null;
					this.subjectIndex = e;
					this.changeImage();
					//console.log(this.subjectList[this.subjectIndex].knowledge.name);
					this.name=this.subjectList[this.subjectIndex].knowledge.name;
					if(!this.total && e>this.subjectList.length/2){
					this.more();						
						}
					
					
														
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
	
	
	
	/* #ifdef MP-TOUTIAO */
	@font-face {
		font-family: uniicons;
		font-weight: normal;
		font-style: normal;
		src: url('~@/static/uni.ttf') format('truetype');
	}
	
	/* #endif */
	
	/* #ifndef APP-NVUE */
	page {
		display: flex;
		flex-direction: column;
		box-sizing: border-box;
		background-color: #efeff4;
		min-height: 100%;
		height: auto;
	}
	
	
	
	
	view {
		font-size: 14px;
		line-height: inherit;
	}
	
	.example {
		padding: 0 15px 15px;
	}
	
	.example-info {
		padding: 15px;
		color: #3b4144;
		background: #ffffff;
	}
	
	.example-body {
		flex-direction: row;
		flex-wrap: wrap;
		justify-content: center;
		padding: 0;
		font-size: 14px;
		background-color: #ffffff;
	}
	
	/* #endif */
	.example {
		padding: 0 15px;
	}
	
	.example-info {
		/* #ifndef APP-NVUE */
		display: block;
		/* #endif */
		padding: 15px;
		color: #3b4144;
		background-color: #ffffff;
		font-size: 14px;
		line-height: 20px;
	}
	
	.example-info-text {
		font-size: 14px;
		line-height: 20px;
		color: #3b4144;
	}
	
	
	.example-body {
		flex-direction: column;
		padding: 15px;
		background-color: #ffffff;
	}
	
	.word-btn-white {
		font-size: 18px;
		color: #FFFFFF;
	}
	
	.word-btn {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		align-items: center;
		justify-content: center;
		border-radius: 6px;
		height: 48px;
		margin: 15px;
		background-color: #007AFF;
	}
	
	.word-btn--hover {
		background-color: #4ca2ff;
	}
	
	page {
		background-color: #FFFFFF;
	}
	.example-body {
		flex-direction: row;
		flex-wrap: wrap;
		justify-content: center;
		padding: 0;
		font-size: 14px;
		background-color: #ffffff;
	}

    .holdon{
    	width: 100%;
    	height: 100%;
    }
    .ball{
    	width: 70upx;height: 70upx;
    	/* background:linear-gradient(to bottom, #F8F8FF,#ffffff); */
		background:transparent;
    	border-radius: 50%;
    	
    	color: #fff;
    	font-size: 30upx;
    	display: flex;justify-content: center;align-items: center;
    	position: fixed !important;
    	z-index: 1000000;
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
	
	/* 头条小程序组件内不能引入字体 */
	/* #ifdef MP-TOUTIAO */
	@font-face {
		font-family: uniicons;
		font-weight: normal;
		font-style: normal;
		src: url('~@/static/uni.ttf') format('truetype');
	}
	
	/* #endif */
	

	.example {
		padding: 0 15px;
	}
	
	.example-info {
		/* #ifndef APP-NVUE */
		display: block;
		/* #endif */
		padding: 15px;
		color: #3b4144;
		background-color: #ffffff;
		font-size: 14px;
		line-height: 20px;
	}
	
	.example-info-text {
		font-size: 14px;
		line-height: 20px;
		color: #3b4144;
	}
	
	
	.example-body {
		flex-direction: column;
		padding: 15px;
		background-color: #ffffff;
	}
	
	.word-btn-white {
		font-size: 18px;
		color: #FFFFFF;
	}
	
	.word-btn {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		align-items: center;
		justify-content: center;
		border-radius: 6px;
		height: 48px;
		margin: 15px;
		background-color: #007AFF;
	}
	
	.word-btn--hover {
		background-color: #4ca2ff;
	}
	
	
	
	/* #ifndef APP-NVUE */
	page {
		height: 100vh;
	}
	
	/* #endif */
	
	.uni-fab-box {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		justify-content: center;
		position: absolute;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
	}
	
	.uni-padding-wrap {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}
	
	.word-btn {
		width: 250px;
	}
	
	.cu-bar2{
		display: flex;
		position: relative;
		align-items:flex-start;
		min-height: 100upx;
		justify-content: space-between;
	}
	.cu-bar2 .action{
		align-items:flex-start;
	}
	
	
	
	.song-title{
		margin: 30upx;
	}
	.song-name{
		text-align: center;
		color: #FFFFFF;
		font-size: 35upx;
		margin-top: 30upx;
	
	}
	.song-author{
		text-align: center;
		color: #FFFFFF;
		font-size: 25upx;
	}
	.info{
		background-color: #990000;
		margin: 30upx;
		border-radius:5px
	}
	.iconbtn{
		width: 60upx;
		height: 60upx;	
		margin-left: 30upx;
		margin-right: 30upx;
	
	}
	.imt-audio {
		padding: 30upx;
		background: #fff;
		border-radius: 20upx;
	}
	
	.audio-wrapper {
		display: flex;
		align-items: center;
		margin: 30upx;
	}
	
	.audio-number {
		font-size: 24upx;
		line-height: 1;
		color: #FFFFFF;
	}
	
	.audio-slider {
		flex: 1;
		margin: 0 30upx;
	}
	
	.audio-control-wrapper {
		margin-top: 20upx;
		display: flex;
		justify-content: center;
		align-items: center;
		font-family: "iconfont" !important;
	
	}

	
	
	
</style>
