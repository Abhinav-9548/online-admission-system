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
import com.cg.admission.dto.UniversityDto;
import com.cg.admission.entity.University;
import com.cg.admission.service.UniversityService;

@RestController
@RequestMapping("/university")
@CrossOrigin("*")
public class UniversityController {

    @Autowired
    UniversityService universityService; 

    /**
     * Retrieve all universities.
     * @return ResponseEntity containing a list of UniversityDto objects.
     */
    @GetMapping
    public ResponseEntity<List<UniversityDto>> getAllUniversity() {
        List<UniversityDto> resource = universityService.getAllUniversity();
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Retrieve a university by its ID.
     * @param id The ID of the university to retrieve.
     * @return ResponseEntity containing the UniversityDto object.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<UniversityDto> getUniversityById(@PathVariable(value = "id") Long id) {
        UniversityDto resource = universityService.getUniversityById(id);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Add a new university.
     * @param university The University entity to add.
     * @return ResponseEntity containing the created UniversityDto object.
     */
    @PostMapping
    public ResponseEntity<UniversityDto> addUniversity(@RequestBody University university) {
        UniversityDto resource = universityService.addUniversity(university);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    /**
     * Retrieve universities by city.
     * @param city The city to filter universities by.
     * @return ResponseEntity containing a list of UniversityDto objects.
     */
    @GetMapping("/city/{city}")
    public ResponseEntity<List<UniversityDto>> getUniversityDetailsByCity(@PathVariable(value = "city") String city) {
        List<UniversityDto> resource = universityService.getUniversityDetailsByCity(city);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Retrieve universities by college name.
     * @param collegeName The college name to filter universities by.
     * @return ResponseEntity containing a list of UniversityDto objects.
     */
    @GetMapping("/collegename/{collegename}")
    public ResponseEntity<List<UniversityDto>> getUniversityDetailsByCollegeName(
            @PathVariable(value = "collegename") String collegeName) {
        List<UniversityDto> resource = universityService.getUniversityDetailsByCollegeName(collegeName);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Update an existing university by its ID.
     * @param id The ID of the university to update.
     * @param university The updated University entity.
     * @return ResponseEntity containing the ID of the updated university.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Long> updateUniversity(@PathVariable(value = "id") Long id,
            @RequestBody University university) {
        Long response = universityService.updateUniversity(id, university);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Delete a university by its ID.
     * @param id The ID of the university to delete.
     * @return ResponseEntity containing a response message.
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteUniversityById(@PathVariable(value = "id") Long id) {
        String response = universityService.deleteUniversityById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Find colleges by university ID.
     * @param universityId The ID of the university to filter colleges by.
     * @return ResponseEntity containing a list of CollegeDto objects.
     */
    @GetMapping("/{universityId}/colleges")
    public ResponseEntity<List<CollegeDto>> findCollegeByUniverisity(@PathVariable Long universityId) {
        List<CollegeDto> resource = universityService.findCollegeByUniverisity(universityId);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
