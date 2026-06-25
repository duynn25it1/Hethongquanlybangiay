package dao;

import db.DatabaseConnection;
import model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    public void insertPayment(Payment payment) {

        String sql =
                "INSERT INTO Payment(OrderID, PaymentDate, PaymentMethod, Amount) " +
                        "VALUES (?, ?, ?, ?)";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    payment.getOrderId()
            );

            stmt.setDate(
                    2,
                    new java.sql.Date(
                            payment.getPaymentDate().getTime()
                    )
            );

            stmt.setString(
                    3,
                    payment.getMethod()
            );

            stmt.setDouble(
                    4,
                    payment.getAmount()
            );

            stmt.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public List<Payment> getAllPayments() {

        List<Payment> list =
                new ArrayList<>();

        String sql =
                "SELECT * FROM Payment";

        try (

                Connection conn =
                        DatabaseConnection.getConnection();

                Statement stmt =
                        conn.createStatement();

                ResultSet rs =
                        stmt.executeQuery(sql)

        ) {

            while (rs.next()) {

                list.add(

                        new Payment(
                                rs.getInt("PaymentID"),
                                rs.getInt("OrderID"),
                                rs.getDouble("Amount"),
                                rs.getDate("PaymentDate"),
                                rs.getString("PaymentMethod")
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }
}