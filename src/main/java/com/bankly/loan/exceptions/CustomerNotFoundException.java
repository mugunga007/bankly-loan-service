package com.bankly.loan.exceptions;

import lombok.AllArgsConstructor;


public class CustomerNotFoundException extends RuntimeException{
  
  public CustomerNotFoundException(String message){
    super(message);
  }

}
