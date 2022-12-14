package com.youngjun.bank.domain.dto;

import lombok.Data;

@Data
public class AdminDTO {

    public AdminDTO() {}

    public AdminDTO(String receiverAN, Integer value, String message) {
        this.receiverAN = receiverAN;
        this.value = value;
        this.message = message;
    }

    private final String TRANSFER = "영준뱅크";
    private String receiverAN;
    private Integer value;
    private String message;

}
