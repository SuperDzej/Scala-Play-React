import React, {Component} from 'react'
import { Calendar, Alert, Button } from 'antd';
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
      <ul className="events">
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

  render() {
    const { value } = this.state;
    return (
      <div>
        <div className="leaveInfo">
          <Alert className="totalLeave" message="There are total 20 active leaves" type="info" />
          <Button className="requestLeaveBtn" type="primary">Request leave</Button>
        </div>
        <Calendar value={value} onSelect={this.onSelect} onPanelChange={this.onPanelChange} 
          dateCellRender={this.dateCellRender}  disabledDate={this.disabledDate} />
      </div>
    );
  }
}

export default LeaveCalendar 