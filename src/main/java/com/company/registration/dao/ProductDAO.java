package com.company.registration.dao;

import com.company.registration.conn.ConnectionFactory;
import com.company.registration.domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAO {

    public static void saveProduct(Product product) {
        System.out.printf("saving product %s\n", product.getName());
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = saveProductPreparedStatement(conn, product)) {
            ps.executeUpdate();
            System.out.println("Product save successful");
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

}
