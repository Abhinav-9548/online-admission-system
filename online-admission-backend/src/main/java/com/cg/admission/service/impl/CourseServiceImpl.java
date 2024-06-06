package com.cg.admission.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.admission.dto.CourseDto;
import com.cg.admission.entity.Branch;
import com.cg.admission.entity.College;
import com.cg.admission.entity.Course;
import com.cg.admission.entity.Program;
import com.cg.admission.entity.ProgramScheduled;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.repository.CollegeRepository;
import com.cg.admission.repository.CourseRepository;
import com.cg.admission.repository.ProgramRepository;
import com.cg.admission.repository.ProgramScheduledRepository;
import com.cg.admission.service.CourseService;
import com.cg.admission.service.ProgramScheduledService;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private CollegeRepository collegeRepository;
	@Autowired
	private ProgramRepository programRepository;

	@Autowired
	private ProgramScheduledRepository programScheduledRepository;

	@Autowired
	private ProgramScheduledService programScheduledService;

	@Override
	public CourseDto addCourse(Long collegeRegId, Long programId, Course course) {

		CourseDto courseDto = new CourseDto();

		College college = collegeRepository.findById(collegeRegId).get();
		Program program = programRepository.findById(programId).get();

		List<Course> c = program.getCourses();
		for (Course i : c) {
			if (i.getCourseName().equalsIgnoreCase(course.getCourseName())) {
				throw new ResourceNotFoundException("Course already exists in Program");
			}
		}

		program.getCourses().add(course); 

		college.getCourseList().add(course);

		courseRepository.save(course);

		BeanUtils.copyProperties(course, courseDto);

		return courseDto;

	}
	

	@Override
	public List<CourseDto> viewAllCourseDetails() {
		List<Course> courseList = courseRepository.findAll();

		List<CourseDto> courseDtoList = courseList.stream().map(i -> {
			CourseDto courseDto = new CourseDto();

			BeanUtils.copyProperties(i, courseDto);
			return courseDto;
		}).collect(Collectors.toList());

		if (courseDtoList.isEmpty()) {
			throw new ResourceNotFoundException("No Courses found");
		}

		return courseDtoList;

	}

	@Override
	public String deleteCourseById(Long courseId) {

		courseRepository.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("No Course found with Id " + courseId));

		List<ProgramScheduled> list = programScheduledRepository.findByCourse_CourseId(courseId);
		for (ProgramScheduled j : list) {
			programScheduledService.deleteProgramScheduledById(j.getScheduledId());
		}
		courseRepository.deleteById(courseId);
		return "Course Deleted Succesfully";
	}

	@Override
	public Long updateCourseDetails(Long courseId, Course course) {

		Course courseExisted = courseRepository.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("No Course found "));

		courseExisted.setCourseName(course.getCourseName());
		courseExisted.setEligibility(course.getEligibility());
		courseRepository.save(courseExisted);

		return courseId;
	}

	@Override
	public CourseDto findCourseDetailsByCourseName(String courseName) {
		CourseDto courseDto = new CourseDto();
		Course course = courseRepository.findCourseDetailsByCourseName(courseName);
		if (course == null) {
			throw new ResourceNotFoundException("No Course Found with Course Name " + courseName);
		}

		BeanUtils.copyProperties(course, courseDto);
		return courseDto;

	}

	@Override
	public List<CourseDto> findCourseDetailsByEligibility(String eligibility) {
		List<Course> courseListByEligibility = courseRepository.findCourseDetailsByEligibility(eligibility);

		List<CourseDto> courseDtos = courseListByEligibility.stream().map(i -> {
			CourseDto courseDto = new CourseDto();

			BeanUtils.copyProperties(i, courseDto);
			return courseDto;
		}).collect(Collectors.toList());
		if (courseListByEligibility.isEmpty()) {
			throw new ResourceNotFoundException("No Details by" + eligibility + "is present");
		}

		return courseDtos;

	}

	@Override
	@Transactional // It used to method to indicate that a transaction should be created and
					// managed ,DataConsistency,ErrorHandling,performanceoptimization
	public String deleteCourseByCourseName(String courseName) {
		Course deletedCourse = courseRepository.findCourseDetailsByCourseName(courseName);

		if (deletedCourse == null) {
			throw new ResourceNotFoundException("No Course found with Course Name " + courseName);
		}

		List<ProgramScheduled> list = programScheduledRepository.findByCourse_CourseId(deletedCourse.getCourseId());
		for (ProgramScheduled j : list) {
			programScheduledService.deleteProgramScheduledById(j.getScheduledId());
		}
		courseRepository.deleteByCourseName(courseName);

		return "Course Deleted Succesfully";
	}

	@Override
	public CourseDto findCourseDetailsByCourseId(Long courseId) {
		CourseDto courseDto = new CourseDto();
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("No Course Found with Id" + courseId));
		BeanUtils.copyProperties(course, courseDto);

		return courseDto;
	}

	@Override
	public List<Branch> findBranchByCourse(Long courseId) {
	
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("No Course Found with Id" + courseId));
		List<Branch> branchs = course.getBranches();

		return branchs;
	}

	
}