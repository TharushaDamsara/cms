<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.ijse.dto.ComplaintDto" %> <%-- Update with your actual Complaint class path --%>

<%
  List<ComplaintDto> complaintDtos = (List<ComplaintDto>) request.getAttribute("complaints");
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
      if (complaintDtos != null) {
        for (ComplaintDto complaintDto : complaintDtos) {
    %>
    <tr>
      <td><%= complaintDto.getId() %></td>
      <td><%= complaintDto.getUsername() %></td>
      <td><%= complaintDto.getSubject() %></td>
      <td><%= complaintDto.getStatus() %></td>
      <td><%= complaintDto.getRemarks() %></td>
      <td>
        <form action="updateStatus" method="post" class="d-flex gap-1">
          <input type="hidden" name="id" value="<%= complaintDto.getId() %>">
          <select name="status" class="form-select form-select-sm">
            <option <%= "Pending".equals(complaintDto.getStatus()) ? "selected" : "" %>>Pending</option>
            <option <%= "In Progress".equals(complaintDto.getStatus()) ? "selected" : "" %>>In Progress</option>
            <option <%= "Resolved".equals(complaintDto.getStatus()) ? "selected" : "" %>>Resolved</option>
          </select>
          <input type="text" name="remarks" value="<%= complaintDto.getRemarks() %>" class="form-control form-control-sm" required>
          <button type="submit" class="btn btn-sm btn-success">Update</button>
        </form>
        <form action="deleteComplaint" method="post" style="margin-top:5px;">
          <input type="hidden" name="id" value="<%= complaintDto.getId() %>">
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
