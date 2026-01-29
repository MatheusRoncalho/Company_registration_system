package com.company.registration.dao;


import com.company.registration.conn.ConnectionFactory;
import com.company.registration.domain.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
