package com.company.registration.service;

import com.company.registration.dao.ClientDAO;
import com.company.registration.dao.OrdersDAO;
import com.company.registration.dao.ProductDAO;
import com.company.registration.domain.Client;
import com.company.registration.domain.Orders;
import com.company.registration.domain.Product;

import javax.swing.text.NumberFormatter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

public class OrdersService {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void ordersMenu(int op){

        switch (op){
            case 1 -> saveOrder();
            case 2 -> findAllOrders();
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
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt, br"));
        List<Orders> ordersList = OrdersDAO.findAllOrders();
        ordersList.forEach(o ->
                System.out.printf("ID: %d | client_id: %d | order_date: %s | Total: %s%n",
                        o.getId(), o.getClient().getId(), o.getOrder_date().format(formatter), nf.format(o.getTotal())));
    }


//    private static void addProductToOrder() {
//        ProductService.findAllProducts();
//        System.out.println("Type the ID of the product you want to add: ");
//        int id = Integer.parseInt(SCANNER.nextLine());
//        Optional<Product> productOptional = ProductDAO.findProductById(id);
//        if (productOptional.isEmpty()) return;
//        Product product = productOptional.get();
//
//    }
}
