import React, {Component} from 'react'


import Project from '../../services/Project'

class ProjectList extends Component {

  componentDidMount() {
    Project.get()
      .then((response) => {
        console.log(response.data)
      })
  }

  render() {
    return (
      <div>
       <h2>Projects</h2>
      </div>
    )
  }
}


export default ProjectList
