<template>
	<view class="demo-popup">
		<cl-card label="基本用法">
			<cl-row v-for="(item, index) in dirs" :key="index">
				<cl-popup
					:size="item.size"
					:visible.sync="item.visible"
					:direction="item.value"
				>
					<p class="f-26">{{ item.label }}</p>
				</cl-popup>

				<cl-button @tap="open(item)">
					<text>{{ item.label }}</text>
				</cl-button>
			</cl-row>
		</cl-card>

		<cl-card label="自定义内容">
			<cl-confirm ref="confirm"></cl-confirm>

			<cl-popup :visible.sync="visible" direction="bottom">
				<view class="demo-icon">
					<cl-card label="图标">
						<cl-row>
							<cl-col span="8" v-for="(item, index) in list" :key="index">
								<view class="block" @click="hello(index)">
									<cl-icon :name="item.value" :size="44"></cl-icon>
									<text class="label">{{ item.label }}</text>
								</view>
							</cl-col>
						</cl-row>
					</cl-card>
				</view>
				
				<!-- <cl-form>
					<cl-form-item label="名称">
						<cl-input
							placeholder="请填写名称"
							maxlength="11"
							type="text"
						>
						</cl-input>
					</cl-form-item>
					<cl-form-item label="手机号">
						<cl-input
							placeholder="请填写手机号"
							maxlength="11"
							type="text"
						>
						</cl-input>
					</cl-form-item>
				</cl-form>

				<view class="footer">
					<cl-button @tap="visible = false">
						<text>取消</text>
					</cl-button>
					<cl-button type="primary" @tap="submit">
						<text>提交</text>
					</cl-button>
				</view> -->
			</cl-popup>

			<cl-button @tap="visible = true">
				<text>新增联系方式</text>
			</cl-button>
		</cl-card>
	</view>
</template>

<script>
export default {
	data() {
		return {
			visible: false,
			dirs: [
				{
					label: "顶部弹出",
					value: "top",
					visible: false,
					size: "auto",
				},
				{
					label: "右侧弹出",
					value: "right",
					visible: false,
					size: "auto",
				},
				{
					label: "底部弹出",
					value: "bottom",
					visible: false,
					size: "auto",
				},
				{
					label: "左侧弹出",
					value: "left",
					visible: false,
					size: "auto",
				},
				{
					label: "中间弹出",
					value: "center",
					visible: false,
					size: "auto",
				},
			],
			list: [
				{
					label: '练习历史',
					value: 'cl-icon-notification'
				},
				{
					label: '我的收藏',
					value: 'cl-icon-notification-fill'
				},
				{
					label: '我的错题',
					value: 'cl-icon-info'
				},
				{
					label: '我的笔记',
					value: 'cl-icon-info-fill'
				},
				{
					label: '资料缓存',
					value: 'cl-icon-arrow-top'
				},
				{
					label: 'arrow-right',
					value: 'cl-icon-arrow-right'
				}/* ,
				{
					label: 'arrow-bottom',
					value: 'cl-icon-arrow-bottom'
				},
				{
					label: 'arrow-left',
					value: 'cl-icon-arrow-left'
				},
				{
					label: 'question',
					value: 'cl-icon-question'
				},
				{
					label: 'question-fill',
					value: 'cl-icon-question-fill'
				},
				{
					label: 'toast-waiting',
					value: 'cl-icon-toast-waiting'
				},
				{
					label: 'toast-warning',
					value: 'cl-icon-toast-warning'
				},
				{
					label: 'toast-success',
					value: 'cl-icon-toast-success'
				},
				{
					label: 'toast-error',
					value: 'cl-icon-toast-error'
				},
				{
					label: 'avatar',
					value: 'cl-icon-avatar'
				},
				{
					label: 'plus',
					value: 'cl-icon-plus'
				},
				{
					label: 'minus',
					value: 'cl-icon-minus'
				},
				{
					label: 'image',
					value: 'cl-icon-image'
				},
				{
					label: 'check',
					value: 'cl-icon-check'
				},
				{
					label: 'image',
					value: 'cl-icon-image'
				},
				{
					label: 'close',
					value: 'cl-icon-close'
				},
				{
					label: 'emoji',
					value: 'cl-icon-emoji'
				},
				{
					label: 'favor-fill',
					value: 'cl-icon-favor-fill'
				},
				{
					label: 'round-check',
					value: 'cl-icon-round-check'
				},
				{
					label: 'round-check-fill',
					value: 'cl-icon-round-check-fill'
				},
				{
					label: 'round-close',
					value: 'cl-icon-round-close'
				},
				{
					label: 'round-close-fill',
					value: 'cl-icon-round-close-fill'
				},
				{
					label: 'search',
					value: 'cl-icon-search'
				} */
			]
		};
	},

	methods: {
		hello(i){
			console.log(i);
		},
		open(item) {
			item.visible = true;
		},
		submit() {
			this.$refs["confirm"].open({
				title: "提示",
				message: "确定添加该条数据吗？",
				callback: ({ action }) => {
					if (action === "confirm") {
						this.visible = false;
					}
				},
			});
		},
	},
};
</script>

<style lang="scss">
.demo-popup {
	.f-26 {
		font-size: 26rpx;
	}

	.footer {
		margin-top: 20rpx;
		margin-bottom: 20rpx;
		display: flex;

		.cl-button {
			flex: 1;
		}
	}
}

/* .demo-flex {
	.a,
	.b {
		border-radius: 6rpx;
		height: 60rpx;
	}

	.a {
		background-color: #d3dce6;
	}

	.b {
		background-color: #e5e9f2;
	}
} */
.demo-icon {
	.block {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		height: 160rpx;

		&:active {
			background-color: #eee;
		}

		.label {
			font-size: 24rpx;
			margin-top: 10rpx;
		}
	}
}
</style>
