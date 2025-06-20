<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>

<%
  List<HashMap<String, Object>> complaints = (List<HashMap<String, Object>>) request.getAttribute("complaints");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>My Complaints</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
  <h3>My Complaints</h3>

  <% if (request.getAttribute("message") != null) { %>
  <div class="alert alert-success"><%= request.getAttribute("message") %></div>
  <% } else if (request.getAttribute("error") != null) { %>
  <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
  <% } %>

  <table class="table table-bordered table-striped mt-3">
    <thead class="table-dark">
    <tr>
      <th>ID</th>
      <th>Subject</th>
      <th>Status</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <% if (complaints != null && !complaints.isEmpty()) {
      for (HashMap<String, Object> complaint : complaints) { %>
    <tr>
      <form action="<%= request.getContextPath() %>/employee" method="post">
        <td>
          <%= complaint.get("id") %>
          <input type="hidden" name="id" value="<%= complaint.get("id") %>">
        </td>
        <td>
          <input type="text" class="form-control" name="subject" value="<%= complaint.get("subject") %>">
          <input type="hidden" name="description" value="<%= complaint.get("description") != null ? complaint.get("description") : "" %>">
        </td>
        <td><%= complaint.get("status") %></td>
        <td class="d-flex gap-1">
          <input type="hidden" name="action" value="update">
          <button type="submit" class="btn btn-success btn-sm">Update</button>
      </form>

      <form action="<%= request.getContextPath() %>/employee" method="post">
        <input type="hidden" name="id" value="<%= complaint.get("id") %>">
        <input type="hidden" name="action" value="delete">
        <button type="submit" class="btn btn-danger btn-sm">Delete</button>
      </form>
      </td>
    </tr>
    <%  } } else { %>
    <tr>
      <td colspan="4" class="text-center">No complaints found.</td>
    </tr>
    <% } %>
    </tbody>
  </table>

  <a href="<%= request.getContextPath() %>/views/submitcomplaint.jsp" class="btn btn-primary">Add Complaint</a>
</div>
</body>
</html>
