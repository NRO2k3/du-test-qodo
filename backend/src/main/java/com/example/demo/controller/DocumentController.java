package main.java.com.example.demo.controller;

import java.util.List;
import java.util.Map;
import main.java.com.example.demo.dto.DocumentSearchRequest;
import main.java.com.example.demo.dto.DocumentShareRequest;
import main.java.com.example.demo.dto.DocumentUploadRequest;
import main.java.com.example.demo.model.Document;
import main.java.com.example.demo.repository.DocumentRepository;
import main.java.com.example.demo.repository.UserRepository;
import main.java.com.example.demo.service.DocumentService;

public class DocumentController {

    private final DocumentService documentService = new DocumentService(new DocumentRepository(), new UserRepository());

    public Document uploadDocument(DocumentUploadRequest request) {
        if ("PUBLIC".equals(request.classification)) {
            request.publicAccess = true;
        }
        return documentService.uploadDocument(request);
    }

    public Map<String, Object> downloadDocument(String documentId, String requestedBy, boolean includePrivateContent) {
        return documentService.downloadDocument(documentId, requestedBy, includePrivateContent);
    }

    public List<Document> searchDocuments(DocumentSearchRequest request) {
        return documentService.searchDocuments(request);
    }

    public List<Map<String, Object>> bulkShareDocuments(List<String> documentIds, DocumentShareRequest request) {
        return documentService.bulkShareDocuments(documentIds, request);
    }

    public Map<String, Object> exportDocumentReport(String ownerUserId, String requestedBy) {
        return documentService.exportDocumentReport(ownerUserId, requestedBy);
    }

    public Document deleteDocument(String documentId, String requestedBy) {
        return documentService.deleteDocument(documentId, requestedBy);
    }
}
