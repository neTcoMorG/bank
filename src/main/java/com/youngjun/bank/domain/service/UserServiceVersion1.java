package com.youngjun.bank.domain.service;

import com.youngjun.bank.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceVersion1 implements UserService {

    private final UserRepository userRepository;

    @Override
    public Integer getCurrentMoney() {
        return null;
    }

    @Override
    public String getAccountNumber() {
        return null;
    }
}
