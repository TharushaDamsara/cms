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
        try {
            ArrayList<HashMap<String, Object>> complaints = model.getAllComplaintsWithUser(ds);
            req.setAttribute("complaints", complaints);
//            req.getRequestDispatcher("/WEB-INF/views/employeeDashboard").forward(req, resp);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getWriter(),complaints);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to load complaints.");
        }
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

    }


}
