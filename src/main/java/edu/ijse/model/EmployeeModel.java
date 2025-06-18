package edu.ijse.model;

import edu.ijse.dto.ComplaintDto;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeModel {

    // Return list of complaints with user info as list of maps
    public ArrayList<HashMap<String, Object>> getAllComplaintsWithUser(DataSource ds, String email) {
        ArrayList<HashMap<String, Object>> complaintsList = new ArrayList<>();

        String sql = "SELECT c.id AS complaint_id, c.subject, c.description, c.status, c.remarks, c.created_at, " +
                "u.name AS user_name, u.email AS user_email, u.role AS user_role " +
                "FROM complaints c " +
                "JOIN users u ON c.user_id = u.id WHERE u.email = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set email parameter
            preparedStatement.setString(1, email);

            try (ResultSet rs = preparedStatement.executeQuery()) {
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
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load complaints.", e);
        }

        return complaintsList;
    }

    public int updateComplints(DataSource ds, ComplaintDto dto) throws SQLException {
        String sql = "UPDATE complaints SET subject = ?, description = ? WHERE id = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, dto.getSubject());
            ps.setString(2, dto.getDiscription());
            ps.setInt(3, dto.getId());

            return ps.executeUpdate(); // returns number of rows affected

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update complaint details", e);
        }
    }

    public int deleteBypk(DataSource ds, int id) throws SQLException {
        String sql = "DELETE FROM complaints WHERE id = ?";
        try (
                Connection connection = ds.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        }
    }

}
