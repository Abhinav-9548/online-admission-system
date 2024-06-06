package com.cg.admission.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
@Entity
@Table(name = "address")
public class Address {

	@Id
	@SequenceGenerator(name = "address_", initialValue = 101, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_")
	private Long addressId;
	
	
	@NotBlank(message = "City Should not be blank")
	@Size(max=20, message = "City should not exceed 20 characters")
	private String city;
	
	@NotBlank(message = "District should not be blank")
	@Size(max=20, message = "District should not exceed 20 characters")
	private String district;
	
	@NotBlank(message = "State should not be blank")
	@Size(max=50, message="State should not exceed 50 characters")
	private String state;
	
	
	@NotBlank(message = "Country should not be blank")
	@Size(max=20, message = "Country should not exceed 20 characters")
	private String country;
	
	
	
	@NotBlank(message = "Zip code cannot be blank")
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Zip code must be a 6-digit number starting with a non-zero digit")
	private String zipcode;
	private String landmark;

}
