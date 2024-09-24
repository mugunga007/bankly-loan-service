package com.bankly.loan.dto;

public record AccountsMsgDto(String accountType, Long branchId, Long primaryAccountHolder, double balance) {
  
}
