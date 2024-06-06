import React, { useEffect, useState } from "react";
 
import { useNavigate, useParams } from "react-router-dom";
import { deleteCourse, findCoursesByProgramId} from "../Services/CourseService";

function AdminCourseList() {


    const [course, setCourse] = useState([]);
 
    const navigator = useNavigate();
    const { programId } = useParams();
    const { collegeId } = useParams();
   
    useEffect(() => {
      getAllCourse();
    }, []);
   
    function getAllCourse() {
      findCoursesByProgramId(programId)
        .then((response) => {
          setCourse(response.data);
        })
        .catch((error) => {
          console.error(error);
        });
    }
   
    function addNewCourse() {
      navigator(`/add-course/${collegeId}/${programId}`);
    }
    function updateCourse(courseId) {
      navigator(`/update-course/${collegeId}/${programId}/${courseId}`);
    }
   
    function branchInfo(collegeId, courseId) {
      navigator(`/branch-info/${collegeId}/course/${courseId}`);
    }
   
    function deleteCourseDetails(courseId) {
      console.log(courseId);
      deleteCourse(courseId)
        .then((response) => {
          getAllCourse();
        })
        .catch((error) => {
          console.log(error);
        });
    }


    function print(){
        if(course.length > 0){
            return (
              <div>
                <div className="container mt-5">
                  <h2 className="text-center mb-5">Course Details</h2>
           
                  <table className="table table-bordered table-striped mt-5">
                    <thead className="text-center">
                      <tr>
                        <th className="text-center">Course Id</th>
                        <th className="text-center">Course Name</th>
                        <th className="text-center">Eligibility</th>
                        <th className="text-center">Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      {course.map((cour) => (
                        <tr key={cour.courseId}>
                          <td>{cour.courseId}</td>
                          <td>{cour.courseName}</td>
                          <td>{cour.eligibility}</td>
                          <td>
                            <div className="button">
           
                            <button
                              className="btn btn-primary mb-2"
                              type="button"
                              onClick={() => branchInfo(collegeId,cour.courseId)}
                            >
                              Branch-Info
                            </button>
           
                             
                              <button
                                className="btn btn-info mb-2"
                                onClick={() => updateCourse(cour.courseId)}
                              >
                                Update
                              </button>
           
                              <button
                                className="btn btn-danger mb-2"
                                onClick={() => deleteCourseDetails(cour.courseId)}
                              >
                                delete
                              </button>
                            </div>
                           
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                  <button className="btn btn-success mb-2" onClick={addNewCourse}>
                    Add Courses
                  </button>
                </div>
                </div>
              );
           
        } else {
            return (
              <div className="noData">
                <h1 className="text-center">No Course Found</h1>
                <div className="">
                  <center>
                    <button
                      className="btn btn-success  me-md-2"
                      onClick={addNewCourse}
                    >
                      Add Course
                    </button>
                  </center>
                </div>
              </div>
            );
          }
          }


  return (
    <div className="coursediv">{print()}</div>
  )
}

export default AdminCourseList