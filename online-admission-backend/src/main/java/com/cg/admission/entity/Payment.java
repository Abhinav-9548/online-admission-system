package com.cg.admission.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {
	@Id
	@SequenceGenerator(name = "payment_", initialValue = 6864, allocationSize = 6)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_") 
	private Long paymentId;
	
	
	
	@NotBlank(message = "Email cannot be blank")
    @Size(max = 30, message = "Email cannot exceed 30 characters")
	private String emailId;

	@OneToOne 
	private Application application;

	
	@NotNull(message = "Payment amount cannot be blank")
	private double paymentAmount;
	
	
	
	@NotBlank(message = "Payment Description cannot be blank")
    @Size(max = 250, message = "Payment Description cannot exceed 250 characters")
	private String paymentDescription;
	

	private LocalDate paymentDate;
	

    @Size(max = 20, message = "Payment status cannot exceed 20 characters")
	private String paymentStatus;
}
