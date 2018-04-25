import React, {Component} from 'react';
import {
  BrowserRouter as Router
} from 'react-router-dom';

import { Layout } from 'antd';

import PageHeader from '../PageHeader/Header'
import Sidebar from '../Sidebar/Sidebar';
import PageContent from '../PageContent/PageContent';
import UserService from "../../services/User";

import './App.css';
import 'antd/dist/antd.css';

const Tech = ({ match }) => {
  return <div>Current Route: {match.params.id}</div>
};


class App extends Component {
  constructor(props) {
    super(props);
    this.state = { users: [] };
  }

  postData() {
    var data = {
      "firstName": "Name",
      "lastName": "Doe",
      "mobile": "+38765235625",
      "email": "name_doe@gmail.com",
      "username": "doe_name",
      "password": "123az45AZ!"
  	}

    UserService.post(data, response => {
      console.log(response);
    })
  }

  render() {
    return (
      <Router>
        <Layout  style={{ minHeight: '100vh' }}>
          <Sidebar />
          <Layout>  
            <PageHeader />
            <PageContent />
          </Layout>
        </Layout>
      </Router>
    );
  }
}

export default App;
/*<Link to="1" >
  <img width="400" onClick={() => this.postData() } src={scalaLogo} alt="Scala Logo" />
</Link>
<Link to="2" >
  <img width="400" src={playLogo} alt="Play Framework Logo" />
</Link>
<Link to="3" >
  <img width="400" src={reactLogo} className="App-logo" alt="React Logo"/>
</Link>*/
