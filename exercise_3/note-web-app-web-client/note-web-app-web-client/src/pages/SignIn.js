import { useDispatch, useSelector } from "react-redux";
import Credentials from "../components/Credentials";
import { useNavigate } from "react-router-dom";
import { setToken, setUsername } from "../auth/storage";
import ShowError from "../components/ShowError";
import { addError } from "../reducers/error-reducer";
import { signIn } from "../api/auth-api";

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
        <center>
            <div>
                <h1>Sign In</h1>
                <form>
                    <Credentials />
                    <input type="button" value="Login" onClick={login} />
                </form>
                <ShowError />
            </div>
        </center>
    )

}

export default SignIn;