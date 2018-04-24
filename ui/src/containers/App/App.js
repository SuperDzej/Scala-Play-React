import React, {Component} from 'react';
import {
  BrowserRouter as Router,
  Route,
  Link
} from 'react-router-dom';

import UserService from "../../services/User";

import reactLogo from '../../images/react.svg';
import playLogo from '../../images/play.svg';
import scalaLogo from '../../images/scala.png';

import './App.css';

const Tech = ({ match }) => {
  return <div>Current Route: {match.params.id}</div>
};

const UserList = users => (
  <ul>
    {console.log("Users: ", users.users)}
    {
      users.users.map(user =>
        <li key={user.id}>{user.id}-{user.firstName}-{user.lastName}</li>
      )
    }
  </ul>
)
    /*{
      users.map(user =>
        <li>{user.id}-{user.firstName}-{user.lastName}</li>
      )
    }*/

class App extends Component {
  constructor(props) {
    super(props);
    this.state = { users: [] };
  }

  async componentDidMount() {
    UserService.getWithOffsetAndLimit(0, 10, users => {
      console.log(users)
      this.setState({ users: users.data });
    });
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
        <div className="App">
          <h1>Welcome {this.state.email}!</h1>
          <nav>
            <Link to="1" >
              <img width="400" onClick={() => this.postData() } src={scalaLogo} alt="Scala Logo" />
            </Link>
            <Link to="2" >
              <img width="400" src={playLogo} alt="Play Framework Logo" />
            </Link>
            <Link to="3" >
              <img width="400" src={reactLogo} className="App-logo" alt="React Logo"/>
            </Link>
          </nav>
          <div>
            <UserList users={this.state.users} />
          </div>
          <Route path="/:id" component={Tech} />
        </div>
      </Router>
    );
  }
}
export default App;
