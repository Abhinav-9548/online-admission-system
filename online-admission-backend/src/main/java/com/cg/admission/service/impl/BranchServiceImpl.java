package com.cg.admission.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.admission.entity.Branch;
import com.cg.admission.entity.College;
import com.cg.admission.entity.Course;
import com.cg.admission.entity.ProgramScheduled;
import com.cg.admission.exceptions.ResourceAlreadyPresentException;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.repository.BranchRepository;
import com.cg.admission.repository.CollegeRepository;
import com.cg.admission.repository.CourseRepository;
import com.cg.admission.repository.ProgramScheduledRepository;
import com.cg.admission.service.BranchService;
import com.cg.admission.service.ProgramScheduledService;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private CollegeRepository collegeRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProgramScheduledRepository programScheduledRepository;

    @Autowired
    private ProgramScheduledService programScheduledService;

    /**
     * Adds a new branch to a specified college and course.
     * @param collegeId the ID of the college to which the branch is to be added
     * @param courseId the ID of the course to which the branch is to be added
     * @param branch the branch entity to be added
     * @return the added branch entity
     * @throws ResourceAlreadyPresentException if the branch already exists in the course
     */
    @Override
    public Branch addBranch(Long collegeId, Long courseId, Branch branch) {
        // Fetching the college and course by ID
        College college = collegeRepository.findById(collegeId).get();
        Course course = courseRepository.findById(courseId).get();

        // Checking if the branch already exists in the course
        List<Branch> b = course.getBranches();
        for (Branch i : b) {
            if (i.getBranchName().equalsIgnoreCase(branch.getBranchName())) {
                throw new ResourceAlreadyPresentException("Branch Already Exist");
            }
        }

        // Adding the branch to the college and course
        college.getBranchList().add(branch);
        course.getBranches().add(branch);
        branchRepository.save(branch);

        return branch;
    }

    /**
     * Retrieves details of all branches.
     * @return a list of all branch entities
     * @throws ResourceNotFoundException if no branches are found
     */
    @Override
    public List<Branch> viewAllBranchDetails() {
        List<Branch> branches = branchRepository.findAll();
        if (branches.isEmpty()) {
            throw new ResourceNotFoundException("No Branches Found");
        } else {
            return branches;
        }
    }

    /**
     * Retrieves branch details by branch name.
     * @param branchName the name of the branch
     * @return a list of branch entities with the specified name
     * @throws ResourceNotFoundException if no branches are found with the specified name
     */
    @Override
    public List<Branch> getBranchDetailsByName(String branchName) {
        List<Branch> branches = new ArrayList<>();
        branchRepository.findByBranchName(branchName).forEach(branches::add);
        if (branches.isEmpty()) {
            throw new ResourceNotFoundException("No Branch Found for " + branchName);
        } else {
            return branches;
        }
    }

    /**
     * Deletes a branch by its ID.
     * @param branchId the ID of the branch to be deleted
     * @return a success message
     * @throws ResourceNotFoundException if no branch is found with the specified ID
     */
    @Override
    public String deleteBranchById(Long branchId) {
        // Checking if the branch exists
        branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("No Branch Found with Id " + branchId));

        // Deleting all scheduled programs associated with the branch
        List<ProgramScheduled> list = programScheduledRepository.findByBranch_BranchId(branchId);
        for (ProgramScheduled j : list) {
            programScheduledService.deleteProgramScheduledById(j.getScheduledId());
        }

        // Deleting the branch
        branchRepository.deleteById(branchId);
        return "deleted successfully";
    }

    /**
     * Retrieves details of a branch by its ID.
     * @param branchId the ID of the branch
     * @return the branch entity with the specified ID
     * @throws ResourceNotFoundException if no branch is found with the specified ID
     */
    @Override
    public Branch getBranchById(Long branchId) {
        // Fetching the branch by ID
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("No Branch Found with Id " + branchId));

        return branch;
    }

    /**
     * Deletes a branch by its name.
     * @param branchName the name of the branch to be deleted
     * @return a success message
     * @throws ResourceNotFoundException if no branch is found with the specified name
     */
    @Override
    @Transactional
    public String deleteBranchByName(String branchName) {
        // Fetching the branches with the specified name
        List<Branch> deletedBranch = branchRepository.findByBranchName(branchName);

        if (deletedBranch.isEmpty()) {
            throw new ResourceNotFoundException("No Branch Found  with Name " + branchName);
        } else {
            // Deleting all scheduled programs associated with each branch
            for (Branch i : deletedBranch) {
                List<ProgramScheduled> list = programScheduledRepository.findByBranch_BranchId(i.getBranchId());
                for (ProgramScheduled j : list) {
                    programScheduledService.deleteProgramScheduledById(j.getScheduledId());
                }
            }

            // Deleting the branches
            branchRepository.deleteByBranchName(branchName);
        }
        return "Deleted successfully";
    }

    /**
     * Updates details of a branch.
     * @param branchId the ID of the branch to be updated
     * @param branch the new branch entity with updated details
     * @return the ID of the updated branch
     * @throws ResourceNotFoundException if no branch is found with the specified ID
     */
    @Override
    public Long updateBranch(Long branchId, Branch branch) {
        // Fetching the existing branch by ID
        Branch existingBranch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch Not Found With Id " + branchId));

        // Updating the branch details
        existingBranch.setBranchName(branch.getBranchName());
        existingBranch.setBranchDescription(branch.getBranchDescription());
        branchRepository.save(existingBranch);

        return branchId;
    }
}
