package dao;

import db.DatabaseConnection;
import model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public List<Order> getAllOrders() {

        List<Order> list = new ArrayList<>();

        String sql = "SELECT * FROM Orders";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                Statement stmt =
                        conn.createStatement();

                ResultSet rs =
                        stmt.executeQuery(sql)
        ) {

            while (rs.next()) {

                System.out.println(
                        "Order Found: "
                                + rs.getInt("OrderID")
                                + " - "
                                + rs.getInt("CustomerID")
                );

                list.add(
                        new Order(
                                rs.getInt("OrderID"),
                                rs.getInt("CustomerID"),
                                rs.getDate("OrderDate"),
                                rs.getDouble("TotalAmount")
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insertOrder(Order o) {

        String sql =
                "INSERT INTO Orders(CustomerID, OrderDate, TotalAmount) VALUES (?, ?, ?)";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ) {

            System.out.println(
                    "Insert Order -> CustomerID: "
                            + o.getCustomerId()
                            + ", Total: "
                            + o.getTotalAmount()
            );

            ps.setInt(
                    1,
                    o.getCustomerId()
            );

            ps.setDate(
                    2,
                    new java.sql.Date(
                            o.getOrderDate().getTime()
                    )
            );

            ps.setDouble(
                    3,
                    o.getTotalAmount()
            );

            int rows =
                    ps.executeUpdate();

            System.out.println(
                    "Rows affected = "
                            + rows
            );

        } catch (Exception e) {

            System.out.println(
                    "Insert Order Error:"
            );

            e.printStackTrace();
        }
    }

    public void updateOrder(Order o) {

        String sql =
                "UPDATE Orders " +
                        "SET CustomerID=?, OrderDate=?, TotalAmount=? " +
                        "WHERE OrderID=?";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    o.getCustomerId()
            );

            ps.setDate(
                    2,
                    new java.sql.Date(
                            o.getOrderDate().getTime()
                    )
            );

            ps.setDouble(
                    3,
                    o.getTotalAmount()
            );

            ps.setInt(
                    4,
                    o.getId()
            );

            int rows =
                    ps.executeUpdate();

            System.out.println(
                    "Update rows = "
                            + rows
            );

        } catch (Exception e) {

            System.out.println(
                    "Update Order Error:"
            );

            e.printStackTrace();
        }
    }

    public void deleteOrder(int id) {

        String sql =
                "DELETE FROM Orders WHERE OrderID=?";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    id
            );

            int rows =
                    ps.executeUpdate();

            System.out.println(
                    "Delete rows = "
                            + rows
            );

        } catch (Exception e) {

            System.out.println(
                    "Delete Order Error:"
            );

            e.printStackTrace();
        }
    }

}