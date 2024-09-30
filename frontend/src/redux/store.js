import { configureStore } from "@reduxjs/toolkit";
import { useDispatch, useSelector } from "react-redux";
import authSlice from "./authSlice";
import customerSlice from "./customerSlice";

export const store = configureStore({
    reducer: {
        auth: authSlice,
        selectCustomer: customerSlice
    },
});

export const useAppDispatch = useDispatch;
export const useAppSelector = useSelector;
