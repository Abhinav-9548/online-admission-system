package com.cg.admission.service.impl;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
 
import com.cg.admission.dto.CollegeDto;
import com.cg.admission.dto.UniversityDto;
import com.cg.admission.entity.Address;
import com.cg.admission.entity.College;
import com.cg.admission.entity.ProgramScheduled;
import com.cg.admission.entity.University;
import com.cg.admission.exceptions.ResourceAlreadyPresentException;
import com.cg.admission.repository.AddressRepository;
import com.cg.admission.repository.ProgramScheduledRepository;
import com.cg.admission.repository.UniversityRepository;
import com.cg.admission.service.ProgramScheduledService;
 
@ExtendWith(MockitoExtension.class)
public class UniversityServiceImplTest {
 
    @InjectMocks
    private UniversityServiceImpl universityService;
 
    @Mock
    private UniversityRepository universityRepository;
 
    @Mock
    private AddressRepository addressRepository;
 
    @Mock
    private ProgramScheduledRepository programScheduledRepository;
 
    @Mock
    private ProgramScheduledService programScheduledService;
 
    private University university;
    private UniversityDto universityDto;
    private Address address;
    private List<College> collegeList;
    private List<ProgramScheduled> programScheduledList;
 
    @BeforeEach
    public void setUp() {
        address = new Address();
        address.setAddressId(1L);
        address.setCity("TestCity");
 
        university = new University();
        university.setUniversityId(1L);
        university.setName("TestUniversity");
        university.setAddress(address);
 
        universityDto = new UniversityDto();
        BeanUtils.copyProperties(university, universityDto);
 
        collegeList = new ArrayList<>();
        programScheduledList = new ArrayList<>();
    }
 
    @Test
    public void testGetAllUniversity() {
        List<University> universities = new ArrayList<>();
        universities.add(university);
 
        when(universityRepository.findAll()).thenReturn(universities);
 
        List<UniversityDto> result = universityService.getAllUniversity();
        assertEquals(1, result.size());
        assertEquals("TestUniversity", result.get(0).getName());
    }
 
    @Test
    public void testGetUniversityById() {
        when(universityRepository.findById(1L)).thenReturn(Optional.of(university));
 
        UniversityDto result = universityService.getUniversityById(1L);
        assertEquals("TestUniversity", result.getName());
    }
 
    @Test
    public void testAddUniversity() {
        when(universityRepository.findByName("TestUniversity")).thenReturn(null);
        when(universityRepository.save(any(University.class))).thenReturn(university);
        when(addressRepository.save(any(Address.class))).thenReturn(address);
 
        UniversityDto result = universityService.addUniversity(university);
        assertEquals("TestUniversity", result.getName());
    }
 
    @Test
    public void testAddUniversity_ThrowsException() {
        when(universityRepository.findByName("TestUniversity")).thenReturn(university);
 
        assertThrows(ResourceAlreadyPresentException.class, () -> {
            universityService.addUniversity(university);
        });
    }
 
    @Test
    public void testUpdateUniversity() {
        when(universityRepository.findById(1L)).thenReturn(Optional.of(university));
        when(universityRepository.save(any(University.class))).thenReturn(university);
        when(addressRepository.save(any(Address.class))).thenReturn(address);
 
        University updatedUniversity = new University();
        updatedUniversity.setName("UpdatedName");
        updatedUniversity.setAddress(address);
 
        Long result = universityService.updateUniversity(1L, updatedUniversity);
        assertEquals(1L, result);
    }
 
    @Test
    public void testDeleteUniversityById() {
        when(universityRepository.findById(1L)).thenReturn(Optional.of(university));
        when(programScheduledRepository.findByUniversity_UniversityId(1L)).thenReturn(programScheduledList);
        doNothing().when(universityRepository).delete(any(University.class));
 
        String result = universityService.deleteUniversityById(1L);
        assertEquals("University Deleted successfully", result);
        verify(universityRepository, times(1)).delete(university);
    }
 
    @Test
    public void testFindCollegeByUniversity() {
        College college = new College();
        college.setCollegeName("TestCollege");
        collegeList.add(college);
        university.setCollegeList(collegeList);
 
        when(universityRepository.findById(1L)).thenReturn(Optional.of(university));
 
        List<CollegeDto> result = universityService.findCollegeByUniverisity(1L);
        assertEquals(1, result.size());
        assertEquals("TestCollege", result.get(0).getCollegeName());
    }
 
    @Test
    public void testGetUniversityDetailsByCity() {
        List<University> universities = new ArrayList<>();
        universities.add(university);
 
        when(universityRepository.findByAddressCity("TestCity")).thenReturn(universities);
 
        List<UniversityDto> result = universityService.getUniversityDetailsByCity("TestCity");
        assertEquals(1, result.size());
        assertEquals("TestUniversity", result.get(0).getName());
    }
 
    @Test
    public void testGetUniversityDetailsByCollegeName() {
        List<University> universities = new ArrayList<>();
        universities.add(university);
 
        when(universityRepository.findByCollegeListCollegeName("TestCollege")).thenReturn(universities);
 
        List<UniversityDto> result = universityService.getUniversityDetailsByCollegeName("TestCollege");
        assertEquals(1, result.size());
        assertEquals("TestUniversity", result.get(0).getName());
    }
}