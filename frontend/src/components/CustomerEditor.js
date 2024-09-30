import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import CustomerApi from '../api/customersApi';
import { selectCustomer } from '../redux/customerSlice';
import { useAppDispatch } from '../redux/store';

export default function CustomerEditor({ customer }) {
    const navigate = useNavigate();
    const dispatch = useAppDispatch();

    const [firstName, setFirstName] = useState(customer?.firstName || "");
    const [lastName, setLastName] = useState(customer?.lastName || "");
    const [street, setStreet] = useState(customer?.setAddress || "");
    const [address, setAddress] = useState(customer?.address || "");
    const [city, setCity] = useState(customer?.city || "");
    const [state, setState] = useState(customer?.state || "");
    const [email, setEmail] = useState(customer?.email || "");
    const [phone, setPhone] = useState(customer?.phone || "");

    const { addCustomer, updateCustomer } = CustomerApi();

    const handleBtnClick = async () => {
        if (!customer) {
            const status = await addCustomer({ firstName, lastName, street, address, city, state, email, phone });
            if (status === 201) {
                navigate("/customers")
            }
        }
        else {
            const status = await updateCustomer({ firstName, lastName, street, address, city, state, email, phone });
            if (status === 202) {
                dispatch(selectCustomer(null));
                navigate("/customers")
            }
        }
    }


    return (
        <div>
            <h1>
                {customer ? "Edit" : "Add"} Customer
            </h1>
            <div>
                <input placeholder='First Name' value={firstName} onChange={(e) => setFirstName(e.target.value)} />
                <input placeholder='Last Name' value={lastName} onChange={(e) => setLastName(e.target.value)} />
                <input placeholder='Street' value={street} onChange={(e) => setStreet(e.target.value)} />
                <input placeholder='Address' value={address} onChange={(e) => setAddress(e.target.value)} />
                <input placeholder='City' value={city} onChange={(e) => setCity(e.target.value)} />
                <input placeholder='State' value={state} onChange={(e) => setState(e.target.value)} />
                <input placeholder='Email' value={email} onChange={(e) => setEmail(e.target.value)} />
                <input placeholder='Phone' value={phone} onChange={(e) => setPhone(e.target.value)} />
            </div>
            <button onClick={() => handleBtnClick()}>Submit</button>
            <button onClick={() => navigate("/customers")}>Cancel</button>
        </div>
    )
}
