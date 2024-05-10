package src.domain;

public class GardenException extends Exception{
    public static final String GENERAL_ERROR = "Error";

    public GardenException(String message){
        super(message);
    }
}

