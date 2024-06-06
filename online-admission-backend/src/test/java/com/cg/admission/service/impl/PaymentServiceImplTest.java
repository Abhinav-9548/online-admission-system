package com.cg.admission.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cg.admission.dto.PaymentDto;
import com.cg.admission.entity.Admission;
import com.cg.admission.entity.Application;
import com.cg.admission.entity.Payment;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.repository.AdmissionRepository;
import com.cg.admission.repository.ApplicationRepository;
import com.cg.admission.repository.PaymentRepository;

public class PaymentServiceImplTest {

	@InjectMocks
	private PaymentServiceImpl paymentService;

	@Mock
	private PaymentRepository paymentRepository;

	@Mock
	private ApplicationRepository applicationRepository;

	@Mock
	private AdmissionRepository admissionRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
/**Test for adding payment successfully, 
	verifies payment is made successfully
	**/
	@Test
	void testAddPayment() {
		Long applicationId = 1L;
		Payment payment = new Payment();
		payment.setEmailId("test@example.com");

		Application application = new Application();
		application.setApplicationId(applicationId);
		application.setEmailId("test@example.com");
		application.setApplicationStatus("Accepted");

		when(applicationRepository.findById(applicationId)).thenReturn(Optional.of(application));
		when(paymentRepository.save(payment)).thenReturn(payment);

		Admission admission = new Admission();
		when(admissionRepository.findByApplication_ApplicationId(applicationId)).thenReturn(admission);

		PaymentDto paymentDto = paymentService.addPayment(applicationId, payment);

		assertEquals(payment.getEmailId(), paymentDto.getEmailId());
		verify(admissionRepository, times(1)).save(admission);
	}
/**Test for viewing all the payments
 * Verifies the list of payments is returned
 */
	@Test
	void testViewAllPaymentDetails() {
		List<Payment> payments = new ArrayList<>();
		Payment payment = new Payment();
		payments.add(payment);

		when(paymentRepository.findAll()).thenReturn(payments);

		List<PaymentDto> paymentDtos = paymentService.viewAllPaymentDetails();

		assertEquals(1, paymentDtos.size());
	}
/**Verifies payment details is fetched by paymentID**/
	@Test
	void testGetPaymentDetailsByPaymentId() {
		Long paymentId = 1L;
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);

		when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));

		PaymentDto paymentDto = paymentService.getPaymentDetailsByPaymentId(paymentId);

		assertEquals(paymentId, paymentDto.getPaymentId());
	}
/** Test for payment details when there is no payment associated with particular paymentID**/
	@Test
	void testGetPaymentDetailsByPaymentId_NotFound() {
		Long paymentId = 1L;

		when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			paymentService.getPaymentDetailsByPaymentId(paymentId);
		});
	}
/**Test to get payment details by applicationID**/
	@Test
	void testGetPaymentDetailsByApplicationId() {
		Long applicationId = 1L;
		Payment payment = new Payment();
		Application application = new Application();
		application.setApplicationId(applicationId);
		payment.setApplication(application);

		when(paymentRepository.findPaymentByApplication_ApplicationId(applicationId)).thenReturn(payment);

		PaymentDto paymentDto = paymentService.getPaymentDetailsByApplicationId(applicationId);

		assertEquals(applicationId, paymentDto.getApplication().getApplicationId());
	}
/**Test when there are payments found with specific applicationID**/
	@Test
	void testGetPaymentDetailsByApplicationId_NotFound() {
		Long applicationId = 1L;

		when(paymentRepository.findPaymentByApplication_ApplicationId(applicationId)).thenReturn(null);

		assertThrows(ResourceNotFoundException.class, () -> {
			paymentService.getPaymentDetailsByApplicationId(applicationId);
		});
	}

	/** Test to delete specific payment associated with paymentID**/
	@Test
	void testDeletePaymentById() {
		Long paymentId = 1L;
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);

		when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));

		String result = paymentService.deletePaymentById(paymentId);

		assertEquals("Payment deleted successfully", result);
		verify(paymentRepository, times(1)).delete(payment);
	}
/** Test to update the payment details associated with specific paymentID**/
	@Test
	void testUpdatePaymentDetails() {
		Long paymentId = 1L;
		Payment existingPayment = new Payment();
		existingPayment.setPaymentId(paymentId);

		Payment updatedPayment = new Payment();
		updatedPayment.setPaymentStatus("Paid");

		when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(existingPayment));
		when(paymentRepository.save(existingPayment)).thenReturn(existingPayment);

		PaymentDto result = paymentService.updatePaymentDetails(updatedPayment, paymentId);

		assertEquals("Paid", result.getPaymentStatus());
		verify(paymentRepository, times(1)).save(existingPayment);
	}
}