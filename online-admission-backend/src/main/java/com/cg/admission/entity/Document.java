
package com.cg.admission.entity;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;

import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;

import jakarta.persistence.Lob;

import jakarta.persistence.Table;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;

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

@Table(name = "document")

public class Document {

	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long documentId;

	@NotBlank(message = "Name is mandatory")

	@Size(max = 255, message = "Name should not exceed 255 characters")

	private String name; 

	@NotBlank(message = "Type is mandatory")

	@Size(max = 255, message = "Type should not exceed 255 characters")

	private String type;

	@Lob

	@NotNull(message = "Data cannot be null")

	@Column(length = 1048576) // Ensure the size is 1MB

	private byte[] data;

}
