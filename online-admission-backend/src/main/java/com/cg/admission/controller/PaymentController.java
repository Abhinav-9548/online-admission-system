package com.cg.admission.controller;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.admission.dto.PaymentDto;
import com.cg.admission.entity.Payment;
import com.cg.admission.service.PaymentService;

import jakarta.validation.Valid;
 
@RestController
@RequestMapping("/payments")
@CrossOrigin("*")
public class PaymentController { 
 
	@Autowired
	private PaymentService paymentService;
  

//	/	@PreAuthorize("hasRole('usER')")
	@PostMapping("/applicationId/{applicationId}")
	public ResponseEntity<PaymentDto> addPayment(@PathVariable("applicationId") Long applicationId,@Valid @RequestBody Payment paymentDto) {
		PaymentDto savedPayment = paymentService.addPayment(applicationId,paymentDto);
		return ResponseEntity.ok(savedPayment);
	}

//	/	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<PaymentDto>> viewAllPaymentDetails() {
		List<PaymentDto> payments = paymentService.viewAllPaymentDetails();
		return ResponseEntity.ok(payments);
	}
 
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/{paymentId}")
	public ResponseEntity<PaymentDto> getPaymentDetailsByPaymentId(@PathVariable Long paymentId) {
		PaymentDto payment = paymentService.getPaymentDetailsByPaymentId(paymentId);
		return ResponseEntity.ok(payment);
	}

//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/application/{applicationId}")
	public ResponseEntity<PaymentDto> getPaymentDetailsByApplicationId(@PathVariable Long applicationId) {
		PaymentDto payment = paymentService.getPaymentDetailsByApplicationId(applicationId);
//		return new ResponseEntity<PaymentDto>(payment,HttpStatus.OK);
		return ResponseEntity.ok(payment);
	}
 
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/email/{emailId}")
	public ResponseEntity<PaymentDto> getPaymentDetailsByEmailId(@PathVariable String emailId) {
		PaymentDto payment = paymentService.getPaymentDetailsByEmailId(emailId);
		return ResponseEntity.ok(payment);
	}
 
	

//	/	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/status/{paymentStatus}")
	public ResponseEntity<List<PaymentDto>> getPaymentDetailsByStatus(@PathVariable String paymentStatus) {
		List<PaymentDto> payments = paymentService.getPaymentDetailsByStatus(paymentStatus);
		return ResponseEntity.ok(payments);
	}
 

//	/	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{paymentId}")
	public ResponseEntity<String> deletePaymentById(@PathVariable Long paymentId) {
		String message = paymentService.deletePaymentById(paymentId);
		return ResponseEntity.ok(message);
	}
 

//	/	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{paymentId}")
	public ResponseEntity<PaymentDto> updatePaymentDetails(@Valid @RequestBody Payment payment,
			@PathVariable Long paymentId) {
		PaymentDto updatedPayment = paymentService.updatePaymentDetails(payment, paymentId);
		return ResponseEntity.ok(updatedPayment);
	}
}