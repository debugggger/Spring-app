package main.exception;

public class InvalidJwtAuthException extends RuntimeException{
    public InvalidJwtAuthException(String message){
        super(message);
    }
}
