<template>
	<view class="player-page" v-if="flag==true">
		<!-- <cu-custom class="nav-bar" :isBack="true" backText="返回" :content="song.name" :require="false">
		 </cu-custom> -->
		<!-- <view class="content" :style="{marginTop:customBar+'px'}"> -->
		
		<view  @tab="hideList" @click="hideList" class="hello">
			<view class="bg-img-box" :style="'background-image:url('+song.picUrl+')'">

			</view>
			<!-- <view class="ear-phone" :style="{top:customBar*2+'rpx'}">
				<image class="img" src="https://s3.music.126.net/mobile-new/img/needle-ip6.png?be4ebbeb6befadfcae75ce174e7db862="></image>
			</view> -->
			<view class="img-box" :class="[isPlay ? '' : 'stoped']" :style="{top:customBar*2+50+'rpx'}">
				
		
				<view class="circle">
					<!-- <image class="img" :src="song.picUrl" mode=""></image> -->
					<view style="color: white;">{{subjectList[curPlayIndex].knowledge.name}}:{{subjectList[curPlayIndex].knowledgeDescList[0].descDetail}}</view>
				</view>
				
				
			</view>
			<view class="lyric-box">
				<view class="ric">{{lytop}}</view>
				<view class="ric cur">{{lycur}}</view>
				<view class="ric">{{lybot}}</view>
			</view>
			<view class="btn-box">
				<view class="flex-item">
					<text class="iconfont" :style="audiolist[curPlayIndex].favor==true?'color: red':''" @click="Favor(curPlayIndex)">&#xe615;</text>
					</view>
				<view class="flex-item iconfont" @click="FeedBack" id="feedback">&#xe628;</view>
				
			</view>
			<view class="slider-bar">
				<view class="time start">{{curPlayTimeNum}}</view>
				<slider class="line" :value="curPlayTime" :min="0" :max="playTime" @change="sliderChange" block-size="15"
				 backgroundColor="rgba(255,255,255,.5)" activeColor="rgba(255,255,255,.5)" />
				<view class="time end">{{playTimeNum}}</view>
			</view>
			<view class="btn-groups flex-box">
				<view class="flex-item" @click="setPlayModel">
					<view v-if="playModel==0" class="iconfont">&#xe66c;</view>
					<view v-if="playModel==1" class="iconfont">&#xe66b;</view>
					<view v-if="playModel==2" class="iconfont">&#xe66d;</view>
				</view>
				<view class="flex-item" @click="prev">
					<view class="iconfont">&#xe78a;</view>
				</view>
				<view class="play-btn flex-item" @click="play">
					<view v-if="!isPlay" class="iconfont">&#xe638;</view>
					<view v-if="isPlay" class="iconfont">&#xe76a;</view>
				</view>
				<view class="flex-item" @click="next(false)">
					<view class="iconfont">&#xe7a5;</view>
				</view>
				<view class="flex-item iconfont" @click="openList" id="list">&#xe634;</view>
			</view>
		</view>
		
		
		<view class="poplist-box" :class="[opentList?'':'hide']">
			<view class="title">
				<text class="total">当前播放({{curPlayIndex+1}})</text>
				<text class="model"  v-if="playModel==0" @click="setPlayModel">
					<text class="iconfont">&#xe66c;</text>
					<text>列表循环</text>
				</text>
				<text class="model"  v-if="playModel==1" @click="setPlayModel">
					<text class="iconfont">&#xe66b;</text>
					<text>随机播放</text>
				</text>
				<text class="model"  v-if="playModel==2" @click="setPlayModel">
					<text class="iconfont">&#xe66d;</text>
					<text>单曲循环</text>
				</text>
			</view>
			<scroll-view scroll-y="true" style="height: 578rpx;">
				<view class="item" :class="[index == curPlayIndex?' current':'']" :style="index != curPlayIndex?((audiolist[index].n2==true)?'color:black':'color:grey'):''" v-for="(val,index) in copyAudioList" :key="index">
					<view class="img num" v-if="val.desc">
						{{index + 1}}
					</view>
					<view class="text ellipsis" @click="initPlay(val.id,index)">
						<text class="name ellipsis">{{val.name}}</text>
						<text class="ar ellipsis">来源:{{val.n1}}</text>
					</view>
					<text class="cuIcon-close"  @click="listCloseOne(index)"></text>
				</view>
			</scroll-view>
		</view>
		
		<view class="poplist-box" :class="[feedback?'':'hide']">
			<view class="title">
			<text class="total">反馈</text>
			<text class="model"  @click="errorSubmit()">
				<!-- <text class="iconfont">&#xe66c;</text> -->
				<text>提交</text>
			</text>
			</view>
		
			<view class="padding-xl" style="background-color: #F0F0F0;">
				<scroll-view scroll-y="true" style="height: 578rpx;">
				<checkbox-group class="block"  @change="CheckboxChange2">
					<view class="cu-form-group" v-for="(item,index) in errorList" :key="index" >
						<checkbox :value="index" :class="item.checked==true?'checked':''"></checkbox>
						<view class="title  text-black">{{item.value}}</view>
					</view>
				</checkbox-group>
				</scroll-view>
				
				<!-- <view class="padding flex flex-direction ">				
					<button class="cu-btn bg-red margin-tb-sm lg" @click="SubmitError">提 交</button>
					<button class="cu-btn  margin-tb-sm lg" @click="errorSubmit()">提 交 </button>
				</view> -->
			</view>
			
		</view>
		
	</view>
</template>
<script>
	import cuCustom from '@/colorui/components/cu-custom.vue'
	import {
		apiSong,
		apiSongDetail,
		apiLyic
	} from '@/apis/player.js'
	import {
		plat
	} from '@/utils/conmmon.js'
	import {
		mapGetters,
		mapMutations
	} from 'vuex'
	import Vue from 'vue'
	let update = true;
	export default {
		components:{
			cuCustom
		},
		data() {
			return {
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
					 value:'语音有错'	 
					},
					{
					 checked:false,
					 value:'音质不好'	 
					}],
				feedback:false,
				customBar:this.CustomBar,
				opentList:false,
				song: {
					id: '',
					url: '',
					name: '',
					singer: '',
					time: 0,
					picUrl: '',
				},
				
				playModel: 0,
				isPlay: true,
				playTime: 0,
				curPlayTime: 0,
				curPlayIndex: 0,
				lyric:[],
				lytop:'',
				lycur:'',
				lybot:'',
				showList: false,
				copyAudioList:[],
				cid:null,
				subjectList:null,
				pn:0,
				total:false,
				flag:false,
				errorInfo:null,
			};
		},
		computed: {
			...mapGetters(['audiolist']),
			
			
			playTimeNum() {
				return this.$util.formatTime(this.playTime)
			},
			curPlayTimeNum() {
				return this.$util.formatTime(this.curPlayTime)
			}
		},
		components:{
		},
		onLoad(param) {
			/* if(!param.id){
					return
			} */
			this.cid = isNaN(parseInt(param.cid))?-1:parseInt(param.cid);
		    this.loadData();
			//const list = JSON.parse(decodeURIComponent(param.list));
			/* let index = param.index
			if(index == 'none'){
				list.forEach((v,i)=>{
					if(v.id == param.id ){
						index = i;
					}
				})
				this.curPlayIndex = index;
			}else{
				this.curPlayIndex = Number(index);
			} */
			
		},
		methods: {
			...mapMutations(['setAudiolist','setPlaydetail','setIsplayingmusic','setIsplayactive']),
			errorSubmit()
			{
				var that=this;
				if(that.errorInfo!=null && that.errorInfo.length!=0)
				{
					that.feedback = false;
					uni.showLoading({
						title:"提交中"
					});
					var data={
						bid:that.subjectList[that.curPlayIndex].knowledge.id,
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
			Favor: function(e) { //收藏题
			   var kid=this.subjectList[e].knowledge.id;
			   var favor=this.subjectList[e].knowledge.md5;
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
			   			that.subjectList[e].knowledge.md5='f';
						that.audiolist[e].favor=false;
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
			   			that.subjectList[e].knowledge.md5='t'
						that.audiolist[e].favor=true;
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
				console.log(this.audiolist);
				if(this.audiolist[i].n2==false){
				var that=this;
				 var kid=that.subjectList[i].knowledge.id;
				uni.request({
					url:this.$server+"/user-knowledge/add?kid="+kid,
					withCredentials:true,
					header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
					success(res){
						that.subjectList[i].knowledge.md5='f';
						that.audiolist[i].n2=true;
						
					},
					fail(res){
						
					}
				})
				}
				
			},
			more:async function()
			{
				if(this.total==false){
					this.pn+=1;
					var l=this.subjectList.length;
					var url=this.$server+"/knowledge-basic/getCollection?pn="+this.pn+"&ps=20&cid="+this.cid;
					var[error,res] = await uni.request({
							url:url,
							withCredentials:true,
							header:{'Cookie':"JSESSIONID="+this.JSESSIONID()}, 
						});
						if(res!=undefined &&res!=null &&res.data!=undefined && res.data.code==200){
									if(res.data.data!=null && res.data.data.length>0){
										
									var list2=res.data.data;
									if(list2.length<20)
									{
										this.total=true;
									}
									for(var i=0;i<list2.length;++i)
									{
										//this.total=true;
										list2[i].userFavor=0;
										list2[i].userAnswer="";
										this.subjectList.push(list2[i]);
	
									}
									var list = this.initList(list2);
									for(var j=0;j<list.length;++j)
									{
										this.audiolist.push(list[j]);
									}
									
								}else{
									this.total=true;
								}
						}
						}
						
			},
			
			loadData:async function(){
				uni.showLoading({
					title:"加载中"
				});
				var url=this.$server+"/knowledge-basic/getCollection?pn="+this.pn+"&ps=20&cid="+this.cid;
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
				const list = this.initList(this.subjectList);
				this.curPlayIndex = 0;
				
				this.setAudiolist(list);
				this.initPlay(0);
				//列表延后渲染
				setTimeout(()=>{
					this.copyAudioList = list;
				},1000)
				
				
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
			
			
			initList(subjectList)
			{
			   var list = [];
			
				 /*  id:64093
				   n1:"陈奕迅"
				   n2:"？"
				   name:"孤独患者"
				   picUrl:"http://p3.music.126.net/uyMO9Fx8a_d4a93xRnuRLA==/109951163871163527.jpg" */
			   for(var i=0;i<subjectList.length;++i)
				{
					var obj={
						  id:i,
						  n1:"研知音",
						  n2:(subjectList[i].knowledge.md5!='n'?true:false),
						  favor:(subjectList[i].knowledge.md5=='t'?true:false),
						  name:subjectList[i].knowledge.name,
						  picUrl:this.$server+"/images/bgImg.jpg"
					};
					list.push(obj);
				}	  
				

				
				return list;  // 返回在用的list
			},
			sliderChange(e) {
				this.curPlayTime = e.detail.value;
				this.$au_player.seek(this.curPlayTime)
			},
			setPlayModel() {
				this.playModel = this.playModel == 2 ? 0 : this.playModel + 1;
				uni.showToast({
					icon: 'none',
					title: ['列表循环', '随机播放', '单曲循环'][this.playModel]
				})
			},
			listCloseOne(index){
				const list  = this.copyAudioList;
				console.log(index)
				list.splice(index,1)
				if(list.length>0){
					if(index == this.curPlayIndex){
						if(index<list.length){
							this.initPlay(list[index].id);
							this.curPlayIndex = index
						}else{
							this.initPlay(list[0].id);
							this.curPlayIndex = 0;
						}
					}else{
						this.curPlayIndex = index>this.curPlayIndex?this.curPlayIndex:this.curPlayIndex-1;
					}
					
					this.copyAudioList = list;
					this.setAudiolist(list)
					this.setIsplayactive(true)
					
				}else{
					this.$au_player.stop();
					uni.navigateBack({
						delta: 1
					});
				}
			},
			hideList(e){
				if(e.target.id != 'list'){
					this.opentList = false;
				}
				
				if(e.target.id != 'feedback'){
					this.feedback = false;
				}
				
			},
			openList() {
				this.opentList = !this.opentList;
				
			},
			FeedBack()
			{
				this.feedback = !this.feedback;
			},
			initPlay(id,index) {
				if(index){
					this.curPlayIndex = index
				}
				
				    this.add(id);
					
					const surl = this.$server+"/knowledgeaudio/"+this.subjectList[id].audoFilePath.path;
					//const surl = "https://www.lztk.xyz/knowledgeaudio/"+this.subjectList[id].audoFilePath.path;
					//const surl = "http://m7.music.126.net/20201106233201/71a8ac388f553e4e5bf1538d6e07c8b9/ymusic/dbc0/6141/a7e2/5d049d85c0784326e0e48726835ec652.mp3";
					const name = this.subjectList[id].knowledge.name;
					const singer = "研知音";
					var picUrl = this.$server+"/images/bgImg.jpg";
					this.lybot = '';
					this.lycur = '';
					this.lytop = '';
					this.song = {
						id,
						url: surl,
						name: name,
						picUrl: picUrl,
						singer,
						time:Math.floor(271000/1000)
					}
					this.setPlaydetail({id,pic:picUrl})
					this.$au_player.url = this.song.url;
					this.$au_player.title = this.song.name;
					this.$au_player.coverImgUrl = this.song.picUrl;
					this.$au_player.singer = this.song.singer;
					//h5
					this.$au_player.autoplay = true;
					//app
					this.$au_player.src = this.song.url;
					var that=this;
					/* this.$au_player.onCanplay(function(){
						that.song.time=Math.floor(that.$au_player.duration);
					}) */
					
					/* this.$au_player.
					this.$au_player.onNext(function(){
						that.next(false);
					});
					this.$au_player.onPrev(function(){
						that.prev();
					}); */
					this.$au_player.onStop(function(){
						that.isPlay=false;
					})
					
					
					Vue.prototype.cusPlay = this.onPlayFn
					Vue.prototype.cusTimeUpdate = this.onTimeUpdateFn
					Vue.prototype.cusEnded = this.onEndedFn
					
					if(this.total==false && id>=this.audiolist.length/2)
					{
						this.more();
					}
					
					
					//console.log('init')
					
					
				/* Promise.all([apiSong({
					id
				}), apiSongDetail({
					ids: id
				})]).then(res => {

					const surl = res[0].data[0].url;
					if(!surl){
	
						uni.showToast({
							icon:'none',
							title:'资源已经失效!请返回'
						})
						this.next()
						return;
					}
					const sdetail = res[1].songs[0];
					const singer = sdetail.ar[0].name;
					this.lybot = '';
					this.lycur = '';
					this.lytop = '';
					this.song = {
						id,
						url: surl,
						name: sdetail.name,
						picUrl: sdetail.al.picUrl,
						singer,
						time: Math.floor(sdetail.dt / 1000) 
					}
					this.setPlaydetail({id,pic:sdetail.al.picUrl})
					this.$au_player.url = this.song.url;
					this.$au_player.title = this.song.name;
					this.$au_player.coverImgUrl = this.song.picUrl;
					this.$au_player.singer = this.song.singer;
					//h5
					this.$au_player.autoplay = true;
					//app
					this.$au_player.src = this.song.url;

					
					console.log('init')
					
				}).catch(e => {
					console.info(e)
					this.setIsplayactive(false)
				})
				
				apiLyic({
					id
				}).then(res => {
					if(res.uncollected){
							console.log('暂未收录歌词');
							this.lycur='~暂未收录歌词~'
					}
					const lines = res.lrc.lyric.split('\n');
					const target = []
					for (let k in lines) {
						const timeMatch = lines[k].match(/\[(\d+:\d+\.\d+)\]/);
						let time = null;
						if(timeMatch){
							const ts = timeMatch[1].split(':');
							time = Number(ts[0])*60+Number(ts[1])
						}
						target.push({
							time,
							text: lines[k].replace(/^.+?\]/, '')
						})
					}            
					this.lyric = target;
				}).catch(e => {
					this.$au_player.play();
					console.log('歌词加载失败', e)
					this.lycur='~歌词加载失败~'
				}) */
			},
			onPlayFn() {
				this.song.time=Math.floor(this.$au_player.duration);
				this.playTime = this.song.time;
				this.isPlay = true
				this.setIsplayingmusic(true)
				this.setIsplayactive(true)
				console.log('onplaying')
			},
			onTimeUpdateFn() { 
				const curtime = this.$au_player.currentTime
				this.curPlayTime = Math.floor(curtime);
				
				//const lyric = this.lyric;
				// console.log(curtime,Math.floor(curtime))
				/* if(update && lyric.length>0){
					
					
					for (let i = 0;i<lyric.length-1;i++) {
						if(lyric[i] !==null && curtime - lyric[i].time< 0.15){
							if(i>2) this.lytop = lyric[i-2].text;
							if(i>1) this.lycur = lyric[i-1].text?lyric[i-1].text:'~~~~~~~~'
							if(i<lyric.length-1) this.lybot = lyric[i].text;
							// update = true;
							break;
						}
					}
				} */
				
			},
			onEndedFn() {
				
				this.isPlay = false;
				this.setIsplayingmusic(false)
				this.setIsplayactive(false)
				this.next(true);
			},
			getIndex(type, isAuto) {
				//['列表循环', '随机播放', '单曲循环']
				let next = 0;
				let prev = 0;
				const cur = this.curPlayIndex;
				const last = this.audiolist.length - 1;
				if (this.playModel === 0 || this.playModel === 2) {
					next = cur == last ? 0 : cur + 1;
					prev = cur == 0 ? last : cur - 1;
				}
				if (this.playModel === 1) {
					next = Math.floor(Math.random() * (last + 1))
					prev = Math.floor(Math.random() * (last + 1))
				}
				if (isAuto && this.playModel === 2) {
					next = cur
				}
				return type == 'next' ? next : prev
			},
			play() {
				if (this.isPlay) {
					this.$au_player.pause();
				} else {
					this.$au_player.play();
				}
				this.isPlay = !this.isPlay;
				this.setIsplayingmusic(this.isPlay )
			},
			next(isAuto) {
				const index = this.getIndex('next', isAuto)
				
				//console.log(this.audioList[index].id);
				this.initPlay(this.audiolist[index].id)
				this.curPlayIndex = index;
			},
			prev() {
				const index = this.getIndex('prev')
				this.initPlay(this.audiolist[index].id)
				this.curPlayIndex = index;
			}
		},
		destroyed() {
			//console.log('destroyed')
		}
	}
</script>
<style lang="scss">
	@import "@/common/css/common.scss";
	@import "@/common/css/iconfont.css";
	
	uni-page-body {
		height: 100%;
	}

	.player-page {
		height: 100%;
		// display: flex;
		// flex-direction: column;
	}

	.cu-bar {
		// border-bottom: 2rpx solid rgba(255, 255, 255, 0.7);
		width: 100%;
	}

	.nav-bar {
		color: #F5F5F5;
		position: fixed;
		z-index: 2;
	}

	.hello {
		// flex: 1;
		height: 100%;
		/* position: relative; */
		overflow: hidden;
		/* width: 100%; */
		/* overflow: hidden; */

		.bg-img-box {
			width: 100%;
			height: 100%;
			filter: blur(25px);
			background-position: center center;
			background-repeat: no-repeat;
			background-size: cover;
			position: absolute;
			transform: scale(1.5);
			overflow: hidden;
		}

		.ear-phone {
			position: absolute;
			top: 0;
			left: 330rpx;
			width: 220rpx;
			height: 330rpx;
			z-index: 1;

			.img {
				height: 100%;
			}

			&.stop {
				transform: rotate(-25deg);
				transform-origin: 1% 0 0;
			}
		}

		.img-box {
			&.stoped {
				animation-play-state: paused;
			}

			animation: rotate 12s linear .1s infinite;
			position: absolute;
			top: 150rpx;
			left: 100rpx;
			width: 550rpx;
			height: 550rpx;
			border-radius: 50%;
			/* background-color: rgba(255, 255, 255, 0.1); */
			display: flex;
			align-items: center;
			justify-content: center;

			.circle {
				width: 92%;
				height: 92%;
				border-radius: 50%;
				/* background-color: rgba(255, 255, 255, 0.3); */
				display: flex;
				align-items: center;
				justify-content: center;

				.img {
					width: 70%;
					height: 70%;
					border-radius: 50%;
				}
			}
		}

		/* .lyric-box {
			position: absolute;
			bottom: 390rpx;
			width: 100%;
			-webkit-mask-image: linear-gradient(to bottom,
				rgba(255, 255, 255, 0) 0,
				rgba(255, 255, 255, .6) 15%,
				rgba(255, 255, 255, 1) 25%,
				rgba(255, 255, 255, 1) 75%,
				rgba(255, 255, 255, .6) 85%,
				rgba(255, 255, 255, 0) 100%);
			height: 140rpx;

			.ric {
				text-align: center;
				color: #F1F1F1;
				font-size: 30rpx;
				opacity: 0.8;
				height: 46rpx;
				    white-space: nowrap;
				    overflow: hidden;
				&.cur {
					font-size: 32rpx;
					opacity: 1;
					color: #8dc63f;
				}
			}
		} */

		.btn-box,
		.btn-groups,
		.slider-bar {
			display: flex;
			position: absolute;
			width: 100%;
			color: #F1F1F1;
		}

		.btn-box {
			bottom: 250rpx;

			.flex-item {
				flex: 1;
				text-align: center;
				font-size: 54rpx;

				.iconfont {
					font-size: 54rpx;
				}
			}

		}

		.slider-bar {
			bottom: 160rpx;
			align-items: center;

			.line {
				flex: 1;
			}

			.time {
				height: 28rpx;
				line-height: 28rpx;
				font-size: 24rpx;
				min-width: 66rpx;
			}

			.line {
				margin: 20rpx 20rpx;
			}

			.start {
				margin-left: 30rpx;
			}

			.end {
				margin-right: 30rpx;
			}
		}

		.btn-groups {
			bottom: 80rpx;
			font-size: 44rpx;

			.flex-item {
				text-align: center;
			}

			.iconfont {
				font-size: 44rpx;
			}

			.play-btn {
				position: relative;

				&::before {
					content: '';
					display: block;
					position: absolute;
					top: 50%;
					left: 50%;
					border: 2rpx solid #F1F1F1;
					width: 100rpx;
					height: 100rpx;
					border-radius: 50%;
					transform: translateX(-50%) translateY(-50%);
				}
			}
		}

	}
		.poplist-box{
			position: fixed;
			display: block;
			bottom: 0;
			left: 3%;
			height: 700rpx;
			width: 94%;
			background-color: #F0F0F0;
			z-index: 1001;
			border-radius: 5% 5% 0 0;
			&.hide{
				bottom:-700rpx;
			}
			transition: all .15s linear;
			padding: 0 0 0 20rpx;
			.title{
				display: flex;
				justify-content: space-between;
				width: 100%;
				height: 120rpx;
				line-height: 120rpx;
				font-size: 34rpx;
				.total{
					font-size: 40rpx;
				}
				.model{
					margin-right: 20rpx;
				}
			}
			.item{
				
				display: flex;
				align-items: center;
				margin-bottom: 15rpx;
				.img{
					height: 100rpx;
					width: 100rpx;
					border-radius: 18rpx;
				}
				.num{
					height: 100rpx;
					width: 50rpx;
					line-height: 100rpx;
					text-align: center;
					border-radius: 18rpx;
					font-size: 38rpx;
					color: #9E9E9E;
				}
				&.current{
					color: #e54d42;
					.num{
						color: #e54d42;
					}
				}
				.text{
					flex: 1;
					margin-left: 20rpx;
					;
					text{
						display: block;
					}
					.name{
						font-size: 32rpx;
						overflow: hidden;
					}
					.ar{
						font-size: 24rpx;
						overflow: hidden;
						.point{
							font-size: 40rpx;
						}
					}
				}
				.cuIcon-close{
					font-size: 38rpx;
					color: #9E9E9E;
					width: 68rpx;
					height: 80rpx;
				}
			}
		}
/* 	@keyframes rotate {
		0% {
			transform: rotate(0deg);
		}

		100% {
			transform: rotate(360deg);
		}

	} */
	/* .cu-form-group {
		justify-content: flex-start
	}
	
	.cu-form-group .title {
		padding-left: 30upx;
		padding-right: 0upx;
	}
	
	.cu-form-group+.cu-form-group {
		border-top: none;
	} */
</style>





