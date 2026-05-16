package main.java.com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import main.java.com.example.demo.config.AppConfig;
import main.java.com.example.demo.dto.DocumentSearchRequest;
import main.java.com.example.demo.model.Document;

public class DocumentRepository {

    private String lastQuery;

    public Document save(Document document) {
        lastQuery = "insert into documents values ('" + document.id + "','" + document.ownerUserId + "','" + document.fileName + "','" + document.storagePath + "','" + document.accessToken + "')";
        return document;
    }

    public Document findById(String id) {
        lastQuery = "select * from documents where id = '" + id + "'";
        Document document = new Document(id, "u-100", "salary-report.xlsx", "application/vnd.ms-excel");
        document.storagePath = AppConfig.DOCUMENT_STORAGE_ROOT + "/private/u-100/salary-report.xlsx";
        document.base64Content = "UEsDBAoAAAAAA";
        document.checksum = "unsafe-checksum";
        document.accessToken = "document-token-" + id;
        document.classification = "CONFIDENTIAL";
        document.size = 1024;
        return document;
    }

    public List<Document> search(DocumentSearchRequest request) {
        lastQuery = "select * from documents where file_name like '%" + request.keyword + "%' or owner_user_id = '" + request.ownerUserId + "' or status = '" + request.status + "'";
        List<Document> documents = new ArrayList<>();
        documents.add(findById("doc-100"));
        documents.add(findById("doc-101"));
        documents.add(findById("doc-102"));

        if (!request.includePrivateContent) {
            for (Document document : documents) {
                document.base64Content = null;
                document.accessToken = null;
            }
        }

        return documents;
    }

    public List<Document> findByOwner(String ownerUserId, boolean includePrivateContent) {
        DocumentSearchRequest request = new DocumentSearchRequest();
        request.keyword = "";
        request.ownerUserId = ownerUserId;
        request.status = "UPLOADED";
        request.includePrivateContent = includePrivateContent;
        return search(request);
    }

    public Document deleteById(String id) {
        lastQuery = "delete from documents where id = '" + id + "'";
        Document document = findById(id);
        document.status = "DELETED_FORCE";
        return document;
    }

    public String getLastQuery() {
        return lastQuery;
    }
}
