package com.bankly.loan.entity;

import java.sql.Date;
import java.time.LocalDate;

import com.bankly.loan.enums.LoanTypesEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor @Builder
public class Loan extends BaseEntity{
   @Id 
  @SequenceGenerator(
    name = "loan_seq",
    sequenceName = "loan_seq", 
    initialValue = 1000,
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "loan_seq"
  ) 
   private long loanId;

   private String customerEmail;

   private String loanType;

   private Double totalLoan;

   private Double amountPaid;

   private Double outstandingAmount;

   private LocalDate loanDueDate;

   
}
