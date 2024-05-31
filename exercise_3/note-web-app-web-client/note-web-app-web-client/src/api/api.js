import axios from "axios";
import { clearToken, getToken } from "../auth/storage";

const api = axios.create({
        baseURL: 'http://localhost:8080/',
        headers: { 'Content-Type': 'application/json' }
});

api.interceptors.request.use(config => {
    const token = getToken();
    if (token) config.headers['Authorization'] = 'Bearer ' + token
    return config;
}, error => Promise.reject(error))

api.interceptors.response.use((response) => response,  (error) => {

    if (error.response.status === 401) {

        clearToken();

        window.location.href = "/login";

        return Promise.reject(error);
    }
})


export default api;