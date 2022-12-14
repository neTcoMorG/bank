package com.youngjun.bank.domain.service;

import com.youngjun.bank.domain.dto.TransferDTO;
import com.youngjun.bank.domain.entity.History;
import com.youngjun.bank.domain.entity.TradeType;
import com.youngjun.bank.domain.entity.User;

import java.util.List;
public interface BankService {

    Integer transferTo(TransferDTO dto) throws Exception;
    List<History> getHistories(User user);
    List<History> getHistoryWithType(TradeType type);
    Integer getMoney(String AN) throws Exception;
    String getAccountNumberWithName(String name) throws Exception;
    User getUserWithName(String name) throws Exception;
    void jackpot(User user);
    User getUserWithAccount(String account);
}
