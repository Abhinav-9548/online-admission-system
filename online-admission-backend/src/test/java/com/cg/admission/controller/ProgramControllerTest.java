package com.cg.admission.controller;

import com.cg.admission.dto.CourseDto;
import com.cg.admission.dto.ProgramDto;
import com.cg.admission.entity.Program;
import com.cg.admission.service.ProgramService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class ProgramControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProgramService programService; 

    @InjectMocks
    private ProgramController programController;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(programController).build();
    }

    @Test
    public void testAddProgram() throws Exception {
        // Mock data
        ProgramDto programDto = new ProgramDto(1L, "Test Program", "Test Eligibility", "Test Duration", "Test Degree Offered", new ArrayList<>());
        Program program = new Program(1L, "Test Program", "Test Eligibility", "Test Duration", "Test Degree Offered", new ArrayList<>());

        // Mocking service method
        when(programService.addProgram(anyLong(), any(Program.class))).thenReturn(programDto);

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/add/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(program)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.programId").value(programDto.getProgramId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.programName").value(programDto.getProgramName()))
                .andDo(print());
    }

    @Test
    public void testViewAllProgramDetails() throws Exception {
        // Mock data
        List<ProgramDto> programs = new ArrayList<>();
        programs.add(new ProgramDto(1L, "Test Program", "Test Eligibility", "Test Duration", "Test Degree Offered", new ArrayList<>()));

        // Mocking service method
        when(programService.viewAllProgramDetails()).thenReturn(programs);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/program"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(programs.size()))
                .andDo(print());
    }

    @Test
    public void testDeleteProgramById() throws Exception {
        // Mock data
        Long programId = 1L;

        // Mocking service method
        when(programService.deleteProgramById(anyLong())).thenReturn("Program deleted successfully");

        // Perform DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/delete/{programId}", programId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Program deleted successfully"))
                .andDo(print());
    }

    @Test
    public void testFindProgramById() throws Exception {
        // Mock data
        ProgramDto programDto = new ProgramDto(1L, "Test Program", "Test Eligibility", "Test Duration", "Test Degree Offered", new ArrayList<>());

        // Mocking service method
        when(programService.findProgramById(anyLong())).thenReturn(programDto);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/program/Id/{programId}", 1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.programId").value(programDto.getProgramId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.programName").value(programDto.getProgramName()))
                .andDo(print());
    }

    @Test
    public void testFindProgramDetailsByProgramName() throws Exception {
        // Mock data
        List<ProgramDto> programs = new ArrayList<>();
        programs.add(new ProgramDto(1L, "Test Program", "Test Eligibility", "Test Duration", "Test Degree Offered", new ArrayList<>()));

        // Mocking service method
        when(programService.findProgramDetailsByProgramName(anyString())).thenReturn(programs);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/program/name/{programName}", "Test Program"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(programs.size()))
                .andDo(print());
    }

    @Test
    public void testFindProgramDetailsByEligibility() throws Exception {
        // Mock data
        List<ProgramDto> programs = new ArrayList<>();
        programs.add(new ProgramDto(1L, "Test Program", "Test Eligibility", "Test Duration", "Test Degree Offered", new ArrayList<>()));

        // Mocking service method
        when(programService.findProgramDetailsByEligibility(anyString())).thenReturn(programs);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/program/eligibility/{eligibility}", "Test Eligibility"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(programs.size()))
                .andDo(print());
    }

    @Test
    public void testDeleteProgramByProgramName() throws Exception {
        // Mock data
        String programName = "Test Program";

        // Mocking service method
        when(programService.deleteProgramByProgramName(anyString())).thenReturn("Program deleted successfully");

        // Perform DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/delete/name/{programName}", programName))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Program deleted successfully"))
                .andDo(print());
    }

    @Test
    public void testUpdateProgramStatus() throws Exception {
        // Mock data
        ProgramDto programDto = new ProgramDto(1L, "Updated Program", "Updated Eligibility", "Updated Duration", "Updated Degree Offered", new ArrayList<>());
        Program program = new Program(1L, "Updated Program", "Updated Eligibility", "Updated Duration", "Updated Degree Offered", new ArrayList<>());

        // Mocking service method
        when(programService.updateProgramStatus(anyLong(), any(Program.class))).thenReturn(programDto.getProgramId());

        // Perform PUT request
        mockMvc.perform(MockMvcRequestBuilders.put("/api/update/{programId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(program)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(programDto.getProgramId())))
                .andDo(print());
    }

    @Test
    public void testFindCoursesByProgram() throws Exception {
        // Mock data
        List<CourseDto> courses = new ArrayList<>();
        courses.add(new CourseDto(1L, "Test Course", "Test Eligibility","", new ArrayList<>()));

        // Mocking service method
        when(programService.findCoursesByProgram(anyLong())).thenReturn(courses);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/program/{programId}/courses", 1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(courses.size()))
                .andDo(print());
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
