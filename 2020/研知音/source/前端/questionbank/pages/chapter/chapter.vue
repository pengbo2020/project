<template>
	<!-- <view class="content">
		<audioQuickPlay url="https://www.lztk.xyz/knowledgeaudio/2.mp3"></audioQuickPlay>
	</view> -->
</template>

<script>
	/* import audioQuickPlay from '@/components/wangding-audioQuickPlay/index.vue' */
	export default {
		/* components: { audioQuickPlay }, */
		data() {
			return {
				title: 'Hello'
			}
		},
		onLoad() {
           const backgroundAudioManager = wx.getBackgroundAudioManager()
           
           backgroundAudioManager.title = '此时此刻'
           backgroundAudioManager.epname = '此时此刻'
           backgroundAudioManager.singer = '许巍'
           backgroundAudioManager.coverImgUrl = 'http://y.gtimg.cn/music/photo_new/T002R300x300M000003rsKF44GyaSk.jpg?max_age=2592000'
           // 设置了 src 之后会自动播放
		   backgroundAudioManager.playbackRate=2.0;
           backgroundAudioManager.src = 'https://www.lztk.xyz/knowledgeaudio/2.mp3'
		   backgroundAudioManager.onEnded(()=>{
			
			 console.log("开始播放下一首了");
			 
		   });
		   backgroundAudioManager.onStop(()=>{
			   console.log("停止播放了");
		   })
		   backgroundAudioManager.onNext(()=>{
			   backgroundAudioManager.title = 'zcj'
			   backgroundAudioManager.epname = 'zcj'
			   backgroundAudioManager.singer = '许巍'
			   backgroundAudioManager.coverImgUrl = 'http://y.gtimg.cn/music/photo_new/T002R300x300M000003rsKF44GyaSk.jpg?max_age=2592000'
			   // 设置了 src 之后会自动播放
			   backgroundAudioManager.playbackRate=0.5;
			   backgroundAudioManager.src = 'https://www.lztk.xyz/knowledgeaudio/3.mp3';
		   })
		},
		methods: {

		}
	}
</script>

<style>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>
