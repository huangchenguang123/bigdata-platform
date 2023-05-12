import React, {useContext} from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Button from '@/components/Button';
import {
  Modal,
  Form
} from 'antd';
import Dragger from "antd/es/upload/Dragger";
import {FileManagerContext} from "@/context/file-manager";

function VisiblyUploadFile() {
  const [form] = Form.useForm();
  const { model, setIsUploaderShow } = useContext(FileManagerContext);
  const isUploaderShow = model.isUploaderShow;
  const handleOk = () => {
    setIsUploaderShow(false);
  };

  return <div className={classnames(styles.box)}>
    <Modal
      title={"上传文件"}
      open={isUploaderShow}
      onOk={handleOk}
      footer={false}
      width={560}
    >
      <Form
        form={form}
        autoComplete="off"
        className={styles.form}
      >
        <div>
          <Dragger>
            <p className="ant-upload-drag-icon">
            </p>
            <p className="ant-upload-text">Click or drag file to this area to upload</p>
            <p className="ant-upload-hint">
              Support for a single or bulk upload. Strictly prohibit from uploading company data or other
              band files
            </p>
          </Dragger>
        </div>
        <div className={styles.formFooter}>
          <div className={styles.test}>
            {
              // !dataSourceId &&
              <Button
                className={styles.test}>
                上传
              </Button>
            }
          </div>
        </div>
      </Form>
    </Modal>
  </div >

}

export default function UploadFile() {
  return <VisiblyUploadFile />
}
