package edu.ijse.model;

import edu.ijse.dto.ComplaintDto;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminModel {


    // ✅ Use this in Servlet → JSP workflow
    public List<ComplaintDto> getAllComplaintsAsDto(DataSource ds) throws SQLException {
        List<ComplaintDto> list = new ArrayList<>();
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM complaints");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ComplaintDto dto = new ComplaintDto();
                dto.setId(rs.getInt("id"));
                dto.setUserId(rs.getInt("user_id"));
                dto.setSubject(rs.getString("subject"));
                dto.setDiscription(rs.getString("description"));
                dto.setStatus(rs.getString("status"));
                dto.setRemarks(rs.getString("remarks"));
                list.add(dto);
            }
        }
        return list;
    }


    public int updateComplaints(DataSource ds, ComplaintDto dto) throws SQLException{
        String sql = "UPDATE complaints SET remarks = ?, status = ? WHERE id = ?";
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, dto.getRemarks());
            ps.setString(2, dto.getStatus());
            ps.setInt(3, dto.getId());

            return ps.executeUpdate(); // returns number of rows affected

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update complaint details", e);
        }
    }
}
