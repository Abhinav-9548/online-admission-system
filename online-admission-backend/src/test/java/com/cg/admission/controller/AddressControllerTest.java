package com.cg.admission.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.admission.entity.Address;
import com.cg.admission.service.AddressService;

@ExtendWith(MockitoExtension.class)
public class AddressControllerTest {

	@Mock
	private AddressService addressService;

	@InjectMocks
	private AddressController addressController;

	private Address address;

	@BeforeEach
	public void setUp() {
		// Initialize mock data
		address = new Address();
		address.setAddressId(1L);
		address.setCity("Springfield");
		address.setDistrict("District 1");
		address.setState("State A");
		address.setCountry("Country X");
		address.setZipcode("123456");
		address.setLandmark("Near Park");
	}

	@Test
	public void testAddAddressForUniversity() {
		Long id = 1L;

		// Mock the service method
		when(addressService.addAddressForUniversity(id, address)).thenReturn(address);

		// Call the controller method
		ResponseEntity<Address> response = addressController.addAddressForUniversity(id, address);

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(address, response.getBody());
	}

	@Test
	public void testAddAddressForCollege() {
		Long id = 1L;

		// Mock the service method
		when(addressService.addAddressForCollege(id, address)).thenReturn(address);

		// Call the controller method
		ResponseEntity<Address> response = addressController.addAddressForCollege(id, address);

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(address, response.getBody());
	}

	@Test
	public void testGetAddressById() {
		Long id = 1L;

		// Mock the service method
		when(addressService.getAddressById(id)).thenReturn(address);

		// Call the controller method
		ResponseEntity<Address> response = addressController.getAddressById(id);

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(address, response.getBody());
	}

	@Test
	public void testUpdateAddress() {
		Long id = 1L;

		// Mock the service method
		when(addressService.updateAddress(id, address)).thenReturn(address);

		// Call the controller method
		ResponseEntity<Address> response = addressController.updateAddress(id, address);

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(address, response.getBody());
	}

	@Test
	public void testDeleteAddressById() {
		Long id = 1L;

		// Mock the service method
		when(addressService.deleteAddressById(id)).thenReturn("Deleted");

		// Call the controller method
		ResponseEntity<String> response = addressController.deleteAddressById(id);

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Deleted", response.getBody());
	}
}
