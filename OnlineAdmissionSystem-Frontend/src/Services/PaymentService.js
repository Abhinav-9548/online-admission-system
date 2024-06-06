import axios from 'axios';
 
const API_BASE_URL = 'http://localhost:8080/payments';
 
export const postPayment = (applicationId, payment) => {
    return axios.post(`${API_BASE_URL}/applicationId/${applicationId}`, payment);
};
 
export const getAllPayment = () => {
    return axios.get(API_BASE_URL);
};
 
export const getPaymentDetailsByPaymentId = (paymentId) => {
    return axios.get(`${API_BASE_URL}/${paymentId}`);
};
 
export const getPaymentDetailsByApplicationId = (applicationId) => {
    return axios.get(`${API_BASE_URL}/application/${applicationId}`);
};
 
export const getPaymentDetailsByEmailId = (emailId) => {
    return axios.get(`${API_BASE_URL}/email/${emailId}`);
};
 
export const getPaymentDetailsByStatus = (paymentStatus) => {
    return axios.get(`${API_BASE_URL}/status/${paymentStatus}`);
};
 
export const deletePaymentById = (paymentId) => {
    return axios.delete(`${API_BASE_URL}/${paymentId}`);
};
 
export const updatePaymentDetails = (payment, paymentId) => {
    return axios.put(`${API_BASE_URL}/${paymentId}`, payment);
};