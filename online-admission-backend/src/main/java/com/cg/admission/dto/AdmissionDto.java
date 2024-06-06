package com.cg.admission.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class AdmissionDto {

	private Long admissionId;
	private String emailId;

	private ApplicationDto application;

	private String admissionStatus;

	private CollegeDto college;

	private ProgramDto program;

	private CourseDto course;

	private String year;
}
