import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

import axios from 'axios';
import { useNavigate, useParams } from "react-router-dom";
import { postApplication } from "../Services/ApplicationService";

function ApplyComponent() {
  const [showForm, setShowForm] = useState(false);

  const [applicantFullName, setApplicantFullName] = useState("");
  const [dateOfBirth, setDateOfBirth] = useState("");
  const [highestQualification, setHighestQualification] = useState("");
  const [finalYearPercentage, setFinalYearPercentage] = useState("");
  const [goals, setGoals] = useState("");
  const [emailId, setEmailId] = useState("");
  const [applicationStatus, setApplicationStatus] = useState("Pending");
  const [applicantInterviewFeedback, setApplicantInterviewFeedback] = useState("NA");
  const navigator = useNavigate();
  const { scheduleId } = useParams();

  const [selectedFiles, setSelectedFiles] = useState([]);
  const handleFileChange = (event) => {
    setSelectedFiles(event.target.files);
  };

  const handleFileUpload = async (event, appId) => {
    event.preventDefault();
    const formData = new FormData();
    for (let i = 0; i < selectedFiles.length; i++) {
      formData.append('file', selectedFiles[i]);
    }
    try {
      await axios.post(`http://localhost:8080/api/doc/${appId}/upload`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
    } catch (error) {
      console.error('Error uploading files', error);
      alert('Failed to upload files');
    }
  };

  const [errors, setErrors] = useState({
    applicantFullName: "",
    dateOfBirth: "",
    highestQualification: "",
    finalYearPercentage: "",
    goals: "",
    emailId: "",
    document: "",
  });

  function validateForm() {
    let valid = true;
    const errorsCopy = { ...errors };

    if (!applicantFullName) {
      errorsCopy.applicantFullName = "Name cannot be null";
      valid = false;
    } else if (applicantFullName.length > 40) {
      errorsCopy.applicantFullName = "Name should not exceed 40 characters";
      valid = false;
    } else {
      errorsCopy.applicantFullName = "";
    }

    if (!dateOfBirth) {
      errorsCopy.dateOfBirth = "Date of birth cannot be empty";
      valid = false;
    } else {
      const today = new Date();
      const dob = new Date(dateOfBirth);
      const ageDiff = today.getFullYear() - dob.getFullYear();
      const monthDiff = today.getMonth() - dob.getMonth();
      const dayDiff = today.getDate() - dob.getDate();
      const age = ageDiff - (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0) ? 1 : 0);

      if (dob >= today || age < 15) {
        errorsCopy.dateOfBirth = "Date of birth should be before today and at least 15 years ago";
        valid = false;
      } else {
        errorsCopy.dateOfBirth = "";
      }
    }

    if (!highestQualification) {
      errorsCopy.highestQualification = "Qualification cannot be null";
      valid = false;
    } else if (highestQualification.length > 20) {
      errorsCopy.highestQualification = "Qualification should not exceed 20 characters";
      valid = false;
    } else {
      errorsCopy.highestQualification = "";
    }

    if (!finalYearPercentage) {
      errorsCopy.finalYearPercentage = "Final year percentage cannot be empty";
      valid = false;
    }else if(finalYearPercentage > 100){
      errorsCopy.finalYearPercentage = "Final year percentage should be below 100";
      valid = false;
    }
    else {
      errorsCopy.finalYearPercentage = "";
    }

    // Uncomment and modify if goals is required
    // if (!goals) {
    //   errorsCopy.goals = "Goals cannot be empty";
    //   valid = false;
    // } else {
    //   errorsCopy.goals = "";
    // }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailId) {
      errorsCopy.emailId = "Email ID cannot be empty";
      valid = false;
    } else if (!emailRegex.test(emailId)) {
      errorsCopy.emailId = "Invalid email format";
      valid = false;
    } else {
      errorsCopy.emailId = "";
    }

    if (selectedFiles.length === 0) {
      errorsCopy.document = "Document cannot be empty";
      valid = false;
    } else {
      errorsCopy.document = "";
    }

    setErrors(errorsCopy);
    return valid;
  }

  const submitApplication =  (e) => {
    e.preventDefault();
    if (validateForm()) {
      const application = {
        applicantFullName,
        dateOfBirth,
        highestQualification,
        finalYearPercentage,
        goals,
        emailId,
        applicationStatus,
        applicantInterviewFeedback,
      };
  //    try{
  //     const response = await postApplication(scheduleId, application)
      
  //       console.log(response.data);
  //       handleFileUpload(e, response.data.applicationId);
  //       navigator(`/dashboard`);
      
  //    }catch (error){
  //     handleError(error)
  //    }
      
  //   }
  // };

  postApplication(scheduleId, application).then((response)=>{
    console.log(response.data);
    handleFileUpload(e, response.data.applicationId);
    navigator(`/dashboard`);
  }).catch((error)=>{
    handleError(error)
  })

    }}



  const handleClose = () => {
    navigator('/dashboard');
  };

  const handleError = (error) => {
    if (error.response) {
      // Server responded with a status other than 200 range
      console.error("Server Error:", error.response.status, error.response.data);
      alert(`Error: ${error.response.data.message || error.response.statusText} (Status Code: ${error.response.status}) `);
      navigator('/program-schedule-apply');
    } else if (error.request) {
      // Request was made but no response received
      console.error("Network Error:", error.request);
      alert("Network error. Please try again later.");
    } else {
      // Something else happened
      console.error("Error:", error.message);
      alert(`Error: ${error.message}`);
    }
  };

  return (
    <div className="applydiv">
      <div className="container">
        <div className="row">
          <div className="card col-md-6 offset-md-3">
            <h2 className="text-center mt-5">Application Form</h2>
            <div className="card-body">
              <form>
                <div className="form-group mb-2">
                  <label htmlFor="name" className="form-label"><strong>Enter Full Name:</strong></label>
                  <input
                    type="text"
                    value={applicantFullName}
                    id="name"
                    name="name"
                    className={`form-control ${errors.applicantFullName ? 'is-invalid' : ''}`}
                    onChange={(e) => setApplicantFullName(e.target.value)}
                  />
                  {errors.applicantFullName && (
                    <div className="invalid-feedback">{errors.applicantFullName}</div>
                  )}
                </div>

                <div className="form-group mb-2">
                  <label><strong>Date Of Birth:</strong></label>
                  <input
                    type="date"
                    value={dateOfBirth}
                    name="date"
                    className={`form-control ${errors.dateOfBirth ? 'is-invalid' : ''}`}
                    onChange={(e) => setDateOfBirth(e.target.value)}
                  />
                  {errors.dateOfBirth && (
                    <div className="invalid-feedback">{errors.dateOfBirth}</div>
                  )}
                </div>

                <div className="form-group mb-2">
                  <label htmlFor="qualification"><strong>Highest Qualification:</strong></label>
                  <input
                    type="text"
                    value={highestQualification}
                    id="qualification"
                    name="qualification"
                    className={`form-control ${errors.highestQualification ? 'is-invalid' : ''}`}
                    onChange={(e) => setHighestQualification(e.target.value)}
                  />
                  {errors.highestQualification && (
                    <div className="invalid-feedback">{errors.highestQualification}</div>
                  )}
                </div>

                <div className="form-group mb-2">
                  <label htmlFor="percentage"><strong>Final Year Percentage:</strong></label>
                  <input
                    type="number"
                    value={finalYearPercentage}
                    id="percentage"
                    name="percentage"
                    className={`form-control ${errors.finalYearPercentage ? 'is-invalid' : ''}`}
                    onChange={(e) => setFinalYearPercentage(e.target.value)}
                  />
                  {errors.finalYearPercentage && (
                    <div className="invalid-feedback">{errors.finalYearPercentage}</div>
                  )}
                </div>

                <div className="form-group mb-2">
                  <label htmlFor="goals"><strong>Goals:</strong></label>
                  <input
                    type="text"
                    value={goals}
                    id="goals"
                    name="goals"
                    className={`form-control ${errors.goals ? 'is-invalid' : ''}`}
                    onChange={(e) => setGoals(e.target.value)}
                  />
                  {errors.goals && (
                    <div className="invalid-feedback">{errors.goals}</div>
                  )}
                </div>

                <div className="form-group mb-2">
                  <label htmlFor="email"><strong>Email:</strong></label>
                  <input
                    type="email"
                    value={emailId}
                    id="email"
                    name="email"
                    className={`form-control ${errors.emailId ? 'is-invalid' : ''}`}
                    onChange={(e) => setEmailId(e.target.value)}
                  />
                  {errors.emailId && (
                    <div className="invalid-feedback">{errors.emailId}</div>
                  )}
                </div>

                <div className="form-group mb-2">
                  <label htmlFor="document"><strong>Document:</strong></label>
                  <input
                    type="file"
                    className={`form-control ${errors.document ? 'is-invalid' : ''}`}
                    onChange={handleFileChange}
                    required
                  />
                  {errors.document && (
                    <div className="invalid-feedback">{errors.document}</div>
                  )}
                </div>

                <div className="mb-3 mt-4">
                  <button className="btn btn-info mt-4 mr-3" type="button" onClick={submitApplication}>Submit Application</button>
                  <button className="btn btn-danger mt-4 mr-3" type="button" onClick={handleClose}>Close</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ApplyComponent;
