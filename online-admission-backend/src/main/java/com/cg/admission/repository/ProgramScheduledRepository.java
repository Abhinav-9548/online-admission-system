package com.cg.admission.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.admission.entity.ProgramScheduled;

@Repository

public interface ProgramScheduledRepository extends JpaRepository<ProgramScheduled, Long> {

	List<ProgramScheduled> findProgramScheduledByCollege_CollegeName(String collegeName);

	List<ProgramScheduled> findProgramScheduledByStartDate(LocalDate startDate);

	List<ProgramScheduled> findByCollege_CollegeRegId(Long collegeRegId);

	List<ProgramScheduled> findByBranch_BranchId(Long branchId);

	List<ProgramScheduled> findByProgram_ProgramId(Long programId);

	List<ProgramScheduled> findByCourse_CourseId(Long courseId);

	List<ProgramScheduled> findByUniversity_UniversityId(Long universityId);

	String deleteByCollege_CollegeRegId(Long applicationId);

	String deleteByBranch_BranchId(Long branchId);

	String deleteByprogram_ProgramId(Long programId);

	String deleteByCourse_courseId(Long courseId);
}