import axios from "axios";

const REST_API_BASE_URL_COURSE = 'http://localhost:8080/api';
 
const REST_API_BASE_URL = 'http://localhost:8080/api';

export const findCoursesByProgramId = (Id) =>{
    return axios.get(REST_API_BASE_URL_COURSE+'/program/'+Id+'/courses');
}
export const findProgramById = (Id) =>{
    return axios.get(REST_API_BASE_URL_COURSE+'/program/Id/'+Id);
}



 
// export const listCourse= (programId)=> axios.get(REST_API_BASE_URL +'/program/'+programId+'/courses');
 
export const getCourseById = (courseId) => axios.get(REST_API_BASE_URL +'/course/coursedetail/'+courseId)
 
export const addCourse =(collegeId,programId,course) =>axios.post(REST_API_BASE_URL +'/course/'+collegeId+'/program/'+programId,course)
 
export const updateCourse =(courseId,course) =>axios.put(REST_API_BASE_URL +'/course/update/'+courseId,course)
 
export const deleteCourse =(courseId) =>axios.delete(REST_API_BASE_URL +'/course/'+courseId)