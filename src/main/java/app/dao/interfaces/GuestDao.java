package app.dao.interfaces;

import app.dto.GuestDto;
import java.util.List;

public interface GuestDao {
    void createGuest(GuestDto guest) throws Exception;
    GuestDto findByInvitationId(long invitationId) throws Exception;
    List<GuestDto> findAll() throws Exception;
    boolean existsByInvitationId(long invitationId) throws Exception;
    void updateGuest(GuestDto guest) throws Exception;
    void deleteGuest(long invitationId) throws Exception;

    public boolean existsByUserId(long userId)throws Exception;
}


