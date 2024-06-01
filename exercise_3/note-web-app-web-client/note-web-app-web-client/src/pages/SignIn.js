import { useDispatch, useSelector } from "react-redux";
import Credentials from "../components/Credentials";
import { useNavigate, Link } from "react-router-dom";
import { setToken, setUsername } from "../auth/storage";
import ShowError from "../components/ShowError";
import { addError } from "../reducers/error-reducer";
import { signIn } from "../api/auth-api";
import Input from "../components/Input";
import '../index.css';

const SignIn = () => {

    const { username, password } = useSelector(state => state.credential);

    const dispatch = useDispatch()
    const navigate = useNavigate();

    const login = async () => {

        const basicAuth = {
            auth: {
                username: username,
                password: password
            }
        }

        await signIn(basicAuth)
            .then((response) => {
                setToken(response.data)
                setUsername(username)
                navigate("/")
            })
            .catch(error => dispatch(addError(error.message)))


    }

    return (
        <div className="centralized-container">
            <form>
                <h1>Sign In</h1>
                <Credentials />
                <div className="credentials-link">
                    <p>Don't you have an account yet? Click <b><Link to="/register">here</Link></b> to create one.</p>
                </div>
                <Input type="button" value="Login" onClick={login}/>
            </form>
            <ShowError />
        </div>
    )

}

export default SignIn;