package model;

import java.util.Date;
import java.io.Serializable;

public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private int orderId;
    private double amount;
    private Date paymentDate;
    private String method;

    public Payment(int id, int orderId, double amount, Date paymentDate, String method) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.method = method;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
}
