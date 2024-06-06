import axios from 'axios';

const REST_API_BASE_URL_UNIVERSITY = 'http://localhost:8080/university';


// export const getAllColleges = (universityId) =>{
//     return axios.get(REST_API_BASE_URL_UNIVERSITY +'/'+ universityId+'/colleges');
// }

export const getUniversityDetailsByCity = (city) =>{
    return axios.get(REST_API_BASE_URL_UNIVERSITY +'/city/'+ city); 
}

export const getAllUniversity = () =>{
    return axios.get(REST_API_BASE_URL_UNIVERSITY);
}

export const getUniversityById = (Id) =>{
    return axios.get(REST_API_BASE_URL_UNIVERSITY+'/id/'+Id);
}

export const addUniversity = (universityObj) =>{
    return axios.post(REST_API_BASE_URL_UNIVERSITY,universityObj);
}

export const deleteUniversityById = (Id) =>{
    return axios.delete(REST_API_BASE_URL_UNIVERSITY+'/id/'+Id);
}

export const updateUniversity =(Id, universityObj) =>{
    return axios.put(REST_API_BASE_URL_UNIVERSITY+'/update/'+Id, universityObj);    
}

export const findCollegeByUniverisityId = (universityId) =>{
    return axios.get(REST_API_BASE_URL_UNIVERSITY +'/'+universityId+'/colleges');
}

