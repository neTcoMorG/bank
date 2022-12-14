package com.youngjun.bank.domain.service;

import com.youngjun.bank.domain.dto.TransferDTO;
import com.youngjun.bank.domain.dto.TransferEntity;
import com.youngjun.bank.domain.entity.User;
import com.youngjun.bank.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountValidUtil {

    private final UserRepository userRepository;

    public void isValidWithDTO(TransferDTO dto) throws Exception {
        Optional<User> transferOpt = userRepository.findByAccountNumber(dto.getTransfer().getAccountNumber());
        Optional<User> receiverOpt = userRepository.findByAccountNumber(dto.getReceiver().getAccountNumber());

        User transfer = transferOpt.orElseThrow();
        User receiver = receiverOpt.orElseThrow();
    }

    public User isValidWithAN(String accountNumber) throws NoSuchElementException {
        return userRepository.findByAccountNumber(accountNumber).orElseThrow();
    }

    public User isValidWithUserName(String userName) throws Exception {
        return userRepository.findByUserName(userName).orElseThrow();
    }
}
