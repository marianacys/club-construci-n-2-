package app.dao;

import app.dao.interfaces.GuestDao;
import app.dto.GuestDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestDaoImplementation implements GuestDao {

    private Connection connection;

    public GuestDaoImplementation(Connection connection) {
        this.connection = connection;
    }

    public GuestDaoImplementation() {
    }

    @Override
    public void createGuest(GuestDto guest) throws Exception {
        String query = "INSERT INTO guests (invitation_id, partner_id, invitation_state) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, guest.getInvitationId());
            stmt.setLong(2, guest.getPartnerId());
            stmt.setString(3, guest.getInvitationState());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error creating guest", e);
        }
    }

    @Override
    public GuestDto findByInvitationId(long invitationId) throws Exception {
        String query = "SELECT * FROM guests WHERE invitation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, invitationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    GuestDto guest = new GuestDto();
                    guest.setInvitationId(rs.getLong("invitation_id"));
                    guest.setPartnerId(rs.getLong("partner_id"));
                    guest.setInvitationState(rs.getString("invitation_state"));
                    return guest;
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error finding guest by invitation ID", e);
        }
        return null;
    }

    @Override
    public List<GuestDto> findAll() throws Exception {
        String query = "SELECT * FROM guests";
        List<GuestDto> guests = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                GuestDto guest = new GuestDto();
                guest.setInvitationId(rs.getLong("invitation_id"));
                guest.setPartnerId(rs.getLong("partner_id"));
                guest.setInvitationState(rs.getString("invitation_state"));
                guests.add(guest);
            }
        } catch (SQLException e) {
            throw new Exception("Error retrieving all guests", e);
        }
        return guests;
    }

    @Override
    public boolean existsByInvitationId(long invitationId) throws Exception {
        String query = "SELECT COUNT(*) FROM guests WHERE invitation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, invitationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error checking if guest exists by invitation ID", e);
        }
        return false;
    }

    @Override
    public void updateGuest(GuestDto guest) throws Exception {
        String query = "UPDATE guests SET partner_id = ?, invitation_state = ? WHERE invitation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, guest.getPartnerId());
            stmt.setString(2, guest.getInvitationState());
            stmt.setLong(3, guest.getInvitationId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error updating guest", e);
        }
    }

    @Override
    public void deleteGuest(long invitationId) throws Exception {
        String query = "DELETE FROM guests WHERE invitation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, invitationId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error deleting guest", e);
        }
        
    }
    
    @Override
    public boolean existsByUserId(long userId) throws Exception{
        String query = "SELECT COUNT(*) FROM guests WHERE userid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error checking if guest exists by User ID", e);
        }
        return false;
    }
}

