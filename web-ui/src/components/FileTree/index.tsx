import React, {useState, forwardRef, useEffect, useContext} from 'react';
import styles from './index.less';
import classnames from 'classnames';
import {IFileTreeNode} from '@/types';
import LoadingContent from '../Loading/LoadingContent';
import fileService from '@/service/file';
import {Menu} from "antd";
import Iconfont from "@/components/Iconfont";
import {FileManagerContext} from "@/context/file-manager";

interface IProps {
  className?: string;
  cRef: any;
  addTreeData?: IFileTreeNode[];
}

interface TreeNodeIProps {
  data: IFileTreeNode;
}

function Tree(props: IProps, ref: any) {
  const {className, cRef} = props;
  const [treeData, setTreeData] = useState<IFileTreeNode[]>([]);

  function getFileList() {
    fileService.searchTables({}).then(res => {
      const treeData = res.map(t => {
        return {
          name: t
        }
      })
      setTreeData(treeData);
    })
    return treeData;
  }

  useEffect(() => {
    getFileList();
  }, [])

  return <div className={classnames(className, styles.box)}>
    <LoadingContent data={treeData}>
      {
        treeData.map((item) => {
          return <TreeNode
            key = {item.name}
            data={item}
          />
        })
      }
    </LoadingContent>
  </div>
};

function TreeNode(props: TreeNodeIProps) {
  const {data} = props;
  const indentArr = new Array(1).fill('indent');
  const {model, setFileInfo} = useContext(FileManagerContext);

  function onDoubleClick() {
    const fileInfo = {
      name: data.name,
      key: data.name
    }
    setFileInfo(fileInfo);
  }

  return <>
    <div>
      <div className={classnames(styles.treeNode)} >
        <div className={styles.left}>
          {
            indentArr.map((item, i) => {
              return <div key={i} className={styles.indent}></div>
            })
          }
        </div>
        <div className={styles.right}>
          <div className={styles.dblclickArea}>
            <div className={styles.typeIcon}>
              <Iconfont code="&#xe63e;"></Iconfont>
            </div>
            <div className={styles.contentText} onDoubleClick={onDoubleClick}>
              {data.name}
            </div>
          </div>
        </div>
      </div>
    </div>
  </>
}

export default forwardRef(Tree);
