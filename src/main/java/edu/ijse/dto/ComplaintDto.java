package edu.ijse.dto;

public class ComplaintDto {
    private int id;
    private int userId; // <-- make sure this field exists
    private String subject;
    private String status;
    private String remarks;
    private String discription;
    private String created_at;

    public ComplaintDto() {
    }

    public ComplaintDto(String created_at, String discription, int id, String remarks, String status, String subject, int userId) {
        this.created_at = created_at;
        this.discription = discription;
        this.id = id;
        this.remarks = remarks;
        this.status = status;
        this.subject = subject;
        this.userId = userId;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
