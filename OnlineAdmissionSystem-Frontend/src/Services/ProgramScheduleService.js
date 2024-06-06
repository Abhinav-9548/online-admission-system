import axios from "axios";
 
const REST_API_BASE_URL = "http://localhost:8080/university"
 
export const getAllUniversity = ()=>axios.get(REST_API_BASE_URL );
 
export const getCollegesByUniversity=(universityId)=>axios.get(REST_API_BASE_URL+"/"+universityId+"/colleges")
 
export const getProgramsByColleges=(collegeRegId)=>axios.get("http://localhost:8080/api/"+collegeRegId+"/programs")
 
export const getCoursesByProgram=(programId)=>axios.get("http://localhost:8080/api/program/"+programId+"/courses")
 
export const getBranchByCourses=(courseId)=>axios.get("http://localhost:8080/api/courses/"+courseId+"/branches")
 
export const postProgramSchedule=(universityId,collegeRegId,programId,courseId,branchId,programScheduled)=>{
    return axios.post(`http://localhost:8080/api/programscheduled/${universityId}/${collegeRegId}/${programId}/${courseId}/${branchId}`,programScheduled)
}
 
export const listProgramScheduled= ()=>{
    return axios.get("http://localhost:8080/api"+'/allprogramscheduled');
};
 
export const deleteProgramScheduled= (scheduledId) => axios.delete("http://localhost:8080/api"+'/deleteprogramscheduled/'+scheduledId)
 
export const updateProgramScheduled= (scheduledId,programScheduled) => axios.put("http://localhost:8080/api"+'/update/programscheduled/'+scheduledId,programScheduled)
 
export const getProgramScheduleByCourse = (courseId) => axios.get("http://localhost:8080/api" +'/programscheduled/courseId/'+courseId)
 
export const getProgramScheduleByBranch = (branchId) => axios.get("http://localhost:8080/api" +'/programscheduled/branchId/'+branchId)
 
export const getAllProgramScheduled=()=>axios.get("http://localhost:8080/api"+"/allprogramscheduled")