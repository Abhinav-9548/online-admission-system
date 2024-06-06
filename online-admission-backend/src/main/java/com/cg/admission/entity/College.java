package com.cg.admission.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "college")
public class College {

	@Id 
	@SequenceGenerator(name = "college_", initialValue = 2400, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "college_")
	private Long collegeRegId;

	@NotBlank(message = "College name cannot be blank")
    @Size(max = 30, message = "College name cannot exceed 30 characters")
	private String collegeName;
	
	@NotBlank(message = "College description cannot be blank")
    @Size(max = 250, message = "College description cannot exceed 250 characters")
	private String description;

	@OneToOne 
	private Address address;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "collegeRegId")
	private List<Program> programList = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "collegeRegId")
	private List<Course> courseList = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "collegeRegId")
	private List<Branch> branchList = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "universityId")
	@JsonIgnoreProperties("collegeList")
	private University university;

}
