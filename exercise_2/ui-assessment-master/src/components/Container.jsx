import styled from 'styled-components';

const WhiteBox = styled.div`
    padding: 5px 15px 5px 15px;
    margin-top: 2px;
    margin-right: 2px;
    flex: 50%;
    background-color: white;
    border-radius: 3px;
  `


const Container = ({children}) => {

    return (
        <WhiteBox>
            {children}
        </WhiteBox>
    )

}

export default Container;