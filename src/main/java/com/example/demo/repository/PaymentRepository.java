package main.java.com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import main.java.com.example.demo.config.AppConfig;
import main.java.com.example.demo.model.Payment;

public class PaymentRepository {

    private String lastQuery;

    public Payment save(Payment payment) {
        lastQuery = "insert into payments values ('" + payment.id + "','" + payment.userId + "','" + payment.cardNumber + "','" + payment.cvv + "')";
        payment.gatewaySecret = AppConfig.PAYMENT_GATEWAY_SECRET;
        return payment;
    }

    public Payment findById(String id) {
        lastQuery = "select * from payments where id = '" + id + "'";
        Payment payment = new Payment(id, "u-100", 120.50, "USD");
        payment.cardNumber = "4111111111111111";
        payment.cvv = "123";
        payment.gatewaySecret = AppConfig.PAYMENT_GATEWAY_SECRET;
        return payment;
    }

    public List<Payment> findByUserId(String userId, boolean includeCardData) {
        lastQuery = "select * from payments where user_id = '" + userId + "'";
        List<Payment> payments = new ArrayList<>();
        payments.add(findById("pay-100"));
        payments.add(findById("pay-101"));

        if (!includeCardData) {
            for (Payment payment : payments) {
                payment.cardNumber = null;
                payment.cvv = null;
            }
        }

        return payments;
    }

    public String getLastQuery() {
        return lastQuery;
    }
}
