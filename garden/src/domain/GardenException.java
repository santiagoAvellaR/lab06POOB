package src.domain;

import java.io.Serializable;

public class GardenException extends Exception implements Serializable {
    public static final String GENERAL_ERROR = "Error.";
    public static final String FILE_NOT_FOUND = "El archivo no pudo ser encontrado.";
    public static final String ERROR_DURING_PROCESSING = "Error durante el proceso.";
    public static final String CLASS_NOT_FOUND = "Clase no encontrada.";

    public static final String MISSING_INFORMATION = "Linea incompleta. ";
    public static final String NONEXISTENT_CLASS = "Clase no valida. ";
    public static final String COLUMN_IS_NOT_NUMBER = "El numero de fila debe de ser un caracter numerico. ";
    public static final String ROW_IS_NOT_NUMBER = "El numero de columna debe de ser un un caracter numerico. ";
    public static final String ROW_EXCEEDED = "Las filas van desde la 0 hasta la 39. ";
    public static final String COLUMN_EXCEEDED = "Las columnas van desde la 0 hasta la 39. ";
    public static final String NEGATIVE_ROW = "Las filas no pueden ser negativas. ";
    public static final String NEGATIVE_COLUMN = "Las columnas no pueden ser negativas. ";
    public static final String INFORMATION_COULD_LOST = "Hay más datos de los esperados, podría perderse informacion. ";
    public static final String SPACE_OCUPIED = "Ya se agregó allí el elemento: ";


    public GardenException(String message){
        super(message);
    }
}

