package com.cg.admission.repository;
import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.admission.entity.Payment;
 
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	Payment findPaymentByApplication_ApplicationId(Long applicationId);
	String deletePaymentByApplication_ApplicationId(Long applicationId);
 
	Payment findPaymentByEmailId(String emailId);
 
	List<Payment> findPaymentByPaymentStatus(String paymentStatus);
}