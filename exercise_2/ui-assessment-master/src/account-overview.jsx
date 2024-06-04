import React from 'react';
import { faUpload, faInfoCircle } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';
import './account-overview.css';
import PropTypes from 'prop-types';
import Container from './components/Container';
import Metric from './components/Metric';
import Icon from './components/Icon';
import Contact from './components/Contact';

const Header = styled.header`
    display: flex;
    padding-bottom: 40px;
    @media(max-width: 600px) {
      flex-direction: column;
    }
  `

const Title = styled.div`
    flex: 50%;
    color: #555253;
    font-weight: 500;
    font-size: 28px;
  `

const Sales = styled.div`
    padding: 8px;
    color: #333333;
    font-size: 18px;
  `

const SalesIcons = styled.div`
  column-count: 2
`

const Metrics = styled.div`
    display: flex;
    width: 100%;
    @media(max-width: 600px) {
      flex-direction: column;
    }
  `

export const AccountOverview = ({ data }) => {

  return (

    <div className="AccountOverview">
      <Header>
        <Title>Account Overview</Title>
        <Contact name={data.supportContact.name} email={data.supportContact.email}/>
      </Header>

      <Container children={
        <Sales>
          <SalesIcons>
            <Icon icon={faUpload} color='#3EB1EB' />
            <b>Sales</b><br /><br />
            <Icon icon={faInfoCircle} color="#A6A2A2" side="right"/>
          </SalesIcons>
          You had <b>{data.salesOverview.uploads} uploads</b> and <b>{data.salesOverview.linesAttempted} lines</b> added.
        </Sales>
      } />

      <Metrics>
        <Metric title={data.salesOverview.successfulUploads} subtitle="Upload Success" />
        <Metric title={data.salesOverview.linesSaved} subtitle="Lines Saved" />
      </Metrics>
    </div >
  )
}

AccountOverview.propTypes = {
  data: PropTypes.shape({
    supportContact: PropTypes.shape({
      name: PropTypes.string.isRequired,
      email: PropTypes.string.isRequired,
    }).isRequired,
    salesOverview: PropTypes.shape({
      uploads: PropTypes.number.isRequired,
      successfulUploads: PropTypes.number.isRequired,
      linesAttempted: PropTypes.number.isRequired,
      linesSaved: PropTypes.number.isRequired,
      lastUploadDate: PropTypes.number.isRequired,
    }).isRequired
  }).isRequired
}

export default AccountOverview;