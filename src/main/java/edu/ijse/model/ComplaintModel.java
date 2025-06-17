package edu.ijse.model;

import edu.ijse.dto.ComplaintDto;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ComplaintModel {
    public int addComplainnts(DataSource ds, ComplaintDto dto) throws SQLException {
        Connection connection = ds.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO complaints (user_id, subject, description, status, remarks) VALUES (?, ?, ?, ?, ?)");

        stmt.setInt(1, dto.getUserId());     // user_id (foreign key)
        stmt.setString(2, dto.getSubject());       // subject
        stmt.setString(3, dto.getDiscription());   // description
        stmt.setString(4, dto.getStatus());        // status (e.g., "Pending")
        stmt.setString(5, dto.getRemarks());       // remarks (can be null)

        return stmt.executeUpdate();
    }
}
