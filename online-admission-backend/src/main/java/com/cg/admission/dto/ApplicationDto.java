package com.cg.admission.dto;

import java.time.LocalDate;

import com.cg.admission.entity.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class ApplicationDto {

	private Long applicationId;
	private String applicantFullName;
	private LocalDate dateOfBirth;
	private String highestQualification;
	private double finalYearPercentage;
	private String goals;
	private String emailId;
	private ProgramScheduledDto schedule;
	private String applicationStatus;
	private LocalDate dateOfInterview;
	private String applicantInterviewFeedback;
	private Document document;
}
