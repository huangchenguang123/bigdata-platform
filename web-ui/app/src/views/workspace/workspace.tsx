import * as React from 'react'
import { Button, Input, Spin, Card } from 'antd'

import { withStore } from '@/core/store'

interface WorkSpaceProps extends PageProps, StoreProps {
  count: StoreStates['count']
  countAlias: StoreStates['count']
}

declare interface WorkSpaceState {
  resData: Partial<queryTestInfoUsingGET.Response>
  loading: boolean
  createWindowLoading: boolean
  asyncDispatchLoading: boolean
}

@withStore(['count', { countAlias: 'count' }])
export default class WorkSpace extends React.Component<WorkSpaceProps, WorkSpaceState> {
  // state 初始化
  state: WorkSpaceState = {
    resData: {},
    loading: false,
    createWindowLoading: false,
    asyncDispatchLoading: false,
  }

  // 构造函数
  constructor(props: WorkSpaceProps) {
    super(props)
  }

  componentDidMount(): void {
    console.log(this)
  }

  render(): JSX.Element {
    const { resData, loading} = this.state
    return (
        <div className="layout-padding">
        <Card title="Request Test" className="mb-16">
          <Spin spinning={loading}>
            <div className="mb-16">
              <Button type="primary" onClick={this.requestTest.bind(this)}>
                Request
              </Button>
            </div>
            <Input.TextArea value={JSON.stringify(resData)} autoSize />
          </Spin>
        </Card>
      </div>
    )
  }

  requestTest(): void {
    this.setState({ loading: true })
    $api
      .queryTestInfo({})
      .then((resData) => {
        this.setState({ resData })
      })
      .finally(() => this.setState({ loading: false }))
  }

}
