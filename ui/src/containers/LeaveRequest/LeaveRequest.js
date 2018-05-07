import React, {Component} from 'react'
import { DatePicker, Input, Select } from 'antd';

import './LeaveRequest.css'

const { RangePicker } = DatePicker
const Option = Select.Option

class LeaveRequest extends Component {
  state = {
    size: 'default'
  }

  onCalendarChange(value) {
    console.log(value)
  }

  handleTypeChange() {

  }

  render() {
    return (
      <div>
        <div className="divField">
          <span className="fieldDescription">Pick date</span> 
          <RangePicker size={this.state.size} onCalendarChange={this.onCalendarChange.bind(this)} />
        </div>
        <div className="divField">
          <span className="fieldDescription">Type</span> 
          <Select defaultValue="dayOff" style={{ width: 120 }} onChange={this.handleTypeChange}>
            <Option value="vacation">Vacation</Option>
            <Option value="dayOff">Day Off</Option>
            <Option value="sick">Sick</Option>
          </Select>
        </div>
        <div className="divField">
          <span className="fieldDescription">Reason</span> 
          <Input />
        </div>
      </div>
    );
  }
}

export default LeaveRequest 
