package app.controllers;

import app.dto.PartnerDto;
import app.service.LoginService;
import java.util.Scanner;

public class AdminController implements ControllerInterface {
    private static final String MENU = "Ingrese la opción que desea \n 1. PERSONAS \n 2. USUARIOS \n 3. SOCIOS \n 4. INVITADOS \n 9. Para cerrar sesión \n";

    private final ControllerInterface personController;
    private final ControllerInterface userController;
    private final ControllerInterface partnerController;
    private final ControllerInterface guestController;
    private String PartnerDto;
    private Iterable<PartnerDto> partnerList;

   
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
            case "9":
                System.out.println("Se ha cerrado sesión");
                return false;
            default:
                System.out.println("Ingrese una opción válida");
                return true;
        }
    }
    private void numberPertnersVIP() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese nombre de usuario:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese contraseña:");
        String contraseña = scanner.nextLine();
        System.out.println("Ingrese tipo de suscripción (Regular/VIP):");
        String tipoSuscripcion = scanner.nextLine();

        PartnerDto partner = new PartnerDto(nombre, contraseña, tipoSuscripcion);
        partner.add(partner);
        System.out.println("Socio registrado exitosamente: " + PartnerDto);
    }

    private void PartnerTypeDto() {
        System.out.println("Lista de Socios:");
        for (PartnerDto Partner : PartnerDto) {
            System.out.println(PartnerDto);
        }
    }

   private void mostrarListaSocios() {
    System.out.println("Lista de Socios:");
    
    // Aquí se asume que tienes una lista de socios llamada partnerList
    for (PartnerDto partner : partnerList) {
        System.out.println(partner);  // Suponiendo que PartnerDto tiene un método toString() implementado
    }
}

 private void PartnerAmountIncraseDto() {
        Scanner scanner = new Scanner(System.in);

        // Pedir el ID del socio
        System.out.println("Ingrese ID del socio:");
        int id = scanner.nextInt();

        // Pedir el monto a incrementar
        System.out.println("Ingrese monto a incrementar:");
        double monto = scanner.nextDouble();

        for (PartnerDto partner : partnerList) {
            if (partner.getId() == id) {
                // Incrementar los fondos del socio
                partner.partnerAmountDto(monto);
                System.out.println("Fondos incrementados. Fondos actuales: " + partner.getpartnerAmountDto());
                return;
            }
        }

        // Si no se encuentra el socio
        System.out.println("Socio no encontrado.");
    }
}

}
