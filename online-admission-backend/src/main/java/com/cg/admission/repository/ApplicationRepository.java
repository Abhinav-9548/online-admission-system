package com.cg.admission.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.admission.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

	List<Application> findByApplicationStatus(String applicationStatus);

	String deleteByEmailId(String emailId);

	List<Application> findByEmailId(String emailId);

	List<Application> findBySchedule_scheduledId(Long scheduledId);

	String deleteBySchedule_scheduledId(Long scheduledId);

	Application findByDocument_DocumentId(Long documentId);

}
