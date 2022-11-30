package com.tx.travel.service;

import static org.springframework.util.ObjectUtils.isEmpty;

import com.tx.travel.model.ExchangeRate;
import com.tx.travel.repository.ExchangeRateRepository;
import com.tx.travel.service.exception.ExchangeRateAlreadyDefinedException;
import com.tx.travel.service.exception.ExchangeRateAmountInRsdNotValidException;
import com.tx.travel.service.exception.ExchangeRateCodeNotValidException;
import com.tx.travel.service.exception.ExchangeRateNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Override
  public void createExchangeRate(ExchangeRate exchangeRatePost) throws ExchangeRateAlreadyDefinedException {
    validateByCode(exchangeRatePost.getCode());
    exchangeRateRepository.save(exchangeRatePost);
  }

  private void validateByCode(String code)
      throws ExchangeRateAlreadyDefinedException {
    validateCodeContent(code);
    Optional<ExchangeRate> exchangeRateByCode = exchangeRateRepository.findByCode(code);
    if (exchangeRateByCode.isPresent()) throw new ExchangeRateAlreadyDefinedException(code);
  }

  private void validateCodeContent(final String code) throws ExchangeRateCodeNotValidException {
    if (isEmpty(code) || code.length() != 3) throw new ExchangeRateCodeNotValidException(code);
  }

  @Override
  public void deleteExchangeRate(Long id) throws ExchangeRateNotFoundException {

    try {
      exchangeRateRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ExchangeRateNotFoundException(id);
    }
  }

  @Override
  @Transactional
  public ExchangeRate updateExchangeRate(ExchangeRate exchangeRatePutRequest)
      throws ExchangeRateNotFoundException, ExchangeRateAmountInRsdNotValidException {

    if (exchangeRatePutRequest.getAmountInRsd() == null
        || exchangeRatePutRequest.getAmountInRsd().floatValue() < 0)
      throw new ExchangeRateAmountInRsdNotValidException();

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
      throw new ExchangeRateNotFoundException(exchangeRatePutRequest.getId());
    }
  }
}
