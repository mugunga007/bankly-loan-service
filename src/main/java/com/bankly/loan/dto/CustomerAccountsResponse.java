package com.bankly.loan.dto;

import java.util.List;

import com.bankly.loan.model.Account;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerAccountsResponse {
  @Schema(
                name = "status code response",
                description = "http status code",
                example = "200"
        ) 
  private int status;
  @Schema(
                name = "result",
                description = "result of response",
                example = "FAILED/SUCCEEDED"
        ) 
  private String result;
  @Schema(
                name = "message",
                description = "we probably don't need this lol",
                example = "200"
        ) 
  private String message;
  private ErrorResponseDto error;
  private String count;
  @Schema(
                name = "data",
                description = "data returned by api"

                
        ) 
  private List<Account> data;
}
