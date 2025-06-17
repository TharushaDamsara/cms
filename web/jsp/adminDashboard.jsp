<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.ijse.model.Complaint" %> <%-- Update with your actual Complaint class path --%>

<%
  List<Complaint> complaints = (List<Complaint>) request.getAttribute("complaints");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Admin - All Complaints</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h3>All Complaints</h3>
  <table class="table table-hover table-bordered mt-3">
    <thead class="table-dark">
    <tr>
      <th>ID</th>
      <th>Employee</th>
      <th>Subject</th>
      <th>Status</th>
      <th>Remarks</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
      if (complaints != null) {
        for (Complaint complaint : complaints) {
    %>
    <tr>
      <td><%= complaint.getId() %></td>
      <td><%= complaint.getUsername() %></td>
      <td><%= complaint.getSubject() %></td>
      <td><%= complaint.getStatus() %></td>
      <td><%= complaint.getRemarks() %></td>
      <td>
        <form action="updateStatus" method="post" class="d-flex gap-1">
          <input type="hidden" name="id" value="<%= complaint.getId() %>">
          <select name="status" class="form-select form-select-sm">
            <option <%= "Pending".equals(complaint.getStatus()) ? "selected" : "" %>>Pending</option>
            <option <%= "In Progress".equals(complaint.getStatus()) ? "selected" : "" %>>In Progress</option>
            <option <%= "Resolved".equals(complaint.getStatus()) ? "selected" : "" %>>Resolved</option>
          </select>
          <input type="text" name="remarks" value="<%= complaint.getRemarks() %>" class="form-control form-control-sm" required>
          <button type="submit" class="btn btn-sm btn-success">Update</button>
        </form>
        <form action="deleteComplaint" method="post" style="margin-top:5px;">
          <input type="hidden" name="id" value="<%= complaint.getId() %>">
          <button type="submit" class="btn btn-sm btn-danger">Delete</button>
        </form>
      </td>
    </tr>
    <%
      }
    } else {
    %>
    <tr><td colspan="6" class="text-center">No complaints found.</td></tr>
    <%
      }
    %>
    </tbody>
  </table>
</div>
</body>
</html>
