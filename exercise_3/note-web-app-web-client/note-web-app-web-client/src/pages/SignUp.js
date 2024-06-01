import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { register } from "../api/auth-api";
import Credentials from "../components/Credentials";
import ShowError from "../components/ShowError";
import { addError } from "../reducers/error-reducer";

const SignUp = () => {

    const { username, password } = useSelector(state => state.credential);
    const [checkPassword, setCheckPassword] = useState('')

    const dispatch = useDispatch()
    const navigate = useNavigate()

    const validatePassword = (password) => {

        if (password !== checkPassword)
            throw new Error("password does not match")
    }

    const createUser = async () => {

        const user = {
            username: username,
            password: password
        }

        try {
            validatePassword(password)
            await register(user)
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
                <ShowError />
            </div>
        </center>
    )

}

export default SignUp;