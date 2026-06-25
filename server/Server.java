package server;

import dao.OrderDAO;
import dao.ProductDAO;
import dao.UserDAO;
import dao.CustomerDAO;
import model.Order;
import model.Product;
import model.UserAccount;
import model.Customer;
import security.PasswordUtils;
import network.Request;
import network.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {

    private static final ProductDAO productDAO = new ProductDAO();
    private static final UserDAO userDAO = new UserDAO();
    private static final CustomerDAO customerDAO = new CustomerDAO();
    private static final OrderDAO orderDAO = new OrderDAO();
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(5000)) {

            System.out.println("SERVER STARTED...");

            while (true) {

                Socket socket = serverSocket.accept();

                new Thread(() -> handle(socket)).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handle(Socket socket) {

        try (
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {

            Request req = (Request) in.readObject();

            Response res = process(req);

            out.writeObject(res);
            out.flush();

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Response process(Request req) {

        try {

            switch (req.getAction()) {

                case "LOGIN": {

                    String[] acc = ((String) req.getData()).split(":");

                    UserAccount user = userDAO.login(acc[0], acc[1]);

                    if (user != null) {
                        return new Response(
                                true,
                                "Login success",
                                user
                        );
                    }

                    return new Response(false, null, "Sai tài khoản hoặc mật khẩu");
                }

                case "REGISTER": {

                    String[] data = ((String) req.getData()).split(":");

                    String username = data[0];
                    String password = data[1];

                    if (userDAO.existsUsername(username)) {
                        return new Response(false, null, "Username đã tồn tại");
                    }

                    String hashed = PasswordUtils.hashPassword(password);

                    UserAccount newUser = new UserAccount(
                            0,
                            username,
                            hashed,
                            "USER"
                    );

                    userDAO.insertUser(newUser);

                    return new Response(true, null, "Register thành công");
                }

                case "GET_PRODUCTS": {

                    List<Product> list = productDAO.getAllProducts();

                    return new Response(true, list.toString(), "OK");
                }

                case "ADD_PRODUCT": {

                    productDAO.insertProduct((Product) req.getData());

                    return new Response(true, null, "Added");
                }

                case "DELETE_PRODUCT": {

                    productDAO.deleteProduct((Integer) req.getData());

                    return new Response(true, null, "Deleted");
                }

                case "SEARCH_PRODUCT": {

                    String keyword = (String) req.getData();

                    List<Product> result = productDAO.searchByName(keyword);

                    return new Response(true, result.toString(), "OK");
                }
                case "GET_CUSTOMERS": {
                    return new Response(true, "OK", customerDAO.getAllCustomers());
                }

                case "ADD_CUSTOMER": {
                    customerDAO.insertCustomer((Customer) req.getData());
                    return new Response(true, "OK", null);
                }

                case "UPDATE_CUSTOMER": {
                    customerDAO.updateCustomer((Customer) req.getData());
                    return new Response(true, "OK", null);
                }

                case "DELETE_CUSTOMER": {
                    customerDAO.deleteCustomer((Integer) req.getData());
                    return new Response(true, "OK", null);
                }
                case "GET_ORDERS": {
                    return new Response(true, "OK", orderDAO.getAllOrders());
                }

                case "ADD_ORDER": {
                    orderDAO.insertOrder((Order) req.getData());
                    return new Response(true, "OK", null);
                }

                case "UPDATE_ORDER": {
                    orderDAO.updateOrder((Order) req.getData());
                    return new Response(true, "OK", null);
                }

                case "DELETE_ORDER": {
                    orderDAO.deleteOrder((Integer) req.getData());
                    return new Response(true, "OK", null);
                }
                default:
                    return new Response(false, null, "Unknown action");
            }

        } catch (Exception e) {
            return new Response(false, null, e.getMessage());
        }
    }
}