import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { Button, Card, Container } from 'react-bootstrap';
import { getProgramScheduleByBranch } from '../Services/ProgramScheduleService';

function ProgramScheduleBranch() {



    const[programScheduled,setProgramScheduled]=useState([])
 
    const {branchId} = useParams();
    const navigator = useNavigate();

    useEffect(()=>{
      getAllProgramSchedule();
    },[])
 
    function getAllProgramSchedule(){
      getProgramScheduleByBranch(branchId).then((response)=>{
        setProgramScheduled(response.data)
      }).catch((error)=>{
        console.log(error)
      })
    }

    function handleApply(scheduledId)
    {
        navigator(`/program-schedule/${scheduledId}`)
    }

    function print()
    {
      if(programScheduled.length>0)
      {
        return(
<div>
        
        
        <Container className='containercard'>
    <h2 className="text-center mb-4">Program Schedule Details</h2>
    <div className="row">
      {programScheduled.map((prog) => (
        <div key={prog.scheduledId} className="col-md-6 mb-4">
          <Card className='coursecard'>
            <Card.Body>
              <Card.Title className='coursetitle'><b><u>Program Scheduled For {prog.programScheduledResponseDto.branchName}</u></b></Card.Title>
              <Card.Text className='coursetext'>
              <strong>University Name: </strong> {prog.programScheduledResponseDto.name} <br />
              <strong>College Name: </strong> {prog.programScheduledResponseDto.collegeName} <br />
              <strong>Program Name: </strong> {prog.programScheduledResponseDto.programName} <br />
              <strong>Course Name: </strong> {prog.programScheduledResponseDto.courseName} <br />
              <strong>Eligibility: </strong> {prog.programScheduledResponseDto.eligibility} <br />
              <strong>Duration: </strong> {prog.programScheduledResponseDto.duration} <br />
              <strong>Degree Offered: </strong> {prog.programScheduledResponseDto.degreeOffered} <br />
              <strong>Start Date: </strong> {prog.startDate} <br />
              <strong>End Date: </strong> {prog.endDate}
              </Card.Text>
             
               <Button className='btn btn-info buttonclass' onClick={()=>handleApply(prog.scheduledId)} >Apply Now</Button>
            </Card.Body>
          </Card>
        </div>
      ))}
    </div>
  </Container>

    </div>

        )
      }else{
        return(
          <div className="no">
          <h2 className="noData">No Program Schedule Found</h2>
        </div>

        )
      }
    }

  return (
    <div className='programschedulebranch'>
{print()}

    </div>
  )
}

export default ProgramScheduleBranch