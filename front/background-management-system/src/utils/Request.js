import axios from 'axios'
import Api from '@/utils/Api'
import Message from '@/utils/Message'
import { ElLoading } from 'element-plus'
import router from '@/router'

// 数据提交格式，默认为表单
const contentType = 'application/x-www-form-urlencoded;charset=UTF-8'
const contentTypeJson = 'application/json;charset=UTF-8'
// 创建axios实例
const instance = axios.create({
    // 请求路径加上/api作为前缀
    baseURL: Api.devDomain+"/api",
    timeout: 10*1000,
});
// 加载对象
let loading = null;


// 发送请求前拦截器
instance.interceptors.request.use(
    (config)=>{
        // 加载动画
        loading = ElLoading.service({
            lock: true,
            text: '加载中......',
            background: 'rgba(0, 0, 0, 0.7)',
        })
        return config;
    },
    (error)=>{
        loading.close();
        Message.error("请求发送失败");
        return Promise.reject("请求发送失败");
    }
);

// 收到响应后拦截器
instance.interceptors.response.use(
    (response)=>{
        loading.close();
        const { responseType } = response.config;
        
        const responseData = response.data;
        // 后端传输文件不进行包装，直接返回文件对象
        if (responseType == "arraybuffer" || responseType == "blob") {
            return response;
        }
        // 正常响应
        if(responseData.code==200){
            return responseData;
        }else if(responseData.code==401){ // 登录超时
            // 跳转登录页面
            Message.error(responseData.info);
            router.push("/login");
        }else{
            return Promise.reject({message: responseData.info});
        }
    },
    (error)=>{  
        loading.close();
        return Promise.reject({message: "网络异常"});
    }
);

const request = (config) =>{
    const { url, params, dataType, responseType } = config;
    // 创建表单对象
    let formData = new FormData();
    for(let key in params){
        formData.append(key,params[key]==undefined?"":params[key]);
    }
    if (dataType != null && dataType == 'json') {
        contentType = contentTypeJson;
    }
    // 携带token
    const token = localStorage.getItem('token');
    // 请求头
    let headers = {
        'Content-Type': contentType,
        'X-Requested-With': 'XMLHttpRequest',
        'token': token,
    }
    return instance.post(url,formData,{
        headers: headers,
        responseType: responseType,
    }).catch(error=>{
        Message.error(error.message);
        return null;
    });
};

const requestJSON = (config) =>{
    const { url, params, dataType, responseType } = config;
    // 创建JSON对象
    const data = JSON.stringify(params);
    // 携带token
    const token = localStorage.getItem('token');
    // 请求头
    let headers = {
        'Content-Type': 'application/json',  
        'X-Requested-With': 'XMLHttpRequest',
        'token': token,
    }
    return instance.post(url,data,{
        headers: headers,
        responseType: responseType,
    }).catch(error=>{
        Message.error(error.message);
        return null;
    });
};

const Request = {
    request,
    requestJSON,
}

export default Request;