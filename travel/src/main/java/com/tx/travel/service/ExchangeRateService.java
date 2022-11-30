package com.tx.travel.service;

import com.tx.travel.model.ExchangeRate;
import java.util.List;

public interface ExchangeRateService {

  List<ExchangeRate> getAllExchangeRates();

  ExchangeRate getExchangeRateById(Long id);

  void createExchangeRate(ExchangeRate exchangeRatePostRequest);

  void deleteExchangeRate(Long id);

  ExchangeRate updateExchangeRate(ExchangeRate exchangeRate);
}
