import { createSlice } from "@reduxjs/toolkit"

const errorReducer = createSlice({
    name: 'error',
    initialState: { message: ''},
    reducers: {
        addError: (state, action) => { state.message = action.payload },
        removeError: (state) => { state.message = '' },
    }
})

export const { addError, removeError } = errorReducer.actions;
export default errorReducer.reducer;