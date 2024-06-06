package com.cg.admission.service;

import java.util.List;

import com.cg.admission.dto.CollegeDto;
import com.cg.admission.dto.ProgramDto;
import com.cg.admission.entity.College;

public interface CollegeService {

	CollegeDto addCollege(Long unversityId, College college);

	List<CollegeDto> viewAllCollegeDetails();

	List<CollegeDto> getCollegeDetailsByProgram(String programName);

	List<CollegeDto> getCollegeDetailsByCourse(String courseName);

	List<CollegeDto> getCollegeDetailsByBranch(String branchName);

	List<CollegeDto> getCollegeDetailsByName(String collegeName);

	String deleteCollegeById(Long collegeId);

	String deleteCollegeByName(String collegeName);

	Long updateCollegeDetails(Long collegeId, College college);

	CollegeDto getCollegeDetailsById(Long collegeId);

	List<ProgramDto> getProgramsByColleges(Long collegeId);

	List<CollegeDto> findBySearchTerms(String searchTerm);

}