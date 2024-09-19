package app.service;

import app.service.interfaces.GuestServiceInterface;
import app.dao.PersonDao;
import app.dao.UserDao;
import app.dao.PartnerDao;
import app.dao.GuestDao;
import app.dao.InvoiceDao;
import app.dao.InvoiceDetailDao;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.dto.GuestDto;
import app.dto.InvoiceDto;
import app.dto.PartnerDto;

public class GuestService implements GuestServiceInterface {
    private final UserService userService = new UserService();
    private final PersonDao personDao = new PersonDao();
    private final UserDao userDao = new UserDao();
    private final PartnerDao partnerDao = new PartnerDao();
    private final GuestDao guestDao = new GuestDao();
    private final InvoiceDao invoiceDao = new InvoiceDao();
    private final InvoiceDetailDao invoiceDetailDao = new InvoiceDetailDao();

    // Crear un invitado sin recibir un UserDto como parámetro
    @Override
    public void createGuest() throws Exception {
        UserDto userDtoLocate = this.userService.createUserGuest();
        if (userDtoLocate == null) {
            throw new Exception("No se encontró ningún usuario");
        }
        createGuest(userDtoLocate);
    }

    // Crea un invitado recibiendo un UserDto
    @Override
    public void createGuest(UserDto userDto) throws Exception {
        // Verificar si el usuario ya es invitado
        if (guestDao.findByUserId(userDto) != null) {
            throw new Exception("El usuario ya es un invitado");
        }

        PersonDto personDto = this.personDao.findByUserId(userDto);
        if (personDto == null) {
            throw new Exception("No se encontró la persona asociada al usuario");
        }

        GuestDto guestDto = new GuestDto();
        guestDto.setUserId(userDto.getId());
        guestDto.setPartnerId(personDto.getId());
        guestDto.setStatus("ACTIVO");

        this.guestDao.createGuest(guestDto);
    }

    @Override
    public void updateGuest() throws Exception {
    }

    @Override
    public void deleteGuest() throws Exception {
        PersonDto personDtoLocale = new PersonDto();
        personDtoLocale.getPersonDocumentDto();
        personDtoLocale = this.personDao.findByDocument(personDtoLocale);

        if (personDtoLocale == null) {
            throw new Exception("No existe la persona");
        }

        double amountActiveInvoices = this.invoiceDao.amountActiveInvoices(personDtoLocale);
        if (amountActiveInvoices > 0) {
            throw new Exception(personDtoLocale.getName() + " tiene facturas pendientes de pago");
        }

        UserDto userDtoLocate = this.userDao.findByPersonId(personDtoLocale);
        if (userDtoLocate == null) {
            throw new Exception("No se encontró ningún usuario con el número de identificación ingresado");
        }

        GuestDto guestDto = this.guestDao.findByUserId(userDtoLocate);
        if (guestDto == null) {
            throw new Exception("No existe el invitado");
        }

        this.guestDao.deleteGuest(guestDto);
    }

    @Override
    public void changeGuestToPartner(UserDto userDto) throws Exception {
        PersonDto personDtoLocale = this.personDao.findByUserId(userDto);

        if (personDtoLocale == null) {
            throw new Exception("No existe la persona");
        }

        double amountActiveInvoices = this.invoiceDao.amountActiveInvoices(personDtoLocale);
        if (amountActiveInvoices > 0) {
            throw new Exception(personDtoLocale.getName() + " tiene facturas pendientes de pago");
        }

        GuestDto guestDto = this.guestDao.findByUserId(userDto);
        if (guestDto == null) {
            throw new Exception(personDtoLocale.getName() + " no es un invitado");
        }

        PartnerDto partnerDto = this.partnerDao.findByUserId(userDto);
        if (partnerDto != null) {
            throw new Exception(personDtoLocale.getName() + " ya es SOCIO del club");
        }

        partnerDto = new PartnerDto();
        partnerDto.setUserId(userDto.getId());
        partnerDto.getPartnerTypeDto(); 

        // Validación del tipo de socio y disponibilidad de cupo VIP
        if (partnerDto.getType().equals("VIP")) {
            long numberVIP = this.partnerDao.numberPertnersVIP();
            if (numberVIP >= 5) {
                throw new Exception("Cupo de socios VIP copado");
            }
        }

        partnerDto.getPartnerAmountDto(); // Obtención de monto

        userDto.setRole("SOCIO");

        // Eliminar facturas pendientes
        InvoiceDto invoiceDto = this.invoiceDao.firstInvoiceByPersonId(personDtoLocale);
        while (invoiceDto != null) {
            this.invoiceDetailDao.deleteInvoiceDetail(invoiceDto);
            this.invoiceDao.deleteInvoice(invoiceDto);
            invoiceDto = this.invoiceDao.firstInvoiceByPersonId(personDtoLocale);
        }

        this.guestDao.deleteGuest(guestDto); 
        this.partnerDao.createPartner(partnerDto); 
        this.userDao.updateRoleUser(userDto); // Actualizar el rol del usuario
    }
}


