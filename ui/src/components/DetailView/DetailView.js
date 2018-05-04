import React, {Component} from 'react'
import {Input} from 'antd'

import Random from '../../utils/Random'

import './DetailView.css'

class DetailView extends Component {
  getFieldDescription(field) {
    if(!field) return

    var camelCaseSplit = field.replace(/([A-Z]+)*([A-Z][a-z])/g, "$1 $2")
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
          // To avoid duplicate keys
          var random = Random.get(10000)
          if (typeof obj[prop] === "object" && obj[prop] !== null) {
            // To avoid adding array index in here      
            var hasElements = Object.keys(obj[prop]).length > 0 || 
              (obj[prop] instanceof Array && obj[prop].length > 0)

            if(isNaN(parseInt(prop, 10)) && hasElements) {
              // To display top object only once
              this.topObjects.push(prop) 

              var topObject = this.topObjects[this.topObjects.length - 1]
              var topObjectView = (<div className='objectDescription' key={topObject + random}> 
                  {this.getFieldDescription(topObject)}
                </div>)
              this.fields.push(topObjectView)
            }

            this.eachRecursive(obj[prop])
          }
          else {
            console.log(this.props.readOnly)
            var value = 
              <p key={this.topObjects.length + prop + obj[prop] + random} className='detailFieldP'> 
                <span className='fieldDescription'>{this.getFieldDescription(prop)}: </span> 
                <Input placeholder="Basic usage" value={obj[prop]} readOnly={this.props.readOnly} onChange={this.props.editField} />
              </p>

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
