package app.controller.validator;

public abstract class CommonsValidator {

    /**
     * Valida si la cadena proporcionada no está vacía.
     *
     * @param element El nombre del elemento para el mensaje de error.
     * @param value La cadena a validar.
     * @throws IllegalArgumentException Si la cadena está vacía.
     */
    public void isValidString(String element, String value) throws IllegalArgumentException {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(element + " no puede ser un valor vacío");
        }
    }

    /**
     * Valida y convierte la cadena proporcionada en un valor entero.
     *
     * @param element El nombre del elemento para el mensaje de error.
     * @param value La cadena a convertir.
     * @return El valor entero convertido.
     * @throws IllegalArgumentException Si la cadena está vacía o no es un valor válido.
     */
    public int isValidInteger(String element, String value) throws IllegalArgumentException {
        isValidString(element, value);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(element + " debe ser un valor entero válido", e);
        }
    }
    
    /**
     * Valida y convierte la cadena proporcionada en un valor largo.
     *
     * @param element El nombre del elemento para el mensaje de error.
     * @param value La cadena a convertir.
     * @return El valor largo convertido.
     * @throws IllegalArgumentException Si la cadena está vacía o no es un valor válido.
     */
    public long isValidLong(String element, String value) throws IllegalArgumentException {
        isValidString(element, value);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(element + " debe ser un valor largo válido", e);
        }
    }
    
    /**
     * Valida y convierte la cadena proporcionada en un valor double.
     *
     * @param element El nombre del elemento para el mensaje de error.
     * @param value La cadena a convertir.
     * @return El valor double convertido.
     * @throws IllegalArgumentException Si la cadena está vacía o no es un valor válido.
     */
    public double isValidDouble(String element, String value) throws IllegalArgumentException {
        isValidString(element, value);
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(element + " debe ser un valor decimal válido", e);
        }
    }
}
