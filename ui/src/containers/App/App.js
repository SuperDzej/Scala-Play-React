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
import ErrorView from '../../components/ErrorView'

import './App.css';
import 'antd/dist/antd.css';

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/404' component={ErrorView}/> {/* If route is not 404, layout will be rendered */}
          <Route path='/500' component={ErrorView} />
          <Route render={ (props) => (
            <Layout style={{ minHeight: '100vh' }}>
            <PageHeader />
            <Switch>
              <Route path='/login' component={Login} />
              <Route path='/signup' component={Signup} />
              <ProtectedRoute path='/' component={ProtectedContent} />
            </Switch>
          </Layout>
          ) } />
        </Switch>
      </Router>
    );
  }
}

export default App;
