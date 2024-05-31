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
    }
})

export const { setUsername, setPassword } = credentialReducer.actions;
export default credentialReducer.reducer;