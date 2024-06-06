import React, { useEffect, useState } from "react";

import { useNavigate, useParams } from "react-router-dom";
import { Button, Card } from 'react-bootstrap';

import { Container, Row, Col } from 'react-bootstrap';

import { getApplicationByEmail } from "../Services/ApplicationService";
import { Dropdown, DropdownButton } from "react-bootstrap";

function StudentAllApplication() {




  const [application, setApplication] = useState([]);
 
  const navigator = useNavigate("");
  const{emailId} =useParams();
 
  const [selectedStatus, setSelectedStatus] = useState("View All");
 
  const handleSelect = (eventKey) => {
    setSelectedStatus(eventKey);
  };
  function handleProgramScheduleApply()
  {
    navigator(`/program-schedule-apply`)
  }
//   const acceptedCount = application.filter(
//     (e) => e.applicationStatus.trim() === "Accepted"
//   ).length;
//   const rejectedCount = application.filter(
//     (e) => e.applicationStatus.trim() === "Rejected"
//   ).length;
//   const pendingCount = application.filter(
//     (e) => e.applicationStatus.trim() === "Pending"
//   ).length;
 
  useEffect(() => {
    getApplicationByEmail(emailId)
      .then((response) => {
        setApplication(response.data);
        // console.log(acceptedCount)
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);
 
  const viewApplication = (appId) => {
    navigator(`/application/${appId}`);
  };




  return (application.length>0 ?(<>
  
  <div className="studentallapplicationdiv">
    <h2 className="text-center">List of Applications</h2>
   

    <div>
      <label htmlFor="status-dropdown mb-4"><b>Select Application Status: </b></label>
     
      <DropdownButton style={{marginBottom:"20px"}}
        id="status-dropdown"
        title={selectedStatus}
        onSelect={handleSelect}
      >
        <Dropdown.Item eventKey="Accepted">Accepted</Dropdown.Item>
        <Dropdown.Item eventKey="Pending">Pending</Dropdown.Item>
        <Dropdown.Item eventKey="Rejected">Rejected</Dropdown.Item>
        <Dropdown.Item eventKey="View All">View All</Dropdown.Item>
      </DropdownButton>
     
    </div>
    

    <div className="flex-table">
      <table className="table table-striped table-bordered">
        <thead>
          <tr>
            <th style={{ textAlign: "center" }}>Application ID</th>
            <th style={{ textAlign: "center" }}>Applicant Name</th>
            <th style={{ textAlign: "center" }}>Application Status</th>

            <th style={{ textAlign: "center" }}>Actions</th>
          </tr>
        </thead>

        <tbody>
          {application
            .filter((e) => selectedStatus!="View All"? e.applicationStatus == `${selectedStatus}`:(()=>{}))
            .map((e) => (
              <tr key={e.applicationId}>
                <td>{e.applicationId}</td>
                <td>{e.applicantFullName}</td>
                <td>{e.applicationStatus}</td>
                <td>
                  
                    <button
                      onClick={() => {
                        viewApplication(e.applicationId);
                      }}
                      className="btn btn-info"
                    >
                      View
                    </button>
                 
                  
                </td>
              </tr>
            ))}
        </tbody>
      </table>
    </div>
  </div>
  </>):(<>
    <div className="noData">
          <h2 >No Application Found</h2>
          <Container className="d-flex justify-content-center" style={{ alignItems: 'center',flexDirection:"column",marginTop:'60px' }}>

<h5 style={{color:"black"}} >Apply here to start your journey! Admissions are now open.</h5>
<Button type="button" style={{width:"300px",marginTop:'10px'}} className="btn btn-info mb-2" onClick={handleProgramScheduleApply}>
                    Apply here
                  </Button>
  </Container>
        </div>
  
  </>)
  )
}

export default StudentAllApplication