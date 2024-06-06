package com.cg.admission.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "application")
public class Application {
 
	@Id
	@SequenceGenerator(name = "application_", initialValue = 1001, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_")
	private Long applicationId;
	
	
	@NotBlank(message = "Name cannot be null")
	@Size(max=40, message = "Name should not exceed 30 characters")
	private String applicantFullName;
	
	

	private LocalDate dateOfBirth;
	
	
	@NotBlank(message = "Qualification cannot be null")
	@Size(max=20, message = "Qualification should not exceed 20 characters")
	private String highestQualification;
	
	
	@NotNull(message = "Percentage cannot be null")
//	@Pattern(regexp = "^[0-9]{2}[%]$", message = "Invalid percentage format")
	private double finalYearPercentage;
	
	
	private String goals;
	
	
	@NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
	private String emailId;

	@NotBlank(message = "Status cannot be null")
	@Size(max=20, message = "Status should be exceed 20 characters")
	private String applicationStatus;
	
	

	private LocalDate dateOfInterview;
	
	

	@Size(max=100, message = "Feedback should not exceed 100 characters")
	private String applicantInterviewFeedback;
	
	@ManyToOne
	@JoinColumn(name = "scheduledId") 
	private ProgramScheduled schedule;
	@OneToOne
	private Document document;
}
