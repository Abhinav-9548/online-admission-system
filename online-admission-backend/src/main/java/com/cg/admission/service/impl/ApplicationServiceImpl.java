package com.cg.admission.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.admission.dto.ApplicationDto;
import com.cg.admission.dto.ProgramScheduledDto;
import com.cg.admission.dto.ProgramScheduledResponseDto;
import com.cg.admission.entity.Admission;
import com.cg.admission.entity.Application;
import com.cg.admission.entity.Payment;
import com.cg.admission.entity.ProgramScheduled;
import com.cg.admission.exceptions.ResourceAlreadyPresentException;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.repository.AdmissionRepository;
import com.cg.admission.repository.ApplicationRepository;
import com.cg.admission.repository.PaymentRepository;
import com.cg.admission.repository.ProgramScheduledRepository;
import com.cg.admission.service.AdmissionService;
import com.cg.admission.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepository; // Repository to handle Application entity CRUD operations

	@Autowired
	private ProgramScheduledRepository programScheduledRepository; // Repository to handle ProgramScheduled entity CRUD
																	// operations

	@Autowired
	private PaymentRepository paymentRepository; // Repository to handle Payment entity CRUD operations

	@Autowired
	private AdmissionRepository admissionRepository; // Repository to handle Admission entity CRUD operations

	@Autowired
	private AdmissionService admissionService; // Service to handle Admission related operations

	// Method to view all application details
	@Override
	public List<ApplicationDto> viewAllApplicationDetails() {
		List<Application> applicationList = applicationRepository.findAll(); // Fetch all applications

		List<ApplicationDto> applicationDtoList = applicationList.stream().map(i -> {
			ApplicationDto applicationDto = new ApplicationDto();
			ProgramScheduledDto programScheduledDto = new ProgramScheduledDto();
			ProgramScheduledResponseDto dto = new ProgramScheduledResponseDto();
			// Copying properties from ProgramScheduled to DTO
			BeanUtils.copyProperties(i.getSchedule().getBranch(), dto);
			BeanUtils.copyProperties(i.getSchedule().getCourse(), dto);
			BeanUtils.copyProperties(i.getSchedule().getProgram(), dto);
			BeanUtils.copyProperties(i.getSchedule().getCollege(), dto);
			BeanUtils.copyProperties(i.getSchedule().getUniversity(), dto);

			programScheduledDto.setProgramScheduledResponseDto(dto);

			BeanUtils.copyProperties(i.getSchedule(), programScheduledDto);

			applicationDto.setSchedule(programScheduledDto);
			BeanUtils.copyProperties(i, applicationDto);
			return applicationDto;
		}).collect(Collectors.toList());

		if (applicationDtoList.isEmpty()) {
			throw new ResourceNotFoundException("No Applications Found"); // Throw exception if no applications found
		}

		return applicationDtoList;
	}

	// Method to get application details by email
	@Override
	public List<ApplicationDto> getApplicationDetailsByEmail(String emailId) {
		List<Application> application = applicationRepository.findByEmailId(emailId); // Fetch applications by email

		List<ApplicationDto> applicationDtoList = application.stream().map(i -> {
			ApplicationDto applicationDto = new ApplicationDto();
			ProgramScheduledDto programScheduledDto = new ProgramScheduledDto();
			ProgramScheduledResponseDto dto = new ProgramScheduledResponseDto();
			// Copying properties from ProgramScheduled to DTO
			ProgramScheduled programScheduled = i.getSchedule();

			BeanUtils.copyProperties(programScheduled.getCourse(), dto);
			BeanUtils.copyProperties(programScheduled.getBranch(), dto);
			BeanUtils.copyProperties(programScheduled.getProgram(), dto);
			BeanUtils.copyProperties(programScheduled.getCollege(), dto);
			BeanUtils.copyProperties(programScheduled.getUniversity(), dto);

			programScheduledDto.setProgramScheduledResponseDto(dto);

			BeanUtils.copyProperties(i.getSchedule(), programScheduledDto);

			applicationDto.setSchedule(programScheduledDto);
			BeanUtils.copyProperties(i, applicationDto);
			return applicationDto;
		}).collect(Collectors.toList());

		if (applicationDtoList.isEmpty()) {
			throw new ResourceNotFoundException("No Applications Found With Email Id " + emailId); // Throw exception if
																									// no applications
																									// found
		}

		return applicationDtoList;
	}

	// Method to add an application
	@Override
	public ApplicationDto addApplication(Long programscheduleId, Application application) {
		ProgramScheduled programScheduled = programScheduledRepository.findById(programscheduleId)
				.orElseThrow(() -> new ResourceAlreadyPresentException("No program Found")); // Fetch ProgramScheduled
																								// by ID or throw
																								// exception

		List<Application> applications = applicationRepository.findByEmailId(application.getEmailId()); // Fetch
																										// applications
																										// by email

		for (Application i : applications) {
			if (i.getSchedule().getScheduledId() == programscheduleId) {
				throw new ResourceAlreadyPresentException("You have Already Applied For the Course"); // Throw exception
																										// if
																										// application
																										// already
																										// exists
			}
		}

		application.setSchedule(programScheduled); // Set the schedule for the application
		applicationRepository.save(application); // Save the application

		ApplicationDto applicationDto = new ApplicationDto();
		ProgramScheduledDto programScheduledDto = new ProgramScheduledDto();
		ProgramScheduledResponseDto dto = new ProgramScheduledResponseDto();
		// Copying properties from ProgramScheduled to DTO
		BeanUtils.copyProperties(application.getSchedule().getBranch(), dto);
		BeanUtils.copyProperties(application.getSchedule().getCourse(), dto);
		BeanUtils.copyProperties(application.getSchedule().getProgram(), dto);
		BeanUtils.copyProperties(application.getSchedule().getCollege(), dto);
		BeanUtils.copyProperties(application.getSchedule().getUniversity(), dto);

		programScheduledDto.setProgramScheduledResponseDto(dto);

		BeanUtils.copyProperties(application.getSchedule(), programScheduledDto);

		applicationDto.setSchedule(programScheduledDto);
		BeanUtils.copyProperties(application, applicationDto);
		return applicationDto;
	}

	// Method to get application details by status
	@Override
	public List<ApplicationDto> getApplicationDetailsByStatus(String status) {
		List<Application> applicationList = applicationRepository.findByApplicationStatus(status); // Fetch applications
																									// by status

		List<ApplicationDto> applicationDtoList = applicationList.stream().map(i -> {
			ApplicationDto applicationDto = new ApplicationDto();
			ProgramScheduledDto programScheduledDto = new ProgramScheduledDto();
			ProgramScheduledResponseDto dto = new ProgramScheduledResponseDto();
			// Copying properties from ProgramScheduled to DTO
			BeanUtils.copyProperties(i.getSchedule().getBranch(), dto);
			BeanUtils.copyProperties(i.getSchedule().getCourse(), dto);
			BeanUtils.copyProperties(i.getSchedule().getProgram(), dto);
			BeanUtils.copyProperties(i.getSchedule().getCollege(), dto);
			BeanUtils.copyProperties(i.getSchedule().getUniversity(), dto);

			programScheduledDto.setProgramScheduledResponseDto(dto);

			BeanUtils.copyProperties(i.getSchedule(), programScheduledDto);

			applicationDto.setSchedule(programScheduledDto);
			BeanUtils.copyProperties(i, applicationDto);
			return applicationDto;
		}).collect(Collectors.toList());

		if (applicationDtoList.isEmpty()) {
			throw new ResourceNotFoundException("No Applications Found With Status " + status); // Throw exception if no
																								// applications found
		}

		return applicationDtoList;
	}

	// Method to delete an application by ID
	@Override
	@Transactional
	public String deleteApplicationByid(Long applicationId) {
		Application application = applicationRepository.findById(applicationId)
				.orElseThrow(() -> new ResourceNotFoundException("No Application Found with ID" + applicationId)); // Fetch
																													// application
																													// by
																													// ID
																													// or
																													// throw
																													// exception

		String a = paymentRepository.deletePaymentByApplication_ApplicationId(applicationId); // Delete related payments
		admissionRepository.deleteByApplication_ApplicationId(applicationId); // Delete related admissions
		application.setSchedule(null); // Set schedule to null

		applicationRepository.deleteById(applicationId); // Delete application

		return "Object Deleted Successfully"; // Return success message
	}

	// Method to delete an application by email
	@Override
	@Transactional
	public String deleteApplicationByEmail(String emailId) {
		List<Application> application = applicationRepository.findByEmailId(emailId); // Fetch applications by email

		if (application.isEmpty()) {
			throw new ResourceNotFoundException("No Application Found With Email " + emailId); // Throw exception if no
																								// applications found
		} else {
			for (Application i : application) {
				Payment payment = paymentRepository.findPaymentByApplication_ApplicationId(i.getApplicationId()); // Fetch
																													// related
																													// payments
				i.setSchedule(null); // Set schedule to null
				paymentRepository.delete(payment); // Delete payments
			}
			applicationRepository.deleteByEmailId(emailId); // Delete applications
		}
		return "Application Deleted Successfully"; // Return success message
	}

	// Method to get an application by ID
	@Override
	public ApplicationDto getApplicationByid(Long applicationId) {
		Application application = applicationRepository.findById(applicationId)
				.orElseThrow(() -> new ResourceNotFoundException("No Application Found With ID " + applicationId)); // Fetch
																													// application
																													// by
																													// ID
																													// or
																													// throw
																													// exception
		ApplicationDto applicationDto = new ApplicationDto();
		ProgramScheduledDto programScheduledDto = new ProgramScheduledDto();
		ProgramScheduledResponseDto dto = new ProgramScheduledResponseDto();
		// Copying properties from ProgramScheduled to DTO
		BeanUtils.copyProperties(application.getSchedule().getBranch(), dto);
		BeanUtils.copyProperties(application.getSchedule().getCourse(), dto);
		BeanUtils.copyProperties(application.getSchedule().getProgram(), dto);
		BeanUtils.copyProperties(application.getSchedule().getCollege(), dto);
		BeanUtils.copyProperties(application.getSchedule().getUniversity(), dto);

		programScheduledDto.setProgramScheduledResponseDto(dto);

		BeanUtils.copyProperties(application.getSchedule(), programScheduledDto);

		applicationDto.setSchedule(programScheduledDto);
		BeanUtils.copyProperties(application, applicationDto);

		return applicationDto; // Return application DTO
	}

	// Method to update application status
	@Override
	public Long updateApplicationStatus(Long id, Application application) {
		Application app = applicationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Application Found With ID" + id)); // Fetch
																										// application
																										// by ID or
																										// throw
																										// exception

		app.setApplicationStatus(application.getApplicationStatus()); // Update application status
		if (application.getApplicationStatus().equalsIgnoreCase("Accepted")) {
			// If application is accepted, create a new admission
			Admission admission = new Admission();
			admission.setEmailId(app.getEmailId());
			admission.setAdmissionStatus("Pending");
			admission.setYear(String.valueOf(app.getSchedule().getStartDate().getYear()));
			admissionService.addAdmission(app.getApplicationId(), app.getSchedule().getCollege().getCollegeRegId(),
					app.getSchedule().getProgram().getProgramId(), app.getSchedule().getCourse().getCourseId(),
					admission);
		}

		app.setDateOfInterview(application.getDateOfInterview()); // Update date of interview
		applicationRepository.save(app); // Save updated application

		return app.getApplicationId(); // Return application ID
	}

}
