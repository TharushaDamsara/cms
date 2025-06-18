<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.ijse.dto.ComplaintDto" %>

<%
  List<ComplaintDto> complaintDtos = (List<ComplaintDto>) request.getAttribute("complaints");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>My Complaints</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h3>My Complaints</h3>

  <% if (request.getAttribute("message") != null) { %>
  <div class="alert alert-success"><%= request.getAttribute("message") %></div>
  <% } %>
  <% if (request.getAttribute("error") != null) { %>
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
    <% if (complaintDtos != null && !complaintDtos.isEmpty()) {
      for (ComplaintDto complaintDto : complaintDtos) { %>
    <tr>
      <form action="<%= request.getContextPath() %>/employee" method="post">
        <td>
          <%= complaintDto.getId() %>
          <input type="hidden" name="id" value="<%= complaintDto.getId() %>">
        </td>
        <td>
          <input type="text" name="subject" value="<%= complaintDto.getSubject() %>" class="form-control" required>
        </td>
        <td>
          <%= complaintDto.getStatus() %>
        </td>
        <td class="d-flex gap-1">
          <input type="hidden" name="action" value="update">
          <button type="submit" class="btn btn-success btn-sm">Update</button>
      </form>

      <form action="<%= request.getContextPath() %>/employee" method="post">
        <input type="hidden" name="id" value="<%= complaintDto.getId() %>">
        <input type="hidden" name="action" value="delete">
        <button type="submit" class="btn btn-danger btn-sm">Delete</button>
      </form>
      </td>
    </tr>
    <%   }
    } else { %>
    <tr>
      <td colspan="4" class="text-center">No complaints found.</td>
    </tr>
    <% } %>
    </tbody>
  </table>
</div>
</body>
</html>
