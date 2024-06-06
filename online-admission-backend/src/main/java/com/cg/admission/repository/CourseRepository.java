package com.cg.admission.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.admission.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

	 Course findCourseDetailsByCourseName(String courseName);

//	public List<Course> findCourseDetailsByCollege_CollegeName(String collegeName);

	 List<Course> findCourseDetailsByEligibility(String eligibility);

	 String deleteByCourseName(String courseName);

}