package com.cg.admission.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.admission.dto.CollegeDto;
import com.cg.admission.dto.UniversityDto;
import com.cg.admission.entity.Address;
import com.cg.admission.entity.College;
import com.cg.admission.entity.ProgramScheduled;
import com.cg.admission.entity.University;
import com.cg.admission.exceptions.ResourceAlreadyPresentException;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.repository.AddressRepository;
import com.cg.admission.repository.ProgramScheduledRepository;
import com.cg.admission.repository.UniversityRepository;
import com.cg.admission.service.ProgramScheduledService;
import com.cg.admission.service.UniversityService;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    private UniversityRepository universityRepository;
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private ProgramScheduledRepository programScheduledRepository;
    
    @Autowired
    private ProgramScheduledService programScheduledService;

    /**
     * Retrieves all universities from the repository and converts them to UniversityDto.
     * @return List of UniversityDto
     */
    @Override
    public List<UniversityDto> getAllUniversity() {
        List<University> universityList = universityRepository.findAll();
        List<UniversityDto> universityDtoList = universityList.stream().map(i -> {
            UniversityDto universityDto = new UniversityDto();
            BeanUtils.copyProperties(i, universityDto);
            return universityDto;
        }).collect(Collectors.toList());

        if (universityDtoList.isEmpty()) {
            throw new ResourceNotFoundException("No University found!");
        } else {
            return universityDtoList;
        }
    }

    /**
     * Retrieves a university by its ID and converts it to UniversityDto.
     * @param id The ID of the university to retrieve.
     * @return UniversityDto
     */
    @Override
    public UniversityDto getUniversityById(Long id) {
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No University found with Id " + id));
        UniversityDto universityDto = new UniversityDto();
        BeanUtils.copyProperties(university, universityDto);
        return universityDto;
    }

    /**
     * Adds a new university to the repository.
     * @param university The University entity to add.
     * @return UniversityDto of the added university.
     */
    @Override
    public UniversityDto addUniversity(University university) {
        University existingUniversity = universityRepository.findByName(university.getName());
        if (existingUniversity != null) {
            throw new ResourceAlreadyPresentException("University already exists!");
        } else {
            addressRepository.save(university.getAddress());
            universityRepository.save(university);
            UniversityDto universityDto = new UniversityDto();
            BeanUtils.copyProperties(university, universityDto);
            return universityDto;
        }
    }

    /**
     * Retrieves universities by city and converts them to UniversityDto.
     * @param city The city to search for universities.
     * @return List of UniversityDto
     */
    @Override
    public List<UniversityDto> getUniversityDetailsByCity(String city) {
        List<University> universityList = universityRepository.findByAddressCity(city);
        if (universityList.isEmpty()) {
            throw new ResourceNotFoundException("No University found with City " + city);
        } else {
            List<UniversityDto> universityDtoList = universityList.stream().map(i -> {
                UniversityDto universityDto = new UniversityDto();
                BeanUtils.copyProperties(i, universityDto);
                return universityDto;
            }).collect(Collectors.toList());
            return universityDtoList;
        }
    }

    /**
     * Retrieves universities by college name and converts them to UniversityDto.
     * @param collegeName The college name to search for universities.
     * @return List of UniversityDto
     */
    @Override
    public List<UniversityDto> getUniversityDetailsByCollegeName(String collegeName) {
        List<University> universityList = universityRepository.findByCollegeListCollegeName(collegeName);
        if (universityList.isEmpty()) {
            throw new ResourceNotFoundException("College is not Registered with any university!");
        } else {
            List<UniversityDto> universityDtoList = universityList.stream().map(i -> {
                UniversityDto universityDto = new UniversityDto();
                BeanUtils.copyProperties(i, universityDto);
                return universityDto;
            }).collect(Collectors.toList());
            return universityDtoList;
        }
    }

    /**
     * Updates a university by its ID.
     * @param id The ID of the university to update.
     * @param university The updated University entity.
     * @return The ID of the updated university.
     */
    @Override
    public Long updateUniversity(Long id, University university) {
        University existingUniversity = universityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No University found with Id " + id));

        // Update fields
        existingUniversity.setName(university.getName());
        
        // Update address
        Address address = university.getAddress();
        address.setAddressId(existingUniversity.getAddress().getAddressId());
        addressRepository.save(address);
        
        // Save updated university
        universityRepository.save(existingUniversity);
        
        return id;
    }

    /**
     * Deletes a university by its ID, including its associated scheduled programs.
     * @param id The ID of the university to delete.
     * @return A success message.
     */
    @Override
    @Transactional
    public String deleteUniversityById(Long id) {
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No University found with Id " + id));

        List<ProgramScheduled> scheduledPrograms = programScheduledRepository.findByUniversity_UniversityId(id);
        for (ProgramScheduled program : scheduledPrograms) {
            programScheduledService.deleteProgramScheduledById(program.getScheduledId());
        }
        universityRepository.delete(university);
        return "University Deleted successfully";
    }

    /**
     * Retrieves colleges by university ID and converts them to CollegeDto.
     * @param universityId The ID of the university to search for colleges.
     * @return List of CollegeDto
     */
    @Override
    public List<CollegeDto> findCollegeByUniverisity(Long universityId) {
        University university = universityRepository.findById(universityId)
                .orElseThrow(() -> new ResourceNotFoundException("No University found with Id " + universityId));

        List<College> colleges = university.getCollegeList();

        List<CollegeDto> collegeDtoList = colleges.stream().map(i -> {
            CollegeDto collegeDto = new CollegeDto();
            BeanUtils.copyProperties(i, collegeDto);
            return collegeDto;
        }).collect(Collectors.toList());

        if (collegeDtoList.isEmpty()) {
            throw new ResourceNotFoundException("No College Found in University " + universityId);
        }
        return collegeDtoList;
    }
}
