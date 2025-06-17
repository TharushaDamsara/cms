package edu.ijse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ijse.dto.UserDto;
import edu.ijse.model.SignInModel;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/signIn")
public class SigninServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource ds;
    SignInModel model = new SignInModel();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> users = mapper.readValue(req.getInputStream(),Map.class);

        String email = users.get("email");
        String password = users.get("password");

        UserDto dto = new UserDto();
        dto.setEmail(email);
        dto.setPassword(password);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        // Basic input validation
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(out, Map.of(
                    "code", "400",
                    "status", "Bad Request",
                    "message", "Email and password are required"
            ));
            return;
        }

        ServletContext sc = req.getServletContext();
        int i = model.checkCredentials(dto,ds);

    if (i>0){
        resp.setStatus(HttpServletResponse.SC_OK);
        mapper.writeValue(out, Map.of(
                "code", "200",
                "status", "Login Success",
                "message", "You have been logged in successfully"
        ));
    } else {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        mapper.writeValue(out, Map.of(
                "code", "401",
                "status", "Unauthorized",
                "message", "Unauthorized Behaviour"
        ));
    }

    }
}
