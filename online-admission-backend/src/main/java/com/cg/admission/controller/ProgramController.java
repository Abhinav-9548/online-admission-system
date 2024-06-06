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

import com.cg.admission.dto.CourseDto;
import com.cg.admission.dto.ProgramDto;
import com.cg.admission.entity.Program;
import com.cg.admission.service.ProgramService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ProgramController {

    @Autowired
    private ProgramService programService;

    /**
     * Add a new program to a university.
     * @param id The ID of the university.
     * @param program The Program entity to add.
     * @return ResponseEntity containing the created ProgramDto object.
     */
    @PostMapping("/add/{id}")
    public ResponseEntity<ProgramDto> addProgram(@PathVariable Long id, @Valid @RequestBody Program program) {
        return new ResponseEntity<>(programService.addProgram(id, program), HttpStatus.OK);
    }

    /**
     * Retrieve all programs.
     * @return ResponseEntity containing a list of ProgramDto objects.
     */
    @GetMapping("/program")
    public ResponseEntity<List<ProgramDto>> viewAllProgramDetails() {
        return new ResponseEntity<>(programService.viewAllProgramDetails(), HttpStatus.OK);
    }

    /**
     * Delete a program by its ID.
     * @param programId The ID of the program to delete.
     * @return ResponseEntity containing a response message.
     */
    @DeleteMapping("/delete/{programId}")
    public ResponseEntity<String> deleteProgramById(@PathVariable Long programId) {
        return new ResponseEntity<>(programService.deleteProgramById(programId), HttpStatus.OK);
    }

    /**
     * Retrieve a program by its ID.
     * @param programId The ID of the program to retrieve.
     * @return ResponseEntity containing the ProgramDto object.
     */
    @GetMapping("/program/Id/{programId}")
    public ResponseEntity<ProgramDto> findProgramById(@PathVariable Long programId) {
        return new ResponseEntity<>(programService.findProgramById(programId), HttpStatus.OK);
    }

    /**
     * Retrieve programs by their name.
     * @param programName The name of the program to filter by.
     * @return ResponseEntity containing a list of ProgramDto objects.
     */
    @GetMapping("/program/name/{programName}")
    public ResponseEntity<List<ProgramDto>> findProgramDetailsByProgramName(@PathVariable String programName) {
        return new ResponseEntity<>(programService.findProgramDetailsByProgramName(programName), HttpStatus.OK);
    }

    /**
     * Retrieve programs by their eligibility criteria.
     * @param eligibility The eligibility criteria to filter programs by.
     * @return ResponseEntity containing a list of ProgramDto objects.
     */
    @GetMapping("/program/eligibility/{eligibility}")
    public ResponseEntity<List<ProgramDto>> findProgramDetailsByEligibility(@PathVariable String eligibility) {
        return new ResponseEntity<>(programService.findProgramDetailsByEligibility(eligibility), HttpStatus.OK);
    }

    /**
     * Delete a program by its name.
     * @param programName The name of the program to delete.
     * @return ResponseEntity containing a response message.
     */
    @DeleteMapping("/delete/name/{programName}")
    public ResponseEntity<String> deleteProgramByProgramName(@PathVariable("programName") String programName) {
        return new ResponseEntity<>(programService.deleteProgramByProgramName(programName), HttpStatus.OK);
    }

    /**
     * Update the status of a program by its ID.
     * @param programId The ID of the program to update.
     * @param program The updated Program entity.
     * @return ResponseEntity containing the ID of the updated program.
     */
    @PutMapping("/update/{programId}")
    public ResponseEntity<Long> updateProgramStatus(@PathVariable Long programId, @Valid @RequestBody Program program) {
        return new ResponseEntity<>(programService.updateProgramStatus(programId, program), HttpStatus.OK);
    }

    /**
     * Retrieve courses by program ID.
     * @param programId The ID of the program to filter courses by.
     * @return ResponseEntity containing a list of CourseDto objects.
     */
    @GetMapping("program/{programId}/courses")
    public ResponseEntity<List<CourseDto>> findCoursesByProgram(@PathVariable Long programId) {
        return new ResponseEntity<>(programService.findCoursesByProgram(programId), HttpStatus.OK);
    }
}
