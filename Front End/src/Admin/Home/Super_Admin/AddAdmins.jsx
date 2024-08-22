import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import { Button, Table } from 'react-bootstrap';
import './AddAdmins.css';
import VerifyTpinModal from '../../../Components/Organisms/Pages_Component/Modal/verifyTpinModal';
import config from '../../../config';

const AddAdmins = () => {
    const [customers, setCustomers] = useState([]);
    const [showTpinModal, setShowTpinModal] = useState(false);
    const [selectedCustomerId, setSelectedCustomerId] = useState(null);

    useEffect(() => {
        fetchAllCustomers();
      }, []);
    
      const fetchAllCustomers = async () => {
        try {
          const token = sessionStorage.getItem('token'); // Retrieve the token from session storage
          const response = await axios.get(`${config.url}/bank/superadmin/getAllCustomersOnly`, {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${token}`, // Add the token to the Authorization header
            }
          });
          setCustomers(response.data);
        } catch (error) {
          toast.error('Failed to fetch customers');
        }
      };
    
      const handleMakeAdmin = (customerId) => {
        setSelectedCustomerId(customerId);
        setShowTpinModal(true);
      };
    
      const handleTpinVerification = async () => {
        try {
          const token = sessionStorage.getItem('token'); // Retrieve the token from session storage
          await axios.put(`${config.url}/bank/superadmin/updateAdmin/${selectedCustomerId}`, {}, {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${token}`, // Add the token to the Authorization header
            }
          });
          toast.success('Admin role assigned successfully');
          fetchAllCustomers(); // Refresh the list after assignment
        } catch (error) {
          toast.error('Action failed');
        }
      };

    return (
        <div className="add-admins">
            <h2>Add Admins</h2>
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>Customer ID</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Adhar No</th>
                        <th>PAN No</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {customers.map((customer) => (
                        <tr key={customer.customerId}>
                            <td>{customer.customerId}</td>
                            <td>{customer.firstName}</td>
                            <td>{customer.lastName}</td>
                            <td>{customer.email}</td>
                            <td>{customer.adharNo}</td>
                            <td>{customer.panNo}</td>
                            <td>
                                <Button
                                    variant="dark"
                                    onClick={() => handleMakeAdmin(customer.customerId)}
                                >
                                    Make Admin
                                </Button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>

            <VerifyTpinModal
                show={showTpinModal}
                handleClose={() => setShowTpinModal(false)}
                onVerify={handleTpinVerification}
            />
        </div>
    );
};

export default AddAdmins;
