package com.cg.admission.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cg.admission.dto.DocumentDto;
import com.cg.admission.entity.Application;
import com.cg.admission.entity.Document;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.repository.ApplicationRepository;
import com.cg.admission.repository.DocumentRepository;
import com.cg.admission.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private ApplicationRepository applicationRepository;

	@Override
	public DocumentDto addDocument(Long applicatonId, MultipartFile file) throws IOException {
		Document document = new Document();
		document.setName(file.getOriginalFilename());
		document.setData(file.getBytes());
		document.setType(file.getContentType());
		documentRepository.save(document);
		Application application = applicationRepository.findById(applicatonId).get();
		application.setDocument(document);
		applicationRepository.save(application);
		DocumentDto documentDto = new DocumentDto();
		BeanUtils.copyProperties(document, documentDto);
		return documentDto;
	}

	@Override
	public DocumentDto getDocumentById(Long id) {
		Document document = documentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Document found with ID " + id));

		DocumentDto documentDto = new DocumentDto();
		BeanUtils.copyProperties(document, documentDto);
		return documentDto;
	}

	@Override
	public List<DocumentDto> viewAllDocuments() {
		List<Document> documentList = documentRepository.findAll();

		if (documentList.isEmpty()) {
			throw new ResourceNotFoundException("No Documents found");
		}

		return documentList.stream().map(document -> {
			DocumentDto documentDto = new DocumentDto();
			BeanUtils.copyProperties(document, documentDto);
			return documentDto;
		}).collect(Collectors.toList());
	}

	@Override
	public byte[] downloadDocument(Long id) {
		Document document = documentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Document found with ID " + id));
		return document.getData();
	}

	@Override
	public String deleteDocumentById(Long id) {
		Document document = documentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Document found with ID " + id));

		documentRepository.delete(document);
		return "Document deleted successfully";
	}

	@Override
	@Transactional
	public DocumentDto updateDocument(Long id, DocumentDto documentDto) {
		Document existingDocument = documentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Document found with ID " + id));

		BeanUtils.copyProperties(documentDto, existingDocument, "id");
		existingDocument.setData(documentDto.getData()); // Explicitly setting the new data

		documentRepository.save(existingDocument);

		DocumentDto updatedDocumentDto = new DocumentDto();
		BeanUtils.copyProperties(existingDocument, updatedDocumentDto);
		return updatedDocumentDto;
	}
}