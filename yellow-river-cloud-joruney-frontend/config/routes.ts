export default [
  { path: '/user', layout: false, routes: [{ path: '/user/login', component: './User/Login' }] },
  { path: '/', redirect: '/add_chart' },
  { path: '/add_chart', name: '智能分析', icon: 'DotChart', component: './AddChart' },
  {
    path: '/add_chart_async',
    name: '智能分析（异步）',
    icon: 'DotChart',
    component: './AddChartAsync',
  },
  {
    path: '/add_chart_async_mq',
    name: '智能分析（异步 基于消息队列）',
    icon: 'DotChart',
    component: './AddChartAsyncMq',
  },
  { path: '/my_chart', name: '我的图表', icon: 'Home', component: './MyChart' },
  {
    path: '/admin',
    icon: 'crown',
    access: 'canAdmin',
    name: '管理员页面',
    routes: [
      { path: '/admin', redirect: '/admin/sub-page' },
      { path: '/admin/sub-page', component: './Admin' },
    ],
  },
  { path: '/', redirect: '/welcome' },
  { path: '*', layout: false, component: './404' },
];
