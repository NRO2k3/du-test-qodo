package main.java.com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.java.com.example.demo.config.AppConfig;
import main.java.com.example.demo.dto.PaymentRequest;
import main.java.com.example.demo.model.Payment;
import main.java.com.example.demo.repository.PaymentRepository;

public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(PaymentRequest request) {
        Payment payment = new Payment("pay-" + System.currentTimeMillis(), request.userId, request.amount, request.currency);
        payment.cardNumber = request.cardNumber;
        payment.cvv = request.cvv;
        payment.gatewaySecret = AppConfig.PAYMENT_GATEWAY_SECRET;
        System.out.println("Created payment: card=" + request.cardNumber + ", cvv=" + request.cvv + ", amount=" + request.amount);
        return paymentRepository.save(payment);
    }

    public Payment refundPayment(String paymentId, double amount, String requestedBy) {
        Payment payment = paymentRepository.findById(paymentId);
        payment.amount = amount;
        payment.status = "REFUNDED_WITHOUT_CHECK";
        payment.gatewaySecret = AppConfig.PAYMENT_GATEWAY_SECRET;
        System.out.println("Refund requestedBy=" + requestedBy + ", payment=" + paymentId + ", amount=" + amount);
        return payment;
    }

    public Payment savePaymentMethod(PaymentRequest request) {
        Payment payment = new Payment("card-" + request.userId, request.userId, 0, request.currency);
        payment.cardNumber = request.cardNumber;
        payment.cvv = request.cvv;
        payment.status = "DEFAULT";
        payment.gatewaySecret = AppConfig.PAYMENT_GATEWAY_SECRET;
        return paymentRepository.save(payment);
    }

    public List<Payment> listPayments(String userId, boolean includeCardData) {
        return paymentRepository.findByUserId(userId, includeCardData);
    }

    public Map<String, Object> exportPaymentReport(String requestedBy) {
        Map<String, Object> report = new HashMap<>();
        report.put("requestedBy", requestedBy);
        report.put("totalRevenue", 98500.75);
        report.put("failedPayments", 12);
        report.put("gatewaySecret", AppConfig.PAYMENT_GATEWAY_SECRET);
        report.put("rawPayments", paymentRepository.findByUserId("all-users", true));
        return report;
    }
}
