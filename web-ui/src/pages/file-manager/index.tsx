import React, {useState, useRef} from 'react';
import styles from './index.less';
import classnames from 'classnames';
import {Dropdown} from 'antd';
import i18n from '@/i18n';
import Iconfont from '@/components/Iconfont';
import SearchInput from '@/components/SearchInput';
import FileManagerAddDropdown from "@/components/FileManagerAddDropdown";
import UploadFile from "@/components/UploadFile";
import FileManagerContextProvider from "@/context/file-manager";
import Tree from "@/components/Tree";
import {ITreeNode} from "@/types";

interface IProps {
  className?: any;
}

function FileManagerPage({className}: IProps) {
  const [, setOpenDropdown] = useState(false);
  const [addTreeNode, setAddTreeNode] = useState<ITreeNode[]>();
  const treeRef = useRef<any>();

  const searchTable = () => {
  };

  function refresh() {
  }

  return <>
    <div className={classnames(className, styles.box)}>
      <div className={styles.asideBox}>
        <div className={styles.aside}>
          <div className={styles.header}>
            <div className={styles.searchBox}>
              <SearchInput onChange={searchTable} placeholder="搜索文件"/>
              <div
                className={classnames(styles.refresh, styles.button)}
                onClick={refresh}
              >
                <Iconfont code="&#xec08;"/>
              </div>
              <Dropdown
                overlay={<FileManagerAddDropdown/>}
                trigger={['click']}
              >
                <div
                  onClick={() => setOpenDropdown(true)}
                  className={classnames(styles.create, styles.button)}
                >
                  <Iconfont code="&#xe631;"/>
                </div>
              </Dropdown>
            </div>
          </div>
          <div className={styles.overview}>
            <Iconfont code="&#xe63d;"/>
            <span>{i18n('file.list.icon')}</span>
          </div>
          <Tree
            cRef={treeRef}
            className={styles.tree}
            addTreeData={addTreeNode}
          />
        </div>
      </div>
      <div className={styles.main}>
      </div>
    </div>
    <UploadFile/>
  </>
};

export default function () {
  return <FileManagerContextProvider>
    <FileManagerPage/>
  </FileManagerContextProvider>
}
