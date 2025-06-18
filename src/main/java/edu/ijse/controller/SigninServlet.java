package edu.ijse.controller;

import edu.ijse.dto.UserDto;
import edu.ijse.model.SignInModel;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/signIn")
public class SigninServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource ds;

    private final SignInModel model = new SignInModel();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Read form parameters
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Basic validation
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            req.setAttribute("error", "Email and password are required.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        // Create DTO and check credentials
        UserDto dto = new UserDto();
        dto.setEmail(email);
        dto.setPassword(password);

        UserDto user = model.checkCredentials(dto, ds);

        if (user != null) {
            // Store user data in session
            req.getSession().setAttribute("user_id", user.getId());
            req.getSession().setAttribute("user_email", user.getEmail());
            req.getSession().setAttribute("user_role", user.getRole());

            if ("admin".equalsIgnoreCase(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/views/adminDashboard.jsp");
            } else {
                resp.sendRedirect(req.getContextPath() + "/views/submitcomplaint.jsp");
            }
        } else {
            // Invalid login - forward back with error
            req.setAttribute("error", "Invalid email or password.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
