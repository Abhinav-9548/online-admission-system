package com.cg.admission.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.admission.entity.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
	List<Branch> findByBranchName(String branchName);

	Long deleteByBranchName(String branchName);
}