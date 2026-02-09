package com.company.registration.dao;

import com.company.registration.conn.ConnectionFactory;
import com.company.registration.domain.Client;
import com.company.registration.domain.Orders;

import java.math.BigDecimal;
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

    public static Optional<Orders> findOrderById(int id) {
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = findOrderByIdPreparedStatement(conn, id)) {
            ResultSet rs = ps.executeQuery();
            Client clientById;
            if (!rs.next()) Optional.empty();
            Optional<Client> clientOptional = ClientDAO.findClientById(rs.getInt("client_id"));
            clientById = clientOptional.get();
            Orders order = new Orders.OrdersBuilder()
                    .id(rs.getInt("id"))
                    .client(clientById)
                    .order_date(rs.getTimestamp("order_date").toLocalDateTime())
                    .total(rs.getBigDecimal("total"))
                    .build();
            return Optional.of(order);
        } catch (SQLException e) {
            System.out.printf("Error, Order with id %d not exist%n", id);
        }
        return Optional.empty();
    }

    private static PreparedStatement findOrderByIdPreparedStatement(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM `registration_system`.`orders` WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    public static void updateOrderById(int idOrder, BigDecimal orderTotal) {
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = updateOrderByIdPreparedStatement(conn, idOrder,  orderTotal)) {
            ps.executeUpdate();
            System.out.println("Order updated");
        } catch (SQLException e) {
            System.out.printf("Error while trying to update order with ID: %d%n", idOrder);
        }
    }

    private static PreparedStatement updateOrderByIdPreparedStatement(Connection conn, int idOrder, BigDecimal orderTotal) throws SQLException {
        String sql = "UPDATE `registration_system`.`orders` SET `total` = ? WHERE (`id` = ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setBigDecimal(1, orderTotal);
        ps.setInt(2, idOrder);
        return ps;
    }

    public static void deleteOrderById(int idOrder) {
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = deleteOrderByIdPreparedStatement(conn, idOrder)) {
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) throw new SQLException();
            System.out.println("Order deleted sucessfully");
        } catch (SQLException e) {
            System.out.printf("Error while trying to delete order with ID: %d%n", idOrder);
        }
    }

    private static PreparedStatement deleteOrderByIdPreparedStatement(Connection conn, int idOrder) throws SQLException {
        String sql = "DELETE FROM `registration_system`.`orders` WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idOrder);
        return ps;
    }
}
