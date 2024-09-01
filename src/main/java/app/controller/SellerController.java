package app.controller;

import java.sql.Date;
import app.controller.validator.InvoiceValidator;
import app.controller.validator.PersonValidator;
import app.dto.InvoiceDto;
import app.dto.UserDto;
import app.service.SellerService;
import app.service.interfaces.SellerServiceInterface;

public class SellerController implements ControllerInterface {
    private PersonValidator personValidator;
    private InvoiceValidator invoiceValidator;
    private SellerServiceInterface service; 
    private static final String MENU = "Ingrese la opción que desea realizar: \n1. Crear Factura\n2. Cerrar Sesión";

    public SellerController() {
        this.personValidator = new PersonValidator();
        this.invoiceValidator = new InvoiceValidator();
        this.service = (SellerServiceInterface) new SellerService();
    }

    /**
     *
     * @throws Exception
     */
    @Override
    public void session() throws Exception {
        boolean session = true;
        while (session) {
            session = sellerSession();
        }
    }

    private boolean sellerSession() {
        try {
            System.out.println(MENU);
            String option = Utils.getReader().nextLine();
            return menu(option);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return true;
        }
    }

    private boolean menu(String option) throws Exception {
        switch (option) {
            case "1": {
                this.createInvoice();
                return true;
            }
            case "2": {
                System.out.println("Se ha cerrado la sesión.");
                return false;
            }
            default: {
                System.out.println("Opción inválida.");
                return true;
            }
        }
    }

    private void createInvoice() throws Exception {
        try {
            System.out.println("Ingrese el ID del usuario (Partner o Guest) para la factura:");
            long userId = personValidator.validId(Utils.getReader().nextLine());

            System.out.println("Ingrese el monto de la factura:");
            double amount = invoiceValidator.validAmount(Utils.getReader().nextLine());

            System.out.println("Ingrese la descripción de la factura:");
            String description = Utils.getReader().nextLine();
            invoiceValidator.validDescription(description);

            // Crear objeto Invoice
            InvoiceDto invoice = new InvoiceDto();
            UserDto user = new UserDto();
            user.setuserid(userId);
            invoice.setIdUser(user);
            invoice.setGenerationDate(new Date(System.currentTimeMillis()));
            invoice.setAmount(amount);
            invoice.setDescription(description);
            invoice.setStatus(true);

            // Llamada al servicio para crear la factura
            service.createInvoice(invoice);
            System.out.println("Factura creada exitosamente.");

        } catch (Exception e) {
            System.out.println("Error al crear la factura: " + e.getMessage());
        }
    }
}