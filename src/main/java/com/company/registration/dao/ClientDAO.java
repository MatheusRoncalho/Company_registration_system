package com.company.registration.dao;


import com.company.registration.conn.ConnectionFactory;
import com.company.registration.domain.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ClientDAO {

    public static void saveClient(Client client) {
        System.out.printf("Saving client %s\n", client.getFirstName());
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = saveClientPreparedStatement(conn, client)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static PreparedStatement saveClientPreparedStatement(Connection conn, Client client) throws SQLException {
        String sql = "INSERT INTO `registration_system`.`client` (`name`, `email`) VALUES (?, ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, client.getFirstName());
        ps.setString(2, client.getEmail());
        return ps;
    }

    public static List<Client> findAllClients() {
        System.out.println("Finding all clients");
        List<Client> clients = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = findAllClientPreparedStatement(conn)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Client client = new Client.ClientBuilder()
                        .id(rs.getInt("id"))
                        .firstName(rs.getString("name"))
                        .email(rs.getString("email"))
                        .build();
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    private static PreparedStatement  findAllClientPreparedStatement(Connection conn) throws SQLException {
        String sql = "SELECT * FROM `registration_system`.`client`;";
        return conn.prepareStatement(sql);
    }

    public static Optional<Client> findClientById(int id) {
        System.out.println("Finding client by id");
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = findClientByIdPreparedStatement(conn, id)) {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) Optional.empty();
            return Optional.of(new Client.ClientBuilder()
                    .id(rs.getInt("id"))
                    .firstName(rs.getString("name"))
                    .email(rs.getString("email"))
                    .build());
        } catch (SQLException e) {
            System.out.println("Client with ID "+id+" not found");
        }
        return Optional.empty();
    }

    private static PreparedStatement findClientByIdPreparedStatement(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM `registration_system`.`client` WHERE id = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }
}
