package com.cg.admission.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "program_scheduled")
public class ProgramScheduled {

	@Id
	@SequenceGenerator(name = "programScheduled_", initialValue = 300, allocationSize = 33)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "programScheduled_")
	private Long scheduledId;

	@ManyToOne
	private Branch branch;

	@ManyToOne
	private Course course;

	@ManyToOne
	private Program program;

	@ManyToOne
	private College college;

	@ManyToOne
	private University university;
	
	
	private LocalDate startDate;

	private LocalDate endDate;
}
