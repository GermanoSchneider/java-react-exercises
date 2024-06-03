import api from "./api"

export const signIn = async (basicAuth) => {
   return await api.post('auth/login', {}, basicAuth)
}

export const register = async (user) => {
    return await api.post('auth/user', user)
 }