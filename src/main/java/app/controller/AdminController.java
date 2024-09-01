package app.controller;

import app.controller.validator.PersonValidator;
import app.controller.validator.UserValidator;
import app.model.Guest;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.dto.PartnerDto;
import app.dto.GuestDto;
import app.service.Service;
import app.service.interfaces.AdminService;
import java.util.Scanner;
import java.sql.Date;

public class AdminController implements ControllerInterface {
    private Service service;
    private static final String MENU = "Ingrese la opción que desea: \n 1. Crear usuario socio \n 2. Crear invitado \n 3. Cerrar sesión \n";

    public AdminController() {
        this.service = new Service() {}; 
    }

    @Override
    public void session() throws Exception {
        boolean session = true;
        while (session) {
            session = menu();
        }
    }

    private boolean menu() {
        try {
            System.out.println("Bienvenido, Administrador");
            System.out.print(MENU);
            String option = Utils.getReader().nextLine();
            return options(option);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    private boolean options(String option) throws Exception {
        switch (option) {
            case "1":
                createPartner();
                return true;
            case "2":
                createGuest();
                return true;
            case "3":
                System.out.println("Se ha cerrado sesión");
                return false;
            default:
                System.out.println("Ingrese una opción válida");
                return true;
        }
    }

    private void createPartner() throws Exception {
        Scanner reader = Utils.getReader();

        System.out.println("Ingrese el nombre del usuario socio");
        String name = reader.nextLine();

        System.out.println("Ingrese la cédula del usuario socio");
        long identificationCard = Long.parseLong(reader.nextLine());

        System.out.println("Ingrese el número de teléfono del usuario socio");
        String phone = reader.nextLine();

        PersonDto person = new PersonDto();
        person.setname(name);
        person.setdocument(identificationCard);
        person.setphone(phone);

        System.out.println("Ingrese el nombre de usuario para el socio");
        String userName = reader.nextLine();

        System.out.println("Ingrese la contraseña para el socio");
        String password = reader.nextLine();

        UserDto user = new UserDto();
        user.setusername(userName);
        user.setpassword(password);
        user.setrole("partner");
        long userId = 0;
        user.setuserid(userId);

        System.out.println("Ingrese los fondos disponibles del socio");
        double availableFunds = Double.parseDouble(reader.nextLine());

        System.out.println("Ingrese el tipo de suscripción del socio (true para activo, false para inactivo)");
        boolean subscriptionType = Boolean.parseBoolean(reader.nextLine());

        System.out.println("Ingrese la fecha de afiliación del socio (formato yyyy-MM-dd)");
        Date affiliationDate = Date.valueOf(reader.nextLine());

        Partner partner = new Partner();
        partner.setUserId(user);
        partner.setAvailableFunds(availableFunds);
        partner.setSubscriptionType(subscriptionType);
        partner.setAffiliationDate(affiliationDate);
        PartnerDto partnerDto = null;

        service.createPartner(partnerDto); 
        System.out.println("Se ha creado el socio exitosamente");
    }

    private void createGuest() throws Exception {
        Scanner reader = Utils.getReader();

        System.out.println("Ingrese el nombre del invitado");
        String name = reader.nextLine();

        System.out.println("Ingrese la cédula del invitado");
        long identificationCard = Long.parseLong(reader.nextLine());

        System.out.println("Ingrese el número de teléfono del invitado");
        String cellPhoneNumber = reader.nextLine();

        Person person = new Person();
        person.setName(name);
        person.setIdentificationCard(identificationCard);
        person.setCellPhoneNumber(cellPhoneNumber);

        System.out.println("Ingrese el nombre de usuario para el invitado");
        String userName = reader.nextLine();

        System.out.println("Ingrese la contraseña para el invitado");
        String password = reader.nextLine();

        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setRole("guest");
        user.setPersonId(person);

        System.out.println("Ingrese el ID del socio al que está asociado este invitado");
        long partnerId = Long.parseLong(reader.nextLine());

        System.out.println("Ingrese el registro del invitado");
        long registrationGuest = Long.parseLong(reader.nextLine());

        System.out.println("Ingrese el estado de la invitación del invitado (true para activo, false para inactivo)");
        boolean invitationStatus = Boolean.parseBoolean(reader.nextLine());
        
        try {
            GuestDto guest = null;
            service.createGuest(guest); 
            System.out.println("Se ha creado el invitado exitosamente");
        }       catch (Exception e) {
            System.out.println("Error al crear el invitado: " + e.getMessage());
        }

    }
}
