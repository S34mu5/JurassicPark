package jurassicpark.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase utilitaria para validar entradas del usuario
 */
public class InputValidator {

    private static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Verifica si una cadena es un número entero
     * 
     * @param str Cadena a verificar
     * @return true si es un número entero, false en caso contrario
     */
    public static boolean esNumero(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Valida y convierte una cadena a un entero positivo
     * 
     * @param str             Cadena a convertir
     * @param valorPorDefecto Valor por defecto si la cadena no es un número
     * @return El número entero o el valor por defecto
     */
    public static int validarEnteroPositivo(String str, int valorPorDefecto) {
        if (esNumero(str)) {
            return Integer.parseInt(str);
        }
        return valorPorDefecto;
    }

    /**
     * Verifica si una cadena es un número real (con o sin decimales)
     * 
     * @param str Cadena a verificar
     * @return true si es un número real, false en caso contrario
     */
    public static boolean esNumeroReal(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        boolean puntoEncontrado = false;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (c == '.') {
                if (puntoEncontrado) {
                    return false;
                }
                puntoEncontrado = true;
            } else if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Verifica si una cadena tiene formato de fecha válido (DD/MM/AAAA)
     * 
     * @param fechaStr Cadena a verificar
     * @return true si tiene formato válido, false en caso contrario
     */
    public static boolean esFechaValida(String fechaStr) {
        if (fechaStr == null || fechaStr.isEmpty()) {
            return false;
        }

        // Verificar formato básico
        if (!fechaStr.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
            return false;
        }

        try {
            // Configurar para validación estricta
            FORMATO_FECHA.setLenient(false);
            FORMATO_FECHA.parse(fechaStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Convierte una cadena con formato DD/MM/AAAA a un objeto Date
     * 
     * @param fechaStr Cadena a convertir
     * @return Objeto Date o null si el formato es inválido
     */
    public static Date convertirAFecha(String fechaStr) {
        if (!esFechaValida(fechaStr)) {
            return null;
        }

        try {
            return FORMATO_FECHA.parse(fechaStr);
        } catch (ParseException e) {
            return null;
        }
    }
}