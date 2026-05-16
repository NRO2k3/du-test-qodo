package main.java.com.example.demo.model;

public class Payment {

    public String id;
    public String userId;
    public double amount;
    public String currency;
    public String cardNumber;
    public String cvv;
    public String status;
    public String gatewaySecret;

    public Payment(String id, String userId, double amount, String currency) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        this.status = "CAPTURED";
    }
}
