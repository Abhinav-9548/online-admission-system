package com.cg.admission.service;

import java.util.List;

import com.cg.admission.dto.CollegeDto;
import com.cg.admission.dto.UniversityDto;
import com.cg.admission.entity.University;

public interface UniversityService {

	List<UniversityDto> getAllUniversity();

	UniversityDto getUniversityById(Long Id);

	UniversityDto addUniversity(University university);

	List<UniversityDto> getUniversityDetailsByCity(String city);

	List<UniversityDto> getUniversityDetailsByCollegeName(String collegeName);

	Long updateUniversity(Long Id, University university);

	String deleteUniversityById(Long id);

	List<CollegeDto> findCollegeByUniverisity(Long universityId);
}