package com.cg.admission.service;

import java.util.List;

import com.cg.admission.dto.PaymentDto;
import com.cg.admission.entity.Payment;

public interface PaymentService {
	PaymentDto addPayment(Long paymentId, Payment payment);

	List<PaymentDto> viewAllPaymentDetails();

	PaymentDto getPaymentDetailsByPaymentId(Long paymentId);

	PaymentDto getPaymentDetailsByApplicationId(Long applicationId);

	PaymentDto getPaymentDetailsByEmailId(String emailId);

	String deletePaymentById(Long paymentId);

	PaymentDto updatePaymentDetails(Payment payment, Long paymentId);

	List<PaymentDto> getPaymentDetailsByStatus(String paymentStatus);

}