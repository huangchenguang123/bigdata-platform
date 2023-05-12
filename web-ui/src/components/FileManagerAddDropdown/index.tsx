import React, { memo, useState, useContext } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Iconfont from '../Iconfont';
import type { MenuProps } from 'antd';
import { Menu } from 'antd';
import {IDatabase, IFile, ITreeNode} from '@/types'
import {databaseType, DatabaseTypeCode, fileType} from '@/utils/constants';
import { DatabaseContext } from '@/context/database'
import {FileManagerContext} from "@/context/file-manager";

interface IProps {
  className?: string;
  getAddTreeNode: (data: ITreeNode) => void;
}

type MenuItem = {
  label: React.ReactNode,
  key: React.Key | null,
  icon?: React.ReactNode,
  children?: MenuItem[],
}

function getItem(
  label: React.ReactNode,
  key: React.Key | null,
  icon?: React.ReactNode,
  children?: MenuItem[],
): MenuItem {
  return {
    label,
    key,
    icon,
    children,
  } as MenuItem;
}

const newDataSourceChildren = Object.keys(fileType).map(t => {
  const source: IFile = fileType[t];
  return getItem(source.name, source.name, <Iconfont className={styles.databaseTypeIcon} code={source.icon} />)
})

type IGlobalAddMenuItem = {

} & MenuItem


const globalAddMenuList: IGlobalAddMenuItem[] = [
  // {
  //   label: '新建控制台',
  //   key: 'newConsole',
  //   icon: <Iconfont code='&#xe619;' />
  // },
  {
    label: '新建数据源',
    key: 'newDataSource',
    icon: <Iconfont code='&#xe631;' />,
    children: newDataSourceChildren
  },
]

const items: MenuItem[] = newDataSourceChildren

export default memo<IProps>(function FileManagerAddDropdown(props) {
  const { className, getAddTreeNode } = props;
  const { setIsUploaderShow } = useContext(FileManagerContext);

  const onClickMenuNode: MenuProps['onClick'] = (e) => {
    setIsUploaderShow(true)
  };

  function submitCallback(data: ITreeNode) {
    getAddTreeNode(data);
  }

  return <div className={classnames(styles.box, className)}>
    <Menu onClick={onClickMenuNode} mode="vertical" items={items as any} />
    {/* {((!!dataSourceType && isModalVisible) || editDataSourceData) &&
      <CreateConnection
        submitCallback={submitCallback}
        dataSourceType={editDataSourceData.dataType || dataSourceType}
        dataSourceData={editDataSourceData}
        onCancel={onCancel}
      />
    } */}
  </div>
})
