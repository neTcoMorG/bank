package com.youngjun.bank.domain.service;

import com.youngjun.bank.domain.dto.TransferDTO;
import com.youngjun.bank.domain.entity.History;
import com.youngjun.bank.domain.entity.User;
import com.youngjun.bank.domain.repository.TradeHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HistoryService {

    private final TradeHistoryRepository historyRepository;

    public void addHistory (TransferDTO object) {
        historyRepository.save(History.of(object));
    }

    public List<History> getHistories(User user) {
        List<History> byReceiver = historyRepository.findByReceiver(user);
        List<History> byTransfer = historyRepository.findByTransfer(user);
        List<History> result = new ArrayList<>();

        result.addAll(byReceiver); result.addAll(byTransfer);
        return result;
    }
}
