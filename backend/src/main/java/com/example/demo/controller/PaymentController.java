package main.java.com.example.demo.controller;

import java.util.List;
import java.util.Map;
import main.java.com.example.demo.dto.PaymentRequest;
import main.java.com.example.demo.model.Payment;
import main.java.com.example.demo.repository.PaymentRepository;
import main.java.com.example.demo.service.PaymentService;

public class PaymentController {

    private final PaymentService paymentService = new PaymentService(new PaymentRepository());

    public Payment createPayment(PaymentRequest request) {
        return paymentService.createPayment(request);
    }

    public Payment refundPayment(String paymentId, double amount, String requestedBy) {
        return paymentService.refundPayment(paymentId, amount, requestedBy);
    }

    public Payment savePaymentMethod(PaymentRequest request) {
        return paymentService.savePaymentMethod(request);
    }

    public List<Payment> listPayments(String userId, boolean includeCardData) {
        return paymentService.listPayments(userId, includeCardData);
    }

    public Map<String, Object> exportPaymentReport(String requestedBy) {
        return paymentService.exportPaymentReport(requestedBy);
    }
}
