package app.service;

import app.service.interfaces.PartnerServiceInterface;
import app.dao.PersonDao;
import app.dao.PartnerDao;
import app.dao.InvoiceDao;
import app.dao.InvoiceDetailDao;
import app.dao.UserDao;
import app.dto.InvoiceDto;
import app.dto.PersonDto;
import app.dto.PartnerDto;
import app.dto.UserDto;
import java.util.Scanner;

public class PartnerService implements PartnerServiceInterface {
    private final PersonDao personDao = new PersonDao();
    private final UserDao userDao = new UserDao();
    private final PartnerDao partnerDao = new PartnerDao();
    private final InvoiceDao invoiceDao = new InvoiceDao();
    private final InvoiceDetailDao invoiceDetailDao = new InvoiceDetailDao();

@Override
public void createPartner() throws Exception {
    PersonDto personDto = getExistingPerson();
    UserDto userDto = getExistingUser(personDto);
    
    if (partnerDao.findByUserId(userDto) != null) {
        throw new Exception(personDto.getName() + " ya es SOCIO del club");
    }

    PartnerDto partnerDto = new PartnerDto();
    partnerDto.setUserId(userDto.getId());
    partnerDto.setType("REGULAR");  

    if ("VIP".equals(partnerDto.getType()) && partnerDao.numberPertnersVIP() >= 5) {
        throw new Exception("Cupo de socios VIP copado");
    }

    partnerDto.setAmount(0.0); 
    partnerDao.createPartner(partnerDto);
}

public void updateAmountPartner(double incremento) throws Exception {
    PersonDto personDto = getExistingPerson();
    UserDto userDto = getExistingUser(personDto);
    
    PartnerDto partnerDto = partnerDao.findByUserId(userDto);
    if (partnerDto == null) {
        throw new Exception(personDto.getName() + " NO es SOCIO del club");
    }

    // Incrementar el monto del socio
    partnerDto.setAmount(partnerDto.getAmount() + incremento);
    partnerDao.updateAmountPartner(partnerDto);

    // Procesar las facturas activas
    processActiveInvoices(partnerDto);
}

    @Override
    public void deletePartner() throws Exception {
        PersonDto personDto = getExistingPerson();
        validateNoActiveInvoices(personDto);

        UserDto userDto = getExistingUser(personDto);
        PartnerDto partnerDto = partnerDao.findByUserId(userDto);
        if (partnerDto == null) {
            throw new Exception("No existe el socio");
        }

        deleteAllInvoices(partnerDto);
        partnerDao.deletePartner(partnerDto);
    }

    @Override
    public void deletePartner(UserDto userDto) throws Exception {
        PersonDto personDto = personDao.findByUserId(userDto);
        if (personDto == null) {
            throw new Exception("No existe la persona");
        }

        validateNoActiveInvoices(personDto);

        PartnerDto partnerDto = partnerDao.findByUserId(userDto);
        if (partnerDto == null) {
            throw new Exception("No existe el socio");
        }

        deleteAllInvoices(partnerDto);
        partnerDao.deletePartner(partnerDto);
    }

    @Override
    public void updateTypePartner(PartnerDto partnerDto) throws Exception {
        partnerDto.setType("VIP");
        partnerDao.updateTypePartner(partnerDto);
    }
    private PersonDto getExistingPerson() throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.getPersonDocumentDto();
        personDto = personDao.findByDocument(personDto);
        if (personDto == null) {
            throw new Exception("No existe la persona");
        }
        return personDto;
    }

    private UserDto getExistingUser(PersonDto personDto) throws Exception {
        UserDto userDto = userDao.findByPersonId(personDto);
        if (userDto == null) {
            throw new Exception("No se encontró ningún usuario con el número de identificación ingresado");
        }
        return userDto;
    }

    private void validateNoActiveInvoices(PersonDto personDto) throws Exception {
        double amountActiveInvoices = invoiceDao.amountActiveInvoices(personDto);
        if (amountActiveInvoices > 0) {
            throw new Exception(personDto.getName() + " tiene facturas pendientes de pago");
        }
    }

    private void deleteAllInvoices(PartnerDto partnerDto) throws Exception {
        InvoiceDto invoiceDto = invoiceDao.firstInvoiceByPartnerId(partnerDto);
        while (invoiceDto != null) {
            invoiceDetailDao.deleteInvoiceDetail(invoiceDto);
            invoiceDao.deleteInvoice(invoiceDto);
            invoiceDto = invoiceDao.firstInvoiceByPartnerId(partnerDto);
        }
    }

   private void processActiveInvoices(PartnerDto partnerDto) throws Exception {
    InvoiceDto invoiceDto = invoiceDao.firstActiveInvoice(partnerDto);
    while (invoiceDto != null) {
        if (partnerDto.getAmount() >= invoiceDto.getAmount()) {
            // Cancelar factura y reducir el monto del socio
            invoiceDao.cancelInvoice(invoiceDto);
            partnerDto.setAmount(partnerDto.getAmount() - invoiceDto.getAmount());
            partnerDao.updateAmountPartner(partnerDto);

            invoiceDto = invoiceDao.firstActiveInvoice(partnerDto);
        } else {
            invoiceDto = null;
            
        }
    }
   }

    @Override
    public void updateAmountPartner() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   
}

