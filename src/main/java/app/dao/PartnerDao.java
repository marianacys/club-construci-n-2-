package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;

import app.config.MYSQLConnection;
import app.dto.GuestDto;
import app.dto.PartnerDto;
import app.dto.UserDto;
import app.helper.Helper;
import app.model.Partner;
import app.dao.interfaces.PartnerDaoInterface;
import app.dto.InvoiceDto;

public class PartnerDao implements PartnerDaoInterface {

    @Override
    public boolean existsByUserId(UserDto userDto) throws Exception {
        String query = "SELECT ID FROM PARTNER WHERE USERID = ?";
        try (PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, userDto.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    @Override
    public void createPartner(PartnerDto partnerDto) throws Exception {
        String query = "INSERT INTO PARTNER (USERID, TYPE, AMOUNT, CREATIONDATE) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, partnerDto.getUserId());
            preparedStatement.setString(2, partnerDto.getType());
            preparedStatement.setDouble(3, partnerDto.getAmount());
            preparedStatement.setString(4, LocalDateTime.now().toString());
            preparedStatement.execute();
        }
    }

    @Override
    public void updateAmountPartner(PartnerDto partnerDto) throws Exception {
        String query = "UPDATE PARTNER SET AMOUNT = ? WHERE ID = ?";
        try (PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setDouble(1, partnerDto.getAmount());
            preparedStatement.setLong(2, partnerDto.getId());
            preparedStatement.execute();
        }
    }

    @Override
    public void updateTypePartner(PartnerDto partnerDto) throws Exception {
        String query = "UPDATE PARTNER SET TYPE = ? WHERE ID = ?";
        try (PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, partnerDto.getType());
            preparedStatement.setLong(2, partnerDto.getId());
            preparedStatement.execute();
        }
    }

    @Override
    public void deletePartner(PartnerDto partnerDto) throws Exception {
        String query = "DELETE FROM PARTNER WHERE ID = ?";
        try (PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, partnerDto.getId());
            preparedStatement.execute();
        }
    }

    @Override
    public PartnerDto findByUserId(UserDto userDto) throws Exception {
        String query = "SELECT * FROM PARTNER WHERE USERID = ?";
        PartnerDto partner = null;

        try (Connection connection = MYSQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userDto.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                partner = new PartnerDto();
                partner.setId(resultSet.getInt("ID"));
                partner.setUserId(resultSet.getLong("USERID"));
                partner.setType(resultSet.getString("TYPE"));
                partner.setAmount(resultSet.getDouble("AMOUNT"));
                partner.setCreationDate(resultSet.getDate("CREATIONDATE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return partner;
    }

@Override
public PartnerDto findByGuestPartnerId(GuestDto guestDto) throws Exception {
    String query = "SELECT ID, USERID, TYPE, AMOUNT, CREATIONDATE FROM PARTNER WHERE ID = ?";
    try (PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query)) {
        preparedStatement.setLong(1, guestDto.getPartnerId());
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                PartnerDto partnerDto = new PartnerDto();
                partnerDto.setId(resultSet.getLong("ID"));
                partnerDto.setUserId(resultSet.getLong("USERID"));
                partnerDto.setType(resultSet.getString("TYPE"));
                partnerDto.setAmount(resultSet.getDouble("AMOUNT"));
                partnerDto.setCreationDate(resultSet.getDate("CREATIONDATE"));
                
                return partnerDto; 
            }
        }
    }
    return null; 
}

public PartnerDto findByPartnerId(long partnerId) throws Exception {
    String query = "SELECT ID, USERID, TYPE, AMOUNT, CREATIONDATE FROM PARTNER WHERE ID = ?";
    try (PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query)) {
        preparedStatement.setLong(1, partnerId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Partner partner = new Partner();
            partner.setId(resultSet.getLong("ID"));
            partner.setUserId(resultSet.getLong("USERID"));
            partner.setType(resultSet.getString("TYPE"));
            partner.setAmount(resultSet.getDouble("AMOUNT"));
            partner.setCreationDate(resultSet.getDate("CREATIONDATE"));

            return Helper.parse(partner); // Asumiendo que Helper.parse convierte a PartnerDto
        }
    }
    return null; // Retorna null si no se encuentra
}

    
    @Override
public long numberPertnersVIP() throws Exception {
    String query = "SELECT COUNT(ID) AS NUMBERVIP FROM PARTNER WHERE TYPE = ?";
    try (PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query)) {
        preparedStatement.setString(1, "VIP");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getLong("NUMBERVIP");
        }
    }
    return 0;
}


    public PartnerDto findById(int partnerId) throws Exception {
        String query = "SELECT ID, USERID, TYPE, AMOUNT, CREATIONDATE FROM PARTNER WHERE ID = ?";
        PartnerDto partner = null;

        try (Connection connection = MYSQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, partnerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                partner = new PartnerDto();
                partner.setId(resultSet.getInt("ID"));
                partner.setUserId(resultSet.getLong("USERID"));
                partner.setType(resultSet.getString("TYPE"));
                partner.setAmount(resultSet.getDouble("AMOUNT"));
                partner.setCreationDate(resultSet.getDate("CREATIONDATE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al buscar el socio por ID: " + e.getMessage());
        }

        return partner;
    }

@Override
public PartnerDto findByPartnerId(InvoiceDto invoiceDto) throws Exception {
    String query = "SELECT ID, USERID, TYPE, AMOUNT, CREATIONDATE FROM PARTNER WHERE ID = ?";
    
    try (PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query)) {
        preparedStatement.setLong(1, invoiceDto.getPartnerId()); // Se asume que InvoiceDto tiene un método getPartnerId
        
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                PartnerDto partnerDto = new PartnerDto();
                partnerDto.setId(resultSet.getLong("ID"));
                partnerDto.setUserId(resultSet.getLong("USERID"));
                partnerDto.setType(resultSet.getString("TYPE"));
                partnerDto.setAmount(resultSet.getDouble("AMOUNT"));
                partnerDto.setCreationDate(resultSet.getDate("CREATIONDATE"));

                return partnerDto; // Devuelve el objeto PartnerDto
            }
        }
    }
    return null; // Retorna null si no se encuentra
}

    @Override
    public void getPartnerAmountIncreaseDto() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}


