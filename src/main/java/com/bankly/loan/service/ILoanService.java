package com.bankly.loan.service;

import com.bankly.loan.dto.LoanDto;
import com.bankly.loan.entity.Loan;

import reactor.core.publisher.Mono;

import java.util.List;



public interface ILoanService {
  
  Mono<Loan> createLoan(LoanDto loanDto);

  List<Loan> getLoans();
}
