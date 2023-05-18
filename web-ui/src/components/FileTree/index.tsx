import React, {useEffect, useContext, useState, forwardRef, useImperativeHandle} from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Iconfont from '../Iconfont';
import {Dropdown, Tooltip} from 'antd';
import {IFileTreeNode} from '@/types';
import {approximateFileTreeNode} from '@/utils';
import LoadingContent from '../Loading/LoadingContent';
import {useUpdateEffect} from '@/utils/hooks';
import fileService from '@/service/file';
import {DatabaseContext} from '@/context/database';

interface IProps {
  className?: string;
  cRef: any;
  addTreeData?: IFileTreeNode[];
}

interface TreeNodeIProps {
  data: IFileTreeNode;
  level: number;
  show: boolean;
  setTreeData: Function;
  showAllChildrenPenetrate?: boolean;
}

function Tree(props: IProps, ref: any) {
  const {className, cRef, addTreeData} = props;
  const [treeData, setTreeData] = useState<IFileTreeNode[] | null>(null);
  const [searchedTreeData, setSearchedTreeData] = useState<IFileTreeNode[] | null>(null);
  const {model} = useContext(DatabaseContext);


  useUpdateEffect(() => {
    setTreeData([...(treeData || []), ...(addTreeData || [])]);
  }, [addTreeData])

  function filtrationDataTree(keywords: string) {
    if (!keywords) {
      setSearchedTreeData(null)
    } else if (treeData?.length && keywords) {
      setSearchedTreeData(approximateFileTreeNode(treeData, keywords));
    }
  }

  function getFileList() {
    setTreeData(null);
    fileService.searchTables({}).then(res => {
      if (res != null && res.code == 200 && res.data != null) {
        const treeData = res.data.map(t => {
          return {
            name: t,
            key: t
          }
        })
        setTreeData(treeData);
      }
    })
  }

  useImperativeHandle(cRef, () => ({
    getFileList,
    filtrationDataTree
  }))

  useEffect(() => {
    getFileList();
  }, [])

  return <div className={classnames(className, styles.box)}>
    <LoadingContent data={treeData}>
      {
        (searchedTreeData || treeData)?.map((item, index) => {
          return <TreeNode
            setTreeData={setTreeData}
            key={item.name + index}
            show={true}
            level={0}
            data={item}
          />
        })
      }
    </LoadingContent>
  </div>
};

function TreeNode(props: TreeNodeIProps) {
  const {setTreeData, data, level, show = false, showAllChildrenPenetrate = false} = props;
  const [showChildren, setShowChildren] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const indentArr = new Array(level).fill('indent');

  useEffect(() => {
    setShowChildren(showAllChildrenPenetrate);
  }, [showAllChildrenPenetrate])

  const renderMenu = () => {
    return <div/>
  }

  function renderTitle(data: IFileTreeNode) {
    return <>
    </>
  }

  return show ? <>
    <Dropdown overlay={renderMenu()} trigger={['contextMenu']}>
      <Tooltip placement="right" title={renderTitle(data)}>
        <div
          className={classnames(styles.treeNode, {[styles.hiddenTreeNode]: !show})}>
          <div className={styles.left}>
            {
              indentArr.map((item, i) => {
                return <div key={i} className={styles.indent}></div>
              })
            }
          </div>
          <div className={styles.right}>
            {
              <div className={styles.arrows}>
                {
                  isLoading
                    ?
                    <div className={styles.loadingIcon}>
                      <Iconfont code='&#xe6cd;'/>
                    </div>
                    :
                    <Iconfont className={classnames(styles.arrowsIcon, {[styles.rotateArrowsIcon]: showChildren})}
                              code='&#xe608;'/>
                }
              </div>
            }
            <div className={styles.dblclickArea}>
              <div className={styles.typeIcon}>
                <Iconfont code="&#xe63d;"/>
              </div>
              <div className={styles.contentText}>
                <div className={styles.name} dangerouslySetInnerHTML={{__html: data.name}}></div>
              </div>
            </div>
          </div>
        </div>
      </Tooltip>
    </Dropdown>
  </> : <></>
}

export default forwardRef(Tree);
