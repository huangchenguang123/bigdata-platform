import { OSType } from '@/utils/constants';
import querystring from 'query-string';

export function formatDate(date:any, fmt = 'yyyy-MM-dd') {
  if (!date) {
    return '';
  }
  if (typeof date == 'number' || typeof date == 'string') {
    date = new Date(date);
  }
  if (!(date instanceof Date) || isNaN(date.getTime())) {
    return '';
  }
  var o:any = {
    'M+': date.getMonth() + 1,
    'd+': date.getDate(),
    'h+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds(),
    'q+': Math.floor((date.getMonth() + 3) / 3),
    S: date.getMilliseconds(),
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
  for (var k  in o)
    if (new RegExp('(' + k + ')').test(fmt))
      fmt = fmt.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length));
  return fmt;
}

export function safeAccess<T = any>(obj: any, str: string) {
  return str.split('.').reduce((o, k) => (o ? o[k] : undefined), obj) as T;
};

// 储存当前页面的hash
export function setCurrentPosition(){
  const hash = location.hash
  localStorage.setItem('lastPosition',hash)
}

// os is mac or windows
export function OSnow():OSType{
  var agent = navigator.userAgent.toLowerCase();
  var isMac = /macintosh|mac os x/i.test(navigator.userAgent);
  if (agent.indexOf("win32") >= 0 || agent.indexOf("wow32") >= 0 || agent.indexOf("win64") >= 0 || agent.indexOf("wow64") >= 0) {
      return OSType.WIN
  } else if(isMac){
    return OSType.MAC
  }else{
    return OSType.RESTS
  }
}

// 获取地址栏 ? 参数 如果换成历史模式需要改这里
export function qs<T>() {
  const parms:unknown = querystring.parse(location.hash.split('?')[1])
  return parms as T
}
