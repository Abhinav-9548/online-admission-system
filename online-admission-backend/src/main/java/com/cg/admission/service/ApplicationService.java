package com.cg.admission.service;

import java.util.List;

import com.cg.admission.dto.ApplicationDto;
import com.cg.admission.entity.Application;

public interface ApplicationService {

	List<ApplicationDto> viewAllApplicationDetails();

	List<ApplicationDto> getApplicationDetailsByEmail(String emailId);

	ApplicationDto addApplication(Long programscheduleId, Application application);

	List<ApplicationDto> getApplicationDetailsByStatus(String status);

	String deleteApplicationByid(Long applicationId);

	String deleteApplicationByEmail(String emailId);

	ApplicationDto getApplicationByid(Long applicationId);

	Long updateApplicationStatus(Long Id, Application application);

}
