import { configureStore } from "@reduxjs/toolkit";
import credentialReducer from "../reducers/credential-reducer";
import errorReducer from "../reducers/error-reducer";

const store = configureStore({
    reducer: {
        credential: credentialReducer,
        error: errorReducer
    }
})

export default store;
