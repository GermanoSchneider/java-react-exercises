import '../account-overview.css';
import { faEnvelope } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';
import Icon from './Icon';

const Section = styled.div`
    flex: 50%;
    margin-top: -15px;
`

const Title = styled.div`
    font-weight: bolder;
    color: #B7B4B4;
    letter-spacing: 1px;
    padding-bottom: 10px;
    @media(max-width: 600px) {
        margin-top: 30px;
    }
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


const Contact = ({name, email}) => {

    return (
        <Section>
            <Title>YOUR FEEFO SUPPORT CONTACT</Title>
            <ContactDetails>
                <ContactAvatar>{name[0]}</ContactAvatar>
                <ContactInfo>
                    <b>{name}</b><br />
                    <Icon icon={faEnvelope} />
                    {email}
                </ContactInfo>
                <span className='contact-phone'>020 3362 4208</span>
            </ContactDetails>
        </Section>
    )
}

export default Contact;