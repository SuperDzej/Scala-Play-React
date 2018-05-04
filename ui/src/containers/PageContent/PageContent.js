import React, {Component} from 'react';
import { Layout } from 'antd';
import UserList from '../UserList/UserList'
import {
  Route
} from 'react-router-dom';
import ProtectedHome from '../ProtectedHome/ProtectedHome';
import UserDetail from '../UserDetail/UserDetail';
import LeaveCalendar from '../LeaveCalendar/LeaveCalendar'

const { Content } = Layout;
const { Footer } = Layout;

class PageContent extends Component {
  constructor(props) {
    super(props);
    this.state = { users: [] };
  }

  render() {
    return (
      <Layout style={{ padding: '0 24px 24px' }}>
        <div style={{ 'marginTop': '10px' }}></div>
        <Content style={{ background: '#fff', padding: 24, margin: 0, minHeight: 280 }}>

          <Route path='/' exact component={ProtectedHome} />
          <Route path='/users' exact component={UserList} />
          <Route path='/users/:id/:type' component={UserDetail} />
          <Route path="/leaves" exact component={LeaveCalendar} />
          <Route path='/profile' component={UserDetail} />
        </Content>
        <Footer style={{ textAlign: 'center' }}>
          Ant Colony ©2018 Created by Ant Dzej
        </Footer>
      </Layout>)
  }
}

export default PageContent
