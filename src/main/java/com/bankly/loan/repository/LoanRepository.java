package com.bankly.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankly.loan.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long>{
  
}
