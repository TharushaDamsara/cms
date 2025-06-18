package edu.ijse.controller;

import edu.ijse.dto.UserDto;
import edu.ijse.model.SignUpModel;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource ds;

    private final SignUpModel model = new SignUpModel();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Read form parameters
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        // Basic validation
        if (name == null || email == null || password == null || role == null ||
                name.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
            req.setAttribute("error", "All fields are required.");
            req.getRequestDispatcher("/views/signUp.jsp").forward(req, resp);
            return;
        }

        UserDto dto = new UserDto();
        dto.setName(name);
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setRole(role);

        int result = model.adduser(dto, ds);

        if (result > 0) {
            // Success - redirect to login page
            resp.sendRedirect(req.getContextPath() + "/index.jsp?success=1");
        } else {
            // Failure - show error on sign-up page
            req.setAttribute("error", "Failed to sign up. Please try again.");
            req.getRequestDispatcher("/views/signUp.jsp").forward(req, resp);
        }
    }
}
