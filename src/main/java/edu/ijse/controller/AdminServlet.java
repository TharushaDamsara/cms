package edu.ijse.controller;

import edu.ijse.dto.ComplaintDto;
import edu.ijse.model.AdminModel;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource ds;

    private final AdminModel model = new AdminModel();

    // Show all complaints on page load
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<ComplaintDto> complaints = model.getAllComplaintsAsDto(ds); // use DTOs for JSP compatibility
            req.setAttribute("complaints", complaints);
            System.out.println(complaints);
            req.getRequestDispatcher("/views/adminDashboard.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("error", "Failed to load complaints: " + e.getMessage());
            req.getRequestDispatcher("/views/adminDashboard.jsp").forward(req, resp);
        }
    }

    // Handle status/remarks update
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        String status = req.getParameter("status");
        String remarks = req.getParameter("remarks");

        if (idParam == null || status == null || remarks == null ||
                idParam.isEmpty() || status.isEmpty() || remarks.isEmpty()) {
            req.setAttribute("error", "All fields are required.");
            doGet(req, resp); // re-render page with error
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            ComplaintDto dto = new ComplaintDto();
            dto.setId(id);
            dto.setStatus(status);
            dto.setRemarks(remarks);

            int result = model.updateComplaints(ds, dto);

            if (result > 0) {
                req.setAttribute("message", "Complaint updated successfully.");
            } else {
                req.setAttribute("error", "Failed to update complaint.");
            }

        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid complaint ID.");
        } catch (SQLException e) {
            req.setAttribute("error", "Database error: " + e.getMessage());
        }

        doGet(req, resp); // re-fetch complaints and render the page
    }
}
