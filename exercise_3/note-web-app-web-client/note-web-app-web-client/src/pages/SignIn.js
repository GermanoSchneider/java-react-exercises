import { useSelector } from "react-redux";
import api from "../api/api"
import Credentials from "../components/Credentials";
import { authenticate } from "../auth/auth-service";
import { useNavigate } from "react-router-dom";

const SignIn = () => {

    const { username, password } = useSelector(state => state.credential);

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
                authenticate(response.data);
                navigate("/")
            });
    }

    return (
        <center>
            <div>
                <h1>Sign In</h1>
                <form>
                    <Credentials/>
                    <input type="button" value="Login" onClick={login} />
                </form>
            </div>
        </center>
    )

}

export default SignIn;