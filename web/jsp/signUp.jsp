<%--
  Created by IntelliJ IDEA.
  User: tharusha
  Date: 6/16/2025
  Time: 11:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Sign Up</title>

    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="bg-primary bg-gradient d-flex justify-content-center align-items-center vh-100">

<div class="card shadow p-4" style="width: 100%; max-width: 400px;">
    <h3 class="text-center text-primary mb-4">Create Account</h3>

    <%-- Optional error message from request attribute --%>
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
    <div class="alert alert-danger text-center">
        <%= error %>
    </div>
    <% } %>

    <form action="signup" method="post">
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="name" name="name" placeholder="Full Name" required>
            <label for="name">Full Name</label>
        </div>
        <div class="form-floating mb-3">
            <input type="email" class="form-control" id="email" name="email" placeholder="name@example.com" required>
            <label for="email">Email address</label>
        </div>
        <div class="form-floating mb-3">
            <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
            <label for="password">Password</label>
        </div>

        <button class="btn btn-primary w-100" type="submit">Sign Up</button>
    </form>

    <div class="text-center mt-3">
        <small class="text-muted">Already have an account?
            <a href="../index.jsp" class="text-decoration-none">Login</a>
        </small>
    </div>
</div>

<!-- Optional: custom JavaScript file -->
<script src="js/signup.js"></script>

<!-- Bootstrap Bundle JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
