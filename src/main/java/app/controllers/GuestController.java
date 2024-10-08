package app.controllers;

import app.dto.GuestDto;
import app.dto.PartnerDto;
import app.service.GuestService;
import app.service.LoginService;
import app.dao.GuestDao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuestController implements ControllerInterface {
    private static final String MENU = "Ingrese la opción que desea \n 1. Crear invitado \n 2. Borrar invitado \n 9. Volver a menú principal  \n";
    
    private final GuestService guestService = new GuestService();
    private List<PartnerDto> partnerList; 

    @Override
    public void session() throws Exception {
        boolean session = true;
        while (session) {
            session = menu();
        }
    }

    private boolean menu() {
        try {
            System.out.println("Bienvenido " + LoginService.user.getUserName());
            System.out.print(MENU);
            String option = Utils.getReader().nextLine();
            return options(option);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return true;
        }
    }

    private boolean options(String option) throws Exception {
        switch (option) {
            case "1": {
                this.createGuest();
                return true;
            }
            case "2": {
                this.guestService.deleteGuest();
                return true;
            }
            case "9": {
                System.out.println("Se ha cerrado sesión");
                return false;
            }
            default: {
                System.out.println("Ingrese una opción válida");
                return true;
            }
        }
    }

    private PartnerDto getLoggedPartner() {
        if (LoginService.user == null) {
            System.out.println("No hay un usuario logueado.");
            return null;
        }

        long personId = LoginService.user.getId();

        for (PartnerDto partner : partnerList) {
            if (partner.getUserId() == personId) { 
                return partner;
            }
        }

        System.out.println("No se encontró el socio correspondiente al usuario logueado.");
        return null;
    }

    private void createGuest() throws Exception {
        PartnerDto currentPartner = getLoggedPartner();
        
        // Verificar si es socio regular y cuantos invitados activos tiene
        if (currentPartner == null) {
            System.out.println("No hay un socio logueado.");
            return;
        }
        
        if ("REGULAR".equals(currentPartner.getType())) {
            long activeGuests = guestService.getActiveGuestsByPartnerId(currentPartner.getId()).stream()
                                             .filter(GuestDto::isActive)
                                             .count();
            if (activeGuests >= 3) {
                System.out.println("Has alcanzado el límite de invitados activos.");
                return;
            }
        }
        
        // Crea un nuevo invitado
        GuestDto newGuest = new GuestDto();
        newGuest.setStatus("Activo"); 
        newGuest.setPartnerId(currentPartner.getId());
        try {
            guestService.addGuest(newGuest);
        } catch (Exception ex) {
            Logger.getLogger(GuestController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Invitado creado exitosamente.");
    }
}
