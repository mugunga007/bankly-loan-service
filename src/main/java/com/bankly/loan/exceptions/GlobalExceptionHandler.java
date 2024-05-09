package com.bankly.loan.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bankly.loan.constants.Constants;
import com.bankly.loan.dto.ErrorResponseDto;
import com.bankly.loan.dto.ResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseDto> handleException(Exception ex){
    return ResponseEntity
            .badRequest()
            .body(ResponseDto.builder()
                  .status(HttpStatus.BAD_REQUEST.value())
                  .message(Constants.FAILED)
                  .error(ErrorResponseDto.builder()
                          .errorCode(HttpStatus.BAD_REQUEST)
                          .errorMessage(ex.getMessage())
                          .errorTime(LocalDateTime.now())
                          .build()).build()
                          );
  }

  @ExceptionHandler(InvalidObjectException.class)
  public ResponseEntity<ResponseDto> handleInvalidObjectException(InvalidObjectException ex){
    return ResponseEntity
            .badRequest()
            .body(ResponseDto.builder()
                  .status(HttpStatus.BAD_REQUEST.value())
                  .message(Constants.FAILED)
                  .error(ErrorResponseDto.builder()
                          .errorCode(HttpStatus.BAD_REQUEST)
                          .errorMessage(ex.getMessage())
                          .errorTime(LocalDateTime.now())
                          .build()).build()
                          );
  }
}
