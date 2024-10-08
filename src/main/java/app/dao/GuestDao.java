package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import app.config.MYSQLConnection;
import static app.config.MYSQLConnection.getConnection;
import app.helper.Helper;
import app.dao.interfaces.GuestDaoInterface;

import app.dto.UserDto;
import app.dto.GuestDto;
import app.model.Guest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestDao implements GuestDaoInterface{
    private Connection connection;
    public void addGuest(GuestDto newGuest) {
        String sql = "INSERT INTO guests (user_id, partner_id, status) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, newGuest.getUserId());
            pstmt.setLong(2, newGuest.getPartnerId());
            pstmt.setString(3, newGuest.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al agregar el invitado: " + e.getMessage());
        }
    }

    @Override
    public boolean existsByUserId(UserDto userDto) throws Exception {
        String query = "SELECT ID, USERID, PARTNERID, STATUS FROM GUEST WHERE USERID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, String.valueOf( userDto.getId() ) );
        ResultSet resulSet = preparedStatement.executeQuery();
        boolean exists = resulSet.next();
        resulSet.close();
        preparedStatement.close();
        return exists;
    }

    @Override
    public void createGuest(GuestDto guestDto) throws Exception {
        String query = "INSERT INTO GUEST( USERID, PARTNERID, STATUS ) VALUES ( ?, ?, ? ) ";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong( 1, guestDto.getUserId() );
        preparedStatement.setLong( 2, guestDto.getPartnerId() );
        preparedStatement.setString( 3, guestDto.getStatus() );

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void updateGuestStatus( GuestDto guestDto ) throws Exception {
        String query = "UPDATE GUEST SET STATUS = ? WHERE ID = ? ";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setString( 1, guestDto.getStatus() );
        preparedStatement.setLong( 2, guestDto.getId());

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void deleteGuest( GuestDto guestDto ) throws Exception {
        String query = "DELETE FROM GUEST WHERE ID = ? ";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong( 1, guestDto.getId());

        preparedStatement.execute();
        preparedStatement.close();
    }
    
    @Override
public GuestDto findByUserId(UserDto userDto) throws Exception {
    String query = "SELECT ID, USERID, PARTNERID, STATUS FROM GUEST WHERE USERID = ?";
    try (PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query)) {
        preparedStatement.setLong(1, userDto.getId());
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                GuestDto guest = new GuestDto();
                guest.setId(resultSet.getLong("ID"));
                guest.setUserId(resultSet.getLong("USERID"));
                guest.setPartnerId(resultSet.getLong("PARTNERID"));
                guest.setStatus(resultSet.getString("STATUS"));

                return guest;
            }
        }
    }
    return null;
}

    public List<GuestDto> findActiveGuestsByPartnerId(long partnerId) {
        List<GuestDto> activeGuests = new ArrayList<>();
        String sql = "SELECT * FROM guests WHERE partner_id = ? AND status = 'Activo'";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setLong(1, partnerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                GuestDto guest = new GuestDto();
                guest.setId(rs.getLong("id"));
                guest.setUserId(rs.getLong("user_id"));
                guest.setPartnerId(rs.getLong("partner_id"));
                guest.setStatus(rs.getString("status"));
                activeGuests.add(guest);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return activeGuests;
    }
@Override
public GuestDto findById(long guestId) throws Exception {
    String query = "SELECT ID, USERID, PARTNERID, STATUS FROM GUEST WHERE ID = ?";
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        pstmt.setLong(1, guestId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            GuestDto guest = new GuestDto();
            guest.setId(rs.getLong("ID"));
            guest.setUserId(rs.getLong("USERID"));
            guest.setPartnerId(rs.getLong("PARTNERID"));
            guest.setStatus(rs.getString("STATUS"));
            return guest;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error al buscar el invitado: " + e.getMessage());
    }
    return null;
}
@Override
public List<GuestDto> findActiveGuests() throws Exception {
    List<GuestDto> activeGuests = new ArrayList<>();
    String sql = "SELECT * FROM guests WHERE status = 'Activo'";

    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            GuestDto guest = new GuestDto();
            guest.setId(rs.getLong("ID"));
            guest.setUserId(rs.getLong("USERID"));
            guest.setPartnerId(rs.getLong("PARTNERID"));
            guest.setStatus(rs.getString("STATUS"));
            activeGuests.add(guest);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error al buscar los invitados activos: " + e.getMessage());
    }
    return activeGuests;
}
@Override
public GuestDto findByDocument(String document) throws Exception {
    String query = "SELECT ID, USERID, PARTNERID, STATUS FROM GUEST WHERE DOCUMENT = ?";
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        pstmt.setString(1, document);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            GuestDto guest = new GuestDto();
            guest.setId(rs.getLong("ID"));
            guest.setUserId(rs.getLong("USERID"));
            guest.setPartnerId(rs.getLong("PARTNERID"));
            guest.setStatus(rs.getString("STATUS"));
            return guest;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error al buscar el invitado por documento: " + e.getMessage());
    }
    return null;
}

    public GuestDto findByUserId(long userId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void updateGuest(GuestDto guestDto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
