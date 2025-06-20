<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Submit Complaint</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h3 class="mb-4">Submit a New Complaint</h3>

    <%-- Display success or error messages --%>
    <% if (request.getAttribute("message") != null) { %>
    <div class="alert alert-success"><%= request.getAttribute("message") %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
    <% } %>

    <form action="<%= request.getContextPath() %>/complaint" method="post">
        <div class="mb-3">
            <label for="subject" class="form-label">Subject</label>
            <input type="text" id="subject" name="subject" class="form-control" required
                   value="<%= request.getParameter("subject") != null ? request.getParameter("subject") : "" %>">
        </div>
        <div class="mb-3">
            <label for="description" class="form-label">Description</label>
            <textarea id="description" name="description" rows="4" class="form-control" required><%= request.getParameter("description") != null ? request.getParameter("description") : "" %></textarea>
        </div>
        <button type="submit" class="btn btn-success">Submit Complaint</button>
    </form>
</div>
</body>
</html>
