package com.cg.admission.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.cg.admission.dto.DocumentDto;
import com.cg.admission.entity.Application;
import com.cg.admission.entity.Document;
import com.cg.admission.exceptions.ResourceNotFoundException;
import com.cg.admission.repository.ApplicationRepository;
import com.cg.admission.repository.DocumentRepository;

public class DocumentServiceImplTest {

	@Mock
	private DocumentRepository documentRepository;

	@Mock
	private ApplicationRepository applicationRepository;

	@Mock
	private MultipartFile file;

	@InjectMocks
	private DocumentServiceImpl documentService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
/**Test to check document is uploaded**/
	@Test
	public void testAddDocument() throws IOException {
		Long applicationId = 1L;
		Application application = new Application();
		Document document = new Document();
		document.setName("file.pdf");
		document.setData(new byte[] { 1, 2, 3 });
		document.setType("application/pdf");

		when(file.getOriginalFilename()).thenReturn("file.pdf");
		when(file.getBytes()).thenReturn(new byte[] { 1, 2, 3 });
		when(file.getContentType()).thenReturn("application/pdf");
		when(documentRepository.save(any(Document.class))).thenReturn(document);
		when(applicationRepository.findById(applicationId)).thenReturn(Optional.of(application));
		when(applicationRepository.save(application)).thenReturn(application);

		DocumentDto documentDto = documentService.addDocument(applicationId, file);

		assertEquals("file.pdf", documentDto.getName());
		verify(documentRepository, times(1)).save(any(Document.class));
		verify(applicationRepository, times(1)).findById(applicationId);
		verify(applicationRepository, times(1)).save(application);
	}
/**Test to get specific document associated with the documentID**/
	@Test
	public void testGetDocumentById() {
		Long documentId = 1L;
		Document document = new Document();
		document.setDocumentId(documentId);
		document.setName("file.pdf");

		when(documentRepository.findById(documentId)).thenReturn(Optional.of(document));

		DocumentDto documentDto = documentService.getDocumentById(documentId);

		assertEquals("file.pdf", documentDto.getName());
		verify(documentRepository, times(1)).findById(documentId);
	}

	@Test
	public void testGetDocumentById_NotFound() {
		Long documentId = 1L;
		when(documentRepository.findById(documentId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			documentService.getDocumentById(documentId);
		});

		verify(documentRepository, times(1)).findById(documentId);
	}
/**Test to view all uploaded documents **/
	@Test
	public void testViewAllDocuments() {
		List<Document> documentList = new ArrayList<>();
		Document document = new Document();
		document.setName("file.pdf");
		documentList.add(document);

		when(documentRepository.findAll()).thenReturn(documentList);

		List<DocumentDto> documentDtoList = documentService.viewAllDocuments();

		assertEquals(1, documentDtoList.size());
		assertEquals("file.pdf", documentDtoList.get(0).getName());
		verify(documentRepository, times(1)).findAll();
	}
/** Test to check if there are no documents**/
	@Test
	public void testViewAllDocuments_NotFound() {
		when(documentRepository.findAll()).thenReturn(new ArrayList<>());

		assertThrows(ResourceNotFoundException.class, () -> {
			documentService.viewAllDocuments();
		});

		verify(documentRepository, times(1)).findAll();
	}
/**Test to check whether the document is uploaded **/
	@Test
	public void testDownloadDocument() {
		Long documentId = 1L;
		Document document = new Document();
		document.setDocumentId(documentId);
		document.setData(new byte[] { 1, 2, 3 });

		when(documentRepository.findById(documentId)).thenReturn(Optional.of(document));

		byte[] data = documentService.downloadDocument(documentId);

		assertEquals(3, data.length);
		verify(documentRepository, times(1)).findById(documentId);
	}
/**Test to check if there is no document to download and view**/
	@Test
	public void testDownloadDocument_NotFound() {
		Long documentId = 1L;
		when(documentRepository.findById(documentId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			documentService.downloadDocument(documentId);
		});

		verify(documentRepository, times(1)).findById(documentId);
	}
/**Test to check if a document associated with specific documentID is deleted**/
	@Test
	public void testDeleteDocumentById() {
		Long documentId = 1L;
		Document document = new Document();
		document.setDocumentId(documentId);

		when(documentRepository.findById(documentId)).thenReturn(Optional.of(document));

		String result = documentService.deleteDocumentById(documentId);

		assertEquals("Document deleted successfully", result);
		verify(documentRepository, times(1)).findById(documentId);
		verify(documentRepository, times(1)).delete(document);
	}
/**Test to check if there is no document to delete with specific documentID**/
	@Test
	public void testDeleteDocumentById_NotFound() {
		Long documentId = 1L;
		when(documentRepository.findById(documentId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			documentService.deleteDocumentById(documentId);
		});

		verify(documentRepository, times(1)).findById(documentId);
	}
/**Test to check the document is updated**/
	@Test
	public void testUpdateDocument() {
		Long documentId = 1L;
		Document existingDocument = new Document();
		existingDocument.setDocumentId(documentId);
		existingDocument.setName("oldFile.pdf");
		existingDocument.setData(new byte[] { 1, 2, 3 });

		DocumentDto documentDto = new DocumentDto();
		documentDto.setName("newFile.pdf");
		documentDto.setData(new byte[] { 4, 5, 6 });

		when(documentRepository.findById(documentId)).thenReturn(Optional.of(existingDocument));
		when(documentRepository.save(existingDocument)).thenReturn(existingDocument);

		DocumentDto updatedDocumentDto = documentService.updateDocument(documentId, documentDto);

		assertEquals("newFile.pdf", updatedDocumentDto.getName());
		assertEquals(3, updatedDocumentDto.getData().length);
		verify(documentRepository, times(1)).findById(documentId);
		verify(documentRepository, times(1)).save(existingDocument);
	}
/** Test to check whether there is no document to update with specific documentID **/
	@Test
	public void testUpdateDocument_NotFound() {
		Long documentId = 1L;
		DocumentDto documentDto = new DocumentDto();
		documentDto.setName("newFile.pdf");
		documentDto.setData(new byte[] { 4, 5, 6 });

		when(documentRepository.findById(documentId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			documentService.updateDocument(documentId, documentDto);
		});

		verify(documentRepository, times(1)).findById(documentId);
	}
}
