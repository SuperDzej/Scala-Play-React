import React, {Component} from 'react';

import {
  Redirect
} from 'react-router-dom';
import UserAuth from '../../services/UserAuth'
import LoginForm from '../../components/LoginForm'

import './Login.css'
class Login extends Component {
  state = {
    redirectToReferrer: false
  };

  login = () => {
    UserAuth.authenticate("email@gmail.com", "123az45AZ!", () => {
      this.setState({ redirectToReferrer: true });
    });
  };

  render() {
    const { from } = (this.props.location && this.props.location.state) || { from: { pathname: "/" } };
    const { redirectToReferrer } = this.state;

    if (redirectToReferrer) {
      return <Redirect to={from} />;
    }

    return (
      <div className="container">
        <LoginForm login={this.login.bind(this)} />
      </div>
    );
  }
}

export default Login
