import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate, useParams } from "react-router-dom";
 
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Dropdown from "react-bootstrap/Dropdown";
import Modal from "react-bootstrap/Modal";
import axios from 'axios';
import { getApplicationId, updateApplication } from "../Services/ApplicationService";
 
function AdminApplicationForm() {
 
 
    const [applicationId, setApplicationId] = useState("");
    const [applicantFullName, setApplicantFullName] = useState("");
    const [dateOfBirth, setDateOfBirth] = useState("");
    const [highestQualification, setHighestQualification] = useState("");
    const [finalYearPercentage, setFinalYearPercentage] = useState("");
    const [goals, setGoals] = useState("");
    const [emailId, setEmailId] = useState("");
    const [applicationStatus, setApplicationStatus] = useState("");
    const [dateOfInterview, setDateOfInterview] = useState("");
    const [applicantInterviewFeedback, setApplicantInterviewFeedback] = useState("na");
    const [schedule, setSchedule] = useState({});
    const [programScheduledResponseDto, setProgramScheduledResponseDto] = useState({});
    const [showUpdateModal, setShowUpdateModal] = useState(false);
    const [file, setFile] = useState('');
    const [errors, setErrors] = useState({
      dateOfInterview: "",
    });
   
    const navigate = useNavigate('');
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
          setProgramScheduledResponseDto(response.data.schedule.programScheduledResponseDto);
          setFile(response.data.document);
        })
        .catch((error) => {
          console.log(error);
        });
    }, [appId]);
   
    function validateForm() {
      let valid = true;
      const errorsCopy = { ...errors };
   
      if (!dateOfInterview) {
        errorsCopy.dateOfInterview = "Date of Interview cannot be empty";
        valid = false;
      } else {
        const today = new Date();
        const interviewDate = new Date(dateOfInterview);
   
        if (interviewDate <= today) {
          errorsCopy.dateOfInterview = "Date of Interview must be after today's date";
          valid = false;
        } else {
          errorsCopy.dateOfInterview = "";
        }
      }
   
      setErrors(errorsCopy);
      return valid;
    }
   
    const [uploadedFiles, setUploadedFiles] = useState([]);
    const fetchFiles = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/doc');
        setUploadedFiles(response.data);
      } catch (error) {
        console.error('Error fetching files', error);
      }
    };
   
    useEffect(() => {
      fetchFiles();
    }, []);
   
    const submitApplication = (e) => {
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
          dateOfInterview,
          applicantInterviewFeedback
        };
        updateApplication(appId, application)
          .then((response) => {
            console.log(response);
            navigate("/applications");
          })
          .catch((error) => {
            console.log(error);
          });
      }
    };
   
    const handleShowUpdateModal = () => setShowUpdateModal(true);
    const handleCloseUpdateModal = () => setShowUpdateModal(false);
 
 
 
  return (
    <div className="adminallapplicationformdiv">
       
        <div className="d-flex justify-content-center">
        <Card style={{ width: "70%" }}>
  <Card.Body>
    <Card.Title>
      <h3>Applicant Details</h3>
    </Card.Title>
    <hr />
    <Container className="applicationContainer">
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
            <p className="ml-2 font-weight-semibold">{(dateOfInterview === "" || dateOfInterview == null) ? "Not Available" : dateOfInterview}</p>
          </div>
        </Col>
      </Row>
      <br />
      <br />
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
    </Container>
    <div className="applicationContainer">
      <div className="d-flex justify-content-left">
        <div  aria-label="Inline buttons">
          <Button style={{width:"200px"}} variant="success" className="ml-2">
            <a className="custom-link3" target="_blank" href={`http://localhost:8080/api/doc/downloadfile/${file.documentId}`} download>Download Document </a>
          </Button>
          <Button  style={{width:"200px"}} variant="success" className="ml-2" onClick={handleShowUpdateModal}>
            Update
          </Button>
        </div>
      </div>
    </div>
  </Card.Body>
</Card>
 
      </div>
 
      <Modal show={showUpdateModal} onHide={handleCloseUpdateModal}>
        <Modal.Header closeButton >
          <Modal.Title>Update Details</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form>
            <div className="form-group mb-2">
              <br />
              <label>Application Status:</label>
              <Dropdown onSelect={(e) => setApplicationStatus(e)}>
                <Dropdown.Toggle variant="success" id="dropdown-basic">
                  {applicationStatus || "Select an option"}
                </Dropdown.Toggle>
                <Dropdown.Menu>
                  <Dropdown.Item eventKey="Accepted">Accepted</Dropdown.Item>
                  <Dropdown.Item eventKey="Rejected">Rejected</Dropdown.Item>
                  <Dropdown.Item eventKey="Pending">Pending</Dropdown.Item>
                </Dropdown.Menu>
              </Dropdown>
 
              {applicationStatus === "Accepted" && (
                <div>
                  <label>Interview Date:</label>
                  <input
                    type="date"
                    min={new Date().toISOString().split('T')[0]}
                    value={dateOfInterview}
                    name="dateOfInterview"
                    className={`form-control ${errors.dateOfInterview ? `is-invalid` : ``}`}
                    onChange={(e) => setDateOfInterview(e.target.value)}
                  />
                  {errors.dateOfInterview && (
                    <div className="invalid-feedback">{errors.dateOfInterview}</div>
                  )}
                </div>
              )}
 
              <br />
              <div>
                <button className="btn btn-primary" onClick={submitApplication} style={{ marginRight: "10px" }}>
                  Submit Application
                </button>
              </div>
            </div>
          </form>
        </Modal.Body>
      </Modal>
 
 
 
    </div>
  )
}
 
export default AdminApplicationForm