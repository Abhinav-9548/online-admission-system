package com.cg.admission.entity;

import java.util.ArrayList;
import java.util.List;

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
@Table(name = "course")
public class Course {
	
	@Id
	@SequenceGenerator(name = "course_", initialValue = 100, allocationSize = 5)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_")
	private Long courseId;
	
	
	
	@NotBlank(message = "Course name cannot be blank")
    @Size(max = 30, message = "Course name cannot exceed 30 characters")
	private String courseName;
	
	
	
	@NotBlank(message = "Eligibiity cannot be blank")
    @Size(max = 60, message = "Eligibility cannot exceed 60 characters")
	private String eligibility;
	
	
	@NotBlank(message = "Duration cannot be null")
	@Size(max = 20, message = "Duration should not exceed 20 characters")
	private String duration;

	 


	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "courseId")
	private List<Branch> branches = new ArrayList<>();

}
