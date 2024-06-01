import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { resetCredentials, setPassword, setUsername } from "../reducers/credential-reducer";
import '../index.css';
import Input from "./Input";

const Credentials = () => {

    const { username, password } = useSelector(state => state.credential);
    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(resetCredentials())
    }, [])

    const handleUsernameChange = (event) => {
        dispatch(setUsername(event.target.value));
    };

    const handlePasswordChange = (event) => {
        dispatch(setPassword(event.target.value));
    };

    return (
        <div className="credentials-box">
            <Input placeholder="Username" required={true} type="text" name="username" value={username} onChange={handleUsernameChange}/><br/>
            <Input placeholder="Password" required={true} type="password" name="password" value={password} onChange={handlePasswordChange} />
        </div>
    )

}

export default Credentials;
