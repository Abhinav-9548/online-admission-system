package com.cg.admission.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.admission.dto.CollegeDto;
import com.cg.admission.dto.ProgramDto;
import com.cg.admission.entity.Address;
import com.cg.admission.entity.College;
import com.cg.admission.entity.Program;
import com.cg.admission.entity.University;
import com.cg.admission.exceptions.ResourceAlreadyPresentException;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.repository.AddressRepository;
import com.cg.admission.repository.CollegeRepository;
import com.cg.admission.repository.ProgramScheduledRepository;
import com.cg.admission.repository.UniversityRepository;
import com.cg.admission.service.ProgramScheduledService;

@ExtendWith(MockitoExtension.class)
public class CollegeServiceImplTest {

    @Mock
    private CollegeRepository collegeRepository;

    @Mock
    private UniversityRepository universityRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ProgramScheduledRepository programScheduledRepository;

    @Mock
    private ProgramScheduledService programScheduledService;

    @InjectMocks
    private CollegeServiceImpl collegeService;

    private College college;
    private University university;
    private Address address;
    private Program program;

    @BeforeEach
    void setUp() {
        address = new Address();
        address.setAddressId(1L);
        address.setCity("Test City");

        college = new College();
        college.setCollegeRegId(1L);
        college.setCollegeName("Test College");
        college.setAddress(address);

        university = new University();
        university.setUniversityId(1L);
        university.setName("Test University");

        program = new Program();
        program.setProgramId(1L);
        program.setProgramName("Test Program");
    }

    /**
     * Test for adding a college successfully.
     * Verifies that the college is added when all conditions are met.
     */
    @Test
    void testAddCollege_Success() {
        // Mocking repository calls
        when(universityRepository.findById(1L)).thenReturn(Optional.of(university));
        when(collegeRepository.findByCollegeName("Test College")).thenReturn(Collections.emptyList());
        when(addressRepository.save(address)).thenReturn(address);
        when(collegeRepository.save(college)).thenReturn(college);

        // Calling the service method
        CollegeDto result = collegeService.addCollege(1L, college);

        // Assertions
        assertNotNull(result);
        assertEquals("Test College", result.getCollegeName());
        verify(collegeRepository, times(1)).save(college);
    }

    /**
     * Test for attempting to add a college that already exists.
     * Verifies that a ResourceAlreadyPresentException is thrown.
     */
    @Test
    void testAddCollege_AlreadyExists() {
        when(universityRepository.findById(1L)).thenReturn(Optional.of(university));
        when(collegeRepository.findByCollegeName("Test College")).thenReturn(Arrays.asList(college));

        assertThrows(ResourceAlreadyPresentException.class, () -> {
            collegeService.addCollege(1L, college);
        });
    }

    /**
     * Test for viewing all college details successfully.
     * Verifies that a list of all colleges is returned.
     */
    @Test
    void testViewAllCollegeDetails_Success() {
        when(collegeRepository.findAll()).thenReturn(Arrays.asList(college));

        List<CollegeDto> result = collegeService.viewAllCollegeDetails();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(collegeRepository, times(1)).findAll();
    }

    /**
     * Test for viewing all college details when no colleges are found.
     * Verifies that a ResourceNotFoundException is thrown.
     */
    @Test
    void testViewAllCollegeDetails_NotFound() {
        when(collegeRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            collegeService.viewAllCollegeDetails();
        });
    }

    /**
     * Test for getting college details by program name successfully.
     * Verifies that a list of colleges offering the specified program is returned.
     */
    @Test
    void testGetCollegeDetailsByProgram_Success() {
        when(collegeRepository.findByProgramList_ProgramName("Test Program")).thenReturn(Arrays.asList(college));

        List<CollegeDto> result = collegeService.getCollegeDetailsByProgram("Test Program");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(collegeRepository, times(1)).findByProgramList_ProgramName("Test Program");
    }

    /**
     * Test for getting college details by program name when no colleges are found.
     * Verifies that a ResourceNotFoundException is thrown.
     */
    @Test
    void testGetCollegeDetailsByProgram_NotFound() {
        when(collegeRepository.findByProgramList_ProgramName("Test Program")).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            collegeService.getCollegeDetailsByProgram("Test Program");
        });
    }

    /**
     * Test for getting college details by course name successfully.
     * Verifies that a list of colleges offering the specified course is returned.
     */
    @Test
    void testGetCollegeDetailsByCourse_Success() {
        when(collegeRepository.findByCourseList_CourseName("Test Course")).thenReturn(Arrays.asList(college));

        List<CollegeDto> result = collegeService.getCollegeDetailsByCourse("Test Course");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(collegeRepository, times(1)).findByCourseList_CourseName("Test Course");
    }

    /**
     * Test for getting college details by course name when no colleges are found.
     * Verifies that a ResourceNotFoundException is thrown.
     */
    @Test
    void testGetCollegeDetailsByCourse_NotFound() {
        when(collegeRepository.findByCourseList_CourseName("Test Course")).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            collegeService.getCollegeDetailsByCourse("Test Course");
        });
    }

    /**
     * Test for getting college details by branch name successfully.
     * Verifies that a list of colleges offering the specified branch is returned.
     */
    @Test
    void testGetCollegeDetailsByBranch_Success() {
        when(collegeRepository.findByBranchList_BranchName("Test Branch")).thenReturn(Arrays.asList(college));

        List<CollegeDto> result = collegeService.getCollegeDetailsByBranch("Test Branch");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(collegeRepository, times(1)).findByBranchList_BranchName("Test Branch");
    }

    /**
     * Test for getting college details by branch name when no colleges are found.
     * Verifies that a ResourceNotFoundException is thrown.
     */
    @Test
    void testGetCollegeDetailsByBranch_NotFound() {
        when(collegeRepository.findByBranchList_BranchName("Test Branch")).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            collegeService.getCollegeDetailsByBranch("Test Branch");
        });
    }

    /**
     * Test for getting college details by college name successfully.
     * Verifies that a list of colleges with the specified name is returned.
     */
    @Test
    void testGetCollegeDetailsByName_Success() {
        when(collegeRepository.findByCollegeName("Test College")).thenReturn(Arrays.asList(college));

        List<CollegeDto> result = collegeService.getCollegeDetailsByName("Test College");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(collegeRepository, times(1)).findByCollegeName("Test College");
    }

    /**
     * Test for getting college details by college name when no colleges are found.
     * Verifies that a ResourceNotFoundException is thrown.
     */
    @Test
    void testGetCollegeDetailsByName_NotFound() {
        when(collegeRepository.findByCollegeName("Test College")).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            collegeService.getCollegeDetailsByName("Test College");
        });
    }

    /**
     * Test for deleting a college by its ID successfully.
     * Verifies that a college is deleted when it exists and no programs are scheduled with it.
     */
    @Test
    void testDeleteCollegeById_Success() {
        when(collegeRepository.findById(1L)).thenReturn(Optional.of(college));
        when(programScheduledRepository.findByCollege_CollegeRegId(1L)).thenReturn(Collections.emptyList());

        String result = collegeService.deleteCollegeById(1L);

        assertEquals("deleted successfully", result);
        verify(collegeRepository, times(1)).deleteById(1L);
    }

    /**
     * Test for deleting a college by its ID when it is not found.
     * Verifies that a ResourceNotFoundException is thrown.
     */
    @Test
    void testDeleteCollegeById_NotFound() {
        when(collegeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            collegeService.deleteCollegeById(1L);
        });
    }

    /**
     * Test for deleting a college by its name successfully.
     * Verifies that a college is deleted when it exists and no programs are scheduled with it.
     */
    @Test
    void testDeleteCollegeByName_Success() {
        // Mocking the repository to return a list containing the college when searching by college name
        when(collegeRepository.findByCollegeName("Test College")).thenReturn(Arrays.asList(college));

        // Mocking the program scheduled repository to return an empty list when searched by college registration ID
        when(programScheduledRepository.findByCollege_CollegeRegId(1L)).thenReturn(Collections.emptyList());

        // Calling the service method to delete the college by name
        String result = collegeService.deleteCollegeByName("Test College");

        // Asserting that the delete operation was successful
        assertEquals("deleted successfully", result);

        // Verifying that the deleteByCollegeName method was called exactly once with "Test College"
        verify(collegeRepository, times(1)).deleteByCollegeName("Test College");
    }

    @Test
    void testDeleteCollegeByName_NotFound() {
        // Mocking the repository to return empty when searched by college name
        when(collegeRepository.findByCollegeName("Test College")).thenReturn(Collections.emptyList());

        // Asserting that a ResourceNotFoundException is thrown when attempting to delete the college by name
        assertThrows(ResourceNotFoundException.class, () -> {
            collegeService.deleteCollegeByName("Test College");
        });
    }

    @Test
    void testUpdateCollegeDetails_Success() {
        // Mocking the repository to return the college when searched by ID
        when(collegeRepository.findById(1L)).thenReturn(Optional.of(college));

        // Mocking the repository to return the college when saved
        when(collegeRepository.save(college)).thenReturn(college);

        // Creating an updated college object
        College updatedCollege = new College();
        updatedCollege.setCollegeName("Updated College");
        updatedCollege.setDescription("Updated College");

        // Calling the service method to update college details
        Long result = collegeService.updateCollegeDetails(1L, updatedCollege);

        // Asserting that the update operation was successful
        assertEquals(1L, result);

        // Verifying that the save method was called exactly once with the updated college object
        verify(collegeRepository, times(1)).save(college);
    }

    @Test
    void testUpdateCollegeDetails_NotFound() {
        // Mocking the repository to return empty when searched by ID
        when(collegeRepository.findById(1L)).thenReturn(Optional.empty());

        // Creating an updated college object
        College updatedCollege = new College();
        updatedCollege.setCollegeName("Updated College");
        updatedCollege.setDescription("Updated College");

        // Asserting that a ResourceNotFoundException is thrown when attempting to update college details
        assertThrows(ResourceNotFoundException.class, () -> {
            collegeService.updateCollegeDetails(1L, updatedCollege);
        });
    }

    @Test
    void testGetCollegeDetailsById_Success() {
        // Mocking the repository to return the college when searched by ID
        when(collegeRepository.findById(1L)).thenReturn(Optional.of(college));

        // Calling the service method to get college details by ID
        CollegeDto result = collegeService.getCollegeDetailsById(1L);

        // Asserting that the result is not null
        assertNotNull(result);

        // Asserting that the college name in the result matches "Test College"
        assertEquals("Test College", result.getCollegeName());
    }

    @Test
    void testGetCollegeDetailsById_NotFound() {
        // Mocking the repository to return empty when searched by ID
        when(collegeRepository.findById(1L)).thenReturn(Optional.empty());

        // Asserting that a ResourceNotFoundException is thrown when attempting to get college details by ID
        assertThrows(ResourceNotFoundException.class, () -> {
            collegeService.getCollegeDetailsById(1L);
        });
    }

    @Test
    void testGetProgramsByColleges_Success() {
        // Setting up the college entity to have a list of programs
        college.setProgramList(Arrays.asList(program));

        // Mocking the repository to return the college when searched by ID
        when(collegeRepository.findById(1L)).thenReturn(Optional.of(college));

        // Calling the service method to get programs by college ID
        List<ProgramDto> result = collegeService.getProgramsByColleges(1L);

        // Asserting that the result is not empty
        assertFalse(result.isEmpty());

        // Asserting that the number of programs retrieved is 1
        assertEquals(1, result.size());

        // Verifying that the findById method was called exactly once with ID 1L
        verify(collegeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProgramsByColleges_NotFound() {
        // Mocking the repository to return empty when searched by ID
        when(collegeRepository.findById(1L)).thenReturn(Optional.empty());

        // Asserting that a ResourceNotFoundException is thrown when attempting to get programs by college ID
        assertThrows(ResourceNotFoundException.class, () -> {
            collegeService.getProgramsByColleges(1L);
        });
    }
}
