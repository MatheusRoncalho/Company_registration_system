package com.company.registration.service;

import com.company.registration.dao.ClientDAO;
import com.company.registration.domain.Client;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class ClientService {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void buildMenu(int op) {
        switch (op) {
            case 1 -> saveClient();
            case 2 -> findAllClients();
            case 3 -> findClientById();
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

    private static void findAllClients() {
        List<Client> clientList = ClientDAO.findAllClients();
        clientList.forEach(client -> {
            System.out.printf("ID: %d, name: %s, email: %s%n", client.getId(), client.getFirstName(), client.getEmail());
        });
    }

    private static void findClientById() {
        System.out.println("Type the ID of the client you want to find");
        int id = Integer.parseInt(SCANNER.nextLine());
        Client client = ClientDAO.findClientById(id);
        if (!Objects.isNull(client)) {
            System.out.printf("ID: %d, name: %s, email: %s%n", client.getId(), client.getFirstName(), client.getEmail());
        }
    }
}
