package com.cg.admission.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.admission.entity.Branch;
import com.cg.admission.entity.College;
import com.cg.admission.entity.Course;
import com.cg.admission.exceptions.ResourceAlreadyPresentException;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.repository.BranchRepository;
import com.cg.admission.repository.CollegeRepository;
import com.cg.admission.repository.CourseRepository;
import com.cg.admission.repository.ProgramScheduledRepository;
import com.cg.admission.service.ProgramScheduledService;

/**
 * Unit tests for BranchServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
public class BranchServiceImplTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private CollegeRepository collegeRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ProgramScheduledRepository programScheduledRepository;

    @Mock
    private ProgramScheduledService programScheduledService;

    @InjectMocks
    private BranchServiceImpl branchService;

    private Branch branch;
    private College college;
    private Course course;

    /**
     * Set up test data before each test method.
     */
    @BeforeEach
    void setUp() {
        branch = new Branch();
        branch.setBranchId(1L);
        branch.setBranchName("Computer Science");
        branch.setBranchDescription("CS branch");

        college = new College();
        college.setCollegeRegId(1L);
        college.setCollegeName("Test College");

        course = new Course();
        course.setCourseId(1L);
        course.setCourseName("B.Tech");
    }

    /**
     * Test for adding a branch successfully.
     */
    @Test
    void testAddBranch_Success() {
        // Mocking repository calls and saving branch
        when(collegeRepository.findById(1L)).thenReturn(Optional.of(college));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(branchRepository.save(branch)).thenReturn(branch);

        // Calling service method to add a branch
        Branch result = branchService.addBranch(1L, 1L, branch);

        // Assertions
        assertNotNull(result);
        assertEquals("Computer Science", result.getBranchName());
        verify(branchRepository, times(1)).save(branch);
    }

    /**
     * Test for adding a branch that already exists.
     */
    @Test
    void testAddBranch_AlreadyExists() {
        // Mocking repository calls and setting up existing branch
        when(collegeRepository.findById(1L)).thenReturn(Optional.of(college));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        course.setBranches(Arrays.asList(branch));

        // Asserting that ResourceAlreadyPresentException is thrown
        assertThrows(ResourceAlreadyPresentException.class, () -> {
            branchService.addBranch(1L, 1L, branch);
        });
    }

    /**
     * Test for viewing all branch details successfully.
     */
    @Test
    void testViewAllBranchDetails_Success() {
        // Mocking repository call to return list of branches
        when(branchRepository.findAll()).thenReturn(Arrays.asList(branch));

        // Calling service method to view all branch details
        List<Branch> result = branchService.viewAllBranchDetails();

        // Assertions
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(branchRepository, times(1)).findAll();
    }

    /**
     * Test for viewing all branch details when no branches are found.
     */
    @Test
    void testViewAllBranchDetails_NotFound() {
        // Mocking repository call to return empty list
        when(branchRepository.findAll()).thenReturn(Arrays.asList());

        // Asserting that ResourceNotFoundException is thrown
        assertThrows(ResourceNotFoundException.class, () -> {
            branchService.viewAllBranchDetails();
        });
    }

    /**
     * Test for getting branch details by name successfully.
     */
    @Test
    void testGetBranchDetailsByName_Success() {
        // Mocking repository call to return list of branches by name
        when(branchRepository.findByBranchName("Computer Science")).thenReturn(Arrays.asList(branch));

        // Calling service method to get branches by name
        List<Branch> result = branchService.getBranchDetailsByName("Computer Science");

        // Assertions
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(branchRepository, times(1)).findByBranchName("Computer Science");
    }

    /**
     * Test for getting branch details by name when no branches are found.
     */
    @Test
    void testGetBranchDetailsByName_NotFound() {
        // Mocking repository call to return empty list
        when(branchRepository.findByBranchName("Computer Science")).thenReturn(Arrays.asList());

        // Asserting that ResourceNotFoundException is thrown
        assertThrows(ResourceNotFoundException.class, () -> {
            branchService.getBranchDetailsByName("Computer Science");
        });
    }

    /**
     * Test for deleting a branch by ID successfully.
     */
    @Test
    void testDeleteBranchById_Success() {
        // Mocking repository calls and setting up for deletion
        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));
        when(programScheduledRepository.findByBranch_BranchId(1L)).thenReturn(Arrays.asList());

        // Calling service method to delete branch by ID
        String result = branchService.deleteBranchById(1L);

        // Assertions
        assertEquals("deleted successfully", result);
        verify(branchRepository, times(1)).deleteById(1L);
    }

    /**
     * Test for deleting a branch by ID when branch is not found.
     */
    @Test
    void testDeleteBranchById_NotFound() {
        // Mocking repository call to return empty
        when(branchRepository.findById(1L)).thenReturn(Optional.empty());

        // Asserting that ResourceNotFoundException is thrown
        assertThrows(ResourceNotFoundException.class, () -> {
            branchService.deleteBranchById(1L);
        });
    }

    /**
     * Test for getting a branch by ID successfully.
     */
    @Test
    void testGetBranchById_Success() {
        // Mocking repository call to return branch by ID
        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));

        // Calling service method to get branch by ID
        Branch result = branchService.getBranchById(1L);

        // Assertions
        assertNotNull(result);
        assertEquals("Computer Science", result.getBranchName());
    }

    /**
     * Test for getting a branch by ID when branch is not found.
     */
    @Test
    void testGetBranchById_NotFound() {
        // Mocking repository call to return empty
        when(branchRepository.findById(1L)).thenReturn(Optional.empty());

        // Asserting that ResourceNotFoundException is thrown
        assertThrows(ResourceNotFoundException.class, () -> {
            branchService.getBranchById(1L);
        });
    }

    /**
     * Test for deleting a branch by name successfully.
     */
    @Test
    void testDeleteBranchByName_Success() {
        // Mocking repository calls and setting up for deletion
        when(branchRepository.findByBranchName("Computer Science")).thenReturn(Arrays.asList(branch));
        when(programScheduledRepository.findByBranch_BranchId(1L)).thenReturn(Arrays.asList());

        // Calling service method to delete branch by name
        String result = branchService.deleteBranchByName("Computer Science");

        // Assertions
        assertEquals("Deleted successfully", result);
        verify(branchRepository, times(1)).deleteByBranchName("Computer Science");
    }

    /**
     * Test for deleting a branch by name when branch is not found.
     */
    @Test
    void testDeleteBranchByName_NotFound() {
        // Mocking repository call to return empty list
        when(branchRepository.findByBranchName("Computer Science")).thenReturn(Arrays.asList());

        // Asserting that ResourceNotFoundException is thrown
        assertThrows(ResourceNotFoundException.class, () -> {
            branchService.deleteBranchByName("Computer Science");
        });
    }

    /**
     * Test for updating a branch successfully.
     */
    @Test
    void testUpdateBranch_Success() {
        // Mocking repositories and setting up for update
        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));
        when(branchRepository.save(branch)).thenReturn(branch);

        // Creating an updated branch object
        Branch updatedBranch = new Branch();
        updatedBranch.setBranchName("Electronics");
        updatedBranch.setBranchDescription("ECE branch");

        // Calling service method to update branch
        Long result = branchService.updateBranch(1L, updatedBranch);

        // Assertions
        assertEquals(1L, result);
        verify(branchRepository, times(1)).save(branch);
    }

    /**
     * Test for updating a branch when branch is not found.
     */
    @Test
    void testUpdateBranch_NotFound() {
        // Mocking repository call to return empty
        when(branchRepository.findById(1L)).thenReturn(Optional.empty());

        // Creating an updated branch object
        Branch updatedBranch = new Branch();
        updatedBranch.setBranchName("Electronics");
        updatedBranch.setBranchDescription("ECE branch");

        // Asserting that ResourceNotFoundException is thrown
        assertThrows(ResourceNotFoundException.class, () -> {
            branchService.updateBranch(1L, updatedBranch);
        });
    }
}
