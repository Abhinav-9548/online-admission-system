package com.cg.admission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.admission.entity.Admission;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {

	String deleteByApplication_ApplicationId(Long applicationId);

	String deleteByCollege_collegeRegId(Long collegeId);

	String deleteByProgram_programId(Long programId);

	String deleteByCourse_courseId(Long courseId);

	Admission findByEmailId(String emailId);

	Admission findByApplication_ApplicationId(Long applicationId);

}
