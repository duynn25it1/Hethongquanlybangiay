package dao;

import db.DatabaseConnection;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void insertProduct(Product product) {

        String sql = "INSERT INTO Product(ProductName, Brand, Size, Price, Quantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getBrand());
            stmt.setInt(3, Integer.parseInt(product.getSize()));
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getQuantity());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {

        List<Product> list = new ArrayList<>();

        String sql = "SELECT * FROM Product";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Brand"),
                        String.valueOf(rs.getInt("Size")),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void updateProduct(Product product) {

        String sql = "UPDATE Product SET ProductName=?, Brand=?, Size=?, Price=?, Quantity=? WHERE ProductID=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getBrand());
            stmt.setInt(3, Integer.parseInt(product.getSize()));
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getQuantity());
            stmt.setInt(6, product.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {

        String sql = "DELETE FROM Product WHERE ProductID=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> searchByName(String keyword) {

        List<Product> list = new ArrayList<>();

        String sql = "SELECT * FROM Product WHERE ProductName LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Brand"),
                        String.valueOf(rs.getInt("Size")),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}