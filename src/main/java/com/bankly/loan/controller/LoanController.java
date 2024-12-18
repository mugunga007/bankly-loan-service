package com.bankly.loan.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankly.loan.constants.Constants;
import com.bankly.loan.dto.EnvPropertiesDto;
import com.bankly.loan.dto.LoanDto;
import com.bankly.loan.dto.ResponseDto;
import com.bankly.loan.entity.Loan;
import com.bankly.loan.service.ILoanService;

import io.micrometer.observation.annotation.Observed;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "api/v1/loan")
@RequiredArgsConstructor
public class LoanController {
  private final @NonNull ILoanService loanService;
  private final @NonNull EnvPropertiesDto envPropertiesDto;
  
  private static final Logger log = LoggerFactory.getLogger(LoanController.class);
 
  @PostMapping
  @Observed
  public Mono<ResponseEntity<ResponseDto>> createLoan(@RequestBody LoanDto loanDto){
    return loanService.createLoan(loanDto)
        .map(loan -> ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ResponseDto.builder()
              .status(HttpStatus.CREATED.value())
              .message(Constants.SUCCEEDED)
              .build()));
          
  }

  @GetMapping
  @Observed
  public ResponseEntity<ResponseDto<Object>> getLoans(){
    List<Loan> list = loanService.getLoans();
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.builder()
        .status(HttpStatus.OK.value())
        .message(Constants.SUCCEEDED+" !")
        .count(String.valueOf(list.size()))
        .data(list)
        .build());

  }

  @GetMapping("/info")
public ResponseEntity<ResponseDto<Object>> getInfo(){
  return ResponseEntity.status(HttpStatus.OK)
          .body(ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .result(Constants.SUCCEEDED)
                .data(envPropertiesDto)
                .build())
                ;
}

}
