package com.cg.admission.dto;

import java.util.ArrayList;
import java.util.List;

import com.cg.admission.entity.Branch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
	
	private Long courseId;
	private String courseName;
	private String eligibility;
	private String duration;
	private List<Branch> branches = new ArrayList<>();

}