import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import { useEffect } from "react";
import { addBranch, getBranch, updateBranch } from "../Services/BranchService";

function AdminBranchComponent() {
  const [branchId, setBranchId] = useState("");
  const [branchName, setBranchName] = useState("");
  const [branchDescription, setBranchDescription] = useState("");

  const [errors, setErrors] = useState({
    branchId: "",
    branchName: "",
    branchDescription: "",
  });

  const { collegeId } = useParams();
  const { courseId } = useParams();
  const { branchId1 } = useParams();

  const navigator = useNavigate();

  useEffect(() => {
    if (branchId1) {
      getBranch(branchId1)
        .then((response) => {
          setBranchId(response.data.branchId);
          setBranchName(response.data.branchName);
          setBranchDescription(response.data.branchDescription);
          // console.log(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  }, [branchId1]);

  function saveOrUpdateBranch(e) {
    e.preventDefault();

    if (validateForm()) {
      const branch = { branchName, branchDescription };
      console.log(branch);

      if (branchId1) {
        updateBranch(branch, branchId1)
          .then((response) => {
            console.log(response.data);
            //   console.log(categoryId)
            alert("Branch Updated Successfully");
            navigator(`/branch-info/${collegeId}/course/${courseId}`);
          })
          .catch((error) => {
            console.error(error);
          });
      } else {
        addBranch(collegeId, courseId, branch)
          .then((response) => {
            console.log(response.data);
            alert("Branch Added Successfully");
            navigator(`/branch-info/${collegeId}/course/${courseId}`);
          })
          .catch((error) => {
            handleError(error)
          });
      }
    }
  }

  // function validateForm() {
  //   let valid = true;
  //   const errorsCopy = { ...errors };

  //   if (branchName.trim()) {
  //     errorsCopy.branchName = "";
  //   } else {
  //     errorsCopy.branchName = "Branch Name is required";
  //     valid = false;
  //   }

  //   if (branchDescription.trim()) {
  //     errorsCopy.branchDescription = "";
  //   } else {
  //     errorsCopy.branchDescription = "Branch Description is required";
  //     valid = false;
  //   }

  //   setErrors(errorsCopy);
  //   return valid;
  // }





  function validateForm() {
    let valid = true;
    const errorsCopy = { ...errors };
 
    if (branchName.trim()) {
        if (branchName.length <= 50) {
            errorsCopy.branchName = '';
        } else {
            errorsCopy.branchName = 'Branch name cannot exceed 20 characters';
            valid = false;
        }
    } else {
        errorsCopy.branchName = 'Branch name is required';
        valid = false;
    }
 
    if (branchDescription.trim()) {
        if (branchDescription.length <= 100) {
            errorsCopy.branchDescription = '';
        } else {
            errorsCopy.branchDescription = 'Branch Description cannot exceed 50 characters';
            valid = false;
        }
    } else {
        errorsCopy.branchDescription = 'Branch Description is required';
        valid = false;
    }
 
    setErrors(errorsCopy);
    return valid;
  }




  const handleError = (error) => {
    if (error.response) {
      // Server responded with a status other than 200 range
      console.error("Server Error:", error.response.status, error.response.data);
      alert(`Error: ${error.response.data.message || error.response.statusText} (Status Code: ${error.response.status}) `);
      // navigator('/program-schedule-apply');
    } else if (error.request) {
      // Request was made but no response received
      console.error("Network Error:", error.request);
      alert("Network error. Please try again later.");
    } else {
      // Something else happened
      console.error("Error:", error.message);
      alert(`Error: ${error.message}`);
    }
  };


  function pageTitle() {
    if (branchId1) {
      return <h2 className="text-center mt-4">Update Branch</h2>;
    } else {
      return <h2 className="text-center mt-4">Add Branch</h2>;
    }
  }

  function handleClose() {
    navigator(`/branch-info/${collegeId}/course/${courseId}`);
  }

  return (
    <div>
      <div className="container cc">
        <br />
        <br />
        <div className="row">
          <div className="card col-md-6 offset-md-3 offset-md-3">
            {pageTitle()}
            <div className="card-body">
              <form>
                <div className="form-group mb-2">
                  {branchId1 && (
                    <>
                      <label className="form-label">
                        <strong>Branch Id:</strong>
                      </label>
                      <input
                        type="text"
                        placeholder="Enter Branch Id"
                        name="branchId"
                        disabled
                        value={branchId}
                        className={`form-control`}
                        onChange={() => {}}
                      ></input>
                    </>
                  )}
                  {/* {errors.courseId && <div className="invalid-feedback">{errors.courseId}</div>} */}
                </div>
                <div className="form-group mb-2">
                  <label className="form-label">
                    <strong>Branch Name:</strong>
                  </label>
                  <input
                    type="text"
                    placeholder="Enter Branch Name"
                    name="name"
                    value={branchName}
                    className={`form-control ${
                      errors.branchName ? "is-invalid" : ""
                    }`}
                    onChange={(e) => setBranchName(e.target.value)}
                  ></input>
                  {errors.branchName && (
                    <div className="invalid-feedback">{errors.branchName}</div>
                  )}
                </div>
                <div className="form-group mb-2">
                  <label className="form-label">
                    <strong>Branch Description:</strong>
                  </label>
                  <input
                    type="text"
                    placeholder="Enter Branch Description"
                    name="description"
                    value={branchDescription}
                    className={`form-control ${
                      errors.branchDescription ? "is-invalid" : ""
                    }`}
                    onChange={(e) => setBranchDescription(e.target.value)}
                  ></input>
                  {errors.branchDescription && (
                    <div className="invalid-feedback">
                      {errors.branchDescription}
                    </div>
                  )}
                </div>

<div className="mb-3">
                <button
                  className="btn btn-info mt-4 mr-3"
                  onClick={saveOrUpdateBranch}
                >
                  Submit
                </button>
                <button
                  className="btn btn-danger mt-4"
                  onClick={handleClose}
                  type="button"
                >
                  Close
                </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
      f
    </div>
  );
}

export default AdminBranchComponent;
