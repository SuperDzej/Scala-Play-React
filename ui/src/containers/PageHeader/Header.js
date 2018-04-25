import React, {Component} from 'react';
import { Layout, Menu } from 'antd';

const { Header } = Layout;

class PageHeader extends Component {
  constructor(props) {
    super(props);
    this.state = { users: [] };
  }

  async componentDidMount() {

  }

  render() {
    return (
      <Header className="header">
        <Menu
          theme="dark"
          mode="horizontal"
          defaultSelectedKeys={['2']}
          style={{ lineHeight: '64px' }}
        >
        </Menu>
      </Header>
    )
  }
}

export default PageHeader;
