
package com.cg.admission.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.admission.dto.ProgramScheduledDto;
import com.cg.admission.dto.ProgramScheduledResponseDto;
import com.cg.admission.entity.Branch;
import com.cg.admission.entity.College;
import com.cg.admission.entity.Course;
import com.cg.admission.entity.Program;
import com.cg.admission.entity.ProgramScheduled;
import com.cg.admission.entity.University;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.repository.ApplicationRepository;
import com.cg.admission.repository.BranchRepository;
import com.cg.admission.repository.CollegeRepository;
import com.cg.admission.repository.CourseRepository;
import com.cg.admission.repository.ProgramRepository;
import com.cg.admission.repository.ProgramScheduledRepository;
import com.cg.admission.repository.UniversityRepository;

@ExtendWith(MockitoExtension.class)

public class ProgramScheduledServiceImplTest {

	@Mock

	private ProgramScheduledRepository programScheduledRepository;

	@Mock

	private UniversityRepository universityRepository;

	@Mock

	private CollegeRepository collegeRepository;

	@Mock

	private ProgramRepository programRepository;

	@Mock

	private CourseRepository courseRepository;

	@Mock

	private BranchRepository branchRepository;

	@Mock

	private ApplicationRepository applicationRepository;

	@Mock

	private ApplicationServiceImpl applicationServiceImpl;

	@InjectMocks

	private ProgramScheduledServiceImpl programScheduledServiceImpl;

	private ProgramScheduled programScheduled;

	private ProgramScheduledDto programScheduledDto;

	private ProgramScheduledResponseDto programScheduledResponseDto;

	private University university;

	private College college;

	private Program program;

	private Course course;

	private Branch branch;

	@BeforeEach

	void setUp() {

		programScheduled = new ProgramScheduled();

		programScheduled.setScheduledId(1L);

		programScheduled.setStartDate(LocalDate.now());

		programScheduled.setEndDate(LocalDate.now().plusDays(10));

		university = new University();

		university.setUniversityId(1L);

		college = new College();

		college.setCollegeRegId(1L);

		program = new Program();

		program.setProgramId(1L);

		course = new Course();

		course.setCourseId(1L);

		branch = new Branch();

		branch.setBranchId(1L);

		programScheduled.setUniversity(university);

		programScheduled.setCollege(college);

		programScheduled.setProgram(program);

		programScheduled.setCourse(course);

		programScheduled.setBranch(branch);

		programScheduledDto = new ProgramScheduledDto();

		programScheduledResponseDto = new ProgramScheduledResponseDto();

	}

	@Test

	void testViewAllProgramScheduledDetails() {

		when(programScheduledRepository.findAll()).thenReturn(Collections.singletonList(programScheduled));

		List<ProgramScheduledDto> result = programScheduledServiceImpl.viewAllProgramScheduledDetails();

		assertFalse(result.isEmpty());

		verify(programScheduledRepository, times(1)).findAll();

	}

	@Test

	void testDeleteProgramScheduledById() {

		when(programScheduledRepository.findById(1L)).thenReturn(Optional.of(programScheduled));

		when(applicationRepository.findBySchedule_scheduledId(1L)).thenReturn(Collections.emptyList());

		String result = programScheduledServiceImpl.deleteProgramScheduledById(1L);

		assertEquals("Program Scheduled deleted Succesfully", result);

		verify(programScheduledRepository, times(1)).findById(1L);

		verify(programScheduledRepository, times(1)).deleteById(1L);

	}

	@Test

	void testDeleteProgramScheduledById_NotFound() {

		when(programScheduledRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {

			programScheduledServiceImpl.deleteProgramScheduledById(1L);

		});

		verify(programScheduledRepository, times(1)).findById(1L);

	}

	@Test

	void testUpdateProgramScheduled() {

		when(programScheduledRepository.findById(1L)).thenReturn(Optional.of(programScheduled));

		programScheduled.setStartDate(LocalDate.now().plusDays(1));

		programScheduled.setEndDate(LocalDate.now().plusDays(11));

		when(programScheduledRepository.save(any(ProgramScheduled.class))).thenReturn(programScheduled);

		ProgramScheduledDto result = programScheduledServiceImpl.updateProgramScheduled(1L, programScheduled);

		assertNotNull(result);

		verify(programScheduledRepository, times(1)).findById(1L);

		verify(programScheduledRepository, times(1)).save(any(ProgramScheduled.class));

	}

	@Test

	void testAddProgramSchedule() {

		when(universityRepository.findById(1L)).thenReturn(Optional.of(university));

		when(collegeRepository.findById(1L)).thenReturn(Optional.of(college));

		when(programRepository.findById(1L)).thenReturn(Optional.of(program));

		when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

		when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));

		when(programScheduledRepository.save(any(ProgramScheduled.class))).thenReturn(programScheduled);

		ProgramScheduledDto result = programScheduledServiceImpl.addProgramSchedule(1L, 1L, 1L, 1L, 1L,
				programScheduled);

		assertNotNull(result);

		verify(programScheduledRepository, times(1)).save(any(ProgramScheduled.class));

	}

	@Test

	void testAddProgramSchedule_NotFound() {

		when(universityRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {

			programScheduledServiceImpl.addProgramSchedule(1L, 1L, 1L, 1L, 1L, programScheduled);

		});

		verify(universityRepository, times(1)).findById(1L);

	}

	@Test

	void testFindProgramScheduledById() {

		when(programScheduledRepository.findById(1L)).thenReturn(Optional.of(programScheduled));

		ProgramScheduledDto result = programScheduledServiceImpl.findProgramScheduledById(1L);

		assertNotNull(result);

		verify(programScheduledRepository, times(1)).findById(1L);

	}

	@Test

	void testFindProgramScheduledById_NotFound() {

		when(programScheduledRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {

			programScheduledServiceImpl.findProgramScheduledById(1L);

		});

		verify(programScheduledRepository, times(1)).findById(1L);

	}

	@Test

	void testFindProgramScheduledByCollege_CollegeName() {

		when(programScheduledRepository.findProgramScheduledByCollege_CollegeName(anyString()))

				.thenReturn(Collections.singletonList(programScheduled));

		List<ProgramScheduledDto> result = programScheduledServiceImpl
				.findProgramScheduledByCollege_CollegeName("Test College");

		assertFalse(result.isEmpty());

		verify(programScheduledRepository, times(1)).findProgramScheduledByCollege_CollegeName(anyString());

	}

	@Test

	void testFindProgramScheduledByCollege_CollegeName_NotFound() {

		when(programScheduledRepository.findProgramScheduledByCollege_CollegeName(anyString()))

				.thenReturn(Collections.emptyList());

		assertThrows(ResourceNotFoundException.class, () -> {

			programScheduledServiceImpl.findProgramScheduledByCollege_CollegeName("Test College");

		});

		verify(programScheduledRepository, times(1)).findProgramScheduledByCollege_CollegeName(anyString());

	}

	@Test

	void testFindByCollege_CollegeRegId() {

		when(programScheduledRepository.findByCollege_CollegeRegId(1L))
				.thenReturn(Collections.singletonList(programScheduled));

		List<ProgramScheduledDto> result = programScheduledServiceImpl.findByCollege_CollegeRegId(1L);

		assertFalse(result.isEmpty());

		verify(programScheduledRepository, times(1)).findByCollege_CollegeRegId(1L);

	}

	@Test

	void testFindByCollege_CollegeRegId_NotFound() {

		when(programScheduledRepository.findByCollege_CollegeRegId(1L)).thenReturn(Collections.emptyList());

		assertThrows(ResourceNotFoundException.class, () -> {

			programScheduledServiceImpl.findByCollege_CollegeRegId(1L);

		});

		verify(programScheduledRepository, times(1)).findByCollege_CollegeRegId(1L);

	}

	@Test

	void testFindByBranch_BranchId() {

		when(programScheduledRepository.findByBranch_BranchId(1L))
				.thenReturn(Collections.singletonList(programScheduled));

		List<ProgramScheduledDto> result = programScheduledServiceImpl.findByBranch_BranchId(1L);

		assertFalse(result.isEmpty());

		verify(programScheduledRepository, times(1)).findByBranch_BranchId(1L);

	}

	@Test

	void testFindByBranch_BranchId_NotFound() {

		when(programScheduledRepository.findByBranch_BranchId(1L)).thenReturn(Collections.emptyList());

		assertThrows(ResourceNotFoundException.class, () -> {

			programScheduledServiceImpl.findByBranch_BranchId(1L);

		});

		verify(programScheduledRepository, times(1)).findByBranch_BranchId(1L);

	}

	@Test

	void testFindByProgram_ProgramId() {

		when(programScheduledRepository.findByProgram_ProgramId(1L))
				.thenReturn(Collections.singletonList(programScheduled));

		List<ProgramScheduledDto> result = programScheduledServiceImpl.findByProgram_ProgramId(1L);

		assertFalse(result.isEmpty());

		verify(programScheduledRepository, times(1)).findByProgram_ProgramId(1L);

	}

	@Test

	void testFindByProgram_ProgramId_NotFound() {

		when(programScheduledRepository.findByProgram_ProgramId(1L)).thenReturn(Collections.emptyList());

		assertThrows(ResourceNotFoundException.class, () -> {

			programScheduledServiceImpl.findByProgram_ProgramId(1L);

		});

		verify(programScheduledRepository, times(1)).findByProgram_ProgramId(1L);

	}

	@Test

	void testFindByCourse_CourseId() {

		when(programScheduledRepository.findByCourse_CourseId(1L))
				.thenReturn(Collections.singletonList(programScheduled));

		List<ProgramScheduledDto> result = programScheduledServiceImpl.findByCourse_CourseId(1L);

		assertFalse(result.isEmpty());

		verify(programScheduledRepository, times(1)).findByCourse_CourseId(1L);

	}

	@Test

	void testFindByCourse_CourseId_NotFound() {

		when(programScheduledRepository.findByCourse_CourseId(1L)).thenReturn(Collections.emptyList());

		assertThrows(ResourceNotFoundException.class, () -> {

			programScheduledServiceImpl.findByCourse_CourseId(1L);

		});

		verify(programScheduledRepository, times(1)).findByCourse_CourseId(1L);

	}

	@Test

	void testFindByUniversity_UniversityId() {

		when(programScheduledRepository.findByUniversity_UniversityId(1L))
				.thenReturn(Collections.singletonList(programScheduled));

		List<ProgramScheduledDto> result = programScheduledServiceImpl.findByUniversity_UniversityId(1L);

		assertFalse(result.isEmpty());

		verify(programScheduledRepository, times(1)).findByUniversity_UniversityId(1L);

	}

	@Test

	void testFindByUniversity_UniversityId_NotFound() {

		when(programScheduledRepository.findByUniversity_UniversityId(1L)).thenReturn(Collections.emptyList());

		assertThrows(ResourceNotFoundException.class, () -> {

			programScheduledServiceImpl.findByUniversity_UniversityId(1L);

		});

		verify(programScheduledRepository, times(1)).findByUniversity_UniversityId(1L);

	}

}
