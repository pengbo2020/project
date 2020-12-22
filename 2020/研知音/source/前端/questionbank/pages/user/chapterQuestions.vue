<template>
	
	<view class="wrap" v-if="flag==true">
		<!-- <page-nav :desc="desc" title="nav.components"></page-nav> -->
		<view class="nav-wrap">
			<view class="nav-title">
		
				<view class="nav-info">
					<view class="nav-title__text">
						{{title}}
					</view>
					<view class="nav-slogan">
						{{subTitle}}
					</view>
				</view>
			</view>
			<view class="nav-desc">
				{{extraDesc}}
			</view>
			
		</view>
		<!-- #ifdef MP-WEIXIN || MP-QQ -->
		<ad unit-id="adunit-9fe2657b44e8c0e2"></ad>
		<!-- #endif -->
	
		<view class="list-wrap">
			<u-cell-group title-bg-color="rgb(243, 244, 246)" :title="getGroupTitle(item)" v-for="(item, index) in list" :key="index">
				<u-cell-item :titleStyle="{fontWeight: 500}" :is-link="false"  :title="getFieldTitle(item1)" :arrow="false" :value="item1.num+'道'"
				 v-for="(item1, index1) in item.chapter" :key="index1" @click="jump(item1)">
					<image slot="icon" class="u-cell-icon" :src="getIcon(item1.icon)" mode="widthFix"></image>
				</u-cell-item>
			</u-cell-group>
		</view>
		<!-- <u-gap height="70"></u-gap> -->
		<!-- <u-tabbar :list="vuex_tabbar" :mid-button="true"></u-tabbar> -->
		<!-- <u-tabbar
		    v-model="current"
		    :show="show"
		    :bg-color="bgColor"
		    :border-top="borderTop"
		    :list="list2"
			
			:inactive-color="inactiveColor"
			:activeColor="activeColor"
			@change="hello"
		></u-tabbar> -->
	</view>
</template>

<script>
	import iconList from "common/components.config.js";
	export default {
		data() {
			return {
				flag:false,
				list: [],
				type:null,
				iconlist:[],
				hasData:0,
				title:"",
				subTitle:"",
				extraDesc:""
				//desc: '众多组件覆盖开发过程的各个需求，组件功能丰富，多端兼容。让你快速集成，开箱即用。',
				
				
				
			}
		},
		computed: {
			getIcon() {
				return path => {
					return 'https://cdn.uviewui.com/uview/example/' + path + '.png';
				}
			},
			/* desc() {
				return this.$t('components.desc');
			} */
		},
		onShow() {
			/* uni.setNavigationBarTitle({
				title: this.$t('nav.components')
			}); */
		},
		onLoad(option){
			 /* var num =  Math.floor(Math.random() * (50 - 0 + 1)) + 0 ;
			 console.log(num);
			 var arr=[1,2,3,4,5,6];
			 arr.splice(1,1);
			 console.log(arr); */
			 this.type=parseInt(option.type);
			 if(this.type==1)
			 {
				 this.title="我的错题";
				 this.subTitle="经常复习错题，不断进步";
			 }else if(this.type==0)
			 {
				 this.title="我的收藏";
				 this.subTitle="学而时习之";
			 }else{
				 this.title="我的笔记";
				 this.subTitle="好记性不如烂笔头";
				 
			 }
			 this.initIcon();
			 this.loadData();
			
			
		},
		created() {
			
		},
		methods: {
			initIcon()
			{
				for(var i=0;i<iconList.length;++i)
				{
					for(var j=0;j<iconList[i].list.length;++j)
					{
						this.iconlist.push(iconList[i].list[j].icon);
					}
				}
				
			
				
			},
			
			
			loadData:async function()
			{
				uni.showLoading({
					title:"加载中"
				});
				var that=this;
				for(var pid=1;pid<6;++pid)
				{
				 var obj = await that.request(pid);	
				// console.log(obj);
				 /* if(obj.subName!=undefined)
				 {
					 that.list.push(obj);
					 uni.hideLoading();
					 that.flag=true;
				 } */
				  
				}  
				if(that.flag==false)
				{
					if(that.list.length>0)
					{
						uni.hideLoading();
						that.flag=true;
					}else{
						uni.hideLoading();
						uni.showToast({
							icon:"none",
							title:"暂无数据"
						});
					}
				}
				/* console.log(22222);
				console.log(this.list.length); */
							
			},
			request(pid){
				var that=this;
	
			   return new Promise(resolve=>{
				   var url=that.$server+"/chapter/getChapterByType2?pid="+pid+"&type="+that.type;
				   
				   uni.request({
				   						url:url ,
				   						withCredentials:true,
				   						header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
				   						success(res){
											//resolve(res.data);
				   							if(res!=undefined && res.data.code==200){
				   										if(res.data.data!=null && res.data.data.length>0){
				   											
				   										var chapters=res.data.data;
				   										
				   											//title="list.title" :note="list.note"
				   										var subName1="马克思主义基本原理概论";
				   										var subName2="毛泽东思想和中国特色社会主义理论体系概论";
				   										var subName3="中国近代史纲要";
				   										var subName4="思想道德修养与法律基础";
				   										var subName5="形势与政策以及当代世界经济与政治";
				   										var subName=null;
				   										
				   										switch(pid-1)
				   										{
				   											case 0:subName=subName1;break;
				   											case 1:subName=subName2;break;
				   											case 2:subName=subName3;break;
				   											case 3:subName=subName4;break;
				   											case 4:subName=subName5;break; 
				   										}
				   										
				   										var obj={
				   											subName:subName,
				   											chapter:[]
				   										};
				   										
				   											
				   										for(var i=0;i<chapters.length;++i)
				   										{
				   											var chapter={
				   											    icon:null,
				   												title:chapters[i].name,
				   												num:chapters[i].uqnum,
				   												id:chapters[i].id
				   											
				   											};
				   											var l=that.iconlist.length-1;
				   											var index = Math.floor(Math.random() * (l - 0 + 1)) + 0 ;
				   											//console.log(that.iconlist[index]);
				   											chapter.icon=that.iconlist[index];
				   											that.iconlist.splice(index,1);
				   											obj.chapter.push(chapter);	
															
				   										
				   										}
														that.list.push(obj);
				   										resolve(obj);
				   										//console.log(listData[pid-1].data[0].subList);
				   										/* uni.hideLoading();
				   										that.flag=true; */
				   										/* if(this.list.length>0)
				   										{
				   											uni.hideLoading();
				   											this.flag=true;
				   										}else{
				   											uni.hideLoading();
				   											uni.showToast({
				   												icon:"none",
				   												title:"暂无数据"
				   											});
				   										} */
				   										
				   									     
				   									}
				   								
				   							}else{
												resolve(res.data);
											}
				   						},
				   						fail(res){resolve(res)},
										complete(res){
											if(pid==5)
											{
											   if(that.list!=null&& that.list.length>0)
											   {
											   	
										
											   	uni.hideLoading();
											   	that.flag=true;
											   }else{
											   	uni.hideLoading();
											   	uni.showToast({
											   		icon:"none",
											   		title:"暂无数据"
											   	});
											   }
											   
											}
											resolve(res);
											
											
											   
												
												
											}
											
										
				   					});
			   }).catch((e)=>{});	
				
					
			},
			
			openPage(path) {
				this.$u.route({ 
					url: path
				})
			},
			getGroupTitle(item) {
				return  item.subName 
			},
			getFieldTitle(item) {
				return item.title 
			},
			jump(item)
			{
			   uni.showActionSheet({
			   	title:'',
			   	itemList: ['继续练习', '重新开始'],
			   	success: (e) => {
			   		if(e.tapIndex==0)
			   		{
			   			uni.navigateTo({
			   				url:"/pages/user/questions?type="+this.type+"&cid="+item.id+"&continue=1"
			   			})
			   		}else{
			   			uni.navigateTo({
			   				url:"/pages/user/questions?type="+this.type+"&cid="+item.id+"&continue=0"
			   			})
			   		}
			   		
			   	/* 	uni.showToast({
			   			title:"点击了第" + e.tapIndex + "个选项",
			   			icon:"none"
			   		}) */
			   	}
			   })		
				
				
				/* uni.navigateTo({
					url:"/pages/user/questions?type="+this.type+"&cid="+item.id
				})
				console.log(item); */
			}
		}
	}
</script>

<style>
	/* page {
		background-color: rgb(240, 242, 244);
	} */
</style>

<style lang="scss" scoped>
	
	.u-cell-icon {
		width: 36rpx;
		height: 36rpx;
		margin-right: 8rpx;
	}
	.nav-wrap {
		padding: 15px;
		position: relative;
	}
	
	.lang {
		position: absolute;
		top: 15px;
		right: 15px;
	}
	
	.nav-title {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		align-items: center;
	}
	
	.nav-info {
		margin-left: 15px;
	}
	
	.nav-title__text {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		color: $u-main-color;
		font-size: 25px;
		font-weight: bold;
	}
	
	.logo {
		width: 70px;
		/* #ifndef APP-NVUE */
		height: auto;
		/* #endif */
	}
	
	.nav-slogan {
		color: $u-tips-color;
		font-size: 14px;
	}
	
	.nav-desc {
		margin-top: 10px;
		font-size: 14px;
		color: $u-content-color;
	}
</style>
