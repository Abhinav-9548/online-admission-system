package com.cg.admission.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.admission.entity.University;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {

	University findByName(String name);

	List<University> findByAddressCity(String city);

	List<University> findByCollegeListCollegeName(String name);
}