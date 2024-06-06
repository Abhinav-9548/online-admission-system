package com.cg.admission.controller;

import com.cg.admission.dto.CourseDto;
import com.cg.admission.entity.Branch;
import com.cg.admission.entity.Course;
import com.cg.admission.service.CourseService;
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
public class CourseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(courseController).build();
    }

    @Test
    public void testViewAllCourseDetails() throws Exception {
        // Mock data
        List<CourseDto> courses = new ArrayList<>();
        courses.add(new CourseDto(1L, "Test Course", "Test Eligibility","", new ArrayList<>()));

        // Mocking service method
        when(courseService.viewAllCourseDetails()).thenReturn(courses);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(courses.size()))
                .andDo(print());
    }

    @Test
    public void testFindCourseDetailsByCourseName() throws Exception {
        // Mock data
        CourseDto courseDto = new CourseDto(1L, "Test Course", "Test Eligibility","", new ArrayList<>());

        // Mocking service method
        when(courseService.findCourseDetailsByCourseName(anyString())).thenReturn(courseDto);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/course/coursename/{courseName}", "Test Course"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseId").value(courseDto.getCourseId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseName").value(courseDto.getCourseName()))
                .andDo(print());
    }

    @Test
    public void testFindCourseDetailsByEligibility() throws Exception {
        // Mock data
        List<CourseDto> courses = new ArrayList<>();
        courses.add(new CourseDto(1L, "Test Course", "Test Eligibility","", new ArrayList<>()));

        // Mocking service method
        when(courseService.findCourseDetailsByEligibility(anyString())).thenReturn(courses);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/course/eligibility/{eligibility}", "Test Eligibility"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(courses.size()))
                .andDo(print());
    }

    @Test
    public void testFindCourseDetailsByCourseId() throws Exception {
        // Mock data
        CourseDto courseDto = new CourseDto(1L, "Test Course", "Test Eligibility","", new ArrayList<>());

        // Mocking service method
        when(courseService.findCourseDetailsByCourseId(anyLong())).thenReturn(courseDto);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/course/coursedetail/{courseId}", 1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseId").value(courseDto.getCourseId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseName").value(courseDto.getCourseName()))
                .andDo(print());
    }

    @Test
    public void testAddCourse() throws Exception {
        // Mock data
        CourseDto courseDto = new CourseDto(1L, "Test Course", "Test Eligibility","", new ArrayList<>());
        Course course = new Course(1L, "Test Course", "Test Eligibility","", new ArrayList<>());

        // Mocking service method
        when(courseService.addCourse(anyLong(), anyLong(), any(Course.class))).thenReturn(courseDto);

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/course/{collegeRegId}/program/{progrmId}", 1L, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(course)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseId").value(courseDto.getCourseId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseName").value(courseDto.getCourseName()))
                .andDo(print());
    }

    @Test
    public void testUpdateCourseDetails() throws Exception {
        // Mock data
        CourseDto courseDto = new CourseDto(1L, "Updated Course Name", "Updated Eligibility","", new ArrayList<>());
        Course course = new Course(1L, "Updated Course Name", "Updated Eligibility"," ", new ArrayList<>());

        // Mocking service method
        when(courseService.updateCourseDetails(anyLong(), any(Course.class))).thenReturn(courseDto.getCourseId());

        // Perform PUT request
        mockMvc.perform(MockMvcRequestBuilders.put("/api/course/update/{courseId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(course)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(courseDto.getCourseId())))
                .andDo(print());
    }

    @Test
    public void testDeleteCourseById() throws Exception {
        // Mock data
        Long courseId = 1L;

        // Mocking service method
        when(courseService.deleteCourseById(anyLong())).thenReturn("Course deleted successfully");

        // Perform DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/course/{courseId}", courseId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Course deleted successfully"))
                .andDo(print());
    }

    @Test
    public void testDeleteCourseByCourseName() throws Exception {
        // Mock data
        String courseName = "Test Course";

        // Mocking service method
        when(courseService.deleteCourseByCourseName(anyString())).thenReturn("Course deleted successfully");

        // Perform DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/course/delete/coursename/{courseName}", courseName))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Course deleted successfully"))
                .andDo(print());
    }

    @Test
    public void testFindBranchByCourse() throws Exception {
        // Mock data
        List<Branch> branches = new ArrayList<>();
        branches.add(new Branch(1L, "Test Branch", "Test Branch Description"));

        // Mocking service method
        when(courseService.findBranchByCourse(anyLong())).thenReturn(branches);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses/{courseId}/branches", 1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(branches.size()))
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
