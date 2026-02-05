package com.company.registration.dao;

import com.company.registration.conn.ConnectionFactory;
import com.company.registration.domain.OrderItem;
import com.company.registration.domain.Orders;
import com.company.registration.domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderItemDAO {

    public static void addProductToOrder(OrderItem orderItem) {
        System.out.println("Adding product to order");
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = addProductToOrderPreparedStatement(conn, orderItem.getOrder().getId(), orderItem.getProduct(), orderItem.getQuantity());) {
            ps.executeUpdate();
            System.out.println("Product added successfully");
        } catch (SQLException e) {
            System.out.println("Error while fetching product");
        }
    }

    private static PreparedStatement addProductToOrderPreparedStatement(Connection conn,int idOrder, Product product, int quantity) throws SQLException {
        String sql = "INSERT INTO `registration_system`.`order_items` (`order_id`, `product_id`, `quantity`, `price`) VALUES (?, ?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idOrder);
        ps.setInt(2, product.getId());
        ps.setInt(3, quantity);
        ps.setBigDecimal(4, product.getPrice());
        return ps;
    }
}
