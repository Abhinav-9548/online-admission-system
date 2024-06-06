import React, { useState } from 'react'
 
import { useNavigate, useParams } from 'react-router-dom';
import { Form ,Alert} from 'react-bootstrap';
import { updateProgramScheduled } from '../Services/ProgramScheduleService';


function AdminProgramScheduleList() {

    const [startDate,setStartDate]=useState("");
    const [endDate,setEndDate]=useState("");
    const {scheduleId}=useParams();
    const [error, setError] = useState("");
    const navigator =useNavigate();
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
    const updateProgramScheduledDetails =(e)=>{
        e.preventDefault();
        if (!validateDates()) {
            return;
          }
          
        const programScheduled={startDate,endDate}
        console.log(programScheduled)
       if(scheduleId){
         updateProgramScheduled(scheduleId,programScheduled).then((response)=>{
            console.log(response.data);
            navigator('/programschedule')
         }).catch(error=>{
            console.log(error)
         })
        }
        else{
            console.log("ProgramScheduledNotPresent")
        }
    }


  return (
    <div className='adminprogramschedulelistdiv'>
        

        <div className='container'>
    <br></br>
    <div className='row'>
        <div className='card col-md-6 offset-md-3 offset-md-3'>
            <h3 className='mt-4' style={{textAlign:"center"}}>Program Scheduled Update</h3>
            <div className='card-body'>
                <form>
                    <div className='form-group mb-2'>
                        <Form.Group controlId="startDate">
                        <Form.Label>Start Date</Form.Label>
                        <Form.Control type="date" name="startDate" value={startDate} onChange={(e)=>{setStartDate(e.target.value)}} />
                         </Form.Group>
                    </div>
                   
                    <div className='form-group mb-2'>
                    <Form.Group controlId="endDate">
                    <Form.Label>End Date</Form.Label>
                    <Form.Control type="date" name="endDate" value={endDate} onChange={(e)=>{setEndDate(e.target.value)}} />
                    </Form.Group>
                    {error && <Alert variant="danger" className="mt-2">{error}</Alert>}
                    <br></br>
                    </div>
           
           
            <button className='btn btn-success mb-4 mt-5' onClick={updateProgramScheduledDetails}>Submit</button>
        </form>
    </div>
</div>
</div >
</div >


    </div>
  )
}

export default AdminProgramScheduleList