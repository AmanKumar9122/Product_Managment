package org.example.dao;

import org.example.config.DBConnection;
import org.example.model.Product;

import java.sql.*;
import java.util.*;

public class ProductDAO {

    private Product map(ResultSet rs) throws Exception {
        Product p = new Product();
        p.productId = rs.getInt("product_id");
        p.productName = rs.getString("product_name");
        p.category = rs.getString("category");
        p.price = rs.getDouble("price");
        p.quantity = rs.getInt("quantity");
        p.rating = rs.getDouble("rating");
        p.manufacturer = rs.getString("manufacturer");

        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) p.createdAt = ts.toLocalDateTime();

        return p;
    }

    // add
    public boolean insert(Product p) throws Exception {
        String sql =
                "INSERT INTO products " +
                        "(product_id, product_name, category, price, quantity, rating, manufacturer) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, p.productId);
            ps.setString(2, p.productName);
            ps.setString(3, p.category);
            ps.setDouble(4, p.price);
            ps.setInt(5, p.quantity);
            ps.setDouble(6, p.rating);
            ps.setString(7, p.manufacturer);

            return ps.executeUpdate() > 0;
        }
    }

    // find all
    public List<Product> findAll() throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY product_id";

        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    // find by id
    public Product findById(int id) throws Exception {
        String sql = "SELECT * FROM products WHERE product_id=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }

    // update price
    public int updatePrice(int id, double price) throws Exception {
        String sql = "UPDATE products SET price=? WHERE product_id=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDouble(1, price);
            ps.setInt(2, id);
            return ps.executeUpdate();
        }
    }

    // update stock
    public int updateStock(int id, int quantity) throws Exception {
        String sql = "UPDATE products SET quantity=? WHERE product_id=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, id);
            return ps.executeUpdate();
        }
    }

    // delete
    public int delete(int id) throws Exception {
        String sql = "DELETE FROM products WHERE product_id=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    // search by name
    public List<Product> searchByName(String name) throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE product_name LIKE ?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    // category
    public List<Product> searchByCategory(String cat) throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, cat);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    // low stock
    public List<Product> lowStock(int threshold) throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE quantity < ?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, threshold);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    // top rated
    public List<Product> topRated(double rating) throws Exception {
        List<Product> list = new ArrayList<>();
        String sql =
                "SELECT * FROM products " +
                        "WHERE rating >= ? " +
                        "ORDER BY rating DESC";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDouble(1, rating);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    // pagination
    public List<Product> pagination(int page, int size) throws Exception {
        List<Product> list = new ArrayList<>();
        int offset = (page - 1) * size;

        String sql =
                "SELECT * FROM products " +
                        "ORDER BY product_id " +
                        "LIMIT ? OFFSET ?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, size);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    // stock value
    public double stockValue() throws Exception {
        String sql = "SELECT SUM(price * quantity) FROM products";

        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            rs.next();
            return rs.getDouble(1);
        }
    }

    // count
    public int count() throws Exception {
        String sql = "SELECT COUNT(*) FROM products";

        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            rs.next();
            return rs.getInt(1);
        }
    }
}