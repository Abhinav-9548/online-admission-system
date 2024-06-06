package com.cg.admission.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.admission.dto.ProgramScheduledDto;
import com.cg.admission.entity.ProgramScheduled;
import com.cg.admission.service.ProgramScheduledService;
 
@ExtendWith(MockitoExtension.class)
public class ProgramScheduledControllerTest {
 
    @Mock
    private ProgramScheduledService programScheduledService;
 
    @InjectMocks
    private ProgramScheduledController programScheduledController;
 
    private ProgramScheduled programScheduled;
    private ProgramScheduledDto programScheduledDto;
    private List<ProgramScheduledDto> programScheduledDtoList;
 
    @BeforeEach
    void setUp() {
        programScheduled = new ProgramScheduled();
        programScheduled.setScheduledId(1L);
        programScheduled.setStartDate(LocalDate.now());
        programScheduled.setEndDate(LocalDate.now().plusDays(10));
 
        programScheduledDto = new ProgramScheduledDto();
        programScheduledDtoList = Collections.singletonList(programScheduledDto);
    }
 
    @Test
    void testAddProgramSchedule() {
        when(programScheduledService.addProgramSchedule(anyLong(), anyLong(), anyLong(), anyLong(), anyLong(), any(ProgramScheduled.class)))
                .thenReturn(programScheduledDto);
        ResponseEntity<ProgramScheduledDto> response = programScheduledController.addProgramSchedule(1L, 1L, 1L, 1L, 1L, programScheduled);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(programScheduledService, times(1)).addProgramSchedule(anyLong(), anyLong(), anyLong(), anyLong(), anyLong(), any(ProgramScheduled.class));
    }
 
    @Test
    void testViewAllProgramScheduledDetails() {
        when(programScheduledService.viewAllProgramScheduledDetails()).thenReturn(programScheduledDtoList);
        ResponseEntity<List<ProgramScheduledDto>> response = programScheduledController.viewAllProgramScheduledDetails();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        verify(programScheduledService, times(1)).viewAllProgramScheduledDetails();
    }
 
    @Test
    void testFindProgramScheduledById() {
        when(programScheduledService.findProgramScheduledById(anyLong())).thenReturn(programScheduledDto);
        ResponseEntity<ProgramScheduledDto> response = programScheduledController.findProgramScheduledById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(programScheduledService, times(1)).findProgramScheduledById(anyLong());
    }
 
    @Test
    void testDeleteProgramScheduledById() {
        when(programScheduledService.deleteProgramScheduledById(anyLong())).thenReturn("Program Scheduled deleted Successfully");
        ResponseEntity<String> response = programScheduledController.deleteProgramScheduledById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Program Scheduled deleted Successfully", response.getBody());
        verify(programScheduledService, times(1)).deleteProgramScheduledById(anyLong());
    }
 
    @Test
    void testUpdateProgramScheduled() {
        when(programScheduledService.updateProgramScheduled(anyLong(), any(ProgramScheduled.class))).thenReturn(programScheduledDto);
        ResponseEntity<ProgramScheduledDto> response = programScheduledController.updateProgramScheduled(1L, programScheduled);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(programScheduledService, times(1)).updateProgramScheduled(anyLong(), any(ProgramScheduled.class));
    }
 
    @Test
    void testFindProgramScheduledByCollege_CollegeName() {
        when(programScheduledService.findProgramScheduledByCollege_CollegeName(anyString())).thenReturn(programScheduledDtoList);
        ResponseEntity<List<ProgramScheduledDto>> response = programScheduledController.findProgramScheduledByCollege_CollegeName("Test College");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        verify(programScheduledService, times(1)).findProgramScheduledByCollege_CollegeName(anyString());
    }
 
    @Test
    void testFindProgramScheduledByStartDate() {
        when(programScheduledService.findProgramScheduledByStartDate(any(LocalDate.class))).thenReturn(programScheduledDtoList);
        ResponseEntity<List<ProgramScheduledDto>> response = programScheduledController.findProgramScheduledByStartDate(LocalDate.now());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        verify(programScheduledService, times(1)).findProgramScheduledByStartDate(any(LocalDate.class));
    }
 
    @Test
    void testFindByCollege_CollegeRegId() {
        when(programScheduledService.findByCollege_CollegeRegId(anyLong())).thenReturn(programScheduledDtoList);
        ResponseEntity<List<ProgramScheduledDto>> response = programScheduledController.findByCollege_CollegeRegId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        verify(programScheduledService, times(1)).findByCollege_CollegeRegId(anyLong());
    }
 
    @Test
    void testFindByBranch_BranchId() {
        when(programScheduledService.findByBranch_BranchId(anyLong())).thenReturn(programScheduledDtoList);
        ResponseEntity<List<ProgramScheduledDto>> response = programScheduledController.findByBranch_BranchId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        verify(programScheduledService, times(1)).findByBranch_BranchId(anyLong());
    }
 
    @Test
    void testFindByProgram_ProgramId() {
        when(programScheduledService.findByProgram_ProgramId(anyLong())).thenReturn(programScheduledDtoList);
        ResponseEntity<List<ProgramScheduledDto>> response = programScheduledController.findByProgram_ProgramId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        verify(programScheduledService, times(1)).findByProgram_ProgramId(anyLong());
    }
 
    @Test
    void testFindByCourse_CourseId() {
        when(programScheduledService.findByCourse_CourseId(anyLong())).thenReturn(programScheduledDtoList);
        ResponseEntity<List<ProgramScheduledDto>> response = programScheduledController.findByCourse_CourseId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        verify(programScheduledService, times(1)).findByCourse_CourseId(anyLong());
    }
 
    @Test
    void testFindByUniversity_UniversityId() {
        when(programScheduledService.findByUniversity_UniversityId(anyLong())).thenReturn(programScheduledDtoList);
        ResponseEntity<List<ProgramScheduledDto>> response = programScheduledController.findByUniversity_UniversityId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        verify(programScheduledService, times(1)).findByUniversity_UniversityId(anyLong());
    }
}