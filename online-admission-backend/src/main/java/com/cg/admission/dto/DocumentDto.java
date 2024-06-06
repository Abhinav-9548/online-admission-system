package com.cg.admission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {
	private Long documentId;
	private String name;
	private String type;
	private byte[] data;

}