import React, {Component} from 'react'
import { message, Button, Row, Col } from 'antd';

import Leave from '../../services/Leave'

import './LeaveEvaluate.css'

class LeaveEvaluate extends Component {
  state = {
    size: 'default',
    pendingLeaves: []
  }

  componentDidMount() {
    Leave.getPending()
      .then((response) => {
        console.log(response)
        this.setState({ pendingLeaves: response.data })
      })
  }

  
  render() {
    var options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
    var leaveColViews = this.state.pendingLeaves.map(leave => {
      return (
          <Col span={12} key={leave.id}>
            <div  className='card'>
              <Row className='row'>
                <Col span={4} className='colDesc'>Type:</Col>
                <Col span={20}>{leave.category}</Col>
              </Row>
              <Row className='row'>
                <Col span={4} className='colDesc'>Reason: </Col>
                <Col span={20}>{leave.description}</Col>
              </Row>
              <Row className='row'>
                <Col span={4} className='colDesc'>From: </Col>
                <Col span={20}>{new Date(leave.startDate).toLocaleDateString("en-US",options)} </Col>
              </Row>
              <Row className='row'>
                <Col span={4} className='colDesc'>To: </Col>
                <Col span={20}>{new Date(leave.endDate).toLocaleDateString("en-US",options)} </Col>
              </Row>
              <Row className='row'>
                <Col span={12}><Button type='default'>Approve</Button></Col>
                <Col span={12}><Button type='danger'>Reject</Button></Col>
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
