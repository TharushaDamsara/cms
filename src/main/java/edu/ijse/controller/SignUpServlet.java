package edu.ijse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource ds;
    SignUpModel model = new SignUpModel();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> users = mapper.readValue(req.getInputStream(),Map.class);

        String name = users.get("name");
        String email = users.get("email");
        String password = users.get("password");
        String role = users.get("role");

        UserDto dto = new UserDto(name,email,password,role);

        int i = model.adduser(dto, ds);
        resp.setContentType("application/json");
        mapper.writeValue(resp.getWriter(), i);

            if (i>0){
                resp.setStatus(200);

            }
            else {
                resp.setStatus(500);
            }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = ds.getConnection();
            ResultSet resultSet = connection.prepareStatement("SELECT * FROM users").executeQuery();
            List<Map<String,String>> ulist = new ArrayList<>();

            while (resultSet.next()){
                Map<String,String> user = new HashMap<>();
                user.put("name",resultSet.getString("name"));
                user.put("email",resultSet.getString("email"));
                user.put("password",resultSet.getString("password"));
                user.put("role",resultSet.getString("role"));
                ulist.add(user);
            }

            resp.setContentType("application/json");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getWriter(), ulist);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
