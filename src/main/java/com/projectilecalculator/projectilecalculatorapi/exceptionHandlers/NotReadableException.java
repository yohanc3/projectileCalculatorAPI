package com.projectilecalculator.projectilecalculatorapi.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestControllerAdvice
public class NotReadableException extends ResponseEntityExceptionHandler{


  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  @ResponseStatus
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){

     Throwable mostSpecificCause = ex.getMostSpecificCause();

    if(mostSpecificCause instanceof InvalidFormatException){
      return handleInvalidFormatException((InvalidFormatException)mostSpecificCause, request);
    }

    return super.handleExceptionInternal(ex, mostSpecificCause, headers, HttpStatus.BAD_REQUEST, request);

  }

  private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, WebRequest request){

    ObjectNode jsonResponse = mapper.createObjectNode();

    jsonResponse.put("status", HttpStatus.BAD_REQUEST.value());
    jsonResponse.put("error", "Bad Request");
    jsonResponse.put("message", "Invalid value for field");
    jsonResponse.put("originalMessage", ex.getOriginalMessage());
    jsonResponse.put("field", ex.getPath().get(0).getFieldName());
    jsonResponse.put("rejectedValue", ex.getValue() + "");

    return handleExceptionInternal(ex, jsonResponse, null, HttpStatus.BAD_REQUEST, request);

  }
}
