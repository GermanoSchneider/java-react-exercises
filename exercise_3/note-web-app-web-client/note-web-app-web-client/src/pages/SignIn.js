import { useDispatch, useSelector } from "react-redux";
import api from "../api/api"
import Credentials from "../components/Credentials";
import { useNavigate } from "react-router-dom";
import { setToken, setUsername } from "../auth/storage";
import ShowError from "../components/ShowError";
import { addError } from "../reducers/error-reducer";

const SignIn = () => {

    const { username, password } = useSelector(state => state.credential);
    
    const dispatch = useDispatch()
    const navigate = useNavigate();

    const login = () => {

        const basicAuth = {
            auth: {
                username: username,
                password: password
            }
        }

        api.post('auth/login', {}, basicAuth)
            .then(response => {
                setToken(response.data);
                setUsername(username);
                navigate("/")
            }).catch(error => dispatch(addError(error.message)));
    }

    return (
        <center>
            <div>
                <h1>Sign In</h1>
                <form>
                    <Credentials/>
                    <input type="button" value="Login" onClick={login} />
                </form>
                <ShowError/>
            </div>
        </center>
    )

}

export default SignIn;