package com.cg.admission.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.admission.entity.Address;
import com.cg.admission.entity.College;
import com.cg.admission.entity.University;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.repository.AddressRepository;
import com.cg.admission.repository.CollegeRepository;
import com.cg.admission.repository.UniversityRepository;
import com.cg.admission.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressRepository addressRepository; // Repository to handle Address entity CRUD operations

	@Autowired
	UniversityRepository universityRepository; // Repository to handle University entity CRUD operations

	@Autowired
	CollegeRepository collegeRepository; // Repository to handle College entity CRUD operations

	@Override
	public Address addAddressForUniversity(Long id, Address address) {
		// Fetch the university by its ID
		University university = universityRepository.findById(id).get();
		// Set the address for the university
		university.setAddress(address);
		// Save the address to the repository
		addressRepository.save(address);
		// Save the updated university to the repository
		universityRepository.save(university);
		// Return the saved address
		return address;
	}

	@Override
	public Address addAddressForCollege(Long id, Address address) {
		// Fetch the college by its ID
		College college = collegeRepository.findById(id).get();
		// Set the address for the college
		college.setAddress(address);
		// Save the address to the repository
		addressRepository.save(address);
		// Save the updated college to the repository
		collegeRepository.save(college);
		// Return the saved address
		return address;
	}

	@Override
	public Address getAddressById(Long Id) {
		// Fetch the address by its ID, throw exception if not found
		Address address = addressRepository.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("No Address found with Id " + Id));
		// Return the found address
		return address;
	}

	@Override
	public Address updateAddress(Long Id, Address address) {
		// Fetch the address by its ID, throw exception if not found
		Address address2 = addressRepository.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("No Address found with Id " + Id));
		// Copy properties from the new address to the existing address
		BeanUtils.copyProperties(address, address2);
		// Ensure the address ID remains the same
		address2.setAddressId(Id);
		// Save the updated address to the repository
		addressRepository.save(address2);
		// Return the updated address
		return address2;
	}

	@Override
	public String deleteAddressById(Long Id) {
		// Fetch the address by its ID, throw exception if not found
		Address address = addressRepository.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("No Address found with Id " + Id));
		// Delete the address from the repository
		addressRepository.delete(address);
		// Return success message
		return "Deleted Successfully";
	}
}
