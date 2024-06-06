import React, { useEffect } from "react";
import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Card from "react-bootstrap/Card";
import Container from "react-bootstrap/Container";
import {
  findCoursesByProgramId,
  findProgramById,
} from "../Services/CourseService";

const CourseComponent = () => {
  const [course, setCourse] = useState([]);
  const [program, setProgram] = useState("");

  const navigator = useNavigate();

  const { programId } = useParams();

  function branchInfo(courseId) {
    navigator(`/branch/${courseId}`);
  }
// ------------------------------------------------------
//   function programSchedule(courseId){
//     navigator(`programScheduledByCourse/${courseId}`)
// }
// ----------------------------------------------------------
  useEffect(() => {
    getAllCourse();
  }, []);

  useEffect(() => {
    findProgramById(programId)
      .then((response) => {
        setProgram(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
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

  function handleCourse() {
    if (course.length > 0) {
      return (
        <div>
                <Container className='containercard'>
                    <h2 className="text-center mb-4">Courses in <i>{program.programName}</i> program</h2>
                    <div className="row">
                      {course.map((cour) => (
                        <div key={cour.courseId} className="col-md-6 mb-4">
                          <Card className='coursecard'>
                            <Card.Body>
                              <Card.Title style={{marginBottom:"20px"}} className='coursetitle'><h3><b><u>{cour.courseName}</u></b></h3></Card.Title>
                              <Card.Text className='coursetext'><strong>Eligibiity:</strong> {cour.eligibility}</Card.Text>
                              <Card.Text><strong>Duration:</strong> {cour.duration}</Card.Text>
                              <button className='btn btn-info buttonclass' onClick = {()=>branchInfo(cour.courseId)}>View Branches</button>
                              <button className='btn btn-info buttonclass ml-4' onClick = {()=>navigator(`/programScheduledByCourse/${cour.courseId}`)}>Apply Now</button>
                              {/* <button type="button" className="btn btn-info mb-2" onClick={() => programSchedule(cour.courseId)}>Apply Now </button> */}
                            </Card.Body>
                          </Card>
                        </div>
                      ))}
                    </div>
                  </Container>
        </div>
      );
    } else {
      return (
        <div className="no">
          <h2 className="noData">No Course Found in {program.programName}</h2>
        </div>
      );
    }
  }

  return <div className="coursediv">{handleCourse()}</div>;
};

export default CourseComponent;
