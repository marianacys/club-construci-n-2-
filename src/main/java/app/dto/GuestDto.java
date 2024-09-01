package app.dto;

public class GuestDto {
    private long invitationId; 
    private long partnerId;    
    private String invitationState;
    private long userId;

    public GuestDto() {}

    public long getInvitationId() { 
        return invitationId;
    }

    public void setInvitationId(long invitationId) { 
        this.invitationId = invitationId;
    }

    public long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(long partnerId) {
        this.partnerId = partnerId;
    }

    public String getInvitationState() {
        return invitationState;
    }

    public void setInvitationState(String invitationState) { 
        this.invitationState = invitationState;
    }
    public long getUserId() { // Método UserId
        return userId;
    }

    public void setUserId(long userId) { // Método UserId
        this.userId = userId;
    }
}
 

