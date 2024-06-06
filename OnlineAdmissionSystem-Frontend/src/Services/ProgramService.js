import axios from 'axios';
 
const REST_API_BASE_URL= "http://localhost:8080"
 
export const getProgramList =(collegeId)=> axios.get(REST_API_BASE_URL +'/api/'+collegeId+'/programs')
 
export const getProgramById = (programId) => axios.get(REST_API_BASE_URL +'/api/program/Id/'+programId)
 
export const addProgram = (collegeId,program) => axios.post(REST_API_BASE_URL + '/api/add/'+collegeId,program)
 
export const updateProgram = (programId,program) => axios.put(REST_API_BASE_URL +'/api/update/' +programId,program)
 
export const deleteProgram = (programId) => axios.delete(REST_API_BASE_URL +'/api/delete/'+programId)