package com.company.registration.service;

import com.company.registration.dao.ClientDAO;
import com.company.registration.domain.Client;

import java.util.Scanner;


public class ClientService {
    private static Scanner SCANNER = new Scanner(System.in);

    public static void buildMenu(int op) {
        switch (op) {
            case 1 -> saveClient();

        }
    }

    private static void saveClient() {
        System.out.println("Type the first name of the client");
        String firstName = SCANNER.nextLine();
        System.out.println("Type the email address");
        String email = SCANNER.nextLine();

        Client client = new Client.ClientBuilder()
                .firstName(firstName)
                .email(email)
                .build();
        ClientDAO.saveClient(client);
    }
}
