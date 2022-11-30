package com.tx.travel.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.tx.travel.AbstractUnitTestBase;
import com.tx.travel.model.ExchangeRate;
import com.tx.travel.repository.ExchangeRateRepository;
import com.tx.travel.service.exception.ExchangeRateAlreadyDefinedException;
import com.tx.travel.service.exception.ExchangeRateAmountInRsdNotValidException;
import com.tx.travel.service.exception.ExchangeRateCodeNotValidException;
import com.tx.travel.service.exception.ExchangeRateNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ExchangeRateServiceLocalImplTest extends AbstractUnitTestBase {

  @Mock ExchangeRateRepository exchangeRateRepository;

  @InjectMocks ExchangeRateServiceLocalImpl exchangeRateServiceLocal;

  @Test
  @DisplayName(
      "given list of all exchange rates"
          + "when getting list of all exchange rates"
          + "then return list of all exchange rates successfully")
  public void getAllExchangeRates_success() {

    long id = 1L;
    List<ExchangeRate> exchangeRateList =
        Arrays.asList(
            ExchangeRate.builder()
                .id(id++)
                .code("EUR")
                .amountInRsd(new BigDecimal("150.211"))
                .build(),
            ExchangeRate.builder()
                .id(id++)
                .code("CHF")
                .amountInRsd(new BigDecimal("150.211"))
                .build(),
            ExchangeRate.builder()
                .id(id)
                .code("USD")
                .amountInRsd(new BigDecimal("150.211"))
                .build());

    when(exchangeRateRepository.findAll()).thenReturn(exchangeRateList);

    final List<ExchangeRate> result = exchangeRateRepository.findAll();
    assertIterableEquals(exchangeRateList, result);
  }

  @Test
  @DisplayName(
      "given exchange rate" + "when getting exchange rate by id" + "then return exchange rate")
  public void getExchangeRateById_success() {

    long id = 1L;
    String code = "USA";
    BigDecimal amountInRsd = new BigDecimal("115.17");

    ExchangeRate exchangeRate =
        ExchangeRate.builder().id(id).code(code).amountInRsd(amountInRsd).build();

    when(exchangeRateRepository.findById(id)).thenReturn(Optional.of(exchangeRate));

    exchangeRateServiceLocal.getExchangeRateById(id);
  }

  @Test
  @DisplayName(
      "given exchange rate"
          + "when getting exchange rate by id"
          + "then return ExchangeRateNotFoundException")
  public void getExchangeRateById_notFound() {

    long id = 1L;

    when(exchangeRateRepository.findById(id)).thenThrow(new NoSuchElementException());

    assertThrows(
        NoSuchElementException.class, () -> exchangeRateServiceLocal.getExchangeRateById(id));
  }

  @Test
  @DisplayName(
      "given new exchange rate"
          + "when saving new exchange rate"
          + "then save new exchange rate in database")
  public void createExchangeRate_success() {

    long id = 1L;
    String code = "USD";
    BigDecimal amountInRsd = new BigDecimal("120.23");

    ExchangeRate newExchangeRate =
        ExchangeRate.builder().id(id).code(code).amountInRsd(amountInRsd).build();

    when(exchangeRateRepository.save(newExchangeRate)).thenReturn(newExchangeRate);

    exchangeRateServiceLocal.createExchangeRate(newExchangeRate);

    verify(exchangeRateRepository).findByCode(code);
    verify(exchangeRateRepository).save(newExchangeRate);
  }

  @Test
  @DisplayName(
      "given exchange rate with null currency code"
          + "when validating exchange rate by code"
          + "then throw ExchangeRateCodeNotValidException")
  public void createExchangeRate_codeIsNull() {
    long id = 1L;
    BigDecimal amountInRsd = new BigDecimal("120.23");

    ExchangeRate newExchangeRate =
        ExchangeRate.builder().id(id).code(null).amountInRsd(amountInRsd).build();

    assertThrows(
        ExchangeRateCodeNotValidException.class,
        () -> exchangeRateServiceLocal.createExchangeRate(newExchangeRate));
  }

  @Test
  @DisplayName(
      "given exchange rate with incompatible currency code length"
          + "when validating exchange rate by code"
          + "then throw ExchangeRateCodeNotValidException")
  public void createExchangeRate_codeLengthIsNotValid() {
    long id = 1L;
    String code = "USDRSD";
    BigDecimal amountInRsd = new BigDecimal("120.23");

    ExchangeRate newExchangeRate =
        ExchangeRate.builder().id(id).code(code).amountInRsd(amountInRsd).build();

    assertThrows(
        ExchangeRateCodeNotValidException.class,
        () -> exchangeRateServiceLocal.createExchangeRate(newExchangeRate));
  }

  @Test
  @DisplayName(
      "given exchange rate currency code"
          + "when validating exchange rate by code"
          + "then throw ExchangeRateAlreadyExistsException")
  public void createExchangeRate_exchangeRateAlreadyDefined() {

    long id = 1L;
    String code = "EUR";
    BigDecimal amountInRsd = new BigDecimal("115.17");
    String codeForMethod = "EUR";

    ExchangeRate exchangeRate =
        ExchangeRate.builder().id(id).code(code).amountInRsd(amountInRsd).build();

    when(exchangeRateRepository.findByCode(codeForMethod)).thenReturn(Optional.of(exchangeRate));

    assertThrows(
        ExchangeRateAlreadyDefinedException.class,
        () -> exchangeRateServiceLocal.createExchangeRate(exchangeRate),
        "Exchange rate for USD is already defined! Update an existing one.");
  }

  @Test
  @DisplayName(
      "given exchange rate id" + "when deleting exchange rate" + "then delete exchange rate")
  public void deleteExchangeRate_success() {

    long id = 1L;

    doNothing().when(exchangeRateRepository).deleteById(id);

    exchangeRateServiceLocal.deleteExchangeRate(id);

    verify(exchangeRateRepository).deleteById(id);
  }

  @Test
  @DisplayName(
      "given exchange rate id"
          + "when deleting exchange rate"
          + "then throw ExchangeRateNotFoundException")
  public void deleteExchangeRate_exchangeRateNotFound() {

    long id = 1L;

    doThrow(new ExchangeRateNotFoundException(id)).when(exchangeRateRepository).deleteById(id);

    assertThrows(
        ExchangeRateNotFoundException.class, () -> exchangeRateServiceLocal.deleteExchangeRate(id));
  }

  @Test
  @DisplayName(
      "given exchange rate with updated amountInRsd"
          + "when updating exchange rate"
          + "then update exchange rate")
  public void updateExchangeRate_success() {

    long id = 1L;
    String code = "EUR";
    BigDecimal amountInRsd = new BigDecimal("117.32");
    BigDecimal updatedAmountInRsd = new BigDecimal("100");

    ExchangeRate exchangeRate =
        ExchangeRate.builder().id(id).code(code).amountInRsd(amountInRsd).build();

    when(exchangeRateRepository.findById(id)).thenReturn(Optional.of(exchangeRate));

    ExchangeRate updatedExchangeRate =
        ExchangeRate.builder().id(id).amountInRsd(updatedAmountInRsd).build();

    ArgumentCaptor<ExchangeRate> exchangeRateToBeSaved =
        ArgumentCaptor.forClass(ExchangeRate.class);
    when(exchangeRateRepository.save(exchangeRateToBeSaved.capture()))
        .thenReturn(updatedExchangeRate);

    exchangeRateServiceLocal.updateExchangeRate(updatedExchangeRate);

    assertEquals(updatedAmountInRsd, exchangeRateToBeSaved.getValue().getAmountInRsd());
    verify(exchangeRateRepository).save(exchangeRateToBeSaved.getValue());
  }

  @Test
  @DisplayName(
      "given exchange rate with updated amountInRsd"
          + "when updating exchange rate"
          + "then throw ExchangeRateNotFound")
  public void updateExchangeRate_exchangeRateNotFound() {

    long id = 1L;
    String code = "EUR";
    BigDecimal amountInRsd = new BigDecimal("117.32");

    ExchangeRate exchangeRate =
        ExchangeRate.builder().id(id).code(code).amountInRsd(amountInRsd).build();

    when(exchangeRateRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(
        ExchangeRateNotFoundException.class,
        () -> exchangeRateServiceLocal.updateExchangeRate(exchangeRate));
  }

  @Test
  @DisplayName(
      "given exchange rate with null amountInRsd"
          + "when updating exchange rate"
          + "then throw AmountInRsdIsNotValid")
  public void updateExchangeRate_amountInRsdIsNull() {

    long id = 1L;

    ExchangeRate exchangeRate = ExchangeRate.builder().id(id).amountInRsd(null).build();

    assertThrows(
        ExchangeRateAmountInRsdNotValidException.class,
        () -> exchangeRateServiceLocal.updateExchangeRate(exchangeRate));
  }

  @Test
  @DisplayName(
      "given exchange rate with negative amountInRsd"
          + "when updating exchange rate"
          + "then throw AmountInRsdIsNotValid")
  public void updateExchangeRate_amountInRsdIsNegative() {

    long id = 1L;
    BigDecimal negativeAmountInRsd = new BigDecimal("-110.202");

    ExchangeRate exchangeRate =
        ExchangeRate.builder().id(id).amountInRsd(negativeAmountInRsd).build();

    assertThrows(
        ExchangeRateAmountInRsdNotValidException.class,
        () -> exchangeRateServiceLocal.updateExchangeRate(exchangeRate));
  }
}
