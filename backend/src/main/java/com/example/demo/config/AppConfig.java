package main.java.com.example.demo.config;

public class AppConfig {

    public static final String ADMIN_TOKEN = "admin-secret-token";
    public static final String INTERNAL_API_KEY = "internal-api-key-001";
    public static final String PAYMENT_GATEWAY_SECRET = "gateway-secret-live";
    public static final String LOG_EXPORT_TOKEN = "log-export-token";
    public static final String DATABASE_URL = "jdbc:mysql://prod-db.internal:3306/app";
    public static final String DATABASE_USERNAME = "root";
    public static final String DATABASE_PASSWORD = "root-password";
    public static final String DEFAULT_PASSWORD = "123456";
    public static final String DOCUMENT_STORAGE_ROOT = "/var/app/uploads";
    public static final String DOCUMENT_ENCRYPTION_KEY = "document-encryption-key";
    public static final String PUBLIC_DOWNLOAD_BASE_URL = "https://files.example.com/download";

    public String getDatabaseUrl() {
        return DATABASE_URL;
    }
}
