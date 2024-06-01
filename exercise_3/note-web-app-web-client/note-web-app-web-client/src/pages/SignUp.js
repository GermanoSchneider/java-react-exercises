import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { register } from "../api/auth-api";
import Credentials from "../components/Credentials";
import ShowError from "../components/ShowError";
import { addError } from "../reducers/error-reducer";
import Input from "../components/Input";

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
        <div className="centralized-container">
            <form>
                <h1>Sign Up</h1>
                <Credentials />
                <Input placeholder="Repeat the password" required={true} name="password" type="password" value={checkPassword} onChange={handleCheckPasswordChange} />
                <div className="credentials-link">
                    <p>Are you already registered? Click <b><Link to="/login">here</Link></b> to log in.</p>
                </div>
                <Input type="button" value="Create" onClick={createUser}/>
                <ShowError />
            </form>
            
        </div>
    )

}

export default SignUp;