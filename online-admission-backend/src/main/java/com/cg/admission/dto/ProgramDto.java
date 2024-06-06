package com.cg.admission.dto;

import java.util.ArrayList;
import java.util.List;

import com.cg.admission.entity.Course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter

@Setter

@AllArgsConstructor

@NoArgsConstructor

public class ProgramDto {
 
	private Long programId;

	private String programName;

	private String eligibility;

	private String duration;

	private String degreeOffered;

	private List<Course> courses = new ArrayList<>();

}