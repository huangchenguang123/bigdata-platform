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
import {IConsole, ITreeNode} from "@/typings/types";
import FileTree from "@/components/FileTree";
import FileConsoleList from "@/components/FileConsoleList";
import DraggableContainer from "@/components/DraggableContainer";

interface IProps {
  className?: any;
}

let monacoEditorExternalList: any = {};

function FileManagerPage({className}: IProps) {
  const [, setOpenDropdown] = useState(false);
  const [addTreeNode, setAddTreeNode] = useState<ITreeNode[]>();
  const treeRef = useRef<any>();
  const [, setWindowList] = useState<IConsole[]>([]);
  const [activeKey, setActiveKey] = useState<string>();
  const volatileRef = useRef<any>();
  const [isUnfold, setIsUnfold] = useState(true);

  const searchTable = () => {
  };

  function refresh() {
  }

  function windowListChange(value: IConsole[]) {
    setWindowList(value)
  }

  const callback = () => {
    monacoEditorExternalList[activeKey!] && monacoEditorExternalList[activeKey!].layout();
  };

  const moveLeftAside = () => {
    if (volatileRef.current) {
      if (volatileRef.current.offsetWidth === 0) {
        volatileRef.current.style.width = '250px';
        setIsUnfold(true);
      } else {
        volatileRef.current.style.width = '0px';
        setIsUnfold(false);
      }
    }
  };

  return <>
    <DraggableContainer className={classnames(className, styles.box)} callback={callback} volatileDom={{ volatileRef, volatileIndex: 0 }} >
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
          <FileTree
            cRef={treeRef}
            className={styles.tree}
            addTreeData={addTreeNode}
          />
        </div>
      </div>
      <div className={styles.main}>
        <FileConsoleList windowListChange={windowListChange} />
        <div className={styles.footer}>
          <div className={classnames({ [styles.reversalIconBox]: !isUnfold }, styles.iconBox)} onClick={moveLeftAside}>
            <Iconfont code='&#xeb93;' />
          </div>
          {
            <div className={classnames(styles.commandSearchResult)}>
              查询结果
              <Iconfont code='&#xeb93;' />
            </div>
          }
        </div>
      </div>
    </DraggableContainer>
    <UploadFile/>
  </>
};

export default function () {
  return <FileManagerContextProvider>
    <FileManagerPage/>
  </FileManagerContextProvider>
}
