package com.youngjun.bank;

import com.youngjun.bank.domain.entity.User;
import com.youngjun.bank.domain.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class BankApplication {

	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

	@PostConstruct
	public void init() {
		if (userRepository.findByUserName("영준뱅크").isEmpty()) {
			userRepository.save(User.adminOf());
		}
	}
}
