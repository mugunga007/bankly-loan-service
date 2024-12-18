package com.bankly.loan.restapis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.bankly.loan.dto.CustomerAccountsResponse;
import com.bankly.loan.dto.EnvPropertiesDto;
import com.bankly.loan.dto.ResponseDto;
import com.bankly.loan.exceptions.CustomerNotFoundException;
import com.bankly.loan.model.Account;
import com.bankly.loan.model.Customer;
import com.bankly.loan.service.LoanServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AccountsApi {
  private final WebClient webClient;

  
  private final EnvPropertiesDto envPropertiesDto;
  private static final Logger logger= LoggerFactory.getLogger(AccountsApi.class);
  
  public AccountsApi(WebClient.Builder webClientBuilder, EnvPropertiesDto envPropertiesDto) {
      this.envPropertiesDto = envPropertiesDto;
      this.webClient = webClientBuilder.baseUrl(envPropertiesDto.getAccountsApiUrl()).build();
  }
 
  public Mono<Integer> getCustomerIdByEmail(String email) {
    logger.info("Fetching customer Id form email: {}",email);
      return webClient.get()
              .uri(uriBuilder -> uriBuilder
                    .path("/customer_id")
                    .queryParam("email", email)
                    .build())
              .retrieve()
              .bodyToMono(ResponseDto.class)
              .flatMap(response -> {
                if (response.getData() == null) {
                  logger.error("Customer not found for email: {}", email);
                  return Mono.error(new CustomerNotFoundException("Customer does not exist!"));
                }
                return Mono.just((int) response.getData());
            });
  }

  public Mono<Customer> getCustomerAccounts(Long customerId) {
    logger.info("Account url --------> {}",envPropertiesDto.getAccountsApiUrl());
    return webClient.get()
            .uri(uriBuilder ->uriBuilder
                .path("/accounts")
                .queryParam("customer_id", customerId)
                .build())
            .retrieve()
            .bodyToMono(
              new ParameterizedTypeReference<ResponseDto<List<Account>>>(){}
              //String.class
              ).map(response -> new Customer(response.getData()))
              .doOnError(e->logger.error("Error fetching customer account for ID: {}", customerId, e));

}



}
