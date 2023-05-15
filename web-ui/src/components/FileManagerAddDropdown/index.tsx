import React, {useContext} from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Iconfont from '../Iconfont';
import type {MenuProps} from 'antd';
import {Menu} from 'antd';
import {IFile} from '@/types'
import {fileType} from '@/utils/constants';
import {FileManagerContext} from "@/context/file-manager";

type MenuItem = {
  label: React.ReactNode,
  key: React.Key | null,
  icon?: React.ReactNode,
  children?: MenuItem[],
}

function getItem(
  label: React.ReactNode,
  key: React.Key | null,
  icon?: React.ReactNode
): MenuItem {
  return {
    label,
    key,
    icon
  } as MenuItem;
}

const fileTypeChildren = Object.keys(fileType).map(t => {
  const source: IFile = fileType[t];
  return getItem(source.name, source.name, <Iconfont className={styles.databaseTypeIcon} code={source.icon}/>)
})

const items: MenuItem[] = fileTypeChildren

export default function FileManagerAddDropdown() {
  const {setIsUploaderShow} = useContext(FileManagerContext);
  const onClickMenuNode: MenuProps['onClick'] = () => {
    setIsUploaderShow(true)
  };

  return <div className={classnames(styles.box)}>
    <Menu onClick={onClickMenuNode} mode="vertical" items={items as any}/>
  </div>
}
