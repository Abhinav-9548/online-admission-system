package com.cg.admission.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.admission.dto.ApplicationDto;
import com.cg.admission.dto.PaymentDto;
import com.cg.admission.entity.Admission;
import com.cg.admission.entity.Application;
import com.cg.admission.entity.Payment;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.repository.AdmissionRepository;
import com.cg.admission.repository.ApplicationRepository;
import com.cg.admission.repository.PaymentRepository;
import com.cg.admission.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private ApplicationRepository applicationRepository;
	@Autowired
	private AdmissionRepository admissionRepository;
	

	@Override 
	public PaymentDto addPayment(Long applicationId, Payment payment) {

		Application application = applicationRepository.findById(applicationId)
				.orElseThrow(() -> new ResourceNotFoundException("No application found with Id " + applicationId));

		if (!payment.getEmailId().equalsIgnoreCase(application.getEmailId())) {
			throw new ResourceNotFoundException(
					"Application email does not match with payment email " + payment.getEmailId());
		}

		if (!application.getApplicationStatus().equalsIgnoreCase("Accepted")) {
			throw new ResourceNotFoundException("Application is not accepted  " + payment.getEmailId());
		}

		payment.setApplication(application);
		payment = paymentRepository.save(payment);
		Admission admission=admissionRepository.findByApplication_ApplicationId(applicationId);
		admission.setAdmissionStatus("Accepted");
		admissionRepository.save(admission);
		 

		PaymentDto savedPaymentDto = new PaymentDto();
		BeanUtils.copyProperties(payment, savedPaymentDto);
		ApplicationDto applicationDto = new ApplicationDto();
		BeanUtils.copyProperties(payment.getApplication(), applicationDto);
		savedPaymentDto.setApplication(applicationDto);

		return savedPaymentDto;
	}

	@Override
	public List<PaymentDto> viewAllPaymentDetails() {
		// TODO Auto-generated method stub
		List<Payment> payments = paymentRepository.findAll();
		if (payments.isEmpty()) {
			throw new ResourceNotFoundException("No payments found");
		}
		return payments.stream().map(payment -> {
			PaymentDto paymentDto = new PaymentDto();

			BeanUtils.copyProperties(payment, paymentDto);
			if (payment.getApplication() != null) {
				ApplicationDto applicationDto = new ApplicationDto();
				BeanUtils.copyProperties(payment.getApplication(), applicationDto);
				paymentDto.setApplication(applicationDto);
			}

			return paymentDto;
		}).collect(Collectors.toList());
	}

	@Override
	public PaymentDto getPaymentDetailsByPaymentId(Long paymentId) {
		// TODO Auto-generated method stub
		Payment payment = paymentRepository.findById(paymentId)
				.orElseThrow(() -> new ResourceNotFoundException("No payment found with Id " + paymentId));
		PaymentDto paymentDto = new PaymentDto();
		BeanUtils.copyProperties(payment, paymentDto);
		if (payment.getApplication() != null) {
			ApplicationDto applicationDto = new ApplicationDto();
			BeanUtils.copyProperties(payment.getApplication(), applicationDto);
			paymentDto.setApplication(applicationDto);
		}
		return paymentDto;
	}

	@Override
	public PaymentDto getPaymentDetailsByApplicationId(Long applicationId) {
		// TODO Auto-generated method stub
		Payment payment = paymentRepository.findPaymentByApplication_ApplicationId(applicationId);
		if (payment == null) {
			throw new ResourceNotFoundException("No payment found with application Id " + applicationId);
		}
		PaymentDto paymentDto = new PaymentDto();
		ApplicationDto applicationDto = new ApplicationDto();
		BeanUtils.copyProperties(payment.getApplication(), applicationDto);
		paymentDto.setApplication(applicationDto);
		BeanUtils.copyProperties(payment, paymentDto);

		return paymentDto;
	}

	@Override
	public PaymentDto getPaymentDetailsByEmailId(String emailId) {
		// TODO Auto-generated method stub
		Payment payment = paymentRepository.findPaymentByEmailId(emailId);
		if (payment == null) {
			throw new ResourceNotFoundException("No payment found with email Id " + emailId);
		}
		PaymentDto paymentDto = new PaymentDto();
		BeanUtils.copyProperties(payment, paymentDto);
		if (payment.getApplication() != null) {
			ApplicationDto applicationDto = new ApplicationDto();
			BeanUtils.copyProperties(payment.getApplication(), applicationDto);
			paymentDto.setApplication(applicationDto);
		}
		return paymentDto;
	}

	@Override
	public String deletePaymentById(Long paymentId) {
		// TODO Auto-generated method stub
		Payment payment = paymentRepository.findById(paymentId)
				.orElseThrow(() -> new ResourceNotFoundException("No payment found with Id: " + paymentId));
		paymentRepository.delete(payment); 
		return "Payment deleted successfully";
	}

	@Override // only status can be addedd
	public PaymentDto updatePaymentDetails(Payment payment, Long paymentId) {
		// Fetch the existing payment by its ID
		Payment existingPayment = paymentRepository.findById(paymentId)
				.orElseThrow(() -> new ResourceNotFoundException("No payment found with Id " + paymentId));

		// Update the payment status and other necessary fields
		existingPayment.setPaymentStatus(payment.getPaymentStatus());

		// If the Payment object contains an application, fetch it and set it to the
		// payment
		if (payment.getApplication() != null && payment.getApplication().getApplicationId() != null) {
			Application application = applicationRepository.findById(payment.getApplication().getApplicationId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"No application found with Id " + payment.getApplication().getApplicationId()));
			existingPayment.setApplication(application);
		}

		// Save the updated payment
		existingPayment = paymentRepository.save(existingPayment);

		// Convert the updated payment to PaymentDto
		PaymentDto updatedPaymentDto = new PaymentDto();
		BeanUtils.copyProperties(existingPayment, updatedPaymentDto);

		// If the payment has an associated application, convert it to ApplicationDto
		// and set it
		if (existingPayment.getApplication() != null) {
			ApplicationDto applicationDto = new ApplicationDto();
			BeanUtils.copyProperties(existingPayment.getApplication(), applicationDto);
			updatedPaymentDto.setApplication(applicationDto);
		}

		return updatedPaymentDto;
	}

	@Override
	public List<PaymentDto> getPaymentDetailsByStatus(String paymentStatus) {
		// TODO Auto-generated method stub
		List<Payment> payments = paymentRepository.findPaymentByPaymentStatus(paymentStatus);

		return payments.stream().map(payment -> {
			PaymentDto paymentDto = new PaymentDto();
			BeanUtils.copyProperties(payment, paymentDto);
			return paymentDto;
		}).collect(Collectors.toList());
	}

}