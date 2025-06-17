package edu.ijse.dto;

public class ComplaintDto {
    private int id;
    private String username; // <-- make sure this field exists
    private String subject;
    private String status;
    private String remarks;

    public ComplaintDto() {
    }

    public ComplaintDto(int id, String remarks, String status, String subject, String username) {
        this.id = id;
        this.remarks = remarks;
        this.status = status;
        this.subject = subject;
        this.username = username;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username; // <-- this must exist
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
