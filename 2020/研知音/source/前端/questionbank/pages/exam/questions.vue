<template>
	<view class="demo-tabs">
		<!-- #ifndef MP -->
		<cl-tabs v-model="tabIndex" v-if="list.length>0" >
			<template >
				<cl-list
					:label="item.title"
					class="block"
					v-for="item in list"
					:key="item"
					@click="question(item.id)"
				>
				
					<cl-icon name="cl-icon-arrow-right"></cl-icon>
				</cl-list>
			</template>
		</cl-tabs>
		<cl-tabs v-model="tabIndex" v-if="list.length==0" >
			<template >
				无数据
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
				/* {
					label: "C"
				},
				{
					label: "D"
				},
				{
					label: "E"
				},
				{
					label: "F"
				},
				{
					label: "G"
				} */
			],
			list: []
		};
	},
	onLoad(){
		//console.log(this.list);
		//this.list=[{id:0,title:'政治'},{id:1,title:'马原'}];
		this.loadData();
		
	},
	methods:{
		loadData:async function(){
			var [error,res] = await uni.request({
				url: "http://192.168.43.202:8081/exam/getQuestions",
				
			});
			if(res.data.code==200)
			{
				if(res.data.data!=null && res.data.data.length>0)
				{
					
					this.list= res.data.data;
				}
			}else{
				
			}
		},
		question(id){
			console.log(id);
			uni.navigateTo({
				url:'/pages/exam/index?eid='+id
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
