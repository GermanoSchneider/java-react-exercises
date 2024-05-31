import { useSelector } from "react-redux";
import api from "../api"
import Credentials from "../components/Credentials";

const SignIn = () => {

    const { username, password } = useSelector(state => state.credential);

    const login = () => {

        const basicAuth = {
            auth: {
                username: username,
                password: password
            }
        }

        api.post('auth/login', {}, basicAuth)
            .then(response => console.log(response))
            .catch(erro => console.log(erro))
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