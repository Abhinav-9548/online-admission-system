import axios from "axios";
// const REST_API_BASE_URL='http://localhost:8080/api/application/programschedule/367/add';
 
 
export const  postApplication=(programscheduleID,application)=>{
    return axios.post('http://localhost:8080/api/application/programschedule/'+programscheduleID+'/add',application);
}
 
 
export const getAllApplication=()=>{
    return axios.get('http://localhost:8080/api/application/all')
}
 
export const getApplicationId=(appId)=>{
    return axios.get('http://localhost:8080/api/application/'+appId);
}
 
export const getApplicationByEmail=(emailId)=>{
    return axios.get('http://localhost:8080/api/application/email/'+emailId);
}
 
export const updateApplication=(appId,application)=>{
    return axios.put("http://localhost:8080/api/application/"+appId,application)
}

export const deleteApplication=(appId)=>{
    return axios.delete("http://localhost:8080/api/application/"+appId)
}