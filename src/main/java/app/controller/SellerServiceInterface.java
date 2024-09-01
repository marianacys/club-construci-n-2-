
package app.controller;

// Debería ser una interfaz, no una clase
public interface SellerServiceInterface {

    // Métodos en interfaces no tienen cuerpo
    void createInvoice(Invoice invoice);

    // Asegúrate de que Invoice sea accesible aquí
    // Puedes mover la clase Invoice a un paquete común
    class Invoice {
        // Detalles de la implementación de Invoice
    }
}

