import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import { useEffect } from "react";
import {
  addCollege,
  getCollegeDetailsById,
  updateCollege,
} from "../Services/CollegeService";

function AdminCollegeComponent() {
  const [collegeRegId, setCollegeRegId] = useState("");
  const [collegeName, setCollegeName] = useState("");
  const [description, setDescription] = useState("");

  // const[address,setAddress]=useState('')

  const [city, setCity] = useState("");
  const [district, setDistrict] = useState("");
  const [state, setState] = useState("");
  const [country, setCountry] = useState("");
  const [zipcode, setZipcode] = useState("");
  const [landmark, setLandmark] = useState("");

  const [errors, setErrors] = useState({
    collegeRegId: "",
    collegeName: "",
    description: "",
    city: "",
    district: "",
    state: "",
    country: "",
    zipcode: "",
    landmark: "",
  });

  const { universityId } = useParams();
  const { collegeId } = useParams();
  const navigator = useNavigate();

  useEffect(() => {
    if (collegeId) {
      getCollegeDetailsById(collegeId)
        .then((response) => {
          setCollegeRegId(response.data.collegeRegId);
          setCollegeName(response.data.collegeName);
          setDescription(response.data.description);
          // setAddress(response.data.address)
          setCity(response.data.address.city);
          setDistrict(response.data.address.district);
          setState(response.data.address.state);
          setCountry(response.data.address.country);
          setZipcode(response.data.address.zipcode);
          setLandmark(response.data.address.landmark);

          // console.log(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  }, [collegeId]);

  function handleCollegeName(e) {
    setCollegeName(e.target.value);
  }
  function handleDescription(e) {
    setDescription(e.target.value);
  }
  function handleCity(e) {
    setCity(e.target.value);
  }
  function handleDistrict(e) {
    setDistrict(e.target.value);
  }
  function handleState(e) {
    setState(e.target.value);
  }
  function handleCountry(e) {
    setCountry(e.target.value);
  }
  function handleZipcode(e) {
    setZipcode(e.target.value);
  }
  function handleLandmark(e) {
    setLandmark(e.target.value);
  }

  function saveOrUpdateCollege(e) {
    e.preventDefault();

    if (validateForm()) {
      const address = { city, district, state, country, zipcode, landmark };
      const college = { collegeName, description, address };
      console.log(college);

      if (collegeId) {
        updateCollege(collegeId, college)
          .then((response) => {
            console.log(response.data);
            //   console.log(categoryId)
            alert("College Updated Successfully");
            navigator(`/college-info/${universityId}`);
          })
          .catch((error) => {
            console.error(error);
          });
      } else {
        addCollege(universityId, college)
          .then((response) => {
            console.log(response.data);
            alert("College Added Successfully");
            navigator(`/college-info/${universityId}`);
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

  function validateForm() {
    let valid = true;
    const errorsCopy = { ...errors };

    // if (description.trim()) {
    //   errorsCopy.description = "";
    // } else {
    //   errorsCopy.description = "Description is required";
    //   valid = false;
    // }

    if (description.trim()) {
      if (description.length <= 100) {
          errorsCopy.description = '';
      } else {
          errorsCopy.description = 'Description cannot exceed 100 characters';
          valid = false;
      }
  } else {
      errorsCopy.description = 'Description is required';
      valid = false;
  }

    // if (collegeName.trim()) {
    //   errorsCopy.collegeName = "";
    // } else {
    //   errorsCopy.collegeName = "College Name is required";
    //   valid = false;
    // }
    if (collegeName.trim()) {
      if (collegeName.length <= 40) {
          errorsCopy.collegeName = '';
      } else {
          errorsCopy.collegeName = 'College name cannot exceed 40 characters';
          valid = false;
      }
  } else {
      errorsCopy.collegeName = 'College name is required';
      valid = false;
  }


    if (city.length == 0) {
      errorsCopy.city = "City is required";
      valid = false;
    } else if (city.length > 20) {
      errorsCopy.city = "City should not exceed 20 characters";
      valid = false;
    } else {
      errorsCopy.city = "";
    }

    if (district.length == 0) {
      errorsCopy.district = "District is required";
      valid = false;
    } else if (district.length > 20) {
      errorsCopy.district = "District should not exceed 20 characters";
      valid = false;
    } else {
      errorsCopy.district = "";
    }

    if (state.length == 0) {
      errorsCopy.state = " State is required";
      valid = false;
    } else if (state.length > 20) {
      errorsCopy.state = "State should not exceed 20 characters";
      valid = false;
    } else {
      errorsCopy.state = "";
    }

    if (country.length == 0) {
      errorsCopy.country = "Country is required";
      valid = false;
    } else if (country.length > 20) {
      errorsCopy.country = "Country should not exceed 20 characters";
      valid = false;
    } else {
      errorsCopy.country = "";
    }

    if (zipcode.length == 0) {
      errorsCopy.zipcode = " Zip-Code is required";
      valid = false;
    } else if (zipcode.length != 6 || zipcode.indexOf(0) == 0) {
      errorsCopy.zipcode =
        "Zipcode must be up to 6 digits and cannot start with zero";
      valid = false;
    } else {
      errorsCopy.zipcode = "";
    }


    setErrors(errorsCopy);
    return valid;
  }

  function pageTitle() {
    if (collegeId) {
      return <h2 className="text-center mt-4">Update College</h2>;
    } else {
      return <h2 className="text-center mt-4">Add College</h2>;
    }
  }
  function handleClose() {
    navigator(`/college-info/${universityId}`);
  }

  return (
    <div className="collegecomponentdiv">
      <div className="container">
        <br />
        <br />
        <div className="row">
          <div className="card col-md-6 offset-md-3 offset-md-3">
            {pageTitle()}
            <div className="card-body">
              <form>
                <div className="form-group mb-2">
                  {collegeId && (
                    <>
                      <label className="form-label">
                        <strong>College Id:</strong>
                      </label>
                      <input
                        type="text"
                        placeholder="Enter College Id"
                        name="collegeId"
                        value={collegeRegId}
                        className={`form-control`}
                        onChange={() => {}}
                        disabled
                      ></input>
                    </>
                  )}
                  {/* {errors.courseId && <div className="invalid-feedback">{errors.courseId}</div>} */}
                </div>

                <div className="form-group mb-2">
                  <label className="form-label">
                    <strong>College Name:</strong>
                  </label>
                  <input
                    type="text"
                    placeholder="Enter College Name"
                    name="name"
                    value={collegeName}
                    className={`form-control ${
                      errors.collegeName ? "is-invalid" : ""
                    }`}
                    onChange={handleCollegeName}
                  ></input>
                  {errors.collegeName && (
                    <div className="invalid-feedback">{errors.collegeName}</div>
                  )}
                </div>

                <div className="form-group mb-2">
                  <label className="form-label">
                    <strong>College Description:</strong>
                  </label>
                  <input
                    type="text"
                    placeholder="Enter College Name"
                    name="name"
                    value={description}
                    className={`form-control ${
                      errors.description ? "is-invalid" : ""
                    }`}
                    onChange={handleDescription}
                  ></input>
                  {errors.description && (
                    <div className="invalid-feedback">{errors.description}</div>
                  )}
                </div>

                <div>
                  <hr />
                  <h5 className="text-left">Address:-</h5>
                  <div className="form-group mb-2">
                    <label className="form-label"><strong>City:</strong></label>
                    <input
                      type="text"
                      placeholder="Enter City"
                      name="city"
                      value={city}
                      className={`form-control ${
                        errors.city ? "is-invalid" : ""
                      }`}
                      onChange={handleCity}
                    ></input>
                    {errors.city && (
                      <div className="invalid-feedback">{errors.city}</div>
                    )}
                  </div>
                  <div className="form-group mb-2">
                    <label className="form-label"><strong>District:</strong></label>
                    <input
                      type="text"
                      placeholder="Enter District"
                      name="district"
                      value={district}
                      className={`form-control ${
                        errors.district ? "is-invalid" : ""
                      }`}
                      onChange={handleDistrict}
                    ></input>
                    {errors.district && (
                      <div className="invalid-feedback">{errors.district}</div>
                    )}
                  </div>
                  <div className="form-group mb-2">
                    <label className="form-label"><strong>State:</strong></label>
                    <input
                      type="text"
                      placeholder="Enter State"
                      name="state"
                      value={state}
                      className={`form-control ${
                        errors.state ? "is-invalid" : ""
                      }`}
                      onChange={handleState}
                    ></input>
                    {errors.state && (
                      <div className="invalid-feedback">{errors.state}</div>
                    )}
                  </div>
                  <div className="form-group mb-2">
                    <label className="form-label"><strong>Country:</strong></label>
                    <input
                      type="text"
                      placeholder="Enter Country"
                      name="country"
                      value={country}
                      className={`form-control ${
                        errors.country ? "is-invalid" : ""
                      }`}
                      onChange={handleCountry}
                    ></input>
                    {errors.country && (
                      <div className="invalid-feedback">{errors.country}</div>
                    )}
                  </div>
                  <div className="form-group mb-2">
                    <label className="form-label"><strong>Zipcode:</strong></label>
                    <input
                      type="text"
                      placeholder="Enter Zipcode"
                      name="zipcode"
                      value={zipcode}
                      className={`form-control ${
                        errors.zipcode ? "is-invalid" : ""
                      }`}
                      onChange={handleZipcode}
                    ></input>
                    {errors.zipcode && (
                      <div className="invalid-feedback">{errors.zipcode}</div>
                    )}
                  </div>
                  <div className="form-group mb-2">
                    <label className="form-label"><strong>Landmark:</strong></label>
                    <input
                      type="text"
                      placeholder="Enter Landmark"
                      name="landmark"
                      value={landmark}
                      className={`form-control`}
                      onChange={handleLandmark}
                    ></input>
                    {/* {errors.landmark && <div className="invalid-feedback">{errors.landmark}</div>} */}
                  </div>
                </div>
              <div className="mb-3">
                <button
                  className="btn btn-info mt-4 mr-3"
                  onClick={saveOrUpdateCollege}
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

export default AdminCollegeComponent;
