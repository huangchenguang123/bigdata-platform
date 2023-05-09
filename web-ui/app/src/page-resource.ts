/**
 * 页面资源集合，请不要在主进程中引用
 */

// common
export const NoMatch = import('./views/common/no-match')

// 设为 undefined 将不会创建路由，一般用于重定向
export const Home = undefined

export const WorkSpace = import('@/src/views/workspace/workspace')