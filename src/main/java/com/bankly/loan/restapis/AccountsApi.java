package com.bankly.loan.restapis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.bankly.loan.dto.CustomerAccountsResponse;
import com.bankly.loan.dto.ResponseDto;
import com.bankly.loan.exceptions.CustomerNotFoundException;
import com.bankly.loan.model.Account;
import com.bankly.loan.model.Customer;
import com.bankly.loan.service.LoanServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AccountsApi {
  private final WebClient webClient;

  private static final Logger logger= LoggerFactory.getLogger(AccountsApi.class);
  public AccountsApi(@Value("${api.accountsUrl}") String accountsUrl) {
     // this.webClient = WebClient.builder().baseUrl(accountsUrl).build();
      this.webClient = WebClient.builder().baseUrl("http://localhost:8095/api/v1/customer").build();
  }
 
  public Mono<Integer> getCustomerIdByEmail(String email) {
      return webClient.get()
              .uri(uriBuilder -> uriBuilder
                    .path("/customer_id")
                    .queryParam("email", email)
                    .build())
              .retrieve()
              .bodyToMono(ResponseDto.class)
              .map(response -> {
                if (response.getData() == null) {
                    throw new CustomerNotFoundException("Customer ID is null");
                }
                return (int) response.getData();
            });
  }

  public Mono<Customer> getCustomerAccounts(Long customerId) {
    return webClient.get()
            .uri(uriBuilder ->uriBuilder
                .path("/accounts")
                .queryParam("customer_id", customerId)
                .build())
            .retrieve()
            .bodyToMono(
              new ParameterizedTypeReference<ResponseDto<List<Account>>>(){}
              //String.class
              ).map(response -> new Customer(response.getData()));

}

}
