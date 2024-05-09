package com.bankly.loan.enums;

import lombok.ToString;


public enum LoanTypesEnum {
  HOME_LOAN("home"),
  CAR_LOAN("car"),
  BUSINESS_LOAN("business"),
  INVESTMENT_LOAN("investment"),
  STUDENT_LOAN("student");
  
  private final String loanTypeString;

  LoanTypesEnum(String loanTypeString){
    this.loanTypeString = loanTypeString;
  }

  public static LoanTypesEnum fromString(String loanTypeString){
    for(LoanTypesEnum typeEnum: LoanTypesEnum.values())
      if(typeEnum.loanTypeString.equalsIgnoreCase(loanTypeString))
        return typeEnum;
    return null;
  }
  
}
