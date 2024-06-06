import React, { useEffect, useState } from "react";
import {
  addUniversity,
  getUniversityById,
  updateUniversity,
} from "../Services/UniversityService";
import { useNavigate, useParams } from "react-router-dom";

const AdminUniversityComponent = () => {
  const navigator = useNavigate("");
  const [universityId, setUniversityId] = useState("");
  const [name, setUniversityName] = useState("");
  const [addressId, setAddressId] = useState("");
  const [city, setCity] = useState("");
  const [district, setDistrict] = useState("");
  const [state, setState] = useState("");
  const [country, setCountry] = useState("");
  const [zipcode, setZipcode] = useState("");
  const [landmark, setLandmark] = useState("");

  const [errors, setErrors] = useState({
    //   universityId:'',
    name: "",
    city: "",
    district: "",
    state: "",
    country: "",
    zipcode: "",
    landmark: "",
  });

  const { uniId } = useParams();

  useEffect(() => {
    if (uniId) {
      getUniversityById(uniId)
        .then((response) => {
          setUniversityId(response.data.universityId);
          setUniversityName(response.data.name);
          setAddressId(response.data.address.addressId);
          setCity(response.data.address.city);
          setDistrict(response.data.address.district);
          setState(response.data.address.state);
          setCountry(response.data.address.country);
          setZipcode(response.data.address.zipcode);
          setLandmark(response.data.address.landmark);
        })
        .catch((error) => console.error(error));
    }
  }, [uniId]);

  function createOrUpdateUniversity(e) {
    e.preventDefault();

    const address = { city, district, state, country, zipcode, landmark };
    const university = { name, address };
    console.log(university);

    if (uniId) {
      if (validateForm()) {
        updateUniversity(uniId, university).then((response) => {
          console.log(response.data);

          alert("University Updated Successfully");
          navigator("/university-list");
        });
      }
    } else {
      if (validateForm()) {
        addUniversity(university).then((response) => {
          console.log(response.data);

          alert("University Added Successfully");
          navigator("/university-list");
        }).catch((error)=>{
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

    //setErrors({...errors, [e.target.departmentId]:e.target value});

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

    if (name.length == 0) {
      errorsCopy.name = "University Name is required";
      valid = false;
    } else if (name.length > 20) {
      errorsCopy.name = "University name cannot exceed 20 characters";
      valid = false;
    } else {
      errorsCopy.name = "";
    }

    setErrors(errorsCopy);

    return valid;
  }

  function pageTitle() {
    if (uniId) {
      return <h2 className="text-center mt-4">Update University</h2>;
    } else {
      return <h2 className="text-center mt-4">Add University</h2>;
    }
  }
  function handleClose() {
    navigator("/university-list");
  }
  return (
    <div className="container cc">
      <div className="row">
        <div className="card col-md-6 offset-md-3 offset-md-3">
          {pageTitle()}
          <div className="card-body">
            <form>
              {universityId && (
                <div className="form-group">
                  <label className="form-label">
                    <strong>University Id:</strong>
                  </label>
                  <input
                    type="text"
                    name="Id"
                    disabled
                    value={universityId}
                    className={`form-control`}
                    onChange={(e) => setUniversityName(e.target.value)}
                  ></input>
                </div>
              )}

              <div className="form-group">
                <label className="form-label">
                  <strong>Name:</strong>
                </label>
                <input
                  type="text"
                  placeholder="Enter University Name"
                  name="universityName"
                  value={name}
                  className={`form-control ${errors.name ? "is-invalid" : ""}`}
                  onChange={(e) => setUniversityName(e.target.value)}
                ></input>
                {errors.name && (
                  <div className="invalid-feeback">{errors.name}</div>
                )}
              </div>

              <div>
                <hr />
                <h5 className="text-left">Address:-</h5>

                <div className="form-group">
                  <label className="form-label">
                    <strong>City:</strong>
                  </label>
                  <input
                    type="text"
                    placeholder="Enter City"
                    name="city"
                    value={city}
                    className={`form-control ${
                      errors.city ? "is-invalid" : ""
                    }`}
                    onChange={(e) => setCity(e.target.value)}
                  ></input>
                  {errors.city && (
                    <div className="invalid-feeback">{errors.city}</div>
                  )}
                </div>

                <div className="form-group">
                  <label className="form-label">
                    <strong>District:</strong>
                  </label>
                  <input
                    type="text"
                    placeholder="Enter District"
                    name="district"
                    value={district}
                    className={`form-control ${
                      errors.district ? "is-invalid" : ""
                    }`}
                    onChange={(e) => setDistrict(e.target.value)}
                  ></input>
                  {errors.district && (
                    <div className="invalid-feeback">{errors.district}</div>
                  )}
                </div>

                <div className="form-group">
                  <label className="form-label">
                    <strong>State:</strong>
                  </label>
                  <input
                    type="text"
                    placeholder="Enter State"
                    name="state"
                    value={state}
                    className={`form-control ${
                      errors.state ? "is-invalid" : ""
                    }`}
                    onChange={(e) => setState(e.target.value)}
                  ></input>
                  {errors.state && (
                    <div className="invalid-feeback">{errors.state}</div>
                  )}
                </div>

                <div className="form-group">
                  <label className="form-label">
                    <strong>Country:</strong>
                  </label>
                  <input
                    type="text"
                    placeholder="Enter Country"
                    name="country"
                    value={country}
                    className={`form-control ${
                      errors.country ? "is-invalid" : ""
                    }`}
                    onChange={(e) => setCountry(e.target.value)}
                  ></input>
                  {errors.country && (
                    <div className="invalid-feeback">{errors.country}</div>
                  )}
                </div>

                <div className="form-group">
                  <label className="form-label">
                    <strong>Zip-Code:</strong>
                  </label>
                  <input
                    type="number"
                    placeholder="Enter Zipcode"
                    name="zipcode"
                    value={zipcode}
                    className={`form-control ${
                      errors.zipcode ? "is-invalid" : ""
                    }`}
                    onChange={(e) => setZipcode(e.target.value)}
                  ></input>
                  {errors.zipcode && (
                    <div className="invalid-feeback">{errors.zipcode}</div>
                  )}
                </div>

                <div className="form-group">
                  <label className="form-label">
                    <strong>Landmark:</strong>
                  </label>
                  <input
                    type="text"
                    placeholder="Enter Landmark"
                    name="landmark"
                    value={landmark}
                    className={`form-control ${
                      errors.landmark ? "is-invalid" : ""
                    }`}
                    onChange={(e) => setLandmark(e.target.value)}
                  ></input>
                </div>
              </div>
              <br />
              <div className="mb-3">
                <button
                  className="btn btn-info mt-4 mr-3"
                  onClick={createOrUpdateUniversity}
                  type="button"
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
  );
};

export default AdminUniversityComponent;
