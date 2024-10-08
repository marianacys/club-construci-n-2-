package app.dto;

import java.sql.Date;

public class InvoiceDto {
    private long id;
    private long personId;
    private long partnerId;
    private Date creationDate;
    private double amount;
    private String status;

    // Constructor 
    public InvoiceDto(long id, long personId, long partnerId, double amount, Date creationDate, String status) {
        this.id = id;
        this.personId = personId;
        this.partnerId = partnerId;
        this.amount = amount;
        this.creationDate = creationDate;
        this.status = status;
    }

    // Constructor vacio
    public InvoiceDto() {
        this.id = 0;
        this.personId = 0;
        this.partnerId = 0;
        this.amount = 0.0;
        this.creationDate = new Date(System.currentTimeMillis());
        this.status = "Pendiente";
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(long partnerId) {
        this.partnerId = partnerId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}

