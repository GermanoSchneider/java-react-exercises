import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const Icon = ({icon, color, side}) => {

    return (
        <span style={{marginRight: '5px', float: side}}>
            <FontAwesomeIcon icon={icon} color={color}/>
        </span>
    )
}

export default Icon;