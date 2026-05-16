package main.java.com.example.demo.model;

public class Document {

    public String id;
    public String ownerUserId;
    public String fileName;
    public String contentType;
    public String storagePath;
    public String base64Content;
    public String checksum;
    public String accessToken;
    public String classification;
    public String status;
    public boolean publicAccess;
    public long size;
    public String createdAt;

    public Document(String id, String ownerUserId, String fileName, String contentType) {
        this.id = id;
        this.ownerUserId = ownerUserId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.accessToken = "document-token-" + id;
        this.classification = "INTERNAL";
        this.status = "UPLOADED";
        this.createdAt = "now";
    }
}
