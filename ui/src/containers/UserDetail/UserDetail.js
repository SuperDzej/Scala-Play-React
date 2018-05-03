import React, {Component} from 'react'
import {Button} from 'antd'
import UserService from '../../services/User'
import DetailView from '../../components/DetailView'

import './UserDetail.css'

class UserDetail extends Component {
  constructor(props) {
    super(props)
    this.state = {
      user: {}
    }
    console.log(this.props.match.params.type)
  }

  getUserById() {
    var userId = this.props.match.params.id
    UserService.getById(userId)
      .then((response) => {
        this.setState({ user: response })
      })
  }

  deleteUser() {
    var userId = this.props.match.params.id
    UserService.deleteResource(userId)
      .then((response) => {
        console.log(response)
      })
  }

  async componentDidMount() {
    this.getUserById()
  }

  render() {
    var type = this.props.match.params.type
    var deleteResource = null
    if(type === 'delete' && Object.keys(this.state.user).length > 0) {
        deleteResource = 
        <span>
          <Button type="primary">Back</Button>
          <Button type="danger" onClick={this.deleteUser}>Delete</Button>
        </span>
    }

    return (
      <div>
        <DetailView data={this.state.user} />
        {deleteResource}
      </div>
    )
  }
}

export default UserDetail
