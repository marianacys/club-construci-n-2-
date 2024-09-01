package app.dto;

import java.sql.Date;

public class PartnerDto {
    private long recordId;
    private float availableFunds;
    private String subscriptionType;
    private Date affiliationDate; 
    private long userId; // Agregar un campo para el ID de usuario

    public PartnerDto() {}

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public float getAvailableFunds() {
        return availableFunds;
    }

    public void setAvailableFunds(float availableFunds) {
        this.availableFunds = availableFunds;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Date getAffiliationDate() { 
        return affiliationDate;
    }

    public void setAffiliationDate(Date affiliationDate) {
        this.affiliationDate = affiliationDate;
    }

    public long getUserId() { // Método UserId
        return userId;
    }

    public void setUserId(long userId) { // Método UserId
        this.userId = userId;
    }
}

