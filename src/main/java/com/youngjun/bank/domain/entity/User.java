package com.youngjun.bank.domain.entity;

import com.youngjun.bank.domain.service.BankService;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BankUser")
public class User {

    protected  User() {}
    protected  User(String userName, Integer money) {
        this.userName = userName;
        this.money = money;
        this.accountNumber = generateAccountNumber();
    }
    protected  User(String userName) {
        this.userName = userName;
        this.money = 0;
        this.accountNumber = "7777";
    }

    public static User of(String name) {
        return new User(name, 0);
    }
    public static User adminOf() {
        return new User("영준뱅크");
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    private String userName;
    private Integer money;
    private String accountNumber;
    @CreatedDate private LocalDateTime created;

    @OneToMany(mappedBy = "transfer")  private List<History> transferHistories = new ArrayList<>();
    @OneToMany(mappedBy = "receiver") private List<History> receiverHistories = new ArrayList<>();

    private String generateAccountNumber() {
        return String.valueOf(RandomStringUtils.randomNumeric(12));
    }
}
