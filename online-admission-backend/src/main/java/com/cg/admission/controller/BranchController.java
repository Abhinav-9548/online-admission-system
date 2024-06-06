package com.cg.admission.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.admission.entity.Branch;
import com.cg.admission.service.impl.BranchServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BranchController {
    
    // Autowiring the BranchServiceImpl to use its methods for handling branch-related operations
	@Autowired
	private BranchServiceImpl branchServiceImpl;
   
    // Endpoint to get details of all branches
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/branches")
	public ResponseEntity<List<Branch>> viewAllBranchDetails() {
        // Returns a list of all branch details with HTTP status OK
		return new ResponseEntity<List<Branch>>(branchServiceImpl.viewAllBranchDetails(), HttpStatus.OK);
	}
 
    // Endpoint to get branch details by branch name
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/branches/branchname/{branchName}")
	public ResponseEntity<List<Branch>> getBranchDetailsByName(@PathVariable String branchName) {
        // Returns a list of branch details for the given branch name with HTTP status OK
		return new ResponseEntity<List<Branch>>(branchServiceImpl.getBranchDetailsByName(branchName), HttpStatus.OK);
	}
 
    // Endpoint to get branch details by branch ID
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/branch/branchid/{branchId}")
	public ResponseEntity<Branch> getBranchById(@PathVariable Long branchId) {
        // Returns branch details for the given branch ID with HTTP status OK
		return new ResponseEntity<Branch>(branchServiceImpl.getBranchById(branchId), HttpStatus.OK);
	}
 
    // Endpoint to add a new branch, linked to a specific college and course
//	/	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/college/{collegeId}/course/{courseId}/branch")
	public ResponseEntity<Branch> addBranch(@PathVariable Long collegeId, @PathVariable Long courseId, @Valid @RequestBody Branch branch) {
        // Adds a new branch and links it to the specified college and course, returns the created branch with HTTP status CREATED
		return new ResponseEntity<Branch>(branchServiceImpl.addBranch(collegeId, courseId, branch), HttpStatus.CREATED);
	}
 
    // Endpoint to update branch details by branch ID
//	/	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateBranch/{branchId}")
	public ResponseEntity<Long> updateBranch(@PathVariable Long branchId, @Valid @RequestBody Branch branch) {
        // Updates branch details for the given branch ID, returns the updated branch ID with HTTP status CREATED
		return new ResponseEntity<Long>(branchServiceImpl.updateBranch(branchId, branch), HttpStatus.CREATED);
	}
 
    // Endpoint to delete a branch by its ID
//	/	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteBranch/byId/{branchId}")
	public ResponseEntity<String> deleteBranchById(@PathVariable Long branchId) {
        // Deletes the branch with the given ID, returns a confirmation message with HTTP status OK
		return new ResponseEntity<String>(branchServiceImpl.deleteBranchById(branchId), HttpStatus.OK);
	}
 
    // Endpoint to delete a branch by its name
//	/	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteBranch/byName/{branchName}")
	public ResponseEntity<String> deleteBranchByName(@PathVariable String branchName) {
        // Deletes the branch with the given name, returns a confirmation message with HTTP status OK
		return new ResponseEntity<String>(branchServiceImpl.deleteBranchByName(branchName), HttpStatus.OK);
	}
}
