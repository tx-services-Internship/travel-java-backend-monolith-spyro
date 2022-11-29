package com.tx.travel.controller;

import com.tx.travel.mapper.ExchangeRateMapper;
import com.tx.travel.model.ExchangeRate;
import com.tx.travel.payload.request.ExchangeRatePostRequest;
import com.tx.travel.payload.request.ExchangeRatePutRequest;
import com.tx.travel.payload.response.ExchangeRateResponse;
import com.tx.travel.service.ExchangeRateService;
import com.tx.travel.service.exception.ExchangeRateAlreadyDefined;
import com.tx.travel.service.exception.ExchangeRateNotFound;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("${apiPrefix}/exchange-rates")
public class ExchangeRateController {

  private final ExchangeRateService exchangeRateService;
  private final ExchangeRateMapper exchangeRateMapper;

  public ExchangeRateController(
      ExchangeRateService exchangeRateService, ExchangeRateMapper exchangeRateMapper) {
    this.exchangeRateService = exchangeRateService;
    this.exchangeRateMapper = exchangeRateMapper;
  }

  @GetMapping
  public ResponseEntity<List<ExchangeRateResponse>> getAllExchangeRates() {

    List<ExchangeRateResponse> exchangeRateResponses = new ArrayList<>();

    for (ExchangeRate er : exchangeRateService.getAllExchangeRates()) {
      exchangeRateResponses.add(exchangeRateMapper.mapExchangeRateToExchangeRateResponse(er));
    }

    return ResponseEntity.ok().body(exchangeRateResponses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExchangeRateResponse> getExchangeRateById(@PathVariable Long id) {

    ExchangeRate exchangeRateById;

    try {
      exchangeRateById = exchangeRateService.getExchangeRateById(id);
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    }

    return ResponseEntity.ok()
        .body(exchangeRateMapper.mapExchangeRateToExchangeRateResponse(exchangeRateById));
  }

  @PostMapping
  public ResponseEntity<?> createExchangeRate(
      @Valid @RequestBody ExchangeRatePostRequest exchangeRatePostRequest) {

    try {
      exchangeRateService.validateByCode(exchangeRatePostRequest.getCode());
    } catch (ExchangeRateAlreadyDefined e) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
    }

    exchangeRateService.createExchangeRate(
        exchangeRateMapper.mapExchangeRatePostRequestToExchangeRate(exchangeRatePostRequest));

    return ResponseEntity.ok().body("Successfully created exchanged rate!");
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateExchangeRate(
      @Valid @RequestBody ExchangeRatePutRequest exchangeRatePutRequest, @PathVariable Long id) {

    try {
      ExchangeRate newExchangeRate =
          exchangeRateMapper.mapExchangeRatePutRequestToExchangeRate(exchangeRatePutRequest, id);

      ExchangeRateResponse updatedExchangeRateResponse =
          exchangeRateMapper.mapExchangeRateToExchangeRateResponse(
              exchangeRateService.updateExchangeRate(newExchangeRate));

      return ResponseEntity.ok().body(updatedExchangeRateResponse);
    } catch (ExchangeRateNotFound e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteExchangeRate(@PathVariable Long id) {

    try {
      exchangeRateService.deleteExchangeRate(id);
    } catch (ExchangeRateNotFound e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    }

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Exchange rate successfully deleted.");
  }
}
