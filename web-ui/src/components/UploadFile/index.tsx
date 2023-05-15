import React, {useContext} from 'react';
import styles from './index.less';
import classnames from 'classnames';
import {
  Modal,
  UploadProps, message
} from 'antd';
import Dragger from "antd/es/upload/Dragger";
import {FileManagerContext} from "@/context/file-manager";
import i18n from "@/i18n";

function VisiblyUploadFile() {
  const {model, setIsUploaderShow} = useContext(FileManagerContext);
  const isUploaderShow = model.isUploaderShow;
  const handleOk = () => {
    setIsUploaderShow(false);
  };
  const handleCancel = () => {
    setIsUploaderShow(false);
  };

  const props: UploadProps = {
    name: 'data',
    multiple: false,
    action: 'http://localhost:8080/csv/uploadTable',
    onChange(info) {
      const {status} = info.file;
      if (status === 'done') {
        message.success(`${info.file.name}` + i18n('file.upload.success')).then(() => {
        });
      } else if (status === 'error') {
        message.error(`${info.file.name}` + i18n('file.upload.error')).then(() => {
        });
      }
    }
  };

  return <div className={classnames(styles.box)}>
    <Modal
      title={i18n('file.upload.model.title')}
      open={isUploaderShow}
      onOk={handleOk}
      onCancel={handleCancel}
      footer={false}
      width={560}
    >
      <div>
        <Dragger {...props}>
          <p className="ant-upload-text">{i18n('file.upload.model.text')}</p>
        </Dragger>
      </div>
    </Modal>
  </div>

}

export default function UploadFile() {
  return <VisiblyUploadFile/>
}
