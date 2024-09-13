package com.bankly.loan.service;

import java.util.List;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bankly.loan.dto.AccountsMsgDto;
import com.bankly.loan.dto.EnvPropertiesDto;
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
  private final @NonNull EnvPropertiesDto envPropertiesDto;
  private static final Logger logger= LoggerFactory.getLogger(LoanServiceImpl.class);
  private final StreamBridge streamBridge;
  
   @Override
  public Loan createLoan(LoanDto loanDto) {
    // Step 1: Validate loanDto (assuming ValidationUtils.validate performs necessary checks)
    ValidationUtils.validate(loanDto);

    Long customerId = Long.valueOf(accountsApi.getCustomerIdByEmail(loanDto.getCustomerEmail())
                        .block());
    
    Loan l = accountsApi.getCustomerAccounts(Long.valueOf(customerId))
    .flatMap((Customer customer) -> {
        double balance = calculateTotalBalance(customer);

        if (!isEligibleForLoan(balance, loanDto.getTotalLoan())) {
            logger.warn("Customer not eligible for the loan");
            return Mono.error(new RuntimeException("Customer Not eligible for this loan"));
        }
        
        Loan loan = LoanMapper.mapToLoan(loanDto);

        
        return Mono.just(loan);

    })
    .map(loanRepository::save) // Convert the result to Mono<Void>
    .doOnSuccess(unused -> logger.info("Loan created successfully for customer with email: {}", loanDto.getCustomerEmail()))
    .doOnError(e -> logger.error("Error creating loan for customer with email: {}", loanDto.getCustomerEmail(), e))
    .block();

    sendCommunication(customerId, l);

    return l;         
    }        

  
  private void sendCommunication(Long customerId, Loan loan){
    var accountsMsgDto = new AccountsMsgDto(loan.getLoanType(),
     envPropertiesDto.getDefaultBranchId(), customerId);
    logger.info("data to send {}", accountsMsgDto);
    var result = streamBridge.send("sendCommunication-out-O",accountsMsgDto);
    logger.info("was the data sent successful ? {}", result);
  }

  private double calculateTotalBalance(Customer customer){
    return customer.getAccounts()
            .stream()
            .mapToDouble(Account::getBalance)
            .sum();
  }
  private boolean isEligibleForLoan(double balance, double loan){
    DoublePredicate isEligible = loanAmount->loanAmount <(balance * 5);
    return isEligible.test(loan);
  }
  @Override
  public List<Loan> getLoans() {
    // TODO Auto-generated method stub
    return loanRepository.findAll(Sort.by(
      Sort.Order.desc("createdAt")
    ));
  }
}
