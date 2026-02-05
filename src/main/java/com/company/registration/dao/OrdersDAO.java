package com.company.registration.dao;

import com.company.registration.conn.ConnectionFactory;
import com.company.registration.domain.Client;
import com.company.registration.domain.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public static List<Orders> findAllOrders() {
        List<Orders> orders = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = findAllOrdersPreparedStatement(conn)) {
            ResultSet rs = ps.executeQuery();
            Client clientById;
            while (rs.next()) {
                Optional<Client> clientOptional = ClientDAO.findClientById(rs.getInt("client_id"));
                if (clientOptional.isEmpty()) break;
                clientById = clientOptional.get();
                Orders order = new Orders.OrdersBuilder()
                        .id(rs.getInt("id"))
                        .client(clientById)
                        .order_date(rs.getTimestamp("order_date").toLocalDateTime())
                        .total(rs.getBigDecimal("total"))
                        .build();
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching orders\n");
        }
        return orders;
    }

    private static PreparedStatement findAllOrdersPreparedStatement(Connection conn) throws SQLException {
        String sql = "SELECT * FROM `registration_system`.`orders`";
        return conn.prepareStatement(sql);
    }
}
