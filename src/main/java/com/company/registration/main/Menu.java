package com.company.registration.main;

import com.company.registration.service.ClientService;

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
                default ->  System.out.println("Invalid option");
            }
        }
    }

    private static void menu() {
        System.out.println("Main menu");
        System.out.println("1. Client");
        System.out.println("0. Exit");
    }

    private static void clientMenu() {
        System.out.println("Client menu");
        System.out.println("1. Create Client");
        System.out.println("2. Find all Clients");
        System.out.println("9. Go back");
    }
}
