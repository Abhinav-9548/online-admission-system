package com.cg.admission.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cg.admission.dto.DocumentDto;

public interface DocumentService {
	DocumentDto addDocument(Long applicationId, MultipartFile file) throws IOException;

	DocumentDto getDocumentById(Long id);

	List<DocumentDto> viewAllDocuments();

	String deleteDocumentById(Long id);

	DocumentDto updateDocument(Long id, DocumentDto documentDto);

	byte[] downloadDocument(Long id);
}