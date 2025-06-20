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
  <title>Admin - All Complaints</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    tr:hover { background-color: #f1f1f1; cursor: pointer; }
  </style>
</head>
<body>
<div class="container mt-5">
  <h3 class="mb-4">All Complaints</h3>

  <!-- Success/Error Messages -->
  <% if (request.getAttribute("error") != null) { %>
  <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
  <% } else if (request.getAttribute("message") != null) { %>
  <div class="alert alert-success"><%= request.getAttribute("message") %></div>
  <% } %>

  <!-- Complaints Table -->
  <table class="table table-hover table-bordered">
    <thead class="table-dark">
    <tr>
      <th>ID</th>
      <th>Employee</th>
      <th>Subject</th>
      <th>Status</th>
      <th>Remarks</th>
    </tr>
    </thead>
    <tbody id="complaintsTable">
    <% if (complaintDtos != null && !complaintDtos.isEmpty()) {
      for (ComplaintDto complaint : complaintDtos) { %>
    <tr data-id="<%= complaint.getId() %>"
        data-user-id="<%= complaint.getUserId() %>"
        data-subject="<%= complaint.getSubject() %>"
        data-status="<%= complaint.getStatus() %>"
        data-remarks="<%= complaint.getRemarks() != null ? complaint.getRemarks() : "" %>">
      <td><%= complaint.getId() %></td>
      <td><%= complaint.getUserId() %></td>
      <td><%= complaint.getSubject() %></td>
      <td><%= complaint.getStatus() %></td>
      <td><%= complaint.getRemarks() != null ? complaint.getRemarks() : "-" %></td>
    </tr>
    <% }} else { %>
    <tr>
      <td colspan="5" class="text-center">No complaints found.</td>
    </tr>
    <% } %>
    </tbody>
  </table>

  <!-- Update Complaint Form -->
  <h5 class="mt-5">Update Complaint</h5>
  <form action="<%= request.getContextPath() %>/admin" method="post" id="updateForm">
    <div class="row mb-3">
      <div class="col-md-2">
        <label class="form-label">Complaint ID</label>
        <input type="text" name="id" id="complaintId" class="form-control" readonly>
      </div>
      <div class="col-md-2">
        <label class="form-label">User ID</label>
        <input type="text" id="userId" class="form-control" readonly>
      </div>
      <div class="col-md-4">
        <label class="form-label">Subject</label>
        <input type="text" id="subject" class="form-control" readonly>
      </div>
      <div class="col-md-4">
        <label class="form-label">Status</label>
        <select name="status" id="status" class="form-select" required>
          <option value="Pending">Pending</option>
          <option value="In Progress">In Progress</option>
          <option value="Resolved">Resolved</option>
        </select>
      </div>
    </div>

    <div class="mb-3">
      <label class="form-label">Remarks</label>
      <textarea name="remarks" id="remarks" class="form-control" rows="3" required></textarea>
    </div>

    <button type="submit" class="btn btn-success">Update Complaint</button>
  </form>
</div>

<!-- JavaScript: Row click populates form -->
<script>
  document.querySelectorAll('#complaintsTable tr').forEach(row => {
    row.addEventListener('click', () => {
      document.getElementById('complaintId').value = row.dataset.id;
      document.getElementById('userId').value = row.dataset.userId;
      document.getElementById('subject').value = row.dataset.subject;
      document.getElementById('status').value = row.dataset.status;
      document.getElementById('remarks').value = row.dataset.remarks;
    });
  });
</script>
</body>
</html>
