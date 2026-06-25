package dao;

import db.DatabaseConnection;
import model.OrderDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {
    public void insertOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO OrderDetails(id, orderId, productId, quantity, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderDetail.getId());
            stmt.setInt(2, orderDetail.getOrderId());
            stmt.setInt(3, orderDetail.getProductId());
            stmt.setInt(4, orderDetail.getQuantity());
            stmt.setDouble(5, orderDetail.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<OrderDetail> getAllOrderDetails() {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetails";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                OrderDetail od = new OrderDetail(
                        rs.getInt("id"),
                        rs.getInt("orderId"),
                        rs.getInt("productId"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                );
                orderDetails.add(od);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

    public void updateOrderDetail(OrderDetail orderDetail) {
        String sql = "UPDATE OrderDetails SET orderId=?, productId=?, quantity=?, price=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderDetail.getOrderId());
            stmt.setInt(2, orderDetail.getProductId());
            stmt.setInt(3, orderDetail.getQuantity());
            stmt.setDouble(4, orderDetail.getPrice());
            stmt.setInt(5, orderDetail.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrderDetail(int id) {
        String sql = "DELETE FROM OrderDetails WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
