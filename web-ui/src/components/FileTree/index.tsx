import React, {useState, forwardRef, useImperativeHandle, useEffect, useContext} from 'react';
import styles from './index.less';
import classnames from 'classnames';
import {IFileTreeNode} from '@/types';
import LoadingContent from '../Loading/LoadingContent';
import fileService from '@/service/file';
import {Dropdown, Menu, Space} from "antd";
import {DownOutlined} from "@ant-design/icons";
import Iconfont from "@/components/Iconfont";

interface IProps {
  className?: string;
  cRef: any;
  addTreeData?: IFileTreeNode[];
}

interface TreeNodeIProps {
  data: IFileTreeNode;
}

function Tree(props: IProps) {
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

  const renderMenu = () => {
    return <div>
      <Menu>
      </Menu>
    </div>
  }


  return <>
    <Dropdown overlay={renderMenu()} trigger={['contextMenu']}>
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
            <div className={styles.contentText} >
              {data.name}
            </div>
          </div>
        </div>
      </div>
    </Dropdown>
  </>
}

export default forwardRef(Tree);
