package com.cg.admission.controller;
import java.time.LocalDate;
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
 
import com.cg.admission.dto.ProgramScheduledDto;
import com.cg.admission.entity.ProgramScheduled;
import com.cg.admission.service.ProgramScheduledService;
 
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
 
public class ProgramScheduledController {
 
	@Autowired
 
	private ProgramScheduledService programScheduledService;
 
	
	

//	/	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/programscheduled/{universityId}/{collegeId}/{programId}/{courseId}/{branchId}")
 
	public ResponseEntity<ProgramScheduledDto> addProgramSchedule(@PathVariable Long universityId,
			@PathVariable Long collegeId, @PathVariable Long programId, @PathVariable Long courseId,
			@PathVariable Long branchId, @RequestBody ProgramScheduled programScheduled) {
		/// test this after mapping
		return new ResponseEntity<>(programScheduledService.addProgramSchedule(universityId, collegeId, programId,
				courseId, branchId, programScheduled), HttpStatus.CREATED);
 
		// done
 
	}
 
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/allprogramscheduled")
 
	public ResponseEntity<List<ProgramScheduledDto>> viewAllProgramScheduledDetails() {
 
		return new ResponseEntity<List<ProgramScheduledDto>>(programScheduledService.viewAllProgramScheduledDetails(),
				HttpStatus.OK);

 
	}
 
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/programscheduled/{scheduledId}")
 
	public ResponseEntity<ProgramScheduledDto> findProgramScheduledById(@PathVariable Long scheduledId) {
 
		return new ResponseEntity<ProgramScheduledDto>(programScheduledService.findProgramScheduledById(scheduledId),
				HttpStatus.OK);
 
		
 
	}
 

//	/	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteprogramscheduled/{scheduledId}")
 
	public ResponseEntity<String> deleteProgramScheduledById(@PathVariable Long scheduledId) {
 
		return new ResponseEntity<String>(programScheduledService.deleteProgramScheduledById(scheduledId),
				HttpStatus.OK);
 
 
	}
 

//	/	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/programscheduled/{scheduledId}")
 
	public ResponseEntity<ProgramScheduledDto> updateProgramScheduled(@PathVariable("scheduledId") Long scheduledId,
			@RequestBody ProgramScheduled programScheduled) {
 
		return new ResponseEntity<ProgramScheduledDto>(
				programScheduledService.updateProgramScheduled(scheduledId, programScheduled), HttpStatus.OK);
 
		
	}
	
 
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/programscheduled/collegename/{collegeName}")
 
	public ResponseEntity<List<ProgramScheduledDto>> findProgramScheduledByCollege_CollegeName(
			@PathVariable String collegeName) {
 
		return new ResponseEntity<List<ProgramScheduledDto>>(
				programScheduledService.findProgramScheduledByCollege_CollegeName(collegeName), HttpStatus.OK);
 
 
	}
 
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/programscheduled/date/{startDate}")
 
	public ResponseEntity<List<ProgramScheduledDto>> findProgramScheduledByStartDate(
			@PathVariable LocalDate startDate) {
 
		return new ResponseEntity<List<ProgramScheduledDto>>(
				programScheduledService.findProgramScheduledByStartDate(startDate), HttpStatus.OK);
 
	}
	
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/programscheduled/collegeId/{collegeRegId}")
	public ResponseEntity<List<ProgramScheduledDto>> findByCollege_CollegeRegId(@PathVariable Long collegeRegId){
		return new ResponseEntity<List<ProgramScheduledDto>>(
				programScheduledService.findByCollege_CollegeRegId(collegeRegId), HttpStatus.OK);
	}
	
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/programscheduled/branchId/{branchId}")
	public ResponseEntity<List<ProgramScheduledDto>> findByBranch_BranchId(@PathVariable Long branchId){
		return new ResponseEntity<List<ProgramScheduledDto>>(
				programScheduledService.findByBranch_BranchId(branchId), HttpStatus.OK);
	}
	
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/programscheduled/programId/{programId}")
	public ResponseEntity<List<ProgramScheduledDto>> findByProgram_ProgramId(@PathVariable Long programId) {
		return new ResponseEntity<List<ProgramScheduledDto>>(
				programScheduledService.findByProgram_ProgramId(programId), HttpStatus.OK);
	}
	
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/programscheduled/courseId/{courseId}")
	public ResponseEntity<List<ProgramScheduledDto>> findByCourse_CourseId(@PathVariable Long courseId) {
		return new ResponseEntity<List<ProgramScheduledDto>>(
				programScheduledService.findByCourse_CourseId(courseId), HttpStatus.OK);
	}
	
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/programscheduled/universityId/{universityId}")
	public ResponseEntity <List<ProgramScheduledDto>> findByUniversity_UniversityId(@PathVariable Long universityId) {
		return new ResponseEntity<List<ProgramScheduledDto>>(
				programScheduledService.findByUniversity_UniversityId(universityId), HttpStatus.OK);
	}
}