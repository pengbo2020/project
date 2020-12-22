<template>
	<view>
		<view v-for="(item, index) in list" :key="index">
			<!-- <uni-section :title="item.name" type="line"></uni-section> -->
			<uni-collapse ref="add" class="warp"  @change="change(list[index].id)">
				<uni-collapse-item v-for="(sub, key) in item.data" :key="key" :open="sub.open" :show-animation="sub.showAnimation" :disabled="sub.disabled" :title="sub.subName" >
					<template v-if="!sub.type">
						<text class="content">{{ sub.content }}</text>
					</template>
					<template v-else>
						<uni-list>
							<uni-list-item v-for="(list, listIndex) in sub.subList" :key="listIndex" :title="list.title" :note="list.note" :thumb="list.thumb" :show-extra-icon="list.showExtraIcon" :extra-icon="list.extraIcon" :show-switch="list.showSwitch" @switchChange="change"  @click="question(list.id)" />          
						</uni-list>
					</template>
				</uni-collapse-item>
			</uni-collapse>
		</view>
	
	</view>
</template>

<script>
	import uniCollapse from '@/components/uni-collapse/uni-collapse.vue'
	import uniCollapseItem from '@/components/uni-collapse-item/uni-collapse-item.vue'
	import uniList from '@/components/uni-list/uni-list.vue'
	import uniListItem from '@/components/uni-list-item/uni-list-item.vue'
	import uniSection from '@/components/uni-section/uni-section.vue'
	
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
		/* {
			name: '添加动画效果',
			data: [{
				type: true,
				subName: '折叠列表',
				showAnimation: true,
				subList: [{
						title: '标题文字',
						thumb: 'https://img-cdn-qiniu.dcloud.net.cn/new-page/uni.png'
					},
					{
						title: '标题文字',
						note: '描述信息',
						thumb: 'https://img-cdn-qiniu.dcloud.net.cn/new-page/uni.png'
					},
					{
						title: '标题文字',
						showExtraIcon: true,
						extraIcon: {
							color: '#4cd964',
							size: '26',
							type: 'image'
						},
						showSwitch: true
					}
				]
			}]
		}, */
		
	]

	export default {
		components: {
			uniSection,
			uniCollapse,
			uniCollapseItem,
			uniList,
			uniListItem
		},
		data() {
			
			return {
				list: listData,
				/* accordion: [{
						id: 0,
						title: '标题文字',
						content: '手风琴效果',
						animation: true
					},
					{
						id: 1,
						title: '标题文字',
						content: '手风琴效果',
						animation: true
					},
					{
						id: 2,
						title: '标题文字',
						content: '手风琴效果',
						animation: true
					}
				], */
				extraIcon: {
					color: '#4cd964',
					size: '26',
					type: 'image'
				},
				id: 2
			}
		},
		onLoad(){
			  
			 for(var i=0;i<5;++i)
			 {
				 this.load(i+1);
			 }
				//console.log(data);
		},
		methods: {
			load:async function(pid)
			{
				
				var url="http://192.168.43.202:8081/chapter-question/getChildChapter?pid="+pid;
				
				var[error,res] = await uni.request({
						url:url 
					});
					if(res.data.code==200){
						       /* var data={
						        		name: '添加动画效果',
						        		data: [{
						        			type: true,
						        			subName: subName,
						        			showAnimation: true,
						        			subList: []
						        		}]
						        	}; */
								if(res.data.data!=null){
								var chapters=res.data.data;
								
									//title="list.title" :note="list.note"
								for(var i=0;i<chapters.length;++i)
								{
									var chapter={
										title:chapters[i].name,
										note:"",
										id:chapters[i].id
										
									};
								 listData[pid-1].data[0].subList.push(chapter);
								}
							     
							}else{
								var chapter={
									title:无做过的题,
									note:"",
									id:-1
									
								};
								 listData[pid-1].data[0].subList.push(chapter);
								
							}
							/* console.log(data);
							
							listData.push(data); */
							
							
							
					}
			},
			hello(){
				console.log("hello");
			},
			change(e) {
				console.log(e);
			},
			exam(i){
				console.log(i);
			},
			question(pid){
				//var url="http://192.168.43.202:8081/chapter-question/getChildChapter?pid="+pid;
				uni.navigateTo({
					url:'/pages/user/questionHistory?pid='+pid
				});
				
			}
		}
	}
</script>

<style>
	/* 头条小程序组件内不能引入字体 */
	/* #ifdef MP-TOUTIAO */
	/* @font-face {
		font-family: uniicons;
		font-weight: normal;
		font-style: normal;
		src: url('~@/static/uni.ttf') format('truetype');
	} */

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
		font-size: 28rpx;
		line-height: inherit;
	}

	.example {
		padding: 0 30rpx 30rpx;
	}

	.example-info {
		padding: 30rpx;
		color: #3b4144;
		background: #ffffff;
	}

	.example-body {
		flex-direction: row;
		flex-wrap: wrap;
		justify-content: center;
		padding: 0;
		font-size: 14rpx;
		background-color: #ffffff;
	}

	/* #endif */
	.example {
		padding: 0 30rpx;
	}

	.example-info {
		/* #ifndef APP-NVUE */
		display: block;
		/* #endif */
		padding: 30rpx;
		color: #3b4144;
		background-color: #ffffff;
		font-size: 30rpx;
	}

	.example-info-text {
		font-size: 28rpx;
		line-height: 36rpx;
	}


	.example-body {
		flex-direction: column;
		padding: 30rpx;
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


	.example-body {
		flex-direction: column;
		flex: 1;
	}

	.content {
		padding: 15px;
		font-size: 14px;
		line-height: 20px;
		background-color: #f9f9f9;
		color: #666;
	}

	.button {
		font-size: 26rpx;
		line-height: 90rpx;
	}
</style>