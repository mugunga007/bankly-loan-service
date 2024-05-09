package com.bankly.loan.exceptions;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidObjectException extends RuntimeException{

  private final Set<String> errorMessages;
  
}
