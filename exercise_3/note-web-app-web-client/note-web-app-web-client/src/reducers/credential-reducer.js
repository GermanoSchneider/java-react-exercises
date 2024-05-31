import { createSlice } from "@reduxjs/toolkit";

const credentialReducer = createSlice({
    name: 'credential',
    initialState: {
        username: '',
        password: ''
    },
    reducers: {
        setUsername: (state, action) => { state.username = action.payload },
        setPassword: (state, action) => { state.password = action.payload },
        resetCredentials: (state) => { state.password = ''; state.username = '' }
    }
})

export const { setUsername, setPassword, resetCredentials } = credentialReducer.actions;
export default credentialReducer.reducer;