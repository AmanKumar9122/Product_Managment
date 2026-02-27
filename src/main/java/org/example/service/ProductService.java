package org.example.service;
import org.example.dao.ProductDAO;
import org.example.model.Product;

import java.util.List;

public class ProductService {

    private final ProductDAO dao = new ProductDAO();

    public boolean add(Product p) throws Exception {

        if (p.price <= 0) throw new RuntimeException("Invalid price");
        if (p.quantity < 0) throw new RuntimeException("Invalid quantity");
        if (p.rating < 1 || p.rating > 5)
            throw new RuntimeException("Invalid rating");

        if (dao.findById(p.productId) != null)
            throw new RuntimeException("Product ID exists");

        return dao.insert(p);
    }

    public List<Product> all() throws Exception { return dao.findAll(); }
    public Product byId(int id) throws Exception { return dao.findById(id); }
    public int updatePrice(int id,double p) throws Exception { return dao.updatePrice(id,p); }
    public int updateStock(int id,int q) throws Exception { return dao.updateStock(id,q); }
    public int delete(int id) throws Exception { return dao.delete(id); }

    public List<Product> searchName(String n) throws Exception { return dao.searchByName(n); }
    public List<Product> searchCat(String c) throws Exception { return dao.searchByCategory(c); }
    public List<Product> lowStock(int t) throws Exception { return dao.lowStock(t); }
    public List<Product> topRated(double r) throws Exception { return dao.topRated(r); }
    public List<Product> page(int p,int s) throws Exception { return dao.pagination(p,s); }

    public double stockValue() throws Exception { return dao.stockValue(); }
    public int count() throws Exception { return dao.count(); }
}