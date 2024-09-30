import React from 'react';
import CustomerEditor from '../components/CustomerEditor';
import { useAppSelector } from '../redux/store';

export default function EditCustomer() {
    const customer = useAppSelector(state => state.selectCustomer.customer);
    return (
        <div>
            <CustomerEditor customer={customer} />
        </div>
    )
}
