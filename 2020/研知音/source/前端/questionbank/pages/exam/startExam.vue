<template>
	
	<view class="wrap" v-if="flag==true">
		<!-- <page-nav :desc="desc" title="nav.components"></page-nav> -->
		<view class="nav-wrap">
			<view class="nav-title">
				<!-- <image class="logo" src="https://cdn.uviewui.com/uview/common/logo.png" mode="widthFix"></image> -->
				<view class="nav-info">
					<view class="nav-title__text">
						考试
					</view>
					<view class="nav-slogan">
						看一下自己掌握知识的程度吧
					</view>
				</view>
			</view>
			<view class="nav-desc">
				
			</view>
			
		</view>
		
		<!-- #ifdef MP-WEIXIN || MP-QQ -->
		<ad unit-id="adunit-9fe2657b44e8c0e2"></ad>
		<!-- #endif -->
	
		<view class="list-wrap">
			<u-cell-group title-bg-color="rgb(243, 244, 246)" :title="getGroupTitle(item)" v-for="(item, index) in list" :key="index">
				<u-cell-item :titleStyle="{fontWeight: 500}" :is-link="false"  :title="getFieldTitle(item1)" :arrow="false" :value="item1.num+'道'"
				 v-for="(item1, index1) in item.chapter" :key="index1">
					<image slot="icon" class="u-cell-icon" :src="getIcon(item1.icon)" mode="widthFix"></image>
				</u-cell-item>
			</u-cell-group>
		</view>
		<!-- <u-gap height="70"></u-gap> -->
		<!-- <u-tabbar :list="vuex_tabbar" :mid-button="true"></u-tabbar> -->
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
</template>

<script>
	import iconList from "common/components.config.js";
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
				list2: [/* {
						iconPath: "home",
						selectedIconPath: "home-fill",
						text: '下载试卷',
						
						isDot: false,
						customIcon: false,
					}, */
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
					/* {
						iconPath: "play-right",
						selectedIconPath: "play-right-fill",
						text: '直播',
						customIcon: false,
					}, */
					/* {
						iconPath: "account",
						selectedIconPath: "account-fill",
						text: '开始考试',
						
						isDot: false,
						customIcon: false,
					}, */
				],
				
				flag:false,
				list: [],
				type:null,
				iconlist:[],
				hasData:0,
				title:"",
				subTitle:"",
				extraDesc:"",
				eid:null,
				num:0,
				exam:null,
				downloaded:false
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
			/* this.type=parseInt(option.type);
			 if(this.type==0)
			 {
				 this.title="我的错题";
				 this.subTitle="经常复习错题，不断进步";
			 }else if(this.type==1)
			 {
				 this.title="我的收藏";
				 this.subTitle="学而时习之";
			 }else{
				 this.title="我的笔记";
				 this.subTitle="好记性不如烂笔头";
				 
			 } */
			 this.eid=parseInt(option.eid);
			 this.initData();
			 this.initIcon();
			 this.loadData();
			
			
		},
		created() {
			
		},
		methods: {
			download(exam) {
				var that = this;
				if (that.downloaded == false) {
					var eid = exam.id;
					uni.showLoading({
						title: '正在下载'
					});
					uni.request({
						url: that.$server + '/exam/download?eid=' + eid,
						method: 'GET',
						dataType: 'json',
						success(res) {
							if (res.data.code == 200) {
								var url = that.$server + '/paper/' + res.data.data.path + '.pdf';
								
								//url="http://cn.createpdfonline.org/pdffiles/hello%20word(20200623144016).pdf";
								/* var filename = url.split('/');
								filename=filename[filename.length-1]; */
			
								var temp = url.split('/');
								var fileName = temp[temp.length - 1];
								wx.downloadFile({
									url: url,
									success: function(res) {
										const manage = wx.getFileSystemManager();
										if (res.statusCode === 200) {
											var tempFilePath = res.tempFilePath;
											var dir = new Date().getTime();
											manage.mkdir({
												dirPath: wx.env.USER_DATA_PATH + '/' + dir,
												recursive: false,
												success(res) {
													manage.saveFile({
														tempFilePath: tempFilePath,
														filePath: wx.env.USER_DATA_PATH + '/' + dir + '/' + fileName,
														success: function(res) {
															that.downloaded = true;
															uni.hideLoading();
															uni.showToast({
																icon: 'none',
																title: '下载成功'
															});
														},
														fail(res) {
															uni.hideLoading();
															uni.showToast({
																icon: 'none',
																title: '下载失败,请重试'
															});
														}
													});
												},
												fail(res) {
													uni.hideLoading();
													uni.showToast({
														icon: 'none',
														title: '下载失败,请重试'
													});
												}
											});
										} else {
											uni.hideLoading();
											uni.showToast({
												icon: 'none',
												title: '下载失败,请重试'
											});
										}
									}
								});
			
							} else {
								uni.hideLoading();
								uni.showToast({
									icon: 'none',
									title: '下载失败'
								});
							}
						},
						fail(res) {
							uni.hideLoading();
							
							uni.showToast({
								icon: 'none',
								title: '网络连接失败'
							});
						},
						complete() {}
					});
				} else {
					uni.showToast({
						icon: 'none',
						title: '下载成功'
					});
				}
			},
			
			change(e)
			{
				if(e==0)
				{
					uni.navigateTo({
						url:'/pages/exam/start?eid='+this.exam.id
					});
				}else{
					this.download(this.exam);
					
				}	
			},
			initData()
			{
				var that=this;
				var url=that.$server+"/exam/getExam?eid="+that.eid;
				                     uni.request({
										url:url ,
										withCredentials:true,
										header:{'Cookie':"JSESSIONID="+this.JSESSIONID()},
										success(res){
											if(res.data.code==200)
											{
												that.exam=res.data.data;
												if(that.exam.type<2)
												{
													that.list2=[
														{
															iconPath: "list",
															selectedIconPath: "list",
															text: '开始考试',
															
															isDot: false,
															customIcon: false,
														},
														{
																iconPath: "download",
																selectedIconPath: "download",
																text: '下载试卷',
																
																isDot: false,
																customIcon: false,
															}
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
															/* {
																iconPath: "play-right",
																selectedIconPath: "play-right-fill",
																text: '直播',
																customIcon: false,
															}, */
															
													];
												}else{
													
													that.list2=[{
																iconPath: "list",
																selectedIconPath: "list",
																text: '开始考试',
																
																isDot: false,
																customIcon: false,
															}];
												}
												
											}
										},
										fail(res){} 
			   	
			});
			},
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
				
				/* if(obj.subName!=undefined)
				 {
					 that.list.push(obj);
					 uni.hideLoading();
					 that.flag=true;
				 } */
				  
				}  
				/* if(that.flag==false)
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
				} */
				/* console.log(22222);
				console.log(this.list.length); */
							
			},
			request(pid){
				var that=this;
				
			 return new Promise(resolve=>{
				   var url=that.$server+"/exam/getChapters?pid="+pid+"&eid="+that.eid;
				   
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
															that.num+=chapters[i].uqnum;
				   										
				   										}
														that.list.push(obj);
														
				   										//resolve(obj);
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
				   								
				   							}
											//console.log(pid);
											if(pid==5)
											{
											   var num1= that.exam.type<2?38:15;
											   if(that.num<num1){
											   var obj={
											   	subName:"其他",
											   	chapter:[]
											   };
											   var chapter={
											       icon:null,
											   	title:"其他",
											   	num:num1-that.num,
											   	id:0
											   
											   };
											   var l=that.iconlist.length-1;
											   var index = Math.floor(Math.random() * (l - 0 + 1)) + 0 ;
											   //console.log(that.iconlist[index]);
											   chapter.icon=that.iconlist[index];
											   that.iconlist.splice(index,1);
											   obj.chapter.push(chapter);
											   that.list.push(obj);
											   //resolve(obj);
											   
											   
											   
											}
											
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
											resolve(res.data);
											
				   						},
				   						fail(res){
											
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
											resolve(res.data);
										}
				   					});
			   }).catch((e)=>{console.log(e);});	
				
					
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
				uni.navigateTo({
					url:"/pages/user/questions?type="+this.type+"&cid="+item.id
				})
				console.log(item);
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



