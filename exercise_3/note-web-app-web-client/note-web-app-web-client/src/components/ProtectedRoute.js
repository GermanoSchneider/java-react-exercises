import { Navigate } from "react-router-dom";
import { getToken } from "../auth/storage";

const ProtectedRoute = ({ children }) => {

    if (isNotAuthenticated()) {
      return <Navigate to="/login" replace/>;
    }
  
    return children;
};

const isNotAuthenticated = () => {

  return getToken() === null;
}

export default ProtectedRoute;