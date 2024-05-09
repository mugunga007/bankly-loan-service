package com.bankly.loan.service;

import org.springframework.stereotype.Service;

import com.bankly.loan.dto.LoanDto;
import com.bankly.loan.entity.Loan;

import reactor.core.publisher.Mono;


public interface ILoanService {
  
  Loan createLoan(LoanDto loanDto);
}
