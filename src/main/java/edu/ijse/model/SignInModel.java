package edu.ijse.model;

import edu.ijse.dto.UserDto;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignInModel {
    public UserDto checkCredentials(UserDto dto, DataSource ds) {
        try (Connection connection = ds.getConnection()) {
            String sql = "SELECT id, email,role FROM users WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, dto.getEmail());
            preparedStatement.setString(2, dto.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                UserDto user = new UserDto();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
                return user;
            }
            return null; // No matching user
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
