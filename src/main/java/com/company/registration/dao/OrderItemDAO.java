package com.company.registration.dao;

import com.company.registration.conn.ConnectionFactory;
import com.company.registration.domain.Client;
import com.company.registration.domain.OrderItem;
import com.company.registration.domain.Orders;
import com.company.registration.domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public static List<OrderItem> findAllOrderItems() {
        List<OrderItem> orderItems = new ArrayList<>();
        try(Connection connection = ConnectionFactory.getConnection();
        PreparedStatement ps = findAllOrderItemsPreparedStatement(connection)) {
            ResultSet rs = ps.executeQuery();
            Orders order;
            Product product;
            while (rs.next()) {
                Optional<Orders> orderOptional = OrdersDAO.findOrderById(rs.getInt("order_id"));
                order = orderOptional.get();
                Optional<Product> productOptional = ProductDAO.findProductById(rs.getInt("product_id"));
                product = productOptional.get();
                OrderItem orderItem = new OrderItem.OrderItemBuilder()
                        .id(rs.getInt("id"))
                        .order(order)
                        .product(product)
                        .quantity(rs.getInt("quantity"))
                        .price(rs.getBigDecimal("price"))
                        .build();
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching order items");
        }
        return orderItems;
    }

    private static PreparedStatement findAllOrderItemsPreparedStatement(Connection conn) throws SQLException {
        String sql = "SELECT * FROM `registration_system`.`order_items`;";
        return conn.prepareStatement(sql);
    }

    public static void deleteItemFromOrder(int id) {
        System.out.println("Deleting item from order items");
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = deleteItemFromOrderPreparedStatement(conn, id)) {
            ps.executeUpdate();
            System.out.println("Item deleted successfully");
        } catch (SQLException e) {
            System.out.println("Error while deleting item from order items");
        }
    }

    private static PreparedStatement deleteItemFromOrderPreparedStatement(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM `registration_system`.`order_items` WHERE id = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }
}
