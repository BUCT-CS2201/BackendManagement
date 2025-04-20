import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'default',
      component: () => import('@/views/Login.vue'),
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/Login.vue'),
    },
    {
      path: '/manage',
      name: 'manage',
      component: () => import('@/views/Manage.vue'),
      // 登录校验，防止直接访问
      meta: {
        requiresAuth: true,
      }
    },
    {
      path: '/:catchAll(.*)', // 匹配所有未定义的路由
      name: 'notFound',
      component: () => import('@/views/ErrorPage.vue'),
    }
  ],
})

export default router