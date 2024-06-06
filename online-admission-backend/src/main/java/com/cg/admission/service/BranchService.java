package com.cg.admission.service;

import java.util.List;

import com.cg.admission.entity.Branch;

public interface BranchService {

	Branch addBranch(Long collegeId, Long courseId, Branch branch);

	List<Branch> viewAllBranchDetails();

	List<Branch> getBranchDetailsByName(String branchName);

	String deleteBranchById(Long branchId);

	Branch getBranchById(Long branchId);

	String deleteBranchByName(String branchName);

	Long updateBranch(Long branchId, Branch branch);
}