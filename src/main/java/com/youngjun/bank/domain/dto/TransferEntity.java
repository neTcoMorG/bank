package com.youngjun.bank.domain.dto;

import com.youngjun.bank.domain.entity.User;
import lombok.Data;

@Data
public class TransferEntity {

    public TransferEntity(User transferAN, User receiverAN, Integer value) {
        this.transferAN = transferAN;
        this.receiverAN = receiverAN;
        this.value = value;
    }

    private User transferAN;
    private User receiverAN;
    private Integer value;
}
