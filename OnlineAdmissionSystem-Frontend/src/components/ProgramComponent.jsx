import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { getCollegeDetailsById, getProgramsByCollegesId } from '../Services/CollegeService';
import { Button, Card, Container } from 'react-bootstrap';
import ugLogo from '../assets/UgLogo.png';
import pgLogo from '../assets/PgLogo.png';
import phdLogo from '../assets/PhDlogo.png';

function ProgramComponent() {

const[program, setProgram] = useState('');
const[college, setCollege] = useState('');


    const navigator = useNavigate();
    const {collegeId} = useParams();

    useEffect(()=>{
        getProgramsByCollegesId(collegeId).then((response)=>{
            setProgram(response.data)
            console.log(response.data);
        }).catch((error)=>{
            console.error(error);
        })
    }, [])

    useEffect(()=>{
        getCollegeDetailsById(collegeId).then((response)=>{
            setCollege(response.data);
        }).catch((error)=>{
            console.error(error);
        })
    }, [])
    function handleLogo(programName)
    {
        // const programName = program.programName.toLowerCase();
       
        if(programName.toLowerCase() === 'under graduate')
        {
            return(ugLogo);
        }else if(programName.toLowerCase() === 'post graduate')
        {
            return(pgLogo);
        }else{
            return(phdLogo);
        }
    }
    function handleCourse(programId){
      navigator(`/course/${programId}`)
    }

    function handleProgram()
    {
        if(program.length>0)
        {
            return(
                <div>
        <Container>
          <h2 className="text-center mb-4 ">Programs in {college.collegeName} </h2>
          <br/><br/>
          <div className="row">
            {program.map((program) => (
              <div key={program.programId} className="col-md-6  programbottom">
                <Card className='programcard'>
                 <div className="program-logo-container" >
                        <img src={handleLogo(program.programName)} className="program-logo" alt="Program Logo" />
                    </div>
                  <Card.Body>
                    <Card.Title style={{textAlign:"left",marginBottom:"20px"}}><h3><b><u>{program.programName}</u></b></h3></Card.Title>
                    <Card.Text><strong>Eligibility:</strong> {program.eligibility}</Card.Text>
                    <Card.Text><strong>Duration:</strong> {program.duration}</Card.Text>
                    <Card.Text><strong>Degree Offered:</strong> {program.degreeOffered}</Card.Text>
                    <Button type="button" className="btn btn-info mb-2" onClick={()=>handleCourse(program.programId)}>
                        Course Info
                      </Button>
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
                <div className='collegediv'>
                     <div className="no">
                <h2 className="noData">No Program Found</h2>
              </div>
    
                </div>
            )
        }
    }
     
      return (
        <div className='programdiv'>
        {handleProgram()}
        </div>
  )
}

export default ProgramComponent;
