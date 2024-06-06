import React, { useEffect, useState } from "react";
import { Container, Card, Button } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import { getAllBranchesByCourseId } from "../Services/BranchService";

function BranchComponent() {
  const [branch, setBranch] = useState([]);
  const navigator = useNavigate();
  const {courseId} = useParams();
  //   const { productId } = useParams();
  //console.log(courseId);
  useEffect(() => {
    getAllBranches();
  }, []);

  function programScheduledByBranch(branchId) {
    navigator(`/programScheduledByBranch/${branchId}`);
  }

  function getAllBranches() {
    getAllBranchesByCourseId(courseId)
      .then((response) => {
        console.log(response.data);
        setBranch(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }

  function print() {
    if (branch.length > 0) {
      return (
        <div>
          <Container>
            <h2 className="text-center mb-4">Branch Details</h2>
            <div className="row">
              {branch.map((branch) => (
                <div key={branch.branchId} className="col-md-6 mb-4">
                  <Card className="collegeCard">
                    <Card.Body>
                      <Card.Title className="branchTitle">
                        <b>
                          <u>{branch.branchName}</u>
                        </b>
                      </Card.Title>
                      <Card.Text>
                        <strong>Description:</strong> {branch.branchDescription}
                      </Card.Text>
                      <button
                        className="btn btn-info buttonclass"
                        onClick={() =>
                          programScheduledByBranch(branch.branchId)
                        }
                      >
                        Apply Now
                      </button>
                    </Card.Body>
                  </Card>
                </div>
              ))}
            </div>
          </Container>
        </div>
      );
    } else {
      return(
      <div className="no">
        <h2 className="noData">No Branch Found</h2>
      </div>);
    }
  }

  return <div className="branchdiv">{print()}</div>
}

export default BranchComponent;
