import React from "react";
import { getPassword, getUsername, setPassword, setUsername } from "../stores/credentials-store";

const Credentials = () => {

    return (
        <div>
            <label>
                Username:
                <input type="text" name="username" value={getUsername} onChange={(fieldValue) => setUsername(fieldValue.target.value)} />
            </label><br></br>
            <label>
                Password:
                <input type="password" name="password" value={getPassword} onChange={(fieldValue) => setPassword(fieldValue.target.value)} />
            </label><br></br>
        </div>
    )
}

export default Credentials;
