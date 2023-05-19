import React, { memo, useEffect, useLayoutEffect, useRef, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { ConfigProvider } from 'antd';
import { history } from 'umi';
import { useLogin } from '@/utils/hooks';
import { getLastPosition, setCurrentPosition } from '@/utils';
import miscService from '@/service/misc';
import LoadingLiquid from '@/components/Loading/LoadingLiquid';
import i18n from '@/i18n';
import { ThemeType } from '@/utils/constants';
import Setting from '@/components/Setting';

interface IProps {
  className?: any;
}

/** 重启次数 */
const restartCount = 1;

declare global {
  interface Window {
    _ENV: string;
    _BaseURL: string;
  }
}

window._ENV = process.env.UMI_ENV!

export default memo<IProps>(function AppContainer({ className, children }) {
  const [startSchedule, setStartSchedule] = useState(0); // 0 初始状态 1 服务启动中 2 启动成功
  const [serviceFail, setServiceFail] = useState(false);

  function hashchange() {
    setCurrentPosition();
  }

  useLayoutEffect(() => {
    settings();
    window.addEventListener('hashchange', hashchange);
    return () => {
      window.removeEventListener('hashchange', hashchange);
    };
  }, []);

  useEffect(() => {
    detectionService();
  }, []);

  function detectionService() {
    setServiceFail(false);
    let flag = 0;
    const time = setInterval(() => {
      miscService.testService().then(() => {
        clearInterval(time);
        setStartSchedule(2);
        flag++;
      }).catch(error => {
        setStartSchedule(1);
        flag++;
      });
      if (flag > restartCount) {
        setServiceFail(true);
        clearInterval(time);
      }
    }, 1000);
  }

  function settings() {
    const theme = localStorage.getItem('theme') || ThemeType.dark;
    document.documentElement.setAttribute('theme', theme);
    localStorage.setItem('theme', theme);
    document.documentElement.setAttribute(
      'primary-color',
      localStorage.getItem('primary-color') || 'polar-blue',
    );

    if (!localStorage.getItem('lang')) {
      localStorage.setItem('lang', 'zh-cn');
    }

  }

  return (
    <ConfigProvider prefixCls="custom">
      <div className={classnames(className, styles.app)}>{children}</div>
    </ConfigProvider>
  );

});
