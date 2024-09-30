import { useAppSelector } from "../redux/store";
import Pagination from "./Pagination";

export default function CustomerApi() {
    const endPoint = "/customers";
    const token = useAppSelector(state => state.auth.token);
    const getHeaders = () => {
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("Authorization", `Bearer ${token}`)
        return headers;
    }

    const getAllCustomers = async ({ pageNo, pageSize, sortBy, order }) => {
        const url = `/customers?${pageNo ? `pageNo=${pageNo}&` : ''}${pageSize ? `pageSize=${pageSize}&` : ''}${sortBy ? `sortBy=${sortBy}&` : ''}${order ? `order=${order}` : ''}`;
        const response = await fetch(url, {
            method: "GET",
            headers: getHeaders()
        });
        return new Pagination(await response.json());
    }


    const addCustomer = async (customer) => {
        const url = endPoint;
        const response = await fetch(url, {
            method: "POST",
            headers: getHeaders(),
            body: JSON.stringify(customer)
        });
        return response.status;
    }

    const updateCustomer = async (customer) => {
        const url = endPoint;
        const response = await fetch(url, {
            method: "PUT",
            headers: getHeaders(),
            body: JSON.stringify(customer)
        });
        return response.status;
    }

    const deleteCustomer = async (id) => {
        const url = endPoint + "?id=" + id;
        const response = await fetch(url, {
            method: "DELETE",
            headers: getHeaders()
        });
        return response.status;
    }

    return {
        getAllCustomers, addCustomer, updateCustomer, deleteCustomer
    }
}