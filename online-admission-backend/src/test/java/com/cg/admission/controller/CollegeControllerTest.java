package com.cg.admission.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
 
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 
import com.cg.admission.dto.CollegeDto;
import com.cg.admission.dto.ProgramDto;
import com.cg.admission.entity.College;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.service.impl.CollegeServiceImpl;
public class CollegeControllerTest {
    @Mock
    private CollegeServiceImpl collegeServiceImpl;
    @InjectMocks
    private CollegeController collegeController;
    private CollegeDto collegeDto;
    private ProgramDto programDto;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        collegeDto = new CollegeDto();
        collegeDto.setCollegeRegId(1L);
        collegeDto.setCollegeName("Test College");
        programDto = new ProgramDto();
        programDto.setProgramId(1L);
        programDto.setProgramName("Test Program");
    }
    @Test
    void testViewAllCollegeDetails_Positive() {
        List<CollegeDto> collegeList = Arrays.asList(collegeDto);
        when(collegeServiceImpl.viewAllCollegeDetails()).thenReturn(collegeList);
        ResponseEntity<List<CollegeDto>> response = collegeController.viewAllCollegeDetails();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(collegeDto.getCollegeRegId(), response.getBody().get(0).getCollegeRegId());
    }
    @Test
    void testViewAllCollegeDetails_Empty() {
        when(collegeServiceImpl.viewAllCollegeDetails()).thenReturn(Collections.emptyList());
        ResponseEntity<List<CollegeDto>> response = collegeController.viewAllCollegeDetails();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }
    @Test
    void testGetCollegeDetailsByProgram_Positive() {
        List<CollegeDto> collegeList = Arrays.asList(collegeDto);
        when(collegeServiceImpl.getCollegeDetailsByProgram(anyString())).thenReturn(collegeList);
        ResponseEntity<List<CollegeDto>> response = collegeController.getCollegeDetailsByProgram("Test Program");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(collegeDto.getCollegeRegId(), response.getBody().get(0).getCollegeRegId());
    }
    @Test
    void testGetCollegeDetailsByCourse_Positive() {
        List<CollegeDto> collegeList = Arrays.asList(collegeDto);
        when(collegeServiceImpl.getCollegeDetailsByCourse(anyString())).thenReturn(collegeList);
        ResponseEntity<List<CollegeDto>> response = collegeController.getCollegeDetailsByCourse("Test Course");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(collegeDto.getCollegeRegId(), response.getBody().get(0).getCollegeRegId());
    }
    @Test
    void testGetCollegeDetailsByBranch_Positive() {
        List<CollegeDto> collegeList = Arrays.asList(collegeDto);
        when(collegeServiceImpl.getCollegeDetailsByBranch(anyString())).thenReturn(collegeList);
        ResponseEntity<List<CollegeDto>> response = collegeController.getCollegeDetailsByBranch("Test Branch");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(collegeDto.getCollegeRegId(), response.getBody().get(0).getCollegeRegId());
    }
    @Test
    void testGetCollegeDetailsByName_Positive() {
        List<CollegeDto> collegeList = Arrays.asList(collegeDto);
        when(collegeServiceImpl.getCollegeDetailsByName(anyString())).thenReturn(collegeList);
        ResponseEntity<List<CollegeDto>> response = collegeController.getCollegeDetailsByName("Test College");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(collegeDto.getCollegeRegId(), response.getBody().get(0).getCollegeRegId());
    }

    @Test
    void testGetCollegeDetailsById_Positive() {
        when(collegeServiceImpl.getCollegeDetailsById(anyLong())).thenReturn(collegeDto);
        ResponseEntity<CollegeDto> response = collegeController.getCollegeDetailsById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(collegeDto.getCollegeRegId(), response.getBody().getCollegeRegId());
    }
    @Test
    void testGetCollegeDetailsById_Negative() {
        when(collegeServiceImpl.getCollegeDetailsById(anyLong())).thenThrow(new ResourceNotFoundException("College not found"));
        assertThrows(ResourceNotFoundException.class, () -> collegeController.getCollegeDetailsById(1L));
    }
    @Test
    void testAddCollege_Positive() {
        when(collegeServiceImpl.addCollege(anyLong(), any(College.class))).thenReturn(collegeDto);
        ResponseEntity<CollegeDto> response = collegeController.addCollege(1L, new College());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(collegeDto.getCollegeRegId(), response.getBody().getCollegeRegId());
    }
    @Test
    void testUpdateCollegeDetails_Positive() {
        when(collegeServiceImpl.updateCollegeDetails(anyLong(), any(College.class))).thenReturn(1L);
        ResponseEntity<Long> response = collegeController.updateCollegeDetails(1L, new College());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody());
    }
    @Test
    void testDeleteCollegeById_Positive() {
        when(collegeServiceImpl.deleteCollegeById(anyLong())).thenReturn("College deleted successfully");
        ResponseEntity<String> response = collegeController.deleteCollegeById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("College deleted successfully", response.getBody());
    }
    @Test
    void testDeleteCollegeByName_Positive() {
        when(collegeServiceImpl.deleteCollegeByName(anyString())).thenReturn("College deleted successfully");
        ResponseEntity<String> response = collegeController.deleteCollegeByName("Test College");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("College deleted successfully", response.getBody());
    }
    @Test
    void testGetProgramsByColleges_Positive() {
        List<ProgramDto> programList = Arrays.asList(programDto);
        when(collegeServiceImpl.getProgramsByColleges(anyLong())).thenReturn(programList);
        ResponseEntity<List<ProgramDto>> response = collegeController.getProgramsByColleges(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(programDto.getProgramId(), response.getBody().get(0).getProgramId());
    }
    @Test
    void testGetProgramsByColleges_Empty() {
        when(collegeServiceImpl.getProgramsByColleges(anyLong())).thenReturn(Collections.emptyList());
        ResponseEntity<List<ProgramDto>> response = collegeController.getProgramsByColleges(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }
}