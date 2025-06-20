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
        // Set encoding to avoid issues with special chars
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        try {
            // Read form parameters
            String userIdStr = req.getParameter("user_id"); // you may need to get from session instead
            String subject = req.getParameter("subject");
            String description = req.getParameter("description");

            if (subject == null || description == null || subject.trim().isEmpty() || description.trim().isEmpty()) {
                req.setAttribute("error", "Subject and Description are required.");
                req.getRequestDispatcher("/views/submitcomplaint.jsp").forward(req, resp);
                return;
            }

            // For userId, you probably want to get from session (not form) to avoid spoofing:
            Integer userId = null;
            Object userIdObj = req.getSession().getAttribute("user_id");
            if (userIdObj != null) {
                userId = Integer.parseInt(userIdObj.toString());
            } else {
                req.setAttribute("error", "User not logged in.");
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
                return;
            }

            ComplaintDto dto = new ComplaintDto();
            dto.setUserId(userId);
            dto.setSubject(subject);
            dto.setDiscription(description);

            // You may want to set default status & remarks here if not in form
            dto.setStatus("Pending");  // default status
            dto.setRemarks("");        // default remarks

            // Call model to insert complaint
            int result = model.addComplainnts(ds, dto);

            if (result > 0) {
                req.setAttribute("message", "Complaint submitted successfully.");
                req.getRequestDispatcher("/views/submitcomplaint.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "Failed to submit complaint.");
                req.getRequestDispatcher("/views/submitcomplaint.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "An error occurred.");
            req.getRequestDispatcher("/views/submitcomplaint.jsp").forward(req, resp);
        }
    }

}
