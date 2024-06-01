import axios from "axios";
import { getToken } from "../auth/storage";

const api = axios.create({
        baseURL: 'http://localhost:8080/',
        headers: { 'Content-Type': 'application/json' }
});

api.interceptors.request.use(config => {
    const token = getToken();
    if (token) config.headers['Authorization'] = 'Bearer ' + token
    return config;
}, error => {
    console.log(error)
    return Promise.reject(new Error("An error occurred"))
})

api.interceptors.response.use((response) => response,  (error) => {

    if (error.response && error.response.status === 401) {

        window.location.href = "/login";
        return Promise.reject(new Error())
    }

    return Promise.reject(new Error("An error occurred. Please, try again later!"))
})


export default api;