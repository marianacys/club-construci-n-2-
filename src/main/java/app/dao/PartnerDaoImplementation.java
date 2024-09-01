package app.dao;

import app.config.MYSQLConnection;
import app.dao.interfaces.PartnerDao;
import app.dto.PartnerDto;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartnerDaoImplementation implements PartnerDao {

    private Connection connection;

    public PartnerDaoImplementation() {
        this.connection = MYSQLConnection.getConnection();
    }

  

    @Override
    public void createPartner(PartnerDto partner) throws Exception {
        String query = "INSERT INTO partners (record_id, available_funds, subscription_type, affiliation_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, partner.getRecordId());
            stmt.setFloat(2, partner.getAvailableFunds());
            stmt.setString(3, partner.getSubscriptionType());
            stmt.setDate(4, partner.getAffiliationDate());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error creating partner", e);
        }
    }

    @Override
    public PartnerDto findByRecordId(long recordId) throws Exception {
        String query = "SELECT * FROM partners WHERE record_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, recordId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PartnerDto partner = new PartnerDto();
                    partner.setRecordId(rs.getLong("record_id"));
                    partner.setAvailableFunds(rs.getFloat("available_funds"));
                    partner.setSubscriptionType(rs.getString("subscription_type"));
                    partner.setAffiliationDate(rs.getDate("affiliation_date"));
                    return partner;
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error finding partner by record ID", e);
        }
        return null;
    }

    @Override
    public List<PartnerDto> findAll() throws Exception {
        String query = "SELECT * FROM partners";
        List<PartnerDto> partners = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PartnerDto partner = new PartnerDto();
                partner.setRecordId(rs.getLong("record_id"));
                partner.setAvailableFunds(rs.getFloat("available_funds"));
                partner.setSubscriptionType(rs.getString("subscription_type"));
                partner.setAffiliationDate(rs.getDate("affiliation_date"));
                partners.add(partner);
            }
        } catch (SQLException e) {
            throw new Exception("Error retrieving all partners", e);
        }
        return partners;
    }

    @Override
    public boolean existsByRecordId(long recordId) throws Exception {
        String query = "SELECT COUNT(*) FROM partners WHERE record_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, recordId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error checking if partner exists by record ID", e);
        }
        return false;
    }

    @Override
    public void updatePartner(PartnerDto partner) throws Exception {
        String query = "UPDATE partners SET available_funds = ?, subscription_type = ?, affiliation_date = ? WHERE record_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setFloat(1, partner.getAvailableFunds());
            stmt.setString(2, partner.getSubscriptionType());
            stmt.setDate(3, partner.getAffiliationDate());
            stmt.setLong(4, partner.getRecordId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error updating partner", e);
        }
    }

    @Override
    public void deletePartner(long recordId) throws Exception {
        String query = "DELETE FROM partners WHERE record_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, recordId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error deleting partner", e);
        }
    }

    @Override
    public PartnerDto findPartnerById(long id) throws Exception {
        String query = "SELECT * FROM partner WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PartnerDto partner = new PartnerDto();
                    partner.setRecordId(rs.getLong("record_id"));
                    partner.setAvailableFunds(rs.getFloat("available_funds"));
                    partner.setSubscriptionType(rs.getString("subscription_type"));
                    partner.setAffiliationDate(rs.getDate("affiliation_date"));
                    return partner;
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error finding partner by record ID", e);
        }
        return null;
    }

    @Override
    public boolean existsByUserId(long userId) throws Exception {
        String query = "SELECT COUNT(*) FROM partner WHERE userid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error checking if partner exists by record ID", e);
        }
        return false;
    }
}
