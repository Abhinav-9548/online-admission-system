package com.cg.admission.controller;

import com.cg.admission.dto.CollegeDto;
import com.cg.admission.dto.UniversityDto;
import com.cg.admission.entity.University;
import com.cg.admission.service.UniversityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UniversityControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UniversityService universityService;

    @InjectMocks
    private UniversityController universityController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(universityController).build();
    }

    @Test
    public void testGetAllUniversity() throws Exception {
        // Mock data
        List<UniversityDto> universityDtos = new ArrayList<>();
        UniversityDto universityDto = new UniversityDto();
        universityDto.setUniversityId(1L);
        universityDto.setName("Test University");
        universityDtos.add(universityDto);

        // Mocking service method
        when(universityService.getAllUniversity()).thenReturn(universityDtos);

        // Perform GET request
        mockMvc.perform(get("/university"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].universityId").value(universityDto.getUniversityId()))
                .andExpect(jsonPath("$[0].name").value(universityDto.getName()));
    }

    @Test
    public void testGetUniversityById() throws Exception {
        // Mock data
        UniversityDto universityDto = new UniversityDto();
        universityDto.setUniversityId(1L);
        universityDto.setName("Test University");

        // Mocking service method
        when(universityService.getUniversityById(anyLong())).thenReturn(universityDto);

        // Perform GET request
        mockMvc.perform(get("/university/id/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.universityId").value(universityDto.getUniversityId()))
                .andExpect(jsonPath("$.name").value(universityDto.getName()));
    }

    @Test
    public void testAddUniversity() throws Exception {
        // Mock data
        University university = new University();
        university.setUniversityId(1L);
        university.setName("Test University");

        UniversityDto universityDto = new UniversityDto();
        universityDto.setUniversityId(1L);
        universityDto.setName("Test University");

        // Mocking service method
        when(universityService.addUniversity(any(University.class))).thenReturn(universityDto);

        // Perform POST request
        mockMvc.perform(post("/university")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(university)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.universityId").value(universityDto.getUniversityId()))
                .andExpect(jsonPath("$.name").value(universityDto.getName()));
    }

    @Test
    public void testGetUniversityDetailsByCity() throws Exception {
        // Mock data
        List<UniversityDto> universityDtos = new ArrayList<>();
        UniversityDto universityDto = new UniversityDto();
        universityDto.setUniversityId(1L);
        universityDto.setName("Test University");
        universityDtos.add(universityDto);

        // Mocking service method
        when(universityService.getUniversityDetailsByCity(anyString())).thenReturn(universityDtos);

        // Perform GET request
        mockMvc.perform(get("/university/city/{city}", "TestCity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].universityId").value(universityDto.getUniversityId()))
                .andExpect(jsonPath("$[0].name").value(universityDto.getName()));
    }

    @Test
    public void testGetUniversityDetailsByCollegeName() throws Exception {
        // Mock data
        List<UniversityDto> universityDtos = new ArrayList<>();
        UniversityDto universityDto = new UniversityDto();
        universityDto.setUniversityId(1L);
        universityDto.setName("Test University");
        universityDtos.add(universityDto);

        // Mocking service method
        when(universityService.getUniversityDetailsByCollegeName(anyString())).thenReturn(universityDtos);

        // Perform GET request
        mockMvc.perform(get("/university/collegename/{collegename}", "TestCollege"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].universityId").value(universityDto.getUniversityId()))
                .andExpect(jsonPath("$[0].name").value(universityDto.getName()));
    }

    @Test
    public void testUpdateUniversity() throws Exception {
        // Mock data
        University university = new University();
        university.setUniversityId(1L);
        university.setName("Updated University");

        Long updatedId = 1L;

        // Mocking service method
        when(universityService.updateUniversity(anyLong(), any(University.class))).thenReturn(updatedId);

        // Perform PUT request
        mockMvc.perform(put("/university/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(university)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(updatedId));
    }

    @Test
    public void testDeleteUniversityById() throws Exception {
        // Mock data
        Long universityId = 1L;
        String responseMessage = "University deleted successfully";

        // Mocking service method
        when(universityService.deleteUniversityById(anyLong())).thenReturn(responseMessage);

        // Perform DELETE request
        mockMvc.perform(delete("/university/id/{id}", universityId))
                .andExpect(status().isOk())
                .andExpect(content().string(responseMessage));
    }

    @Test
    public void testFindCollegeByUniverisity() throws Exception {
        // Mock data
        List<CollegeDto> collegeDtos = new ArrayList<>();
        CollegeDto collegeDto = new CollegeDto();
        collegeDto.setCollegeRegId(1L);
        collegeDto.setCollegeName("Test College");
        collegeDtos.add(collegeDto);

        // Mocking service method
        when(universityService.findCollegeByUniverisity(anyLong())).thenReturn(collegeDtos);

        // Perform GET request
        mockMvc.perform(get("/university/{universityId}/colleges", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].collegeRegId").value(collegeDto.getCollegeRegId()))
                .andExpect(jsonPath("$[0].collegeName").value(collegeDto.getCollegeName()));
    }

    // Helper method to convert object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
