package app.dto;

import java.sql.Date;

public class InvoiceDto {
    private UserDto idUser;
    private Date generationDate;
    private double amount;
    private String description;
    private boolean status;

    public InvoiceDto() {}

    public UserDto getIdUser() {
        return idUser;
    }

    public void setIdUser(UserDto idUser) {
        this.idUser = idUser;
    }

    public Date getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(Date generationDate) {
        this.generationDate = generationDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

