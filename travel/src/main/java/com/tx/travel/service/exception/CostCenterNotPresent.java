package com.tx.travel.service.exception;


import java.text.MessageFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason = "Value not present")
public class CostCenterNotPresent extends RuntimeException{

  public CostCenterNotPresent(final Long id){
    super(MessageFormat.format("Error: Cost center with id {0} does not exsist!", Long.toString(id)));
  }

}
