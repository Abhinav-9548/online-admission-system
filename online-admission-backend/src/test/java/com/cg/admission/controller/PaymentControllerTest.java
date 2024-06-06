package com.cg.admission.controller;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.admission.dto.PaymentDto;
import com.cg.admission.entity.Payment;
import com.cg.admission.service.PaymentService;
 
public class PaymentControllerTest {
 
    @InjectMocks
    private PaymentController paymentController;
 
    @Mock
    private PaymentService paymentService;
 
    private PaymentDto paymentDto;
    private Payment payment;
 
    @BeforeEach
    void setUp() {
        //Initialize mock data
        MockitoAnnotations.openMocks(this);
        paymentDto = new PaymentDto();
        paymentDto.setPaymentId(1L);
        paymentDto.setPaymentAmount(1000.0);
        paymentDto.setPaymentStatus("Completed");
        payment = new Payment();
        payment.setPaymentId(1L);
        payment.setPaymentAmount(1000.0);
        payment.setPaymentStatus("Completed");
    }
 
    @Test
    void testAddPayment() {
    	//Mock the service method
        when(paymentService.addPayment(anyLong(), any(Payment.class))).thenReturn(paymentDto);
        //Call the controller method
        ResponseEntity<PaymentDto> response = paymentController.addPayment(1L, payment);
        //Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paymentDto, response.getBody());
        verify(paymentService, times(1)).addPayment(anyLong(), any(Payment.class));
    }
 
    @Test
    void testViewAllPaymentDetails() {
        List<PaymentDto> paymentList = Arrays.asList(paymentDto);
        //Mock the service method
        when(paymentService.viewAllPaymentDetails()).thenReturn(paymentList);
        //Call the controller method
        ResponseEntity<List<PaymentDto>> response = paymentController.viewAllPaymentDetails();
        //Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paymentList, response.getBody());
        verify(paymentService, times(1)).viewAllPaymentDetails();
    }
 
    @Test
    void testGetPaymentDetailsByPaymentId() {
    	//Mock the service method
        when(paymentService.getPaymentDetailsByPaymentId(1L)).thenReturn(paymentDto);
        //Call the controller method
        ResponseEntity<PaymentDto> response = paymentController.getPaymentDetailsByPaymentId(1L);
 
        //Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paymentDto, response.getBody());
        verify(paymentService, times(1)).getPaymentDetailsByPaymentId(1L);
    }
 
    @Test
    void testGetPaymentDetailsByApplicationId() {
        //Mock the service method
        when(paymentService.getPaymentDetailsByApplicationId(1L)).thenReturn(paymentDto);
 
        //Call the controller method
        ResponseEntity<PaymentDto> response = paymentController.getPaymentDetailsByApplicationId(1L);
 
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paymentDto, response.getBody());
        verify(paymentService, times(1)).getPaymentDetailsByApplicationId(1L);
    }
 
    @Test
    void testGetPaymentDetailsByEmailId() {
        //Mock the service method
        when(paymentService.getPaymentDetailsByEmailId("test@example.com")).thenReturn(paymentDto);
        
        //Call the controller method
        ResponseEntity<PaymentDto> response = paymentController.getPaymentDetailsByEmailId("test@example.com");
        
        //Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paymentDto, response.getBody());
        verify(paymentService, times(1)).getPaymentDetailsByEmailId("test@example.com");
    }
 
    @Test
    void testGetPaymentDetailsByStatus() {
        List<PaymentDto> paymentList = Arrays.asList(paymentDto);
        
        //Mock the service method
        when(paymentService.getPaymentDetailsByStatus("Completed")).thenReturn(paymentList);
 
        //Call the controller method
        ResponseEntity<List<PaymentDto>> response = paymentController.getPaymentDetailsByStatus("Completed");
 
        //Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paymentList, response.getBody());
        verify(paymentService, times(1)).getPaymentDetailsByStatus("Completed");
    }
 
    @Test
    void testDeletePaymentById() {
    	
        //Mock the service method
        when(paymentService.deletePaymentById(1L)).thenReturn("Payment deleted successfully");
 
        //Call the controller method
        ResponseEntity<String> response = paymentController.deletePaymentById(1L);
 
        //Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payment deleted successfully", response.getBody());
        verify(paymentService, times(1)).deletePaymentById(1L);
    }
 
    @Test
    void testUpdatePaymentDetails() {
        //Mock the service method
        when(paymentService.updatePaymentDetails(any(Payment.class), anyLong())).thenReturn(paymentDto);
 
        //Call the controller method
        ResponseEntity<PaymentDto> response = paymentController.updatePaymentDetails(payment, 1L);
 
        //Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paymentDto, response.getBody());
        verify(paymentService, times(1)).updatePaymentDetails(any(Payment.class), anyLong());
    }
}