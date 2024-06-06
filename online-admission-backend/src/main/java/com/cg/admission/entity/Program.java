package com.cg.admission.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "program")
public class Program {

	@Id
	@SequenceGenerator(name = "program_", initialValue = 1100, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "program_")
	private Long programId;

	@NotBlank(message = "Program name cannot be blank")
	@Size(max = 30, message = "Program name should not exceed 30 characters")
	private String programName;
 
	@NotBlank(message = "Eligibility cannot be null")
	@Size(max = 20, message = "Eligibility should not exceed 20 characters")
	private String eligibility;

	@NotBlank(message = "Duration cannot be null")
	@Size(max = 20, message = "Duration should not exceed 20 characters")
	private String duration;
	private String degreeOffered;

	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore //////////////////// changed
	@JoinColumn(name = "programId")
	private List<Course> courses = new ArrayList<>();

}
