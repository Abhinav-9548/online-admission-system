package com.cg.admission.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "admission")
public class Admission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long admissionId; 
	
	
	@NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
	private String emailId;

	@OneToOne 
	private Application application;

	
	@NotBlank(message = "Admission status cannot be blank")
    @Size(max = 20, message = "Admission status cannot exceed 20 characters")
	private String admissionStatus;

	@ManyToOne
	private College college;

	@ManyToOne
	private Program program;

	@ManyToOne
	private Course course;

	@NotBlank(message = "Year cannot be blank")
	@Pattern(regexp = "^(?:19|20)\\d{2}$", message = "Year is not in correct format YYYY")
    @Size(max = 4, message = "Year cannot exceed 4 Numbers")
	private String year;
}
