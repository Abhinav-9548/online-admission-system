import axios from "axios";
 
const REST_API_BASE_URL = "http://localhost:8080/api"
 
export const getAllBranchesByCourseId = (courseId)=>
{
    return axios.get(REST_API_BASE_URL +'/courses'+'/'+courseId+'/branches');
}


 
// export const branchList=(courseId)=>axios.get(REST_API_BASE_URL +'/courses/'+courseId+'/branches');
 
export const branches = () =>axios.get(REST_API_BASE_URL+ '/branches');
 
export const getBranch = (branchId) => axios.get(REST_API_BASE_URL + '/branch/branchid/'+branchId);
 
export const addBranch = (collegeId,courseId,branch) => axios.post(REST_API_BASE_URL+'/college/'+collegeId+'/course/'+courseId+'/branch',branch);
 
export const updateBranch = (branch, branchId)=>axios.put(REST_API_BASE_URL+'/updateBranch/'+branchId,branch);
 
export const deleteBranch = (branchId) =>axios.delete(REST_API_BASE_URL+'/deleteBranch/byId/'+branchId);