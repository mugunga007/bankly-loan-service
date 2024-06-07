package com.bankly.loan.service;

import java.util.List;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bankly.loan.dto.LoanDto;
import com.bankly.loan.dto.ResponseDto;
import com.bankly.loan.entity.Loan;
import com.bankly.loan.exceptions.CustomerNotFoundException;
import com.bankly.loan.mapper.LoanMapper;
import com.bankly.loan.model.Account;
import com.bankly.loan.model.Customer;
import com.bankly.loan.repository.LoanRepository;
import com.bankly.loan.restapis.AccountsApi;
import com.bankly.loan.utils.ValidationUtils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements ILoanService{

  
  private final @NonNull AccountsApi accountsApi;
  private final @NonNull LoanRepository loanRepository;

  private static final Logger logger= LoggerFactory.getLogger(LoanServiceImpl.class);
  @Override
  public Loan createLoan(LoanDto loanDto) {
    // Step 1: Validate loanDto (assuming ValidationUtils.validate performs necessary checks)
    ValidationUtils.validate(loanDto);
     Long customerId = Long.valueOf(accountsApi.getCustomerIdByEmail(loanDto.getCustomerEmail()).block());
    logger.info("Customer id received");
    Customer r =accountsApi.getCustomerAccounts(customerId).block();
    
    logger.info(String.valueOf(r.getAccounts().size()));
   return accountsApi.getCustomerAccounts(customerId)
    .map(customer -> {
    double balance = customer.getAccounts()
                        .stream()
                        .mapToDouble(Account::getBalance)
                        .sum();
      logger.info(String.valueOf(balance));
      if(!checkEligibility(balance, loanDto.getTotalLoan()))
        throw new RuntimeException("Not eligible");
      return LoanMapper.mapToLoan(loanDto);
    })
    .map(loanRepository::save)
    .block();
  }
  private boolean checkEligibility(double balance, double loan){
    DoublePredicate isEligible = loanAmount->loanAmount <(balance * 5);
    return isEligible.test(loan);
  }
  @Override
  public List<Loan> getLoans() {
    // TODO Auto-generated method stub
    return loanRepository.findAll();
  }
}
