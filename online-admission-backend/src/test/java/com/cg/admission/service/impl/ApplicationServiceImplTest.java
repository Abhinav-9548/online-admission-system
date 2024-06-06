package com.cg.admission.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.admission.dto.ApplicationDto;
import com.cg.admission.dto.ProgramScheduledDto;
import com.cg.admission.dto.ProgramScheduledResponseDto;
import com.cg.admission.entity.Application;
import com.cg.admission.entity.Branch;
import com.cg.admission.entity.College;
import com.cg.admission.entity.Course;
import com.cg.admission.entity.Payment;
import com.cg.admission.entity.Program;
import com.cg.admission.entity.ProgramScheduled;
import com.cg.admission.entity.University;
import com.cg.admission.exceptions.ResourceAlreadyPresentException;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.repository.AdmissionRepository;
import com.cg.admission.repository.ApplicationRepository;
import com.cg.admission.repository.PaymentRepository;
import com.cg.admission.repository.ProgramScheduledRepository;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceImplTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private ProgramScheduledRepository programScheduledRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private AdmissionRepository admissionRepository;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    private Application application;
    private ProgramScheduled programScheduled;
    private ApplicationDto applicationDto;
    private ProgramScheduledDto programScheduledDto;
    private ProgramScheduledResponseDto programScheduledResponseDto;

    @BeforeEach
    void setUp() {
        // Mock Branch
        Branch branch = new Branch();
        branch.setBranchId(1L);
        branch.setBranchName("Computer Science");
        branch.setBranchDescription("Computer Science Branch");

        // Mock Course
        Course course = new Course();
        course.setCourseId(1L);
        course.setCourseName("B.Tech");
        course.setEligibility("Eligibility Criteria");

        // Mock College
        College college = new College();
        college.setCollegeRegId(1L);
        college.setCollegeName("ABC College");

        // Mock University
        University university = new University();
        university.setUniversityId(1L);
        university.setName("XYZ University");

        // Mock Program
        Program program = new Program();
        program.setProgramId(1L);
        program.setProgramName("Engineering");
        program.setCourses(List.of(course));

        // Mock ProgramScheduled
        programScheduled = new ProgramScheduled();
        programScheduled.setScheduledId(1L);
        programScheduled.setBranch(branch);
        programScheduled.setCourse(course);
        programScheduled.setProgram(program);
        programScheduled.setCollege(college);
        programScheduled.setUniversity(university);
        programScheduled.setStartDate(LocalDate.of(2024, 6, 1));
        programScheduled.setEndDate(LocalDate.of(2024, 12, 1));

        // Mock Application
        application = new Application();
        application.setApplicationId(1L);
        application.setEmailId("test@example.com");
        application.setApplicationStatus("Pending");
        application.setSchedule(programScheduled);

        // Mock DTOs
        programScheduledResponseDto = new ProgramScheduledResponseDto();
        programScheduledResponseDto.setBranchId(branch.getBranchId());
        programScheduledResponseDto.setBranchName(branch.getBranchName());
        programScheduledResponseDto.setCourseId(course.getCourseId());
        programScheduledResponseDto.setCourseName(course.getCourseName());
        programScheduledResponseDto.setEligibility(course.getEligibility());
        programScheduledResponseDto.setProgramId(program.getProgramId());
        programScheduledResponseDto.setProgramName(program.getProgramName());
        programScheduledResponseDto.setCollegeRegId(college.getCollegeRegId());
        programScheduledResponseDto.setCollegeName(college.getCollegeName());
        programScheduledResponseDto.setUniversityId(university.getUniversityId());
        programScheduledResponseDto.setName(university.getName());

        programScheduledDto = new ProgramScheduledDto();
        programScheduledDto.setScheduledId(programScheduled.getScheduledId());
        programScheduledDto.setProgramScheduledResponseDto(programScheduledResponseDto);
        programScheduledDto.setStartDate(programScheduled.getStartDate());
        programScheduledDto.setEndDate(programScheduled.getEndDate());

        applicationDto = new ApplicationDto();
        applicationDto.setApplicationId(1L);
        applicationDto.setEmailId("test@example.com");
        applicationDto.setSchedule(programScheduledDto);
    }

    @Test
    void testViewAllApplicationDetails() {
        List<Application> applications = new ArrayList<>();
        applications.add(application);

        when(applicationRepository.findAll()).thenReturn(applications);

        List<ApplicationDto> result = applicationService.viewAllApplicationDetails();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size()); 
        assertEquals(applicationDto.getEmailId(), result.get(0).getEmailId());
        // Verify that the properties were copied correctly
        assertEquals(application.getApplicationId(), result.get(0).getApplicationId());
        assertEquals(application.getSchedule(), result.get(0).getSchedule().getProgramScheduledResponseDto());
    }

    @Test
    void testViewAllApplicationDetails_Empty() {
        when(applicationRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> {
            applicationService.viewAllApplicationDetails();
        });
    }

    @Test
    void testGetApplicationDetailsByEmail() {
        List<Application> applications = new ArrayList<>();
        applications.add(application);

        when(applicationRepository.findByEmailId(anyString())).thenReturn(applications);

        List<ApplicationDto> result = applicationService.getApplicationDetailsByEmail("test@example.com");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(applicationDto.getEmailId(), result.get(0).getEmailId());
    }

    @Test
    void testGetApplicationDetailsByEmail_NotFound() {
        when(applicationRepository.findByEmailId(anyString())).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> {
            applicationService.getApplicationDetailsByEmail("test@example.com");
        });
    }

    @Test
    void testAddApplication() {
        when(programScheduledRepository.findById(anyLong())).thenReturn(Optional.of(programScheduled));
        when(applicationRepository.save(any(Application.class))).thenReturn(application);
        when(applicationRepository.findByEmailId(anyString())).thenReturn(new ArrayList<>());

        ApplicationDto result = applicationService.addApplication(1L, application);

        assertNotNull(result);
        assertEquals(applicationDto.getEmailId(), result.getEmailId());
        // Verify that the properties were copied correctly
        assertEquals(application.getApplicationId(), result.getApplicationId());
        assertEquals(application.getSchedule(), result.getSchedule().getProgramScheduledResponseDto());
    }
 
    @Test
    void testAddApplication_AlreadyExists() {
        List<Application> applications = new ArrayList<>();
        applications.add(application);

        when(applicationRepository.findByEmailId(anyString())).thenReturn(applications);

        assertThrows(ResourceAlreadyPresentException.class, () -> {
            applicationService.addApplication(1L, application);
        });
    }

    @Test
    void testGetApplicationDetailsByStatus() {
        List<Application> applications = new ArrayList<>();
        applications.add(application);

        when(applicationRepository.findByApplicationStatus(anyString())).thenReturn(applications);

        List<ApplicationDto> result = applicationService.getApplicationDetailsByStatus("Pending");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(applicationDto.getEmailId(), result.get(0).getEmailId());
        // Verify that the properties were copied correctly
        assertEquals(application.getApplicationId(), result.get(0).getApplicationId());
        assertEquals(application.getSchedule(), result.get(0).getSchedule().getProgramScheduledResponseDto());
    }

    @Test
    void testGetApplicationDetailsByStatus_NotFound() {
        when(applicationRepository.findByApplicationStatus(anyString())).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> {
            applicationService.getApplicationDetailsByStatus("Pending");
        });
    }

    @Test
    void testDeleteApplicationByid() {
        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(application));
        when(paymentRepository.deletePaymentByApplication_ApplicationId(anyLong())).thenReturn("Deleted");

        String result = applicationService.deleteApplicationByid(1L);

        assertEquals("Deleted", result);
    }

    @Test
    void testDeleteApplicationByid_NotFound() {
        when(applicationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            applicationService.deleteApplicationByid(1L);
        });
    }

    @Test
    void testDeleteApplicationByEmail() {
        List<Application> applications = new ArrayList<>();
        applications.add(application);

        when(applicationRepository.findByEmailId(anyString())).thenReturn(applications);
        when(paymentRepository.findPaymentByApplication_ApplicationId(anyLong())).thenReturn(new Payment());
        doNothing().when(paymentRepository).delete(any(Payment.class));

        String result = applicationService.deleteApplicationByEmail("test@example.com");

        assertEquals("Application Deleted Successfully", result);
    }

    @Test
    void testDeleteApplicationByEmail_NotFound() {
        when(applicationRepository.findByEmailId(anyString())).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> {
            applicationService.deleteApplicationByEmail("test@example.com");
        });
    }

 

    @Test
    void testGetApplicationByid() {
        // Mock the repository's response
        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(application));

        // Call the service method
        ApplicationDto result = applicationService.getApplicationByid(1L);

        // Assert that the result is not null and properties are copied correctly
        assertNotNull(result);
        assertEquals(applicationDto.getEmailId(), result.getEmailId());
        assertEquals(application.getApplicationId(), result.getApplicationId());
        assertEquals(application.getSchedule(), result.getSchedule().getProgramScheduledResponseDto());
    }

    @Test
    void testGetApplicationByid_NotFound() {
        // Mock the repository's response for a non-existing application
        when(applicationRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Assert that calling the service method throws a ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> {
            applicationService.getApplicationByid(1L);
        });
    }

    @Test
    void testUpdateApplicationStatus() {
        // Mock the repository's response for an existing application
        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(application));
        // Mock the repository's response after saving the application
        when(applicationRepository.save(any(Application.class))).thenReturn(application);

        // Call the service method
        Long result = applicationService.updateApplicationStatus(1L, application);

        // Assert that the result is not null and equals the application's ID
        assertNotNull(result);
        assertEquals(application.getApplicationId(), result);
    }

    @Test
    void testUpdateApplicationStatus_NotFound() {
        // Mock the repository's response for a non-existing application
        when(applicationRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Assert that calling the service method throws a ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> {
            applicationService.updateApplicationStatus(1L, application);
        });
    }
}