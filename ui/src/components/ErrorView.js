import React, {Component} from 'react'


class ErrorView extends Component {
  render() {
    var path = window.location.pathname
    var view = null;
    if(path === '/404') {
      view = <p>Not found</p>
    } else if(path === '/500') {
      view = <p>Something went wrong on our side, sorry for inconvinence</p>
    } else {
      view = <p>Error happened</p>
    }

    return (
      <div>
        {view}
      </div>)
  }
}

export default ErrorView
