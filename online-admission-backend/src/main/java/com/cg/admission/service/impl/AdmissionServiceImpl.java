package com.cg.admission.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.admission.dto.AdmissionDto;
import com.cg.admission.dto.ApplicationDto;
import com.cg.admission.dto.CollegeDto;
import com.cg.admission.dto.CourseDto;
import com.cg.admission.dto.ProgramDto;
import com.cg.admission.entity.Admission;
import com.cg.admission.entity.Application;
import com.cg.admission.entity.College;
import com.cg.admission.entity.Course;
import com.cg.admission.entity.Program;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.repository.AdmissionRepository;
import com.cg.admission.repository.ApplicationRepository;
import com.cg.admission.repository.CollegeRepository;
import com.cg.admission.repository.CourseRepository;
import com.cg.admission.repository.ProgramRepository;
import com.cg.admission.service.AdmissionService;

@Service
public class AdmissionServiceImpl implements AdmissionService {

	@Autowired
	private AdmissionRepository admissionRepository;

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private CollegeRepository collegeRepository;

	@Autowired
	private ProgramRepository programRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Override
	public List<AdmissionDto> viewAllAdmission() {
		// Retrieve all admissions from the repository
		List<Admission> admissions = admissionRepository.findAll();

		// Convert each Admission entity to an AdmissionDto
		List<AdmissionDto> admissionDtos = admissions.stream().map(i -> {
			AdmissionDto admissionDto = new AdmissionDto();
			ApplicationDto applicationDto = new ApplicationDto();
			BeanUtils.copyProperties(i.getApplication(), applicationDto);
			admissionDto.setApplication(applicationDto);

			CollegeDto collegeDto = new CollegeDto();
			BeanUtils.copyProperties(i.getCollege(), collegeDto);
			admissionDto.setCollege(collegeDto);

			ProgramDto programDto = new ProgramDto();
			BeanUtils.copyProperties(i.getProgram(), programDto);
			admissionDto.setProgram(programDto);

			CourseDto courseDto = new CourseDto();
			BeanUtils.copyProperties(i.getCourse(), courseDto);
			admissionDto.setCourse(courseDto);

			BeanUtils.copyProperties(i, admissionDto);
			return admissionDto;
		}).collect(Collectors.toList());

		return admissionDtos;
	}

	@Override
	public AdmissionDto addAdmission(Long applicationId, Long collegeId, Long programId, Long courseId,
			Admission admission) {
		// Retrieve related entities and set them in the admission entity
		Application application = applicationRepository.findById(applicationId).get();
		admission.setApplication(application);

		College college = collegeRepository.findById(collegeId).get();
		admission.setCollege(college);

		Program program = programRepository.findById(programId).get();
		admission.setProgram(program);

		Course course = courseRepository.findById(courseId).get();
		admission.setCourse(course);

		// Save the admission entity
		admissionRepository.save(admission);

		// Convert the saved entity to DTO and return
		AdmissionDto admissionDto = new AdmissionDto();
		BeanUtils.copyProperties(admission, admissionDto);
		return admissionDto;
	}

	@Override
	public AdmissionDto getById(Long admissionId) {
		// Retrieve the admission entity by ID
		Admission admission = admissionRepository.findById(admissionId)
				.orElseThrow(() -> new ResourceNotFoundException("No admission found with admissionId " + admissionId));

		// Convert the entity to DTO and return
		AdmissionDto admissionDto = new AdmissionDto();
		BeanUtils.copyProperties(admission, admissionDto);
		return admissionDto;
	}

	@Override
	public String deleteById(Long admissionId) {
		// Check if the admission entity exists
		admissionRepository.findById(admissionId)
				.orElseThrow(() -> new ResourceNotFoundException("No admission found with admissionId " + admissionId));

		// Delete the admission entity
		admissionRepository.deleteById(admissionId);
		return "Admission deleted successfully";
	}

	@Override
	public Long updateAdmission(Long admissionId, Admission admission) {
		// Retrieve the admission entity by ID
		Admission existingAdmission = admissionRepository.findById(admissionId)
				.orElseThrow(() -> new ResourceNotFoundException("No admission found with admissionId " + admissionId));

		// Update the admission status and save the entity
		existingAdmission.setAdmissionStatus(admission.getAdmissionStatus());
		admissionRepository.save(existingAdmission);

		return admissionId;
	}

	@Override
	public AdmissionDto findByApplication_ApplicationId(Long applicationId) {
		// Retrieve the admission entity by application ID
		Admission admission = admissionRepository.findByApplication_ApplicationId(applicationId);

		if (admission == null) {
			throw new ResourceNotFoundException("No admission found with applicationId " + applicationId);
		}

		// Convert the entity to DTO and return
		AdmissionDto admissionDto = new AdmissionDto();
		ApplicationDto applicationDto = new ApplicationDto();
		BeanUtils.copyProperties(admission.getApplication(), applicationDto);
		admissionDto.setApplication(applicationDto);

		CollegeDto collegeDto = new CollegeDto();
		BeanUtils.copyProperties(admission.getCollege(), collegeDto);
		admissionDto.setCollege(collegeDto);

		ProgramDto programDto = new ProgramDto();
		BeanUtils.copyProperties(admission.getProgram(), programDto);
		admissionDto.setProgram(programDto);

		CourseDto courseDto = new CourseDto();
		BeanUtils.copyProperties(admission.getCourse(), courseDto);
		admissionDto.setCourse(courseDto);

		BeanUtils.copyProperties(admission, admissionDto);
		return admissionDto;
	}
}
