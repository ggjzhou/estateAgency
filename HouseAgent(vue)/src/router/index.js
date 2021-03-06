import Vue from 'vue'
import VueRouter from 'vue-router'

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err);
}

Vue.use(VueRouter)

// 不上传反盗链
let head = document.getElementsByTagName('head');
let meta = document.createElement('meta');
meta.name = 'referrer';
//根据实际情况修改referrer的值，可选值参考上文
meta.content = 'no-referrer';
head[0].appendChild(meta);

const routes = [
  {
    path: '/',  // 程序启动默认路由
    component: () => import('@/components/common/Whole.vue'),
    meta: { title: '整体页面布局' },
    redirect: '/Login',  // 重定向到首页

    children: [
      //首页页面
      {
        path: '/Home',
        component: () => import('@/views/Home.vue'),
        meta: { title: '首页' }
      },

      //用户管理
      {
        path: '/Client',
        component: () => import('@/views/Client/index.vue'),
        meta: { title: '用户' },
        redirect: '/Client/clientManage',
        children: [
          {
            path: 'clientManage',
            component: () => import('@/views/Client/clientManage.vue'),
            meta: { title: '用户管理' },
          },
          {
            path: 'clientAdd',
            component: () => import('@/views/Client/clientAdd.vue'),
            meta: { title: '用户添加' },
          }
        ]
      },

      //房源管理
      {
        path: '/House',
        component: () => import('@/views/House/index.vue'),
        meta: { title: '房源' },
        redirect: '/House/houseManage',  // 该配置是若点击选择一级菜单时，默认选中并跳转到该一级菜单下的第一个二级菜单
        children: [
          {
            path: 'houseManage',
            component: () => import('@/views/House/houseManage.vue'),
            meta: { title: '房源管理' },
          },
          {
            path: 'houseSearch',
            component: () => import('@/views/House/houseSearch.vue'),
            meta: { title: '房源管理' },
          },
          {
            path: 'houseRelease',
            component: () => import('@/views/House/houseRelease.vue'),
            meta: { title: '房源发布' },
          }
        ]
      },

      //订单管理
      {
        path: '/Order',
        component: () => import('@/views/Order/index.vue'),
        meta: { title: '订单' },
        redirect: '/Order/orderManage',  // 该配置是若点击选择一级菜单时，默认选中并跳转到该一级菜单下的第一个二级菜单
        children: [
          {
            path: 'orderManage',
            component: () => import('@/views/Order/orderManage.vue'),
            meta: { title: '订单管理' },
          },
          {
            path: 'orderRelease',
            component: () => import('@/views/Order/orderRelease.vue'),
            meta: { title: '订单发布' },
          }
        ]
      },

      //员工管理
      {
        path: '/Emp',
        component: () => import('@/views/Emp/index.vue'),
        meta: { title: '员工' },
        redirect: '/Emp/empManage',  // 该配置是若点击选择一级菜单时，默认选中并跳转到该一级菜单下的第一个二级菜单
        children: [
          {
            path: 'empManage',
            component: () => import('@/views/Emp/empManage.vue'),
            meta: { title: '员工管理' },
          },
          {
            path: 'empAdd',
            component: () => import('@/views/Emp/empAdd.vue'),
            meta: { title: '员工添加' },
          },
          {
            path: 'rankList',
            component: () => import('@/views/Emp/rankList.vue'),
            meta: { title: '员工排行' },
          }
        ]
      },

    ]
  },
  //员工登录进入后台管理页面
  {
    path: '/Login',
    component: () => import('@/views/Login.vue'),
    meta: { title: '登录' }
  },

  //注册
  {
    path: '/regist',
    component: () => import('@/views/regist.vue'),
    meta: { title: '注册' }
  },

  //用户登陆成功首页浏览页面
  {
    path: '/Index',
    component: () =>
      import('@/views/clientViews/index.vue'),
    meta: { title: '首页' }
  },

  //用户首页输入房屋类型跳转的页面
  {
    path: '/HouseType',
    component: () =>
      import('@/views/clientViews/Housetype.vue'),
    meta: { title: '房屋类型' },
    name: 'housetype',
  },

  //用户首页输入房屋区域（所在城市跳转的页面）
  {
    path: '/HouseArea',
    component: () =>
      import('@/views/clientViews/Housearea.vue'),
    meta: { title: '房屋区域' },
    name: 'housearea',
  },

  // 房源详情页
  {
    path: '/HouseDetail',
    component: () =>
      import('@/views/clientViews/Housedetail.vue'),
    meta: { title: '房源详情' },
    name: 'housedetail',
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
