import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faArrowRight,
  faClose,
  faStop,
  faUsers,
} from "@fortawesome/free-solid-svg-icons";
import { Dropdown, DropdownButton } from "react-bootstrap";
import { deleteAdmission, getAllAdmission, getAllColleges } from "../Services/AdmissionService";


function AdminAllAdmission() {


    const [admission, setAdmission] = useState([]);
    const [college, setCollege] = useState([]);
   
    const navigator = useNavigate("");
   
    const [selectedStatus, setSelectedStatus] = useState("View All");
   
    const handleSelect = (eventKey) => {
      setSelectedStatus(eventKey);
    };
   
    const acceptedCount = admission.filter(
      (e) => e.admissionStatus.trim() === "Accepted"
    ).length;
    //   const rejectedCount = application.filter(
    //     (e) => e.applicationStatus.trim() === "Rejected"
    //   ).length;
    const pendingCount = admission.filter(
      (e) => e.admissionStatus.trim() === "Pending"
    ).length;
   
    function getAdmission(){
      getAllAdmission()
        .then((response) => {
          setAdmission(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    }  
   
    useEffect(() => {
      getAdmission();
   
        getAllColleges()
        .then((response) => {
          setCollege(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
   
   
    }, []);
   
    const handleDelete=(id)=>{
      deleteAdmission(id).then((response)=>{
          getAdmission();
   
      }).catch((error)=>{console.log(error)})
    }
   
    const viewAdmission = (appId) => {
      navigator(`/admission/${appId}`);
    };


  return (
    (admission.length>0)?(<div className="adminalladmissiondiv">
        
    <Container>
    <h1 className="text-center ">ADMISSION PANEL</h1>
    <br />
    <br />
    <Row className="mb-3">
      <Col md={4}>
        <Card className="text-center">
          <Card.Body>
            <Card.Title>
              <FontAwesomeIcon icon={faUsers} /> Total Admissions
            </Card.Title>
            <Card.Text>{admission.length}</Card.Text>
          </Card.Body>
        </Card>
      </Col>
      <Col md={4}>
        <Card className="text-center">
          <Card.Body>
            <Card.Title>
              <FontAwesomeIcon icon={faStop} /> Pending Admissions
            </Card.Title>
            <Card.Text>{pendingCount}</Card.Text>
          </Card.Body>
        </Card>
      </Col>
      <Col md={4}>
        <Card className="text-center">
          <Card.Body>
            <Card.Title>
              <FontAwesomeIcon icon={faArrowRight} /> Accepted Admissions
            </Card.Title>
            <Card.Text>{acceptedCount}</Card.Text>
          </Card.Body>
        </Card>
      </Col>
      {/* <Col md={3}>
        <Card className="text-center">
          <Card.Body>
            <Card.Title>
              <FontAwesomeIcon icon={faClose} /> Rejected Applications
            </Card.Title>
            <Card.Text></Card.Text>
          </Card.Body>
        </Card>
      </Col> */}
    </Row>
  </Container>

  <div className="container cc">
    <h2 className="text-center">List of Admissions</h2>

    <div style={{marginBottom:"20px"}}>
      <label htmlFor="status-dropdown"><strong>Select College :</strong> </label>

      <DropdownButton
        id="status-dropdown"
        title={selectedStatus}
        onSelect={handleSelect}
      >
         <Dropdown.Item eventKey="View All">View All</Dropdown.Item>
        {college.map((e)=>(
            <Dropdown.Item eventKey={e.collegeName}>{e.collegeName}</Dropdown.Item>
        )
             
        )}

      </DropdownButton>
      {/* <p>Selected Status: {selectedStatus}</p> */}
    </div>
    {/* <div className="buttondiv">
      <button onClick={() => setStatus(false)} className="btn  btn-primary">
        view all applications
      </button>
      <button onClick={() => setStatus(true)} className="btn btn-primary">
        view pending applications
      </button>
    </div> */}

    <div className="flex-table">
      <table className="table table-striped table-bordered">
        <thead>
          <tr>
            <th> Email ID</th>
            <th>Applicantion ID</th>
            <th>Applicant Name</th>
            <th>Admission Status</th>
            <th>College</th>
            <th>Program</th>
            <th>Year</th>

            <th style={{ textAlign: "center" }}>Actions</th>
          </tr>
        </thead>

        <tbody>
          {admission
            .filter((e) =>
              selectedStatus != "View All"
                ? e.college.collegeName == `${selectedStatus}`
                : () => {}
            )
            .map((e) => (
              <tr key={e.admissionId}>
                <td>{e.emailId}</td>
                <td>{e.application.applicationId}</td>
                <td>{e.application.applicantFullName}</td>

                <td>{e.admissionStatus}</td>
                <td>{e.college.collegeName}</td>
                <td>{e.program.programName}</td>
                <td>{e.year}</td>
                <td>
                  <div className="div1 ">
                    <button
                      disabled={e.admissionStatus!="Pending"}
                    className='btn btn-danger'
                      onClick={() => {
                        handleDelete(e.admissionId);
                      }}
                     
                    >
                      Delete
                    </button>
             
                  </div>
                </td>
              </tr>
            ))}
        </tbody>
      </table>
    </div>
  </div>



</div>):(<div className="no">
          <h2 className="noData">No Admission Found</h2>
        </div>)
  )
}

export default AdminAllAdmission