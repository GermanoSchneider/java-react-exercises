import { useState } from "react";
import { useSelector } from "react-redux";
import api from "../api";
import Credentials from "../components/Credentials";

const SignUp = () => {

    const { username, password } = useSelector(state => state.credential);
    const [checkPassword, setCheckPassword] = useState('')

    const validatePassword = (password) => {

        if (password === checkPassword) return;

        throw new Error("password does not match")
    }

    const createUser = () => {

        validatePassword(password)

        const payload = {
            username: username,
            password: password  
        }

        api.post('auth/user', payload)
            .then(response => console.log(response))
            .catch(erro => console.log(erro))
    }

    const handleCheckPasswordChange = (event) => {
        setCheckPassword(event.target.value);
    };

    return (
        <center>
           <div>
                <h1>Sign Up</h1>
                <form>
                    <Credentials/>
                    <input type="password" value={checkPassword} onChange={handleCheckPasswordChange}/>
                    <input type="button" value="Create" onClick={createUser} />
                </form>
            </div>
        </center>
    )

}

export default SignUp;