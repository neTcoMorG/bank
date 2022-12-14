package com.youngjun.bank.domain.repository;

import com.youngjun.bank.domain.entity.History;
import com.youngjun.bank.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeHistoryRepository extends JpaRepository<History, Long> {
    List<History> findByTransfer(User transfer);
    List<History> findByReceiver(User receiver);
}
