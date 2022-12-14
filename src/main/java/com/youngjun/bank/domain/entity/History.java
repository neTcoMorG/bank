package com.youngjun.bank.domain.entity;

import com.youngjun.bank.domain.dto.TransferDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class History {

    protected History(){}
    protected History(User transfer, User receiver, Integer money) {
        this.transfer = transfer;
        this.receiver = receiver;
        this.money = money;
        this.transferLeftMoney = transfer.getMoney() - money;
        this.receiverLeftMoney = receiver.getMoney() + money;
    }

    public static History of (TransferDTO object) {
        return new History(
                object.getTransfer(),
                object.getReceiver(),
                object.getValue()
        );
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "TRANSFER_ACCOUNT_NUMBER") private User transfer;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "RECEIVER_ACCOUNT_NUMBER") private User receiver;
    private Integer money;
    private Integer transferLeftMoney;
    private Integer receiverLeftMoney;
    @CreatedDate protected LocalDateTime date;

}
