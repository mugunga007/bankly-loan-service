package com.bankly.loan.utils;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.bankly.loan.exceptions.InvalidObjectException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Component
public class ValidationUtils {
  
  private ValidationUtils(){}
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
   private static final Validator validator = factory.getValidator(); 
  
   public static <T> void validate(T objectToValidate){
    Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate);
    if(!violations.isEmpty()){
      
      var errorMessages = violations
        .stream()
        .map(ConstraintViolation::getMessage)
        .collect(Collectors.toSet());
        throw new InvalidObjectException(errorMessages);
    }
  }
}
