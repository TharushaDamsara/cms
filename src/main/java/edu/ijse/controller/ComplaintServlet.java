package edu.ijse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ijse.dto.ComplaintDto;
import edu.ijse.model.ComplaintModel;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/complaint")
public class ComplaintServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource ds;

    private final ComplaintModel model = new ComplaintModel();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        try {
            // Parse JSON request body to Map
            Map<String, Object> complaints = mapper.readValue(req.getInputStream(), Map.class);

            // Extract and convert data
            int userId = Integer.parseInt(complaints.get("user_id").toString());
            String subject = complaints.get("subject").toString();
            String description = complaints.get("description").toString();
            String status = complaints.get("status").toString();
            String remarks = complaints.get("remarks").toString();

            // Create DTO
            ComplaintDto dto = new ComplaintDto();
            dto.setUserId(userId);
            dto.setSubject(subject);
            dto.setDiscription(description);
            dto.setStatus(status);
            dto.setRemarks(remarks);

            // Insert into DB
            int result = model.addComplainnts(ds, dto);

            if (result > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(out, Map.of(
                        "code", "200",
                        "status", "Complaint Submitted",
                        "message", "Your complaint has been recorded successfully."
                ));
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                mapper.writeValue(out, Map.of(
                        "code", "500",
                        "status", "Failed",
                        "message", "Failed to submit complaint."
                ));
            }

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(out, Map.of(
                    "code", "500",
                    "status", "Database Error",
                    "message", e.getMessage()
            ));
            e.printStackTrace();
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(out, Map.of(
                    "code", "400",
                    "status", "Bad Request",
                    "message", "Invalid complaint data"
            ));
            e.printStackTrace();
        }
    }
}
