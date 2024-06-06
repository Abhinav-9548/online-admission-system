package com.cg.admission.service;

import java.util.List;

import com.cg.admission.dto.CourseDto;
import com.cg.admission.dto.ProgramDto;
import com.cg.admission.entity.Program;

public interface ProgramService {

	ProgramDto addProgram(Long id, Program program);

	List<ProgramDto> viewAllProgramDetails();

	String deleteProgramById(Long programId);

	ProgramDto findProgramById(Long programId);

	List<ProgramDto> findProgramDetailsByProgramName(String programName);

	List<ProgramDto> findProgramDetailsByEligibility(String eligibility);

	String deleteProgramByProgramName(String programName);

	Long updateProgramStatus(Long programId, Program program);

	List<CourseDto> findCoursesByProgram(Long programId);

}