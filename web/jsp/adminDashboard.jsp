<%--
  Created by IntelliJ IDEA.
  User: tharusha
  Date: 6/13/2025
  Time: 12:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
    <c:forEach var="complaint" items="${complaints}">
      <tr>
        <td>${complaint.id}</td>
        <td>${complaint.username}</td>
        <td>${complaint.subject}</td>
        <td>${complaint.status}</td>
        <td>${complaint.remarks}</td>
        <td>
          <form action="updateStatus" method="post" class="d-flex gap-1">
            <input type="hidden" name="id" value="${complaint.id}">
            <select name="status" class="form-select form-select-sm">
              <option ${complaint.status == 'Pending' ? 'selected' : ''}>Pending</option>
              <option ${complaint.status == 'In Progress' ? 'selected' : ''}>In Progress</option>
              <option ${complaint.status == 'Resolved' ? 'selected' : ''}>Resolved</option>
            </select>
            <input type="text" name="remarks" placeholder="Remarks" value="${complaint.remarks}" class="form-control form-control-sm" required>
            <button type="submit" class="btn btn-sm btn-success">Update</button>
          </form>
          <form action="deleteComplaint" method="post" style="margin-top:5px;">
            <input type="hidden" name="id" value="${complaint.id}">
            <button type="submit" class="btn btn-sm btn-danger">Delete</button>
          </form>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
</body>
</html>

