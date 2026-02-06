package com.company.registration.main;

import com.company.registration.service.ClientService;
import com.company.registration.service.OrdersService;
import com.company.registration.service.ProductService;

import java.util.Scanner;

public class Menu {
    static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int op;
        while (true) {
            menu();
            op = Integer.parseInt(SCANNER.nextLine());
            if (op == 0) return;
            switch (op) {
                case 1 -> {
                    clientMenu();
                    System.out.println("Type the option you want");
                    op = Integer.parseInt(SCANNER.nextLine());
                    ClientService.buildMenu(op);
                }
                case 2 -> {
                    productMenu();
                    System.out.println("Type the option you want");
                    op = Integer.parseInt(SCANNER.nextLine());
                    ProductService.buildMenu(op);
                }
                case 3 -> {
                    orderMenu();
                    System.out.println("Type the option you want");
                    op = Integer.parseInt(SCANNER.nextLine());
                    OrdersService.ordersMenu(op);
                }
                default ->  System.out.println("Invalid option");
            }
        }
    }

    private static void menu() {
        System.out.println("Main menu");
        System.out.println("1. Client");
        System.out.println("2. Product");
        System.out.println("3. Order");
        System.out.println("0. Exit");
    }

    private static void clientMenu() {
        System.out.println("Client menu");
        System.out.println("1. Create client");
        System.out.println("2. Find all clients");
        System.out.println("3. Find client by id");
        System.out.println("4. Update client");
        System.out.println("5. Delete client");
        System.out.println("9. Go back");
    }

    private static void productMenu() {
        System.out.println("Product menu");
        System.out.println("1. Create product");
        System.out.println("2. Find all products");
        System.out.println("3. Find product by id");
        System.out.println("4. Update product");
        System.out.println("5. Delete product");
        System.out.println("9. Go back");
    }

    private static void orderMenu() {
        System.out.println("Order menu");
        System.out.println("1. Create order");
        System.out.println("2. Find all orders");
        System.out.println("3. Find order by id");
        System.out.println("4. Find order with items by id");
        System.out.println("5. Add Item to an order");
        System.out.println("6. Remove Item to an order");
        System.out.println("9. Go back");
    }
}
