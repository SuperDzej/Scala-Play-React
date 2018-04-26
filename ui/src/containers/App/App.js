import React, {Component} from 'react';
import {
  BrowserRouter as Router,
  Route
} from 'react-router-dom';

import { Layout } from 'antd';

import Home from '../Home/Home'
import ProtectedContent from '../ProtectedContent/ProtectedContent'
// import AuthExample from '../Auth'
import Login from '../Login/Login'

import './App.css';
import 'antd/dist/antd.css';

class App extends Component {
  render() {
    return (
      <Router>
        <Layout  style={{ minHeight: '100vh' }}>
         <Route path="/home" exact component={Home} />
         <Route path="login" exact component={Login} />
         <Route path="/" component={ProtectedContent} />
        </Layout>
      </Router>
    );
  }
}

export default App;
