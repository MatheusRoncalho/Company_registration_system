package com.company.registration.dao;

import com.company.registration.conn.ConnectionFactory;
import com.company.registration.domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAO {

    public static void saveProduct(Product product) {
        System.out.printf("saving product %s\n", product.getName());
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = saveProductPreparedStatement(conn, product)) {
            ps.executeUpdate();
            System.out.println("Product save successfully");
        } catch (SQLException e) {
            System.out.println("Error while saving product");
        }
    }

    private static PreparedStatement saveProductPreparedStatement(Connection conn, Product product) throws SQLException {
        String sql = "INSERT INTO `registration_system`.`product` (`name`, `price`) VALUES (?, ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, product.getName());
        ps.setBigDecimal(2, product.getPrice());
        return ps;
    }

    public static List<Product> findAllProducts() {
        List<Product> products = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = findAllProductsPreparedStatement(conn)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product.ProductBuilder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .price(rs.getBigDecimal("price"))
                        .build();
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching products\n");
        }
        return products;
    }

    private static PreparedStatement findAllProductsPreparedStatement(Connection conn) throws SQLException {
        String sql = "SELECT * FROM `registration_system`.`product`";
        return conn.prepareStatement(sql);
    }

    public static Optional<Product> findProductById(int id) {
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = findProductByIdPreparedStatement(conn, id)) {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) Optional.empty();
            Product product = new Product.ProductBuilder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .price(rs.getBigDecimal("price"))
                    .build();
            return Optional.of(product);
        } catch (SQLException e) {
            System.out.printf("Error, Product with id %d not exist\n", id);
        }
        return Optional.empty();
    }

    private static PreparedStatement findProductByIdPreparedStatement(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM `registration_system`.`product` WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    public static void updateProduct(Product productToUpdate) {
        System.out.printf("updating product with id %d\n", productToUpdate.getId());
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = updateProductPreparedStatement(conn, productToUpdate)) {
            ps.executeUpdate();
            System.out.println("Product updated successfully");
        } catch (SQLException e) {
            System.out.println("Error while updating product");
            e.printStackTrace();
        }
    }

    private static PreparedStatement updateProductPreparedStatement(Connection conn, Product product) throws SQLException {
        String sql = "UPDATE `registration_system`.`product` SET `name` = ?, `price` = ? WHERE `id` = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, product.getName());
        ps.setBigDecimal(2, product.getPrice());
        ps.setInt(3, product.getId());
        return ps;
    }

    public static  void deleteProductById(int id) {
        System.out.printf("deleting product with id %d\n", id);
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = deleteProductPreparedStatement(conn, id)) {
            int rowsAffeted = ps.executeUpdate();
            if (rowsAffeted == 0) {
                System.out.println("Product with id " + id + " not exist");
                return;
            }
            System.out.println("Product delete successfully");
        } catch (SQLException e) {
            System.out.println("Error while deleting product");
        }
    }

    private static PreparedStatement deleteProductPreparedStatement(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM `registration_system`.`product` WHERE id = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }
}
