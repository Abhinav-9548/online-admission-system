package com.cg.admission.dto;
 
import com.cg.admission.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UniversityDto {
 
	private Long universityId;
	private String name; 
	private Address address;
 
}