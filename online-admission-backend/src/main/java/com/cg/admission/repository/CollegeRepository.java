package com.cg.admission.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.admission.entity.College;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {

	List<College> findByProgramList_ProgramName(String programName);

	List<College> findByCourseList_CourseName(String courseName);

	List<College> findByBranchList_BranchName(String branchName);

	List<College> findByCollegeName(String collegeName);

	Long deleteByCollegeName(String collegeName);

	List<College> findByCollegeNameLikeOrProgramList_ProgramNameLikeOrUniversity_NameLikeOrBranchList_BranchNameLikeOrCourseList_CourseNameLike(
			String collegeName, String programName, String universityName, String branchName, String courseName);

}