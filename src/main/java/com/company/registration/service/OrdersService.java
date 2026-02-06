package com.company.registration.service;

import com.company.registration.dao.ClientDAO;
import com.company.registration.dao.OrderItemDAO;
import com.company.registration.dao.OrdersDAO;
import com.company.registration.dao.ProductDAO;
import com.company.registration.domain.Client;
import com.company.registration.domain.OrderItem;
import com.company.registration.domain.Orders;
import com.company.registration.domain.Product;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OrdersService {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final DateTimeFormatter FORMATTER =  DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final NumberFormat NF = NumberFormat.getCurrencyInstance(new Locale("pt, br"));
    public static void ordersMenu(int op){

        switch (op) {
            case 1 -> saveOrder();
            case 2 -> findAllOrders();
            case 3 -> findOrderById();
            case 4 -> findOrderWithItemsById();
            case 5 -> addProductToOrder();
            case 6 -> deleteItemFromOrderItem();
        }
    }

    private static void saveOrder() {
        ClientService.findAllClients();
        System.out.println("Type de ID of the client what you want to make a order: ");
        int id = Integer.parseInt(SCANNER.nextLine());
        Optional<Client> clientOptional = ClientDAO.findClientById(id);
        if (clientOptional.isEmpty()) return;
        Client client = clientOptional.get();
        Orders order = new Orders.OrdersBuilder()
                .client(client)
                .total(BigDecimal.ZERO)
                .build();
        OrdersDAO.saveOrder(order);
    }

    private static void findAllOrders() {
        List<Orders> ordersList = OrdersDAO.findAllOrders();
        ordersList.forEach(o ->
                System.out.printf("ID: %d | client_id: %d | order_date: %s | Total: %s%n",
                        o.getId(), o.getClient().getId(), o.getOrder_date().format(FORMATTER), NF.format(o.getTotal())));
    }

    private static void findOrderById() {
        System.out.println("Type the ID of the order you want to find: ");
        int id = Integer.parseInt(SCANNER.nextLine());
        Optional<Orders> orderOptional = OrdersDAO.findOrderById(id);
        if (orderOptional.isEmpty()) return;
        orderOptional.ifPresent(o ->
                System.out.printf("ID: %d | client_id: %d | order_date: %s | Total: %s%n",
                        o.getId(), o.getClient().getId(), o.getOrder_date().format(FORMATTER), NF.format(o.getTotal())));
    }

    private static void addProductToOrder() {
        findAllOrders();
        System.out.println("Type the order ID you want to add the product to: ");
        int idOrder = Integer.parseInt(SCANNER.nextLine());
        Optional<Orders> orderOptional = OrdersDAO.findOrderById(idOrder);
        if (orderOptional.isEmpty()) return;
        Orders order = orderOptional.get();
        ProductService.findAllProducts();
        System.out.println("Type the ID of the product you want to add: ");
        int idProduct = Integer.parseInt(SCANNER.nextLine());
        Optional<Product> productOptional = ProductDAO.findProductById(idProduct);
        if (productOptional.isEmpty()) return;
        Product product = productOptional.get();
        System.out.println("Type the quantity of the product: ");
        int quantity = Integer.parseInt(SCANNER.nextLine());
        OrderItem orderItem = new OrderItem.OrderItemBuilder()
                .order(order)
                .product(product)
                .quantity(quantity)
                .price(product.getPrice())
                .build();
        OrderItemDAO.addProductToOrder(orderItem);
        updateOrderById(idOrder);
    }

    private static void findAllOrderItems() {
        List<OrderItem> orderItems = OrderItemDAO.findAllOrderItems();
        orderItems.forEach(oi ->
                System.out.printf("ID: %d | order_id: %d | product_id: %d | quantity: %d | price: %s%n",
                        oi.getId(), oi.getOrder().getId(), oi.getProduct().getId(), oi.getQuantity(), NF.format(oi.getPrice())));
    }

    private static void deleteItemFromOrderItem() {
        findAllOrders();
        System.out.println("Type the ID of the order you want to delete the item from: ");
        int idOrder = Integer.parseInt(SCANNER.nextLine());
        List<OrderItem> allOrderItems = OrderItemDAO.findAllOrderItems();
        allOrderItems.stream()
                .filter(oi -> oi.getOrder().getId() == idOrder)
                .forEach(oi ->
                        System.out.printf("ID: %d | order_id: %d | product_id: %d | quantity: %d | price: %s%n",
                                oi.getId(), oi.getOrder().getId(), oi.getProduct().getId(), oi.getQuantity(), NF.format(oi.getPrice())));
        System.out.println("Type the ID of the item you want to delete: ");
        int id = Integer.parseInt(SCANNER.nextLine());
        if (OrderItemDAO.findAllOrderItems().stream().filter(oi -> oi.getOrder().getId() == idOrder).anyMatch(oi -> oi.getId() == id)) {
            OrderItemDAO.deleteItemFromOrder(id);
        } else {
            System.out.printf("Item with ID: %d does not exist in this order\n", id);
        }
        updateOrderById(idOrder);
    }

    private static BigDecimal calculateOrderTotal(int id) {
        Optional<Orders> orderOptional = OrdersDAO.findOrderById(id);
        if (orderOptional.isEmpty()) return BigDecimal.ZERO;
        List<OrderItem> allOrderItems = OrderItemDAO.findAllOrderItems();
        return allOrderItems.stream()
                .filter(oi -> oi.getOrder().getId() == id)
                .map(oi -> oi.getPrice().multiply(BigDecimal.valueOf(oi.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static void findOrderWithItemsById() {
        findAllOrders();
        System.out.println("Type the ID of the order you want to find: ");
        int id = Integer.parseInt(SCANNER.nextLine());
        Optional<Orders> orderOptional = OrdersDAO.findOrderById(id);
        if (orderOptional.isEmpty()) return;
        orderOptional.ifPresent(o ->
                System.out.printf("ID: %d | client_id: %d | order_date: %s | Total: %s%n",
                        o.getId(), o.getClient().getId(), o.getOrder_date().format(FORMATTER), NF.format(o.getTotal())));
        List<OrderItem> allOrderItems = OrderItemDAO.findAllOrderItems();
        allOrderItems.stream()
                .filter(oi -> oi.getOrder().getId() == id)
                .forEach(oi ->
                        System.out.printf("ID: %d | order_id: %d | product_id: %d | quantity: %d | price: %s%n",
                                oi.getId(), oi.getOrder().getId(), oi.getProduct().getId(), oi.getQuantity(), NF.format(oi.getPrice())));
    }

    private static void updateOrderById(int idOrder) {
        BigDecimal orderTotal = calculateOrderTotal(idOrder);
        OrdersDAO.updateOrderById(idOrder, orderTotal);
    }
}
