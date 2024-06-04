import Container from "./Container"
import styled from 'styled-components';

const Title = styled.h1`
    padding: 8px;
    font-weight: bold;
    padding-bottom: 0;
    color: #22AB55;
    margin: 0;
  `

const Subtitle = styled.h2`
    text-transform: uppercase;
    padding: 8px;
    font-size: 14px;
    letter-spacing: 1px;
    padding-top: 0;
    margin: 0;
    color: #A6A2A2; 
  `

const Metric = ({title, subtitle}) => {

    return (
        <Container children={
            <div>
              <Title>{title}%</Title>
              <Subtitle>{subtitle}</Subtitle>
            </div>
          } />
    )
}

export default Metric;