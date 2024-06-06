import React, { useEffect, useState } from 'react';
import { Button, Card, Container, Form } from 'react-bootstrap';
import "bootstrap/dist/css/bootstrap.min.css";
import { getAllProgramScheduled } from '../Services/ProgramScheduleService';
import { useNavigate } from 'react-router-dom';
 
const ProgramScheduledApply = () => {
  const [programScheduled, setProgramScheduled] = useState([]);
  const [filteredPrograms, setFilteredPrograms] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const navigate=useNavigate()
 
  useEffect(() => {
    getAllProgramSchedule();
  }, []);
 
  useEffect(() => {
    filterPrograms();
  }, [searchTerm, programScheduled]);
 
  const getAllProgramSchedule = () => {
    getAllProgramScheduled()
      .then((response) => {
        setProgramScheduled(response.data);
        setFilteredPrograms(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };
 
  const handleInputChange = (e) => {
    setSearchTerm(e.target.value);
  };
 
  const filterPrograms = () => {
    const filtered = programScheduled.filter((prog) => {
      const { name, collegeName, programName, courseName, branchName } = prog.programScheduledResponseDto;
      return (
        name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        collegeName.toLowerCase().includes(searchTerm.toLowerCase()) ||
        programName.toLowerCase().includes(searchTerm.toLowerCase()) ||
        courseName.toLowerCase().includes(searchTerm.toLowerCase()) ||
        branchName.toLowerCase().includes(searchTerm.toLowerCase())
      );
    });
    setFilteredPrograms(filtered);
  };
 
  return (
    <div className='programscheduleapplydiv'>
        
    <Container className='containercard'>
      <h2 className="text-center mb-5">Program Scheduled Details</h2>


      <div className="mb-4 d-flex" style={{width:"510px"}}>
      <label className="mr-3"><b>Search:-</b></label>
      <input
        type="text"
        className="form-control"
        placeholder="Search by University,College,Program,Course or Branch"
        value={searchTerm}
        onChange={handleInputChange}
      />
    </div>
 
      {
        filteredPrograms.length!=0 ?(<>
        
        <div className="row">
        {filteredPrograms.map((prog) => (
          <div key={prog.scheduledId} className="col-md-6 mb-4">
            <Card className='coursecard'>
              <Card.Body>
                <Card.Title className='coursetitle'><b><u>Program Scheduled </u></b></Card.Title>
                <Card.Text className='coursetext'>
                  <strong>University Name: </strong> {prog.programScheduledResponseDto.name} <br />
                  <strong>College Name: </strong> {prog.programScheduledResponseDto.collegeName} <br />
                  <strong>Program Name: </strong> {prog.programScheduledResponseDto.programName} <br />
                  <strong>Course Name: </strong> {prog.programScheduledResponseDto.courseName} <br />
                  <strong>Branch Name: </strong> {prog.programScheduledResponseDto.branchName} <br />
                  <strong>Eligibility: </strong> {prog.programScheduledResponseDto.eligibility} <br />
                  <strong>Duration: </strong> {prog.programScheduledResponseDto.duration} <br />
                  <strong>Degree Offered: </strong> {prog.programScheduledResponseDto.degreeOffered} <br />
                  <strong>Start Date: </strong> {prog.startDate} <br />
                  <strong>End Date: </strong> {prog.endDate}
                </Card.Text>
                <Button onClick={()=>{navigate(`/program-schedule/${prog.scheduledId}`)}} className='btn btn-info buttonclass'>Apply Now</Button>
              </Card.Body>
            </Card>
          </div>
        ))}
      </div>
        </>):(<div className='noData '>
        <h1>No Data Found</h1>
        
        </div>)

      }
    </Container>
    </div>
  );
};
 
export default ProgramScheduledApply;