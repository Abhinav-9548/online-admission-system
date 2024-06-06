
package com.cg.admission.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.cg.admission.dto.ProgramScheduledDto;
import com.cg.admission.dto.ProgramScheduledResponseDto;
import com.cg.admission.entity.Application;
import com.cg.admission.entity.Branch;
import com.cg.admission.entity.College;
import com.cg.admission.entity.Course;
import com.cg.admission.entity.Program;
import com.cg.admission.entity.ProgramScheduled;
import com.cg.admission.entity.University;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.repository.ApplicationRepository;
import com.cg.admission.repository.BranchRepository;
import com.cg.admission.repository.CollegeRepository;
import com.cg.admission.repository.CourseRepository;
import com.cg.admission.repository.ProgramRepository;
import com.cg.admission.repository.ProgramScheduledRepository;
import com.cg.admission.repository.UniversityRepository;
import com.cg.admission.service.ProgramScheduledService;

import lombok.AllArgsConstructor;

@Service

@AllArgsConstructor

public class ProgramScheduledServiceImpl implements ProgramScheduledService {

	private ProgramScheduledRepository programScheduledRepository;

	private UniversityRepository universityRepository;

	private CollegeRepository collegeRepository;

	private ProgramRepository programRepository;

	private CourseRepository courseRepository;

	private BranchRepository branchRepository;

	private ApplicationServiceImpl applicationServiceImpl;

	private ApplicationRepository applicationRepository;

	@Override

	public List<ProgramScheduledDto> viewAllProgramScheduledDetails() {

		List<ProgramScheduled> programScheduledList = programScheduledRepository.findAll();

		List<ProgramScheduledDto> programScheduledDtoList = programScheduledList.stream().map(i -> {

			ProgramScheduledDto programScheduledDto = new ProgramScheduledDto();

			ProgramScheduledResponseDto dto = new ProgramScheduledResponseDto();

			BeanUtils.copyProperties(i.getBranch(), dto);

			BeanUtils.copyProperties(i.getCourse(), dto);

			BeanUtils.copyProperties(i.getProgram(), dto);

			BeanUtils.copyProperties(i.getCollege(), dto);

			BeanUtils.copyProperties(i.getUniversity(), dto);

			programScheduledDto.setProgramScheduledResponseDto(dto);

			BeanUtils.copyProperties(i, programScheduledDto);

			return programScheduledDto;

		}).collect(Collectors.toList());

		if (programScheduledDtoList.isEmpty()) {

			throw new ResourceNotFoundException("No Program Scheduled Found");

		}

		return programScheduledDtoList;

	}

	@Override

	public String deleteProgramScheduledById(Long scheduledId) {

		ProgramScheduled deletedProgramScheduled = programScheduledRepository.findById(scheduledId)

				.orElseThrow(() -> new ResourceNotFoundException("No Program found with Id " + scheduledId));

		Long deletedScheduledId = deletedProgramScheduled.getScheduledId();

		List<Application> list = applicationRepository.findBySchedule_scheduledId(deletedScheduledId);

		for (Application i : list) {

			applicationServiceImpl.deleteApplicationByid(i.getApplicationId());

		}

		programScheduledRepository.deleteById(deletedScheduledId);

		return "Program Scheduled deleted Succesfully";

	}

	@Override

	public ProgramScheduledDto updateProgramScheduled(Long scheduledId, ProgramScheduled programScheduled) {

		// TODO Auto-generated method stub

		ProgramScheduledDto programScheduledDto = new ProgramScheduledDto();

		ProgramScheduled programScheduledExisted = programScheduledRepository.findById(scheduledId)

				.orElseThrow(() -> new ResourceNotFoundException("No Program found with Id " + scheduledId));

		programScheduledExisted.setStartDate(programScheduled.getStartDate());

		programScheduledExisted.setEndDate(programScheduled.getEndDate());

		programScheduledRepository.save(programScheduledExisted);

		BeanUtils.copyProperties(programScheduledExisted, programScheduledDto);

		return programScheduledDto;

	}

	@Override

	public ProgramScheduledDto addProgramSchedule(Long universityId, Long collegeId, Long programId, Long courseId,

			Long branchId, ProgramScheduled programScheduled) {

		ProgramScheduledDto programScheduledDto = new ProgramScheduledDto();

		University university = universityRepository.findById(universityId)

				.orElseThrow(() -> new ResourceNotFoundException("No university found with Id " + universityId));

		College college = collegeRepository.findById(collegeId)

				.orElseThrow(() -> new ResourceNotFoundException("No college found with Id " + collegeId));

		Program program = programRepository.findById(programId)

				.orElseThrow(() -> new ResourceNotFoundException("No program found with Id " + universityId));

		Course course = courseRepository.findById(courseId)

				.orElseThrow(() -> new ResourceNotFoundException("No course found with Id " + courseId));

		Branch branch = branchRepository.findById(branchId)

				.orElseThrow(() -> new ResourceNotFoundException("No branch found with Id " + branchId));

		programScheduled.setBranch(branch);

		programScheduled.setCollege(college);

		programScheduled.setUniversity(university);

		programScheduled.setCourse(course);

		programScheduled.setProgram(program);

		programScheduledRepository.save(programScheduled);

		ProgramScheduledResponseDto dto = new ProgramScheduledResponseDto();

		BeanUtils.copyProperties(programScheduled.getBranch(), dto);

		BeanUtils.copyProperties(programScheduled.getProgram(), dto);
		BeanUtils.copyProperties(programScheduled.getCourse(), dto);

		BeanUtils.copyProperties(programScheduled.getCollege(), dto);

		BeanUtils.copyProperties(programScheduled.getUniversity(), dto);

		programScheduledDto.setProgramScheduledResponseDto(dto);

		BeanUtils.copyProperties(programScheduled, programScheduledDto);

		return programScheduledDto;

	}

	@Override

	public ProgramScheduledDto findProgramScheduledById(Long scheduledId) {

		ProgramScheduled programScheduled = programScheduledRepository.findById(scheduledId)

				.orElseThrow(() -> new ResourceNotFoundException("No Program Scheduled present with ID" + scheduledId));

		ProgramScheduledDto programScheduledDto = new ProgramScheduledDto();

		BeanUtils.copyProperties(programScheduled, programScheduledDto);

		ProgramScheduledResponseDto dto = new ProgramScheduledResponseDto();

		BeanUtils.copyProperties(programScheduled.getBranch(), dto);

		BeanUtils.copyProperties(programScheduled.getCourse(), dto);

		BeanUtils.copyProperties(programScheduled.getProgram(), dto);

		BeanUtils.copyProperties(programScheduled.getCollege(), dto);

		BeanUtils.copyProperties(programScheduled.getUniversity(), dto);

		programScheduledDto.setProgramScheduledResponseDto(dto);

		return programScheduledDto;

	}

	@Override

	public List<ProgramScheduledDto> findProgramScheduledByCollege_CollegeName(String collegeName) {

		List<ProgramScheduled> programScheduledListByCollegeName = programScheduledRepository

				.findProgramScheduledByCollege_CollegeName(collegeName);

		List<ProgramScheduledDto> programScheduledDtos = programScheduledListByCollegeName.stream().map(i -> {

			ProgramScheduledDto programScheduledDto = new ProgramScheduledDto();

			ProgramScheduledResponseDto dto = new ProgramScheduledResponseDto();

			BeanUtils.copyProperties(i.getBranch(), dto);

			BeanUtils.copyProperties(i.getCourse(), dto);

			BeanUtils.copyProperties(i.getProgram(), dto);

			BeanUtils.copyProperties(i.getCollege(), dto);

			BeanUtils.copyProperties(i.getUniversity(), dto);

			programScheduledDto.setProgramScheduledResponseDto(dto);

			BeanUtils.copyProperties(i, programScheduledDto);

			return programScheduledDto; //

		}).collect(Collectors.toList());

		if (programScheduledDtos.isEmpty()) {

			throw new ResourceNotFoundException("No Program Scheduled for " + collegeName);

		}

		return programScheduledDtos;

	}

	@Override

	public List<ProgramScheduledDto> findProgramScheduledByStartDate(LocalDate startDate) {

		List<ProgramScheduled> programList = programScheduledRepository.findProgramScheduledByStartDate(startDate);

		List<ProgramScheduledDto> programScheduledDtos = programList.stream().map(i -> {

			ProgramScheduledDto programScheduledDto = new ProgramScheduledDto();

			ProgramScheduledResponseDto dto = new ProgramScheduledResponseDto();

			BeanUtils.copyProperties(i.getBranch(), dto);

			BeanUtils.copyProperties(i.getCourse(), dto);

			BeanUtils.copyProperties(i.getProgram(), dto);

			BeanUtils.copyProperties(i.getCollege(), dto);

			BeanUtils.copyProperties(i.getUniversity(), dto);

			programScheduledDto.setProgramScheduledResponseDto(dto);

			BeanUtils.copyProperties(i, programScheduledDto);

			return programScheduledDto; //

		}).collect(Collectors.toList());

		BeanUtils.copyProperties(programList, programScheduledDtos);

		if (programScheduledDtos.isEmpty()) {

			throw new ResourceNotFoundException("No Program Scheduled by StartDate " + startDate);

		}

		return programScheduledDtos;

	}

	@Override

	public List<ProgramScheduledDto> findByCollege_CollegeRegId(Long collegeRegId) {

		List<ProgramScheduled> programScheduledListByCollegeId = programScheduledRepository
				.findByCollege_CollegeRegId(collegeRegId);

		List<ProgramScheduledDto> programScheduledDtos = programScheduledListByCollegeId.stream().map(i -> {

			ProgramScheduledDto programScheduledDto = new ProgramScheduledDto();

			ProgramScheduledResponseDto dto = new ProgramScheduledResponseDto();

			BeanUtils.copyProperties(i.getBranch(), dto);

			BeanUtils.copyProperties(i.getCourse(), dto);

			BeanUtils.copyProperties(i.getProgram(), dto);

			BeanUtils.copyProperties(i.getCollege(), dto);

			BeanUtils.copyProperties(i.getUniversity(), dto);

			programScheduledDto.setProgramScheduledResponseDto(dto);

			BeanUtils.copyProperties(i, programScheduledDto);

			return programScheduledDto; //

		}).collect(Collectors.toList());

		if (programScheduledDtos.isEmpty()) {

			throw new ResourceNotFoundException("No Program Scheduled for " + collegeRegId);

		}

		return programScheduledDtos;

	}

	@Override

	public List<ProgramScheduledDto> findByBranch_BranchId(Long branchId) {

		List<ProgramScheduled> programScheduledListByCollegeId = programScheduledRepository
				.findByBranch_BranchId(branchId);

		List<ProgramScheduledDto> programScheduledDtos = programScheduledListByCollegeId.stream().map(i -> {

			ProgramScheduledDto programScheduledDto = new ProgramScheduledDto();

			ProgramScheduledResponseDto dto = new ProgramScheduledResponseDto();

			BeanUtils.copyProperties(i.getBranch(), dto);

			BeanUtils.copyProperties(i.getProgram(), dto);
			BeanUtils.copyProperties(i.getCourse(), dto);

			BeanUtils.copyProperties(i.getCollege(), dto);

			BeanUtils.copyProperties(i.getUniversity(), dto);

			programScheduledDto.setProgramScheduledResponseDto(dto);

			BeanUtils.copyProperties(i, programScheduledDto);

			return programScheduledDto; //

		}).collect(Collectors.toList());

		if (programScheduledDtos.isEmpty()) {

			throw new ResourceNotFoundException("No Program Scheduled for " + branchId);

		}

		return programScheduledDtos;

	}

	@Override

	public List<ProgramScheduledDto> findByProgram_ProgramId(Long programId) {

		List<ProgramScheduled> programScheduledListByCollegeId = programScheduledRepository
				.findByProgram_ProgramId(programId);

		List<ProgramScheduledDto> programScheduledDtos = programScheduledListByCollegeId.stream().map(i -> {

			ProgramScheduledDto programScheduledDto = new ProgramScheduledDto();

			ProgramScheduledResponseDto dto = new ProgramScheduledResponseDto();

			BeanUtils.copyProperties(i.getBranch(), dto);

			BeanUtils.copyProperties(i.getProgram(), dto);
			BeanUtils.copyProperties(i.getCourse(), dto);

			BeanUtils.copyProperties(i.getCollege(), dto);

			BeanUtils.copyProperties(i.getUniversity(), dto);

			programScheduledDto.setProgramScheduledResponseDto(dto);

			BeanUtils.copyProperties(i, programScheduledDto);

			return programScheduledDto; //

		}).collect(Collectors.toList());

		if (programScheduledDtos.isEmpty()) {

			throw new ResourceNotFoundException("No Program Scheduled for " + programId);

		}

		return programScheduledDtos;

	}

	@Override

	public List<ProgramScheduledDto> findByCourse_CourseId(Long courseId) {

		List<ProgramScheduled> programScheduledListByCollegeId = programScheduledRepository
				.findByCourse_CourseId(courseId);

		List<ProgramScheduledDto> programScheduledDtos = programScheduledListByCollegeId.stream().map(i -> {

			ProgramScheduledDto programScheduledDto = new ProgramScheduledDto();

			ProgramScheduledResponseDto dto = new ProgramScheduledResponseDto();

			BeanUtils.copyProperties(i.getBranch(), dto);

			

			BeanUtils.copyProperties(i.getProgram(), dto);
			BeanUtils.copyProperties(i.getCourse(), dto);

			BeanUtils.copyProperties(i.getCollege(), dto);

			BeanUtils.copyProperties(i.getUniversity(), dto);

			programScheduledDto.setProgramScheduledResponseDto(dto);

			BeanUtils.copyProperties(i, programScheduledDto);

			return programScheduledDto; //

		}).collect(Collectors.toList());

		if (programScheduledDtos.isEmpty()) {

			throw new ResourceNotFoundException("No Program Scheduled for " + courseId);

		}

		return programScheduledDtos;

	}

	@Override

	public List<ProgramScheduledDto> findByUniversity_UniversityId(Long universityId) {

		List<ProgramScheduled> programScheduledListByCollegeId = programScheduledRepository
				.findByUniversity_UniversityId(universityId);

		List<ProgramScheduledDto> programScheduledDtos = programScheduledListByCollegeId.stream().map(i -> {

			ProgramScheduledDto programScheduledDto = new ProgramScheduledDto();

			ProgramScheduledResponseDto dto = new ProgramScheduledResponseDto();

			BeanUtils.copyProperties(i.getBranch(), dto);

			BeanUtils.copyProperties(i.getCourse(), dto);

			BeanUtils.copyProperties(i.getProgram(), dto);

			BeanUtils.copyProperties(i.getCollege(), dto);

			BeanUtils.copyProperties(i.getUniversity(), dto);

			programScheduledDto.setProgramScheduledResponseDto(dto);

			BeanUtils.copyProperties(i, programScheduledDto);

			return programScheduledDto;

		}).collect(Collectors.toList());

		if (programScheduledDtos.isEmpty()) {

			throw new ResourceNotFoundException("No Program Scheduled for " + universityId);

		}

		return programScheduledDtos;

	}

}