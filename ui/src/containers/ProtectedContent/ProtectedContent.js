import React, {Component} from 'react'

import { Layout } from 'antd';

import PageHeader from '../PageHeader/Header'
import Sidebar from '../Sidebar/Sidebar';
import PageContent from '../PageContent/PageContent';

class ProtectedContent extends Component {
  render() {
    return (
      <Layout>
        <PageHeader />
        <Layout>  
          <Sidebar />
          <PageContent />
        </Layout>
      </Layout>
    )
  }
}

export default ProtectedContent
