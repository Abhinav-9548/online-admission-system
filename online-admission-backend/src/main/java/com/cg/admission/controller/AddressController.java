package com.cg.admission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.admission.entity.Address;
import com.cg.admission.service.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/address")
@CrossOrigin("*")
public class AddressController {

	@Autowired
	private AddressService addressService;

	/**
	 * Add a new address for a university.
	 * 
	 * @param id      The ID of the university.
	 * @param address The Address entity to add.
	 * @return ResponseEntity containing the created Address object.
	 */
	@PostMapping("/add/university/{id}")
	public ResponseEntity<Address> addAddressForUniversity(@PathVariable Long id, @Valid @RequestBody Address address) {
		Address resource = addressService.addAddressForUniversity(id, address);
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	/**
	 * Add a new address for a college.
	 * 
	 * @param id      The ID of the college.
	 * @param address The Address entity to add.
	 * @return ResponseEntity containing the created Address object.
	 */
	@PostMapping("/add/college/{id}")
	public ResponseEntity<Address> addAddressForCollege(@PathVariable Long id, @Valid @RequestBody Address address) {
		Address resource = addressService.addAddressForCollege(id, address);
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	/**
	 * Retrieve an address by its ID.
	 * 
	 * @param id The ID of the address to retrieve.
	 * @return ResponseEntity containing the Address object.
	 */
	@GetMapping("/id/{id}")
	public ResponseEntity<Address> getAddressById(@PathVariable(value = "id") Long id) {
		Address response = addressService.getAddressById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Update an address by its ID.
	 * 
	 * @param id      The ID of the address to update.
	 * @param address The updated Address entity.
	 * @return ResponseEntity containing the updated Address object.
	 */
	@PutMapping("/id/{id}")
	public ResponseEntity<Address> updateAddress(@PathVariable(value = "id") Long id,
			@Valid @RequestBody Address address) {
		Address response = addressService.updateAddress(id, address);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Delete an address by its ID.
	 * 
	 * @param id The ID of the address to delete.
	 * @return ResponseEntity containing a response message.
	 */
	@DeleteMapping("/id/{id}")
	public ResponseEntity<String> deleteAddressById(@PathVariable(value = "id") Long id) {
		String response = addressService.deleteAddressById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
