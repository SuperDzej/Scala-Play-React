import React, {Component} from 'react';
import { Layout, Menu } from 'antd';
import {
  Link
} from "react-router-dom";
import './Header.css'

const { Header } = Layout;

class PageHeader extends Component {
  render() {
    return (
      <Header className="header">
        <div className="logo" />
        <Menu
          theme="dark"
          mode="horizontal"
          defaultSelectedKeys={['1']}
          style={{ 
            lineHeight: '64px',
            display: 'flex',
            justifyContent: 'flex-end' 
          }}
        >
          <Menu.Item key="1"><Link to="/signup">Sign up</Link></Menu.Item>
          <Menu.Item key="2"><Link to="/login">Sign in</Link></Menu.Item>
          <Menu.Item key="3" style={{ textAlign: 'right' }}><Link to="/logout">Logout</Link></Menu.Item>
        </Menu>
      </Header>
    )
  }
}

export default PageHeader;
