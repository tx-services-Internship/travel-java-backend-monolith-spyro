package com.tx.travel.service;

import com.tx.travel.model.ExchangeRate;
import com.tx.travel.repository.ExchangeRateRepository;
import com.tx.travel.service.exception.ExchangeRateAlreadyDefined;
import com.tx.travel.service.exception.ExchangeRateNotFound;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRateServiceLocalImpl implements ExchangeRateService {

  private final ExchangeRateRepository exchangeRateRepository;

  public ExchangeRateServiceLocalImpl(ExchangeRateRepository exchangeRateRepository) {
    this.exchangeRateRepository = exchangeRateRepository;
  }

  @Override
  public List<ExchangeRate> getAllExchangeRates() {

    return exchangeRateRepository.findAll();
  }

  @Override
  public ExchangeRate getExchangeRateById(Long id) throws NoSuchElementException {

    Optional<ExchangeRate> exchangeRateById = exchangeRateRepository.findById(id);

    if (exchangeRateById.isPresent()) return exchangeRateById.get();
    else throw new NoSuchElementException();
  }

  public void validateByCode(String code) throws ExchangeRateAlreadyDefined {

    Optional<ExchangeRate> exchangeRateByCode = exchangeRateRepository.findByCode(code);

    if (exchangeRateByCode.isPresent()) throw new ExchangeRateAlreadyDefined(code);
  }

  @Override
  public void createExchangeRate(ExchangeRate exchangeRatePost) {

    exchangeRateRepository.save(exchangeRatePost);
  }

  @Override
  public void deleteExchangeRate(Long id) throws ExchangeRateNotFound {

    try {
      exchangeRateRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ExchangeRateNotFound(id);
    }
  }
  // TODO Transactional
  @Override
  public ExchangeRate updateExchangeRate(ExchangeRate exchangeRatePutRequest)
      throws ExchangeRateNotFound {

    Optional<ExchangeRate> exchangeRateWithCode =
        exchangeRateRepository.findById(exchangeRatePutRequest.getId());

    if (exchangeRateWithCode.isPresent()) {
      ExchangeRate updatedExchangeRate =
          ExchangeRate.builder()
              .id(exchangeRatePutRequest.getId())
              .code(exchangeRateWithCode.get().getCode())
              .amountInRsd(exchangeRatePutRequest.getAmountInRsd())
              .build();

      exchangeRateRepository.save(updatedExchangeRate);

      return updatedExchangeRate;
    } else {
      throw new ExchangeRateNotFound(exchangeRatePutRequest.getId());
    }
  }
}
