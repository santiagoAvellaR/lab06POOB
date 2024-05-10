package src.domain;

import java.io.Serializable;

public class GardenException extends Exception implements Serializable {
    public static final String GENERAL_ERROR = "Error";

    public GardenException(String message){
        super(message);
    }
}

