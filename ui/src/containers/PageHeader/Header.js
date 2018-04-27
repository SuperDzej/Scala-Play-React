import React, {Component} from 'react';
import { Layout, Menu } from 'antd';
import {
  Link
} from "react-router-dom";
import AuthButton from '../AuthButton/AuthButton'


import './Header.css'

const { Header } = Layout;

class PageHeader extends Component {
  render() {
    return (
      <Header className="header">
        <div className="logo" />
        
      </Header>
    )
  }
}

export default PageHeader;
