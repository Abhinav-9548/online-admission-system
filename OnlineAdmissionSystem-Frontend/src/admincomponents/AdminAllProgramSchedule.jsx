import { useNavigate } from 'react-router-dom';
import { deleteProgramScheduled, listProgramScheduled } from '../Services/ProgramScheduleService';
 import React, { useEffect, useState } from 'react'
 
function AdminAllProgramSchedule() {
    const [programScheduled, setProgramScheduled] = useState([]);
    const [searchQuery, setSearchQuery] = useState('');
    const navigate = useNavigate();
 
    useEffect(() => {
        getAllProgramScheduled();
    }, []);
 
    function getAllProgramScheduled() {
        listProgramScheduled().then((response) => {
            setProgramScheduled(response.data);
        }).catch((error) => {
            console.error(error);
        });
    }
 
    function addProgramScheduled() {
        navigate(`/addprogramScheduled`);
    }
 
    function updateProgramScheduled(scheduledId) {
        navigate(`/updateprogramScheduled/${scheduledId}`);
    }
 
    function deleteProgramScheduledDetails(scheduledId) {
        console.log(scheduledId);
        deleteProgramScheduled(scheduledId).then((response) => {
            alert("Program deleted Successfully");
            navigate(`/programschedule`);
        }).catch(error => {
            console.log(error);
        });
    }
 
    const handleSearch = (event) => {
        setSearchQuery(event.target.value);
    }
 
    const filteredPrograms = programScheduled.filter(program =>
        program.programScheduledResponseDto.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
        program.programScheduledResponseDto.collegeName.toLowerCase().includes(searchQuery.toLowerCase()) ||
        program.programScheduledResponseDto.programName.toLowerCase().includes(searchQuery.toLowerCase()) ||
        program.programScheduledResponseDto.courseName.toLowerCase().includes(searchQuery.toLowerCase()) ||
        program.programScheduledResponseDto.branchName.toLowerCase().includes(searchQuery.toLowerCase())
    );
 
    return (
        <div className='adminallprogramschedulediv'>
            <div className='container'>
                
              
                {filteredPrograms.length > 0 ? (
                    <div>
                        <h2 className='text-center mb-5'>List of ProgramSchedules</h2>
                          <div className='mb-4 d-flex' style={{width:"450px"}} >
                    <label className='mr-2'><b>Search:-</b></label>
                    <input
                        type="text"
                        className='form-control'
                        placeholder='Search by name, college, program, course, branch'
                        value={searchQuery}
                        onChange={handleSearch}
                    />
                </div>
                    <table className='table table-bordered table-striped'>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>University Name</th>
                                <th>College Name</th>
                                <th>Program Name</th>
                                <th>Course Name</th>
                                <th>Branch Name</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {filteredPrograms.map((program) => (
                                <tr key={program.scheduledId}>
                                    <td>{program.scheduledId}</td>
                                    <td>{program.programScheduledResponseDto.name}</td>
                                    <td>{program.programScheduledResponseDto.collegeName}</td>
                                    <td>{program.programScheduledResponseDto.programName}</td>
                                    <td>{program.programScheduledResponseDto.courseName}</td>
                                    <td>{program.programScheduledResponseDto.branchName}</td>
                                    <td>{program.startDate}</td>
                                    <td>{program.endDate}</td>
                                    <td className="d-flex justify-content-center gap-3">
                                        <button className="btn btn-info" onClick={() => updateProgramScheduled(program.scheduledId)}>Update</button>
                                        <button className="btn btn-danger" onClick={() => deleteProgramScheduledDetails(program.scheduledId)}>Delete</button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                       
                    </table>
                    <button className='btn btn-success mb-2 mt-4' onClick={addProgramScheduled}>Add Programscheduled</button>
                    </div>
                    
                ) : (
                    <div className='noData' >
                        <h2>No Program Schedule Found</h2>
                        <div>
                            <center>
                                <button className='btn btn-success mb-2' onClick={addProgramScheduled}>Add Programscheduled</button>
                            </center>
                        </div>
                    </div>
                )}
                
            </div>
        </div>
    );
}
 
export default AdminAllProgramSchedule;














