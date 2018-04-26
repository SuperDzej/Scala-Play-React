import React, {Component} from 'react'
import UserService from "../../services/User";
import {
  Link
} from "react-router-dom";

import { Table, Divider } from 'antd';

const columns = [{
  title: 'Id',
  dataIndex: 'id',
  key: 'id', 
  width: 100,
  fixed: 'left'
},{
  title: 'First Name',
  dataIndex: 'firstName'
}, {
  title: 'Last Name',
  dataIndex: 'lastName',
  key: 'lastName',
}, {
  title: 'Email',
  dataIndex: 'email',
  key: 'email'
}, {
  title: 'Username',
  dataIndex: 'username',
  key: 'username'
}, {
  title: 'Action',
  key: 'operation',
  render: (record) => {
      var userId = record.id

      return <span>
        <Link to={ `/users/${userId}` }>
          View
        </Link>
        <Divider type="vertical" />
        <Link to={ `/users/${userId}/delete` }>
          Delete
        </Link>
        <Divider type="vertical" />
        <Link to={ `/users/${userId}/edit` }>
          Edit
        </Link>
      </span>
  }
}];

class UserList extends Component {
  constructor(props) {
    super(props)
    this.state = { 
      data: [],
      pageSize: 10,
      pagination: {
        pageSizeOptions: [ '10', '20', '50'],
        showSizeChanger: true,
        onShowSizeChange: this.onShowSizeChange.bind(this),
      },
      loading: false,
    }
  }

  onShowSizeChange(current, pageSize) {
    console.log('Page size change' , current, pageSize);
    console.log('State ', this.state)
    const pager = { ...this.state.pagination };
    pager.pageSize = pageSize
    this.setState({
      pagination: pager,
      pageSize: pageSize
    })
  }

  handleTableChange = (pagination, filters, sorter) => {
    const pager = { ...this.state.pagination };
    pager.current = pagination.current;
    this.setState({
      pagination: pager,
    });

    this.fetch();
  }

  getOffset(page) {
    var offset = page ? ((page - 1) * this.state.pageSize) : 0
    return offset
  }

  fetch = (params = {}) => {
    this.setState({ loading: true });
    UserService.getTotal((response) => {
      const pagination = { ...this.state.pagination };
      pagination.total = response;
      var offset = this.getOffset(this.state.pagination.current)
      UserService.getWithOffsetAndLimit(offset, this.state.pageSize, users => {
        this.setState({ 
          loading: false,
          data: users.data,
          pagination
        });
      });
    })
  }

  async componentDidMount() {
    this.fetch()
  }

  render() {
    return (
      <Table rowKey="id" 
        dataSource={this.state.data} 
        columns={columns} 
        pagination={this.state.pagination}
        loading={this.state.loading}
        onChange={this.handleTableChange.bind(this)}
        scroll={{ x: 1300 }} />
    )
  }
}

export default UserList
