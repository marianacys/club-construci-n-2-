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

import java.util.List;
import java.util.Scanner;

public class GuestService implements GuestServiceInterface {
    private final UserService userService = new UserService();
    private final PersonDao personDao = new PersonDao();
    private final UserDao userDao = new UserDao();
    private final PartnerDao partnerDao = new PartnerDao();
    private final GuestDao guestDao = new GuestDao();
    private final InvoiceDao invoiceDao = new InvoiceDao();
    private final InvoiceDetailDao invoiceDetailDao = new InvoiceDetailDao();

    // Crea un invitado sin recibir un UserDto
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
        guestDto.setStatus("ACTIVO");  // Forzamos el estado a "ACTIVO" para nuevos invitados

        this.guestDao.createGuest(guestDto);
        System.out.println("Invitado creado exitosamente con ID: " + guestDto.getUserId());
    }

@Override
public void updateGuest() throws Exception {
    Scanner scanner = new Scanner(System.in);
    
    System.out.println("Ingrese el ID del usuario que desea actualizar:");
    long userId = scanner.nextLong();

    // Verificar si el invitado existe
    GuestDto guestDto = guestDao.findByUserId(userId);
    if (guestDto == null) {
        throw new Exception("El usuario con ID " + userId + " no es un invitado.");
    }

    // Obtener la persona asociada al invitado
    PersonDto personDto = personDao.findByUserId(userId);
    if (personDto == null) {
        throw new Exception("No se encontro la persona asociada al invitado.");
    }

    // Mostrar datos actuales del invitado
    System.out.println("Datos actuales del invitado:");
    System.out.println("Nombre: " + personDto.getName());
    System.out.println("Estado actual: " + guestDto.getStatus());

    // Permitir al administrador actualizar ciertos campos
    System.out.println("Ingrese el nuevo estado del invitado (ACTIVO/INACTIVO):");
    String newStatus = scanner.next();

    // Actualizar los campos en el DTO
    guestDto.setStatus(newStatus);

    // Guardar los cambios en la base de datos
    guestDao.updateGuest(guestDto);
    
    System.out.println("El invitado ha sido actualizado exitosamente.");
}

    // Elimina un invitado
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
            throw new Exception("No se encontro ningun usuario con el nuumero de identificacion ingresado");
        }

        GuestDto guestDto = this.guestDao.findByUserId(userDtoLocate);
        if (guestDto == null) {
            throw new Exception("No existe el invitado");
        }

        this.guestDao.deleteGuest(guestDto);
        System.out.println("Invitado eliminado exitosamente.");
    }

    // Cambia un invitado a socio
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
        partnerDto.setType("REGULAR"); 
        partnerDto.setAmount(0.0);      

        // Validacion del tipo de socio y disponibilidad de cupo VIP
        if ("VIP".equals(partnerDto.getType())) {
            long numberVIP = this.partnerDao.numberPertnersVIP();
            if (numberVIP >= 5) {
                throw new Exception("Cupo de socios VIP copado");
            }
        }

        userDto.setRole("SOCIO");

        // Elimina facturas pendientes
        InvoiceDto invoiceDto = this.invoiceDao.firstInvoiceByPersonId(personDtoLocale);
        while (invoiceDto != null) {
            this.invoiceDetailDao.deleteInvoiceDetail(invoiceDto);
            this.invoiceDao.deleteInvoice(invoiceDto);
            invoiceDto = this.invoiceDao.firstInvoiceByPersonId(personDtoLocale);
        }

        this.guestDao.deleteGuest(guestDto);
        this.partnerDao.createPartner(partnerDto);
        this.userDao.updateRoleUser(userDto); 
        System.out.println(personDtoLocale.getName() + " ha sido promovido a socio.");
    }

    // Metodo para obtener invitados activos por ID de socio
    public List<GuestDto> getActiveGuestsByPartnerId(long partnerId) throws Exception {
        List<GuestDto> activeGuests = guestDao.findActiveGuestsByPartnerId(partnerId);
        if (activeGuests == null || activeGuests.isEmpty()) {
            throw new Exception("No hay invitados activos para el socio con ID: " + partnerId);
        }
        return activeGuests;
    }

    // Metodo para agregar un nuevo invitado
    public void addGuest(GuestDto newGuest) throws Exception {
        if (newGuest == null) {
            throw new Exception("El invitado no puede ser nulo");
        }

        if (guestDao.findByUserId(newGuest.getUserId()) != null) {
            throw new Exception("El usuario ya es un invitado");
        }

        newGuest.setStatus("ACTIVO");  // Forzar estado a 'ACTIVO'
        guestDao.createGuest(newGuest);
        System.out.println("Invitado agregado exitosamente con ID: " + newGuest.getUserId());
    }
}
