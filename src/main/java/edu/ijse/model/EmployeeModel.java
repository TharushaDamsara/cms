package edu.ijse.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeModel {

    // Return list of complaints with user info as list of maps
    public ArrayList<HashMap<String, Object>> getAllComplaintsWithUser(DataSource ds) {
        ArrayList<HashMap<String, Object>> complaintsList = new ArrayList<>();

        String sql = "SELECT c.id AS complaint_id, c.subject, c.description, c.status, c.remarks, c.created_at, " +
                "u.name AS user_name, u.email AS user_email, u.role AS user_role " +
                "FROM complaints c " +
                "JOIN users u ON c.user_id = u.id";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                HashMap<String, Object> complaintData = new HashMap<>();
                complaintData.put("id", rs.getInt("complaint_id"));
                complaintData.put("subject", rs.getString("subject"));
                complaintData.put("description", rs.getString("description"));
                complaintData.put("status", rs.getString("status"));
                complaintData.put("remarks", rs.getString("remarks"));
                complaintData.put("created_at", rs.getTimestamp("created_at"));

                complaintData.put("user_name", rs.getString("user_name"));
                complaintData.put("user_email", rs.getString("user_email"));
                complaintData.put("user_role", rs.getString("user_role"));

                complaintsList.add(complaintData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load complaints.", e);
        }

        return complaintsList;
    }
}
