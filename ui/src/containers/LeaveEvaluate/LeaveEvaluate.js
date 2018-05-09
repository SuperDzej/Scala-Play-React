import React, {Component} from 'react'
import { Button, Row, Col, Input, Divider } from 'antd';

import Leave from '../../services/Leave'

import './LeaveEvaluate.css'

class LeaveEvaluate extends Component {

  constructor(props) {
    super(props)

    this.removeLeaveFromList = this.removeLeaveFromList.bind(this)
  }
  state = {
    size: 'default',
    pendingLeaves: []
  }

  componentDidMount() {
    Leave.getPending()
      .then((response) => {
        this.setState({ pendingLeaves: response.data })
      })
  }

  removeLeaveFromList(id) {
    var pendingLeaves = this.state.pendingLeaves.filter(leave => leave.id !== id)
    this.setState({ pendingLeaves})
  }

  approveLeave(leave) {
    this.removeLeaveFromList(leave.id)
    console.log('Approved', leave)
  }

  rejectLeave(leave) {
    this.removeLeaveFromList(leave.id)
    console.log('Rejected', leave)
  }
  
  render() {
    var options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
    var leaveColViews = this.state.pendingLeaves.map(leave => {
      return (
          <Col span={12} key={leave.id}>
            <div  className='card'>
              <Row className='row'>
                <Col span={4} className='colDesc'>Type:</Col>
                <Col span={8}>{leave.category.name}</Col>
                <Col span={4} className='colDesc'>User:</Col>
                <Col span={8}>{leave.user.email}</Col>
              </Row>
              <Row className='row'>
                <Col span={4} className='colDesc'>Reason: </Col>
                <Col span={20}>{leave.reason}</Col>
              </Row>
              <Row className='row'>
                <Col span={4} className='colDesc'>From: </Col>
                <Col span={20}>{new Date(leave.startDate).toLocaleDateString('en-US',options)} </Col>
              </Row>
              <Row className='row'>
                <Col span={4} className='colDesc'>To: </Col>
                <Col span={20}>{new Date(leave.endDate).toLocaleDateString('en-US',options)} </Col>
              </Row>
              <Divider />
              <Row> 
                <Col span={4} className='colDesc'>Comment: </Col>
                <Col span={20} className='colDesc'>
                  <Input className='reason' placeholder='Enter evaluation comment' value={leave.evaluationComment} required />
                </Col>
                
              </Row>
              <Row className='row'>
                <Col span={12}><Button type='default' onClick={() => this.approveLeave(leave)}>Approve</Button></Col>
                <Col span={12}><Button type='danger' onClick={() => this.rejectLeave(leave)}>Reject</Button></Col>
              </Row> 
            </div>
          </Col>
      )
    })

    return (
      <div>
        <p> Requests to evaluate </p>
        <Row>
          {leaveColViews}
        </Row>
      </div>
    );
  }
}

export default LeaveEvaluate 
