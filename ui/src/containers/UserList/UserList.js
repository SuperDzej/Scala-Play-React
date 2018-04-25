import React, {Component} from 'react'
import UserService from "../../services/User";

import { Table, Divider , Icon } from 'antd';

const columns = [{
  title: 'Id',
  dataIndex: 'id',
  key: 'id', 
  width: 50,
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
  render: (record) => (
    <span>
      <a href="javascript:;">View</a>
      <Divider type="vertical" />
      <a href="javascript:;">Delete</a>
      <Divider type="vertical" />
      <a href="javascript:;">Edit</a>
    </span>
  )
}];

class UserList extends Component {
  constructor(props) {
    super(props)
    this.state = { 
      data: [],
      pagination: {
        pageSize: 10
      },
      loading: false,
    };
  }

  handleTableChange = (pagination, filters, sorter) => {
    const pager = { ...this.state.pagination };
    pager.current = pagination.current;
    this.setState({
      pagination: pager,
    });

    /*this.fetch({
      results: pagination.pageSize,
      page: pagination.current,
      sortField: sorter.field,
      sortOrder: sorter.order,
      ...filters,
    });*/
  }

  fetchTotal = (params = {}) => {
    console.log('params:', params);
    this.setState({ loading: true });
    console.log('T', this.state.pagination.total)
    UserService.getTotal((response) => {
      const pagination = { ...this.state.pagination };
      console.log('Total', response)
      pagination.total = response;
      this.setState({ pagination })
    })
    /*reqwest({
      url: 'https://randomuser.me/api',
      method: 'get',
      data: {
        results: 10,
        ...params,
      },
      type: 'json',
    }).then((data) => {
      const pagination = { ...this.state.pagination };
      // Read total count from server
      // pagination.total = data.totalCount;
      pagination.total = 200;
      this.setState({
        loading: false,
        data: data.results,
        pagination,
      });
    });*/
  }

  onInputChange = (e) => {
    this.setState({ searchText: e.target.value });
  }

  async componentDidMount() {
    /*UserService.getWithOffsetAndLimit(0, 10, users => {
      console.log(users)
      this.setState({ data: users.data });
    });*/
    this.fetchTotal()
  }

  render() {
    return (
      <Table rowKey="id" dataSource={this.state.data} columns={columns} scroll={{ x: 1300 }} />
    )
  }
}

export default UserList
