package service;

import dao.ProductDAO;
import model.Product;

import java.util.List;

public class ProductService {

    private ProductDAO dao = new ProductDAO();

    public List<Product> getProducts() {
        return dao.getAllProducts();
    }

    public List<Product> searchProducts(String keyword) {
        return dao.searchByName(keyword);
    }

    public void addProduct(Product p) {
        dao.insertProduct(p);
    }

    public void updateProduct(Product p) {
        dao.updateProduct(p);
    }

    public void deleteProduct(int id) {
        dao.deleteProduct(id);
    }
}