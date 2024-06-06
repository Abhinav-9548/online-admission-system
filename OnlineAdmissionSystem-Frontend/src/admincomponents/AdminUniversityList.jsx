import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  deleteUniversityById,
  getAllUniversity,
} from "../Services/UniversityService";

const AdminUniversityList = () => {
  const [university, setUniversity] = useState([]);
  const navigatore = useNavigate();

  function getAllUniversityfunc() {
    getAllUniversity()
      .then((response) => {
        setUniversity(response.data);
        console.log(university);
      })
      .catch((error) => {
        console.error(error);
      });
  }

  useEffect(() => {
    getAllUniversityfunc();
  }, []);

  function deleteUni(universityId) {
    //e.preventDefault();
    console.log(universityId);
    deleteUniversityById(universityId)
      .then((response) => {
        console.log(response.data);
        getAllUniversityfunc();
      })
      .catch((error) => {
        console.error(error);
      });

    alert("University Deleted Successfully");
  }

  function updateUni(universityId) {
    // e.preventDefault();
    navigatore(`/update-university/${universityId}`);
  }

  function addUniversity() {
    navigatore(`/add-university`);
  }

  function collegeInfo(universityId) {
    navigatore(`/college-info/${universityId}`);
  }

  function print() {
    if (university.length > 0) {
      return (
        <div>
          <div className="container mt-5">
            <h2 className="text-center mb-5">List of University</h2>

            <table className="table table-striped table-bordered mt-5">
              <thead>
                <tr>
                  <th className="text-center">University Id</th>
                  <th className="text-center">University Name</th>
                  <th className="text-center">University Address</th>
                  <th className="text-center">Actions</th>
                </tr>
              </thead>
              <tbody>
                {university.map((uni) => (
                  <tr key={uni.universityId}>
                    <td>{uni.universityId}</td>
                    <td>{uni.name}</td>
                    <td>
                      {uni.address.city}, {uni.address.state},{" "}
                      {uni.address.country}
                      <br /> {uni.address.zipcode}
                    </td>
                    <td>
                      <div className="button">
                        <button
                          className="btn btn-primary mb-2"
                          type="button"
                          onClick={() => collegeInfo(uni.universityId)}
                        >
                          College-Info
                        </button>

                        <button
                          className="btn btn-info mb-2"
                          type="button"
                          onClick={() => updateUni(uni.universityId)}
                        >
                          Update
                        </button>

                        <button
                          className="btn btn-danger mb-2"
                          type="button"
                          onClick={() => deleteUni(uni.universityId)}
                        >
                          Delete
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>

            <button
              className="btn btn-success md-2"
              onClick={addUniversity}
            >
              Add University
            </button>
          </div>
        </div>
      );
    } else {
      return (
        <div className="noData">
          <h1>No University Found</h1>
          <div className="">
            <center>
              <button
                className="btn btn-success  me-md-2"
                onClick={addUniversity}
              >
                Add University
              </button>
            </center>
          </div>
        </div>
      );
    }
  }

  return <div className="universitydiv">{print()}</div>;
};

export default AdminUniversityList;
