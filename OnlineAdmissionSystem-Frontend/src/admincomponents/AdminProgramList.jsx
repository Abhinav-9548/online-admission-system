import React, { useEffect } from "react";
import { useState } from "react";
// import "bootstrap/dist/css/bootstrap.min.css";
 
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { deleteProgram, getProgramList } from "../Services/ProgramService";

function AdminProgramList() {




    const [program, setProgram] = useState([]);
 
  const navigator = useNavigate();
 
 
  const { collegeId } = useParams();
 
  useEffect(() => {
    getAllPrograms();
  }, []);
 
  function getAllPrograms() {
    getProgramList(collegeId)
      .then((response) => {
        setProgram(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }
 
  function addNewProgram() {
    navigator(`/add-program/${collegeId}`);
  }
 
  function updateProgramDetails(programId) {
    navigator(`/update-program/${collegeId}/${programId}`);
  }
 
  function courseInfo(programId) {
    navigator(`/course-info/${collegeId}/program/${programId}`);
  }
 
  function deleteProgramDetails(programId) {
    deleteProgram(programId)
      .then((response) => {
        getAllPrograms();
      })
      .catch((error) => {
        console.log(error);
      });
  }


    function print(){
        if(program.length > 0){
            return (
              <div>
                <div className="container mt-5">
                  <h2 className="text-center mb-5">Program Details</h2>
           
                  <table className="table table-striped table-bordered mt-5">
                    <thead>
                      <tr>
                        <th className="text-center">Program Id</th>
                        <th className="text-center">Program Name</th>
                        <th className="text-center">Program Eligibility</th>
                        <th className="text-center">Program Duration</th>
                        <th className="text-center">Degree Offered</th>
                        <th className="text-center">Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      {program.map((program) => (
                        <tr key={program.programId}>
                          <td>{program.programId}</td>
                          <td>{program.programName}</td>
                          <td>{program.eligibility}</td>
                          <td>{program.duration}</td>
                          <td>{program.degreeOffered}</td>
                          {/* <td><button className="btn btn-primary mb-2" onClick={courseInfo(topic.id)}>Course Info</button></td> */}
                          {/* <td>{e.topic.id}</td> */}
                          {/* <td>{course.topic.id}</td> */}
                         
                          <td>
                            <div className="button">
           
                            <button
                                className="btn btn-primary mb-2"
                                type="button"
                                onClick={() => courseInfo(program.programId)}
                              >
                                Course-Info
                              </button>
           
                             
                              <button
                                type="button"
                                className="btn btn-info mb-2"
                                onClick={() => updateProgramDetails(program.programId)}
                              >
                                Update
                              </button>
                              <button
                                type="button"
                                className="btn btn-danger mb-2"
                                onClick={() => deleteProgramDetails(program.programId)}
                              >
                                Delete
                              </button>
                             
                            </div>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                  <button className="btn btn-success md-2" onClick={addNewProgram}>
                    Add Program
                  </button>
                </div>
                </div>
              );
        } else {
            return (
              <div className="noData">
                <h1 className="text-center">No Program Found</h1>
                <div className="">
                  <center>
                    <button
                      className="btn btn-success  me-md-2"
                      onClick={addNewProgram}
                    >
                      Add Program
                    </button>
                  </center>
                </div>
              </div>
            );
          }
          }


  return (
    <div className="programdiv">{print()}</div>
  )
}

export default AdminProgramList