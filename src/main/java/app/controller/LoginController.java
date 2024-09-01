
package app.controller;

import java.util.HashMap;
import java.util.Map;
import app.controller.validator.UserValidator;
import app.dto.UserDto;
import app.service.Service;
import app.service.interfaces.LoginService;

public class LoginController implements ControllerInterface {
    private UserValidator userValidator;
    private LoginService service;
    private static final String MENU = "Ingrese la opción que desea: \n 1. Para iniciar sesión. \n 2. Para detener la ejecución.";
    private Map<String, ControllerInterface> roles;
    private final service Service;

    public LoginController() {
        this.userValidator = new UserValidator();
        this.Service = new service(); // Asumiendo que AdminService implementa LoginService

        // Inicializar controladores para cada rol
        ControllerInterface adminController = (ControllerInterface) new AdminController();
        ControllerInterface partner = (ControllerInterface) new Partner();
        ControllerInterface guest = (ControllerInterface) new Guest(); 

        // Mapear roles a controladores
        this.roles = new HashMap<>();
        roles.put("admin", adminController);
        roles.put("partner", partner);
        ControllerInterface put = roles.put("guest", guest);
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
            System.out.println(MENU);
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
                this.login();
                return true;
            case "2":
                System.out.println("Se detiene el programa");
                return false;
            default:
                System.out.println("Ingrese una opción válida");
                return true;
        }
    }

    private void login() throws Exception {
        System.out.println("Ingrese el usuario:");
        String userName = Utils.getReader().nextLine();
        userValidator.validUserName(userName);

        System.out.println("Ingrese la contraseña:");
        String password = Utils.getReader().nextLine();
        userValidator.validPassword(password);

        // Crear un objeto User en lugar de UserDto según tu diagrama de clases
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);

        // Intentar iniciar sesión con el servicio
        this.LoginService(user);

        // Obtener el rol del usuario autenticado
        String role = user.getRole();
        ControllerInterface controller = roles.get(role);

        if (controller == null) {
            throw new Exception("Rol inválido");
        }

        // Redirigir al controlador correspondiente según el rol del usuario
        controller.session();
    }

    private void LoginService(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static class service {

        public service() {
        }
    }
}

