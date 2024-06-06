import React, { useEffect, useState } from "react";

import { useNavigate } from "react-router-dom";
import Container from "react-bootstrap/Container";
import { deletePaymentById, getAllPayment } from "../Services/PaymentService";

function AdminPayment() {



    const [payment, setPayment] = useState([]);
    const navigate = useNavigate("");
    const [selectedStatus, setSelectedStatus] = useState("View All");
   
    const handleSelect = (eventKey) => {
      setSelectedStatus(eventKey);
    };

   
    function getPayment(){
      getAllPayment()
      .then((response)=>{
        setPayment(response.data);
      })
      .catch((error)=>{
        console.log(error);
      });
    }
    useEffect(() => {
      getAllPayment()
        .then((response) => {
          setPayment(response.data);
          console.log(response.data)
        })
        .catch((error) => {
          console.log(error);
        });
    }, []);
   
    const viewPayment = (appId) => {
      navigate(`/payment/applicationId/${appId}`);
    };
   
    const handleDelete=(paymentId)=>{
      deletePaymentById(paymentId).then((response)=>{
        getPayment();
      }).catch((error)=>{console.log(error)})
    }


  return (
    (payment.length>0)?(<div className="adminpaymentdiv">
        
    <Container>
    <h2 className="text-center mb-5">List of payments</h2>



    <div className="flex-table">
      <table className="table table-striped table-bordered">
        <thead>
          <tr>
            <th>Payment ID</th>
            <th>Applicant Name</th>
            <th>Payment Amount</th>
            <th>Payment Status</th>
            <th>Payment Date</th>
            <th>Applicant Email</th>
            {/* <th style={{ textAlign: "center" }}>Actions</th> */}
          </tr>
        </thead>

        <tbody>
          { payment.map((e) => (
              <tr key={e.paymentId}>
                <td>{e.paymentId}</td>
                <td>{e.application.applicantFullName}</td>
                <td>{e.paymentAmount}</td>
                <td>{e.paymentStatus}</td>
                <td>{e.paymentDate}</td>
                <td>{e.emailId}</td>
                {/* <td>
                <div className="d-flex justify-content-center">
                <button
                  onClick={() => viewPayment(e.paymentId)}
                  className="btn btn-info"
                  style={{ width: '100px', marginRight: '10px' }}
                >
                  View
                </button>
                <button
                disabled
                  className="btn btn-danger"
                  onClick={() => handleDelete(e.paymentId)}
                  style={{ width: '100px' }}
                >
                  Delete
                </button>
              </div>
                </td> */}
           
              </tr>
            ))}
        </tbody>
      </table>
    </div>
  </Container>  



</div>):(<div className="no">
          <h2 className="noData">No Payment Found</h2>
        </div>)
  )
}

export default AdminPayment