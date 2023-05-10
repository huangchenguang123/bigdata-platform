import React, { PureComponent } from 'react';
import classnames from 'classnames';
import desktopStyle from './desktop.less';
import prodStyle from './prod.less';

export default class Iconfont extends PureComponent<{
  code: string;
} & React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement>> {
  render() {
    const styles = window._ENV !== 'prod' ? desktopStyle : prodStyle
    return <i {...this.props} className={classnames(this.props.className, styles.iconfont)}>{this.props.code}</i>
  }
}
