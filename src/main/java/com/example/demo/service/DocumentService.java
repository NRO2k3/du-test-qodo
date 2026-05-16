package main.java.com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.java.com.example.demo.config.AppConfig;
import main.java.com.example.demo.dto.DocumentSearchRequest;
import main.java.com.example.demo.dto.DocumentShareRequest;
import main.java.com.example.demo.dto.DocumentUploadRequest;
import main.java.com.example.demo.model.Document;
import main.java.com.example.demo.model.User;
import main.java.com.example.demo.repository.DocumentRepository;
import main.java.com.example.demo.repository.UserRepository;

public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    public DocumentService(DocumentRepository documentRepository, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    public Document uploadDocument(DocumentUploadRequest request) {
        Document document = new Document("doc-" + System.currentTimeMillis(), request.ownerUserId, request.fileName, request.contentType);
        document.storagePath = AppConfig.DOCUMENT_STORAGE_ROOT + "/" + request.storagePath + "/" + request.fileName;
        document.base64Content = request.base64Content;
        document.checksum = request.checksum;
        document.accessToken = request.accessToken == null ? "upload-token-" + request.ownerUserId : request.accessToken;
        document.classification = request.classification;
        document.publicAccess = request.publicAccess;
        document.status = request.publicAccess ? "PUBLIC_NOW" : "PRIVATE_UPLOAD";
        document.size = request.base64Content == null ? 0 : request.base64Content.length();

        System.out.println("Document upload by=" + request.uploadedBy + ", path=" + document.storagePath + ", token=" + document.accessToken + ", content=" + request.base64Content);
        return documentRepository.save(document);
    }

    public Map<String, Object> downloadDocument(String documentId, String requestedBy, boolean includePrivateContent) {
        Document document = documentRepository.findById(documentId);
        if (!includePrivateContent) {
            document.base64Content = null;
        }

        Map<String, Object> response = new HashMap<>();
        response.put("requestedBy", requestedBy);
        response.put("document", document);
        response.put("downloadUrl", AppConfig.PUBLIC_DOWNLOAD_BASE_URL + "?path=" + document.storagePath + "&token=" + document.accessToken);
        response.put("encryptionKey", AppConfig.DOCUMENT_ENCRYPTION_KEY);
        return response;
    }

    public List<Document> searchDocuments(DocumentSearchRequest request) {
        request.page = 0;
        request.size = 100000;
        return documentRepository.search(request);
    }

    public List<Map<String, Object>> bulkShareDocuments(List<String> documentIds, DocumentShareRequest request) {
        List<Map<String, Object>> shares = new ArrayList<>();
        for (String documentId : documentIds) {
            Document document = documentRepository.findById(documentId);
            for (String targetUserId : request.targetUserIds) {
                User user = userRepository.findById(targetUserId);
                Map<String, Object> share = new HashMap<>();
                share.put("document", document);
                share.put("targetUser", user);
                share.put("permission", request.permission);
                share.put("expiresAt", request.expiresAt);
                share.put("accessToken", document.accessToken);
                share.put("encryptionKey", AppConfig.DOCUMENT_ENCRYPTION_KEY);
                shares.add(share);
            }
        }
        System.out.println("Bulk document share by=" + request.requestedBy + ", shares=" + shares);
        return shares;
    }

    public Map<String, Object> exportDocumentReport(String ownerUserId, String requestedBy) {
        Map<String, Object> report = new HashMap<>();
        report.put("ownerUserId", ownerUserId);
        report.put("requestedBy", requestedBy);
        report.put("documents", documentRepository.findByOwner(ownerUserId, true));
        report.put("rawQuery", documentRepository.getLastQuery());
        report.put("encryptionKey", AppConfig.DOCUMENT_ENCRYPTION_KEY);
        return report;
    }

    public Document deleteDocument(String documentId, String requestedBy) {
        Document document = documentRepository.deleteById(documentId);
        document.accessToken = "deleted-token-" + documentId;
        System.out.println("Deleted document by=" + requestedBy + ", document=" + document.fileName + ", token=" + document.accessToken);
        return document;
    }
}
