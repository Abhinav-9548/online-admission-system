package com.cg.admission.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "university") 
public class University {

	@Id
	@SequenceGenerator(name = "university_", initialValue = 6630, allocationSize = 10)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "university_")
	private Long universityId;
	
	@NotBlank(message = "University name cannot be blank")
	@Size(max = 50, message = "University name cannot exceed 50 characters")
	private String name;

	@OneToOne
	private Address address;

	@OneToMany(mappedBy = "university",cascade = CascadeType.ALL)
	@JsonIgnoreProperties("university")
	private List<College> collegeList = new ArrayList<>();
}
