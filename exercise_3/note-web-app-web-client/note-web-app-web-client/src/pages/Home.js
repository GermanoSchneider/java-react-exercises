import { useSelector } from "react-redux";
import { getUsername } from "../auth/storage";

const Home = () => {
    
    return (
        <div>
            <header>
                <h1>Hello, {getUsername()}</h1> 
            </header>
        </div>
    )
}

export default Home;