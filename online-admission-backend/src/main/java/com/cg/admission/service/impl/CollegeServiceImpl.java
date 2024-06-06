package com.cg.admission.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.admission.dto.CollegeDto;
import com.cg.admission.dto.ProgramDto;
import com.cg.admission.entity.Address;
import com.cg.admission.entity.College;
import com.cg.admission.entity.Program;
import com.cg.admission.entity.ProgramScheduled;
import com.cg.admission.entity.University;
import com.cg.admission.exceptions.ResourceAlreadyPresentException;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.repository.AddressRepository;
import com.cg.admission.repository.CollegeRepository;
import com.cg.admission.repository.ProgramScheduledRepository;
import com.cg.admission.repository.UniversityRepository;
import com.cg.admission.service.CollegeService;
import com.cg.admission.service.ProgramScheduledService;

@Service
public class CollegeServiceImpl implements CollegeService {

    @Autowired
    private CollegeRepository collegeRepository;
    
    @Autowired
    private UniversityRepository universityRepository;
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private ProgramScheduledRepository programScheduledRepository;
    
    @Autowired
    private ProgramScheduledService programScheduledService;

    /**
     * Adds a new college to a specified university.
     * @param universityId the ID of the university to which the college is to be added
     * @param college the college entity to be added
     * @return the DTO of the added college
     * @throws ResourceAlreadyPresentException if the college already exists
     */
    @Override
    public CollegeDto addCollege(Long universityId, College college) {
        // Fetching the university by ID
        University university = universityRepository.findById(universityId).get();
        CollegeDto collegeDto = new CollegeDto();

        // Setting the university for the college
        college.setUniversity(university);
        Address address = college.getAddress();
        addressRepository.save(address); // Saving the address
        college.setAddress(address);
 
        // Checking if the college already exists
        List<College> existingColleges = collegeRepository.findByCollegeName(college.getCollegeName());

        if (existingColleges.isEmpty()) {
            // Saving the college if it doesn't exist
            collegeRepository.save(college);
        } else {
            // Throwing exception if college exists
            throw new ResourceAlreadyPresentException("College Already Exist");
        }

        // Copying properties from college entity to college DTO
        BeanUtils.copyProperties(college, collegeDto);
        return collegeDto;
    }

    /**
     * Retrieves details of all colleges.
     * @return a list of college DTOs
     * @throws ResourceNotFoundException if no colleges are found
     */
    @Override
    public List<CollegeDto> viewAllCollegeDetails() {
        List<College> collegeList = collegeRepository.findAll();
        List<CollegeDto> collegeDtoList = collegeList.stream().map(i -> {
            CollegeDto collegeDto = new CollegeDto();
            BeanUtils.copyProperties(i, collegeDto);
            return collegeDto;
        }).collect(Collectors.toList());

        if (collegeDtoList.isEmpty()) {
            // Throwing exception if no college found
            throw new ResourceNotFoundException("No College Found");
        } else {
            return collegeDtoList;
        }
    }

    /**
     * Retrieves college details by program name.
     * @param programName the name of the program
     * @return a list of college DTOs
     * @throws ResourceNotFoundException if no colleges are found with the specified program name
     */
    @Override
    public List<CollegeDto> getCollegeDetailsByProgram(String programName) {
        List<College> colleges = new ArrayList<>();
        collegeRepository.findByProgramList_ProgramName(programName).forEach(colleges::add);
        List<CollegeDto> collegeDtoList = colleges.stream().map(i -> {
            CollegeDto collegeDto = new CollegeDto();
            BeanUtils.copyProperties(i, collegeDto);
            return collegeDto;
        }).collect(Collectors.toList());

        if (collegeDtoList.isEmpty()) {
            throw new ResourceNotFoundException("No College Found With Program Name " + programName);
        } else {
            return collegeDtoList;
        }
    }
 
    /**
     * Retrieves college details by course name.
     * @param courseName the name of the course
     * @return a list of college DTOs
     * @throws ResourceNotFoundException if no colleges are found with the specified course name
     */
    @Override
    public List<CollegeDto> getCollegeDetailsByCourse(String courseName) {
        List<College> colleges = new ArrayList<>();
        collegeRepository.findByCourseList_CourseName(courseName).forEach(colleges::add);
        List<CollegeDto> collegeDtoList = colleges.stream().map(i -> {
            CollegeDto collegeDto = new CollegeDto();
            BeanUtils.copyProperties(i, collegeDto);
            return collegeDto;
        }).collect(Collectors.toList());

        if (collegeDtoList.isEmpty()) {
            throw new ResourceNotFoundException("No College Found With Course Name " + courseName);
        } else {
            return collegeDtoList;
        }
    }

    /**
     * Retrieves college details by branch name.
     * @param branchName the name of the branch
     * @return a list of college DTOs
     * @throws ResourceNotFoundException if no colleges are found with the specified branch name
     */
    @Override
    public List<CollegeDto> getCollegeDetailsByBranch(String branchName) {
        List<College> colleges = new ArrayList<>();
        collegeRepository.findByBranchList_BranchName(branchName).forEach(colleges::add);
        List<CollegeDto> collegeDtoList = colleges.stream().map(i -> {
            CollegeDto collegeDto = new CollegeDto();
            BeanUtils.copyProperties(i, collegeDto);
            return collegeDto;
        }).collect(Collectors.toList());

        if (collegeDtoList.isEmpty()) {
            throw new ResourceNotFoundException("No College Found With Branch Name " + branchName);
        } else {
            return collegeDtoList;
        }
    }

    /**
     * Retrieves college details by college name.
     * @param collegeName the name of the college
     * @return a list of college DTOs
     * @throws ResourceNotFoundException if no colleges are found with the specified name
     */
    @Override
    public List<CollegeDto> getCollegeDetailsByName(String collegeName) {
        List<College> colleges = new ArrayList<>();
        collegeRepository.findByCollegeName(collegeName).forEach(colleges::add);
        List<CollegeDto> collegeDtoList = colleges.stream().map(i -> {
            CollegeDto collegeDto = new CollegeDto();
            BeanUtils.copyProperties(i, collegeDto);
            return collegeDto;
        }).collect(Collectors.toList());

        if (collegeDtoList.isEmpty()) {
            throw new ResourceNotFoundException("No College Found With Name " + collegeName);
        } else {
            return collegeDtoList;
        }
    }

    /**
     * Deletes a college by its ID.
     * @param collegeId the ID of the college to be deleted
     * @return a success message
     * @throws ResourceNotFoundException if no college is found with the specified ID
     */
    @Override
    public String deleteCollegeById(Long collegeId) {
        // Checking if the college exists
        collegeRepository.findById(collegeId)
                .orElseThrow(() -> new ResourceNotFoundException("No College Found With Id " + collegeId));

        // Deleting all scheduled programs associated with the college
        List<ProgramScheduled> list = programScheduledRepository.findByCollege_CollegeRegId(collegeId);
        for (ProgramScheduled i : list) {
            programScheduledService.deleteProgramScheduledById(i.getScheduledId());
        }

        // Deleting the college
        collegeRepository.deleteById(collegeId);
        return "deleted successfully";
    }

    /**
     * Deletes a college by its name.
     * @param collegeName the name of the college to be deleted
     * @return a success message
     * @throws ResourceNotFoundException if no college is found with the specified name
     */
    @Override
    @Transactional
    public String deleteCollegeByName(String collegeName) {
        // Fetching the colleges with the specified name
        List<College> deletedCollege = collegeRepository.findByCollegeName(collegeName);

        if (deletedCollege.isEmpty()) {
            throw new ResourceNotFoundException("No College Found With Name " + collegeName);
        } else {
            // Deleting all scheduled programs associated with each college
            for (College i : deletedCollege) {
                List<ProgramScheduled> list = programScheduledRepository
                        .findByCollege_CollegeRegId(i.getCollegeRegId());
                for (ProgramScheduled j : list) {
                    programScheduledService.deleteProgramScheduledById(j.getScheduledId());
                }
            }

            // Deleting the colleges
            collegeRepository.deleteByCollegeName(collegeName);
        }
        return "deleted successfully";
    }

    /**
     * Updates details of a college.
     * @param collegeId the ID of the college to be updated
     * @param college the new college entity with updated details
     * @return the ID of the updated college
     * @throws ResourceNotFoundException if no college is found with the specified ID
     */
    @Override
    public Long updateCollegeDetails(Long collegeId, College college) {
        // Fetching the existing college by ID
        College existedCollege = collegeRepository.findById(collegeId)
                .orElseThrow(() -> new ResourceNotFoundException("College Not Exist With Id " + collegeId));

        // Updating the college details
        existedCollege.setCollegeName(college.getCollegeName());
        existedCollege.setDescription(college.getDescription());
        Address address=college.getAddress();
        address.setAddressId(existedCollege.getAddress().getAddressId());
        addressRepository.save(address);
        
      
        collegeRepository.save(existedCollege);
        return collegeId;
    }

    /**
     * Retrieves details of a college by its ID.
     * @param collegeId the ID of the college
     * @return the DTO of the college
     * @throws ResourceNotFoundException if no college is found with the specified ID
     */
    @Override
    public CollegeDto getCollegeDetailsById(Long collegeId) {
        // Fetching the college by ID
        College college = collegeRepository.findById(collegeId)
                .orElseThrow(() -> new ResourceNotFoundException("No College Found With Id " + collegeId));

        // Copying properties to the DTO
        CollegeDto collegeDto = new CollegeDto();
        BeanUtils.copyProperties(college, collegeDto);
        return collegeDto;
    }

    /**
     * Retrieves programs offered by a specific college.
     * @param collegeId the ID of the college
     * @return a list of program DTOs
     * @throws ResourceNotFoundException if no programs are found in the specified college
     */
    @Override
    public List<ProgramDto> getProgramsByColleges(Long collegeId) {
        // Fetching the college by ID
        College college = collegeRepository.findById(collegeId)
                .orElseThrow(() -> new ResourceNotFoundException("No College Found With Id " + collegeId));
        List<Program> programs = college.getProgramList();

        // Converting program entities to DTOs
        List<ProgramDto> programDtos = programs.stream().map(i -> {
            ProgramDto programDto = new ProgramDto();
            BeanUtils.copyProperties(i, programDto);
            return programDto;
        }).collect(Collectors.toList());

        if (programDtos.isEmpty()) {
            throw new ResourceNotFoundException("No Program Found in College " + collegeId);
        }
        return programDtos;
    }

    /**
     * Searches for colleges by various search terms.
     * @param searchTerm the term to search for
     * @return a list of college DTOs matching the search terms
     */
    @Override
    public List<CollegeDto> findBySearchTerms(String searchTerm) {
        // Fetching colleges matching the search terms
        List<College> collegeDetails = collegeRepository
                .findByCollegeNameLikeOrProgramList_ProgramNameLikeOrUniversity_NameLikeOrBranchList_BranchNameLikeOrCourseList_CourseNameLike(
                        "%" + searchTerm + "%", "%" + searchTerm + "%", "%" + searchTerm + "%", "%" + searchTerm + "%",
                        "%" + searchTerm + "%");

        // Converting college entities to DTOs
        List<CollegeDto> colleges = collegeDetails.stream().map(i -> {
            CollegeDto collegeDto = new CollegeDto();
            BeanUtils.copyProperties(i, collegeDto);
            return collegeDto;
        }).collect(Collectors.toList());

        if (colleges.isEmpty()) {
            throw new ResourceNotFoundException("No College Found With Search Term " + searchTerm);
        }
        return colleges;
    }
}
