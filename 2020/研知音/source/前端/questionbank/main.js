import Vue from "vue"
import App from "./App"
import store from './store'

/* import Util from './common/util.js'
Vue.component("Util",Util) */

import choose from "./components/template/image/choose.vue"
import compress from "./components/template/image/compress.vue"
import pageHead from './components/page-head.vue'
import uRadio from './uview-ui/components/u-radio/u-radio.vue'

import uRadioGroup from './uview-ui/components/u-radio-group/u-radio-group.vue'
import cuCustom from './colorui/components/cu-custom.vue'
import commonFuc from '@/utils/util.js'

Vue.prototype.$store = store

Vue.prototype.$util = commonFuc

Vue.component("u-radio",uRadio)
Vue.component("u-radio-group",uRadioGroup)
// main.js
import uView from 'uview-ui';

//Vue.component("uView",uView)
Vue.component('cu-custom', cuCustom)
Vue.use(uView);
Vue.component("choose",choose)
Vue.component("compress",compress)
Vue.component("pageHead",pageHead)
Vue.prototype.$server ="https://www.lztk.xyz" 
//Vue.prototype.$server ="http://localhost:8080" 
Vue.prototype.JSESSIONID=function()
{
	console.log("arrive here")
	var JSESSIONID = wx.getStorageSync("sessionid");
	if(JSESSIONID!="")
	{
		console.log(JSESSIONID)
		return JSESSIONID;
	}else{
		wx.login({
		    success: function (login_res) {
				console.log("get code success")
		        wx.getUserInfo({
		            success: function (res) {
						var userInfo = res.userInfo;
						console.log("get userinfo success")
		                wx.request({
		                    url: Vue.prototype.$server+'/api/me/login',
		                    method: 'POST',
							withCredentials:true,
		                    header: {
		                        'content-type': 'application/x-www-form-urlencoded'
		                    },
		                    data: {
		                        code:login_res.code,
		                        userHead: userInfo.avatarUrl,
		                        userName: userInfo.nickName,
		                        userGender: userInfo.gender,
		                        userCity: userInfo.city,
		                        userProvince: userInfo.province
		                    },
		                    success:function(res) {
		                        // 将返回的数据保存到全局的缓冲中，方便其他页面使用
								JSESSIONID = res.data.data;
		                        wx.setStorageSync( "sessionid", res.data.data)
		                    }
		                })
		            },
					fail:function(res){
						

					}
		        })
		    },
			fail:function(res){
				
			
			}
			
		})
		return JSESSIONID;	
	}
}
Vue.config.productionTip = false
App.mpType = "app"
const app = new Vue({
	...App,
})
app.$mount()
