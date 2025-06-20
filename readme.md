# Project Name

## Project Overview
This project is a **Complaint Management System (CMS)** built using Java Servlets, JSP, and JDBC.  
It allows employees to submit complaints, and admins to view, update the status, and manage complaints efficiently.  
The system uses a relational database (e.g., MySQL) for persistent storage and follows the MVC design pattern.

### Key Features
- Employee complaint submission
- Admin dashboard to view all complaints
- Update complaint status and add remarks
- Delete complaints
- Secure data handling and validations
- Responsive and user-friendly UI with Bootstrap

---

## Setup and Configuration Guide

### Prerequisites
- JDK 17 or higher
- Apache Tomcat 11.0.7 or compatible Servlet container
- MySQL Server
- Maven dependency management
- IDE  IntelliJ IDEA

### Database Setup
1. Create the database and tables:

```sql
CREATE DATABASE cms;

USE cms;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('EMPLOYEE','ADMIN') DEFAULT 'EMPLOYEE',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) 

CREATE TABLE `complaints` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `subject` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `status` enum('Pending','In Progress','Resolved') DEFAULT 'Pending',
  `remarks` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `complaints_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
