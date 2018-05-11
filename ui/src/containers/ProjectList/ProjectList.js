import React, {Component} from 'react'
import moment from 'moment'
import {Link} from 'react-router-dom'
import {Row, Col} from 'antd'

import Project from '../../services/Project'

import ProjectCol from '../../components/ProjectCol'

import './ProjectList.css'

class ProjectList extends Component {
  state = {
    projects: []
  }

  componentDidMount() {
    Project.get()
      .then((response) => {
        console.log(response.data)
        this.setState({ projects: response.data })
      })
  }

  getDateFormat(date) {
    return moment(date).format('DD-MM-YYYY')
  }

  render() {
    
    var projectCols = this.state.projects.map((project) =>  
      <Col span={12} key={project.id}>
        <Row className="card-box">
          <Col>
            <img className='projectImg' 
              src='https://newsignature.com/wp-content/uploads/2017/02/project-management-1024x512.png' alt='Project' />
          </Col>
          <Col className='marginTB20'>
            {}
          </Col>
          <Col> 
            <p>{project.description}</p> 
          </Col>
          <Col>
            <span className='fieldDesc'>Url: </span>
            <Link to={project.url}>{project.url}</Link>
          </Col>
          <Col span={12}>
            <span className='fieldDesc'> From: </span>
            <span>{this.getDateFormat(project.startDate)}</span>
          </Col>
          <Col span={12}>
            <span className='fieldDesc'> To: </span>
            <span>{this.getDateFormat(project.endDate)}</span>
          </Col>
        </Row>
      </Col>
    )

    return (
      <div>
       <h2>Projects</h2>
       <Row gutter={16}>
        {projectCols}
       </Row>
      </div>
    )
  }
}


export default ProjectList
