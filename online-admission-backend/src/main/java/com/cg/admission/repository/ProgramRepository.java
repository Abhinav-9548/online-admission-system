package com.cg.admission.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.admission.entity.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
	// List<Program> findApplicationDetailsByCollege_CollegeName(String
	// collegeName);

	List<Program> findByProgramName(String programName);

	List<Program> findByEligibility(String eligibility);

	String deleteByProgramName(String programName);

}