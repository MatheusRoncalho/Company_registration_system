package com.company.registration.service;

import com.company.registration.dao.ClientDAO;
import com.company.registration.dao.OrdersDAO;
import com.company.registration.domain.Client;
import com.company.registration.domain.Orders;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;

public class OrdersService {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void ordersMenu(int op){

        switch (op){
            case 1 -> saveOrder();
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
}
