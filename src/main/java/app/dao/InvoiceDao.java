package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import app.config.MYSQLConnection;
import app.dao.interfaces.InvoiceDaoInterface;
import app.dto.InvoiceDto;
import app.dto.PartnerDto;
import app.dto.PersonDto;
import app.helper.Helper;
import app.model.Invoice;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class InvoiceDao implements InvoiceDaoInterface {

    @Override
    public double amountActiveInvoices(PersonDto personDto) throws Exception {
        String query = "SELECT SUM(AMOUNT) AS AMOUNT FROM INVOICE WHERE PERSONID = ? AND STATUS = ?";
        try (Connection connection = MYSQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, personDto.getId());
            preparedStatement.setString(2, "PENDIENTE");
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getDouble("AMOUNT") : 0;
        }
    }

    @Override
    public InvoiceDto firstActiveInvoice(PartnerDto partnerDto) throws Exception {
        String query = "SELECT ID, PERSONID, PARTNERID, CREATIONDATE, AMOUNT, STATUS FROM INVOICE WHERE PARTNERID = ? AND STATUS = ? ORDER BY CREATIONDATE DESC";
        try (Connection connection = MYSQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, partnerDto.getId());
            preparedStatement.setString(2, "PENDIENTE");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToInvoiceDto(resultSet);
            }
            return null;
        }
    }

    @Override
    public InvoiceDto firstInvoiceByPartnerId(PartnerDto partnerDto) throws Exception {
        String query = "SELECT ID, PERSONID, PARTNERID, CREATIONDATE, AMOUNT, STATUS FROM INVOICE WHERE PARTNERID = ? ORDER BY CREATIONDATE DESC";
        try (Connection connection = MYSQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, partnerDto.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToInvoiceDto(resultSet);
            }
            return null;
        }
    }

    @Override
    public InvoiceDto firstInvoiceByPersonId(PersonDto personDto) throws Exception {
        String query = "SELECT ID, PERSONID, PARTNERID, CREATIONDATE, AMOUNT, STATUS FROM INVOICE WHERE PERSONID = ? ORDER BY CREATIONDATE DESC";
        try (Connection connection = MYSQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, personDto.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToInvoiceDto(resultSet);
            }
            return null;
        }
    }

    @Override
    public long lastInvoiceByPartnerId(PartnerDto partnerDto) throws Exception {
        String query = "SELECT MAX(ID) AS ID FROM INVOICE WHERE PARTNERID = ?";
        try (Connection connection = MYSQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, partnerDto.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getLong("ID") : 0;
        }
    }

    @Override
    public long lastInvoiceByPersonId(PersonDto personDto) throws Exception {
        String query = "SELECT MAX(ID) AS ID FROM INVOICE WHERE PERSONID = ?";
        try (Connection connection = MYSQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, personDto.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getLong("ID") : 0;
        }
    }

    @Override
    public void createInvoice(InvoiceDto invoiceDto) throws Exception {
        String query = "INSERT INTO INVOICE (PERSONID, PARTNERID, CREATIONDATE, AMOUNT, STATUS) VALUES (?, ?, ?, 0, ?)";
        try (Connection connection = MYSQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, invoiceDto.getPersonId());
            preparedStatement.setLong(2, invoiceDto.getPartnerId());
            preparedStatement.setString(3, LocalDateTime.now().toString());
            preparedStatement.setString(4, "PENDIENTE");
            preparedStatement.execute();
        }
    }

    @Override
    public void updateInvoiceAmount(InvoiceDto invoiceDto) throws Exception {
        String query = "UPDATE INVOICE SET AMOUNT = ? WHERE ID = ?";
        try (Connection connection = MYSQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, invoiceDto.getAmount());
            preparedStatement.setLong(2, invoiceDto.getId());
            preparedStatement.execute();
        }
    }

    @Override
    public void deleteInvoice(InvoiceDto invoiceDto) throws Exception {
        String query = "DELETE FROM INVOICE WHERE ID = ?";
        try (Connection connection = MYSQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, invoiceDto.getId());
            preparedStatement.execute();
        }
    }

    @Override
    public void cancelInvoice(InvoiceDto invoiceDto) throws Exception {
        String query = "UPDATE INVOICE SET STATUS = ? WHERE ID = ?";
        try (Connection connection = MYSQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "CANCELADA");
            preparedStatement.setLong(2, invoiceDto.getId());
            preparedStatement.execute();
        }
    }

    @Override
    public ArrayList<InvoiceDto> listClubInvoices() throws Exception {
        ArrayList<InvoiceDto> listInvoices = new ArrayList<>();
        String query = "SELECT ID, PERSONID, PARTNERID, CREATIONDATE, AMOUNT, STATUS FROM INVOICE ORDER BY CREATIONDATE DESC";
        try (Connection connection = MYSQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                listInvoices.add(mapResultSetToInvoiceDto(resultSet));
            }
        }
        return listInvoices;
    }

    public ArrayList<InvoiceDto> listInvoicesByPartnerId(long partnerId) throws Exception {
        ArrayList<InvoiceDto> invoices = new ArrayList<>();
        String sql = "SELECT * FROM INVOICE WHERE PARTNERID = ?"; 

        try (Connection connection = MYSQLConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, partnerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                invoices.add(mapResultSetToInvoiceDto(rs));
            }
        }
        return invoices;
    }

    public ArrayList<InvoiceDto> listInvoicesByGuestId(long guestId) throws Exception {
        ArrayList<InvoiceDto> invoices = new ArrayList<>();
        String sql = "SELECT * FROM INVOICE WHERE GUESTID = ?";

        try (Connection connection = MYSQLConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, guestId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                invoices.add(mapResultSetToInvoiceDto(rs));
            }
        }
        return invoices;
    }

    private InvoiceDto mapResultSetToInvoiceDto(ResultSet rs) throws SQLException {
        InvoiceDto invoice = new InvoiceDto();
        invoice.setId(rs.getLong("ID"));
        invoice.setPersonId(rs.getLong("PERSONID"));
        invoice.setPartnerId(rs.getLong("PARTNERID"));
        invoice.setCreationDate(rs.getDate("CREATIONDATE"));
        invoice.setAmount(rs.getDouble("AMOUNT"));
        invoice.setStatus(rs.getString("STATUS"));
        return invoice;
    }
}


