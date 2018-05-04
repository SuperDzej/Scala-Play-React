import React, {Component} from 'react'
import { Button, Popconfirm } from 'antd'
import { Link } from 'react-router-dom';
import UserService from '../../services/User'
import DetailView from '../../components/DetailView/DetailView'

import './UserDetail.css'

class UserDetail extends Component {
  constructor(props) {
    super(props)
    this.state = {
      user: {},
      readOnly: true,
      changeResourceView: null
    }

    console.log(this.props.match.params.type)
  }

  getUserById() {
    var userId = this.props.match.params.id
    UserService.getById(userId)
      .then((response) => {
        console.log('User: ', response)
        this.setState({ user: response })
        this.changeResourceView()
      })
  }

  deleteUser(e) {
    var userId = this.props.match.params.id
    UserService.deleteResource(userId)
      .then((response) => {
        console.log(response)
      })
  }

  cancelDelete(e) {
    console.log(e)
  }

  editUser(e) {
    console.log('Edit: ', e)
  }

  changeResourceView() {
    var type = this.props.match.params.type
    var changeResource = null
    if(Object.keys(this.state.user).length > 0) {
      if(type === 'delete') {
        changeResource = 
          <span>
              <Popconfirm title="Are you sure you want to delete this user?" onConfirm={this.deleteUser} 
                onCancel={this.cancelDelete} okText="Yes" cancelText="No">
                <Button className="btnMargin10" type="danger">Delete</Button>
              </Popconfirm>
          </span>
      }
      else if(type === 'edit') {
        this.setState({ readOnly: false })
        changeResource = <span>
          <Button className="btnMargin10" type="default" onClick={this.editUser.bind(this)}>Edit</Button>
        </span>
      }
    }
    this.setState({ changeResourceView: changeResource })
  }

  editChange(e) {
    console.log(e)
  }

  async componentDidMount() {
    this.getUserById()
  }

  render() {
    return (
      <div>
        <DetailView data={this.state.user} readOnly={this.state.readOnly} editField={this.editChange.bind(this)} />
        <Button className="btnMargin10" type="primary">
          <Link to="/users">Back</Link>
        </Button>
        {this.state.changeResourceView}
      </div>
    )
  }
}

export default UserDetail