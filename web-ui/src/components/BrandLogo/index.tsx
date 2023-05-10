import React, { memo } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import logo from '@/assets/chatLogo.png'

interface IProps extends React.DetailedHTMLProps<React.HTMLAttributes<HTMLDivElement>, HTMLDivElement> {
  className?: any;
  size?: number;
}

export default memo<IProps>(function BrandLogo({ className, size = 48, ...res }) {
  return (
    <div {...res} className={classnames(className, styles.box)} style={{ height: `${size}px` }}>
      <img src={logo} alt="" />
    </div>
  );
});
