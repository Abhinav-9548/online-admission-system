package com.cg.admission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.admission.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}