package com.cg.admission.service;

import java.time.LocalDate;
import java.util.List;

import com.cg.admission.dto.ProgramScheduledDto;
import com.cg.admission.entity.ProgramScheduled;

public interface ProgramScheduledService {

	ProgramScheduledDto addProgramSchedule(Long universityId, Long collegeId, Long programId, Long courseId,
			Long branchId, ProgramScheduled programScheduled);

	List<ProgramScheduledDto> viewAllProgramScheduledDetails();

	ProgramScheduledDto findProgramScheduledById(Long scheduledId);

	String deleteProgramScheduledById(Long scheduledId);

	ProgramScheduledDto updateProgramScheduled(Long scheduledId, ProgramScheduled programScheduled);

	List<ProgramScheduledDto> findProgramScheduledByCollege_CollegeName(String collegeName);

	List<ProgramScheduledDto> findProgramScheduledByStartDate(LocalDate startDate);

	List<ProgramScheduledDto> findByCollege_CollegeRegId(Long collegeRegId);

	List<ProgramScheduledDto> findByBranch_BranchId(Long branchId);

	List<ProgramScheduledDto> findByProgram_ProgramId(Long programId);

	List<ProgramScheduledDto> findByCourse_CourseId(Long courseId);

	List<ProgramScheduledDto> findByUniversity_UniversityId(Long universityId);
}