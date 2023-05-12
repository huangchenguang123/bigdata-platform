import React, { useEffect, useState, useRef } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { Dropdown } from 'antd';
import i18n from '@/i18n';
import Iconfont from '@/components/Iconfont';
import DraggableContainer from '@/components/DraggableContainer';
import SearchInput from '@/components/SearchInput';
import { ITreeNode, IConsole } from '@/types';
import FileManagerAddDropdown from "@/components/FileManagerAddDropdown";
import UploadFile from "@/components/UploadFile";
import FileManagerContextProvider from "@/context/file-manager";


interface IProps {
  className?: any;
}
type ITabType = 'sql' | 'editTable';

interface IEditTableConsole {
  label: string;
  key: string;
  tabType: ITabType;
  id: number;
  operationData: any;
}

let monacoEditorExternalList: any = {};


function DatabasePage({ className }: IProps) {
  const [activeKey, setActiveKey] = useState<string>();
  const [openDropdown, setOpenDropdown] = useState(false);
  const [isUnfold, setIsUnfold] = useState(true);
  const [addTreeNode, setAddTreeNode] = useState<ITreeNode[]>();
  const treeRef = useRef<any>();
  const volatileRef = useRef<any>();
  const [windowList, setWindowList] = useState<IConsole[]>([]);

  const closeDropdownFn = () => {
    setOpenDropdown(false);
  };

  useEffect(() => {
    if (openDropdown) {
      document.documentElement.addEventListener('click', closeDropdownFn);
    }
    return () => {
      document.documentElement.removeEventListener('click', closeDropdownFn);
    };
  }, [openDropdown]);

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

  const callback = () => {
    monacoEditorExternalList[activeKey!] && monacoEditorExternalList[activeKey!].layout();
  };

  const searchTable = (value: string) => {
    treeRef.current?.filtrationDataTree(value);
  };

  function refresh() {
    treeRef.current?.getDataSource();
  }

  function getAddTreeNode(data: ITreeNode) {
    setAddTreeNode([data]);
  }

  function windowListChange(value: IConsole[]) {
    setWindowList(value)
  }

  return <>
    <DraggableContainer className={classnames(className, styles.box)} callback={callback} volatileDom={{ volatileRef, volatileIndex: 0 }} >
      <div ref={volatileRef} className={styles.asideBox}>
        <div className={styles.aside}>
          <div className={styles.header}>
            <div className={styles.searchBox}>
              <SearchInput onChange={searchTable} placeholder="搜索文件" />
              <div
                className={classnames(styles.refresh, styles.button)}
                onClick={refresh}
              >
                <Iconfont code="&#xec08;" />
              </div>
              <Dropdown
                overlay={<FileManagerAddDropdown getAddTreeNode={getAddTreeNode} />}
                trigger={['click']}
              >
                <div
                  onClick={() => setOpenDropdown(true)}
                  className={classnames(styles.create, styles.button)}
                >
                  <Iconfont code="&#xe631;" />
                </div>
              </Dropdown>
            </div>
          </div>
          <div className={styles.overview}>
            <Iconfont code="&#xe63d;" />
            <span>{i18n('connection.button.overview')}</span>
          </div>
        </div>
      </div>
      <div className={styles.main}>
      </div>
    </DraggableContainer>
    <UploadFile />
  </>
};

export default function () {
  return <FileManagerContextProvider>
    <DatabasePage />
  </FileManagerContextProvider>
}
