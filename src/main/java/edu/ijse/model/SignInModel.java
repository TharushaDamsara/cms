package edu.ijse.model;

import edu.ijse.dto.UserDto;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignInModel {
    public int checkCredentials(UserDto dto, DataSource ds) {

        try {
            Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");

            preparedStatement.setString(1,dto.getEmail());
            preparedStatement.setString(2, dto.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return 1;
            }
           return 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
