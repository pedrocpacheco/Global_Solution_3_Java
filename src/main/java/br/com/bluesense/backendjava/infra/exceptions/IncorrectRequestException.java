package br.com.bluesense.backendjava.infra.exceptions;

public class IncorrectRequestException extends RuntimeException{
  
  public IncorrectRequestException(String msg){
    super(msg);
  }

}
