package com.bankly.loan.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ResponseDto<T> {
  
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
  private T data;
}
