<template>
	<view class="demo-tabs">
		<!-- #ifndef MP -->
		<cl-tabs v-model="tabIndex" type="swiper" lazy :labels="labels">
			<template v-slot="{ index }">
				<!-- <cl-list
					:label="item.title"
					class="block"
					v-for="item in list[index]"
					:key="item"
				>
				
					<cl-icon name="cl-icon-arrow-right"></cl-icon>
				</cl-list> -->
				<view v-if="index==0">
				<view v-for="(item, i) in list[index].data" :key="i">
					<!-- <uni-section :title="item.name" type="line"></uni-section> -->
					<uni-collapse ref="add" class="block"  @change="change(list[index].data[i].id)">
						<uni-collapse-item v-for="(sub, key) in item.data" :key="key" :open="sub.open" :show-animation="sub.showAnimation" :disabled="sub.disabled" :title="sub.subName" >
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
				</view>
				<view v-if="index==1">
					hello
				</view>
			</template>
		</cl-tabs>
		<!-- #endif -->

		<!-- #ifdef MP -->
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
		<!-- #endif -->
	</view>
</template>

<script>
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
		
	]	
export default {
	data() {
		return {
			tabIndex: 0,
			labels: [
				{
					label: "练习"
				},
				{
					label: "试卷"
				}
				
			],
			list: [{data:[]},{data:[]}]
		};
	},
	onLoad(){
		for(var i=0;i<5;++i)
		{
		 this.load(i+1);
		}
		this.loadExam();
		this.list[0].data=listData;
		/* console.log(this.list);
		this.list[0]=[{id:0,title:'政治'},{id:1,title:'马原'}]; */
		//this.list[1]=[{id:0,title:'政治'},{id:1,title:'马原'}];
	},
	methods:{
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
					
				}
		},
		
		loadExam:async function()
		{
			var url="http://192.168.43.202:8081/exam/history";
			
			var[error,res] = await uni.request({
					url:url 
				});
				if(res.data.code==200){
					       
							if(res.data.data!=null){
							var exams=res.data.data;
							
								//title="list.title" :note="list.note"
							for(var i=0;i<exams.length;++i)
							{
								var data={
									    id:0,
										name: '添加动画效果',
										data: [{
											type: true,
											subName: "",
											note:"",
											showAnimation: true,
											subList: []
										}]
									}; 
								data.id=exams[i].id,
								
								data.data[0].subName=exams[i].name;
								data.data[0].note=exams[i].createAt;
								console.log(data);
								this.list[1].data.push(data);
								
							 
							}
						     
						}else{
							var data={
								    id:0,
									name: '添加动画效果',
									data: [{
										type: true,
										subName: "无数据",
										showAnimation: true,
										subList: []
									}]
								}; 
							data.id=exams[i].id,
							data.data[0].subName=exams[i].name;
							console.log(data);
							this.list[1].data.push(data);
							
							
						}
						/* console.log(data);
						
						listData.push(data); */
						
						
						
				}
		},
		change(e) {
			if(e>0)
			{
				uni.navigateTo({
					url:'/pages/exam/answers?eid='+e
				});
			}
		},
		exam(i){
			if(i>0)
			{
				uni.navigateTo({
					url:'/pages/exam/answers?eid='+i
				});
			}
			
		},
		question(i){
			//var url="http://192.168.43.202:8081/chapter-question/getChildChapter?pid="+pid;
			 uni.request({
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
</style>
