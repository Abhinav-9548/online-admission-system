package com.cg.admission.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.admission.dto.CourseDto;
import com.cg.admission.dto.ProgramDto;
import com.cg.admission.entity.College;
import com.cg.admission.entity.Course;
import com.cg.admission.entity.Program;
import com.cg.admission.entity.ProgramScheduled;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.repository.CollegeRepository;
import com.cg.admission.repository.ProgramRepository;
import com.cg.admission.repository.ProgramScheduledRepository;
import com.cg.admission.service.ProgramScheduledService;
import com.cg.admission.service.ProgramService;

import jakarta.transaction.Transactional;

@Service
public class ProgramServiceImpl implements ProgramService {

	@Autowired
	private ProgramRepository programRepository;
	@Autowired
	private CollegeRepository collegeRepository;
	@Autowired
	private ProgramScheduledRepository programScheduledRepository;
	@Autowired
	private ProgramScheduledService programScheduledService;

	@Override
	public ProgramDto addProgram(Long id, Program program) {
		List<Program> programList = programRepository.findByProgramName(program.getProgramName());
		College college = collegeRepository.findById(id).get();

		List<Program> p1 = college.getProgramList();

		for (Program i : p1) {
			if (i.getProgramName().equalsIgnoreCase(program.getProgramName())) {
				throw new ResourceNotFoundException("Program Already Exists In the College");
			}
		}

		ProgramDto programDto = new ProgramDto();
		BeanUtils.copyProperties(program, programDto);
		college.getProgramList().add(program);
		programRepository.save(program);
		programDto.setProgramId(program.getProgramId());
		return programDto;

	}

	@Override
	public List<ProgramDto> viewAllProgramDetails() {
		List<Program> programList = programRepository.findAll();

		List<ProgramDto> programDtoList = programList.stream().map(i -> {
			ProgramDto programDto = new ProgramDto();
			BeanUtils.copyProperties(i, programDto);
			return programDto;
		}).collect(Collectors.toList());

		if (programDtoList.isEmpty()) {
			throw new ResourceNotFoundException("No program Found");
		}
		return programDtoList;

	}

	@Override
	public String deleteProgramById(Long programId) {
		programRepository.findById(programId)
				.orElseThrow(() -> new ResourceNotFoundException("No Program found with Id " + programId));

		List<ProgramScheduled> list = programScheduledRepository.findByProgram_ProgramId(programId);
		for (ProgramScheduled i : list) {
			programScheduledService.deleteProgramScheduledById(i.getScheduledId());
		}
		programRepository.deleteById(programId);
		return "Programs Deleted Succesfully";

	}

	@Override
	public ProgramDto findProgramById(Long programId) {
		Program program = programRepository.findById(programId)
				.orElseThrow(() -> new ResourceNotFoundException("No Program found with ID" + programId));
		ProgramDto programDto = new ProgramDto();
		BeanUtils.copyProperties(program, programDto);
		return programDto;

	}

	public List<ProgramDto> findProgramDetailsByProgramName(String programName) {
		List<Program> programListByProgramName = programRepository.findByProgramName(programName);

		if (programListByProgramName.isEmpty()) {
			throw new ResourceNotFoundException("No program found");
		} else {
			List<ProgramDto> programDtos = programListByProgramName.stream().map(i -> {
				ProgramDto programDto = new ProgramDto();
				BeanUtils.copyProperties(i, programDto);
				return programDto; //
			}).collect(Collectors.toList());

			return programDtos;
		}

	}

	public List<ProgramDto> findProgramDetailsByEligibility(String eligibility) {
		List<Program> programList = new ArrayList<>();
		programRepository.findByEligibility(eligibility).forEach(programList::add);

		List<ProgramDto> programDtos = programList.stream().map(i -> {
			ProgramDto programDto = new ProgramDto();
			BeanUtils.copyProperties(i, programDto);
			return programDto;
		}).collect(Collectors.toList());
		if (programDtos.isEmpty()) {
			throw new ResourceNotFoundException("No program found");
		}

		return programDtos;

	}

	@Override
	@Transactional
	public String deleteProgramByProgramName(String programName) {

		List<Program> program = programRepository.findByProgramName(programName);
		if (program.isEmpty()) {
			throw new ResourceNotFoundException("No program found with name " + programName);

		} else {

			for (Program i : program) {
				List<ProgramScheduled> list = programScheduledRepository.findByProgram_ProgramId(i.getProgramId());
				for (ProgramScheduled j : list) {
					programScheduledService.deleteProgramScheduledById(j.getScheduledId());
				}
			}
			programRepository.deleteByProgramName(programName);
		}

		return "Programs Deleted Succesfully";

	}

	@Override
	public Long updateProgramStatus(Long programId, Program program) {
		Program program2 = programRepository.findById(programId)
				.orElseThrow(() -> new ResourceNotFoundException("No program found"));

		BeanUtils.copyProperties(program, program2);
		program2.setProgramId(programId);
		programRepository.save(program2);

		return programId;

	}

	@Override
	public List<CourseDto> findCoursesByProgram(Long programId) {
		// TODO Auto-generated method stub
		Program program = programRepository.findById(programId)
				.orElseThrow(() -> new ResourceNotFoundException("No program found"));
		List<Course> courses = program.getCourses();
		List<CourseDto> courseDtos = courses.stream().map(i -> {
			CourseDto courseDto = new CourseDto();
			BeanUtils.copyProperties(i, courseDto);
			return courseDto;
		}).collect(Collectors.toList());
		if (courseDtos.isEmpty()) {
			throw new ResourceNotFoundException("No Courses Found in Program " + programId);
		}
		return courseDtos;
	}

}