package com.cg.admission.dto;

import java.time.LocalDate;

import com.cg.admission.entity.Branch;

import lombok.AllArgsConstructor;

import lombok.Data;

import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor

@NoArgsConstructor 

public class ProgramScheduledDto {

	private Long scheduledId;

	private ProgramScheduledResponseDto programScheduledResponseDto;

	private LocalDate startDate;

	private LocalDate endDate;

}
