package com.cg.admission.service;
import java.util.List;

import com.cg.admission.dto.CourseDto;
import com.cg.admission.entity.Branch;
import com.cg.admission.entity.Course;
 
public interface CourseService {
 
	 CourseDto addCourse(Long collegeRegId,Long programId,Course course);
 
	 List<CourseDto> viewAllCourseDetails();
 
	 String deleteCourseById(Long courseId);
 
	 Long updateCourseDetails(Long courseId, Course course);
 
	 CourseDto findCourseDetailsByCourseName(String courseName);
 
	 CourseDto findCourseDetailsByCourseId(Long courseId);
 
	 List<CourseDto> findCourseDetailsByEligibility(String eligibility);
 
	 String deleteCourseByCourseName(String courseName);
	
	 List<Branch> findBranchByCourse(Long courseId);
 
}