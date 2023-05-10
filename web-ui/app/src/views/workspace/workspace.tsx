import * as React from 'react'
import {Button, Card, message, Upload, UploadProps} from 'antd'

import {UploadOutlined} from "@ant-design/icons";

export default class WorkSpace extends React.Component {

    componentDidMount(): void {
        console.log(this)
    }

    render(): JSX.Element {
        return (
            <div className="layout-padding">
                <Card title="Request Test" className="mb-16">
                    <div className="mb-16">
                        <Upload {...props}>
                            <Button icon={<UploadOutlined/>}>Click to Upload</Button>
                        </Upload>
                    </div>
                </Card>
            </div>
        )
    }

}

const props: UploadProps = {

    name: 'data',
    action: 'http://localhost:8080/csv/uploadTable',

    onChange(info) {
        if (info.file.status !== 'uploading') {
            console.log(info.file, info.fileList);
        }
        if (info.file.status === 'done') {
            message.success(`${info.file.name} file uploaded successfully`);
        } else if (info.file.status === 'error') {
            message.error(`${info.file.name} file upload failed.`);
        }
    }

};
