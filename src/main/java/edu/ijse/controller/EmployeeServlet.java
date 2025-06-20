package edu.ijse.controller;

import edu.ijse.dto.ComplaintDto;
import edu.ijse.model.EmployeeModel;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource ds;

    private final EmployeeModel model = new EmployeeModel();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user_email") == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        String userEmail = (String) session.getAttribute("user_email");

        try {
            ArrayList<HashMap<String, Object>> complaints = model.getAllComplaintsWithUser(ds, userEmail);
            req.setAttribute("complaints", complaints);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Failed to load complaints.");
        }

        req.getRequestDispatcher("/views/employeeDashboard.jsp").forward(req, resp);
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

                int result = model.updateComplints(ds, dto);
                req.setAttribute(result > 0 ? "message" : "error",
                        result > 0 ? "Complaint updated." : "Update failed.");

            } else if ("delete".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                int result = model.deleteBypk(ds, id);
                req.setAttribute(result > 0 ? "message" : "error",
                        result > 0 ? "Complaint deleted." : "Delete failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "An error occurred.");
        }

        doGet(req, resp); // Refresh complaint list
    }
}
