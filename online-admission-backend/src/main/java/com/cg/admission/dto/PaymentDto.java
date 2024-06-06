package com.cg.admission.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;

import lombok.Data;

import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor

@NoArgsConstructor 

public class PaymentDto {

	private Long paymentId;

	private String emailId;

	private ApplicationDto application;

	private double paymentAmount;

	private String paymentDescription;

	private LocalDate paymentDate;

	private String paymentStatus;

}
