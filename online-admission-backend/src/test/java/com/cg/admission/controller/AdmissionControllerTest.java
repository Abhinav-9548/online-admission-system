package com.cg.admission.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.admission.dto.AdmissionDto;
import com.cg.admission.entity.Admission;
import com.cg.admission.service.AdmissionService;

@ExtendWith(MockitoExtension.class)
public class AdmissionControllerTest {

    @Mock
    private AdmissionService admissionService;

    @InjectMocks
    private AdmissionController admissionController;

    private Admission admission;
    private AdmissionDto admissionDto;
    private List<AdmissionDto> admissionDtoList;

    @BeforeEach
    public void setUp() {
        // Initialize mock data
        admission = new Admission();
        admission.setEmailId("test@example.com");
        admission.setAdmissionStatus("Pending");
        admission.setYear("2024");

        admissionDto = new AdmissionDto();
        admissionDto.setEmailId("test@example.com");
        admissionDto.setAdmissionStatus("Pending");
        admissionDto.setYear("2024");

        admissionDtoList = new ArrayList<>();
        admissionDtoList.add(admissionDto);
    }

    @Test
    public void testViewAllAdmission() {
        // Mock the service method
        when(admissionService.viewAllAdmission()).thenReturn(admissionDtoList);

        // Call the controller method
        ResponseEntity<List<AdmissionDto>> response = admissionController.viewAllAdmission();

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(admissionDtoList, response.getBody());
    }

    @Test
    public void testAddAdmission() {
        Long collegeId = 1L;
        Long programId = 2L;
        Long courseId = 3L;
        Long applicationId = 4L;

        // Mock the service method
        when(admissionService.addAdmission(applicationId, collegeId, programId, courseId, admission))
                .thenReturn(admissionDto);

        // Call the controller method
        ResponseEntity<AdmissionDto> response = admissionController.addAdmission(collegeId, programId,
                courseId, applicationId, admission);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(admissionDto, response.getBody());
    }

    @Test
    public void testGetById() {
        Long admissionId = 1L;

        // Mock the service method
        when(admissionService.getById(admissionId)).thenReturn(admissionDto);

        // Call the controller method
        ResponseEntity<AdmissionDto> response = admissionController.getById(admissionId);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(admissionDto, response.getBody());
    }

    @Test
    public void testFindByApplication_ApplicationId() {
        Long applicationId = 1L;

        // Mock the service method
        when(admissionService.findByApplication_ApplicationId(applicationId)).thenReturn(admissionDto);

        // Call the controller method
        ResponseEntity<AdmissionDto> response = admissionController.findByApplication_ApplicationId(applicationId);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(admissionDto, response.getBody());
    }

    @Test
    public void testDeleteById() {
        Long admissionId = 1L;

        // Mock the service method
        when(admissionService.deleteById(admissionId)).thenReturn("Deleted");

        // Call the controller method
        ResponseEntity<String> response = admissionController.deleteById(admissionId);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted", response.getBody());
    }

    @Test
    public void testUpdateAdmission() {
        Long admissionId = 1L;

        // Mock the service method
        when(admissionService.updateAdmission(admissionId, admission)).thenReturn(admissionId);

        // Call the controller method
        ResponseEntity<Long> response = admissionController.updateAdmission(admissionId, admission);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(admissionId, response.getBody());
    }
}
