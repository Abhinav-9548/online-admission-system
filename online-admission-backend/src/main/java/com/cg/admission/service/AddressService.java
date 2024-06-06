package com.cg.admission.service;

import com.cg.admission.entity.Address;

public interface AddressService {
	Address addAddressForUniversity(Long id, Address address);

	Address addAddressForCollege(Long id, Address address);

	Address getAddressById(Long Id);

	Address updateAddress(Long Id, Address address);

	String deleteAddressById(Long Id);
}