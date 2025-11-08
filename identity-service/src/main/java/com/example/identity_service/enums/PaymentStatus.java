package com.example.identity_service.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentStatus {
    PAID("Đã thanh toán"),
    UNPAID("Chưa thanh toán"),
    PENDING("Đang chờ xử lý"),
    REFUNDED("Đã hoàn tiền");

    private final String vietnamese;

    PaymentStatus(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    @JsonValue
    public String getVietnamese() {
        return vietnamese;
    }
}

