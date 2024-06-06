import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate, useParams } from "react-router-dom";
 
 
import Card from "react-bootstrap/Card";
import { Container, Row, Col, Button } from 'react-bootstrap';
import { getApplicationId } from "../Services/ApplicationService";
import { admissionById } from "../Services/AdmissionService";
 
 
function StudentApplication() {
 
 
 
    const [applicationId, setApplicationId] = useState("");
    const [applicantFullName, setApplicantFullName] = useState("");
    const [dateOfBirth, setDateOfBirth] = useState("");
    const [highestQualification, setHighestQualification] = useState("");
    const [finalYearPercentage, setFinalYearPercentage] = useState("");
    const [goals, setGoals] = useState("");
    const [emailId, setEmailId] = useState("");
    const [applicationStatus, setApplicationStatus] = useState("");
    const [dateOfInterview, setDateOfInterview] = useState("");
    const [applicantInterviewFeedback, setApplicantInterviewFeedback] =
      useState("na");
    const [schedule, setSchedule] = useState({});
    const [programScheduledResponseDto, setProgramScheduledResponseDto] =
      useState({});
      const[admission,setAdmission]=useState([]);
   
    const navigator = useNavigate();
    const { appId } = useParams();
   
    useEffect(() => {
      getApplicationId(appId)
        .then((response) => {
          console.log(response.data);
          setApplicationId(response.data.applicationId);
          setApplicantFullName(response.data.applicantFullName);
          setDateOfBirth(response.data.dateOfBirth);
          setHighestQualification(response.data.highestQualification);
          setFinalYearPercentage(response.data.finalYearPercentage);
          setGoals(response.data.goals);
          setEmailId(response.data.emailId);
          setApplicationStatus(response.data.applicationStatus);
          setDateOfInterview(response.data.dateOfInterview);
          setSchedule(response.data.schedule);
          setProgramScheduledResponseDto(
            response.data.schedule.programScheduledResponseDto
          );
        })
        .catch((error) => {
          console.log(error);
        });
 
        admissionById(appId)
        .then((response) => {
          setAdmission(response.data);
          console.log(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
 
    }, [appId]);
 
 
 
  return (
    <div className="studentapplicationdiv">
     {applicantFullName ?( <div className="d-flex justify-content-center">
     <Card style={{ width: "70%" }}>
    <Card.Body>
      <Card.Title>
        <h3>Applicant Details</h3>
      </Card.Title>
      <hr />
      <Container className="StudentApplication">
        <Row xs={1} md={2} className="mb-3">
          <Col>
            <div className="d-flex">
              <p className="h5 font-weight-bold mb-1">Application Id:</p>
              <p className="ml-2 font-weight-semibold">{applicationId}</p>
            </div>
          </Col>
          <Col>
            <div className="d-flex">
              <p className="h5 font-weight-bold mb-1">Applicant Full Name:</p>
              <p className="ml-2 font-weight-semibold">{applicantFullName}</p>
            </div>
          </Col>
        </Row>
        <Row xs={1} md={2} className="mb-3">
          <Col>
            <div className="d-flex">
              <p className="h5 font-weight-bold mb-1">Date Of Birth:</p>
              <p className="ml-2 font-weight-semibold">{dateOfBirth}</p>
            </div>
          </Col>
          <Col>
            <div className="d-flex">
              <p className="h5 font-weight-bold mb-1">Email Id:</p>
              <p className="ml-2 font-weight-semibold">{emailId}</p>
            </div>
          </Col>
        </Row>
        <Row xs={1} md={2} className="mb-3">
          <Col>
            <div className="d-flex">
              <p className="h5 font-weight-bold mb-1">Highest Qualification:</p>
              <p className="ml-2 font-weight-semibold">{highestQualification}</p>
            </div>
          </Col>
          <Col>
            <div className="d-flex">
              <p className="h5 font-weight-bold mb-1">Final Year Percentage:</p>
              <p className="ml-2 font-weight-semibold">{finalYearPercentage}</p>
            </div>
          </Col>
        </Row>
        <Row xs={1} md={2} className="mb-3">
          <Col>
            <div className="d-flex">
              <p className="h5 font-weight-bold mb-1">Application Status:</p>
              <p className="ml-2 font-weight-semibold">{applicationStatus}</p>
            </div>
          </Col>
          <Col>
            <div className="d-flex">
              <p className="h5 font-weight-bold mb-1">Interview Date:</p>
              <p className="ml-2 font-weight-semibold">
                {dateOfInterview === "" || dateOfInterview == null
                  ? "Not Available"
                  : dateOfInterview}
              </p>
            </div>
          </Col>
        </Row>
        <div className="mt-5">
          <h3>Program Schedule Details</h3>
          <hr />
          <Row xs={1} md={2} className="mb-3">
            <Col>
              <div className="d-flex">
                <p className="h5 font-weight-bold mb-1">University Name:</p>
                <p className="ml-2 font-weight-semibold">{programScheduledResponseDto.name}</p>
              </div>
            </Col>
            <Col>
              <div className="d-flex">
                <p className="h5 font-weight-bold mb-1">College:</p>
                <p className="ml-2 font-weight-semibold">{programScheduledResponseDto.collegeName}</p>
              </div>
            </Col>
          </Row>
          <Row xs={1} md={2} className="mb-3">
            <Col>
              <div className="d-flex">
                <p className="h5 font-weight-bold mb-1">Program Name:</p>
                <p className="ml-2 font-weight-semibold">{programScheduledResponseDto.programName}</p>
              </div>
            </Col>
            <Col>
              <div className="d-flex">
                <p className="h5 font-weight-bold mb-1">Course Name:</p>
                <p className="ml-2 font-weight-semibold">{programScheduledResponseDto.courseName}</p>
              </div>
            </Col>
          </Row>
          <Row xs={1} md={2} className="mb-3">
            <Col>
              <div className="d-flex">
                <p className="h5 font-weight-bold mb-1">Branch Name:</p>
                <p className="ml-2 font-weight-semibold">{programScheduledResponseDto.branchName}</p>
              </div>
            </Col>
            <Col>
              <div className="d-flex">
                <p className="h5 font-weight-bold mb-1">Eligibility:</p>
                <p className="ml-2 font-weight-semibold">{programScheduledResponseDto.eligibility}</p>
              </div>
            </Col>
          </Row>
          <Row xs={1} md={2} className="mb-3">
            <Col>
              <div className="d-flex">
                <p className="h5 font-weight-bold mb-1">Start Date:</p>
                <p className="ml-2 font-weight-semibold">{schedule.startDate}</p>
              </div>
            </Col>
            <Col>
              <div className="d-flex">
                <p className="h5 font-weight-bold mb-1">End Date:</p>
                <p className="ml-2 font-weight-semibold">{schedule.endDate}</p>
              </div>
            </Col>
          </Row>
        </div>
      </Container>
      <Container className="d-flex justify-content-center mt-4">
        <Row className="w-100">
         {applicationStatus=="Accepted" ?(<>
          <Col className="d-flex justify-content-center">
      { admission.admissionStatus=="Accepted" ?(<>
        <Button
              variant="primary"
              onClick={() => navigator(`/application/${appId}/payment`)}
              className="w-50 mx-2"
            >
              View Payment Details
            </Button>
            <Button
              variant="secondary"
              onClick={() => navigator(`/admission/${appId}`)}
              className="w-50 mx-2"
            >
              View Admission
            </Button>
      </>): (<>
        <Button
              variant="primary"
              onClick={() => navigator(`/application/${appId}/payment`)}
              className="w-50 mx-2"
            >
              Make Payment
            </Button>
      </>)  }
          </Col>
         
         </>):(
         applicationStatus=="Pending" ? (<div className="mt-4" style={{color:"red"}}> 
         <strong>NOTE: Please wait until your application gets accepted. It is currently being reviewed.</strong>
         </div>):(<div className="mt-4" style={{color:"red"}}>

          <strong>Unfortunately, your application has not been accepted at this time. We appreciate your interest and wish you the best in your future endeavors.</strong>
        
         </div>)
         )
         
         
         }
        </Row>
      </Container>
    </Card.Body>
  </Card>
 
 
 
 
      </div>
):(<>
<h1>"No Applications Found"</h1><br/>
<h3>Please Apply To Program</h3>
</>
)
 
     }
     
    </div>
  )
}
 
export default StudentApplication