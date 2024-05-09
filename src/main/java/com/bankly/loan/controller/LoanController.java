package com.bankly.loan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankly.loan.constants.Constants;
import com.bankly.loan.dto.LoanDto;
import com.bankly.loan.dto.ResponseDto;
import com.bankly.loan.service.ILoanService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/loan")
@RequiredArgsConstructor
public class LoanController {
  public final @NonNull ILoanService loanService;

  @PostMapping
  public ResponseEntity<ResponseDto> createLoan(@RequestBody LoanDto loanDto){
    loanService.createLoan(loanDto);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ResponseDto.builder()
              .status(HttpStatus.CREATED.value())
              .message(Constants.SUCCEEDED)
              .build());
          
  }
}
