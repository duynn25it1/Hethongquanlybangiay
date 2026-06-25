package service;

import dao.OrderDetailDAO;
import model.OrderDetail;

import java.util.List;

public class OrderDetailService {
    private OrderDetailDAO orderDetailDAO;

    public OrderDetailService() {
        this.orderDetailDAO = new OrderDetailDAO();
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetailDAO.insertOrderDetail(orderDetail);
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetailDAO.getAllOrderDetails();
    }

    public void updateOrderDetail(OrderDetail orderDetail) {
        orderDetailDAO.updateOrderDetail(orderDetail);
    }

    public void deleteOrderDetail(int id) {
        orderDetailDAO.deleteOrderDetail(id);
    }
}
