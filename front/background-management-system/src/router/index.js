import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    
    {
      path: '/:catchAll(.*)', // 匹配所有未定义的路由
      name: 'notFound',
      component: () => import('@/views/ErrorPage.vue'),
    }
  ],
})

export default router