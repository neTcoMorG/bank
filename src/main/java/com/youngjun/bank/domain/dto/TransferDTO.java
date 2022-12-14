package com.youngjun.bank.domain.dto;

import com.youngjun.bank.domain.entity.User;
import lombok.Data;

@Data
public class TransferDTO {

    public TransferDTO() {}
    public TransferDTO(User transfer, User receiver, Integer value) {
        this.transfer = transfer;
        this.receiver = receiver;
        this.value = value;
    }

    private User transfer;
    private User receiver;
    private Integer value;
}
