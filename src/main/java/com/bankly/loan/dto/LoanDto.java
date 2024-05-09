package com.bankly.loan.dto;


import java.time.LocalDate;

import com.bankly.loan.enums.LoanTypesEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoanDto {

  @NotEmpty(message = "email is required")
  @NotBlank(message = "email cannot be blank")
  private String customerEmail;
  @NotEmpty(message = "type is required")
  @NotBlank(message = "type cannot be blank")
  private String loanType;

  @Min(0)
  private Double totalLoan;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate loanDueDate;
}
