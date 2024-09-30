import { createSlice } from "@reduxjs/toolkit";

const authSlice = createSlice({
    name: "auth",
    initialState: {
        user: null,
        token: null,
        role: null,
        loggedIn: false,
    },
    reducers: {
        doLogin: (state, action) => {
            state.token = action.payload;
            state.loggedIn = true;
        },
        doLogout: (state) => {
            state.loggedIn = false;
            state.token = null;
        },
    },
});

export const { doLogin, doLogout } = authSlice.actions;
export default authSlice.reducer;