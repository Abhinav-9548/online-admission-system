import React, { useEffect } from "react";
import { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
 
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { deleteBranch, getAllBranchesByCourseId } from "../Services/BranchService";


function AdminBranchList() {



    const [branch, setBranch] = useState([]);
 
  const navigator = useNavigate();
 
  const {courseId} = useParams();
  const {collegeId} = useParams();
//   const {collegeId} = useParams();
//   console.log(universityId);
  useEffect(() => {
    getAllBranches();
  }, []);
 
  function getAllBranches(){
    getAllBranchesByCourseId(courseId)
      .then((response) => {
        setBranch(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }
 
 
  function addNewBranch() {
    navigator(`/add-branch/${collegeId}/${courseId}`);
  }
 
  function updateBranchDetails(branchId){
    navigator(`/update-branch/${collegeId}/${courseId}/${branchId}`);
  }
 
  function deleteBranchDetails(branchId){
    console.log(branchId)
   deleteBranch(branchId).then((response)=>{
      getAllBranches();
    }).catch(error=>{
      console.log(error);
    })
 
  }


    function print(){
        if(branch.length > 0){
            return (
              <div>
                <div className="container mt-5">
                  <h2 className="text-center mb-5">Branch Details</h2>
                 
                  <table className="table table-striped table-bordered mt-5">
                    <thead>
                      <tr>
                        <th className="text-center">Branch Id</th>
                        <th className="text-center">Branch Name</th>
                        <th className="text-center">Branch Description</th>
                        <th className="text-center">Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      {branch.map((branch) => (
                        <tr key={branch.branchId}>
                          <td>{branch.branchId}</td>
                          <td>{branch.branchName}</td>
                          <td>{branch.branchDescription}</td>
                         
                          {/* <td><button className="btn btn-primary mb-2" onClick={courseInfo(topic.id)}>Course Info</button></td> */}
                          {/* <td>{e.topic.id}</td> */}
                          {/* <td>{course.topic.id}</td> */}
                          <td>
                            <div className="button">
                             
                              <button
                                type="button"
                                className="btn btn-info mb-2"
                                onClick={() => updateBranchDetails(branch.branchId)}
                              >
                                Update
                              </button>
                              <button
                                type="button"
                                className="btn btn-danger mb-2"
                                onClick={() => deleteBranchDetails(branch.branchId)}
                              >
                                Delete
                              </button>
                            </div>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                  <button className="btn btn-success me-md-2" onClick={addNewBranch}>
                    Add Branch
                  </button>
                </div>
                </div>
              );
        } else {
            return (
              <div className="noData">
                <h1 className="text-center">No Branch Found</h1>
                <div className="">
                  <center>
                    <button
                      className="btn btn-success md-2"
                      onClick={addNewBranch}
                    >
                      Add Branch
                    </button>
                  </center>
                </div>
              </div>
            );
          }
          }

  return (
    <div className="branchdiv">{print()}</div>
  )
}

export default AdminBranchList