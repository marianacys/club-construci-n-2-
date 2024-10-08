package app.dto;

import java.sql.Date;
import app.controllers.Utils;
import app.controllers.validator.PartnerValidator;
import app.dto.interfaces.PartnerDtoInterface;

public class PartnerDto implements PartnerDtoInterface {
    // Validación para los datos del socio
    private final PartnerValidator partnerValidator = new PartnerValidator();

    // Atributos de la clase
    private long id;
    private long userId;
    private double amount;
    private String type;
    private Date creationDate;
    private String name;
    private String password;
    private double partnerAmountIncrease;

    // Constructor vacio
    public PartnerDto() {}

    // Constructor con algunos datos
    public PartnerDto(String name, String password, String subscriptionType) {
        this.name = name;
        this.password = password;
        this.type = subscriptionType;
        this.amount = 0.0;
        this.creationDate = new Date(System.currentTimeMillis());
    }

    // Metodos de negocio
    @Override
    public void getPartnerTypeDto() throws Exception {
        String partnerTypeDto;
        while (true) {
            System.out.println("Ingrese el tipo de socio \n 1. REGULAR \n 2. VIP \n 4. CANCELAR \n");
            partnerTypeDto = Utils.getReader().nextLine();

            switch (partnerTypeDto) {
                case "1":
                    this.type = "REGULAR";
                    return;
                case "2":
                    this.type = "VIP";
                    return;
                case "4":
                    this.type = ""; 
                    return; 
                default:
                    System.out.println("Ingrese una opción válida");
            }
        }
    }

    @Override
    public void getPartnerAmountDto() throws Exception {
        System.out.println("Ingrese el monto de inversión");
        String partnerAmountDto = Utils.getReader().nextLine();
        this.amount = partnerValidator.validAmount(partnerAmountDto);
    }

    // Incrementa los fondos del socio, actualiza el monto en la clase
    public void getPartnerAmountIncreaseDto() throws Exception {
        System.out.println("Ingrese el monto a AUMENTAR la inversión");
        String partnerAmountDto = Utils.getReader().nextLine();
        double incremento = partnerValidator.validAmount(partnerAmountDto);

        if (incremento > 0) {
            this.amount += incremento;
            System.out.println("Fondos incrementados exitosamente. Fondos actuales: " + this.amount);
        } else {
            throw new IllegalArgumentException("El monto debe ser mayor a 0.");
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getters y setters
    public double getPartnerAmountIncrease() {
        return partnerAmountIncrease;
    }

    public void setPartnerAmountIncrease(double partnerAmountIncrease) {
        this.partnerAmountIncrease = partnerAmountIncrease;
    }

    // Metodo toString para mostrar la información del socio
    @Override
    public String toString() {
        return "ID: " + id + ", Usuario: " + userId + ", Tipo: " + type + ", Fondos: " + amount + ", Socio: " + name;
    }

    @Override
    public void getPartnerAmountIncraseDto() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}


