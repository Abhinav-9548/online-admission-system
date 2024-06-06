package com.cg.admission.dto;

import com.cg.admission.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollegeDto { 

	private Long collegeRegId;
	private String collegeName;
	private String description;
	private Address address;

}
