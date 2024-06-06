import axios from "axios";

const REST_API_BASE_URL_COLLEGE = 'http://localhost:8080/api';
const REST_API_BASE_URL= "http://localhost:8080"

export const getProgramsByCollegesId = (Id) =>{
    return axios.get(REST_API_BASE_URL_COLLEGE+ '/'+Id +'/programs');
}

export const getCollegeDetailsById = (Id) =>{
    return axios.get(REST_API_BASE_URL_COLLEGE + '/college/collegeid/' + Id);
}
 

 
export const collegeList = (universityId) => axios.get(REST_API_BASE_URL +'/university/'+universityId+'/colleges');
 
export const getCollegeByAll = (searchTerm) => axios.get(REST_API_BASE_URL +'/api/colleges-all/'+searchTerm);
// export const colleges = (searchTerm) => axios.get(REST_API_BASE_URL +'/api/colleges-all/'+searchTerm);
export const colleges = () => axios.get(REST_API_BASE_URL +'/api/colleges');
 
export const addCollege = (universityId,college) => axios.post(REST_API_BASE_URL +'/api/university/'+universityId+'/college',college);
 
export const updateCollege = (collegeId,college) => axios.put(REST_API_BASE_URL + '/api/updatecollege/'+collegeId,college);
 
export const deleteCollege = (collegeId) => axios.delete(REST_API_BASE_URL +'/api/deletecollege/collegeid/'+collegeId);
 
// export const getCollege = (collegeId) => axios.get(REST_API_BASE_URL +'/api/college/collegeid/' +collegeId);