package edu.ijse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ijse.dto.ComplaintDto;
import edu.ijse.model.EmployeeModel;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/employee")

public class EmployeeServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource ds;
    EmployeeModel model = new EmployeeModel();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        try {
            String userEmail = (String) req.getSession().getAttribute("user_email");
            if (userEmail == null) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.write("{\"error\":\"User not logged in.\"}");
                return;
            }

            ArrayList<HashMap<String, Object>> complaints = model.getAllComplaintsWithUser(ds, userEmail);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out, complaints);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to load complaints.");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> clist = mapper.readValue(req.getInputStream(), Map.class);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        try {
            // Validate and extract fields
            if (clist.get("id") == null || clist.get("subject") == null || clist.get("description") == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(out, Map.of(
                        "code", "400",
                        "status", "Bad Request",
                        "message", "Complaint ID, subject, and description are required"
                ));
                return;
            }

            int complaintId = Integer.parseInt(clist.get("id").toString());
            String subject = clist.get("subject").toString();
            String description = clist.get("description").toString();

            // Create DTO
            ComplaintDto dto = new ComplaintDto();
            dto.setId(complaintId);
            dto.setSubject(subject);
            dto.setDiscription(description);

            int i = model.updateComplints(ds, dto);

            if (i > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(out, Map.of(
                        "code", "200",
                        "status", "Success",
                        "message", "Complaint updated successfully"
                ));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                mapper.writeValue(out, Map.of(
                        "code", "404",
                        "status", "Not Found",
                        "message", "No complaint found with given ID"
                ));
            }

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(out, Map.of(
                    "code", "500",
                    "status", "Error",
                    "message", e.getMessage()
            ));
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");

        try {
            String idParam = req.getParameter("id");

            if (idParam == null || idParam.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(out, Map.of(
                        "code", "400",
                        "status", "Bad Request",
                        "message", "Complaint ID is required"
                ));
                return;
            }

            int id = Integer.parseInt(idParam);

            int i = model.deleteBypk(ds, id);
            if (i > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(out, Map.of(
                        "code", "200",
                        "status", "Success",
                        "message", "Complaint deleted successfully"
                ));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                mapper.writeValue(out, Map.of(
                        "code", "404",
                        "status", "Not Found",
                        "message", "No complaint found with given ID"
                ));
            }

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(out, Map.of(
                    "code", "400",
                    "status", "Bad Request",
                    "message", "Complaint ID must be a valid integer"
            ));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(out, Map.of(
                    "code", "500",
                    "status", "Error",
                    "message", e.getMessage()
            ));
            e.printStackTrace();
        }
    }

}
