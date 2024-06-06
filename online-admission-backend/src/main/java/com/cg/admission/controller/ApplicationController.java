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

import com.cg.admission.dto.ApplicationDto;
import com.cg.admission.entity.Application;
import com.cg.admission.service.ApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/application")
@CrossOrigin("*")
public class ApplicationController {
 
	@Autowired
	ApplicationService applicationService; // Service to handle Application related operations

	// Endpoint to view all application details
//	/	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/all")
	public ResponseEntity<List<ApplicationDto>> viewAllApplicationDetails() {
		return new ResponseEntity<List<ApplicationDto>>(applicationService.viewAllApplicationDetails(), HttpStatus.OK); // Return list of all application details
	}

	// Endpoint to get application details by email
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/email/{email}")
	public ResponseEntity<List<ApplicationDto>> getApplicationDetailsByEmail(@PathVariable String email) {
		return new ResponseEntity<List<ApplicationDto>>(applicationService.getApplicationDetailsByEmail(email), HttpStatus.OK); // Return application details by email
	}

	// Endpoint to add a new application
//	/	@PreAuthorize("hasRole('usER')")
	@PostMapping("/programschedule/{id}/add")
	public ResponseEntity<ApplicationDto> addApplication(@PathVariable Long id,@Valid @RequestBody Application application) {
		return new ResponseEntity<ApplicationDto>(applicationService.addApplication(id, application), HttpStatus.CREATED); // Add and return the new application
	} 

	// Endpoint to get application details by status
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/status/{status}")
	public ResponseEntity<List<ApplicationDto>> getApplicationDetailsByStatus(@PathVariable String status) {
		return new ResponseEntity<List<ApplicationDto>>(applicationService.getApplicationDetailsByStatus(status), HttpStatus.OK); // Return application details by status
	}

	// Endpoint to delete an application by ID
//	/	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteApplicationByid(@PathVariable Long id) {
		return new ResponseEntity<String>(applicationService.deleteApplicationByid(id), HttpStatus.OK); // Delete and return confirmation message
	}

	// Endpoint to delete an application by email
//	/	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/del/{emailId}")
	public ResponseEntity<String> deleteApplicationByEmail(@PathVariable String emailId) {
		return new ResponseEntity<String>(applicationService.deleteApplicationByEmail(emailId), HttpStatus.OK); // Delete and return confirmation message
	}

	// Endpoint to get an application by ID
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/{id}")
	public ResponseEntity<ApplicationDto> getApplicationByid(@PathVariable Long id) {
		return new ResponseEntity<ApplicationDto>(applicationService.getApplicationByid(id), HttpStatus.OK); // Return application by ID
	}

	// Endpoint to update application status
//	/	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Long> updateApplicationStatus(@PathVariable Long id, @Valid @RequestBody Application application) {
		return new ResponseEntity<Long>(applicationService.updateApplicationStatus(id, application), HttpStatus.OK); // Update and return application status
	}

}
