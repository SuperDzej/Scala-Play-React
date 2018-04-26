import React , {Component} from 'react'
import { Layout, Menu, Icon } from 'antd';

import {
  Link
} from "react-router-dom";

import './Sidebar.css';
import 'antd/dist/antd.css';

const { Sider } = Layout;

class Sidebar extends Component {
  state = {
    collapsed: false,
  };

  onCollapse = (collapsed) => {
    this.setState({ collapsed });
  }

  render() {
    return (
      <Sider
          breakpoint="md"
          collapsedWidth="0"
          collapsible
          collapsed={this.state.collapsed}
          onCollapse={this.onCollapse}
        >
          <Menu theme="dark" defaultSelectedKeys={['15']} mode="inline">
            <Menu.Item key="15">
                <Link to="/users">            
                  <Icon type="user" /> 
                  <span>Users</span>
                </Link>
            </Menu.Item>
            <Menu.Item key="16">
              <Link to="/leaves">            
                <Icon type="calendar" />
                <span>Leaves</span>
              </Link>
            </Menu.Item>
            <Menu.Item key="17">
              <Link to="/projects">
                <Icon type="code" />
                <span>Projects</span>
              </Link>
            </Menu.Item>
          </Menu>
        </Sider>
    )
  }
}

export default Sidebar
