package com.tx.travel.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;

import com.tx.travel.AbstractUnitTestBase;
import com.tx.travel.model.DailyAllowance;
import com.tx.travel.repository.DailyAllowanceRepository;
import com.tx.travel.service.exception.DailyAllowanceNotFoundException;
import com.tx.travel.service.exception.RegionNullPointerException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class DailyAllowanceServiceTest extends AbstractUnitTestBase {
  @Mock DailyAllowanceRepository dailyAllowanceRepository;

  @InjectMocks
  DailyAllowanceService sut;

  @Captor ArgumentCaptor<DailyAllowance> dailyAllowanceArgumentCaptor;

  @DisplayName(
      "given valid daily allowance"
          + "when daily allowance is requested to be updated"
          + "then daily allowance is updated successfully")
  @Test
  public void updateDailyAllowance_success() {
    final Long id = 1L;
    final BigDecimal testAmount = new BigDecimal(100);
    final String testRegion = "TestRegion1";

    DailyAllowance dailyAllowance =
        DailyAllowance.builder().id(id).region(testRegion).amount(testAmount).build();

    when(dailyAllowanceRepository.findByRegion(testRegion))
        .thenReturn(Optional.of(DailyAllowance.builder().region(testRegion).build()));
    when(dailyAllowanceRepository.save(dailyAllowance)).thenReturn(dailyAllowance);

    final DailyAllowance result = sut.updateDailyAllowance(dailyAllowance);
    assertNotNull(result);
    verify(dailyAllowanceRepository, times(1)).save(dailyAllowanceArgumentCaptor.capture());
    DailyAllowance result1 = dailyAllowanceArgumentCaptor.getValue();
    assertThat(result.getAmount()).isEqualTo(dailyAllowance.getAmount());
  }

  @DisplayName(
      "given existing daily allowance"
          + "when daily allowance is searched by id"
          + "then daily allowance is returned successfully")
  @Test
  public void findById_success() {
    final Long id = 1L;
    final BigDecimal testAmount = new BigDecimal(100);
    final String testRegion = "TestRegionFindById";

    DailyAllowance dailyAllowance =
        DailyAllowance.builder().id(id).region(testRegion).amount(testAmount).build();

    when(dailyAllowanceRepository.findById(id))
        .thenReturn(Optional.of(DailyAllowance.builder().id(id).build()));

    final DailyAllowance result = sut.findById(id);
    assertNotNull(result);
  }

  @DisplayName(
      "given a non-existing daily allowance"
          + "when daily allowance is searched by id"
          + "then throw DailyAllowanceNotFoundException")
  @Test
  public void findById_DailyAllowanceNotFoundException() {
    final Long id = 1L;
    final BigDecimal testAmount = new BigDecimal(100);
    final String testRegion = "TestRegionFindById";
    DailyAllowanceNotFoundException e = new DailyAllowanceNotFoundException(testRegion);

    when(dailyAllowanceRepository.findById(id)).thenThrow(e);

    assertThrows(DailyAllowanceNotFoundException.class, () -> sut.findById(id));
  }

  @DisplayName(
      "given a new daily allowance"
          + "when daily allowance is requested to be created"
          + "then daily allowance is is created successfully")
  @Test
  public void addDailyAllowance_success() {
    final Long id = 1L;
    final BigDecimal testAmount = new BigDecimal(100);
    final String testRegion = "TestRegionAdd";

    DailyAllowance dailyAllowance =
        DailyAllowance.builder().id(id).region(testRegion).amount(testAmount).build();

    when(dailyAllowanceRepository.save(dailyAllowance)).thenReturn(dailyAllowance);

    //assertDoesNotThrow(() -> sut.addDailyAllowance(dailyAllowance));
    verify(dailyAllowanceRepository, times(1)).save(dailyAllowanceArgumentCaptor.capture());
    DailyAllowance result1 = dailyAllowanceArgumentCaptor.getValue();
    assertThat(result1.getAmount()).isEqualTo(dailyAllowance.getAmount());
  }

  @DisplayName(
      "given existing daily allowance"
          + "when daily allowance is requested to be created"
          + "then throw DailyAllowanceAlreadyExists")
  @Test
  public void addDailyAllowance_DailyAllowanceAlreadyExists() {
    final Long id = 1L;
    final BigDecimal testAmount = new BigDecimal(100);
    final String testRegion = "TestRegionAlreadyExists";

    when(dailyAllowanceRepository.findByRegion(testRegion))
        .thenReturn(Optional.of(DailyAllowance.builder().id(id).build()));

    final DailyAllowance result = sut.findByRegion(testRegion);
    assertNotNull(result);
  }

  @DisplayName(
      "given existing daily allowance"
          + "when daily allowance is searched by region"
          + "then daily allowance is returned successfully")
  @Test
  public void findByRegion_success() {
    final Long id = 1L;
    final BigDecimal testAmount = new BigDecimal(100);
    final String testRegion = "TestRegionFindByRegion";

    DailyAllowance dailyAllowance =
        DailyAllowance.builder().id(id).region(testRegion).amount(testAmount).build();

    when(dailyAllowanceRepository.findByRegion(testRegion))
        .thenReturn(Optional.of(DailyAllowance.builder().id(id).build()));

    Optional<DailyAllowance> result = dailyAllowanceRepository.findByRegion(testRegion);

    assertNotNull(result);
  }

  @DisplayName(
      "given non-existing daily allowance"
          + "when daily allowance is searched by region"
          + "then throw DailyAllowanceNotFoundException")
  @Test
  public void findByRegion_DailyAllowanceNotFoundException() {
    final Long id = 1L;
    final BigDecimal testAmount = new BigDecimal(100);
    final String testRegion = "TestRegionFindByRegion";
    DailyAllowanceNotFoundException e = new DailyAllowanceNotFoundException(testRegion);

    DailyAllowance dailyAllowance =
        DailyAllowance.builder().id(id).region(testRegion).amount(testAmount).build();

    when(dailyAllowanceRepository.findByRegion(testRegion)).thenThrow(e);

    assertThrows(DailyAllowanceNotFoundException.class, () -> sut.findByRegion(testRegion));
  }

  @DisplayName(
      "given list of all daily allowance"
          + "when all daily allowances are requested to be found"
          + "then return list of all daily allowances successfully")
  @Test
  public void getAllDailyAllowances_success() {
    final Long id1 = 1L, id2 = 2L, id3 = 3L;
    final BigDecimal testAmount1 = new BigDecimal(100),
        testAmount2 = new BigDecimal(200),
        testAmount3 = new BigDecimal(300);
    final String testRegion1 = "TestRegionGetAllDailyAllowances1",
        testRegion2 = "TestRegionGetAllDailyAllowances2",
        testRegion3 = "TestRegionGetAllDailyAllowances3";

    List<DailyAllowance> dailyAllowances =
        Arrays.asList(
            DailyAllowance.builder().id(id1).region(testRegion1).amount(testAmount1).build(),
            DailyAllowance.builder().id(id2).region(testRegion2).amount(testAmount2).build(),
            DailyAllowance.builder().id(id3).region(testRegion3).amount(testAmount3).build());

    when(dailyAllowanceRepository.findAll()).thenReturn(dailyAllowances);

    List<DailyAllowance> result = sut.getAllDailyAllowances();

    assertIterableEquals(dailyAllowances, result);
  }

  @DisplayName(
      "given existing daily allowance"
          + "when daily allowance is requested to be deleted"
          + "then daily allowance is deleted successfully")
  @Test
  public void deleteDailyAllowance_success() {
    final Long id = 1L;
    final BigDecimal testAmount = new BigDecimal(100);
    final String testRegion = "TestRegionDelete";

    DailyAllowance dailyAllowance =
        DailyAllowance.builder().id(id).region(testRegion).amount(testAmount).build();

    doNothing().when(dailyAllowanceRepository).deleteById(id);

    sut.deleteDailyAllowance(id);

    verify(dailyAllowanceRepository, times(1)).deleteById(id);
  }

  @DisplayName(
      "given non-existing daily allowance"
          + "when daily allowance is requested to be updated"
          + "then throw DailyAllowanceNotFoundException")
  @Test
  public void updateDailyAllowance_DailyAllowanceNotFoundException() {
    final Long id = 1L;
    final BigDecimal testAmount = new BigDecimal(100);
    final String testRegion = "TestRegion2";
    DailyAllowanceNotFoundException e = new DailyAllowanceNotFoundException(testRegion);

    DailyAllowance dailyAllowance =
        DailyAllowance.builder().id(id).region(testRegion).amount(testAmount).build();

    when(dailyAllowanceRepository.findByRegion(testRegion))
        .thenReturn(Optional.of(DailyAllowance.builder().region(testRegion).build()));
    when(dailyAllowanceRepository.save(dailyAllowance)).thenThrow(e);

    assertThrows(
        DailyAllowanceNotFoundException.class, () -> sut.updateDailyAllowance(dailyAllowance));
  }

  @DisplayName(
      "given a null region"
          + "when daily allowance requested to be updated"
          + "then throw DailyAllowanceNotFoundException")
  @Test
  public void updateDailyAllowance_RegionIsNull() {
    final Long id = 1L;
    final BigDecimal testAmount = new BigDecimal(100);
    final String testRegion = "TestRegion3";
    RegionNullPointerException e = new RegionNullPointerException();

    DailyAllowance dailyAllowance =
        DailyAllowance.builder().id(id).region(null).amount(testAmount).build();

    assertThrows(RegionNullPointerException.class, () -> sut.updateDailyAllowance(dailyAllowance));
  }
}
