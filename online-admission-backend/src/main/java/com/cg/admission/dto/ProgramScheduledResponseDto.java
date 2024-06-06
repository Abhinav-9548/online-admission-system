package com.cg.admission.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor 

@NoArgsConstructor

public class ProgramScheduledResponseDto {


	private Long branchId;
	private String branchName;
	

	private Long courseId;
	private String courseName;
	private String eligibility;

	private Long programId;
	private String programName;
	private String duration;
	private String degreeOffered;
	private Long collegeRegId;
	private String collegeName;
	private Long universityId;
	private String name;

}
