package com.bankly.loan.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Schema(
        name = "Error Response",
        description = "Schema to hold error response information"
)
@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {
   @Schema(
                name = "api path",
                description = "Schema to hold api path information"
        )  
private String apiPath;
@Schema(
                name = "Error code",
                description = "Schema to hold error code information"
        ) 
private HttpStatus errorCode;
@Schema(
                name = "Validation errors",
                description = "errors if invalid data is provided"
        ) 
private Set<String> validationErrors = new HashSet<>();
@Schema(
                name = "Validation errors",
                description = "error message if error occurs"
        ) 
private String errorMessage;
@Schema(
                name = "Error time",
                description = "Time that error occured"
        ) 
private LocalDateTime errorTime;
}
