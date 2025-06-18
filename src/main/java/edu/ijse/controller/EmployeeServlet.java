package edu.ijse.controller;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource ds;

    private final EmployeeModel model = new EmployeeModel();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Fetch user email from session
        String userEmail = (String) req.getSession().getAttribute("user_email");

        if (userEmail == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        try {
            // Fetch complaints belonging to the logged-in employee
            ArrayList<HashMap<String, Object>> complaints = model.getAllComplaintsWithUser(ds, userEmail);
            req.setAttribute("complaints", complaints);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Failed to load complaints.");
        }

        req.getRequestDispatcher("/web/views/employeeDashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if ("update".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                String subject = req.getParameter("subject");
                String description = req.getParameter("description");

                ComplaintDto dto = new ComplaintDto();
                dto.setId(id);
                dto.setSubject(subject);
                dto.setDiscription(description);

                int success = model.updateComplints(ds, dto);
                if (success>0) {
                    req.setAttribute("message", "Complaint updated successfully.");
                } else {
                    req.setAttribute("error", "Failed to update complaint.");
                }
            } else if ("delete".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                int success = model.deleteBypk(ds, id);
                if (success>0) {
                    req.setAttribute("message", "Complaint deleted successfully.");
                } else {
                    req.setAttribute("error", "Failed to delete complaint.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "An error occurred.");
        }

        // Reload updated complaint list
        doGet(req, resp);
    }
}
