package com.youngjun.bank.domain.dto;

import com.youngjun.bank.domain.entity.History;
import com.youngjun.bank.domain.entity.TradeType;
import lombok.Data;

@Data
public class ProcessedHistory {

    public ProcessedHistory(History raw, TradeType tradeType) {
        if(tradeType.name().equals("RECV")) {
            this.tradeType = "RECV";
        }
        else {
            this.tradeType = "SEND";
        }
        this.raw = raw;
    }

    private History raw;
    private String tradeType;
}
