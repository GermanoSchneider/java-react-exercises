import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEnvelope, faUpload, faInfoCircle } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';
import './account-overview.css';
import PropTypes from 'prop-types';

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

const Subtitle = styled.div`
    font-weight: bolder;
    color: #B7B4B4;
    letter-spacing: 1px;
    padding-bottom: 10px;
    @media(max-width: 600px) {
      margin-top: 30px;
    }
  `

const Contact = styled.div`
    flex: 50%;
    margin-top: -15px;
  `

const ContactDetails = styled.div`
    display: flex;
    font-weight: normal;
    @media(max-width: 600px) {
      flex-direction: column;
    }
  `

const ContactInfo = styled.div`
    padding-left: 5px;
    color: #333333;
    font-size: 14px;
  `

const ContactAvatar = styled.div`
    padding: 10px 15px 10px 15px;
    background-color: #F9CF03;
    border-radius: 5px;
    margin-right: 5px;
    font-weight: bold;
    @media(max-width: 600px) {
      text-align:center;
      margin-bottom: 10px;
    }
  `

const WhiteBox = styled.div`
    padding: 5px 15px 5px 15px;
    margin-top: 2px;
    margin-right: 2px;
    flex: 50%;
    background-color: white;
    border-radius: 3px;
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

const MetricsTitle = styled.h1`
    padding: 8px;
    font-weight: bold;
    padding-bottom: 0;
    color: #22AB55;
    margin: 0;
  `

const MetricsSubtitle = styled.h2`
    text-transform: uppercase;
    padding: 8px;
    font-size: 14px;
    letter-spacing: 1px;
    padding-top: 0;
    margin: 0;
    color: #A6A2A2; 
  `

const Icon = styled.span`
  margin-right: 8px;
`

export const AccountOverview = ({ data }) => {

  function throwError() {
    throw new Error("this is a error");
  }

  return (
    <div className="AccountOverview">
      <Header>
        <Title>Account Overview</Title>
        <Contact>
          <Subtitle onClick={throwError}>YOUR FEEFO SUPPORT CONTACT</Subtitle>
          <ContactDetails>
            <ContactAvatar>{data.supportContact.name[0]}</ContactAvatar>
            <ContactInfo>
              <b>{data.supportContact.name}</b><br/>
              <Icon>
                <FontAwesomeIcon icon={faEnvelope}/>
              </Icon>
              {data.supportContact.email} 
            </ContactInfo>
            <span className='contact-phone'>020 3362 4208</span>
          </ContactDetails>
        </Contact>
      </Header>

      <WhiteBox>
        <Sales>
          <SalesIcons>
            <Icon>
              <FontAwesomeIcon icon={faUpload} color='#3EB1EB' />
            </Icon>
            <b>Sales</b><br/><br/>
            <Icon>
              <FontAwesomeIcon icon={faInfoCircle} color="#A6A2A2" className='info-icon'/>
            </Icon>
          </SalesIcons>
          You had <b>{data.salesOverview.uploads} uploads</b> and <b>{data.salesOverview.linesAttempted} lines</b> added
        </Sales>
      </WhiteBox>
      <Metrics>
        <WhiteBox>
          <MetricsTitle>{data.salesOverview.successfulUploads}%</MetricsTitle>
          <MetricsSubtitle>Upload Success</MetricsSubtitle>
        </WhiteBox>
        <WhiteBox>
          <MetricsTitle>{data.salesOverview.linesSaved}%</MetricsTitle>
          <MetricsSubtitle>Lines Saved</MetricsSubtitle>
        </WhiteBox>
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