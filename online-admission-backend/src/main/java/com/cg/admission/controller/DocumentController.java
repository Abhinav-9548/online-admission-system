package com.cg.admission.controller;
 
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cg.admission.dto.DocumentDto;
import com.cg.admission.service.DocumentService;
 
@RestController
@RequestMapping("/api/doc")
@CrossOrigin("*")
public class DocumentController {
 
    @Autowired 
    private DocumentService documentService;
 
    
    

//	/	@PreAuthorize("hasRole('usER')")
    @PostMapping("/{applicationId}/upload")
    public ResponseEntity<DocumentDto> addDocument(@PathVariable Long applicationId ,@RequestParam("file") MultipartFile file) {
        try {
            return new ResponseEntity<>(documentService.addDocument(applicationId,file), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    
    

//	/	@PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<DocumentDto>> viewAllDocumentDetails() {
        return new ResponseEntity<>(documentService.viewAllDocuments(), HttpStatus.OK);
    }
 

//	/	@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocumentById(@PathVariable Long id) {
        return new ResponseEntity<>(documentService.deleteDocumentById(id), HttpStatus.OK);
    }
 

//	/	@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/downloadfile/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) {
        DocumentDto documentDto = documentService.getDocumentById(id);
        byte[] data = documentService.downloadDocument(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(documentDto.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documentDto.getName() + "\"")
                .body(new ByteArrayResource(data));
    }
 

//	/	@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<DocumentDto> updateDocument(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        DocumentDto documentDto = new DocumentDto();
        documentDto.setDocumentId(id);
        documentDto.setName(file.getOriginalFilename());
        documentDto.setType(file.getContentType());
        documentDto.setData(file.getBytes());
        return new ResponseEntity<>(documentService.updateDocument(id, documentDto), HttpStatus.OK);
    }
}