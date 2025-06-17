<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.ijse.model.Complaint" %>



<%
  List<Complaint> complaints = (List<Complaint>) request.getAttribute("complaints");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>My Complaints</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="../js/employeedash.js"></script>
</head>
<body>
<div class="container mt-5">
  <h3>My Complaints</h3>
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
    <% if (complaints != null) {
      for (Complaint complaint : complaints) { %>
    <tr>
      <td><%= complaint.getId() %></td>
      <td><%= complaint.getSubject() %></td>
      <td><%= complaint.getStatus() %></td>
      <td>
        <a href="editComplaint?id=<%= complaint.getId() %>" class="btn btn-warning btn-sm">Edit</a>
        <form action="deleteComplaint" method="post" style="display:inline;">
          <input type="hidden" name="id" value="<%= complaint.getId() %>">
          <button type="submit" class="btn btn-danger btn-sm">Delete</button>
        </form>
      </td>
    </tr>
    <%   }
    } else { %>
    <tr><td colspan="4" class="text-center">No complaints found.</td></tr>
    <% } %>
    </tbody>
  </table>
</div>

</body>
</html>
