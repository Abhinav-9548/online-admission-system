import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  addCourse,
  getCourseById,
  updateCourse,
} from "../Services/CourseService";

function AdminCourseComponent() {
  const navigator = useNavigate();
  const [courseId, setCourseId] = useState("");
  const [courseName, setCourseName] = useState("");
  const [duration, setDuration] = useState("");
  const [eligibility, setEligibility] = useState("");

  const { courseId1 } = useParams();
  const { programId } = useParams();
  const { collegeId } = useParams();

  const [errors, setErrors] = useState({
    courseName: "",
    eligibility: "",
    duration: "",
  });

  useEffect(() => {
    if (courseId1) {
      getCourseById(courseId1)
        .then((response) => {
          setCourseId(response.data.courseId);
          setCourseName(response.data.courseName);
          setEligibility(response.data.eligibility);
          setDuration(response.data.duration);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  }, [courseId1]);






  function validateForm() {
    let valid = true;
    const errorsCopy = { ...errors };

    // Validate courseName
    if (courseName.trim()) {
        if (courseName.length <= 20) {
            errorsCopy.courseName = '';
        } else {
            errorsCopy.courseName = 'Course name cannot exceed 20 characters';
            valid = false;
        }
    } else {
        errorsCopy.courseName = 'Course name is required';
        valid = false;
    }

    // Validate eligibility
    if (eligibility.trim()) {
        if (eligibility.length <= 60) {
            errorsCopy.eligibility = '';
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
        errorsCopy.duration = 'Duration is required';
        valid = false;
    }

    setErrors(errorsCopy);
    return valid;
}





  function pageTitle() {
    if (courseId1) {
      return <h2 className="text-center mt-4">Update Course</h2>;
    } else {
      return <h2 className="text-center mt-4">Add Course</h2>;
    }
  }

  const saveOrUpdateCourse = (e) => {
    e.preventDefault();
    if (validateForm()) {
      const course = { courseName, duration, eligibility };
      console.log(course);
      if (courseId1) {
        updateCourse(courseId1, course)
          .then((response) => {
            console.log(response.data);
            alert("Course Updated Successfully");
            navigator(`/course-info/${collegeId}/program/${programId}`);
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        addCourse(collegeId, programId, course)
          .then((response) => {
            console.log(response.data);
            alert("Course Added Successfully");
            navigator(`/course-info/${collegeId}/program/${programId}`);
          })
          .catch((error) => {
            handleError(error);
          });
      }
    }
  };

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


  function handleClose() {
    navigator(`/course-info/${collegeId}/program/${programId}`);
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
                {courseId && (
                    <>
                      <label className="form-label"><strong>Course Id:</strong></label>
                      <input
                        type="text"
                        placeholder="Enter Course Id"
                        name="programId"
                        value={courseId}
                        disabled
                        className={`form-control`}
                        onChange={() => {}}
                      ></input>
                    </>
                  )}
                  </div>

                  <div className="form-group">
                  <label className="form-label"><strong>Course Name:</strong></label>
                  <input
                    type="text"
                    placeholder="Enter the Course Name"
                    name="courseName"
                    value={courseName}
                    className={`form-control ${
                      errors.courseName ? "is-invalid" : ""
                    }`}
                    onChange={(e) => setCourseName(e.target.value)}
                  ></input>
                  {errors.courseName && (
                    <div className="invalid-feedback">{errors.courseName}</div>
                  )}
                </div>

                <div className="form-group mb-2">
                  <label className="form-label"><strong>Course Duration:</strong></label>
                  <input
                    type="text"
                    placeholder="Enter the Course Duration"
                    name="courseName"
                    value={duration}
                    className={`form-control ${
                      errors.duration ? "is-invalid" : ""
                    }`}
                    onChange={(e) => setDuration(e.target.value)}
                  ></input>
                  {errors.duration && (
                    <div className="invalid-feedback">{errors.duration}</div>
                  )}
                </div>

                <div className="form-group mb-2">
                  <label className="form-label"><strong>Eligibility:</strong></label>
                  <input
                    type="text"
                    placeholder="Enter the Eligibility"
                    name="eligibility"
                    value={eligibility}
                    className={`form-control ${
                      errors.eligibility ? "is-invalid" : ""
                    }`}
                    onChange={(e) => setEligibility(e.target.value)}
                  ></input>
                  {errors.eligibility && (
                    <div className="invalid-feedback">{errors.eligibility}</div>
                  )}
                </div>

            <div className="mb-3">
                <button
                  className="btn btn-info mt-4 mr-3"
                  onClick={saveOrUpdateCourse}
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

export default AdminCourseComponent;
