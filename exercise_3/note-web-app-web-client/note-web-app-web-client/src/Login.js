import axios from "axios";
import { useState } from "react";

const Login = () => {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

 
    const login = () => {

        axios.create({
            baseURL: 'http://localhost:8080/',
            headers: { 'Content-Type': 'application/json' }
        }).post('/login?username=john&password=12345').then(response => console.log(response))
    }

    return (
        <center> <div>
            <h1>Sign In</h1>
            <form onSubmit={login()}>
                <label>
                    Username:
                    <input type="text" name="username" value={username} onChange={(fieldValue) => setUsername(fieldValue.target.value)} />
                </label><br></br>
                <label>
                    Password:
                    <input type="password" name="password" value={password} onChange={(fieldValue) => setPassword(fieldValue.target.value)} />
                </label><br></br>
                <input type="submit" value="Submit" />
            </form>
            <p>Or click <a href='#'>here</a> to create a new account</p>
        </div></center>
    )
}

export default Login;