import { configureStore } from "@reduxjs/toolkit";
import credentialReducer from "../reducers/credential-reducer";

const store = configureStore({
    reducer: {
        credential: credentialReducer
    }
})

export default store;
