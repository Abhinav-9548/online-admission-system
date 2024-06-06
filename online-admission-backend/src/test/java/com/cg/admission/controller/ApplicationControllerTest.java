package com.cg.admission.controller;

import com.cg.admission.dto.ApplicationDto;
import com.cg.admission.entity.Application;
import com.cg.admission.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationControllerTest {

    @Mock
    private ApplicationService applicationService;

    @InjectMocks
    private ApplicationController applicationController;

    private Application application;
    private ApplicationDto applicationDto;
    private List<ApplicationDto> applicationDtoList;

    @BeforeEach
    public void setUp() {
        // Initialize mock data
        application = new Application();
        application.setApplicantFullName("John Doe");
        application.setEmailId("john.doe@example.com");

        applicationDto = new ApplicationDto();
        applicationDto.setApplicantFullName("John Doe");
        applicationDto.setEmailId("john.doe@example.com");

        applicationDtoList = new ArrayList<>();
        applicationDtoList.add(applicationDto);
    }

    @Test
    public void testViewAllApplicationDetails() {
        // Mock the service method
        when(applicationService.viewAllApplicationDetails()).thenReturn(applicationDtoList);

        // Call the controller method
        ResponseEntity<List<ApplicationDto>> response = applicationController.viewAllApplicationDetails();

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(applicationDtoList, response.getBody());
    }

    @Test
    public void testGetApplicationDetailsByEmail() {
        String email = "john.doe@example.com";

        // Mock the service method
        when(applicationService.getApplicationDetailsByEmail(email)).thenReturn(applicationDtoList);

        // Call the controller method
        ResponseEntity<List<ApplicationDto>> response = applicationController.getApplicationDetailsByEmail(email);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(applicationDtoList, response.getBody());
    }

    @Test
    public void testAddApplication() {
        Long id = 1L;

        // Mock the service method
        when(applicationService.addApplication(id, application)).thenReturn(applicationDto);

        // Call the controller method
        ResponseEntity<ApplicationDto> response = applicationController.addApplication(id, application);

        // Verify the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(applicationDto, response.getBody());
    }

    @Test
    public void testGetApplicationDetailsByStatus() {
        String status = "Pending";

        // Mock the service method
        when(applicationService.getApplicationDetailsByStatus(status)).thenReturn(applicationDtoList);

        // Call the controller method
        ResponseEntity<List<ApplicationDto>> response = applicationController.getApplicationDetailsByStatus(status);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(applicationDtoList, response.getBody());
    }

    @Test
    public void testDeleteApplicationByid() {
        Long id = 1L;

        // Mock the service method
        when(applicationService.deleteApplicationByid(id)).thenReturn("Deleted");

        // Call the controller method
        ResponseEntity<String> response = applicationController.deleteApplicationByid(id);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted", response.getBody());
    }

    @Test
    public void testDeleteApplicationByEmail() {
        String emailId = "john.doe@example.com";

        // Mock the service method
        when(applicationService.deleteApplicationByEmail(emailId)).thenReturn("Deleted");

        // Call the controller method
        ResponseEntity<String> response = applicationController.deleteApplicationByEmail(emailId);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted", response.getBody());
    }

    @Test
    public void testGetApplicationByid() {
        Long id = 1L;

        // Mock the service method
        when(applicationService.getApplicationByid(id)).thenReturn(applicationDto);

        // Call the controller method
        ResponseEntity<ApplicationDto> response = applicationController.getApplicationByid(id);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(applicationDto, response.getBody());
    }

    @Test
    public void testUpdateApplicationStatus() {
        Long id = 1L;

        // Mock the service method
        when(applicationService.updateApplicationStatus(id, application)).thenReturn(id);

        // Call the controller method
        ResponseEntity<Long> response = applicationController.updateApplicationStatus(id, application);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody());
    }
}
