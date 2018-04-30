import React, {Component} from 'react';
import { Layout } from 'antd';

import AuthButton from '../AuthButton/AuthButton'

import './Header.css'

const { Header } = Layout;

class PageHeader extends Component {
  render() {
    return (
      <Header className="header">
        <div className="logo" />
        <AuthButton />
      </Header>
    )
  }
}

export default PageHeader;
