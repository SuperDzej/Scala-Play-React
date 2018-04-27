import React, {Component} from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route
} from 'react-router-dom';

import { Layout } from 'antd';

import Home from '../Home/Home'
import ProtectedContent from '../ProtectedContent/ProtectedContent'
import Login from '../Login/Login'
import ProtectedRoute from '../../components/ProtectedRoute'

import './App.css';
import 'antd/dist/antd.css';

class App extends Component {
  render() {
    return (
      <Router>
        <Layout  style={{ minHeight: '100vh' }}>
          <Switch>
            <Route path="/login" component={Login} />
            <Route path="/signup" component={Login} />
            <ProtectedRoute path="/" component={ProtectedContent} />
          </Switch>
        </Layout>
      </Router>
    );
  }
}

export default App;
