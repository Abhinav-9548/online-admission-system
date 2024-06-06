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

import com.cg.admission.dto.AdmissionDto;
import com.cg.admission.entity.Admission;
import com.cg.admission.service.AdmissionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admission")
@CrossOrigin("*")
public class AdmissionController {

	@Autowired
	private AdmissionService admissionService; // Service to handle Admission related operations

	// Endpoint to view all admissions
	@GetMapping()
//	/	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<AdmissionDto>> viewAllAdmission() {
		return new ResponseEntity<>(admissionService.viewAllAdmission(), HttpStatus.OK); // Return list of all
																							// admissions
	}

//	/	@PreAuthorize("hasRole('usER')")
	// Endpoint to add a new admission
	@PostMapping("/college/{collegeId}/program/{programId}/course/{courseId}/application/{applicationId}")
	public ResponseEntity<AdmissionDto> addAdmission(@PathVariable Long collegeId, @Valid @PathVariable Long programId,
			@PathVariable Long courseId, @PathVariable Long applicationId, @RequestBody Admission admission) {
		return new ResponseEntity<AdmissionDto>(
				admissionService.addAdmission(applicationId, collegeId, programId, courseId, admission), HttpStatus.OK); // Add
																															// and
																															// return
																															// the
																															// new
																															// admission
	}

//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	// Endpoint to get admission by ID
	@GetMapping("/{admissionId}")
	public ResponseEntity<AdmissionDto> getById(@PathVariable Long admissionId) {
		return new ResponseEntity<AdmissionDto>(admissionService.getById(admissionId), HttpStatus.OK); // Return
																										// admission by
																										// ID
	}

//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	// Endpoint to find admission by application ID
	@GetMapping("/application/{applicationId}")
	public ResponseEntity<AdmissionDto> findByApplication_ApplicationId(@PathVariable Long applicationId) {
		return new ResponseEntity<AdmissionDto>(admissionService.findByApplication_ApplicationId(applicationId),
				HttpStatus.OK); // Return admission by application ID
	}

//	/	@PreAuthorize("hasRole('ADMIN')")
	// Endpoint to delete admission by ID
	@DeleteMapping("/{admissionId}")
	public ResponseEntity<String> deleteById(@PathVariable Long admissionId) {
		return new ResponseEntity<String>(admissionService.deleteById(admissionId), HttpStatus.OK); // Delete and return
																									// confirmation
																									// message
	}

//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	// Endpoint to update admission by ID
	@PutMapping("/{admissionId}")
	public ResponseEntity<Long> updateAdmission(@PathVariable Long admissionId,
			@Valid @RequestBody Admission admission) {
		return new ResponseEntity<Long>(admissionService.updateAdmission(admissionId, admission), HttpStatus.OK); // Update
																													// and
																													// return
																													// admission
																													// ID
	}

}
