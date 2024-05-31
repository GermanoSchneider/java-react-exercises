import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { setPassword, setUsername } from "../reducers/credential-reducer";

const Credentials = () => {

    const dispatch = useDispatch()
    const { username, password } = useSelector(state => state.credential);

    const handleUsernameChange = (event) => {
        dispatch(setUsername(event.target.value));
    };

    const handlePasswordChange = (event) => {
        dispatch(setPassword(event.target.value));
    };

    return (
        <div>
            <label>
                Username:
                <input type="text" name="username" value={username} onChange={handleUsernameChange}/>
            </label><br></br>
            <label>
                Password:
                <input type="password" name="password" value={password} onChange={handlePasswordChange} />
            </label><br></br>
        </div>

    )

    
}

export default Credentials;
