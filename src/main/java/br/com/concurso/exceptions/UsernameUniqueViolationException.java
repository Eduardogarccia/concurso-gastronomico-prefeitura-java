package br.com.concurso.exceptions;

public class UsernameUniqueViolationException extends RuntimeException{

    public UsernameUniqueViolationException(String message){
        super(message);
    }
}