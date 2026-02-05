package com.company.registration.service;

import com.company.registration.dao.ClientDAO;
import com.company.registration.domain.Client;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class ClientService {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void buildMenu(int op) {
        switch (op) {
            case 1 -> saveClient();
            case 2 -> findAllClients();
            case 3 -> findClientById();
            case 4 -> updateClientById();
            case 5 -> deleteClientById();
        }
    }

    private static void saveClient() {
        System.out.println("Type the first name of the client");
        String firstName = SCANNER.nextLine();
        System.out.println("Type the email address");
        String email = SCANNER.nextLine();

        if (ClientDAO.findAllClients().stream().anyMatch(c -> c.getEmail().equals(email))) {
            System.out.println("There is already a client with that email address");
            return;
        }

        Client client = new Client.ClientBuilder()
                .firstName(firstName)
                .email(email)
                .build();
        ClientDAO.saveClient(client);
    }

    public static void findAllClients() {
        List<Client> clientList = ClientDAO.findAllClients();
        clientList.forEach(c ->
                System.out.printf("ID: %d | Name: %s | Email: %s%n", c.getId(), c.getFirstName(), c.getEmail()));
    }

    public static void findClientById() {
        System.out.println("Type the ID of the client you want to find");
        int id = Integer.parseInt(SCANNER.nextLine());
        Optional<Client> clientOptional = ClientDAO.findClientById(id);
        if (clientOptional.isEmpty()) return;
        clientOptional.ifPresent(c -> System.out.printf("ID: %d | name: %s | email: %s%n", c.getId(), c.getFirstName(), c.getEmail()));
    }

    private static void updateClientById() {
        findAllClients();
        System.out.println("Type the ID of the client you want to update");
        int id = Integer.parseInt(SCANNER.nextLine());
        Optional<Client> clientOptional = ClientDAO.findClientById(id);
        if (clientOptional.isEmpty()) return;

        Client clientFromDb = clientOptional.get();
        System.out.println("Type the name (ENTER to keep the same)");
        String firstName = SCANNER.nextLine();
        firstName = firstName.isBlank() ? clientFromDb.getFirstName() : firstName;
        System.out.println("Type the email (ENTER to keep the same)");
        String email = SCANNER.nextLine();
        email = email.isBlank() ? clientFromDb.getEmail() : email;
        ClientDAO.updateClient(new Client.ClientBuilder()
                .id(id)
                .firstName(firstName)
                .email(email)
                .build());
    }

    private static void deleteClientById() {
        findAllClients();
        System.out.println("Type the ID of the client you want to delete");
        int id = Integer.parseInt(SCANNER.nextLine());
        ClientDAO.deleteClientById(id);
    }
}
