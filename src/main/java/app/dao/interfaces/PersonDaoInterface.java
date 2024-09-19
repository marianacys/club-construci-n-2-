package app.dao.interfaces;

import app.dto.InvoiceDto;
import app.dto.PersonDto;
import app.dto.UserDto;


public interface PersonDaoInterface {
    public boolean existsByDocument(PersonDto personDto ) throws Exception;
    public void createPerson(PersonDto personDto ) throws Exception;
    public void updatePerson(PersonDto personDto ) throws Exception;
    public void deletePerson(PersonDto personDto ) throws Exception;
    public PersonDto findByDocument(PersonDto personDto ) throws Exception;
    public PersonDto findByPersonId( InvoiceDto invoiceDto ) throws Exception;    
    public PersonDto findByUserId( UserDto userDto ) throws Exception;    
}
