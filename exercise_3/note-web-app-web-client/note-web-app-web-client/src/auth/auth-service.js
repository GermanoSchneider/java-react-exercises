import { getToken, setToken } from "./storage"

export const authenticate = (token) => {
    setToken(token)
}

export const isAuthenticated = () => {

    return getToken() != undefined;
}