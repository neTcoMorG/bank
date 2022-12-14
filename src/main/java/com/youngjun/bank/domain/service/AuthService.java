package com.youngjun.bank.domain.service;

import com.youngjun.bank.BankApplication;
import com.youngjun.bank.domain.dto.AdminDTO;
import com.youngjun.bank.domain.dto.TransferDTO;
import com.youngjun.bank.domain.entity.User;
import com.youngjun.bank.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/*
 * 회원가입 및 로그인을 제공하는 객체
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final HistoryService historyService;

    private void duplicateUser (String name) throws Exception {
        userRepository.findByUserName(name)
                .ifPresent(u -> {throw new RuntimeException("이미 존재하는 사용자");});
    }

    @Transactional
    public User createUser (String name) throws Exception {
        duplicateUser(name);
        User user = userRepository.save(User.of(name));
        historyService.addHistory(new TransferDTO(userRepository.findByUserName("영준뱅크").orElseThrow(), user, 1_000_000));
        user.setMoney(user.getMoney() + 1_000_000);
        return user;
    }

    public Optional<User> getUserWithUserName(String name) {
        return userRepository.findByUserName(name);
    }

    public User login (String name) throws Exception {
        return userRepository.findByUserName(name).orElseThrow();
    }
}
