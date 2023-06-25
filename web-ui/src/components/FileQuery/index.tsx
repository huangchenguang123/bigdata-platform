import React, {
  useState,
  useRef,
  useEffect,
} from 'react';
import classnames from 'classnames';
import { useParams } from 'umi';
import { ISQLQueryConsole } from '@/typings/types';
import { Button, Divider, message, Modal, Select, Tooltip } from 'antd';
import { WindowTabStatus } from '@/utils/constants';
import Iconfont from '@/components/Iconfont';
import MonacoEditor, { setEditorHint } from '@/components/MonacoEditor';
import DraggableContainer from '@/components/DraggableContainer';
import SearchResult from '@/components/SearchResult';
import LoadingContent from '@/components/Loading/LoadingContent';
import mysqlServer from '@/service/mysql';
import historyServer from '@/service/history';
import tableService from '@/service/table';
import adHotQueryService from '@/service/ad-hot-query';
import { formatParams, uuid } from '@/utils/common';
import connectToEventSource from '@/utils/eventSource';

import styles from './index.less';
const monaco = require('monaco-editor/esm/vs/editor/editor.api');

export interface IDatabaseQueryProps {
  activeTabKey: string;
  windowTab: ISQLQueryConsole;
}

enum IPromptType {
  NL_2_SQL = 'NL_2_SQL',
}

interface IProps extends IDatabaseQueryProps {
  className?: string;
}

type IParams = { tableNames: string[]; ext: string; destSqlType: string };

let monacoEditorExternalList: any = {};

const initModal = {
  open: false,
  title: '',
  handleOk: () => { },
  handleCancel: () => { },
  content: <></>,
};
export default function FileQuery(props: IProps) {
  const { activeTabKey, windowTab } = props;
  const params: { id: string; type: string } = useParams();
  const [manageResultDataList, setManageResultDataList] = useState<any>([]);

  const tableListRef = useRef<Array<{ label: string; value: string }>>([]);
  const uid = useRef<string>(uuid());
  const monacoEditor = useRef<any>(null);
  const volatileRef = useRef<any>(null);
  const monacoHint = useRef<any>(null);
  const extendParams = useRef<IParams>({
    tableNames: [],
    ext: '',
    destSqlType: '',
  });
  const [modalConfig, setModalConfig] = useState(initModal);
  // const [aiDropVisible, setAiDropVisible] = useState(false);
  const showSearchResult = false;

  useEffect(() => {
    if (windowTab.consoleId !== +activeTabKey) {
      return;
    }
    connectConsole();
    getTableList();
  }, [activeTabKey]);

  const connectConsole = () => {
    const { consoleId, dataSourceId, databaseName } = windowTab || {};
    mysqlServer.connectConsole({
      consoleId,
      dataSourceId,
      databaseName,
    });
  };

  const getTableList = () => {
    const p = {
      dataSourceId: windowTab.dataSourceId!,
      databaseName: windowTab.databaseName!,
      pageNo: 1,
      pageSize: 999,
    };

    mysqlServer.getList(p).then((res) => {
      const tableList = res.data?.map((item) => {
        return {
          name: item.name,
          key: item.name,
        };
      });
      disposalEditorHintData(tableList);
    });
  };

  const disposalEditorHintData = (tableList: any) => {
    try {
      monacoHint.current?.dispose();
      const myEditorHintData: any = {};
      tableList?.map((item: any) => {
        myEditorHintData[item.name] = [];
      });
      monacoHint.current = setEditorHint(myEditorHintData);
    } catch { }
  };

  const getEditor = (editor: any) => {
    monacoEditor.current = editor;
    monacoEditorExternalList[activeTabKey] = editor;
    const model = editor.getModel(editor);
    model.setValue(
      localStorage.getItem(
        `window-sql-${windowTab.dataSourceId}-${windowTab.databaseName}-${windowTab.consoleId}`,
      ) ||
      windowTab.ddl ||
      '',
    );
  };

  const callback = () => {
    monacoEditor.current && monacoEditor.current.layout();
  };

  /** 获取编辑器整体值 */
  const getMonacoEditorValue = () => {
    if (monacoEditor?.current?.getModel) {
      const model = monacoEditor?.current.getModel(monacoEditor?.current);
      const value = model.getValue();
      return value;
    }
  };

  /** 获取选中区域的值 */
  const getSelectionVal = () => {
    const selection = monacoEditor.current.getSelection(); // 获取光标选中的值
    const { startLineNumber, endLineNumber, startColumn, endColumn } =
      selection;
    const model = monacoEditor.current.getModel(monacoEditor.current);
    const value = model.getValueInRange({
      startLineNumber,
      startColumn,
      endLineNumber,
      endColumn,
    });
    return value;
  };

  const executeSql = () => {}

  const smartExecute = (tables: string[]) => {
    const query = getSelectionVal() || getMonacoEditorValue();
    if (!query) {
      message.warning('请输入SQL语句');
      return;
    }
    let p = {
      query,
      tables
    };
    setManageResultDataList(null);
    adHotQueryService
      .smartExecute(p)
      .then((res) => {
        setManageResultDataList(res);
      })
      .catch((error) => {
        setManageResultDataList([]);
      });
  };

  const saveWindowTabTab = () => {
    let p = {
      id: windowTab.consoleId,
      name: windowTab?.name,
      type: windowTab.DBType,
      dataSourceId: +params.id,
      databaseName: windowTab.databaseName,
      status: WindowTabStatus.RELEASE,
      ddl: getMonacoEditorValue(),
    };
    historyServer.updateWindowTab(p).then((res) => {
      message.success('保存成功');
    });
  };

  const monacoEditorChange = () => {
    localStorage.setItem(
      `window-sql-${windowTab.dataSourceId}-${windowTab.databaseName}-${windowTab.consoleId}`,
      getMonacoEditorValue(),
    );
  };

  const chat2SQL = (promptType: IPromptType) => {
    const sentence = getSelectionVal();

    // TODO: 自然语言转化SQL
    const model = monacoEditor.current.getModel(monacoEditor.current);
    const preValue = model.getValue();

    model.setValue(
      `${preValue}\n\n## ---BEGIN---\n## ${sentence}\n`,
    );

    const { dataSourceId, databaseName } = windowTab || {};
    const { tableNames: tableList, ext, destSqlType } = extendParams.current;
    const tableNames = tableList
      .map((table) => `tableNames=${table}`)
      .join('&');
    const params =
      formatParams({
        dataSourceId,
        databaseName,
        promptType,
        message: sentence,
        ext,
        destSqlType,
      }) + tableNames;

    const handleMessage = (message: string) => {
      const isEOF = message === '[DONE]';

      // 获取当前编辑器的model
      const model = monacoEditor.current.getModel();
      // 获取model的行数
      const lineCount = model.getLineCount();
      // 在文档的末尾添加内容
      model.applyEdits([
        {
          range: new monaco.Range(
            lineCount,
            model.getLineMaxColumn(lineCount),
            lineCount,
            model.getLineMaxColumn(lineCount),
          ),
          text: isEOF ? '\n## --- END --- \n' : JSON.parse(message).content,
        },
      ]);

      if (isEOF) {
        closeEventSource();
      }
    };

    const handleError = (error: any) => {
      console.error('Error:', error);
    };

    // 连接到 EventSourcePolyfill 并设置回调函数
    const closeEventSource = connectToEventSource({
      url: `/api/ai/chat?${params}`,
      uid: uid.current,
      onMessage: handleMessage,
      onError: handleError,
    });
    extendParams.current = { tableNames: [], ext: '', destSqlType: '' };
  };

  /**
   * 自然语言转化SQL
   */
  const lang2SQL = async (type?: 'withParams') => {
    const sentence = getSelectionVal();
    if (!sentence) {
      message.warning('请选择输入信息');
      return;
    }

    if (!type) {
      smartExecute();
    } else {
      let res = await tableService.searchTables({});
      tableListRef.current = res?.map((item) => ({
        label: item,
        value: item
      }));
      // --------

      setModalConfig({
        open: true,
        title: '请选择表',
        handleOk: () => {
          smartExecute(res);
          setModalConfig(initModal);
        },
        handleCancel: () => {
          setModalConfig(initModal);
        },
        content: (
          <Select
            key={IPromptType.NL_2_SQL}
            mode="tags"
            style={{ width: '100%' }}
            placeholder="请输入想要查询的表"
            onChange={(values) => {
              extendParams.current = {
                tableNames: values,
                ext: '',
                destSqlType: '',
              };
            }}
            options={tableListRef.current}
          />
        ),
      });
    }
  };

  const optBtn = [
    /** 基础SQL命令 */
    [

    ],
    /** 自然语言转化SQL */
    [
      {
        name: '自然语言执行',
        icon: '\ue626',
        onClick: () => lang2SQL('withParams'),
      },
      { name: 'SQL执行', icon: '\ue626', onClick: executeSql }
    ]
  ];

  const renderOptBtn = () => {
    let dom = [];
    for (let i = 0; i < optBtn.length; i++) {
      const optList = optBtn[i];
      let tmpDom: Array<React.ReactNode> = [];
      if (i === 0) {
        tmpDom = (optList || []).map((item: any, index) => (
          <Tooltip key={index} placement="bottom" title={item.name}>
            <Iconfont
              code={item.icon}
              className={styles.icon}
              onClick={item.onClick}
            />
          </Tooltip>
        ));
      } else {
        tmpDom = (optList || []).map((item: any, index) => (
          <Button
            key={index}
            type="link"
            onClick={item.onClick}
            className={styles['ai-btn']}
          >
            {item.name}
          </Button>
        ));
      }
      tmpDom.push(<Divider key={'divider'} type="vertical" />);
      dom.push([...tmpDom]);
    }
    // dom.push(
    //   <Select
    //     onMouseEnter={()=>{setAiDropVisible(true)}}
    //     onMouseLeave={()=>{setAiDropVisible(false)}}
    //     dropdownVisible={aiDropVisible}
    //     dropdownMatchSelectWidth={false}
    //     dropdownRender={() => <></>}
    //   >
    //     <Option value="1">Option 1</Option>
    //     <Option value="2">Option 2</Option>
    //     <Option value="3">Option 3</Option>
    //   </Select>,
    // );
    return dom;
    // return <Select />;
  };

  return (
    <>
      <DraggableContainer
        className={classnames(styles.databaseQuery)}
        callback={callback}
        showLine={showSearchResult}
        direction="row"
        volatileDom={{
          volatileRef: volatileRef,
          volatileIndex: 1,
        }}
      >
        <div className={styles.console}>
          <div className={styles.operatingArea}>
            <div className={styles.left}>{renderOptBtn()}</div>
            <div className={styles.right}>
              <span>dataSourceName: {windowTab.dataSourceName}</span>
              <span>database: {windowTab.databaseName}</span>
            </div>
          </div>
          <div className={styles.monacoEditor}>
            <MonacoEditor
              onSave={saveWindowTabTab}
              onChange={monacoEditorChange}
              id={windowTab.consoleId!}
              getEditor={getEditor}
            />
          </div>
        </div>
        <div
          ref={volatileRef}
          style={{ display: showSearchResult ? 'block' : 'none' }}
          className={styles.searchResult}
        >
          <LoadingContent data={manageResultDataList} handleEmpty>
            <SearchResult manageResultDataList={manageResultDataList} />
          </LoadingContent>
        </div>
      </DraggableContainer>

      {modalConfig?.open && (
        <Modal
          title={modalConfig.title}
          open={modalConfig.open}
          onOk={modalConfig.handleOk}
          onCancel={modalConfig.handleCancel}
        >
          {modalConfig.content}
        </Modal>
      )}
    </>
  );
}
