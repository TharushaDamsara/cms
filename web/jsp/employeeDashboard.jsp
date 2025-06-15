<%--
  Created by IntelliJ IDEA.
  User: tharusha
  Date: 6/13/2025
  Time: 12:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
    <c:forEach var="complaint" items="${complaints}">
      <tr>
        <td>${complaint.id}</td>
        <td>${complaint.subject}</td>
        <td>${complaint.status}</td>
        <td>
          <a href="editComplaint?id=${complaint.id}" class="btn btn-warning btn-sm">Edit</a>
          <form action="deleteComplaint" method="post" style="display:inline;">
            <input type="hidden" name="id" value="${complaint.id}">
            <button type="submit" class="btn btn-danger btn-sm">Delete</button>
          </form>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
</body>
</html>
