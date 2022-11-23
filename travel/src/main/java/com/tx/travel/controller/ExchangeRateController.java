package com.tx.travel.controller;

import com.tx.travel.payload.response.ExchangeRateResponse;
import com.tx.travel.service.ExchangeRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/exchange-rates")
public class ExchangeRateController {

    private ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService){
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping
    public ResponseEntity<List<ExchangeRateResponse>> getAllExchangeRates(){

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExchangeRateResponse> getExchangeRateById(@PathVariable Long id){

        return null;
    }

    @PostMapping
    public ResponseEntity<?> createExchangeRate(){

        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExchangeRate(@PathVariable Long id){

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExchangeRate(@PathVariable Long id){

        return null;
    }
}
