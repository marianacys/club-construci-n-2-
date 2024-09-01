package app;

import app.controller.ControllerInterface;
import app.controller.LoginController;

public class App {
    public static void main(String[] args) {
        ControllerInterface controller = new LoginController(); // Instancia de LoginController

        try {
            controller.session(); // Llamada al método session() definido en LoginController
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la traza completa de la excepción si ocurre un error.
        }
    }    
}
