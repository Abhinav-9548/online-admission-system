import React, { useEffect, useState } from "react";
import {  useNavigate, useParams } from "react-router-dom";
import { admissionById } from "../Services/AdmissionService";
import { getApplicationId } from "../Services/ApplicationService";


function StudentAdmissionComponent() {


    const [isVisible, setIsVisible] = useState(false);
    const [admission, setAdmission] = useState([]);
    const[application ,setApplication]=useState([]);
   
    const navigator=useNavigate();
    console.log("Admission details received:", admission);
   
    const handleGoBack = () => {
      navigator('/dashboard')
    };
    const {appId}=useParams();
   
    useEffect(() => {
      admissionById(appId)
        .then((response) => {
          setAdmission(response.data);
          console.log(response.data);
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
   
   
   
      setIsVisible(true);
    }, []);



  return (
    <div className="studentadmissioncomponentdiv">
        
        {admission.admissionStatus  ? (
        <>
          <div className={`confirmation-page ${isVisible ? "visible" : ""}`}>
            <div className="confirmation-message">
                {
                    admission.admissionStatus=="Accepted" ?(<>
                    <h1>Admission confirmed successfully!</h1>
              <p>
                Thank you for choosing our institution. Your admission is
                confirmed. If there’s anything you need, please don’t hesitate
                to reach out to us!
              </p>
              <div className="buttons">
                {/* <button className="view-details-btn">
                  DashBoard
                </button> */}
                <button className="go-back-btn" onClick={handleGoBack}>
                 DashBoard
                </button>
              </div></>):(
                <>
                <><h1>"No Payment  Found"</h1><br/>
        <h3>Please Pay the  Fees the confirm the admission</h3>
        <button id="applyBtn" onClick={()=>{navigator(`/application/${appId}/payment`)}} > Make Payment</button>
        </>
                </>
              )
                }
             
             
            </div>
            <div className="admission-details">
              <h2>Admission details</h2>
              <p>
                <strong>Email ID:</strong> {admission.emailId || "N/A"}
              </p>
              <p>
                <strong>Admission Status:</strong>{" "}
                {admission.admissionStatus || "N/A"}
              </p>
              <p>
                <strong>College:</strong>{" "}
                {admission.college ? admission.college.collegeName : "N/A"}
              </p>
              <p>
                <strong>Program:</strong>{" "}
                {admission.program ? admission.program.programName : "N/A"}
              </p>
              <p>
                <strong>Course:</strong>{" "}
                {admission.course ? admission.course.courseName : "N/A"}
              </p>
              <p>
                <strong>Year:</strong> {admission.year || "N/A"}
              </p>
            </div>
          </div>
        </>
      ) : (<>
        {application.applicationId ?(<>
       
            { admission.admissionStatus!="Accepted" && (<><h1>"No Payment  Found"</h1><br/>
        <h3>Please Pay the  Fees</h3>
        <button id="applyBtn" onClick={()=>{navigator(`/application/${appId}/payment`)}} > Make Payment</button>
        </>)}
       
        </>
        ):(
            <>
       
        <h1>"No Application  Found"</h1><br/>
        <h3>Please Apply to  Program</h3>
        </>
        )}
        </>
        )}

    </div>
  )
}

export default StudentAdmissionComponent