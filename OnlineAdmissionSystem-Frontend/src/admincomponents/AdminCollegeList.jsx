import React, { useEffect } from "react";
import { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { collegeList, deleteCollege } from "../Services/CollegeService";

function AdminCollegeList() {
  const [college, setCollege] = useState([]);

  const navigator = useNavigate();

  const { universityId } = useParams();
  const { collegeId } = useParams();

  useEffect(() => {
    getAllColleges();
  }, []);

  function getAllColleges() {
    collegeList(universityId)
      .then((response) => {
        setCollege(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }

  function addNewCollege() {
    navigator(`/add-college/${universityId}`);
  }

  function updateCollege(collegeId) {
    navigator(`/update-college/${universityId}/${collegeId}`);
  }

  function programInfo(collegeId) {
    navigator(`/program-info/${collegeId}`);
  }

  function deleteCollegeDetails(collegeId) {
    console.log(collegeId);
    deleteCollege(collegeId)
      .then((response) => {
        getAllColleges();
      })
      .catch((error) => {
        console.log(error);
      });
  }

  function print() {
    if (college.length > 0) {
      return (
        <div>
          <div className="container mt-5">
            <h2 className="text-center mb-5">College Details</h2>

            <table className="table table-striped table-bordered mt-5">
              <thead>
                <tr>
                  <th className="text-center">College Id</th>
                  <th className="text-center">College Name</th>
                  <th className="text-center">College Address</th>
                  <th className="text-center">College Description</th>
                  <th className="text-center">Actions</th>
                </tr>
              </thead>
              <tbody>
                {college.map((college) => (
                  <tr key={college.collegeRegId}>
                    <td>{college.collegeRegId}</td>
                    <td>{college.collegeName}</td>
                    <td>
                      {college.address.city}, {college.address.state},{" "}
                      {college.address.country}
                      <br /> {college.address.zipcode}
                    </td>

                    {/* <td><button className="btn btn-primary mb-2" onClick={courseInfo(topic.id)}>Course Info</button></td> */}
                    {/* <td>{e.topic.id}</td> */}
                    {/* <td>{course.topic.id}</td> */}
                    {/* <td>{college.description}</td> */}
                    <td>{college.description}</td>
                    <td>
                      <div className="button">
                        <button
                          type="button"
                          className="btn btn-primary mb-2"
                          onClick={() => programInfo(college.collegeRegId)}
                        >
                          Program-Info
                        </button>
                        <button
                          type="button"
                          className="btn btn-info mb-2"
                          onClick={() => updateCollege(college.collegeRegId)}
                        >
                          Update
                        </button>
                        <button
                          type="button"
                          className="btn btn-danger mb-2"
                          onClick={() =>
                            deleteCollegeDetails(college.collegeRegId)
                          }
                        >
                          Delete
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            <button className="btn btn-success mb-2" onClick={addNewCollege}>
              Add College
            </button>
          </div>
        </div>
      );
    } else {
      return (
        <div className="noData">
          <h1 className="text-center">No College Found</h1>
          <div className="">
            <center>
              <button
                className="btn btn-success  me-md-2"
                onClick={addNewCollege}
              >
                Add College
              </button>
            </center>
          </div>
        </div>
      );
    }
  }

  return <div className="collegediv">{print()} </div>;
}

export default AdminCollegeList;
