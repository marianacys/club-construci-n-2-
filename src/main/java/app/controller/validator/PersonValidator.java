package app.controller.validator;

public class PersonValidator extends CommonsValidator {
    
    /**
     * Valida el nombre de usuario.
     */
    public void validUserName(String userName) throws IllegalArgumentException {
        super.isValidString("el nombre de usuario", userName);
    }

    /**
     * Valida la contraseña de usuario.
     */
    public void validPassword(String password) throws IllegalArgumentException {
        super.isValidString("la contraseña de usuario", password);
    }

    public void validRole(String role) throws IllegalArgumentException {
        super.isValidString("el rol de usuario", role);
    }

    public long validId(String nextLine) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}


