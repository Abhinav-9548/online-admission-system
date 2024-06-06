package com.cg.admission.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import com.cg.admission.dto.AdmissionDto;
import com.cg.admission.entity.Admission;
import com.cg.admission.entity.Application;
import com.cg.admission.entity.College;
import com.cg.admission.entity.Course;
import com.cg.admission.entity.Program;
import com.cg.admission.repository.AdmissionRepository;
import com.cg.admission.repository.ApplicationRepository;
import com.cg.admission.repository.CollegeRepository;
import com.cg.admission.repository.CourseRepository;
import com.cg.admission.repository.ProgramRepository;

@ExtendWith(MockitoExtension.class)
public class AdmissionServiceImplTest {

	@Mock
	private AdmissionRepository admissionRepository;

	@Mock
	private ApplicationRepository applicationRepository;

	@Mock
	private CollegeRepository collegeRepository;

	@Mock
	private ProgramRepository programRepository;

	@Mock
	private CourseRepository courseRepository;

	@InjectMocks
	private AdmissionServiceImpl admissionService;

	private Admission admission;
	private AdmissionDto admissionDto;
	private List<Admission> admissionList;

	@BeforeEach
	public void setUp() {
		// Initialize mock data
		admission = new Admission();
		admission.setAdmissionId(1L);
		admission.setEmailId("test@example.com");
		admission.setAdmissionStatus("Pending");
		admission.setYear("2024");

		Application application = new Application();
		application.setApplicationId(1L);
		admission.setApplication(application);

		College college = new College();
		college.setCollegeRegId(1L);
		admission.setCollege(college);

		Program program = new Program();
		program.setProgramId(1L);
		admission.setProgram(program);

		Course course = new Course();
		course.setCourseId(1L);
		admission.setCourse(course);

		admissionDto = new AdmissionDto();
		BeanUtils.copyProperties(admission, admissionDto);

		admissionList = new ArrayList<>();
		admissionList.add(admission);
	}

	@Test
	public void testViewAllAdmission() {
		// Mock the repository method
		when(admissionRepository.findAll()).thenReturn(admissionList);

		// Call the service method
		List<AdmissionDto> result = admissionService.viewAllAdmission();

		// Verify the response
		List<AdmissionDto> expected = admissionList.stream().map(i -> {
			AdmissionDto dto = new AdmissionDto();
			BeanUtils.copyProperties(i, dto);
			return dto;
		}).collect(Collectors.toList());

		assertEquals(expected.size(), result.size());
		assertEquals(expected.get(0).getAdmissionId(), result.get(0).getAdmissionId());
	}

	@Test
	public void testAddAdmission() {
		Long collegeId = 1L;
		Long programId = 2L;
		Long courseId = 3L;
		Long applicationId = 4L;

		Application application = new Application();
		application.setApplicationId(applicationId);

		College college = new College();
		college.setCollegeRegId(collegeId);

		Program program = new Program();
		program.setProgramId(programId);

		Course course = new Course();
		course.setCourseId(courseId);

		// Mock the repository methods
		when(applicationRepository.findById(applicationId)).thenReturn(Optional.of(application));
		when(collegeRepository.findById(collegeId)).thenReturn(Optional.of(college));
		when(programRepository.findById(programId)).thenReturn(Optional.of(program));
		when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
		when(admissionRepository.save(admission)).thenReturn(admission);

		// Call the service method
		AdmissionDto result = admissionService.addAdmission(applicationId, collegeId, programId, courseId, admission);

		// Verify the response
		assertEquals(admissionDto.getAdmissionId(), result.getAdmissionId());
	}

	@Test
	public void testGetById() {
		Long admissionId = 1L;

		// Mock the repository method
		when(admissionRepository.findById(admissionId)).thenReturn(Optional.of(admission));

		// Call the service method
		AdmissionDto result = admissionService.getById(admissionId);

		// Verify the response
		assertEquals(admissionDto.getAdmissionId(), result.getAdmissionId());
	}

	@Test
	public void testDeleteById() {
		Long admissionId = 1L;

		// Mock the repository method
		when(admissionRepository.findById(admissionId)).thenReturn(Optional.of(admission));

		// Call the service method
		String result = admissionService.deleteById(admissionId);

		// Verify the response
		assertEquals("Admission Deleted SuccessFully", result);
		verify(admissionRepository, times(1)).deleteById(admissionId);
	}

	@Test
	public void testUpdateAdmission() {
		Long admissionId = 1L;
		Admission updatedAdmission = new Admission();
		updatedAdmission.setAdmissionStatus("Approved");

		// Mock the repository method
		when(admissionRepository.findById(admissionId)).thenReturn(Optional.of(admission));
		when(admissionRepository.save(admission)).thenReturn(admission);

		// Call the service method
		Long result = admissionService.updateAdmission(admissionId, updatedAdmission);

		// Verify the response
		assertEquals(admissionId, result);
		verify(admissionRepository, times(1)).save(admission);
	}

	@Test
	public void testFindByApplication_ApplicationId() {
		Long applicationId = 1L;

		// Mock the repository method
		when(admissionRepository.findByApplication_ApplicationId(applicationId)).thenReturn(admission);

		// Call the service method
		AdmissionDto result = admissionService.findByApplication_ApplicationId(applicationId);

		// Verify the response
		assertEquals(admissionDto.getAdmissionId(), result.getAdmissionId());
	}
}
