// const apibath = 'http://192.168.43.202:8801/' //API根路径
const apibath = 'http://192.168.43.202:8082';




//简化uni.request
const get = (url, data = {}, method = 'GET', loading = false) => {
	console.log('请求URL：' + apibath + url + '   请求参数：')
	console.log(data)
	var token = uni.getStorageSync('token');
	//console.log('token is >>>> '+token);
	return new Promise(resolve => {
		if (loading) {
			uni.showLoading({

			})
		}
		uni.request({
			url: apibath + url,
			method: method,
			data: data,
			dataType: 'json',
			header: {
				'Authorization': 'Bearer ' + token
			},
			success(res) {
				console.log(res.data)
				if (res.data.success) {
					resolve(res.data)
				} else {
					uni.showToast({
						icon: 'none',
						title: res.data.message
					})
				}
			},
			fail(res) {
				console.log(res.data)
			},
			complete() {
				if (loading) {
					uni.hideLoading()
				}
			}
		})
	})
}

const checkAuth = (callback) => {
	uni.getSetting({
		success(res) {
			console.log(res.authSetting)
			if (res.authSetting['scope.userInfo']) {
				console.log('已授权过，无需再次授权')
				callback();
			} else {
				uni.navigateTo({
					url:'/pages/login'
				})
			}
		}
	})
}

//获取上一页实例
const prePage = () => {
	let pages = getCurrentPages();
	let prePage = pages[pages.length - 2];
	// #ifdef H5
	return prePage;
	// #endif
	return prePage.$vm;
}
//简化uni.showtoast
const msg = (title, duration = 1500, mask = false, icon = 'none') => {
	//统一提示方便全局修改
	if (Boolean(title) === false) {
		return;
	}
	uni.showToast({
		title,
		duration,
		mask,
		icon
	});
}
//简化uni.navigateTo
const jump = (url) => {
	uni.navigateTo({
		url: url
	})
}
//格式化时间
const formatTime = (strJavaDate) => {
	let currentTime = Date.parse(new Date()) / 1000;
	if (timeStamp > currentTime) {
		let time = timeStamp - currentTime;
		let day = parseInt(time / 86400);
		time = time % 86400;
		let hour = parseInt(time / 3600);
		time = time % 3600;
		let minute = parseInt(time / 60);
		time = time % 60;
		let second = time;
		return {
			day,
			hour,
			minute,
			second
		}
	} else {
		console.log('error')
	}

}

/**
 * 将字符串时间转为毫秒数
 * @param {Object} str
 */
const convertTime = (str) => {
	if(str === undefined || str===null || str === '') {
		return null;
	}
	var arr_date_time = str.split(' '); // ['2020-05-05','12:23:23']
	var arr_ymd = arr_date_time[0].split('-');
	if(arr_date_time.length>1) {
		var arr_hms = arr_date_time[1].split(':');
		if(arr_hms.length>2) {
			return new Date(parseInt(arr_ymd[0]),parseInt(arr_ymd[1])-1,parseInt(arr_ymd[2]),parseInt(arr_hms[0]),parseInt(arr_hms[1]),parseInt(arr_hms[2])).getTime();
		} else {
			return new Date(parseInt(arr_ymd[0]),parseInt(arr_ymd[1])-1,parseInt(arr_ymd[2]),parseInt(arr_hms[0]),parseInt(arr_hms[1]),0).getTime();
		}
	} else {
		return new Date(parseInt(arr_ymd[0]),parseInt(arr_ymd[1])-1,parseInt(arr_ymd[2])).getTime();
	}
}

const rad = e => {
	let PI = Math.PI;
	return e * PI / 180
}

const getDistance = (lat1, lng1, lat2, lng2) => {
	let radLat1 = rad(lat1);
	let radLat2 = rad(lat2);
	let a = radLat1 - radLat2;
	let b = rad(lng1) - rad(lng2);
	let s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(
		b / 2), 2)));
	s = s * 6378.137;
	s = Math.round(s * 10000) / 10000;
	return s;
}

const orderStatus = (status) => {
	if (status == 10) {
		return '待支付';
	}
	if (status == 11) {
		return '已取消';
	}
	if (status == 12) {
		return '已关闭';
	}
	if (status == 19 || status == 20) {
		return '待发货';
	}
	if (status == 30) {
		return '已发货';
	}
	if (status == 40 || status == 50) {
		return '待评价';
	}
	if (status == 60) {
		return '已评价';
	}
	if (status == 70) {
		return '已完成';
	}
}
export default {
	apibath,
	get,
	prePage,
	msg,
	jump,
	formatTime,
	getDistance,
	orderStatus,
	checkAuth,
	convertTime
}
