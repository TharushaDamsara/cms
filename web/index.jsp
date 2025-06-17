<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = (String) request.getAttribute("error");
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - CMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card shadow">
                <div class="card-header text-center bg-primary text-white">
                    <h4>Complaint Management System</h4>
                </div>
                <div class="card-body">
                    <% if (error != null && !error.isEmpty()) { %>
                    <div class="alert alert-danger"><%= error %></div>
                    <% } %>
                    <form action="index.jsp" method="post">
                        <div class="mb-3">
                            <label class="form-label">Username</label>
                            <input type="text" name="username" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Password</label>
                            <input type="password" name="password" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Login</button>
                    </form>
                </div>
            </div>
            <div class="text-center mt-3">
                <small class="text-muted">Already have an account?
                    <a href="./jsp/signUp.jsp" class="text-decoration-none">Sign In</a>
                </small>
            </div>
        </div>
    </div>
</div>
</body>
</html>
