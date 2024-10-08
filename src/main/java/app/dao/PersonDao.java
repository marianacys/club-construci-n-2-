package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import app.config.MYSQLConnection;
import app.dto.PersonDto;
import app.helper.Helper;
import app.model.Person;
import app.dao.interfaces.PersonDaoInterface;
import app.dto.InvoiceDto;
import app.dto.UserDto;

public class PersonDao implements PersonDaoInterface {

    @Override
    public boolean existsByDocument(PersonDto personDto) throws Exception {
        String query = "SELECT 1 FROM PERSON WHERE DOCUMENT = ?";
        try (PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, personDto.getDocument());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    @Override
    public void createPerson(PersonDto personDto) throws Exception {
        Person person = Helper.parse(personDto);
        String query = "INSERT INTO PERSON(NAME, DOCUMENT, CELLPHONE) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setLong(2, person.getDocument());
            preparedStatement.setLong(3, person.getCellPhone());
            preparedStatement.execute();
        }
    }

    @Override
    public void deletePerson(PersonDto personDto) throws Exception {
        String query = "DELETE FROM PERSON WHERE DOCUMENT = ?";
        try (PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, personDto.getDocument());
            preparedStatement.execute();
        }
    }

    @Override
    public PersonDto findByDocument(PersonDto personDto) throws Exception {
        String query = "SELECT ID, NAME, DOCUMENT, CELLPHONE FROM PERSON WHERE DOCUMENT = ?";
        try (PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, personDto.getDocument());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToPersonDto(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public PersonDto findByUserId(UserDto userDto) throws Exception {
        String query = "SELECT ID, NAME, DOCUMENT, CELLPHONE FROM PERSON WHERE ID = ?";
        try (PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, userDto.getPersonId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToPersonDto(resultSet);
                }
            }
        }
        return null;
    }

    public PersonDto findByPersonId(long personId) throws Exception {
        String query = "SELECT ID, NAME, DOCUMENT, CELLPHONE FROM PERSON WHERE ID = ?";
        try (PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, personId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToPersonDto(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public void updatePerson(PersonDto personDto) throws Exception {
        String query = "UPDATE PERSON SET CELLPHONE = ? WHERE DOCUMENT = ?";
        try (PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, personDto.getCellPhone());
            preparedStatement.setLong(2, personDto.getDocument());
            preparedStatement.execute();
        }
    }

    @Override
    public PersonDto findByPersonId(InvoiceDto invoiceDto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // Metodo auxiliar para mapear ResultSet a PersonDto
    private PersonDto mapResultSetToPersonDto(ResultSet resultSet) throws Exception {
        Person person = new Person();
        person.setId(resultSet.getLong("ID"));
        person.setName(resultSet.getString("NAME"));
        person.setDocument(resultSet.getLong("DOCUMENT"));
        person.setCellPhone(resultSet.getLong("CELLPHONE"));
        return Helper.parse(person);
    }

    public PersonDto findByUserId(long userId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

