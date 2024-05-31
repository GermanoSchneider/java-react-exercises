import api from "../api"
import Credentials from "../components/Credentials";
import { getPassword, getUsername, subscribe } from "../stores/credentials-store";

const SignIn = () => {

    console.log();

    const login = () => {

        subscribe(() => {
            const username = getUsername();
            const password = getPassword();

            const basicAuth = {
                'auth': {
                    'username': username,
                    'password': password
                }
            }
    
            api.post('auth/login', {}, basicAuth)
                .then(response => console.log(response))
                .catch(erro => console.log(erro))

        })
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