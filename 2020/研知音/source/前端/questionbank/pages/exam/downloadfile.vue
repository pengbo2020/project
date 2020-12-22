<template></template>
<view>
	<view class="padding flex">
		<view @tap="deleteFile(index)" style="width:60px;margin-right:20px;text-align: center;" v-for="(item,index) in dirs"
		 :key="index">
			<!-- <image style="width: 60px;height: 60px;object-fit: cover;margin-bottom: 2px;border-radius: 50%;" mode="aspectFill" :src="item.icon"></image> -->
			<view class="text-sm">{{item.file.name}}</view>
		</view>
	</view>
</view>
<script>
	var _this;
	
export default {
	data() {
		return {
			dirs: []
		};
	},
	onLoad() {
		_this = this;
		this.filelist();
		
	},
	methods: {
		filelist() {
			plus.io.requestFileSystem(plus.io.PRIVATE_DOC, function(fs) {
				// fs.root是根目录操作对象DirectoryEntry
				// 创建读取目录信息对象
				//http://www.html5plus.org/doc/zh_cn/io.html#plus.io.DirectoryEntry
				var directoryReader = fs.root.createReader();
				//console.log(directoryReader);
				directoryReader.readEntries(
					function(entries) {
						   //console.log(entries.length);
						   var i;
						   _this.dirs=[];
						for (i = 0; i < entries.length; i++) {
							_this.dirs.push({
								entry:null,
								file:null
							});
							_this.dirs[i].entry = entries[i];
						    //console.log(obj.entry);
							var dr = entries[i].createReader();
							dr.readEntries(function(fs){
							   console.log(fs[0].name);
							   
							   //hello(fs);
								//obj.file = fs[0];
								//console.log(obj.entry);
							    //console.log(obj.file.name);
								//console.log(_this.dirs);
								//_this.dirs[i].file = obj;
								//console.log(_this.dirs[i].file.name);
								//console.log(obj.entry);
							    //_this.dirs[i].file = fs[0];
								//console.log(fs[0].name);
								 //console.log(_this.dirs[i].file.name);
								 //console.log(i);
							 	 
							},function(err){
								console.log(err.message);
						
							});
							//console.log(_this.dirs[i].file);
							//console.log(_this.dirs[i].);
					        //console.log(obj.file);                  
							//_this.dirs.push(obj);
							/* this.dirs[i] = new Object();
							this.dirs[i].entry = entries[i];
							console.log(entries[i].name);
							console.log(entires[i].name);
							console.log(this.dirs[i].entry.name);
							var dr = entires[i].createReader();
							dr.readEntries(
								function(succesCB) {
									this.dirs[i].file = succesCB[0];
									console.log("文件名称");
									console.log(this.dirs[i].file.name);
								},
								function(errorCB) {
									console.log(errorCB.message);
								}
							); */
							
						}
					},
					function(e) {
						console.log('Read entries failed: ' + e.message);
					}
				);
			});
		},
		hello(obj)
		{
			console.log(obj);
		},
		deleteFile(i) {
			console.log(i);
			console.log(this.dirs[i]);
			this.dirs[i].entry.remove(
				function(res) {
					console.log(res);
				},
				function(res) {
					console.log(res);
				}
			);
		}
	}
};
</script>

<style></style>
