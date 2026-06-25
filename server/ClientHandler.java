package server;

import dao.CustomerDAO;
import dao.OrderDAO;
import dao.UserDAO;

import model.Customer;
import model.Order;
import model.UserAccount;

import network.Request;
import network.Response;

import security.PasswordUtils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

    private Socket socket;

    private UserDAO userDAO = new UserDAO();

    private CustomerDAO customerDAO =
            new CustomerDAO();

    private OrderDAO orderDAO =
            new OrderDAO();

    public ClientHandler(Socket socket) {

        this.socket = socket;
    }

    @Override
    public void run() {

        try (

                ObjectOutputStream out =
                        new ObjectOutputStream(
                                socket.getOutputStream()
                        );

                ObjectInputStream in =
                        new ObjectInputStream(
                                socket.getInputStream()
                        )

        ) {

            Request req =
                    (Request) in.readObject();

            Response res =
                    handle(req);

            out.writeObject(res);
            out.flush();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private Response handle(Request req) {

        switch (req.getAction()) {

            case "LOGIN": {

                String[] data =
                        ((String) req.getData()).split(":");

                UserAccount user =
                        userDAO.login(
                                data[0],
                                data[1]
                        );

                if (user != null) {

                    return new Response(
                            true,
                            "Login success",
                            user
                    );
                }

                return new Response(
                        false,
                        "Sai tài khoản hoặc mật khẩu",
                        null
                );
            }

            case "REGISTER": {

                String[] data =
                        ((String) req.getData()).split(":");

                if (userDAO.existsUsername(data[0])) {

                    return new Response(
                            false,
                            "Username đã tồn tại",
                            null
                    );
                }

                UserAccount newUser =
                        new UserAccount(
                                0,
                                data[0],
                                PasswordUtils.hashPassword(data[1]),
                                "USER"
                        );

                userDAO.insertUser(newUser);

                return new Response(
                        true,
                        "Register success",
                        null
                );
            }

            // ==========================
            // CUSTOMER CRUD
            // ==========================

            case "GET_CUSTOMERS": {

                List<Customer> customers =
                        customerDAO.getAllCustomers();

                return new Response(
                        true,
                        "Load customer success",
                        customers
                );
            }

            case "ADD_CUSTOMER": {

                Customer customer =
                        (Customer) req.getData();

                customerDAO.insertCustomer(customer);

                return new Response(
                        true,
                        "Add customer success",
                        null
                );
            }

            case "UPDATE_CUSTOMER": {

                Customer customer =
                        (Customer) req.getData();

                customerDAO.updateCustomer(customer);

                return new Response(
                        true,
                        "Update customer success",
                        null
                );
            }

            case "DELETE_CUSTOMER": {

                int id =
                        (Integer) req.getData();

                customerDAO.deleteCustomer(id);

                return new Response(
                        true,
                        "Delete customer success",
                        null
                );
            }


            case "GET_ORDERS": {

                List<Order> orders =
                        orderDAO.getAllOrders();

                return new Response(
                        true,
                        "Load order success",
                        orders
                );
            }

            case "ADD_ORDER": {

                Order order =
                        (Order) req.getData();

                orderDAO.insertOrder(order);

                return new Response(
                        true,
                        "Add order success",
                        null
                );
            }

            case "UPDATE_ORDER": {

                Order order =
                        (Order) req.getData();

                orderDAO.updateOrder(order);

                return new Response(
                        true,
                        "Update order success",
                        null
                );
            }

            case "DELETE_ORDER": {

                int id =
                        (Integer) req.getData();

                orderDAO.deleteOrder(id);

                return new Response(
                        true,
                        "Delete order success",
                        null
                );
            }

            default:

                return new Response(
                        false,
                        "Unknown action",
                        null
                );
        }
    }
}