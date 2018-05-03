import React, {Component} from 'react'
import Random from '../utils/Random'

class DetailView extends Component {
  getFieldDescription(field) {
    if(!field) return

    var camelCaseSplit = field.replace('(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])')
    var textWithUpper = camelCaseSplit[0].toUpperCase() + camelCaseSplit.substring(1, camelCaseSplit.length)
    return textWithUpper
  }

  fields = []
  topObjects = []

  // I go trough all objects recursively and get main fields in topObjects array to display them before fields of that object
  eachRecursive(obj)
  {
      for (var prop in obj)
      {
          if (typeof obj[prop] === "object" && obj[prop] !== null) {      
            if(isNaN(parseInt(prop, 10))) {
              this.topObjects.push(prop) 
            }

            this.eachRecursive(obj[prop])
          }
          else {
            // To avoid duplicate keys
            var random = Random.get(10000)
            var value = 
              <p key={this.topObjects.length + prop + obj[prop] + random}> 
                <span className='fieldDescription'>{this.getFieldDescription(prop)}: </span> 
                <span>{obj[prop]}</span>
              </p>

            // To display top object only once
            if(this.topObjects.length > 0) {
              var topObject = this.topObjects[this.topObjects.length - 1]
              value = <div key={random + topObject}> 
                <span className='objectDescription'>{this.getFieldDescription(topObject)}: </span>
                {value}
              </div>
            } 
            this.fields.push(value)
          }
      }
  }

  getFieldsView(data) {
    this.eachRecursive(data)
  }

  render() {
    if(Object.keys(this.props.data).length < 1) {
      return ('Waiting for data')
    }

    this.getFieldsView(this.props.data)

    return (
      <div>
        {this.fields}
      </div>
    )
  }
}

export default DetailView
