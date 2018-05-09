import React, {Component} from 'react'
import { message, DatePicker, Input, Button } from 'antd';

import LeaveCategory from '../../services/LeaveCategory'
import Leave from '../../services/Leave'
import User from '../../services/User'
import DynamicSelect from '../../components/DynamicSelect'

import './LeaveRequest.css'

const { RangePicker } = DatePicker

class LeaveRequest extends Component {
  constructor(props) {
    super(props)

    this.handleSubmit = this.handleSubmit.bind(this)
    this.onCalendarChange = this.onCalendarChange.bind(this)
    this.handleTypeChange = this.handleTypeChange.bind(this)
    this.reasonChange = this.reasonChange.bind(this)
  }

  state = {
    size: 'default',
    categories: [],
    request: {
      category: '',
      description: '',
      startDate: '',
      endDate:''
    }
  }

  onCalendarChange(value) {
    if(value.length === 2) {
      var request = {...this.state.request}
      request.startDate = value[0]
      request.endDate = value[1]
      this.setState({ request })
    }
  }

  handleTypeChange(categoryTypeId) {
    var request = {...this.state.request}
    request.category = `${categoryTypeId}`;
    this.setState({request})
  }

  reasonChange(event) {
    var request = {...this.state.request}
    request.description = event.target.value
    this.setState({ request })
  }

  checkSubmit(obj) {
    for (var key in obj) {
      if (obj[key] === null || obj[key] === '')
          return false;
    }

    return true;
  }

  handleSubmit(event) {
    var isValid = this.checkSubmit(this.state.request)
    if(isValid) {
      Leave.post(this.state.request)
      .then((response) => {
        console.log(response)
        User.postLeaves(null, [response])
          .then((response) => {
            message.success('Leave successfully requested')
          }) 
      })
    } else {
      message.error('Please fill all fields');
    }
  }

  componentDidMount() {
    LeaveCategory.get()
      .then((response) => {
        console.log(response)
        this.setState({ categories: response.data })
      })
  }

  render() {

    return (
      <form>
          <div className='divField'>
            <span className='fieldDescription'>Pick date</span> 
            <RangePicker size={this.state.size} onCalendarChange={this.onCalendarChange.bind(this)} />
          </div>
          <div className='divField'>
            <span className='fieldDescription'>Type</span> 
            <DynamicSelect data={this.state.categories}  handleChange={this.handleTypeChange.bind(this)}/>
          </div>
          <div className='divField'>
            <span className='fieldDescription'>Reason</span> 
            <Input className='reason' placeholder='Enter leave reason' value={this.state.request.description} onChange={this.reasonChange.bind(this)} required />
          </div>
          <div className='requestBtnDiv'>
            <Button className='requestBtn' type='primary' onClick={this.handleSubmit}>Request</Button>
          </div>
      </form>

    );
  }
}

export default LeaveRequest 
/*
      
*/
