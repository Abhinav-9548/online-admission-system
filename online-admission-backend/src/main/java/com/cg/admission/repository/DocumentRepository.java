package com.cg.admission.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.cg.admission.entity.Document;
 
@Repository
 
public interface DocumentRepository extends JpaRepository<Document, Long> {
	Document findByName(String fileName);
}