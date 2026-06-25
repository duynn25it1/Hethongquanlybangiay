package dao;

import db.DatabaseConnection;
import model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    public void insertPayment(Payment payment) {
        String sql = "INSERT INTO Payments(id, orderId, amount, paymentDate, method) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, payment.getId());
            stmt.setInt(2, payment.getOrderId());
            stmt.setDouble(3, payment.getAmount());
            stmt.setDate(4, new java.sql.Date(payment.getPaymentDate().getTime()));
            stmt.setString(5, payment.getMethod());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payments";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Payment p = new Payment(
                        rs.getInt("id"),
                        rs.getInt("orderId"),
                        rs.getDouble("amount"),
                        rs.getDate("paymentDate"),
                        rs.getString("method")
                );
                payments.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public void updatePayment(Payment payment) {
        String sql = "UPDATE Payments SET orderId=?, amount=?, paymentDate=?, method=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, payment.getOrderId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setDate(3, new java.sql.Date(payment.getPaymentDate().getTime()));
            stmt.setString(4, payment.getMethod());
            stmt.setInt(5, payment.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePayment(int id) {
        String sql = "DELETE FROM Payments WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
