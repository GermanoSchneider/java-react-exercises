import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import api from "../api/api";
import Credentials from "../components/Credentials";
import { useNavigate } from "react-router-dom";
import { addError } from "../reducers/error-reducer";
import ShowError from "../components/ShowError";

const SignUp = () => {

    const { username, password } = useSelector(state => state.credential);
    const [checkPassword, setCheckPassword] = useState('')

    const dispatch = useDispatch()
    const navigate = useNavigate()

    const validatePassword = (password) => {

        if (password !== checkPassword) 
            throw new Error("password does not match")
    } 

    const createUser = () => {

        const payload = {
            username: username,
            password: password
        }

        try {

            validatePassword(password)

            api.post("auth/user", payload)
               .then(() => navigate("/login"))
               .catch((error) => dispatch(addError(error.message))) 

        } catch (error) {
            dispatch(addError(error.message))
        }        
    }

const handleCheckPasswordChange = (event) => {
    setCheckPassword(event.target.value);
};

return (
    <center>
        <div>
            <h1>Sign Up</h1>
            <form>
                <Credentials />
                <input type="password" value={checkPassword} onChange={handleCheckPasswordChange} />
                <input type="button" value="Create" onClick={createUser} />
            </form>
            <ShowError/>
        </div>
    </center>
)

}

export default SignUp;