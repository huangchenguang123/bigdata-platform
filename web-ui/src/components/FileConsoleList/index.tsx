import React, { memo, useEffect, useState, useContext } from 'react';
import classnames from 'classnames';
import ModifyTable from '@/components/ModifyTable/ModifyTable';
import DatabaseQuery from '@/components/DatabaseQuery';
import AppHeader from '@/components/AppHeader';
import { qs } from '@/utils';
import { Tabs } from 'antd';

import historyService from '@/service/history';

import {
  ISavedConsole,
  IConsole,
  IEditTableConsole,
  ISQLQueryConsole,
} from '@/types';
import {
  TabOpened,
  ConsoleType,
  ConsoleStatus,
  DatabaseTypeCode,
  consoleTopComment,
} from '@/utils/constants';
import { DatabaseContext } from '@/context/database';
import styles from './index.less';
import {FileManagerContext} from "@/context/file-manager";
import FileQuery from "@/components/FileQuery";

interface IProps {
  className?: string;
  windowListChange: (value: IConsole[]) => void;
}

export default memo<IProps>(function FileConsoleList(props) {
  const { windowListChange } = props;
  const { consoleId } = qs<{ consoleId: string }>();
  const { model} = useContext(FileManagerContext);
  const { fileInfo} = model;
  const [windowList, setWindowList] = useState<IConsole[]>([]);
  const [activeKey, setActiveKey] = useState<string>(consoleId);

  useEffect(() => {
    windowListChange(windowList);
  }, [windowList]);

  useEffect(() => {
    createConsole();
  }, [model]);

  function createConsole() {
    const newConsole: IConsole = {
      name: `${fileInfo?.name}`,
      key: `${fileInfo?.key}`,
    };
    setActiveKey(newConsole.key);
    setWindowList([...windowList, newConsole]);
  }


  function renderCurrentTab(i: IConsole) {
    return (
      <FileQuery
        windowTab={i as ISQLQueryConsole}
        key={i.key}
        activeTabKey={activeKey!}
      />
    );
  }

  const onChangeTab = (newActiveKey: string) => {
    setActiveKey(newActiveKey);
  };

  const onEdit = (targetKey: any, action: 'add' | 'remove', window: any) => {
    // if (action === 'add') {
    //   setCreateConsoleDialog(true)
    // } else {
    //   closeWindowTab(targetKey);
    // }
    if (action === 'remove') {
      closeWindowTab(targetKey, window);
    }
  };

  const closeWindowTab = (targetKey: string, window: any) => {
    let newActiveKey = activeKey;
    let lastIndex = -1;
    windowList.forEach((item, i) => {
      if (item.key === targetKey) {
        lastIndex = i - 1;
      }
    });
    const newPanes = windowList.filter((item) => item.key !== targetKey);
    if (newPanes.length && newActiveKey === targetKey) {
      if (lastIndex >= 0) {
        newActiveKey = newPanes[lastIndex].key;
      } else {
        newActiveKey = newPanes[0].key;
      }
    }
    setWindowList(newPanes);
    setActiveKey(newActiveKey);
    let p: any = {
      id: targetKey,
      tabOpened: 'n',
    };

    if (window.status === 'DRAFT') {
      historyService.deleteWindowTab({ id: window.id });
    } else {
      historyService.updateWindowTab(p);
    }
  };

  return (
    <div className={styles.box}>
      <AppHeader className={styles.appHeader} showRight={false}>
        <div className={styles.tabsBox}>
          {!!windowList.length && (
            <Tabs
              hideAdd
              type="editable-card"
              onChange={onChangeTab}
              activeKey={activeKey}
              onEdit={(targetKey: any, action: 'add' | 'remove') => {
                onEdit(targetKey, action, window);
              }}
              items={windowList.map((t) => {
                return {
                  key: t.key,
                  label: t.name,
                };
              })}
            ></Tabs>
          )}
        </div>
      </AppHeader>
      <div className={styles.databaseQueryBox}>
        {windowList?.map((i: IConsole, index: number) => {
          return (
            <div
              key={index}
              className={classnames(styles.windowContent, {
                [styles.concealTab]: activeKey !== i.key,
              })}
            >
              {renderCurrentTab(i)}
            </div>
          );
        })}
      </div>
    </div>
  );
});
