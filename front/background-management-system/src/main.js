import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import App from './App.vue'
import router from './router'
import Api from '@/utils/Api'
import Message from '@/utils/Message'
import Request from '@/utils/Request'
import Utils from '@/utils/Utils'
import Confirm from '@/utils/Confirm'

const app = createApp(App)

// 全局注册图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }
app.use(createPinia())
app.use(router)
app.use(ElementPlus)

// 全局注册工具类
app.config.globalProperties.Api = Api;
app.config.globalProperties.Message = Message;
app.config.globalProperties.Request = Request;
app.config.globalProperties.Utils = Utils;
app.config.globalProperties.Confirm = Confirm;

app.mount('#app')

router.beforeEach((to, from, next) => {
  if (to.matched.some(res => res.meta.requireAuth)) { // 验证是否需要登陆
    var token=localStorage.getItem('token');
    if (token) { // 查询本地存储信息是否已经登陆
      next();
    } else {
      next({
        path: '/login', // 未登录则跳转至login页面
        });
    }
  } else {
    next();
  }
}
);