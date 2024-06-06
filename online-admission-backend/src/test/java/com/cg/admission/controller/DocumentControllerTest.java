package com.cg.admission.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cg.admission.dto.DocumentDto;
import com.cg.admission.service.DocumentService;

public class DocumentControllerTest {

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private DocumentController documentController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(documentController).build();
    }

//    @Test
//    public void testAddDocument() throws Exception {
//        Long applicationId = 1L;
//        MockMultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "test data".getBytes());
//        DocumentDto documentDto = new DocumentDto();
//        documentDto.setName("test.pdf");
//
//        when(documentService.addDocument(eq(applicationId), any(MultipartFile.class))).thenReturn(documentDto);
//
//        mockMvc.perform(multipart("/api/doc/{applicationId}/upload", applicationId).file(file))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("test.pdf"));
//
//        verify(documentService, times(1)).addDocument(eq(applicationId), any(MultipartFile.class));
//    }
//
//    @Test
//    public void testAddDocument_InternalServerError() throws Exception {
//        Long applicationId = 1L;
//        MockMultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "test data".getBytes());
//
//        when(documentService.addDocument(eq(applicationId), any(MultipartFile.class))).thenThrow(IOException.class);
//
//        mockMvc.perform(multipart("/api/doc/{applicationId}/upload", applicationId).file(file))
//                .andExpect(status().isInternalServerError());
//
//        verify(documentService, times(1)).addDocument(eq(applicationId),any(MultipartFile.class));
//    }

    @Test
    public void testViewAllDocumentDetails() throws Exception {
        List<DocumentDto> documentDtoList = new ArrayList<>();
        DocumentDto documentDto = new DocumentDto();
        documentDto.setName("test.pdf");
        documentDtoList.add(documentDto);
        //Mock the service method
        when(documentService.viewAllDocuments()).thenReturn(documentDtoList);

        //Call the controller method
        mockMvc.perform(get("/api/doc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("test.pdf"));

        //Verify the response
        verify(documentService, times(1)).viewAllDocuments();
    }

    @Test
    public void testDeleteDocumentById() throws Exception {
        Long documentId = 1L;
        String successMessage = "Document deleted successfully";

        //Mock the service method
        when(documentService.deleteDocumentById(documentId)).thenReturn(successMessage);

        //Call the controller method
        mockMvc.perform(delete("/api/doc/{id}", documentId))
                .andExpect(status().isOk())
                .andExpect(content().string(successMessage));

        //Verify the response
        verify(documentService, times(1)).deleteDocumentById(documentId);
    }

    @Test
    public void testDownloadDocument() throws Exception {
        Long documentId = 1L;
        DocumentDto documentDto = new DocumentDto();
        documentDto.setName("test.pdf");
        documentDto.setType("application/pdf");
        byte[] data = "test data".getBytes();
        
        //Mock the service method
        when(documentService.getDocumentById(documentId)).thenReturn(documentDto);
        
        //Mock the service method
        when(documentService.downloadDocument(documentId)).thenReturn(data);

        //Call the controller method
        mockMvc.perform(get("/api/doc/downloadfile/{id}", documentId))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"test.pdf\""))
                .andExpect(content().contentType("application/pdf"))
                .andExpect(content().bytes(data));

        //Verify the response
        verify(documentService, times(1)).getDocumentById(documentId);
        
        //Verify the response
        verify(documentService, times(1)).downloadDocument(documentId);
    }

    @Test
    public void testUpdateDocument() throws Exception {
        Long documentId = 1L;
        MockMultipartFile file = new MockMultipartFile("file", "updated.pdf", "application/pdf", "updated data".getBytes());
        DocumentDto updatedDocumentDto = new DocumentDto();
        updatedDocumentDto.setName("updated.pdf");

        //Mock the service method
        when(documentService.updateDocument(eq(documentId), any(DocumentDto.class))).thenReturn(updatedDocumentDto);

        //Call the controller method
        mockMvc.perform(multipart("/api/doc/update/{id}", documentId).file(file).with(request -> {
                    request.setMethod("PUT");
                    return request;
                }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("updated.pdf"));

        //Verify the response
        verify(documentService, times(1)).updateDocument(eq(documentId), any(DocumentDto.class));
    }
}

