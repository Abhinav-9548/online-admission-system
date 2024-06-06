import React, { useState, useEffect } from 'react';
import { Form, Button, Dropdown, DropdownButton, Alert } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { getAllUniversity } from '../Services/UniversityService';
import { getBranchByCourses, getCollegesByUniversity, getCoursesByProgram, getProgramsByColleges, postProgramSchedule } from '../Services/ProgramScheduleService';


function AdminProgramScheduleComponent() {


    const [universities, setUniversities] = useState([]);
    const [colleges, setColleges] = useState([]);
    const [programs, setPrograms] = useState([]);
    const [courses, setCourses] = useState([]);
    const [branches, setBranches] = useState([]);
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [error, setError] = useState("");
   
    const navigator = useNavigate();
    const [formData, setFormData] = useState({
      universityId: '',
      collegeId: '',
      programId: '',
      courseId: '',
      branchId: ''
    });
    const [selectedTitles, setSelectedTitles] = useState({
      name: 'Select University',
      collegeName: 'Select College',
      programName: 'Select Program',
      courseName: 'Select Course',
      branchName: 'Select Branch'
    });
   
    useEffect(() => {
      getAllUniversity().then((response) => {
        setUniversities(response.data)
        console.log(response.data);
      }).catch((error) => {
        console.log(error)
      })
    }, []);
   
    const handleUniversityChange = (eventKey, event) => {
      const selectedName = event.target.text;
      console.log(eventKey);
   
      setFormData({ ...formData, universityId: eventKey, collegeId: '', programId: '', courseId: '', branchId: '' });
      setSelectedTitles({ ...selectedTitles, name: selectedName, collegeName: 'Select College', programName: 'Select Program', courseName: 'Select Course', branchName: 'Select Branch' });
   
      getCollegesByUniversity(eventKey).then((response) => {
        setColleges(response.data);
      }).catch(error => {
        console.log(error)
      });
    };
   
    const handleCollegeChange = (eventKey, event) => {
      const selectedName = event.target.text;
      console.log(eventKey);
   
      setFormData({ ...formData, collegeId: eventKey, programId: '', courseId: '', branchId: '' });
      setSelectedTitles({ ...selectedTitles, collegeName: selectedName, programName: 'Select Program', courseName: 'Select Course', branchName: 'Select Branch' });
   
      getProgramsByColleges(eventKey).then((response) => {
        setPrograms(response.data);
      }).catch(error => {
        console.log(error)
      });
    };
   
    const handleProgramChange = (eventKey, event) => {
      const selectedName = event.target.text;
      console.log(eventKey);
   
      setFormData({ ...formData, programId: eventKey, courseId: '', branchId: '' });
      setSelectedTitles({ ...selectedTitles, programName: selectedName, courseName: 'Select Course', branchName: 'Select Branch' });
   
      getCoursesByProgram(eventKey).then((response) => {
        setCourses(response.data);
      }).catch(error => {
        console.log(error)
      });
    };
   
    const handleCourseChange = (eventKey, event) => {
      const selectedName = event.target.text;
      console.log(eventKey);
   
      setFormData({ ...formData, courseId: eventKey, branchId: '' });
      setSelectedTitles({ ...selectedTitles, courseName: selectedName, branchName: 'Select Branch' });
   
      getBranchByCourses(eventKey).then((response) => {
        setBranches(response.data);
      }).catch(error => {
        console.log(error)
      });
    };
   
    const handleBranchChange = (eventKey, event) => {
      const selectedName = event.target.text;
      console.log(eventKey);
   
      setFormData({ ...formData, branchId: eventKey });
      setSelectedTitles({ ...selectedTitles, branchName: selectedName });
    };
   
    const validateDates = () => {
      const currentDate = new Date().toISOString().split('T')[0];
      if (new Date(startDate) <= new Date(currentDate)) {
        setError("Start date must be after the present date.");
        return false;
      }
      if (new Date(endDate) <= new Date(startDate)) {
        setError("End date must be after start date.");
        return false;
      }
      setError("");
      return true;
    };
   
    const handleSubmit = (e) => {
      e.preventDefault();
      if (!validateDates()) {
        return;
      }
      const ProgramScheduled = { startDate, endDate };
   
      console.log(formData.universityId);
      postProgramSchedule(formData.universityId, formData.collegeId, formData.programId, formData.courseId, formData.branchId, ProgramScheduled).then((response) => {
        console.log(response.data);
        navigator("/programschedule")
      }).catch((error) => { console.log(error) });
    };



  return (
    <div className='adminprogramschedulecomponentdiv'>
        
        <div className='container'>
      <div className='row'>
        <div className='card col-md-6 offset-md-3 offset-md-3'>
          <h3 className='mt-5' style={{textAlign:'center'}}> <strong>Add Program Scheduled</strong></h3>
          <div className='card-body'>
            <Form onSubmit={handleSubmit}>
              <Form.Group controlId="universitySelect">
                <Form.Label>University:</Form.Label>
                <DropdownButton title={selectedTitles.name} onSelect={handleUniversityChange}>
                  {universities.map(university => (
                    <Dropdown.Item key={university.universityId} eventKey={university.universityId}>
                      {university.name}
                    </Dropdown.Item>
                  ))}
                </DropdownButton>
                {/* <div>Selected University: {selectedTitles.name}</div> */}
              </Form.Group>
 
              <Form.Group controlId="collegeSelect">
                <Form.Label>College:</Form.Label>
                <DropdownButton title={selectedTitles.collegeName} onSelect={handleCollegeChange} disabled={!formData.universityId}>
                  {colleges.map(college => (
                    <Dropdown.Item key={college.collegeRegId} eventKey={college.collegeRegId}>
                      {college.collegeName}
                    </Dropdown.Item>
                  ))}
                </DropdownButton>
                {/* <div>Selected College: {selectedTitles.collegeName}</div> */}
              </Form.Group>
 
              <Form.Group controlId="programSelect">
                <Form.Label>Program:</Form.Label>
                <DropdownButton title={selectedTitles.programName} onSelect={handleProgramChange} disabled={!formData.collegeId}>
                  {programs.map(program => (
                    <Dropdown.Item key={program.programId} eventKey={program.programId}>
                      {program.programName}
                    </Dropdown.Item>
                  ))}
                </DropdownButton>
                {/* <div>Selected Program: {selectedTitles.programName}</div> */}
              </Form.Group>
 
              <Form.Group controlId="courseSelect">
                <Form.Label>Course:</Form.Label>
                <DropdownButton title={selectedTitles.courseName} onSelect={handleCourseChange} disabled={!formData.programId}>
                  {courses.map(course => (
                    <Dropdown.Item key={course.courseId} eventKey={course.courseId}>
                      {course.courseName}
                    </Dropdown.Item>
                  ))}
                </DropdownButton>
                {/* <div>Selected Course: {selectedTitles.courseName}</div> */}
              </Form.Group>
 
              <Form.Group controlId="branchSelect">
                <Form.Label>Branch:</Form.Label>
                <DropdownButton title={selectedTitles.branchName} onSelect={handleBranchChange} disabled={!formData.courseId}>
                  {branches.map(branch => (
                    <Dropdown.Item key={branch.branchId} eventKey={branch.branchId}>
                      {branch.branchName}
                    </Dropdown.Item>
                  ))}
                </DropdownButton>
                {/* <div>Selected Branch: {selectedTitles.branchName}</div> */}
              </Form.Group>
 
              <Form.Group controlId="startDate">
                <Form.Label>Start Date:</Form.Label>
                <Form.Control type="date" name="startDate" value={startDate} onChange={(e) => { setStartDate(e.target.value) }} />
              </Form.Group>
 
              <Form.Group controlId="endDate">
                <Form.Label>End Date:</Form.Label>
                <Form.Control type="date" name="endDate" value={endDate} onChange={(e) => { setEndDate(e.target.value) }} />
              </Form.Group>
              {error && <Alert variant="danger" className="mt-2">{error}</Alert>}
              <br></br>
              <Button  className="mt-5 mb-4" variant="primary" type="submit">Add Scheduled</Button>
            </Form>
          </div>
        </div>
      </div>
    </div>


    </div>
  )
}

export default AdminProgramScheduleComponent