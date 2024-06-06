package com.cg.admission.service;

import java.util.List;

import com.cg.admission.dto.AdmissionDto;
import com.cg.admission.entity.Admission;

public interface AdmissionService {

	List<AdmissionDto> viewAllAdmission();

	AdmissionDto addAdmission(Long applicationId, Long collegeId, Long programId, Long courseId, Admission admission);

	AdmissionDto getById(Long admissionId);

	String deleteById(Long admissionId);

	Long updateAdmission(Long admissionId, Admission admission);

	AdmissionDto findByApplication_ApplicationId(Long applicationId);

}
