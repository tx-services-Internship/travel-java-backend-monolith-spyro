package com.tx.travel.service;

import com.tx.travel.mapper.DailyAllowanceMapper;
import com.tx.travel.model.DailyAllowance;
import com.tx.travel.payload.request.DailyAllowanceRequest;
import com.tx.travel.payload.response.DailyAllowanceResponse;
import com.tx.travel.repository.DailyAllowanceRepository;
import com.tx.travel.service.exception.DailyAllowanceNotFoundException;
import com.tx.travel.service.exception.RegionAlreadyExistsException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class DailyAllowanceService {

    private final DailyAllowanceRepository dailyAllowanceRepository;

    public DailyAllowanceService(final DailyAllowanceRepository dailyAllowanceRepository) {
        this.dailyAllowanceRepository = dailyAllowanceRepository;
    }

    public List<DailyAllowance> getAllDailyAllowances() {
        List<DailyAllowance> dailyAllowanceResponses = new CopyOnWriteArrayList<>();

        dailyAllowanceResponses.addAll(dailyAllowanceRepository.findAll());

        return dailyAllowanceResponses;
    }

    public DailyAllowance findByRegion(final String region) {

        Optional<DailyAllowance> dailyAllowance = dailyAllowanceRepository.findByRegion(region);

        if(dailyAllowance.isPresent()){
            return dailyAllowance.get();
        }else {
            throw new DailyAllowanceNotFoundException(region);
        }
    }

    public void addDailyAllowance(final DailyAllowance dailyAllowanceResponse) throws RegionAlreadyExistsException{

        dailyAllowanceRepository.save(dailyAllowanceResponse);
    }

    public DailyAllowance findById(Long id) {

        Optional<DailyAllowance> dailyAllowance = dailyAllowanceRepository.findById(id);

        if(dailyAllowance.isPresent()){
            return dailyAllowance.get();
        }else {
            throw new DailyAllowanceNotFoundException(id);
        }
    }

    public void deleteDailyAllowance(final Long id) throws DailyAllowanceNotFoundException {

        try{
           dailyAllowanceRepository.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            throw new DailyAllowanceNotFoundException(id);
        }
    }

    public DailyAllowance updateDailyAllowance(final DailyAllowance newDailyAllowanceInfo) {

        Optional<DailyAllowance> dailyAllowance = dailyAllowanceRepository.findByRegion(newDailyAllowanceInfo.getRegion());

        if(dailyAllowance.isPresent()){
            dailyAllowanceRepository.save(newDailyAllowanceInfo);
        }else {
            throw new DailyAllowanceNotFoundException(newDailyAllowanceInfo.getRegion());
        }

        return newDailyAllowanceInfo;
    }
}
