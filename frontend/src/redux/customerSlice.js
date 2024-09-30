import { createSlice } from "@reduxjs/toolkit";

const customerSlice = createSlice({
    name: "selectCustomer",
    initialState: {
        customer: null
    },
    reducers: {
        selectCustomer: (state, action) => {
            state.customer = action.payload;
        },
    },
});

export const { selectCustomer } = customerSlice.actions;
export default customerSlice.reducer;