package com.cg.admission.service.impl;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
 
import com.cg.admission.dto.CourseDto;
import com.cg.admission.dto.ProgramDto;
import com.cg.admission.entity.College;
import com.cg.admission.entity.Course;
import com.cg.admission.entity.Program;
import com.cg.admission.repository.CollegeRepository;
import com.cg.admission.repository.ProgramRepository;
import com.cg.admission.repository.ProgramScheduledRepository;
import com.cg.admission.service.ProgramScheduledService;
 
@ExtendWith(MockitoExtension.class)
public class ProgramServiceImplTest {
 
    @InjectMocks
    private ProgramServiceImpl programServiceImpl;
 
    @Mock
    private ProgramRepository programRepository;
 
    @Mock
    private CollegeRepository collegeRepository;
 
    @Mock
    private ProgramScheduledRepository programScheduledRepository;
 
    @Mock
    private ProgramScheduledService programScheduledService;
 
    private Program program;
    private ProgramDto programDto;
    private College college;
 
    @BeforeEach
    public void setUp() {
        program = new Program();
        program.setProgramId(1L);
        program.setProgramName("Computer Science");
 
        programDto = new ProgramDto();
        BeanUtils.copyProperties(program, programDto);
 
        college = new College();
        college.setCollegeRegId(1L);
        college.setProgramList(new ArrayList<>());
    }
 
    @Test
    public void testAddProgram() {
        when(collegeRepository.findById(anyLong())).thenReturn(Optional.of(college));
        when(programRepository.save(any(Program.class))).thenReturn(program);
 
        ProgramDto result = programServiceImpl.addProgram(1L, program);
 
        assertNotNull(result);
        assertEquals(program.getProgramId(), result.getProgramId());
        verify(programRepository, times(1)).save(program);
        verify(collegeRepository, times(1)).findById(1L);
    }
 
    @Test
    public void testViewAllProgramDetails() {
        when(programRepository.findAll()).thenReturn(Arrays.asList(program));
 
        List<ProgramDto> result = programServiceImpl.viewAllProgramDetails();
 
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(programRepository, times(1)).findAll();
    }
 
    @Test
    public void testDeleteProgramById() {
        when(programRepository.findById(anyLong())).thenReturn(Optional.of(program));
        when(programScheduledRepository.findByProgram_ProgramId(anyLong())).thenReturn(new ArrayList<>());
 
        String result = programServiceImpl.deleteProgramById(1L);
 
        assertEquals("Programs Deleted Succesfully", result);
        verify(programRepository, times(1)).deleteById(1L);
    }
 
    @Test
    public void testFindProgramById() {
        when(programRepository.findById(anyLong())).thenReturn(Optional.of(program));
 
        ProgramDto result = programServiceImpl.findProgramById(1L);
 
        assertNotNull(result);
        assertEquals(program.getProgramId(), result.getProgramId());
        verify(programRepository, times(1)).findById(1L);
    }
 
    @Test
    public void testFindProgramDetailsByProgramName() {
        when(programRepository.findByProgramName(anyString())).thenReturn(Arrays.asList(program));
 
        List<ProgramDto> result = programServiceImpl.findProgramDetailsByProgramName("Computer Science");
 
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(programRepository, times(1)).findByProgramName("Computer Science");
    }
 
    @Test
    public void testFindProgramDetailsByEligibility() {
        when(programRepository.findByEligibility(anyString())).thenReturn(Arrays.asList(program));
 
        List<ProgramDto> result = programServiceImpl.findProgramDetailsByEligibility("Bachelor");
 
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(programRepository, times(1)).findByEligibility("Bachelor");
    }
 
    @Test
    public void testDeleteProgramByProgramName() {
        when(programRepository.findByProgramName(anyString())).thenReturn(Arrays.asList(program));
        when(programScheduledRepository.findByProgram_ProgramId(anyLong())).thenReturn(new ArrayList<>());
 
        String result = programServiceImpl.deleteProgramByProgramName("Computer Science");
 
        assertEquals("Programs Deleted Succesfully", result);
        verify(programRepository, times(1)).deleteByProgramName("Computer Science");
    }
 
    @Test
    public void testUpdateProgramStatus() {
        when(programRepository.findById(anyLong())).thenReturn(Optional.of(program));
        when(programRepository.save(any(Program.class))).thenReturn(program);
 
        Long result = programServiceImpl.updateProgramStatus(1L, program);
 
        assertEquals(1L, result);
        verify(programRepository, times(1)).save(program);
    }
 
    @Test
    public void testFindCoursesByProgram() {
        Course course = new Course();
        course.setCourseId(1L);
        course.setCourseName("Java");
        program.setCourses(Arrays.asList(course));
 
        when(programRepository.findById(anyLong())).thenReturn(Optional.of(program));
 
        List<CourseDto> result = programServiceImpl.findCoursesByProgram(1L);
 
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(programRepository, times(1)).findById(1L);
    }
}