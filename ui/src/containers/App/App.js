import React, {Component} from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route
} from 'react-router-dom';

import { Layout } from 'antd';

import ProtectedContent from '../ProtectedContent/ProtectedContent'
import Login from '../Login/Login'
import Signup from '../Signup/Signup'
import ProtectedRoute from '../../components/ProtectedRoute'
import PageHeader from '../PageHeader/Header'

import './App.css';
import 'antd/dist/antd.css';

class App extends Component {
  render() {
    return (
      <Router>
        <Layout  style={{ minHeight: '100vh' }}>
          <PageHeader />
          <Switch>
            <Route path="/login" component={Login} />
            <Route path="/signup" component={Signup} />
            <ProtectedRoute path="/" component={ProtectedContent} />
          </Switch>
        </Layout>
      </Router>
    );
  }
}

export default App;
