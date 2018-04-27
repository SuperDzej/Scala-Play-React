import React from "react";
import {
  withRouter,
  Link
} from "react-router-dom";
import { Menu } from 'antd';
import UserAuth from '../../services/UserAuth'

const AuthButton = withRouter(
  ({ history }) =>
    UserAuth.isAuthenticated() ? (
      <Menu
      theme="dark"
      mode="horizontal"
      defaultSelectedKeys={['1']}
      style={{ 
        lineHeight: '64px',
        display: 'flex',
        justifyContent: 'flex-end' 
      }}
    >
        <Menu.Item key="3" style={{ textAlign: 'right' }}>
          Welcome!{" "}
          <Link onClick={() => {
            UserAuth.signout(() => history.push("/"));
          }}
          >Sign out</Link>
        </Menu.Item>
      </Menu>
    ) : (
      <Menu
          theme="dark"
          mode="horizontal"
          defaultSelectedKeys={['1']}
          style={{ 
            lineHeight: '64px',
            display: 'flex',
            justifyContent: 'flex-end' 
          }}
        >
          <Menu.Item key="1"><Link to="/signup">Sign up</Link></Menu.Item>
          <Menu.Item key="2"><Link to="/login">Sign in</Link></Menu.Item>
      </Menu>
    )
);

export default AuthButton
