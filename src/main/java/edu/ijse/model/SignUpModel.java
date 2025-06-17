package edu.ijse.model;

import edu.ijse.dto.UserDto;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpModel {
    public int adduser(UserDto dto, DataSource ds) {

        try {
            Connection connection = ds.getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)");
            stmt.setString(1, dto.getName());
            stmt.setString(2, dto.getEmail());
            stmt.setString(3, dto.getPassword());
            stmt.setString(4, dto.getRole());
            int i = stmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
