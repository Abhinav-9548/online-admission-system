package com.cg.admission.controller;

import com.cg.admission.entity.Branch;
import com.cg.admission.service.impl.BranchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BranchControllerTest {

    @Mock
    private BranchServiceImpl branchService;

    @InjectMocks
    private BranchController branchController;

    private Branch branch;

    @BeforeEach
    public void setUp() {
        // Initialize mock data
        branch = new Branch();
        branch.setBranchId(1L);
        branch.setBranchName("Computer Science");
        branch.setBranchDescription("Computer Science branch");
    }

    @Test
    public void testViewAllBranchDetails() {
        // Mock the service method
        List<Branch> branches = new ArrayList<>();
        branches.add(branch);
        when(branchService.viewAllBranchDetails()).thenReturn(branches);

        // Call the controller method
        ResponseEntity<List<Branch>> response = branchController.viewAllBranchDetails();

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(branch, response.getBody().get(0));
    }

    @Test
    public void testGetBranchDetailsByName() {
        String branchName = "Computer Science";

        // Mock the service method
        List<Branch> branches = new ArrayList<>();
        branches.add(branch);
        when(branchService.getBranchDetailsByName(branchName)).thenReturn(branches);

        // Call the controller method
        ResponseEntity<List<Branch>> response = branchController.getBranchDetailsByName(branchName);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(branch, response.getBody().get(0));
    }

    @Test
    public void testGetBranchById() {
        Long branchId = 1L;

        // Mock the service method
        when(branchService.getBranchById(branchId)).thenReturn(branch);

        // Call the controller method
        ResponseEntity<Branch> response = branchController.getBranchById(branchId);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(branch, response.getBody());
    }

    @Test
    public void testAddBranch() {
        Long collegeId = 1L;
        Long courseId = 2L;

        // Mock the service method
        when(branchService.addBranch(collegeId, courseId, branch)).thenReturn(branch);

        // Call the controller method
        ResponseEntity<Branch> response = branchController.addBranch(courseId, collegeId, branch);

        // Verify the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(branch, response.getBody());
    }

    @Test
    public void testUpdateBranch() {
        Long branchId = 1L;

        // Mock the service method
        when(branchService.updateBranch(branchId, branch)).thenReturn(branchId);

        // Call the controller method
        ResponseEntity<Long> response = branchController.updateBranch(branchId, branch);

        // Verify the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(branchId, response.getBody());
    }

    @Test
    public void testDeleteBranchById() {
        Long branchId = 1L;

        // Mock the service method
        when(branchService.deleteBranchById(branchId)).thenReturn("Deleted");

        // Call the controller method
        ResponseEntity<String> response = branchController.deleteBranchById(branchId);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted", response.getBody());
    }

    @Test
    public void testDeleteBranchByName() {
        String branchName = "Computer Science";

        // Mock the service method
        when(branchService.deleteBranchByName(branchName)).thenReturn("Deleted");

        // Call the controller method
        ResponseEntity<String> response = branchController.deleteBranchByName(branchName);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted", response.getBody());
    }
}
