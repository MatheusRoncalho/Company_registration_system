package com.company.registration.dao;

import com.company.registration.conn.ConnectionFactory;
import com.company.registration.domain.Orders;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrdersDAO {

    public static void saveOrder(Orders order) {
        System.out.println("Saving order");
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = saveOrderPreparedStatement(conn, order)) {
            ps.executeUpdate();
            System.out.println("Order saved");
        } catch (SQLException e) {
            System.out.printf("The client with %d not exists.", order.getClient().getId());
            e.printStackTrace();
        }
    }

    private static PreparedStatement saveOrderPreparedStatement(Connection conn, Orders order) throws SQLException {
        String sql = "INSERT INTO `registration_system`.`orders` (client_id, total) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, order.getClient().getId());
        ps.setBigDecimal(2, order.getTotal());
        return ps;
    }
}
