package com.cg.admission.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
import com.cg.admission.service.ProgramScheduledService;

public class CourseServiceImplTest {

	@Mock

	private CourseRepository courseRepository;

	@Mock

	private CollegeRepository collegeRepository;

	@Mock

	private ProgramRepository programRepository;

	@Mock

	private ProgramScheduledRepository programScheduledRepository;

	@Mock

	private ProgramScheduledService programScheduledService;

	@InjectMocks

	private CourseServiceImpl courseServiceImpl;

	@BeforeEach

	void setUp() {

		MockitoAnnotations.openMocks(this);

	}

	@Test

	void testAddCourse_Success() {

		Long collegeRegId = 1L;

		Long programId = 1L;

		Course course = new Course();

		course.setCourseName("Test Course");

		College college = new College();

		Program program = new Program();

		program.setCourses(new ArrayList<>());

		when(collegeRepository.findById(collegeRegId)).thenReturn(Optional.of(college));

		when(programRepository.findById(programId)).thenReturn(Optional.of(program));

		when(courseRepository.save(course)).thenReturn(course);

		CourseDto result = courseServiceImpl.addCourse(collegeRegId, programId, course);

		assertNotNull(result);

		verify(collegeRepository, times(1)).findById(collegeRegId);

		verify(programRepository, times(1)).findById(programId);

		verify(courseRepository, times(1)).save(course);

	}

	@Test

	void testAddCourse_AlreadyExists() {

		Long collegeRegId = 1L;

		Long programId = 1L;

		Course course = new Course();

		course.setCourseName("Test Course");

		College college = new College();

		Program program = new Program();

		program.setCourses(List.of(course));

		when(collegeRepository.findById(collegeRegId)).thenReturn(Optional.of(college));

		when(programRepository.findById(programId)).thenReturn(Optional.of(program));

		assertThrows(ResourceNotFoundException.class, () -> {

			courseServiceImpl.addCourse(collegeRegId, programId, course);

		});

		verify(collegeRepository, times(1)).findById(collegeRegId);

		verify(programRepository, times(1)).findById(programId);

		verify(courseRepository, times(0)).save(course);

	}

	@Test

	void testViewAllCourseDetails_Success() {

		List<Course> courseList = new ArrayList<>();

		Course course = new Course();

		courseList.add(course);

		when(courseRepository.findAll()).thenReturn(courseList);

		List<CourseDto> result = courseServiceImpl.viewAllCourseDetails();

		assertNotNull(result);

		assertEquals(1, result.size());

		verify(courseRepository, times(1)).findAll();

	}

	@Test

	void testViewAllCourseDetails_NoCoursesFound() {

		when(courseRepository.findAll()).thenReturn(new ArrayList<>());

		assertThrows(ResourceNotFoundException.class, () -> {

			courseServiceImpl.viewAllCourseDetails();

		});

		verify(courseRepository, times(1)).findAll();

	}

	@Test

	void testDeleteCourseById_Success() {

		Long courseId = 1L;

		Course course = new Course();

		List<ProgramScheduled> programScheduledList = new ArrayList<>();

		when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

		when(programScheduledRepository.findByCourse_CourseId(courseId)).thenReturn(programScheduledList);

		String result = courseServiceImpl.deleteCourseById(courseId);

		assertEquals("Course Deleted Succesfully", result);

		verify(courseRepository, times(1)).findById(courseId);

		verify(programScheduledRepository, times(1)).findByCourse_CourseId(courseId);

		verify(courseRepository, times(1)).deleteById(courseId);

	}

	@Test

	void testDeleteCourseById_NoCourseFound() {

		Long courseId = 1L;

		when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {

			courseServiceImpl.deleteCourseById(courseId);

		});

		verify(courseRepository, times(1)).findById(courseId);

		verify(courseRepository, times(0)).deleteById(courseId);

	}

	@Test

	void testUpdateCourseDetails_Success() {

		Long courseId = 1L;

		Course updatedCourse = new Course();

		updatedCourse.setCourseName("Updated Course");

		updatedCourse.setEligibility("New Eligibility");

		Course existingCourse = new Course();

		existingCourse.setCourseId(courseId);

		when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));

		when(courseRepository.save(existingCourse)).thenReturn(existingCourse);

		Long result = courseServiceImpl.updateCourseDetails(courseId, updatedCourse);

		assertEquals(courseId, result);

		assertEquals("Updated Course", existingCourse.getCourseName());

		assertEquals("New Eligibility", existingCourse.getEligibility());

		verify(courseRepository, times(1)).findById(courseId);

		verify(courseRepository, times(1)).save(existingCourse);

	}

	@Test

	void testUpdateCourseDetails_NoCourseFound() {

		Long courseId = 1L;

		Course course = new Course();

		when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {

			courseServiceImpl.updateCourseDetails(courseId, course);

		});

		verify(courseRepository, times(1)).findById(courseId);

		verify(courseRepository, times(0)).save(any(Course.class));

	}

	@Test

	void testFindCourseDetailsByCourseName_Success() {

		String courseName = "Test Course";

		Course course = new Course();

		course.setCourseName(courseName);

		when(courseRepository.findCourseDetailsByCourseName(courseName)).thenReturn(course);

		CourseDto result = courseServiceImpl.findCourseDetailsByCourseName(courseName);

		assertNotNull(result);

		verify(courseRepository, times(1)).findCourseDetailsByCourseName(courseName);

	}

	@Test

	void testFindCourseDetailsByCourseName_NoCourseFound() {

		String courseName = "Non-Existent Course";

		when(courseRepository.findCourseDetailsByCourseName(courseName)).thenReturn(null);

		assertThrows(ResourceNotFoundException.class, () -> {

			courseServiceImpl.findCourseDetailsByCourseName(courseName);

		});

		verify(courseRepository, times(1)).findCourseDetailsByCourseName(courseName);

	}

	@Test

	void testFindCourseDetailsByEligibility_Success() {

		String eligibility = "Eligibility Criteria";

		List<Course> courseList = new ArrayList<>();

		Course course = new Course();

		course.setEligibility(eligibility);

		courseList.add(course);

		when(courseRepository.findCourseDetailsByEligibility(eligibility)).thenReturn(courseList);

		List<CourseDto> result = courseServiceImpl.findCourseDetailsByEligibility(eligibility);

		assertNotNull(result);

		assertEquals(1, result.size());

		verify(courseRepository, times(1)).findCourseDetailsByEligibility(eligibility);

	}

	@Test

	void testFindCourseDetailsByEligibility_NoCoursesFound() {

		String eligibility = "Non-Existent Eligibility";

		when(courseRepository.findCourseDetailsByEligibility(eligibility)).thenReturn(new ArrayList<>());

		assertThrows(ResourceNotFoundException.class, () -> {

			courseServiceImpl.findCourseDetailsByEligibility(eligibility);

		});

		verify(courseRepository, times(1)).findCourseDetailsByEligibility(eligibility);

	}

	@Test

	void testDeleteCourseByCourseName_Success() {

		String courseName = "Test Course";

		Course course = new Course();

		List<ProgramScheduled> programScheduledList = new ArrayList<>();

		when(courseRepository.findCourseDetailsByCourseName(courseName)).thenReturn(course);

		when(programScheduledRepository.findByCourse_CourseId(course.getCourseId())).thenReturn(programScheduledList);

		String result = courseServiceImpl.deleteCourseByCourseName(courseName);

		assertEquals("Course Deleted Succesfully", result);

		verify(courseRepository, times(1)).findCourseDetailsByCourseName(courseName);

		verify(programScheduledRepository, times(1)).findByCourse_CourseId(course.getCourseId());

		verify(courseRepository, times(1)).deleteByCourseName(courseName);

	}

	@Test

	void testDeleteCourseByCourseName_NoCourseFound() {

		String courseName = "Non-Existent Course";

		when(courseRepository.findCourseDetailsByCourseName(courseName)).thenReturn(null);

		assertThrows(ResourceNotFoundException.class, () -> {

			courseServiceImpl.deleteCourseByCourseName(courseName);

		});

		verify(courseRepository, times(1)).findCourseDetailsByCourseName(courseName);

	}

	@Test

	void testFindCourseDetailsByCourseId_Success() {

		Long courseId = 1L;

		Course course = new Course();

		course.setCourseId(courseId);

		when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

		CourseDto result = courseServiceImpl.findCourseDetailsByCourseId(courseId);

		assertNotNull(result);

		verify(courseRepository, times(1)).findById(courseId);

	}

	@Test

	void testFindBranchByCourse_Success() {

		Long courseId = 1L;

		Course course = new Course();

		List<Branch> branchList = new ArrayList<>();

		course.setBranches(branchList);

		when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

		List<Branch> result = courseServiceImpl.findBranchByCourse(courseId);

		assertNotNull(result);

		assertEquals(branchList, result);

		verify(courseRepository, times(1)).findById(courseId);

	}

}
