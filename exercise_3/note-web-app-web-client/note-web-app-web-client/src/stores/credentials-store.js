import { configureStore, createSlice } from "@reduxjs/toolkit";

const reducer = createSlice({
    name: 'credentials',
    initialState: {
        value: {
            'username': 'teste',
            'password': ''
        }
    },
    reducers: {
        setUsername: state => state.value.username = 'ok',
        setPassword: state => state.value.password = 'ok',
    }
})


const credentialsStore = configureStore({
    reducer: reducer
})

const actions = reducer.actions;
const state = credentialsStore.getState();

export const getUsername = state.value.username;
export const getPassword = state.value.password;
export const subscribe = (action) => credentialsStore.subscribe(() => action)
export const setUsername = (username) => credentialsStore.dispatch(() => actions.setUsername(username))
export const setPassword = (password) => credentialsStore.dispatch(() => actions.setPassword(password))     