package app.controllers;

import app.service.LoginService;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PartnerController implements ControllerInterface {
    private static final String MENU = "Ingrese la opcion que desea \n 1. Solicitar consumo \n 2. Ver historial de consumos \n 3. Crear Invitado \n 4. Cambio a VIP \n 9. Para cerrar sesion \n";

    private final ControllerInterface personController;
    private final ControllerInterface userController;
    private final ControllerInterface partnerController;

    // Mapa de opciones del menu
    private final Map<String, Runnable> menuOptions;

    public PartnerController(ControllerInterface personController, ControllerInterface userController, ControllerInterface partnerController) {
        this.personController = personController;
        this.userController = userController;
        this.partnerController = partnerController;
        this.menuOptions = createMenuOptions();
    }

    // Mapa de opciones
    private Map<String, Runnable> createMenuOptions() {
        Map<String, Runnable> options = new HashMap<>();
        options.put("1", () -> executeSession(personController));
        options.put("2", () -> executeSession(userController));
        options.put("3", () -> executeSession(partnerController));
        options.put("9", this::logout);
        return options;
    }

    @Override
    public void session() {
        boolean sessionActive = true;
        while (sessionActive) {
            sessionActive = menu();
        }
    }

    // Ejecutar sesion de otro controlador
    private void executeSession(ControllerInterface controller) {
        try {
            controller.session();
        } catch (Exception e) {
            System.out.println("Error ejecutando opcion: " + e.getMessage());
        }
    }

    private boolean menu() {
        try {
            System.out.println("Bienvenido " + LoginService.user.getUserName());
            System.out.print(MENU);
            Scanner scanner = new Scanner(System.in);
            String option = scanner.nextLine();
            return executeOption(option);
        } catch (Exception e) {
            System.out.println("Error en el menu: " + e.getMessage());
            return true;
        }
    }

    private boolean executeOption(String option) {
        Runnable action = menuOptions.get(option);
        if (action != null) {
            action.run();
            return !option.equals("9");
        } else {
            System.out.println("Ingrese una opcion valida");
            return true;
        }
    }

    private void logout() {
        System.out.println("Se ha cerrado sesion");
    }
}
