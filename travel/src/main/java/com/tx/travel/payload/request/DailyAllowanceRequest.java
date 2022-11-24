package com.tx.travel.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DailyAllowanceRequest {

    private final String region;
    private final double amount;

    public DailyAllowanceRequest(String region, double amount) {
        this.region = region;
        this.amount = amount;
    }

    public DailyAllowanceRequest() {
        this.region = null;
        this.amount = 0;
    }

}