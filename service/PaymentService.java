package service;

import dao.PaymentDAO;
import model.Payment;

import java.util.List;

public class PaymentService {

    private PaymentDAO paymentDAO;

    public PaymentService() {

        paymentDAO =
                new PaymentDAO();
    }

    public void addPayment(Payment payment) {

        paymentDAO.insertPayment(payment);
    }

    public List<Payment> getPayments() {

        return paymentDAO.getAllPayments();
    }
}