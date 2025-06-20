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
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            req.setAttribute("error", "Email and password are required.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        UserDto dto = new UserDto();
        dto.setEmail(email);
        dto.setPassword(password);

        UserDto user = model.checkCredentials(dto, ds);

        if (user != null) {
            req.getSession().setAttribute("user_id", user.getId());
            req.getSession().setAttribute("user_email", user.getEmail());
            req.getSession().setAttribute("user_role", user.getRole());

            if ("admin".equalsIgnoreCase(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/admin");
            } else {
                resp.sendRedirect(req.getContextPath() + "/employee"); // ✅ Correctly redirect to Servlet
            }
        } else {
            req.setAttribute("error", "Invalid email or password.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
