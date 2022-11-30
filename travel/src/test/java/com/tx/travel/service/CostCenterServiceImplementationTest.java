package com.tx.travel.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.tx.travel.AbstractUnitTestBase;
import com.tx.travel.model.CostCenter;
import com.tx.travel.repository.CostCenterRepository;
import com.tx.travel.service.exception.CostCenterCodeAlreadyExistsException;
import com.tx.travel.service.exception.CostCenterNotPresentException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class CostCenterServiceImplementationTest extends AbstractUnitTestBase {
  @Mock CostCenterRepository costCenterRepository;

  @InjectMocks CostCenterServiceImplementation sut;

  @DisplayName(
      "given no cost centers in db"
          + "when cost centers are requested to be fetched"
          + "then empty list of cost centers is returned")
  @Test
  public void fetchCostCentersTest_Success_EmptyListReturned() {

    when(costCenterRepository.findAll()).thenReturn(Collections.emptyList());

    List<CostCenter> costCenterList = sut.fetchCostCenters();
    assertNotNull(costCenterList);
    assertTrue(costCenterList.isEmpty());
  }

  @DisplayName(
      "given existing cost centers in db"
          + "when cost centers are requested to be fetched"
          + "then non empty list of cost centers is returned")
  @Test
  public void fetchCostCentersTest_Success_NonEmptyListReturned() {

    final String costCenterName = "testName";
    final String costCenterCode = "testCode";
    final Long costCenterId = 1L;

    ArrayList<CostCenter> retList = new ArrayList<>();
    retList.add(
        CostCenter.builder().name(costCenterName).code(costCenterCode).id(costCenterId).build());

    when(costCenterRepository.findAll()).thenReturn(retList);

    List<CostCenter> costCenterList = sut.fetchCostCenters();
    assertNotNull(costCenterList);
    assertFalse(costCenterList.isEmpty());
  }

  @DisplayName(
      "Given existing cost center"
          + "when cost center is requested to be fetched"
          + "then cost center is returned")
  @Test
  public void fetchCostCentersByIdTest_Success_CostCenterReturned() {
    final Long costCenterId = 1L;
    final String costCenterName = "testName";
    final String costCenterCode = "testCode";
    when(costCenterRepository.findById(costCenterId))
        .thenReturn(
            Optional.of(
                CostCenter.builder()
                    .name(costCenterName)
                    .code(costCenterCode)
                    .id(costCenterId)
                    .build()));

    CostCenter result = sut.fetchCostCenterById(costCenterId);

    assertNotNull(result);
    assertEquals(result.getId(), costCenterId);
    assertEquals(result.getName(), costCenterName);
    assertEquals(result.getCode(), costCenterCode);
  }

  @DisplayName(
      "Given non existing cost center"
          + "when cost center is requested to be fetched"
          + "then CostCenterNotPresentException exception is raised")
  @Test
  public void fetchCostCentersByIdTest_Failure_CostCenterNotPresentExceptionThrown() {
    final Long costCenterId = 1L;

    when(costCenterRepository.findById(costCenterId)).thenReturn(Optional.empty());

    Exception exception =
        assertThrows(
            CostCenterNotPresentException.class, () -> sut.fetchCostCenterById(costCenterId));

    String expectedMessage =
        MessageFormat.format(
            "Error: Cost center with id {0} does not exsist!", Long.toString(costCenterId));

    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @DisplayName(
      "Given a cost center with non existing code in db"
          + "when cost center is requested to be saved in db"
          + "then cost center is saved successfully and that cost center is returned")
  @Test
  public void saveCostCenter_Success_CostCenterReturned() {
    final Long costCenterId = 1L;
    final String costCenterName = "testName";
    final String costCenterCode = "testCode";
    final CostCenter costCenter =
        CostCenter.builder().name(costCenterName).code(costCenterCode).build();

    when(costCenterRepository.findByCode(costCenter.getCode())).thenReturn(Optional.empty());

    when(costCenterRepository.save(costCenter))
        .thenReturn(
            CostCenter.builder()
                .id(costCenterId)
                .name(costCenterName)
                .code(costCenterCode)
                .build());

    CostCenter costCenterReturned = sut.saveCostCenter(costCenter);

    assertEquals(costCenterReturned.getCode(), costCenter.getCode());
    assertEquals(costCenterReturned.getName(), costCenter.getName());
  }

  @DisplayName(
      "Given cost center with existing code in db"
          + "when cost center is requested to be saved in db"
          + "then CostCenterCodeAlreadyExistsException is raised")
  @Test
  public void saveCostCenter_Failure_CostCenterCodeAlreadyExistsExceptionThrown() {

    final Long costCenterId = 1L;
    final String costCenterName = "testName";
    final String costCenterCode = "testCode";

    final CostCenter costCenter =
        CostCenter.builder().name(costCenterName).code(costCenterCode).build();

    when(costCenterRepository.findByCode(costCenter.getCode()))
        .thenReturn(
            Optional.of(
                CostCenter.builder()
                    .id(costCenterId)
                    .name(costCenterName)
                    .code(costCenterCode)
                    .build()));

    Exception exception =
        assertThrows(
            CostCenterCodeAlreadyExistsException.class, () -> sut.saveCostCenter(costCenter));

    String expectedMessage =
        MessageFormat.format("Error: Code {0} already exists!", costCenter.getCode());

    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  @DisplayName(
      "Given existing cost center in db"
          + "when cost center is requested to be deleted"
          + "then cost center is deleted")
  public void deleteCostCenterByIdTest_Success() {

    final Long costCenterId = 1L;
    final String costCenterName = "testName";
    final String costCenterCode = "testCode";
    when(costCenterRepository.findById(costCenterId))
        .thenReturn(
            Optional.of(
                CostCenter.builder()
                    .name(costCenterName)
                    .code(costCenterCode)
                    .id(costCenterId)
                    .build()));

    assertDoesNotThrow(() -> sut.deleteCostCenterById(costCenterId));
  }

  @Test
  @DisplayName(
      "Given a cost center that does not exist in db"
          + "when cost center is requested to be deleted"
          + "then CostCenterNotPresentException is thrown")
  public void deleteCostCenterByIdTest_Failure_CostCenterNotPresentExceptionThrown() {
    final Long id = 1L;

    when(costCenterRepository.findById(id)).thenReturn(Optional.empty());

    Exception exception =
        assertThrows(CostCenterNotPresentException.class, () -> sut.deleteCostCenterById(id));

    String expectedMessage =
        MessageFormat.format("Error: Cost center with id {0} does not exsist!", Long.toString(id));

    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @DisplayName(
      "Given existing cost center with code that does not exist in db"
          + "when cost center is requested to be updated"
          + "then cost center is updated successfully")
  @Test
  public void updateCostCenterByIdTest_Success_CostCenterReturned() {
    final Long costCenterId = 1L;
    final String costCenterNameToBeChangedTo = "testName";
    final String costCenterCodeToBeChangedTo = "testCode";
    final String costCenterFetchedName = "testName1";
    final String costCenterFetchedCode = "testName2";

    final CostCenter costCenter =
        CostCenter.builder()
            .id(costCenterId)
            .code(costCenterCodeToBeChangedTo)
            .name(costCenterNameToBeChangedTo)
            .build();

    final CostCenter fetchedCostCenter =
        CostCenter.builder()
            .id(costCenterId)
            .code(costCenterFetchedCode)
            .name(costCenterFetchedName)
            .build();

    when(costCenterRepository.findById(costCenterId)).thenReturn(Optional.of(fetchedCostCenter));

    when(costCenterRepository.findByCode(costCenterCodeToBeChangedTo)).thenReturn(Optional.empty());

    when(costCenterRepository.save(costCenter)).thenReturn(costCenter);

    CostCenter result = sut.updateCostCenterById(costCenterId, costCenter);

    assertNotNull(result);
    assertEquals(result.getCode(), costCenterCodeToBeChangedTo);
    assertEquals(result.getName(), costCenterNameToBeChangedTo);
    assertEquals(result.getId(), costCenterId);
  }

  @DisplayName(
      "Given existing cost center with code that exists in db but it's that cost centers code"
          + "when cost center is requested to be updated"
          + "then cost center is updated successfully")
  @Test
  public void updateCostCenterByIdTest_Success_CostCenterReturned_SameCode() {
    final Long costCenterId = 1L;
    final String costCenterCode = "testCode";
    final String costCenterName = "testName";
    final String fetchedCostCenterName = "testName1";
    final CostCenter costCenter =
        CostCenter.builder().id(costCenterId).code(costCenterCode).name(costCenterName).build();

    final CostCenter fetchedCostCenter =
        CostCenter.builder()
            .id(costCenterId)
            .name(fetchedCostCenterName)
            .code(costCenterCode)
            .build();

    when(costCenterRepository.findById(costCenterId)).thenReturn(Optional.of(fetchedCostCenter));

    when(costCenterRepository.findByCode(costCenterCode))
        .thenReturn(Optional.of(fetchedCostCenter));

    when(costCenterRepository.save(costCenter)).thenReturn(costCenter);

    CostCenter result = sut.updateCostCenterById(costCenterId, costCenter);

    assertNotNull(result);
    assertEquals(result.getId(), costCenterId);
    assertEquals(result.getCode(), costCenterCode);
    assertEquals(result.getName(), costCenterName);
  }

  @DisplayName(
      "Given a non existing cost center"
          + "when cost center is requested to be updated"
          + "then CostCenterNotPresentException is thrown")
  @Test
  public void updateCostCenterByIdTest_Failure_CostCenterNotPresentExceptionThrown() {
    final Long costCenterId = 1L;
    final CostCenter costCenter =
        CostCenter.builder().id(costCenterId).code("testCode").name("testName").build();

    when(costCenterRepository.findById(costCenterId)).thenReturn(Optional.empty());

    Exception exception =
        assertThrows(
            CostCenterNotPresentException.class,
            () -> sut.updateCostCenterById(costCenterId, costCenter));

    String expectedMessage =
        MessageFormat.format(
            "Error: Cost center with id {0} does not exsist!", Long.toString(costCenterId));

    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @DisplayName(
      "Given existing cost center with code that already exists in db but it's not that cost centers code"
          + "when cost center is requested to be updated"
          + "then CostCenterCodeAlreadyExistsException is thrown")
  @Test
  public void updateCostCenterByIdTest_Failure_CostCenterCodeAlreadyExistsExceptionThrown() {
    final Long costCenterId = 1L;
    final String costCenterNameToBeChangedTo = "testName";
    final String costCenterCodeToBeChangedTo = "testCode";

    final String fetchedCostCenterName = "testName2";
    final String fetchedCostCenterCode = "testCode2";
    final CostCenter costCenter =
        CostCenter.builder()
            .id(costCenterId)
            .code(costCenterCodeToBeChangedTo)
            .name(costCenterNameToBeChangedTo)
            .build();

    when(costCenterRepository.findById(costCenterId))
        .thenReturn(
            Optional.of(
                CostCenter.builder()
                    .id(costCenterId)
                    .name(fetchedCostCenterName)
                    .code(fetchedCostCenterCode)
                    .build()));

    when(costCenterRepository.findByCode(costCenterCodeToBeChangedTo))
        .thenReturn(
            Optional.of(
                CostCenter.builder()
                    .id(costCenterId + 1)
                    .name("testName3")
                    .code(costCenterCodeToBeChangedTo)
                    .build()));

    Exception exception =
        assertThrows(
            CostCenterCodeAlreadyExistsException.class,
            () -> sut.updateCostCenterById(costCenterId, costCenter));

    String expectedMessage =
        MessageFormat.format("Error: Code {0} already exists!", costCenterCodeToBeChangedTo);

    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }
}
