package app.controllers;

import app.dto.GuestDto;
import app.dto.PartnerDto;
import app.service.HistoryInvoice;
import app.service.LoginService;
import app.dao.PartnerDao;
import app.dao.GuestDao;
import java.util.ArrayList; 
import java.util.List; 
import java.util.Scanner;

public class AdminController implements ControllerInterface {
    private static final String MENU = "Ingrese la opcion que desea \n 1. PERSONAS \n 2. USUARIOS \n 3. SOCIOS \n 4. INVITADOS \n 5. HISTORIAL FACTURAS \n 9. Para cerrar sesion \n";

    private final ControllerInterface personController;
    private final ControllerInterface userController;
    private final ControllerInterface partnerController;
    private final ControllerInterface guestController;
    private final HistoryInvoice historyInvoice = new HistoryInvoice();  // Instancia de HistoryInvoice
    private final PartnerDao partnerDao = new PartnerDao();
    private final GuestDao guestDao = new GuestDao();
    
    private List<PartnerDto> partnerList = new ArrayList<>();

    public AdminController(ControllerInterface personController, ControllerInterface userController,
                      ControllerInterface partnerController, ControllerInterface guestController) {
        this.personController = personController;
        this.userController = userController;
        this.partnerController = partnerController;
        this.guestController = guestController;
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
            case "1":
                this.personController.session();
                return true;
            case "2":
                this.userController.session();
                return true;
            case "3":
                this.partnerController.session();
                return true;
            case "4":
                this.guestController.session();
                return true;
            case "5":
                showInvoiceHistoryMenu();
                return true;
            case "9":
                System.out.println("Se ha cerrado sesion");
                return false;
            default:
                System.out.println("Ingrese una opcion valida");
                return true;
        }
    }

    // Mostrar menu para el historial de facturas
    private void showInvoiceHistoryMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione el tipo de historial:\n1. Club Completo\n2. Socio\n3. Invitado");
        String option = scanner.nextLine();

        try {
            switch (option) {
                case "1":
                    historyInvoice.showClubHistory();
                    break;
                case "2":
                    System.out.println("Ingrese el ID del socio:");
                    int partnerId = scanner.nextInt();
                    PartnerDto partnerDto = partnerDao.findById(partnerId);
                    if (partnerDto != null) {
                        historyInvoice.showPartnerHistory(partnerDto);
                    } else {
                        System.out.println("Socio no encontrado.");
                    }
                    break;
                case "3":
                    System.out.println("Ingrese el ID del invitado:");
                    int guestId = scanner.nextInt();
                    GuestDto guestDto = guestDao.findById(guestId);
                    if (guestDto != null) {
                        historyInvoice.showGuestHistory(guestDto);
                    } else {
                        System.out.println("Invitado no encontrado.");
                    }
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar el historial: " + e.getMessage());
        }
    }


    public void add(String nombre, String contraseña, String subscriptionType) {
        long vipCount = partnerList.stream()
                                   .filter(p -> "VIP".equals(p.getType()))
                                   .count();

        if ("VIP".equals(subscriptionType) && vipCount >= 5) {
            System.out.println("No se pueden agregar más socios VIP. Limite alcanzado.");
            return;
        }

        PartnerDto partner = new PartnerDto(nombre, contraseña, subscriptionType);
        partnerList.add(partner);
        System.out.println("Socio registrado exitosamente: " + partner.getName());
    }

    public void showPartnerList() {
        System.out.println("Lista de Socios:");
        for (PartnerDto partner : partnerList) {
            System.out.println(partner.getName() + " - " + partner.getType());
        }
    }

    private void PartnerAmountIncreaseDto() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese ID del socio:");
        int id = scanner.nextInt();

        System.out.println("Ingrese monto a incrementar:");
        double monto = scanner.nextDouble();

        for (PartnerDto partner : partnerList) {
            if (partner.getId() == id) {
                partner.setAmount(partner.getAmount() + monto);
                System.out.println("Fondos incrementados. Fondos actuales: " + partner.getAmount());
                return;
            }
        }
        System.out.println("Socio no encontrado.");
    }
}



