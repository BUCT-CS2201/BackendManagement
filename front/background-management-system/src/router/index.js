import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'default',
      redirect: "/login",
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
      },
      children:[
        {
          path: '',
          name: 'home',
          redirect: '/manage/user',
        },
        {
          path: 'user',
          name: 'user',
          component: () => import('@/views/user/UserManagement.vue'),
        },
        {
          path: 'review',
          name: 'review',
          component: () => import('@/views/review/ReviewManagement.vue'),
        },
        {
          path: 'museum',
          name: 'museum',
          component: () => import('@/views/museum/MuseumManagement.vue'),
        },
        {
          path: 'relic',
          name: 'relic',
          component: () => import('@/views/relic/RelicManagement.vue'),
        },
        {
          path: 'comment',
          name: 'comment',
          component: () => import('@/views/comment/CommentManagement.vue'),
        },
        {
          path: 'news',
          name: 'news',
          component: () => import('@/views/news/NewsManagement.vue'),
        },
        {
          path: 'backup',
          name: 'backup',
          component: () => import('@/views/backup/BackupManagement.vue'),
        },
      ]
    },
    {
      path: '/:catchAll(.*)', // 匹配所有未定义的路由
      name: 'notFound',
      component: () => import('@/views/ErrorPage.vue'),
    }
  ],
})

export default router