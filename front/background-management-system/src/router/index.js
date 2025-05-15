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
          path: 'userCreateOrUpdate',
          name: 'userCreateOrUpdate',
          component: () => import('@/views/user/UserCreateOrUpdate.vue'),
        },
        {
          path: 'museumCreateOrUpdate',
          name: 'museumCreateOrUpdate',
          component: () => import('@/views/museum/MuseumCreateOrUpdate.vue'),
        },
        {
          path: 'commentReview',
          name: 'commentReview',
          component: () => import('@/views/review/CommentReviewManagement.vue'),
        },
        {
          path: 'imageReview',
          name: 'imageReview',
          component: () => import('@/views/review/ImageReviewManagement.vue'),
        },
        {
          path: 'videoReview',
          name: 'videoReview',
          component: () => import('@/views/review/VideoReviewManagement.vue'),
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
          path: 'log',
          name: 'log',
          component: () => import('@/views/log/LogManagement.vue'),
        },
        {
          path: 'notice',
          name: 'notice',
          component: () => import('@/views/notice/NoticeManagement.vue'),
        },
        {
          path: 'noticeCreateOrUpdate',
          name: 'noticeCreateOrUpdate',
          component: () => import('@/views/notice/NoticeCreateOrUpdate.vue'),
        },
        {
          path: 'database',
          name: 'database',
          component: () => import('@/views/database/DatabaseManagement.vue'),
        },
        {
          path: 'relicCreateOrUpdate',
          name: 'relicCreateOrUpdate',
          component: () => import('@/views/relic/RelicCreateOrUpdate.vue'),
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