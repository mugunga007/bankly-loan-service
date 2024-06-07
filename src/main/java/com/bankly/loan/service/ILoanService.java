package com.bankly.loan.service;

import com.bankly.loan.dto.LoanDto;
import com.bankly.loan.entity.Loan;

import java.util.List;



public interface ILoanService {
  
  Loan createLoan(LoanDto loanDto);

  List<Loan> getLoans();
}
