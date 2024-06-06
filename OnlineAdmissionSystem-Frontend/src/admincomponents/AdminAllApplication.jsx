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
import { deleteApplication, getAllApplication } from "../Services/ApplicationService";





function AdminAllApplication() {


    const [application, setApplication] = useState([]);
    const [searchQuery, setSearchQuery] = useState(""); // New state for search query
   
    const navigator = useNavigate("");
    const [selectedStatus, setSelectedStatus] = useState("View All");
   
    const handleSelect = (eventKey) => {
      setSelectedStatus(eventKey);
    };
   
    const handleSearchChange = (e) => {
      setSearchQuery(e.target.value);
    };
   
    const removeApplication = (id) => {
      deleteApplication(id).then((response) => {
        console.log(response);
        getAllApplication()
          .then((response) => {
            setApplication(response.data);
          })
          .catch((error) => {
            console.log(error);
          });
      }).catch((error) => {
        console.log(error);
      });
    };
   
    const acceptedCount = application.filter(
      (e) => e.applicationStatus.trim() === "Accepted"
    ).length;
    const rejectedCount = application.filter(
      (e) => e.applicationStatus.trim() === "Rejected"
    ).length;
    const pendingCount = application.filter(
      (e) => e.applicationStatus.trim() === "Pending"
    ).length;
   
    useEffect(() => {
      getAllApplication()
        .then((response) => {
          setApplication(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    }, []);
   
    const viewApplication = (appId) => {
      navigator(`/admin-application/${appId}`);
    };
   
    const filteredApplications = application.filter((e) => {
      return (
        (selectedStatus === "View All" || e.applicationStatus === selectedStatus) &&
        (e.applicationId.toString().includes(searchQuery) ||
          e.applicantFullName.toLowerCase().includes(searchQuery.toLowerCase()) ||
          e.emailId.toLowerCase().includes(searchQuery.toLowerCase()))
      );
    });



  return (
    (application.length>0)?(<div className="adminallapplicationdiv">
        
    <Container>
    <h1 className="text-center my-4">APPLICATION PANEL</h1>
    
    <Row className="mb-4">
      <Col md={3}>
        <Card className="text-center">
          <Card.Body>
            <Card.Title>
              <FontAwesomeIcon icon={faUsers} /> Total Applications
            </Card.Title>
            <Card.Text>{application.length}</Card.Text>
          </Card.Body>
        </Card>
      </Col>
      <Col md={3}>
        <Card className="text-center">
          <Card.Body>
            <Card.Title>
              <FontAwesomeIcon icon={faStop} /> Pending Applications
            </Card.Title>
            <Card.Text>{pendingCount}</Card.Text>
          </Card.Body>
        </Card>
      </Col>
      <Col md={3}>
        <Card className="text-center">
          <Card.Body>
            <Card.Title>
              <FontAwesomeIcon icon={faArrowRight} /> Accepted Applications
            </Card.Title>
            <Card.Text>{acceptedCount}</Card.Text>
          </Card.Body>
        </Card>
      </Col>
      <Col md={3}>
        <Card className="text-center">
          <Card.Body>
            <Card.Title>
              <FontAwesomeIcon icon={faClose} /> Rejected Applications
            </Card.Title>
            <Card.Text>{rejectedCount}</Card.Text>
          </Card.Body>
        </Card>
      </Col>
    </Row>
  </Container>

  <div className="container cc">
    <h2 className="text-center">List of Applications</h2>
   
    <div className="mb-3">
      <label htmlFor="status-dropdown" className="form-label">Select Application Status: </label>
      <DropdownButton
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

    <div className="mb-4 d-flex" style={{width:"310px"}}>
      <label className="mr-3"><b>Search:-</b></label>
      <input
        type="text"
        className="form-control"
        placeholder="Search by ID, Name or Email"
        value={searchQuery}
        onChange={handleSearchChange}
      />
    </div>

    <div className="flex-table">
      <table className="table table-striped table-bordered">
        <thead>
          <tr>
            <th>Application ID</th>
            <th>Applicant Name</th>
            <th>Application Status</th>
            <th>Email ID</th>
            <th style={{ textAlign: "center" }}>Actions</th>
          </tr>
        </thead>

        <tbody>
          {filteredApplications.map((e) => (
            <tr key={e.applicationId}>
              <td>{e.applicationId}</td>
              <td>{e.applicantFullName}</td>
              <td>{e.applicationStatus}</td>
              <td>{e.emailId}</td>
              <td>
                <div className="d-flex justify-content-center">
                  <button
                    onClick={() => {
                      viewApplication(e.applicationId);
                    }}
                    className="btn btn-info"
                    style={{ width: '70px', marginRight: '10px' }}
                  >
                    View
                  </button>
                  <button
                    disabled={e.applicationStatus !== "Rejected"}
                    className="btn btn-danger"
                    onClick={() => removeApplication(e.applicationId)}
                    style={{ width: '70px' }}
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
          <h2 className="noData">No Application Found</h2>
        </div>)
  )
}

export default AdminAllApplication