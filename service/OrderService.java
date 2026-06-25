package service;

import dao.OrderDAO;
import model.Order;

import java.util.List;

public class OrderService {

    private OrderDAO orderDAO;

    public OrderService() {

        this.orderDAO =
                new OrderDAO();
    }

    public void addOrder(Order order) {

        orderDAO.insertOrder(order);
    }

    public List<Order> getOrders() {

        return orderDAO.getAllOrders();
    }

    public void updateOrder(Order order) {

        orderDAO.updateOrder(order);
    }

    public void deleteOrder(int id) {

        orderDAO.deleteOrder(id);
    }

}