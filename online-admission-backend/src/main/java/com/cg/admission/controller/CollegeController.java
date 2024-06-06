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

import com.cg.admission.dto.CollegeDto;
import com.cg.admission.dto.ProgramDto;
import com.cg.admission.entity.College;
import com.cg.admission.service.impl.CollegeServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CollegeController {
    
    // Autowiring the CollegeServiceImpl to use its methods for handling college-related operations
	@Autowired
	private CollegeServiceImpl collegeServiceImpl; 
 
    // Endpoint to get details of all colleges
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/colleges") 
	public ResponseEntity<List<CollegeDto>> viewAllCollegeDetails() {
        // Returns a list of all college details with HTTP status OK
		return new ResponseEntity<List<CollegeDto>>(collegeServiceImpl.viewAllCollegeDetails(), HttpStatus.OK);
	}
 
    // Endpoint to get college details by program name
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/colleges/program/{programName}")
	public ResponseEntity<List<CollegeDto>> getCollegeDetailsByProgram(@PathVariable String programName) {
        // Returns a list of college details for the given program name with HTTP status OK
		return new ResponseEntity<List<CollegeDto>>(collegeServiceImpl.getCollegeDetailsByProgram(programName),
				HttpStatus.OK);
	}
 
    // Endpoint to get college details by course name
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/colleges/course/{courseName}")
	public ResponseEntity<List<CollegeDto>> getCollegeDetailsByCourse(@PathVariable String courseName) {
        // Returns a list of college details for the given course name with HTTP status OK
		return new ResponseEntity<List<CollegeDto>>(collegeServiceImpl.getCollegeDetailsByCourse(courseName),
				HttpStatus.OK);
	}
 
    // Endpoint to get college details by branch name
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/colleges/branchname/{branchName}")
	public ResponseEntity<List<CollegeDto>> getCollegeDetailsByBranch(@PathVariable String branchName) {
        // Returns a list of college details for the given branch name with HTTP status OK
		return new ResponseEntity<List<CollegeDto>>(collegeServiceImpl.getCollegeDetailsByBranch(branchName),
				HttpStatus.OK);
	}
 
    // Endpoint to get college details by college name
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/colleges/collegename/{collegeName}")
	public ResponseEntity<List<CollegeDto>> getCollegeDetailsByName(@PathVariable String collegeName) {
        // Returns a list of college details for the given college name with HTTP status OK
		return new ResponseEntity<List<CollegeDto>>(collegeServiceImpl.getCollegeDetailsByName(collegeName),
				HttpStatus.OK);
	}
 
    // Endpoint to get college details by college ID
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/college/collegeid/{collegeId}")
	public ResponseEntity<CollegeDto> getCollegeDetailsById(@PathVariable Long collegeId) {
        // Returns college details for the given college ID with HTTP status OK
		return new ResponseEntity<CollegeDto>(collegeServiceImpl.getCollegeDetailsById(collegeId), HttpStatus.OK);
	}
 
    // Endpoint to add a new college, linked to a specific university
//	/	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/university/{universityId}/college")
	public ResponseEntity<CollegeDto> addCollege(@PathVariable Long universityId, @Valid @RequestBody College college) {
        // Adds a new college and links it to the specified university, returns the created college with HTTP status CREATED
		return new ResponseEntity<CollegeDto>(collegeServiceImpl.addCollege(universityId, college), HttpStatus.CREATED);
	}
 
    // Endpoint to update college details by college ID
//	/	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updatecollege/{collegeId}")
	public ResponseEntity<Long> updateCollegeDetails(@PathVariable Long collegeId, @Valid @RequestBody College college) {
        // Updates college details for the given college ID, returns the updated college ID with HTTP status CREATED
		return new ResponseEntity<Long>(collegeServiceImpl.updateCollegeDetails(collegeId, college),
				HttpStatus.CREATED);
	}
 
    // Endpoint to delete a college by its ID
//	/	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deletecollege/collegeid/{collegeId}")
	public ResponseEntity<String> deleteCollegeById(@PathVariable Long collegeId) {
        // Deletes the college with the given ID, returns a confirmation message with HTTP status OK
		return new ResponseEntity<String>(collegeServiceImpl.deleteCollegeById(collegeId), HttpStatus.OK);
	}
 
    // Endpoint to delete a college by its name
//	/	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deletecollege/collegename/{collegeName}")
	public ResponseEntity<String> deleteCollegeByName(@PathVariable String collegeName) {
        // Deletes the college with the given name, returns a confirmation message with HTTP status OK
		return new ResponseEntity<String>(collegeServiceImpl.deleteCollegeByName(collegeName), HttpStatus.OK);
	}
	
    // Endpoint to get programs by college ID
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/{collegeId}/programs")
	public ResponseEntity<List<ProgramDto>> getProgramsByColleges(@PathVariable Long collegeId){
        // Returns a list of programs for the given college ID with HTTP status OK
		return new ResponseEntity<List<ProgramDto>>(collegeServiceImpl.getProgramsByColleges(collegeId),HttpStatus.OK);
	}
	
    // Endpoint to find colleges by search term
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/colleges-all/{searchTerm}")
	public ResponseEntity<List<CollegeDto>> findBySearchTerms(@PathVariable String searchTerm){
        // Returns a list of colleges that match the search term with HTTP status OK
		return new ResponseEntity<>(collegeServiceImpl.findBySearchTerms(searchTerm),HttpStatus.OK); 
	}
	
} 
