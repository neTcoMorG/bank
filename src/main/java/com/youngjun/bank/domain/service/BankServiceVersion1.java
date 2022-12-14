package com.youngjun.bank.domain.service;

import com.youngjun.bank.BankApplication;
import com.youngjun.bank.domain.dto.TransferDTO;
import com.youngjun.bank.domain.entity.History;
import com.youngjun.bank.domain.entity.TradeType;
import com.youngjun.bank.domain.entity.User;

import com.youngjun.bank.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/*
 * 은행 시스템의 모든 기능을 담당
 * EX) 송금, 내역 조회
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class BankServiceVersion1 implements BankService {

    private final AccountValidUtil accountValidUtil;
    private final HistoryService historyService;
    private final UserRepository userRepository;

    /*
     * TransferDTO 를 가지고 계좌 이체 하는 메소드
     *
     * @param dto   TransferDTO 객체.
     * @return      송금자의 남은 금액을 반환합니다.
     * @throws Exception    송금하는 금액이 보유금액보다 많은 경우 예외를 발생시킵니다.
     */
    @Override
    @Transactional
    public Integer transferTo(TransferDTO dto) throws Exception {

        try {
            accountValidUtil.isValidWithDTO(dto);  // 송금, 받는 계좌 검증
        }
        catch (NoSuchElementException exception) { // 유효하지 않은 계좌번호 처리
            exception.printStackTrace();
        }

        if (dto.getTransfer().getMoney() < dto.getValue()) {
            throw new Exception("송금하는 금액이 보유 잔고보다 많습니다");
        }

        historyService.addHistory(dto);
        dto.getTransfer().setMoney(dto.getTransfer().getMoney() - dto.getValue());
        dto.getReceiver().setMoney(dto.getReceiver().getMoney() + dto.getValue());

        log.info("transfer left money: " + dto.getTransfer().getMoney());
        return dto.getTransfer().getMoney();
    }

    /*
     * 사용자의 거래내역을 가져오는 메소드/
     *
     * @param user   User 객체.
     * @return       해당 유저에 관련된 거래 리스트를 반환합니다
     */
    @Override
    public List<History> getHistories(User user) {
        return historyService.getHistories(user);
    }

    @Override
    public List<History> getHistoryWithType(TradeType type) {
        return new ArrayList<>();
    }

    @Override
    public Integer getMoney(String AN) throws Exception {
        return accountValidUtil.isValidWithAN(AN).getMoney();
    }

    @Override
    public String getAccountNumberWithName(String name) throws Exception {
        return accountValidUtil.isValidWithUserName(name).getAccountNumber();
    }

    @Override
    public User getUserWithName(String name) throws Exception {
        return accountValidUtil.isValidWithUserName(name);
    }

    @Override
    @Transactional
    public void jackpot(User user) {
        historyService.addHistory(new TransferDTO(userRepository.findByUserName("영준뱅크").orElseThrow(), user, 100_000));
        user.setMoney(user.getMoney() + 100_000);
    }

    @Override
    public User getUserWithAccount(String account) {
        return userRepository.findByAccountNumber(account).orElseThrow();
    }
}
