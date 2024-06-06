import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import { useEffect } from "react";
import {
  addProgram,
  getProgramById,
  updateProgram,
} from "../Services/ProgramService";

function AdminProgramComponent() {
  const [programId, setProgramId] = useState("");
  const [programName, setProgramName] = useState("");
  const [eligibility, setEligibility] = useState("");
  const [duration, setDuration] = useState("");
  const [degreeOffered, setDegreeOffered] = useState("");

  const [errors, setErrors] = useState({
    programId: "",
    programName: "",
    eligibility: "",
    duration: "",
    degreeOffered: "",
  });

  const { programId1 } = useParams();
  const { collegeId } = useParams();
  const navigator = useNavigate();

  useEffect(() => {
    if (programId1) {
      getProgramById(programId1)
        .then((response) => {
          setProgramId(response.data.programId);
          setProgramName(response.data.programName);
          setEligibility(response.data.eligibility);
          setDuration(response.data.duration);
          setDegreeOffered(response.data.degreeOffered);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  }, [programId1]);

  function handleProgramName(e) {
    setProgramName(e.target.value);
  }
  function handleEligibility(e) {
    setEligibility(e.target.value);
  }
  function handleDuration(e) {
    setDuration(e.target.value);
  }
  function handleDegreeOffered(e) {
    setDegreeOffered(e.target.value);
  }

  function saveOrUpdateProgram(e) {
    e.preventDefault();

    if (validateForm()) {
      const program = { programName, eligibility, duration, degreeOffered };

      console.log(program);

      if (programId1) {
        updateProgram(programId1, program)
          .then((response) => {
            console.log(response.data);
            //   console.log(categoryId)
            alert("Program Updated Successfully");
            navigator(`/program-info/${collegeId}`);
          })
          .catch((error) => {
            console.error(error);
          });
      } else {
        addProgram(collegeId, program)
          .then((response) => {
            console.log(response.data);
            alert("Program Added Successfully");
            navigator(`/program-info/${collegeId}`);
          })
          .catch((error) => {
            handleError(error)
          });
      }
    }
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


  // function validateForm() {
  //   let valid = true;
  //   const errorsCopy = { ...errors };

  //   if (programName.trim()) {
  //     errorsCopy.programName = "";
  //   } else {
  //     errorsCopy.programName = "Program Name is required";
  //     valid = false;
  //   }

  //   if (eligibility.trim()) {
  //     errorsCopy.eligibility = "";
  //   } else {
  //     errorsCopy.eligibility = "Eligibility is required";
  //     valid = false;
  //   }

  //   if (duration.trim()) {
  //     errorsCopy.duration = "";
  //   } else {
  //     errorsCopy.duration = "Duration is required";
  //     valid = false;
  //   }

  //   if (degreeOffered.trim()) {
  //     errorsCopy.degreeOffered = "";
  //   } else {
  //     errorsCopy.degreeOffered = "Degree Offered is Required";
  //     valid = false;
  //   }

  //   setErrors(errorsCopy);
  //   return valid;
  // }





  function validateForm(){
    let valid = true;
    const errorsCopy = {... errors};

    if (programName.trim()) {
        if (programName.length <= 30) {
            errorsCopy.programName = '';
        } else {
            errorsCopy.programName = 'Program name cannot exceed 30 characters';
            valid = false;
        }
    } else {
        errorsCopy.programName = 'Program name is required';
        valid = false;
    }

    if (eligibility.trim()) {
        if (eligibility.length <= 20) {
            errorsCopy.e = '';
        } else {
            errorsCopy.eligibility = 'Eligibility cannot exceed 20 characters';
            valid = false;
        }
    } else {
        errorsCopy.eligibility = 'Eligibility is required';
        valid = false;
    }

    if (duration.trim()) {
        if (duration.length <= 20) {
            errorsCopy.duration = '';
        } else {
            errorsCopy.duration = 'Duration cannot exceed 20 characters';
            valid = false;
        }
    } else {
        errorsCopy.duration = 'Course Duration is required';
        valid = false;
    }

    if(degreeOffered.trim()){
        errorsCopy.degreeOffered='';
    }else{
        errorsCopy.degreeOffered='Degree Offered is Required';
        valid=false;
    }
       
    setErrors(errorsCopy);
    return valid;

}





  function pageTitle() {
    if (programId1) {
      return <h2 className="text-center mt-4">Update Program</h2>;
    } else {
      return <h2 className="text-center mt-4">Add Program</h2>;
    }
  }
  function handleClose() {
    navigator(`/program-info/${collegeId}`);
  }
  return (
    <div>
      <div className="container cc">
        <div className="row">
          <div className="card col-md-6 offset-md-3 offset-md-3">
            {pageTitle()}
            <div className="card-body">
              <form>
                <div className="form-group">
                  {programId1 && (
                    <>
                      <label className="form-label"><strong>Program Id:</strong></label>
                      <input
                        type="text"
                        placeholder="Enter Program Id"
                        name="programId"
                        disabled
                        value={programId}
                        className={`form-control`}
                        onChange={() => {}}
                      ></input>
                    </>
                  )}
                  {/* {errors.courseId && <div className="invalid-feedback">{errors.courseId}</div>} */}
                </div>
                <div className="form-group mb-2">
                  <label className="form-label"><strong>Program Name:</strong></label>
                  <input
                    type="text"
                    placeholder="Enter Program Name"
                    name="name"
                    value={programName}
                    className={`form-control ${
                      errors.programName ? "is-invalid" : ""
                    }`}
                    onChange={handleProgramName}
                  ></input>
                  {errors.programName && (
                    <div className="invalid-feedback">{errors.programName}</div>
                  )}
                </div>
                <div className="form-group mb-2">
                  <label className="form-label"><strong>Eligibility:</strong></label>
                  <input
                    type="text"
                    placeholder="Enter Eligibility"
                    name="eligibility"
                    value={eligibility}
                    className={`form-control ${
                      errors.eligibility ? "is-invalid" : ""
                    }`}
                    onChange={handleEligibility}
                  ></input>
                  {errors.eligibility && (
                    <div className="invalid-feedback">{errors.eligibility}</div>
                  )}
                </div>
                <div className="form-group mb-2">
                  <label className="form-label"><strong>Duration:</strong></label>
                  <input
                    type="text"
                    placeholder="Enter Duration"
                    name="duration"
                    value={duration}
                    className={`form-control ${
                      errors.duration ? "is-invalid" : ""
                    }`}
                    onChange={handleDuration}
                  ></input>
                  {errors.duration && (
                    <div className="invalid-feedback">{errors.duration}</div>
                  )}
                </div>
                <div className="form-group mb-2">
                  <label className="form-label"><strong>Degree Offered:</strong></label>
                  <input
                    type="text"
                    placeholder="Enter Degree Offered"
                    name="degreeOffered"
                    value={degreeOffered}
                    className={`form-control ${
                      errors.degreeOffered ? "is-invalid" : ""
                    }`}
                    onChange={handleDegreeOffered}
                  ></input>
                  {errors.degreeOffered && (
                    <div className="invalid-feedback">
                      {errors.degreeOffered}
                    </div>
                  )}
                </div>
                  <div className="mb-3">
                <button
                  className="btn btn-info mt-4 mr-3"
                  onClick={saveOrUpdateProgram}
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
    </div>
  );
}

export default AdminProgramComponent;
