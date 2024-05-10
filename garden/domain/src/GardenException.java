package domain.src;

public class GardenException extends Exception{
    public static final String GENERAL_ERROR = "Error";

    public GardenException(String message){
        super(message);
    }
}
