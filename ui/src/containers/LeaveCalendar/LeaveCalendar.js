import React, {Component} from 'react'
import { Link } from 'react-router-dom'
import { Calendar, Alert, Button, Row, Col } from 'antd';
import moment from 'moment';

import './LeaveCalendar.css'

class LeaveCalendar extends Component {
  state = {
    value: moment(),
    selectedValue: moment()
  }

  onSelect = (value) => {
    this.setState({
      value,
      selectedValue: value,
    });
  }

  onPanelChange = (value) => {
    this.setState({ value });
  }

  disabledDate = (date) => {
    return false;
  }

  dateCellRender(value) {
    // console.log(value)
    /*const listData = getListData(value);
    return (
      <ul className='events'>
        {
          listData.map(item => (
            <li key={item.content}>
              <Badge status={item.type} text={item.content} />
            </li>
          ))
        }
      </ul>
    );*/
  }

  componentDidMount() {

  }

  render() {
    const { value } = this.state;
    return (
      <div>
        <Row>
          <Row className='totalLeave'>
            <Alert message='There are total 20 active leaves' type='info' />
          </Row>  
          <Row>
            <Col span={12}>
              <Button className='requestLeaveBtn' type='primary'>
                <Link to='/leaves/request'>Request leave</Link>
              </Button>
            </Col>
            <Col span={12}>
              <Button type='primary'>
                <Link to='/leaves/evaluate'>Evaluate leaves</Link>
              </Button>
            </Col>
          </Row>
        </Row>
        <Calendar value={value} onSelect={this.onSelect} onPanelChange={this.onPanelChange} 
          dateCellRender={this.dateCellRender}  disabledDate={this.disabledDate} />
      </div>
    );
  }
}

export default LeaveCalendar 
