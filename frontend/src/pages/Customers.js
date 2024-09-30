import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import CustomerApi from '../api/customersApi';
import { Page } from '../api/Pagination';
import { selectCustomer } from '../redux/customerSlice';
import { useAppDispatch } from '../redux/store';

export default function Customers() {
    const navigate = useNavigate();
    const dispatch = useAppDispatch();
    const { getAllCustomers, deleteCustomer } = CustomerApi();
    const [customers, setCustomers] = useState([]);
    const [pageNo, setPageNo] = useState(0);
    const [page, setPage] = useState(Page.newPage());
    const [pagination, setPagination] = useState(null);
    const [hasNextPage, setHasNextPage] = useState(true);
    const [hasPrevPage, setHasPrevPage] = useState(false);

    const updateCustomerPage = async () => {
        const customerPage = await getAllCustomers(page);
        setCustomers(customerPage.items);
        setPage(customerPage.page);
        setPagination(customerPage);
        setHasNextPage(!customerPage.isLastPage);
        setHasPrevPage(!customerPage.isFirstPage);
    };

    useEffect(() => {
        updateCustomerPage();
    }, [pageNo]);

    const nextPage = () => {
        if (!pagination.isLastPage) {
            setPage({ ...page, pageNo: page.pageNo + 1 });
            setPageNo(page.pageNo + 1);
        }
    };

    const prevPage = () => {
        if (!pagination.isFirstPage) {
            setPage({ ...page, pageNo: page.pageNo - 1 });
            setPageNo(page.pageNo - 1);
        }
    };

    const addCustomer = () => {
        navigate("/customers/add");
    }

    const editCustomer = (customer) => {
        dispatch(selectCustomer(customer));
        navigate("/customers/edit");
    }

    const _deleteCustomer = async (id) => {
        await deleteCustomer(id);
        updateCustomerPage();
    }


    return (
        <div className="container p-4 w-full bg-black">
            <div className="text-center">
                <p className="h1">Customer list</p>
            </div>
            <div>
                <button onClick={() => addCustomer()}>
                    Add Customer
                </button>
            </div>
            <table className="table-auto border-spacing-1">
                <thead>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Address</th>
                        <th>City</th>
                        <th>State</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {customers && customers.map(customer => (
                        <tr key={customer.uuid}>
                            <td>{customer.firstName}</td>
                            <td>{customer.lastName}</td>
                            <td>{customer.address}</td>
                            <td>{customer.city}</td>
                            <td>{customer.state}</td>
                            <td>{customer.email}</td>
                            <td>{customer.phone}</td>
                            <td>
                                <button className="btn btn-primary" onClick={() => editCustomer(customer)}>Edit</button>
                                <button className="btn btn-danger" onClick={() => _deleteCustomer(customer.uuid)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            {customers && pagination && (
                <div>
                    <p>Page {page.pageNo + 1} of {pagination?.totalPages}</p>
                    <button onClick={prevPage} disabled={!hasPrevPage}>Previous Page</button>
                    <button onClick={nextPage} disabled={!hasNextPage}>Next Page</button>
                </div>
            )}
        </div>
    );
}
