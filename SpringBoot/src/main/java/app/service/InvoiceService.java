package app.service;

import app.controllers.Utils;
import app.dao.GuestDao;
import app.dao.InvoiceDao;
import app.dao.InvoiceDetailDao;
import app.dao.PartnerDao;
import app.dao.PersonDao;
import app.dao.UserDao;
import app.dto.GuestDto;
import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import app.dto.PartnerDto;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.service.interfaces.InvoiceServiceInterface;

import java.util.ArrayList;

public class InvoiceService implements InvoiceServiceInterface {
    private final PersonDao personDao = new PersonDao();
    private final UserDao userDao = new UserDao();
    private final PartnerDao partnerDao = new PartnerDao();
    private final GuestDao guestDao = new GuestDao();
    private final InvoiceDao invoiceDao = new InvoiceDao();
    private final InvoiceDetailDao invoiceDetailDao = new InvoiceDetailDao();

    @Override
    public void createInvoice() throws Exception {
        PersonDto personDto = getPersonByDocument();
        UserDto userDto = getUserByPersonId(personDto);
        InvoiceDto invoiceDto = new InvoiceDto();
        PartnerDto partnerDto = getPartnerOrGuestInfo(userDto, personDto, invoiceDto);
        createInvoiceDetails(invoiceDto, personDto, partnerDto);
    }

    private PersonDto getPersonByDocument() throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.getPersonDocumentDto();

        if (!personDao.existsByDocument(personDto)) {
            throw new Exception("No existe ninguna persona con el documento: " + personDto.getDocument());
        }
        return personDao.findByDocument(personDto);
    }

    private UserDto getUserByPersonId(PersonDto personDto) throws Exception {
        UserDto userDto = userDao.findByPersonId(personDto);
        if (userDto == null) {
            throw new Exception(personDto.getName() + " no tiene USUARIO");
        }
        return userDto;
    }

    private PartnerDto getPartnerOrGuestInfo(UserDto userDto, PersonDto personDto, InvoiceDto invoiceDto) throws Exception {
        PartnerDto partnerDto = null;

        if (userDto.getRole().equals("SOCIO")) {
            partnerDto = partnerDao.findByUserId(userDto);
            if (partnerDto == null) {
                throw new Exception(personDto.getName() + " no es SOCIO");
            }
            invoiceDto.setPartnerId(partnerDto.getId());
        } else {
            GuestDto guestDto = guestDao.findByUserId(userDto);
            if (guestDto == null) {
                throw new Exception(personDto.getName() + " no es INVITADO");
            }
            partnerDto = partnerDao.findByGuestPartnerId(guestDto);
            invoiceDto.setPartnerId(partnerDto.getId());
        }

        invoiceDto.setPersonId(personDto.getId());
        return partnerDto;
    }

    private void createInvoiceDetails(InvoiceDto invoiceDto, PersonDto personDto, PartnerDto partnerDto) throws Exception {
        invoiceDao.createInvoice(invoiceDto);
        invoiceDto.setId(invoiceDao.lastInvoiceByPersonId(personDto));

        boolean continueRead = true;
        while (continueRead) {
            InvoiceDetailDto invoiceDetailDto = new InvoiceDetailDto();
            invoiceDetailDto.setInvoiceId(invoiceDto.getId());
            invoiceDetailDto.getDescription();
            invoiceDetailDto.getInvoiceDetailAmountDto();
            invoiceDetailDto.setItemNumber(invoiceDetailDao.countInvoiceDetails(invoiceDto));
            invoiceDetailDao.createInvoiceDetail(invoiceDetailDto);

            System.out.println("1. Para más detalles");
            String continueReadConsole = Utils.getReader().nextLine();
            continueRead = continueReadConsole.equals("1");
        }

        finalizeInvoice(invoiceDto, partnerDto);
    }

    private void finalizeInvoice(InvoiceDto invoiceDto, PartnerDto partnerDto) throws Exception {
        invoiceDto.setAmount(invoiceDetailDao.totalInvoiceDetails(invoiceDto));
        invoiceDao.updateInvoiceAmount(invoiceDto);
        if (partnerDto.getAmount() >= invoiceDto.getAmount()) {
            invoiceDao.cancelInvoice(invoiceDto);
            invoiceDto.setAmount(partnerDto.getAmount() - invoiceDto.getAmount());
            partnerDao.updateAmountPartner(partnerDto);
        }
    }

    @Override
    public void historyInvoice() throws Exception {
        ArrayList<InvoiceDto> listInvoices = invoiceDao.listClubInvoices();
        if (listInvoices.isEmpty()) {
            throw new Exception("No hay historial de facturación");
        }

        for (InvoiceDto invoiceDto : listInvoices) {
            displayInvoiceHistory(invoiceDto);
        }
    }

    private void displayInvoiceHistory(InvoiceDto invoiceDto) throws Exception {
        PersonDto personDto = personDao.findByPersonId(invoiceDto);
        PartnerDto partnerDto = partnerDao.findByPartnerId(invoiceDto);
        UserDto userDto = userDao.findByUserId(partnerDto);
        PersonDto personPartnerDto = personDao.findByUserId(userDto);
        
        System.out.println("Responsable: " + personDto.getName() + 
                           ", Socio: " + personPartnerDto.getName() + 
                           ", Fecha: " + invoiceDto.getCreationDate() + 
                           ", Monto: " + invoiceDto.getAmount() + 
                           ", Estado: " + invoiceDto.getStatus());
    }

    @Override
    public void historyPartnerInvoice(PartnerDto partnerDto) throws Exception {
        ArrayList<InvoiceDto> listInvoices = invoiceDao.listInvoicesByPartnerId(partnerDto.getId());
        if (listInvoices.isEmpty()) {
            throw new Exception("No hay historial de facturación para el socio " + partnerDto.getName());
        }

        System.out.println("Historial de facturas para el socio: " + partnerDto.getName());
        for (InvoiceDto invoiceDto : listInvoices) {
            PersonDto personDto = personDao.findByPersonId(invoiceDto.getPersonId());
            System.out.println("Factura ID: " + invoiceDto.getId() + 
                               ", Fecha: " + invoiceDto.getCreationDate() + 
                               ", Monto: $" + invoiceDto.getAmount() + 
                               ", Responsable: " + personDto.getName());
        }
    }

    @Override
    public void historyGuestInvoice(GuestDto guestDto) throws Exception {
        PartnerDto partnerDto = partnerDao.findByGuestPartnerId(guestDto);
        if (partnerDto == null) {
            throw new Exception("No se encontro al socio responsable para este invitado.");
        }

        ArrayList<InvoiceDto> listInvoices = invoiceDao.listInvoicesByGuestId(guestDto.getId());
        if (listInvoices.isEmpty()) {
            throw new Exception("No hay historial de facturación para el invitado " + guestDto.getUserId());
        }

        System.out.println("Historial de facturas para el invitado: " + guestDto.getUserId());
        for (InvoiceDto invoiceDto : listInvoices) {
            System.out.println("Factura ID: " + invoiceDto.getId() + 
                               ", Fecha: " + invoiceDto.getCreationDate() + 
                               ", Monto: $" + invoiceDto.getAmount());
        }
    }
    @Override
public void createGuestInvoice(GuestDto guestDto) throws Exception {
    if (guestDto == null) {
        throw new Exception("El invitado no puede ser nulo");
    }

    // Obtener la informacion de la persona asociada al invitado
    PersonDto personDto = personDao.findByUserId(guestDto.getUserId());
    if (personDto == null) {
        throw new Exception("No se encontro información de la persona para el invitado: " + guestDto.getUserId());
    }

    // Crear la nueva factura
    InvoiceDto invoiceDto = new InvoiceDto();
    invoiceDto.setPersonId(personDto.getId());
    invoiceDto.setPartnerId(guestDto.getId());
    invoiceDto.setStatus("PENDIENTE"); 

    // Crear detalles de la factura
    createInvoiceDetails(invoiceDto, personDto, null);

    System.out.println("Factura creada exitosamente para el invitado: " + guestDto.getUserId());
}

    @Override
    public void createPartnerInvoice(PartnerDto partnerDto) throws Exception {
        
    }

}


