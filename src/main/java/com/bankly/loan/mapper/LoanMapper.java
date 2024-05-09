package com.bankly.loan.mapper;

import com.bankly.loan.dto.LoanDto;
import com.bankly.loan.entity.Loan;
import com.bankly.loan.enums.LoanTypesEnum;

public class LoanMapper {
  public static Loan mapToLoan(LoanDto loanDto){
    return Loan.builder()
      .customerEmail(loanDto.getCustomerEmail())
      .loanType(LoanTypesEnum.fromString(loanDto.getLoanType()).toString())
      .totalLoan(loanDto.getTotalLoan())
      .loanDueDate(loanDto.getLoanDueDate())
      .amountPaid(0.0)
      .outstandingAmount(loanDto.getTotalLoan())
      .build();

  }
}
