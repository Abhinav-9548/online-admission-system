package com.cg.admission.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.admission.entity.Address;
import com.cg.admission.entity.College;
import com.cg.admission.entity.University;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.repository.AddressRepository;
import com.cg.admission.repository.CollegeRepository;
import com.cg.admission.repository.UniversityRepository;

@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {

	@InjectMocks
	private AddressServiceImpl addressService; // Inject AddressServiceImpl to be tested

	@Mock
	private AddressRepository addressRepository; // Mock the AddressRepository

	@Mock
	private UniversityRepository universityRepository; // Mock the UniversityRepository

	@Mock 
	private CollegeRepository collegeRepository; // Mock the CollegeRepository

	private Address address;
	private University university;
	private College college;

	@BeforeEach
	public void setUp() {
		// Initialize Address entity
		address = new Address();
		address.setAddressId(1L);
		address.setCity("TestCity");
		address.setDistrict("TestDistrict");
		address.setState("TestState");
		address.setCountry("TestCountry");
		address.setZipcode("123456");
		address.setLandmark("TestLandmark");

		// Initialize University entity
		university = new University();
		university.setUniversityId(1L);
		university.setName("TestUniversity");
		university.setAddress(address);

		// Initialize College entity
		college = new College();
		college.setCollegeRegId(1L);
		college.setCollegeName("TestCollege");
		college.setAddress(address);
	}

	@Test
	public void testAddAddressForUniversity() {
		// Mock the behavior of universityRepository and addressRepository
		when(universityRepository.findById(1L)).thenReturn(Optional.of(university));
		when(addressRepository.save(any(Address.class))).thenReturn(address);
		when(universityRepository.save(any(University.class))).thenReturn(university);

		// Call the method to test
		Address result = addressService.addAddressForUniversity(1L, address);

		// Assert the result and verify interactions with mocks
		assertEquals("TestCity", result.getCity());
		verify(universityRepository, times(1)).findById(1L);
		verify(addressRepository, times(1)).save(address);
		verify(universityRepository, times(1)).save(university);
	}

	@Test
	public void testAddAddressForCollege() {
		// Mock the behavior of collegeRepository and addressRepository
		when(collegeRepository.findById(1L)).thenReturn(Optional.of(college));
		when(addressRepository.save(any(Address.class))).thenReturn(address);
		when(collegeRepository.save(any(College.class))).thenReturn(college);
 
		// Call the method to test
		Address result = addressService.addAddressForCollege(1L, address);

		// Assert the result and verify interactions with mocks
		assertEquals("TestCity", result.getCity());
		verify(collegeRepository, times(1)).findById(1L);
		verify(addressRepository, times(1)).save(address);
		verify(collegeRepository, times(1)).save(college);
	}

	@Test
	public void testGetAddressById() {
		// Mock the behavior of addressRepository
		when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

		// Call the method to test
		Address result = addressService.getAddressById(1L);

		// Assert the result and verify interactions with mocks
		assertEquals("TestCity", result.getCity());
		verify(addressRepository, times(1)).findById(1L);
	}

	@Test
	public void testGetAddressById_NotFound() {
		// Mock the behavior of addressRepository for not found case
		when(addressRepository.findById(1L)).thenReturn(Optional.empty());

		// Call the method to test and assert the exception
		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
			addressService.getAddressById(1L);
		});

		// Assert the exception message and verify interactions with mocks
		assertEquals("No Address found with Id 1", thrown.getMessage());
		verify(addressRepository, times(1)).findById(1L);
	}

	@Test
	public void testUpdateAddress() {
		// Mock the behavior of addressRepository
		when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
		when(addressRepository.save(any(Address.class))).thenReturn(address);

		// Initialize updated Address entity
		Address updatedAddress = new Address();
		updatedAddress.setCity("UpdatedCity");

		// Call the method to test
		Address result = addressService.updateAddress(1L, updatedAddress);

		// Assert the result and verify interactions with mocks
		assertEquals("UpdatedCity", result.getCity());
		verify(addressRepository, times(1)).findById(1L);
		verify(addressRepository, times(1)).save(any(Address.class));
	}

	@Test
	public void testUpdateAddress_NotFound() {
		// Mock the behavior of addressRepository for not found case
		when(addressRepository.findById(1L)).thenReturn(Optional.empty());

		// Initialize updated Address entity
		Address updatedAddress = new Address();
		updatedAddress.setCity("UpdatedCity");

		// Call the method to test and assert the exception
		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
			addressService.updateAddress(1L, updatedAddress);
		});

		// Assert the exception message and verify interactions with mocks
		assertEquals("No Address found with Id 1", thrown.getMessage());
		verify(addressRepository, times(1)).findById(1L);
		verify(addressRepository, times(0)).save(any(Address.class));
	}

	@Test
	public void testDeleteAddressById() {
		// Mock the behavior of addressRepository
		when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
		doNothing().when(addressRepository).delete(any(Address.class));

		// Call the method to test
		String result = addressService.deleteAddressById(1L);

		// Assert the result and verify interactions with mocks
		assertEquals("Deleted Sucessfully", result);
		verify(addressRepository, times(1)).findById(1L);
		verify(addressRepository, times(1)).delete(address);
	}

	@Test
	public void testDeleteAddressById_NotFound() {
		// Mock the behavior of addressRepository for not found case
		when(addressRepository.findById(1L)).thenReturn(Optional.empty());

		// Call the method to test and assert the exception
		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
			addressService.deleteAddressById(1L);
		});

		// Assert the exception message and verify interactions with mocks
		assertEquals("No Address found with Id 1", thrown.getMessage());
		verify(addressRepository, times(1)).findById(1L);
		verify(addressRepository, times(0)).delete(any(Address.class));
	}
}
