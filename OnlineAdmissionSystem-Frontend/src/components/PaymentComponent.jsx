import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate, useParams } from "react-router-dom";
// import React, { useState } from 'react';
// import { useNavigate, useParams } from "react-router-dom";
 
// import { useEffect } from 'react';
// import { getApplicationId } from '../service/ApplicationService';    
 
import Card from "react-bootstrap/Card";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import { getPaymentDetailsByApplicationId, postPayment } from "../Services/PaymentService";
import { getApplicationId } from "../Services/ApplicationService";
 
 
 
 
 
 
function PaymentComponent() {
 
    const [payment,setPayment]=useState([]);
    const[application ,setApplication]=useState([]);
      const navigator = useNavigate();
      const { appId } = useParams();
 
      const [errors, setErrors] = useState({
        name: "",
        emailId: "",
        paymentAmount: "",
        paymentDescription: ""
      });
     
      useEffect(() => {
        getPaymentDetailsByApplicationId(appId)
          .then((response) => {
        //    console.log(appId)
            setPayment(response.data)
            console.log(payment);
          })
          .catch((error) => {
            console.log(error);
          });
     
          getApplicationId(appId).then((response)=>{
            setApplication(response.data)
        console.log(application)
        }).catch((error)=>{
            console.log(error)
          })
     
     
     
      }, [appId]);
     
     
      const [showForm, setShowForm] = useState(false);
     
      const today = new Date(),
                date = today.getFullYear() + '-0' + (today.getMonth() + 1) + '-0' + today.getDate();
     
     
    //   const [application,setApplication]=useState([]);
    const handleGoBack = () => {
      navigator('/dashboard')
    };
     
     
      const [formData, setFormData] = useState({
        name: '',
        emailId: '',
        paymentAmount: '',
        paymentDate: date,
        paymentDescription: '',
        paymentStatus:'Confirmed'
      });
     
      const updateEmailId = (newEmail) => {
        setFormData((prevFormData) => ({
          ...prevFormData,
          emailId: newEmail
        }));
      };
     
      const navigate = useNavigate();
    //   const {appId}=useParams();
     
 
     
 
     
      useEffect(()=>{
        getApplicationId(appId).then((response)=>{
          setApplication(response.data);
          console.log(response.data);
          updateEmailId(response.data.emailId)
         
         
        }).catch((error)=>{
          console.log(error)
        })
      },[])
     
      const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
          ...prevData,
          [name]: value,
        }));
      };
     
      const validateForm = () => {
        let valid = true;
        const errorsCopy = { ...errors };
     
        if (!formData.name) {
          errorsCopy.name = "Name cannot be empty";
          valid = false;
        } else {
          errorsCopy.name = "";
        }
     
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!formData.emailId) {
          errorsCopy.emailId = "Email ID cannot be empty";
          valid = false;
        } else if (!emailRegex.test(formData.emailId)) {
          errorsCopy.emailId = "Invalid email format";
          valid = false;
        } else {
          errorsCopy.emailId = "";
        }
     
        if (!formData.paymentAmount) {
          errorsCopy.paymentAmount = "Payment amount cannot be empty";
          valid = false;
        } else if (isNaN(formData.paymentAmount) || formData.paymentAmount <= 0) {
          errorsCopy.paymentAmount = "Payment amount must be a positive number";
          valid = false;
        } else {
          errorsCopy.paymentAmount = "";
        }
     
        if (!formData.paymentDescription) {
          errorsCopy.paymentDescription = "Payment description cannot be empty";
          valid = false;
        } else {
          errorsCopy.paymentDescription = "";
        }
     
        setErrors(errorsCopy);
        return valid;
      };
 
      const handleClose=()=>{
        navigator('/dashboard')
      }
 
 
      const handleSubmit = (e) => {
        e.preventDefault();
        if(validateForm){
          console.log(formData);
          postPayment(appId,formData).then((response)=>{
            console.log(response.data)
            navigator(`/admission/${appId}`)
          }).catch((error)=>{
            console.log(error)
          })
        }
       
     
        // navigate('/confirmation', { state: formData });
      };
 
 
 
  return (
    <div className="paymentdiv">
       
 
        {payment.paymentId ?( <div className="d-flex justify-content-center">
     <Card className="shadow-sm mb-4">
      <Card.Body>
        <Card.Title className="mb-4">
          <h4 className="font-weight-bold text-center text-uppercase">Payment Details</h4>
        </Card.Title>
        <hr />
        <Container>
          <Row className="mb-4">
            <Col>
              <Card className="bg-light p-3 shadow-sm">
                <p className="h5 font-weight-bold mb-2 text-secondary">Payment Id:</p>
                <p className="text-dark">{payment.paymentId}</p>
              </Card>
            </Col>
            <Col>
              <Card className="bg-light p-3 shadow-sm">
                <p className="h5 font-weight-bold mb-2 text-secondary">Email:</p>
                <p className="text-dark">{payment.emailId}</p>
              </Card>
            </Col>
          </Row>
          <Row className="mb-4">
            <Col>
              <Card className="bg-light p-3 shadow-sm">
                <p className="h5 font-weight-bold mb-2 text-secondary">Payment Amount:</p>
                <p className="text-dark">{payment.paymentAmount}</p>
              </Card>
            </Col>
            <Col>
              <Card className="bg-light p-3 shadow-sm">
                <p className="h5 font-weight-bold mb-2 text-secondary">Date Of Payment:</p>
                <p className="text-dark">{payment.paymentDate}</p>
              </Card>
            </Col>
          </Row>
          <Row className="mb-4">
            <Col>
              <Card className="bg-light p-3 shadow-sm">
                <p className="h5 font-weight-bold mb-2 text-secondary">Payment Status:</p>
                <p className="text-dark">{payment.paymentStatus}</p>
              </Card>
            </Col>
            <Col>
              <Card className="bg-light p-3 shadow-sm">
                <p className="h5 font-weight-bold mb-2 text-secondary">Application Id:</p>
                <p className="text-dark">{payment.application.applicationId}</p>
              </Card>
            </Col>
          </Row>
        </Container>
      </Card.Body>
      <button className="go-back-btn mt-3 mb-4" onClick={handleGoBack}>
                 DashBoard
                </button>
    </Card>
   
      </div>
):(<>
{application.applicationId ?(<>
 
   
<>
{application.applicationStatus=="Accepted"?(
 
      <div className="App">
         {/* <h1>"No Payment  Found"</h1><br/>
<h3>Please Pay the  Fees</h3> */}
      <div className="container">
        <div className="row">
          <div className="card col-md-6 offset-md-3">
            {/* <button className="closeBtn btn btn-danger" onClick={handleClose}>
              &times;
            </button> */}
            <h2 className="text-center">Payment details</h2>
            <div className="card-body  text-start">
              <form onSubmit={handleSubmit}>
                <div className="form-group mb-2 text-start ">
                  <label  htmlFor="name">Name:</label>
                  <input
                    type="text"
                    id="name"
                    name="name"
                    value={formData.name}
                    className={`form-control ${errors.name ? "is-invalid" : ""}`}
                    onChange={handleChange}
                    // required
                  />
                  {errors.name && (
                    <div className="invalid-feedback">{errors.name}</div>
                  )}
                </div>
 
                <div className="form-group mb-2">
                  <label htmlFor="emailId">Email:</label>
                  <input
                    type="email"
                    id="emailId"
                    name="emailId"
                    value={formData.emailId}
                    className={`form-control ${errors.emailId ? "is-invalid" : ""}`}
                    disabled
                    // required
                  />
                  {errors.emailId && (
                    <div className="invalid-feedback">{errors.emailId}</div>
                  )}
                </div>
 
                <div className="form-group mb-2">
                  <label htmlFor="paymentAmount">Amount:</label>
                  <input
                    type="number"
                    id="paymentAmount"
                    name="paymentAmount"
                    value={formData.paymentAmount}
                    className={`form-control ${errors.paymentAmount ? "is-invalid" : ""}`}
                    onChange={handleChange}
                    // required
                  />
                  {errors.paymentAmount && (
                    <div className="invalid-feedback">{errors.paymentAmount}</div>
                  )}
                </div>
 
                <div className="form-group mb-2">
                  <label htmlFor="paymentDescription">Description:</label>
                  <textarea
                    id="paymentDescription"
                    name="paymentDescription"
                    value={formData.paymentDescription}
                    className={`form-control ${errors.paymentDescription ? "is-invalid" : ""}`}
                    onChange={handleChange}
                    // required
                  />
                  {errors.paymentDescription && (
                    <div className="invalid-feedback">{errors.paymentDescription}</div>
                  )}
                </div>
 
                <div className="mb-3">
                  <button className="btn btn-info mt-4 mr-3" type="submit">
                    Make payment
                  </button>
                  <button className="btn btn-danger mt-4" type="button" onClick={handleClose}>
                    Close
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
):(
        application.applicationStatus=="Rejected" ?(
          <>
          <strong>After following thorough review of your application, We regret to notify you that we cannot offer admission to you at this time..
We value the time and work you invested in your application and hope your future activities are filled with success.</strong>
 
          </>
 
        ):(<strong>
           Dear Applicant ,
         
         We are currently in the process of reviewing applications according to our requirements. We kindly ask for your patience as we finalize our decisions. We will notify you as soon as your application status is confirmed. Please feel free to reach out if you have any questions in the meantime.</strong>)
 
)}
    </>
</>
):(
    <>
 
<h1>"No Application  Found"</h1><br/>
<h3>Please Apply to  Program</h3>
</>
)}
</>
)
 
     }
 
 
    </div>
  )
}
 
export default PaymentComponent