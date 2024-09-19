package app.dto;

import java.sql.Date;

import app.controllers.Utils;
import app.dto.interfaces.PartnerDtoInterface;
import app.controllers.validator.PartnerValidator;

public class PartnerDto implements PartnerDtoInterface {
    private final PartnerValidator partnerValidator = new PartnerValidator();

    private long id;
    private long userId;
    private double amount;
    private String type;
    private Date creationDate;
    private String nombre;
    private String Passwork;

    // Método para obtener el tipo de socio (Regular o VIP)
    @Override
    public void getPartnerTypeDto() throws Exception {
        String partnerTypeDto = "";
        boolean continueRead = true;
        while (continueRead) {
            System.out.println("Ingrese el tipo de socio \n 1. REGULAR \n 2. VIP \n 4. CANCELAR \n");
            partnerTypeDto = Utils.getReader().nextLine();
            
            switch (partnerTypeDto) {
                case "1": {
                    partnerTypeDto = "REGULAR";
                    continueRead = false;
                    break;
                }
                case "2": {
                    partnerTypeDto = "VIP";
                    continueRead = false;
                    break;
                }
                case "4": {
                    partnerTypeDto = "";
                    continueRead = false;
                    break;
                }
                default: {
                    System.out.println("Ingrese una opción válida");
                }
            }
        }
        
        this.partnerValidator.validType(partnerTypeDto);  // Validar el tipo de socio
        this.type = partnerTypeDto;  // Asignar el tipo
    }

    // Método para obtener el monto inicial de fondos del socio
    @Override
    public void getPartnerAmountDto() throws Exception {
        System.out.println("Ingrese el monto de inversión");
        String partnerAmountDto = Utils.getReader().nextLine();
        this.amount = partnerValidator.validAmount(partnerAmountDto);  // Validar y asignar el monto
    }

    // Método para incrementar el monto de fondos del socio
    @Override
    public void getPartnerAmountIncraseDto() throws Exception {
        System.out.println("Ingrese el monto a AUMENTAR la inversión");
        String partnerAmountDto = Utils.getReader().nextLine();
        double incremento = partnerValidator.validAmount(partnerAmountDto);  // Validar el monto

        if (incremento > 0) {
            this.amount += incremento;  // Incrementar el monto actual
            System.out.println("Fondos incrementados exitosamente. Fondos actuales: " + this.amount);
        } else {
            System.out.println("El monto debe ser mayor a 0.");
        }
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    // Método para agregar un socio (incompleto, puedes usarlo para agregar a una lista)
    public void add(PartnerDto partner) {
        System.out.println("Socio agregado: " + partner);
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Usuario: " + userId + ", Tipo: " + type + ", Fondos: " + amount + "Socio: " + nombre;
    }

    // Constructor que recibe los datos del socio
    public PartnerDto(com.sun.org.apache.xpath.internal.operations.String nombre, com.sun.org.apache.xpath.internal.operations.String contraseña, com.sun.org.apache.xpath.internal.operations.String tipoSuscripcion) {
        this.nombre = nombre;
        this.Passwork = contraseña;
        this.type = tipoSuscripcion;
        this.amount = 0.0; // Fondos iniciales
        this.creationDate = new Date(System.currentTimeMillis()); 
    }


}
